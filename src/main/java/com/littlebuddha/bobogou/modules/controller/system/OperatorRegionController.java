package com.littlebuddha.bobogou.modules.controller.system;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRegion;
import com.littlebuddha.bobogou.modules.service.system.OperatorRegionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/system/operatorRegion")
public class OperatorRegionController extends BaseController {

    @Autowired
    private OperatorRegionService operatorRegionService;

    @ModelAttribute
    public OperatorRegion get(@RequestParam(required = false) String id) {
        OperatorRegion operatorRegion = null;
        if (StringUtils.isNotBlank(id)) {
            operatorRegion = operatorRegionService.get(new OperatorRegion(id));
        }
        if (operatorRegion == null) {
            operatorRegion = new OperatorRegion();
        }
        return operatorRegion;
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
    public String list(OperatorRegion operatorRegion, Model model, HttpSession session) {
        model.addAttribute("operatorRegion", operatorRegion);
        return "modules/data/operatorRegion";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(OperatorRegion operatorRegion) {
        PageInfo<OperatorRegion> page = operatorRegionService.findPage(new Page<OperatorRegion>(), operatorRegion);
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
    public String form(@PathVariable(name = "mode") String mode, OperatorRegion operatorRegion, Model model) {
        model.addAttribute("operatorRegion", operatorRegion);
        return "modules/data/operatorRegionForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(OperatorRegion operatorRegion) {
        int save = operatorRegionService.save(operatorRegion);
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
            OperatorRegion operatorRegion = operatorRegionService.get(s);
            if (operatorRegion == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = operatorRegionService.deleteByLogic(operatorRegion);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            OperatorRegion operatorRegion = operatorRegionService.get(s);
            if (operatorRegion == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = operatorRegionService.deleteByPhysics(operatorRegion);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(OperatorRegion operatorRegion, Model model) {
        model.addAttribute("operatorRegion", operatorRegion);
        return "modules/recovery/operatorRegionRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(OperatorRegion operatorRegion, Model model) {
        model.addAttribute("operatorRegion", operatorRegion);
        PageInfo<OperatorRegion> page = operatorRegionService.findRecoveryPage(new Page<OperatorRegion>(), operatorRegion);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(OperatorRegion operatorRegion) {
        int recovery = operatorRegionService.recovery(operatorRegion);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
