package com.littlebuddha.bobogou.modules.controller.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.JsonMapper;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.data.Order;
import com.littlebuddha.bobogou.modules.entity.data.OrderInfo;
import com.littlebuddha.bobogou.modules.service.data.OrderInfoService;
import com.littlebuddha.bobogou.modules.service.data.OrderService;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/data/order")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderInfoService orderInfoService;

    @ModelAttribute
    public Order get(@RequestParam(required = false) String id) {
        Order order = null;
        if (StringUtils.isNotBlank(id)) {
            order = orderService.get(id);
        }
        if (order == null) {
            order = new Order();
        }
        return order;
    }

    /**
     * 返回订单列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/order/list")
    @GetMapping(value = {"/", "/list"})
    public String list(Order order, Model model, HttpSession session) {
        model.addAttribute("order", order);
        return "modules/data/order";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Order order) {
        PageInfo<Order> page = orderService.findPage(new Page<Order>(), order);
        return getLayUiData(page);
    }

    /**
     * 返回所有数据
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/all")
    public List<Order> all(Order entity) {
        List<Order> list = orderService.findList(entity);
        return list;
    }

    /**
     * 返回表单
     *
     * @param mode
     * @param
     * @param model
     * @return
     */
    @GetMapping("/form/{mode}")
    public String form(@PathVariable(name = "mode") String mode, Order order, Model model) {
        List<OrderInfo> orderInfoList = orderInfoService.findList(new OrderInfo(order));
        String orderInfo = JsonMapper.getInstance().toJson(orderInfoList);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("order", order);
        return "modules/data/orderForm";
    }

    /**
     * 返回订单信息列表json字符串
     * @param
     * @return
     */
    @ResponseBody
    @GetMapping("/orderInfoList")
    public List<OrderInfo> orderInfoList(String orderId) {
        List<OrderInfo> orderInfoList = null;
        if (StringUtils.isNotBlank(orderId)){
            OrderInfo entity = new OrderInfo();
            entity.setOrderId(orderId);
            orderInfoList = orderInfoService.findList(entity);
        }
        return orderInfoList;
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Order order) {
        int save = orderService.save(order);
        if (save > 0) {
            return new Result("200", "保存成功");
        } else {
            return new Result("310", "未知错误！保存失败");
        }
    }

    @ResponseBody
    @GetMapping("/importTemplate")
    public Result importTemplate(HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "订单管理模板.xlsx";
            List<Order> list = Lists.newArrayList();
            new ExportExcel("订单管理数据", Order.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            result.setSuccess(true);
            result.setMsg("导入模板下载失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/importFile")
    public Result importFile(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
        Result result = new Result();
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<Order> list = ei.getDataList(Order.class);
            for (Order order : list) {
                try {
                    orderService.save(order);
                    successNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条订单管理记录。");
            }
            result.setSuccess(true);
            result.setMsg("已成功导入 " + successNum + " 条订单管理记录" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导入订单管理失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(Order order, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "订单管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<Order> list = orderService.findList(order);
            if (list != null & list.size() > 0) {
                new ExportExcel("订单管理", Order.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("订单管理", Order.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
            }
            return null;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导出订单记录失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String ids) {
        System.out.println("ids:" + ids);
        String[] split = ids.split(",");
        for (String s : split) {
            Order order = orderService.get(s);
            if (order == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = orderService.deleteByLogic(order);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Order order = orderService.get(s);
            if (order == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = orderService.deleteByPhysics(order);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Order order, Model model) {
        model.addAttribute("order", order);
        return "modules/recovery/orderRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Order order, Model model) {
        model.addAttribute("order", order);
        PageInfo<Order> page = orderService.findRecoveryPage(new Page<Order>(), order);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Order order) {
        int recovery = orderService.recovery(order);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
