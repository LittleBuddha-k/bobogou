package com.littlebuddha.bobogou.modules.controller.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.data.CommodityBrand;
import com.littlebuddha.bobogou.modules.service.data.CommodityBrandService;
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
@RequestMapping("/data/commodityBrand")
public class CommodityBrandController extends BaseController {

    @Autowired
    private CommodityBrandService commodityBrandService;

    @ModelAttribute
    public CommodityBrand get(@RequestParam(required = false) String id) {
        CommodityBrand commodityBrand = null;
        if (StringUtils.isNotBlank(id)) {
            commodityBrand = commodityBrandService.get(id);
        }
        if (commodityBrand == null) {
            commodityBrand = new CommodityBrand();
        }
        return commodityBrand;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/CommodityBrand/list")
    @GetMapping(value = {"/", "/list"})
    public String list(CommodityBrand commodityBrand, Model model, HttpSession session) {
        model.addAttribute("commodityBrand", commodityBrand);
        return "modules/data/commodityBrand";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(CommodityBrand commodityBrand) {
        PageInfo<CommodityBrand> page = commodityBrandService.findPage(new Page<CommodityBrand>(), commodityBrand);
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
    public String form(@PathVariable(name = "mode") String mode, CommodityBrand commodityBrand, Model model) {
        model.addAttribute("commodityBrand", commodityBrand);
        return "modules/data/commodityBrandForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(CommodityBrand commodityBrand) {
        int save = commodityBrandService.save(commodityBrand);
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
            List<CommodityBrand> list = Lists.newArrayList();
            new ExportExcel("商品品牌规格数据", CommodityBrand.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<CommodityBrand> list = ei.getDataList(CommodityBrand.class);
            for (CommodityBrand CommodityBrand : list) {
                try {
                    commodityBrandService.save(CommodityBrand);
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
    public Result exportFile(CommodityBrand commodityBrand, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "商品品牌规格" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<CommodityBrand> list = commodityBrandService.findList(commodityBrand);
            if (list != null & list.size() > 0) {
                new ExportExcel("商品品牌规格", CommodityBrand.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("商品品牌规格", CommodityBrand.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
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
            CommodityBrand CommodityBrand = commodityBrandService.get(s);
            if (CommodityBrand == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = commodityBrandService.deleteByLogic(CommodityBrand);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            CommodityBrand CommodityBrand = commodityBrandService.get(s);
            if (CommodityBrand == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = commodityBrandService.deleteByPhysics(CommodityBrand);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(CommodityBrand CommodityBrand, Model model) {
        model.addAttribute("CommodityBrand", CommodityBrand);
        return "modules/recovery/CommodityBrandRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(CommodityBrand CommodityBrand, Model model) {
        model.addAttribute("CommodityBrand", CommodityBrand);
        PageInfo<CommodityBrand> page = commodityBrandService.findRecoveryPage(new Page<CommodityBrand>(), CommodityBrand);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(CommodityBrand CommodityBrand) {
        int recovery = commodityBrandService.recovery(CommodityBrand);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
