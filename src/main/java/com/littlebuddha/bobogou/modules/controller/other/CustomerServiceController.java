package com.littlebuddha.bobogou.modules.controller.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.data.City;
import com.littlebuddha.bobogou.modules.entity.other.CustomerService;
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
@RequestMapping("/data/customerService")
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
    //@RequiresPermissions("system/CustomerService/list")
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
    @GetMapping("/data")
    public TreeResult data(CustomerService customerService) {
        PageInfo<CustomerService> page = customerServiceService.findPage(new Page<CustomerService>(), customerService);
        return getLayUiData(page);
    }

    /**
     * 返回所有数据
     * @return
     */
    @ResponseBody
    @PostMapping("/all")
    public List<CustomerService> all(CustomerService customerService) {
        List<CustomerService> list = customerServiceService.findList(customerService);
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
    public String form(@PathVariable(name = "mode") String mode, CustomerService customerService, Model model) {
        model.addAttribute("customerService", customerService);
        return "modules/other/customerServiceForm";
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

    @ResponseBody
    @GetMapping("/importTemplate")
    public Result importTemplate(HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "客服管理模板.xlsx";
            List<CustomerService> list = Lists.newArrayList();
            new ExportExcel("客服管理数据", CustomerService.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<CustomerService> list = ei.getDataList(CustomerService.class);
            for (CustomerService customerService : list) {
                try {
                    customerServiceService.save(customerService);
                    successNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条客服管理记录。");
            }
            result.setSuccess(true);
            result.setMsg("已成功导入 " + successNum + " 条客服管理记录" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导入客服管理失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(CustomerService customerService, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "客服管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<CustomerService> list = customerServiceService.findList(customerService);
            if (list != null & list.size() > 0) {
                new ExportExcel("客服管理", CustomerService.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("客服管理", CustomerService.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
            }
            return null;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导出药品记录失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String ids) {
        System.out.println("ids:" + ids);
        String[] split = ids.split(",");
        for (String s : split) {
            CustomerService customerService = customerServiceService.get(s);
            if (customerService == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = customerServiceService.deleteByLogic(customerService);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            CustomerService customerService = customerServiceService.get(s);
            if (customerService == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = customerServiceService.deleteByPhysics(customerService);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(CustomerService customerService, Model model) {
        model.addAttribute("customerService", customerService);
        return "modules/recovery/customerServiceRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(CustomerService customerService, Model model) {
        model.addAttribute("customerService", customerService);
        PageInfo<CustomerService> page = customerServiceService.findRecoveryPage(new Page<CustomerService>(), customerService);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(CustomerService customerService) {
        int recovery = customerServiceService.recovery(customerService);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
