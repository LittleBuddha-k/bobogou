/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.littlebuddha.bobogou.modules.controller.system;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.system.Menu;
import com.littlebuddha.bobogou.modules.entity.system.Role;
import com.littlebuddha.bobogou.modules.service.system.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Controller
@RequestMapping(value = "/system/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @ModelAttribute
    public Role get(@RequestParam(required = false) String id) {
        Role role = null;
        if (StringUtils.isNotBlank(id)) {
            role = roleService.get(id);
        }
        if (role == null) {
            role = new Role();
        }
        return role;
    }

    /**
     * 返回用户列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/operator/list")
    @GetMapping(value = {"/", "/list"})
    public String list(Role role, Model model, HttpSession session) {
        model.addAttribute("role", role);
        return "modules/system/role";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Role role) {
        Role currentUserRole = UserUtils.getCurrentUserRole();
        role.setCurrentUserRole(currentUserRole);
        PageInfo<Role> page = roleService.findPage(new Page<Role>(), role);
        return getLayUiData(page);
    }

    @ResponseBody
    @GetMapping("/allData")
    public TreeResult allData(Role role) {
        TreeResult result = null;
        List<Role> allList = roleService.findList(new Role());
        if (allList != null && allList.size() > 0) {
            result = new TreeResult(0, "数据成功", allList, allList.size());
        } else {
            result = new TreeResult(466, "无数据");
        }
        return result;
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
    public String form(@PathVariable(name = "mode") String mode, Role role, Model model) {
        Role currentUserRole = UserUtils.getCurrentUserRole();
        //当点击新建时,查询所有角色
        Role entity = new Role();
        entity.setCurrentUserRole(currentUserRole);
        if (!"add".equals(mode)){
            List<Role> roleList = roleService.findNoAddList(entity);
            model.addAttribute("roleList", roleList);
        }else {
            List<Role> roleList = roleService.findList(entity);
            model.addAttribute("roleList", roleList);
        }
        model.addAttribute("role", role);
        return "modules/system/roleForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Role role) {
        int save = roleService.save(role);
        if (save > 0) {
            return new Result("200", "保存成功");
        } else {
            return new Result("310", "未知错误！保存失败");
        }
    }

    @GetMapping("/permissionPage")
    public String permissionPage(Role role, Model model) {
        List<Menu> menusByRole = new ArrayList<>();
        if (role != null && StringUtils.isNotBlank(role.getId())) {
            menusByRole = roleService.findMenusByRole(role);
            String menusId = "";
            for (Menu menu : menusByRole) {
                if (menu != null && StringUtils.isNotBlank(menu.getId())) {
                    menusId = menu.getId() + "," + menusId;
                }
            }
            model.addAttribute("menusId", menusId);
        }
        model.addAttribute("role", role);
        return "modules/system/permissionPage";
    }

    @ResponseBody
    @PostMapping("/addPermission")
    public Result addPermission(Role role) {
        int row = roleService.addPermission(role);
        return getCommonResult(row);
    }

    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String ids) {
        Result result = null;
        String[] split = ids.split(",");
        for (String s : split) {
            Role role = roleService.get(s);
            if (role == null) {
                result = new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }else if (StringUtils.isNotBlank(role.getId()) && "1".equals(role.getId())){
                result = new Result("311", "超级管理员角色不能被删除");
            }else {
                int i = roleService.deleteByLogic(role);
                result = getCommonResult(i);
            }
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        Result result = null;
        String[] split = ids.split(",");
        for (String s : split) {
            Role role = roleService.get(s);
            if (role == null) {
                result = new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }else if (StringUtils.isNotBlank(role.getId()) && "1".equals(role.getId())){
                result = new Result("311", "超级管理员角色不能被删除");
            }else {
                int i = roleService.deleteByPhysics(role);
                result = getCommonResult(i);
            }
        }
        return result;
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Role role, Model model) {
        model.addAttribute("role", role);
        return "modules/recovery/roleRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Role role, Model model) {
        model.addAttribute("role", role);
        PageInfo<Role> page = roleService.findRecoveryPage(new Page<Role>(), role);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Role role) {
        int recovery = roleService.recovery(role);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
