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
import com.littlebuddha.bobogou.modules.entity.common.ActHistory;
import com.littlebuddha.bobogou.modules.entity.common.DictData;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;
import com.littlebuddha.bobogou.modules.entity.other.UserMember;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRole;
import com.littlebuddha.bobogou.modules.entity.system.Role;
import com.littlebuddha.bobogou.modules.mapper.system.OperatorRoleMapper;
import com.littlebuddha.bobogou.modules.mapper.system.RoleMapper;
import com.littlebuddha.bobogou.modules.service.common.ActHistoryService;
import com.littlebuddha.bobogou.modules.service.common.DictDataService;
import com.littlebuddha.bobogou.modules.service.other.CustomerUserService;
import com.littlebuddha.bobogou.modules.service.other.UserMemberService;
import com.littlebuddha.bobogou.modules.service.system.OperatorRoleService;
import com.littlebuddha.bobogou.modules.service.system.RoleService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/other/customerUser")
public class CustomerUserController extends BaseController {

    @Autowired
    private CustomerUserService customerUserService;

    @Autowired
    private UserMemberService userMemberService;

    @Autowired
    private OperatorRoleService operatorRoleService;

    @Autowired
    private OperatorRoleMapper operatorRoleMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ActHistoryService actHistoryService;

    @Autowired
    private DictDataService dictDataService;

    @ModelAttribute
    public CustomerUser get(@RequestParam(required = false) String id) {
        CustomerUser customerUser = null;
        if (StringUtils.isNotBlank(id)) {
            customerUser = customerUserService.get(id);
            if (customerUser.getUserMember() != null){
                UserMember byUser = userMemberService.getByUser(customerUser.getUserMember());
                customerUser.setUserMember(byUser);
            }
        }
        if (customerUser == null) {
            customerUser = new CustomerUser();
        }
        return customerUser;
    }

    @ResponseBody
    @GetMapping("/byPhone")
    public Result getByPhone(CustomerUser customerUser) {
        Result result = null;
        if (customerUser.getPhone() != null && StringUtils.isNotBlank(customerUser.getPhone())){
            CustomerUser byPhone = customerUserService.getByPhone(customerUser);
            result = new Result();
            result.setCode("200");
            result.setData(byPhone);
        }else {
            result = new Result("333","失败");
        }
        return result;
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
        Role currentUserRole = UserUtils.getCurrentUserRole();
        customerUser.setCurrentUserRole(currentUserRole);
        PageInfo<CustomerUser> page = customerUserService.findPage(new Page<CustomerUser>(), customerUser);
        return getLayUiData(page);
    }

    /**
     * 返回所有数据
     *
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
     * vip审核页面
     *
     * @param
     * @return
     */
    @GetMapping("/vipPage")
    public String vipPage(UserMember userMember, Model model) {
        if (userMember != null && userMember.getUserId() != null) {
            UserMember entity = userMemberService.getByUser(userMember);
            model.addAttribute("userMember", entity);
        }else {
            UserMember userMember1 = new UserMember();
            model.addAttribute("userMember", userMember1);
        }
        if (userMember != null && userMember.getUserId() != null){
            CustomerUser customerUser = customerUserService.get(userMember.getUserId().toString());
            model.addAttribute("customerUser", customerUser);
        }else {
            CustomerUser customerUser = new CustomerUser();
            model.addAttribute("customerUser", customerUser);
        }
        Operator currentUser = UserUtils.getCurrentUser();
        model.addAttribute("currentUserAreaManager", currentUser.getAreaManager());
        DictData type = new DictData();
        type.setType("user_member_type");
        List<DictData> typeList = dictDataService.findList(type);
        model.addAttribute("typeList", typeList);
        DictData isEntrust = new DictData();
        isEntrust.setType("user_member_is_entrust");
        List<DictData> isEntrustList = dictDataService.findList(isEntrust);
        model.addAttribute("isEntrustList", isEntrustList);
        DictData userMemberStatus = new DictData();
        userMemberStatus.setType("user_member_status");
        List<DictData> userMemberStatusList = dictDataService.findList(userMemberStatus);
        model.addAttribute("userMemberStatusList", userMemberStatusList);
        return "modules/other/userMemberForm";
    }

    @ResponseBody
    @PostMapping("/doTask")
    public Result doTask(CustomerUser customerUser) {
        Result result = new Result();
        //点击提交时，新建审核历史记录
        ActHistory actHistory = new ActHistory();
        //获取当前角色
        Operator currentUser = UserUtils.getCurrentUser();
        List<OperatorRole> byOperatorAndRole = operatorRoleMapper.getByOperatorAndRole(new OperatorRole(currentUser));
        String reason = "";
        actHistory.setDataId(customerUser.getId());
        actHistory.setActType(customerUser.getActType());
        if (byOperatorAndRole != null) {
            if (byOperatorAndRole.get(0) != null && byOperatorAndRole.get(0).getRole() != null && StringUtils.isNotBlank(byOperatorAndRole.get(0).getRole().getId())) {
                Role currentRole = roleMapper.get(new Role(byOperatorAndRole.get(0).getRole().getId()));
                //设置下一个审核角色
                customerUser.setNextRole(currentRole.getParentId());
                if (StringUtils.isNotBlank(currentRole.getName())) {
                    if (2 == customerUser.getApplyStatus() || "1".equals(customerUser.getUserMember().getStatus()) || "3".equals(customerUser.getUserMember().getStatus()) || "5".equals(customerUser.getUserMember().getStatus())) {
                        reason = "通过";
                        result.setMsg("已通过VIP审核");
                    }
                    if (3 == customerUser.getApplyStatus() ||  "2".equals(customerUser.getUserMember().getStatus()) || "4".equals(customerUser.getUserMember().getStatus()) || "6".equals(customerUser.getUserMember().getStatus())) {
                        reason = "拒绝";
                        result.setMsg("已拒绝VIP审核");
                    }
                    actHistory.setExecutionLink(currentRole.getName() + "VIP审核" + reason);
                }
                actHistory.setRoleId(currentRole.getId());
                actHistory.setRoleName(currentRole.getName());
            }
        }
        actHistory.setExecutionId(currentUser.getId());
        actHistory.setExecutionName(currentUser.getNickname());
        actHistory.setBeginDate(new Date());
        actHistory.setEndDate(new Date());
        //保存提交审核的历史记录
        actHistoryService.save(actHistory);
        int row = customerUserService.beVip(customerUser);
        Result commonResult = getCommonResult(row);
        return commonResult;
    }

    @GetMapping("/toDoList")
    public String toDoList(CustomerUser customerUser,Model model){
        model.addAttribute("customerUser", customerUser);
        return "modules/other/customerUserToDoList";
    }

    @ResponseBody
    @GetMapping("/toDoData")
    public TreeResult toDoData(CustomerUser customerUser,Model model){
        PageInfo<CustomerUser> page = null;
        Operator currentUser = UserUtils.getCurrentUser();
        if(currentUser.getAreaManager() != 3){
            List<OperatorRole> byOperatorAndRole = operatorRoleService.findByOperatorAndRole(new OperatorRole(currentUser));
            Role role = roleService.get(byOperatorAndRole.get(0).getRole().getId());
            customerUser.setCurrentUserRole(role);
            page = customerUserService.findToDoDataPage(new Page<CustomerUser>(), customerUser);
        }else {
            //查询当前区级管理员所在区域的VIP提交的申请数据
            page = customerUserService.findVipApplyForAreaManager(new Page<CustomerUser>(), customerUser);
        }
        model.addAttribute("customerUser", customerUser);
        return getLayUiData(page);
    }

    /**
     * @return
     */
    @GetMapping("/flow")
    public String flow(CustomerUser customerUser, Model model) {
        model.addAttribute("customerUser", customerUser);
        return "modules/other/customerUserAct";
    }

    /**
     * @return
     */
    @ResponseBody
    @GetMapping("/flowData")
    public TreeResult flowData(ActHistory actHistory, Model model) {
        PageInfo<ActHistory> page = actHistoryService.findPage(new Page<ActHistory>(), actHistory);
        return getLayUiData(page);
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
