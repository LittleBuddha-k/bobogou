package com.littlebuddha.bobogou.modules.controller.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.basic.Factory;
import com.littlebuddha.bobogou.modules.entity.data.Goods;
import com.littlebuddha.bobogou.modules.entity.data.GoodsNorm;
import com.littlebuddha.bobogou.modules.service.basic.FactoryService;
import com.littlebuddha.bobogou.modules.service.data.GoodsNormService;
import com.littlebuddha.bobogou.modules.service.data.GoodsNormService;
import com.littlebuddha.bobogou.modules.service.data.GoodsService;
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

/**
 * 商品品牌规格controller
 */
@Controller
@RequestMapping("/data/goodsNorm")
public class GoodsNormController extends BaseController {

    @Autowired
    private GoodsNormService goodsNormService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private FactoryService factoryService;

    @ModelAttribute
    public GoodsNorm get(@RequestParam(required = false) String id) {
        GoodsNorm goodsNorm = null;
        if (StringUtils.isNotBlank(id)) {
            goodsNorm = goodsNormService.get(new GoodsNorm(id));
            if (goodsNorm != null && StringUtils.isNotBlank(goodsNorm.getFactoryId().toString())){
                Factory factory = factoryService.get(new Factory(goodsNorm.getFactoryId().toString()));
                goodsNorm.setFactory(factory);
            }
        }
        if (goodsNorm == null) {
            goodsNorm = new GoodsNorm();
        }
        return goodsNorm;
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
    public String list(GoodsNorm goodsNorm, Model model, HttpSession session) {
        model.addAttribute("goodsNorm", goodsNorm);
        return "modules/data/goodsNorm";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(GoodsNorm goodsNorm) {
        PageInfo<GoodsNorm> page = goodsNormService.findPage(new Page<GoodsNorm>(), goodsNorm);
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
    public String form(@PathVariable(name = "mode") String mode, GoodsNorm goodsNorm, Model model) {
        List<Factory> factoryList = factoryService.findList(new Factory());
        model.addAttribute("factoryList", factoryList);
        model.addAttribute("goodsNorm", goodsNorm);
        return "modules/data/goodsNormForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(GoodsNorm goodsNorm) {
        int save = goodsNormService.save(goodsNorm);
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
            GoodsNorm goodsNorm = goodsNormService.get(s);
            if (goodsNorm == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = goodsNormService.deleteByLogic(goodsNorm);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            GoodsNorm goodsNorm = goodsNormService.get(s);
            if (goodsNorm == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = goodsNormService.deleteByPhysics(goodsNorm);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(GoodsNorm goodsNorm, Model model) {
        model.addAttribute("goodsNorm", goodsNorm);
        return "modules/recovery/goodsNormRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(GoodsNorm goodsNorm, Model model) {
        model.addAttribute("goodsNorm", goodsNorm);
        PageInfo<GoodsNorm> page = goodsNormService.findRecoveryPage(new Page<GoodsNorm>(), goodsNorm);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(GoodsNorm goodsNorm) {
        int recovery = goodsNormService.recovery(goodsNorm);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
