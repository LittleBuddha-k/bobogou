package com.littlebuddha.bobogou.modules.controller.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.data.GoodsBrand;
import com.littlebuddha.bobogou.modules.service.data.GoodsBrandService;
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
@RequestMapping("/data/goodsBrand")
public class GoodsBrandController extends BaseController {

    @Autowired
    private GoodsBrandService goodsBrandService;

    @ModelAttribute
    public GoodsBrand get(@RequestParam(required = false) String id) {
        GoodsBrand goodsBrand = null;
        if (StringUtils.isNotBlank(id)) {
            goodsBrand = goodsBrandService.get(id);
        }
        if (goodsBrand == null) {
            goodsBrand = new GoodsBrand();
        }
        return goodsBrand;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/goodsBrand/list")
    @GetMapping(value = {"/", "/list"})
    public String list(GoodsBrand goodsBrand, Model model, HttpSession session) {
        model.addAttribute("goodsBrand", goodsBrand);
        return "modules/data/goodsBrand";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(GoodsBrand goodsBrand) {
        PageInfo<GoodsBrand> page = goodsBrandService.findPage(new Page<GoodsBrand>(), goodsBrand);
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
    public String form(@PathVariable(name = "mode") String mode, GoodsBrand goodsBrand, Model model) {
        //查询所有品牌
        List<GoodsBrand> goodsBrandList = goodsBrandService.findList(new GoodsBrand());
        model.addAttribute("goodsBrandList", goodsBrandList);
        model.addAttribute("goodsBrand", goodsBrand);
        return "modules/data/goodsBrandForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(GoodsBrand goodsBrand) {
        int save = goodsBrandService.save(goodsBrand);
        if (save > 0) {
            return new Result("200", "保存成功");
        } else {
            return new Result("310", "未知错误！保存失败");
        }
    }

    @ResponseBody
    @GetMapping("/importTemplate")
    public Result importTemplate(HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "商品品牌规格模板.xlsx";
            List<GoodsBrand> list = Lists.newArrayList();
            new ExportExcel("商品品牌规格数据", GoodsBrand.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            result.setSuccess(true);
            result.setMsg("导入模板下载失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/importFile")
    public Result importFile(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
        Result result = new Result();
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<GoodsBrand> list = ei.getDataList(GoodsBrand.class);
            for (GoodsBrand goodsBrand : list) {
                try {
                    goodsBrandService.save(goodsBrand);
                    successNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条商品品牌规格记录。");
            }
            result.setSuccess(true);
            result.setMsg("已成功导入 " + successNum + " 条商品品牌规格记录" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导入商品品牌规格失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(GoodsBrand goodsBrand, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "商品品牌规格" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<GoodsBrand> list = goodsBrandService.findList(goodsBrand);
            if (list != null & list.size() > 0) {
                new ExportExcel("商品品牌规格", GoodsBrand.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("商品品牌规格", GoodsBrand.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
            }
            return null;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导出商品品牌规格记录失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String ids) {
        System.out.println("ids:" + ids);
        String[] split = ids.split(",");
        for (String s : split) {
            GoodsBrand goodsBrand = goodsBrandService.get(s);
            if (goodsBrand == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = goodsBrandService.deleteByLogic(goodsBrand);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            GoodsBrand goodsBrand = goodsBrandService.get(s);
            if (goodsBrand == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = goodsBrandService.deleteByPhysics(goodsBrand);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(GoodsBrand goodsBrand, Model model) {
        model.addAttribute("goodsBrand", goodsBrand);
        return "modules/recovery/goodsBrandRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(GoodsBrand goodsBrand, Model model) {
        model.addAttribute("goodsBrand", goodsBrand);
        PageInfo<GoodsBrand> page = goodsBrandService.findRecoveryPage(new Page<GoodsBrand>(), goodsBrand);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(GoodsBrand goodsBrand) {
        int recovery = goodsBrandService.recovery(goodsBrand);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
