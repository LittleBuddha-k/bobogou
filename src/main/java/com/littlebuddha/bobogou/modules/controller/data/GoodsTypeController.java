package com.littlebuddha.bobogou.modules.controller.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.data.GoodsType;
import com.littlebuddha.bobogou.modules.service.data.GoodsTypeService;
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
@RequestMapping("/data/goodsType")
public class GoodsTypeController extends BaseController {

    @Autowired
    private GoodsTypeService goodsTypeService;

    @ModelAttribute
    public GoodsType get(@RequestParam(required = false) String id) {
        GoodsType goodsType = null;
        if (StringUtils.isNotBlank(id)) {
            goodsType = goodsTypeService.get(id);
        }
        if (goodsType == null) {
            goodsType = new GoodsType();
        }
        return goodsType;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/GoodsType/list")
    @GetMapping(value = {"/", "/list"})
    public String list(GoodsType goodsType, Model model, HttpSession session) {
        model.addAttribute("goodsType", goodsType);
        return "modules/data/goodsType";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(GoodsType goodsType) {
        PageInfo<GoodsType> page = goodsTypeService.findPage(new Page<GoodsType>(), goodsType);
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
    public String form(@PathVariable(name = "mode") String mode, GoodsType goodsType, Model model) {
        model.addAttribute("goodsType", goodsType);
        return "modules/data/goodsTypeForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(GoodsType goodsType) {
        int save = goodsTypeService.save(goodsType);
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
            List<GoodsType> list = Lists.newArrayList();
            new ExportExcel("商品分类数据", GoodsType.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<GoodsType> list = ei.getDataList(GoodsType.class);
            for (GoodsType goodsType : list) {
                try {
                    goodsTypeService.save(goodsType);
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
    public Result exportFile(GoodsType goodsType, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "商品分类" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<GoodsType> list = goodsTypeService.findList(goodsType);
            if (list != null & list.size() > 0) {
                new ExportExcel("商品分类", GoodsType.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("商品分类", GoodsType.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
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
            GoodsType goodsType = goodsTypeService.get(s);
            if (goodsType == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = goodsTypeService.deleteByLogic(goodsType);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            GoodsType goodsType = goodsTypeService.get(s);
            if (goodsType == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = goodsTypeService.deleteByPhysics(goodsType);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(GoodsType goodsType, Model model) {
        model.addAttribute("goodsType", goodsType);
        return "modules/recovery/goodsTypeRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(GoodsType goodsType, Model model) {
        model.addAttribute("goodsType", goodsType);
        PageInfo<GoodsType> page = goodsTypeService.findRecoveryPage(new Page<GoodsType>(), goodsType);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(GoodsType goodsType) {
        int recovery = goodsTypeService.recovery(goodsType);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
