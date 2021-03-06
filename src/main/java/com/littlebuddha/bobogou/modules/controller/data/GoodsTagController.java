package com.littlebuddha.bobogou.modules.controller.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.data.GoodsTag;
import com.littlebuddha.bobogou.modules.service.data.GoodsTagService;
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
@RequestMapping("/data/goodsTag")
public class GoodsTagController extends BaseController {

    @Autowired
    private GoodsTagService goodsTagService;

    @ModelAttribute
    public GoodsTag get(@RequestParam(required = false) String id) {
        GoodsTag goodsTag = null;
        if (StringUtils.isNotBlank(id)) {
            goodsTag = goodsTagService.get(id);
        }
        if (goodsTag == null) {
            goodsTag = new GoodsTag();
        }
        return goodsTag;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/GoodsTag/list")
    @GetMapping(value = {"/", "/list"})
    public String list(GoodsTag goodsTag, Model model, HttpSession session) {
        model.addAttribute("goodsTag", goodsTag);
        return "modules/data/goodsTag";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(GoodsTag goodsTag) {
        PageInfo<GoodsTag> page = goodsTagService.findPage(new Page<GoodsTag>(), goodsTag);
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
    public String form(@PathVariable(name = "mode") String mode, GoodsTag goodsTag, Model model) {
        model.addAttribute("goodsTag", goodsTag);
        return "modules/data/goodsTagForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(GoodsTag goodsTag) {
        int save = goodsTagService.save(goodsTag);
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
            String fileName = "商品标签模板.xlsx";
            List<GoodsTag> list = Lists.newArrayList();
            new ExportExcel("商品标签数据", GoodsTag.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<GoodsTag> list = ei.getDataList(GoodsTag.class);
            for (GoodsTag goodsTag : list) {
                try {
                    goodsTagService.save(goodsTag);
                    successNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条商品标签记录。");
            }
            result.setSuccess(true);
            result.setMsg("已成功导入 " + successNum + " 条商品标签记录" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导入商品标签失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(GoodsTag goodsTag, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "商品标签" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<GoodsTag> list = goodsTagService.findList(goodsTag);
            if (list != null & list.size() > 0) {
                new ExportExcel("商品标签", GoodsTag.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("商品标签", GoodsTag.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
            }
            return null;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导出商品标签记录失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String ids) {
        System.out.println("ids:" + ids);
        String[] split = ids.split(",");
        for (String s : split) {
            GoodsTag goodsTag = goodsTagService.get(s);
            if (goodsTag == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = goodsTagService.deleteByLogic(goodsTag);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            GoodsTag goodsTag = goodsTagService.get(s);
            if (goodsTag == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = goodsTagService.deleteByPhysics(goodsTag);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(GoodsTag goodsTag, Model model) {
        model.addAttribute("goodsTag", goodsTag);
        return "modules/recovery/goodsTagRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(GoodsTag goodsTag, Model model) {
        model.addAttribute("goodsTag", goodsTag);
        PageInfo<GoodsTag> page = goodsTagService.findRecoveryPage(new Page<GoodsTag>(), goodsTag);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(GoodsTag goodsTag) {
        int recovery = goodsTagService.recovery(goodsTag);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
