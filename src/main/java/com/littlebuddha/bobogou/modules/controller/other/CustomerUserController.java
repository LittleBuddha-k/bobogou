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
import com.littlebuddha.bobogou.modules.entity.other.Vip;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRole;
import com.littlebuddha.bobogou.modules.entity.system.Role;
import com.littlebuddha.bobogou.modules.mapper.other.VipMapper;
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

import javax.annotation.Resource;
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

    @Resource
    private OperatorRoleMapper operatorRoleMapper;

    @Autowired
    private RoleService roleService;

    @Resource
    private RoleMapper roleMapper;

    @Autowired
    private ActHistoryService actHistoryService;

    @Autowired
    private DictDataService dictDataService;

    @Resource
    private VipMapper vipMapper;

    @ModelAttribute
    public CustomerUser get(@RequestParam(required = false) String id) {
        CustomerUser customerUser = null;
        if (StringUtils.isNotBlank(id)) {
            customerUser = customerUserService.get(new CustomerUser(id));
            if (customerUser.getUserMember() != null){
                //?????????userMember???????????????
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
            result = new Result("333","??????");
        }
        return result;
    }

    /**
     * ??????????????????
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
     * ????????????----------?????????????????????????????????????????????????????????----?????????????????????
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(CustomerUser customerUser) {
        Operator currentUser = UserUtils.getCurrentUser();
        currentUser.setCurrentUser(currentUser);
        PageInfo<CustomerUser> page = new PageInfo<>();
        //?????????????????????????????????????????????????????????????????????????????????????????????
        if (currentUser != null && (4 == currentUser.getAreaManager() || 5 == currentUser.getAreaManager() || 6 == currentUser.getAreaManager())) {
            page = customerUserService.findPage(new Page<CustomerUser>(), customerUser);
        }
        return getLayUiData(page);
    }

    /**
     * ??????????????????????????????????????????????????????
     *
     * @return
     */
    @GetMapping("/vipOverStayedList")
    public String vipOverStayedList(CustomerUser customerUser,Model model) {
        model.addAttribute("customerUser", customerUser);
        return "modules/other/vipOverStayedList";
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/vipOverStayedData")
    public TreeResult vipOverStayedData(CustomerUser customerUser) {
        Operator currentUser = UserUtils.getCurrentUser();
        currentUser.setCurrentUser(currentUser);
        PageInfo<CustomerUser> page = new PageInfo<>();
        //????????????????????????????????????
        if (currentUser != null && (4 == currentUser.getAreaManager() || 5 == currentUser.getAreaManager() || 6 == currentUser.getAreaManager())) {
            page = customerUserService.findVipOverStayedPage(new Page<CustomerUser>(), customerUser);
        }
        return getLayUiData(page);
    }

    @ResponseBody
    @PostMapping("/recoveryVip")
    public Result recoveryVip(CustomerUser customerUser){
        Result result = new Result();
        if (customerUser == null){
            customerUser = new CustomerUser();
        }
        //1.VIP???????????????1
        customerUser.setMember(1);
        //2.VIP?????????????????????2
        customerUser.setApplyStatus(2);
        //3.VIP??????????????????-----???????????????????????????????????????
        UserMember userMember = new UserMember();
        userMember.setUserId(Integer.valueOf(customerUser.getId()));
        UserMember byUser = userMemberService.getByUser(userMember);
        if (byUser == null){
            result.setSuccess(false);
            result.setMsg("??????????????????????????????");
        }else {
            //???????????????????????????????????????????????????vip??????????????????vip??????
            String type = byUser.getType();
            Vip vip = new Vip();
            vip.setType(Integer.valueOf(type));
            Vip byType = vipMapper.getByType(vip);
            if (byType != null && byType.getTime() != null){
                Date specifyDate = DateUtils.getSpecifyDate(byType.getTime());
                String fullDate = DateUtils.getFullDate(specifyDate);
                customerUser.setVipExpire(fullDate);
            }
            byUser.setVipStatus(0);
            userMemberService.updateVipStatus(byUser);
        }
        int row = customerUserService.recoveryVip(customerUser);
        if (row > 0){
            result.setSuccess(true);
            result.setMsg("??????????????????");
        }else {
            result.setSuccess(false);
            result.setMsg("??????????????????");
        }
        return result;
    }

    /**
     * ??????????????????
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
     * ??????????????????
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
     * ??????????????????vip?????????
     * @param mode
     * @param
     * @param model
     * @return
     */
    @GetMapping("/userMemberForm/{mode}")
    public String userMemberForm(@PathVariable(name = "mode") String mode, CustomerUser customerUser, Model model) {
        //????????????????????????
        if ("vipAct".equals(mode)){
            if (customerUser != null) {
                model.addAttribute("customerUser", customerUser);
                String customerUserId = customerUser.getId();
                if (StringUtils.isNotBlank(customerUserId)) {
                    UserMember userMember1 = new UserMember();
                    userMember1.setUserId(Integer.valueOf(customerUserId));
                    UserMember userMember = userMemberService.getByUser(userMember1);
                    if (userMember == null) {
                        userMember = new UserMember();
                    }
                    model.addAttribute("userMember", userMember);
                }else {
                    UserMember userMember = new UserMember();
                    model.addAttribute("userMember", userMember);
                }
            }else {
                customerUser = new CustomerUser();
                model.addAttribute("customerUser", customerUser);
                UserMember userMember = new UserMember();
                model.addAttribute("userMember", userMember);
            }
            Operator currentUser = UserUtils.getCurrentUser();
            model.addAttribute("currentUserAreaManager", currentUser.getAreaManager());
        }else {
            if (customerUser != null) {
                String customerUserId = customerUser.getId();
                if (StringUtils.isNotBlank(customerUserId)) {
                    UserMember userMember1 = new UserMember();
                    userMember1.setUserId(Integer.valueOf(customerUserId));
                    UserMember userMember = userMemberService.getByUser(userMember1);
                    if (userMember == null) {
                        userMember = new UserMember();
                    }
                    model.addAttribute("userMember", userMember);
                }else {
                    UserMember userMember = new UserMember();
                    model.addAttribute("userMember", userMember);
                }
            }else {
                UserMember userMember = new UserMember();
                model.addAttribute("userMember", userMember);
            }
        }
        return "modules/other/userMemberForm";
    }

    /**
     * vip????????????----?????????
     *
     * @param
     * @return
     */
    @GetMapping("/vipPage")
    public String vipPage(UserMember userMember, Model model) {
        if (userMember != null && userMember.getUserId() != null) {
            UserMember entity = userMemberService.getByUser(userMember);
            if (entity == null) {
                entity = new UserMember();
            }
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
        return "modules/other/userMemberForm";
    }

    @ResponseBody
    @PostMapping("/doTask")
    public Result doTask(CustomerUser customerUser) {
        Result result = new Result();
        //??????????????????????????????????????????
        ActHistory actHistory = new ActHistory();
        //??????????????????
        Operator currentUser = UserUtils.getCurrentUser();
        List<OperatorRole> byOperatorAndRole = operatorRoleMapper.getByOperatorAndRole(new OperatorRole(currentUser));
        String reason = "";
        actHistory.setDataId(customerUser.getId());
        actHistory.setActType(customerUser.getActType());
        if (byOperatorAndRole != null) {
            if (byOperatorAndRole.get(0) != null && byOperatorAndRole.get(0).getRole() != null && StringUtils.isNotBlank(byOperatorAndRole.get(0).getRole().getId())) {
                Role currentRole = roleMapper.get(new Role(byOperatorAndRole.get(0).getRole().getId()));
                //???????????????????????????
                customerUser.setNextRole(currentRole.getParentId());
                if (StringUtils.isNotBlank(currentRole.getName())) {
                    if (2 == customerUser.getApplyStatus() || "1".equals(customerUser.getUserMember().getStatus()) || "3".equals(customerUser.getUserMember().getStatus()) || "5".equals(customerUser.getUserMember().getStatus())) {
                        reason = "??????";
                        result.setMsg("?????????VIP??????");
                    }
                    if (3 == customerUser.getApplyStatus() ||  "2".equals(customerUser.getUserMember().getStatus()) || "4".equals(customerUser.getUserMember().getStatus()) || "6".equals(customerUser.getUserMember().getStatus())) {
                        reason = "??????";
                        result.setMsg("?????????VIP??????");
                    }
                    actHistory.setExecutionLink(currentRole.getName() + "VIP??????" + reason);
                }
                actHistory.setRoleId(currentRole.getId());
                actHistory.setRoleName(currentRole.getName());
            }
        }
        actHistory.setExecutionId(currentUser.getId());
        actHistory.setExecutionName(currentUser.getNickname());
        actHistory.setBeginDate(new Date());
        actHistory.setEndDate(new Date());
        //?????????????????????????????????
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
            //??????????????????????????????????????????VIP?????????????????????
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
     * ????????????
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(CustomerUser customerUser) {
        int save = customerUserService.save(customerUser);
        if (save > 0) {
            return new Result("200", "????????????");
        } else {
            return new Result("310", "???????????????????????????");
        }
    }

    @ResponseBody
    @GetMapping("/importTemplate")
    public Result importTemplate(HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "??????????????????.xlsx";
            List<CustomerUser> list = Lists.newArrayList();
            new ExportExcel("??????????????????", CustomerUser.class, 1).setDataList(list).write(response, fileName).dispose();
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
    public Result exportFile(CustomerUser customerUser, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "????????????" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<CustomerUser> list = customerUserService.findList(customerUser);
            if (list != null & list.size() > 0) {
                new ExportExcel("????????????", CustomerUser.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("????????????", CustomerUser.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
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
        System.out.println("ids:" + ids);
        String[] split = ids.split(",");
        for (String s : split) {
            CustomerUser customerUser = customerUserService.get(s);
            if (customerUser == null) {
                return new Result("311", "???????????????,????????????????????????????????????");
            }
            int i = customerUserService.deleteByLogic(customerUser);
        }
        return new Result("200", "??????????????????");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            CustomerUser customerUser = customerUserService.get(s);
            if (customerUser == null) {
                return new Result("311", "???????????????,????????????????????????????????????");
            }
            int i = customerUserService.deleteByPhysics(customerUser);
        }
        return new Result("200", "??????????????????");
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
            return new Result("200", "???????????????");
        } else {
            return new Result("322", "?????????????????????????????????");
        }
    }
}
