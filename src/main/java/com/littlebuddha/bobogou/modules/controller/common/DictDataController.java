package com.littlebuddha.bobogou.modules.controller.common;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.common.DictData;
import com.littlebuddha.bobogou.modules.service.common.DictDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 合同controller
 */
@Controller
@RequestMapping("/basic/dictData")
public class DictDataController extends BaseController {

    @Autowired
    private DictDataService dictDataService;

    @ModelAttribute
    public DictData get(@RequestParam(required = false) String id) {
        DictData dictData = null;
        if (StringUtils.isNotBlank(id)) {
            dictData = dictDataService.get(new DictData(id));
        }
        if (dictData == null) {
            dictData = new DictData();
        }
        return dictData;
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
    public String list(DictData dictData, Model model, HttpSession session) {
        model.addAttribute("dictData", dictData);
        return "modules/basic/dictData";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(DictData dictData) {
        PageInfo<DictData> page = dictDataService.findPage(new Page<DictData>(), dictData);
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
    public String form(@PathVariable(name = "mode") String mode, DictData dictData, Model model) {
        model.addAttribute("dictData", dictData);
        return "modules/basic/dictDataForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(DictData dictData) {
        int save = dictDataService.save(dictData);
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
            DictData dictData = dictDataService.get(s);
            if (dictData == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = dictDataService.deleteByLogic(dictData);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            DictData dictData = dictDataService.get(s);
            if (dictData == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = dictDataService.deleteByPhysics(dictData);
        }
        return new Result("200", "数据清除成功");
    }
}
