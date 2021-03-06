package com.littlebuddha.bobogou.modules.controller.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.*;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.data.Order;
import com.littlebuddha.bobogou.modules.entity.data.OrderInfo;
import com.littlebuddha.bobogou.modules.entity.data.utils.OrderExportDTO;
import com.littlebuddha.bobogou.modules.service.common.DictDataService;
import com.littlebuddha.bobogou.modules.service.data.OrderInfoService;
import com.littlebuddha.bobogou.modules.service.data.OrderService;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/data/order")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private DictDataService dictDataService;

    @ModelAttribute
    public Order get(@RequestParam(required = false) String id) {
        Order order = null;
        if (StringUtils.isNotBlank(id)) {
            order = orderService.get(new Order(id));
        }
        if (order == null) {
            order = new Order();
        }
        return order;
    }

    /**
     * ??????????????????
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
     * ????????????
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
     * ??????????????????
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
     * ????????????
     *
     * @param mode
     * @param
     * @param model
     * @return
     */
    @GetMapping("/form/{mode}")
    public String form(@PathVariable(name = "mode") String mode, Order order, Model model) {
        model.addAttribute("order", order);
        if ("chargeBack".equals(mode)) {
            return "modules/data/orderChargeBackForm";
        }
        return "modules/data/orderForm";
    }

    /**
     * ????????????---??????id????????????????????????
     *
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/confirmDeliver")
    public RefundCalBackResult confirmDeliver(String id,Integer distributionMode) {
        RefundCalBackResult refundCalBackResult = new RefundCalBackResult();
        if (2 == distributionMode) {//??????????????????????????????????????????
            if (StringUtils.isNotBlank(id)) {
                String url = "http://1.117.222.27:8000/jd/placeOrder";
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("orderId", id);
                String json = HttpsUtil.httpGetWithJSON(url, paramMap);
                refundCalBackResult = JSON.parseObject(json, RefundCalBackResult.class);
            } else {
                refundCalBackResult = new RefundCalBackResult();
                refundCalBackResult.setSuccess(false);
                refundCalBackResult.setMsg("?????????????????????,??????????????????");
            }
        }else {
            int row = orderService.confirmDeliver(id);
            if (row > 0){
                refundCalBackResult.setCode(200);
            }else {
                refundCalBackResult.setCode(500);
            }
        }
        return refundCalBackResult;
    }

    /**
     * ???????????????????????????????????????????????????
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteOrderInfo")
    public Result deleteOrderInfo(String id) {
        int row = orderInfoService.deleteByLogic(new OrderInfo(id));
        return getCommonResult(row);
    }

    /**
     * ????????????????????????json?????????
     *
     * @param
     * @return
     */
    @ResponseBody
    @GetMapping("/orderInfoList")
    public List<OrderInfo> orderInfoList(String orderId) {
        List<OrderInfo> orderInfoList = null;
        if (StringUtils.isNotBlank(orderId)) {
            OrderInfo entity = new OrderInfo();
            entity.setOrderId(orderId);
            orderInfoList = orderInfoService.findList(entity);
        }
        return orderInfoList;
    }

    /**
     * ????????????
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Order order) {
        int save = orderService.save(order);
        if (save > 0) {
            return new Result("200", "????????????");
        } else {
            return new Result("310", "???????????????????????????");
        }
    }

    /**
     * ??????
     * @param order
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/refund")
    public RefundCalBackResult refund(Order order) throws IOException {
        RefundCalBackResult jsonObject = null;
        if (StringUtils.isNotBlank(order.getId())){
            String url = "http://1.117.222.27:8000/order/refund";
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("cause", order.getRefundReason());
            paramMap.put("explain", order.getRefundExplain());
            paramMap.put("orderId", Integer.valueOf(order.getId()));
            //paramMap.put("cause", "??????");
            //paramMap.put("explain", "??????");
            //paramMap.put("orderId", 549);
            String json = HttpsUtil.httpPostWithJSON(url, paramMap);
            jsonObject = JSON.parseObject(json, RefundCalBackResult.class);
        }else {
            jsonObject = new RefundCalBackResult();
            jsonObject.setSuccess(false);
            jsonObject.setMsg("?????????????????????,??????????????????");
        }
        return jsonObject;
    }

    @ResponseBody
    @GetMapping("/importTemplate")
    public Result importTemplate(HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "??????????????????.xlsx";
            List<Order> list = Lists.newArrayList();
            new ExportExcel("??????????????????", Order.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            result.setSuccess(true);
            result.setMsg("??????????????????????????????????????????" + e.getMessage());
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
                failureMsg.insert(0, "????????? " + failureNum + " ????????????????????????");
            }
            result.setSuccess(true);
            result.setMsg("??????????????? " + successNum + " ?????????????????????" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("??????????????????????????????????????????" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(String id, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "????????????" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<OrderExportDTO> list = orderService.findOrderExportList(id);
            if (list != null & list.size() > 0) {
                new ExportExcel("????????????", OrderExportDTO.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("????????????", OrderExportDTO.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
            }
            return null;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("??????????????????????????????????????????" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Order order = orderService.get(s);
            if (order == null) {
                return new Result("311", "???????????????,????????????????????????????????????");
            }
            int i = orderService.deleteByLogic(order);
        }
        return new Result("200", "??????????????????");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Order order = orderService.get(s);
            if (order == null) {
                return new Result("311", "???????????????,????????????????????????????????????");
            }
            int i = orderService.deleteByPhysics(order);
        }
        return new Result("200", "??????????????????");
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
            return new Result("200", "???????????????");
        } else {
            return new Result("322", "?????????????????????????????????");
        }
    }
}
