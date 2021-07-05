package com.littlebuddha.bobogou.modules.controller.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.data.City;
import com.littlebuddha.bobogou.modules.entity.other.CustomerService;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.service.data.CityService;
import com.littlebuddha.bobogou.modules.service.other.CustomerServiceService;
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
@RequestMapping("/other/customerService")
public class CustomerServiceController extends BaseController {

    @Autowired
    private CustomerServiceService customerServiceService;

    @ModelAttribute
    public CustomerService get(@RequestParam(required = false) String id) {
        CustomerService customerService = null;
        if (StringUtils.isNotBlank(id)) {
            customerService = customerServiceService.get(id);
        }
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    @GetMapping(value = {"/", "/list"})
    public String list(CustomerService customerService, Model model, HttpSession session) {
        model.addAttribute("customerService", customerService);
        return "modules/other/customerService";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/noReadChat")
    public List<CustomerService> noReadChat(CustomerService customerService) {
        Operator currentUser = UserUtils.getCurrentUser();
        if(currentUser != null && StringUtils.isNotBlank(currentUser.getId())){
            customerService.setAccountId(currentUser.getId());
        }
        //查询当前登录用户de未读聊天记录
        List<CustomerService> list = customerServiceService.findNoReadChat(customerService);
        return list;
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(CustomerService customerService) {
        int save = customerServiceService.save(customerService);
        if (save > 0) {
            return new Result("200", "保存成功");
        } else {
            return new Result("310", "未知错误！保存失败");
        }
    }
}
