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
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;
import com.littlebuddha.bobogou.modules.service.data.CityService;
import com.littlebuddha.bobogou.modules.service.other.CustomerUserService;
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
@RequestMapping("/data/customerUser")
public class CustomerUserController extends BaseController {

    @Autowired
    private CustomerUserService customerUserService;

    @ModelAttribute
    public CustomerUser get(@RequestParam(required = false) String id) {
        CustomerUser customerUser = null;
        if (StringUtils.isNotBlank(id)) {
            customerUser = customerUserService.get(id);
        }
        if (customerUser == null) {
            customerUser = new CustomerUser();
        }
        return customerUser;
    }

    /**
     * 返回客户列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/CustomerUser/list")
    @GetMapping(value = {"/", "/list"})
    public String list(CustomerUser customerUser, Model model, HttpSession session) {
        model.addAttribute("customerUser", customerUser);
        return "modules/other/customerUser";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(CustomerUser customerUser) {
        PageInfo<CustomerUser> page = customerUserService.findPage(new Page<CustomerUser>(), customerUser);
        return getLayUiData(page);
    }

    /**
     * 返回所有数据
     * @return
     */
    @ResponseBody
    @PostMapping("/all")
    public List<CustomerUser> all(CustomerUser customerUser) {
        List<CustomerUser> list = customerUserService.findList(customerUser);
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
    public String form(@PathVariable(name = "mode") String mode, CustomerUser customerUser, Model model) {
        model.addAttribute("customerUser", customerUser);
        return "modules/other/customerUserForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(CustomerUser customerUser) {
        int save = customerUserService.save(customerUser);
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
            String fileName = "客户管理模板.xlsx";
            List<CustomerUser> list = Lists.newArrayList();
            new ExportExcel("客户管理数据", CustomerUser.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<CustomerUser> list = ei.getDataList(CustomerUser.class);
            for (CustomerUser customerUser : list) {
                try {
                    customerUserService.save(customerUser);
                    successNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条客户管理记录。");
            }
            result.setSuccess(true);
            result.setMsg("已成功导入 " + successNum + " 条客户管理记录" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导入客户管理失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(CustomerUser customerUser, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "客户管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<CustomerUser> list = customerUserService.findList(customerUser);
            if (list != null & list.size() > 0) {
                new ExportExcel("客户管理", CustomerUser.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("客户管理", CustomerUser.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
            }
            return null;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导出客户记录失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String ids) {
        System.out.println("ids:" + ids);
        String[] split = ids.split(",");
        for (String s : split) {
            CustomerUser customerUser = customerUserService.get(s);
            if (customerUser == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = customerUserService.deleteByLogic(customerUser);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            CustomerUser customerUser = customerUserService.get(s);
            if (customerUser == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = customerUserService.deleteByPhysics(customerUser);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(CustomerUser customerUser, Model model) {
        model.addAttribute("customerUser", customerUser);
        return "modules/recovery/customerUserRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(CustomerUser customerUser, Model model) {
        model.addAttribute("customerUser", customerUser);
        PageInfo<CustomerUser> page = customerUserService.findRecoveryPage(new Page<CustomerUser>(), customerUser);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(CustomerUser customerUser) {
        int recovery = customerUserService.recovery(customerUser);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
