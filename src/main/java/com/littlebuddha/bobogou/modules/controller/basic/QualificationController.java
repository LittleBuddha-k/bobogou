package com.littlebuddha.bobogou.modules.controller.basic;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.basic.Qualification;
import com.littlebuddha.bobogou.modules.service.basic.QualificationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 公司资质controller
 */
@Controller
@RequestMapping("/basic/qualification")
public class QualificationController extends BaseController {

    @Autowired
    private QualificationService qualificationService;

    @ModelAttribute
    public Qualification get(@RequestParam(required = false) String id) {
        Qualification qualification = null;
        if (StringUtils.isNotBlank(id)) {
            qualification = qualificationService.get(new Qualification(id));
        }
        if (qualification == null) {
            qualification = new Qualification();
        }
        return qualification;
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
    public String list(Qualification qualification, Model model, HttpSession session) {
        model.addAttribute("qualification", qualification);
        return "modules/basic/qualification";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Qualification qualification) {
        PageInfo<Qualification> page = qualificationService.findPage(new Page<Qualification>(), qualification);
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
    public String form(@PathVariable(name = "mode") String mode, Qualification qualification, Model model) {
        model.addAttribute("qualification", qualification);
        return "modules/basic/qualificationForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Qualification qualification) {
        int save = qualificationService.save(qualification);
        if (save > 0) {
            return new Result("200", "保存成功");
        } else {
            return new Result("310", "未知错误！保存失败");
        }
    }

    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Qualification qualification = qualificationService.get(s);
            if (qualification == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = qualificationService.deleteByLogic(qualification);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Qualification qualification = qualificationService.get(s);
            if (qualification == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = qualificationService.deleteByPhysics(qualification);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Qualification qualification, Model model) {
        model.addAttribute("qualification", qualification);
        return "modules/recovery/qualificationRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Qualification qualification, Model model) {
        model.addAttribute("qualification", qualification);
        PageInfo<Qualification> page = qualificationService.findRecoveryPage(new Page<Qualification>(), qualification);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Qualification qualification) {
        int recovery = qualificationService.recovery(qualification);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
