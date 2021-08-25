package com.littlebuddha.bobogou.modules.controller.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.common.DictData;
import com.littlebuddha.bobogou.modules.entity.data.Order;
import com.littlebuddha.bobogou.modules.entity.data.OrderFactory;
import com.littlebuddha.bobogou.modules.entity.data.OrderInfo;
import com.littlebuddha.bobogou.modules.service.common.DictDataService;
import com.littlebuddha.bobogou.modules.service.data.OrderFactoryService;
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
@RequestMapping("/data/orderFactory")
public class OrderFactoryController extends BaseController {

    @Autowired
    private OrderFactoryService orderFactoryService;

    @Autowired
    private DictDataService dictDataService;

    @ModelAttribute
    public OrderFactory get(@RequestParam(required = false) String id) {
        OrderFactory orderFactory = null;
        if (StringUtils.isNotBlank(id)) {
            orderFactory = orderFactoryService.get(new OrderFactory(id));
        }
        if (orderFactory == null) {
            orderFactory = new OrderFactory();
        }
        return orderFactory;
    }

    /**
     * 返回订单列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    @GetMapping(value = {"/", "/list"})
    public String list(OrderFactory orderFactory, Model model, HttpSession session) {
        model.addAttribute("orderFactory", orderFactory);
        return "modules/data/orderFactory";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(OrderFactory orderFactory) {
        PageInfo<OrderFactory> page = orderFactoryService.findPage(new Page<OrderFactory>(), orderFactory);
        return getLayUiData(page);
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
    public String form(@PathVariable(name = "mode") String mode, OrderFactory orderFactory, Model model) {
        DictData dictData = new DictData();
        dictData.setType("data_order_factory_distribution_mode");
        List<DictData> distributionModeList = dictDataService.findList(dictData);
        model.addAttribute("distributionModeList", distributionModeList);
        DictData outStatusSelect = new DictData();
        dictData.setType("data_order_factory_out_status");
        List<DictData> outStatusList = dictDataService.findList(outStatusSelect);
        model.addAttribute("outStatusList", outStatusList);
        model.addAttribute("orderFactory", orderFactory);
        return "modules/data/orderFactoryForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(OrderFactory orderFactory) {
        int save = orderFactoryService.save(orderFactory);
        if (save > 0) {
            return new Result("200", "保存成功");
        } else {
            return new Result("310", "未知错误！保存失败");
        }
    }

    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String ids) {
        System.out.println("ids:" + ids);
        String[] split = ids.split(",");
        for (String s : split) {
            OrderFactory orderFactory = orderFactoryService.get(s);
            if (orderFactory == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = orderFactoryService.deleteByLogic(orderFactory);
        }
        return new Result("200", "数据清除成功");
    }
}
