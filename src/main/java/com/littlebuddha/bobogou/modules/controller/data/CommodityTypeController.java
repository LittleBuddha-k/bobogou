package com.littlebuddha.bobogou.modules.controller.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.data.CommodityType;
import com.littlebuddha.bobogou.modules.service.data.CommodityTypeService;
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
@RequestMapping("/data/commodityType")
public class CommodityTypeController extends BaseController {

    @Autowired
    private CommodityTypeService commodityTypeService;

    @ModelAttribute
    public CommodityType get(@RequestParam(required = false) String id) {
        CommodityType commodityType = null;
        if (StringUtils.isNotBlank(id)) {
            commodityType = commodityTypeService.get(id);
        }
        if (commodityType == null) {
            commodityType = new CommodityType();
        }
        return commodityType;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/CommodityType/list")
    @GetMapping(value = {"/", "/list"})
    public String list(CommodityType commodityType, Model model, HttpSession session) {
        model.addAttribute("commodityType", commodityType);
        return "modules/data/commodityType";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(CommodityType commodityType) {
        PageInfo<CommodityType> page = commodityTypeService.findPage(new Page<CommodityType>(), commodityType);
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
    public String form(@PathVariable(name = "mode") String mode, CommodityType commodityType, Model model) {
        model.addAttribute("commodityType", commodityType);
        return "modules/data/commodityTypeForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(CommodityType commodityType) {
        int save = commodityTypeService.save(commodityType);
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
            String fileName = "商品分类模板.xlsx";
            List<CommodityType> list = Lists.newArrayList();
            new ExportExcel("商品分类数据", CommodityType.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<CommodityType> list = ei.getDataList(CommodityType.class);
            for (CommodityType CommodityType : list) {
                try {
                    commodityTypeService.save(CommodityType);
                    successNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条商品分类记录。");
            }
            result.setSuccess(true);
            result.setMsg("已成功导入 " + successNum + " 条商品分类记录" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导入商品分类失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(CommodityType commodityType, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "商品分类" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<CommodityType> list = commodityTypeService.findList(commodityType);
            if (list != null & list.size() > 0) {
                new ExportExcel("商品分类", CommodityType.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("商品分类", CommodityType.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
            }
            return null;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导出商品分类记录失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String ids) {
        System.out.println("ids:" + ids);
        String[] split = ids.split(",");
        for (String s : split) {
            CommodityType CommodityType = commodityTypeService.get(s);
            if (CommodityType == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = commodityTypeService.deleteByLogic(CommodityType);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            CommodityType CommodityType = commodityTypeService.get(s);
            if (CommodityType == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = commodityTypeService.deleteByPhysics(CommodityType);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(CommodityType CommodityType, Model model) {
        model.addAttribute("CommodityType", CommodityType);
        return "modules/recovery/CommodityTypeRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(CommodityType CommodityType, Model model) {
        model.addAttribute("CommodityType", CommodityType);
        PageInfo<CommodityType> page = commodityTypeService.findRecoveryPage(new Page<CommodityType>(), CommodityType);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(CommodityType CommodityType) {
        int recovery = commodityTypeService.recovery(CommodityType);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
