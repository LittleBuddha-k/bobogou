package com.littlebuddha.bobogou.modules.controller.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.basic.Factory;
import com.littlebuddha.bobogou.modules.entity.common.DictData;
import com.littlebuddha.bobogou.modules.entity.data.FactorySterilize;
import com.littlebuddha.bobogou.modules.entity.data.FactorySterilize;
import com.littlebuddha.bobogou.modules.service.basic.FactoryService;
import com.littlebuddha.bobogou.modules.service.common.DictDataService;
import com.littlebuddha.bobogou.modules.service.data.FactorySterilizeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 消毒产品controller
 */
@Controller
@RequestMapping("/data/factorySterilize")
public class FactorySterilizeController extends BaseController {

    @Autowired
    private FactorySterilizeService factorySterilizeService;

    @Autowired
    private DictDataService dictDataService;

    @Autowired
    private FactoryService factoryService;

    @ModelAttribute
    public FactorySterilize get(@RequestParam(required = false) String id) {
        FactorySterilize factorySterilize = null;
        if (StringUtils.isNotBlank(id)) {
            factorySterilize = factorySterilizeService.get(new FactorySterilize(id));
        }
        if (factorySterilize == null) {
            factorySterilize = new FactorySterilize();
        }
        return factorySterilize;
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
    public String list(FactorySterilize factorySterilize, Model model, HttpSession session) {
        model.addAttribute("factorySterilize", factorySterilize);
        return "modules/data/factorySterilize";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(FactorySterilize factorySterilize) {
        PageInfo<FactorySterilize> page = factorySterilizeService.findPage(new Page<FactorySterilize>(), factorySterilize);
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
    public String form(@PathVariable(name = "mode") String mode, FactorySterilize factorySterilize, Model model) {
        DictData select = new DictData();
        select.setType("data_factory_sterilize_product_type");
        List<DictData> productTypeList = dictDataService.findList(select);
        model.addAttribute("productTypeList", productTypeList);
        //查询所有厂商列表
        List<Factory> factoryList = factoryService.findList(new Factory());
        model.addAttribute("factoryList", factoryList);
        model.addAttribute("factorySterilize", factorySterilize);
        return "modules/data/factorySterilizeForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(FactorySterilize factorySterilize) {
        int save = factorySterilizeService.save(factorySterilize);
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
            FactorySterilize factorySterilize = factorySterilizeService.get(s);
            if (factorySterilize == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = factorySterilizeService.deleteByLogic(factorySterilize);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            FactorySterilize factorySterilize = factorySterilizeService.get(s);
            if (factorySterilize == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = factorySterilizeService.deleteByPhysics(factorySterilize);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(FactorySterilize factorySterilize, Model model) {
        model.addAttribute("factorySterilize", factorySterilize);
        return "modules/recovery/factorySterilizeRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(FactorySterilize factorySterilize, Model model) {
        model.addAttribute("factorySterilize", factorySterilize);
        PageInfo<FactorySterilize> page = factorySterilizeService.findRecoveryPage(new Page<FactorySterilize>(), factorySterilize);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(FactorySterilize factorySterilize) {
        int recovery = factorySterilizeService.recovery(factorySterilize);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
