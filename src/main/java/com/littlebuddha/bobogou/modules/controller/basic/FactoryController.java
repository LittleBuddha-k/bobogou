package com.littlebuddha.bobogou.modules.controller.basic;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.basic.Factory;
import com.littlebuddha.bobogou.modules.service.basic.FactoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 厂商controller
 */
@Controller
@RequestMapping("/basic/factory")
public class FactoryController extends BaseController {

    @Autowired
    private FactoryService factoryService;

    @ModelAttribute
    public Factory get(@RequestParam(required = false) String id) {
        Factory factory = null;
        if (StringUtils.isNotBlank(id)) {
            factory = factoryService.get(new Factory(id));
        }
        if (factory == null) {
            factory = new Factory();
        }
        return factory;
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
    public String list(Factory factory, Model model, HttpSession session) {
        model.addAttribute("factory", factory);
        return "modules/basic/factory";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Factory factory) {
        PageInfo<Factory> page = factoryService.findPage(new Page<Factory>(), factory);
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
    public String form(@PathVariable(name = "mode") String mode, Factory factory, Model model) {
        model.addAttribute("factory", factory);
        return "modules/basic/factoryForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Factory factory) {
        int save = factoryService.save(factory);
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
            Factory factory = factoryService.get(s);
            if (factory == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = factoryService.deleteByLogic(factory);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Factory factory = factoryService.get(s);
            if (factory == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = factoryService.deleteByPhysics(factory);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Factory factory, Model model) {
        model.addAttribute("factory", factory);
        return "modules/recovery/factoryRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Factory factory, Model model) {
        model.addAttribute("factory", factory);
        PageInfo<Factory> page = factoryService.findRecoveryPage(new Page<Factory>(), factory);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Factory factory) {
        int recovery = factoryService.recovery(factory);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
