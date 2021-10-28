package com.littlebuddha.bobogou.modules.controller.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.data.ChargeBack;
import com.littlebuddha.bobogou.modules.service.data.ChargeBackService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 退单控制层
 */
@Controller
@RequestMapping("/data/chargeBack")
public class ChargeBackController extends BaseController {

    @Autowired
    private ChargeBackService chargeBackService;

    @ModelAttribute
    public ChargeBack get(@RequestParam(required = false) String id) {
        ChargeBack chargeBack = null;
        if (StringUtils.isNotBlank(id)) {
            chargeBack = chargeBackService.get(new ChargeBack(id));
        }
        if (chargeBack == null) {
            chargeBack = new ChargeBack();
        }
        return chargeBack;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/ChargeBack/list")
    @GetMapping(value = {"/", "/list"})
    public String list(ChargeBack chargeBack, Model model, HttpSession session) {
        model.addAttribute("chargeBack", chargeBack);
        return "modules/data/chargeBack";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(ChargeBack chargeBack) {
        PageInfo<ChargeBack> page = chargeBackService.findPage(new Page<ChargeBack>(), chargeBack);
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
    public String form(@PathVariable(name = "mode") String mode, ChargeBack ChargeBack, Model model) {
        model.addAttribute("ChargeBack", ChargeBack);
        return "modules/data/ChargeBackForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(ChargeBack chargeBack) {
        int save = chargeBackService.save(chargeBack);
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
            ChargeBack ChargeBack = chargeBackService.get(s);
            if (ChargeBack == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = chargeBackService.deleteByLogic(ChargeBack);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            ChargeBack ChargeBack = chargeBackService.get(s);
            if (ChargeBack == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = chargeBackService.deleteByPhysics(ChargeBack);
        }
        return new Result("200", "数据清除成功");
    }
}
