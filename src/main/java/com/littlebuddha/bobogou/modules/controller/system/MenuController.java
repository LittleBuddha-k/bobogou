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
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.service.system.MenuService;
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
 * 菜单控制层
 */
@Controller
@RequestMapping(value = "/system/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @ModelAttribute
    public Menu get(@RequestParam(required = false) String id) {
        Menu menu = null;
        if (StringUtils.isNotBlank(id)) {
            menu = menuService.get(id);
        }
        if (menu == null) {
            menu = new Menu();
        }
        return menu;
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
    public String list(Menu menu, Model model, HttpSession session) {
        model.addAttribute("menu", menu);
        return "modules/system/menu";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Menu menu) {
        TreeResult result = null;
        List<Menu> allList = new ArrayList<>();
        Operator currentUser = UserUtils.getCurrentUser();
        if (currentUser != null && "1".equals(currentUser.getId())) {
            allList = menuService.findAllList(new Menu());
        }else if (currentUser != null && !"1".equals(currentUser.getId())){
            allList = menuService.findListByCurrentUser(menu);
        }
        if (allList != null && allList.size() > 0) {
            result = new TreeResult(0, "数据成功", allList, allList.size());
        } else {
            result = new TreeResult(466, "无数据");
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/allData")
    public Map<String, Object> allData(Menu menu) {
        PageInfo<Menu> page = menuService.findPage(new Page<Menu>(), menu);
        return getBootstrapData(page);
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
    public String form(@PathVariable(name = "mode") String mode, Menu menu, Model model) {
        //当查看的菜单为师祖级菜单
        if (menu.getParentId() == null) {
            menu.setParent(menuService.getTopMenu());
        }
        //为其设置parent
        if (menu.getParentId() != null) {
            Menu entity = menuService.get(menu.getParentId());
            menu.setParent(entity);
        }
        //前端下拉选项数据
        List<Menu> allList = menuService.findAllList(new Menu());
        model.addAttribute("allList", allList);
        model.addAttribute("menu", menu);
        return "modules/system/menuForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Menu menu) {
        Result result = new Result();
        if(UserUtils.getCurrentUser() != null && StringUtils.isNotBlank(UserUtils.getCurrentUser().getId())){
            if (!UserUtils.isAdmin(UserUtils.getCurrentUser().getId())){
                result.setCode("222");
                result.setMsg("越权操作，只有超级管理员才能添加或修改数据！");
                return result;
            }
        }
        // 获取排序号，最末节点排序号+30
        if (StringUtils.isBlank(menu.getId())){
            List<Menu> list = new ArrayList<>();
            List<Menu> sourcelist = menuService.findAllList(new Menu());
            Menu.sortList(list, sourcelist, menu.getParentId(), false);
            if (list.size() > 0){
                menu.setSort(list.get(list.size()-1).getSort() + 30);
            }else {
                menu.setSort(30);
            }
        }
        int save = menuService.save(menu);
        Result commonResult = getCommonResult(save);
        return commonResult;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String ids) {
        Result result = null;
        String[] split = ids.split(",");
        for (String s : split) {
            Menu menu = menuService.get(s);
            if (menu == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }else {
                if (StringUtils.isNotBlank(menu.getId()) && "-1".equals(menu.getId())){
                    result = new Result("311", "菜单管理不能被删除！！！");
                }else if (StringUtils.isNotBlank(menu.getId()) && "d615fa4b406b4173aa909ef403669ba6".equals(menu.getId())){
                    result = new Result("311", "系统设置不能被删除！！！");
                }else if (StringUtils.isNotBlank(menu.getId()) && "928b13d7655d478f81c627e28423efd7".equals(menu.getId())){
                    result = new Result("311", "管理菜单不能被删除！！！");
                }else {
                    int i = menuService.deleteByLogic(menu);
                    result = getCommonResult(i);
                }
            }
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            if (s == "" || StringUtils.isBlank(s)) {

            } else {
                Menu menu = menuService.get(s);
                if (menu == null) {
                    return new Result("366", "数据不存在或者数据已被清除");
                } else {
                    int i = menuService.deleteByPhysics(menu);
                }
            }
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Menu menu, Model model) {
        model.addAttribute("menu", menu);
        return "modules/recovery/menuRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Menu menu, Model model) {
        model.addAttribute("menu", menu);
        PageInfo<Menu> page = menuService.findRecoveryPage(new Page<Menu>(), menu);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Menu menu) {
        int recovery = menuService.recovery(menu);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
