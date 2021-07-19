package com.littlebuddha.bobogou.modules.controller.system;

import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;
import com.littlebuddha.bobogou.modules.entity.system.*;
import com.littlebuddha.bobogou.modules.entity.system.utils.HomeInfo;
import com.littlebuddha.bobogou.modules.entity.system.utils.LogoInfo;
import com.littlebuddha.bobogou.modules.service.system.MenuService;
import com.littlebuddha.bobogou.modules.service.system.OperatorRoleService;
import com.littlebuddha.bobogou.modules.service.system.PortalService;
import com.littlebuddha.bobogou.modules.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 门户页controller
 */
@Controller
@RequestMapping("/portal")
public class PortalController extends BaseController {

    @Autowired
    private PortalService portalService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private LogoInfo logoInfo;//logo信息--保存在配置文件中

    @Autowired
    private HomeInfo homeInfo;//home信息--保存在配置文件中

    @Autowired
    private OperatorRoleService operatorRoleService;

    @Autowired
    private RoleService roleService;

    /**
     * 门户页面
     *
     * @return
     */
    @GetMapping(value = {"/", "/list"})
    public String list(HttpSession session, Model model) {
        Operator currentUser = UserUtils.getCurrentUser();
        session.setAttribute("currentUser", currentUser);
        CustomerUser currentCustomerUser = UserUtils.getCurrentCustomerUser();
        session.setAttribute("currentCustomerUser", currentCustomerUser);
        //是否显示主页导航条的搜索框
        model.addAttribute("showNavSearch", true);
        return "modules/system/portal";
    }

    /**
     * 登录成功请求的菜单数据
     */
    @ResponseBody
    @GetMapping("/data")
    public Portal data() {
        Portal portal = new Portal();
        portal.setHomeInfo(homeInfo);
        portal.setLogoInfo(logoInfo);
        List<Menu> menuInfo = menuService.findMenuInfo();
        portal.setMenuInfo(menuInfo);
        return portal;
    }

    @GetMapping("/importTemplate")
    public String importTemplate() {

        return "modules/common/import";
    }

    @ResponseBody
    @GetMapping("/getCurrentCustomerUser")
    public CustomerUser getCurrentCustomerUser(){
        CustomerUser currentCustomerUser = UserUtils.getCurrentCustomerUser();
        return currentCustomerUser;
    }

    /**
     * 获取当前用户角色id----供审核流程时使用
     * @return
     */
    @ResponseBody
    @GetMapping("/currentUserRole")
    public String getCurrentUserRole(){
        Operator currentUser = UserUtils.getCurrentUser();
        String roleId = null;
        List<OperatorRole> byOperatorAndRole = new ArrayList<>();
        if (currentUser != null){
            byOperatorAndRole = operatorRoleService.findByOperatorAndRole(new OperatorRole(currentUser));
        }
        if (!byOperatorAndRole.isEmpty()){
            roleId = byOperatorAndRole.get(0).getRole().getId();
        }
        return roleId;
    }

    /**
     * 获取当前用户上级角色id----供审核流程时使用
     * @return
     */
    @ResponseBody
    @GetMapping("/currentUserParentRole")
    public String getCurrentUserParentRole(){
        Result result = new Result();
        Operator currentUser = UserUtils.getCurrentUser();
        String parentRoleId = null;
        List<OperatorRole> byOperatorAndRole = new ArrayList<>();
        if (currentUser != null){
            byOperatorAndRole = operatorRoleService.findByOperatorAndRole(new OperatorRole(currentUser));
        }
        if (!byOperatorAndRole.isEmpty()){
            OperatorRole operatorRole = byOperatorAndRole.get(0);
            if (operatorRole != null){
                Role role = operatorRole.getRole();
                if (role != null){
                    String roleId = role.getId();
                    Role parent = roleService.get(new Role(roleId));
                    parentRoleId = parent.getParentId();
                    result.setCode("200");
                    result.setMsg(parentRoleId);
                }
            }
        }else {
            result.setCode("444");
            result.setMsg("当前用户角色设置不当");
        }
        return parentRoleId;
    }
}
