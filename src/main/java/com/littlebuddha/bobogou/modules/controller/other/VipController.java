package com.littlebuddha.bobogou.modules.controller.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.common.DictData;
import com.littlebuddha.bobogou.modules.entity.other.Vip;
import com.littlebuddha.bobogou.modules.service.common.DictDataService;
import com.littlebuddha.bobogou.modules.service.other.VipService;
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
@RequestMapping("/other/vip")
public class VipController extends BaseController {

    @Autowired
    private VipService vipService;

    @Autowired
    private DictDataService dictDataService;

    @ModelAttribute
    public Vip get(@RequestParam(required = false) String id) {
        Vip Vip = null;
        if (StringUtils.isNotBlank(id)) {
            Vip = vipService.get(id);
        }
        if (Vip == null) {
            Vip = new Vip();
        }
        return Vip;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/Vip/list")
    @GetMapping(value = {"/", "/list"})
    public String list(Vip vip, Model model, HttpSession session) {
        model.addAttribute("vip", vip);
        return "modules/other/vip";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Vip vip) {
        PageInfo<Vip> page = vipService.findPage(new Page<Vip>(), vip);
        return getLayUiData(page);
    }

    /**
     * 返回所有数据
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/all")
    public List<Vip> all(Vip vip) {
        List<Vip> list = vipService.findList(vip);
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
    public String form(@PathVariable(name = "mode") String mode, Vip vip, Model model) {
        model.addAttribute("vip", vip);
        return "modules/other/vipForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Vip vip) {
        int save = vipService.save(vip);
        if (save > 0) {
            return new Result("200", "保存成功");
        } else if (-1 == save){
            return new Result("320", "已有同类型数据，不可再保存第二条同类型数据");
        }else {
            return new Result("310", "未知错误！保存失败");
        }
    }

    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Vip vip = vipService.get(s);
            if (vip == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = vipService.deleteByLogic(vip);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Vip vip = vipService.get(s);
            if (vip == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = vipService.deleteByPhysics(vip);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Vip vip, Model model) {
        model.addAttribute("vip", vip);
        return "modules/recovery/vipRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Vip vip, Model model) {
        model.addAttribute("vip", vip);
        PageInfo<Vip> page = vipService.findRecoveryPage(new Page<Vip>(), vip);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Vip vip) {
        int recovery = vipService.recovery(vip);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
