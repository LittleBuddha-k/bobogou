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
import com.littlebuddha.bobogou.modules.entity.data.GoodsTag;
import com.littlebuddha.bobogou.modules.entity.data.GoodsType;
import com.littlebuddha.bobogou.modules.entity.data.Medicine;
import com.littlebuddha.bobogou.modules.service.data.GoodsBrandService;
import com.littlebuddha.bobogou.modules.service.data.GoodsTagService;
import com.littlebuddha.bobogou.modules.service.data.GoodsTypeService;
import com.littlebuddha.bobogou.modules.service.data.MedicineService;
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
@RequestMapping("/data/medicine")
public class MedicineController extends BaseController {

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private GoodsTagService goodsTagService;

    @Autowired
    private GoodsBrandService goodsBrandService;

    @Autowired
    private GoodsTypeService goodsTypeService;

    @ModelAttribute
    public Medicine get(@RequestParam(required = false) String id) {
        Medicine medicine = null;
        if (StringUtils.isNotBlank(id)) {
            medicine = medicineService.get(id);
        }
        if (medicine == null) {
            medicine = new Medicine();
        }
        return medicine;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/Medicine/list")
    @GetMapping(value = {"/", "/list"})
    public String list(Medicine medicine, Model model, HttpSession session) {
        model.addAttribute("medicine", medicine);
        return "modules/data/medicine";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Medicine medicine) {
        PageInfo<Medicine> page = medicineService.findPage(new Page<Medicine>(), medicine);
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
    public String form(@PathVariable(name = "mode") String mode, Medicine medicine, Model model) {
        //查询所有标签
        List<GoodsTag> commodityTagList = goodsTagService.findList(new GoodsTag());
        //标签数据
        model.addAttribute("commodityTagList", commodityTagList);
        //查询所有品牌分类
        List<GoodsBrand> commodityBrandList = goodsBrandService.findList(new GoodsBrand());
        //品牌分类数据
        model.addAttribute("commodityBrandList", commodityBrandList);
        //查询商品分类数据：分一级、二级、三级
        List<GoodsType> goodsTypeLevelOne = goodsTypeService.findList(new GoodsType(0));//查询一级商品分类
        List<GoodsType> goodsTypeLevelTwo = goodsTypeService.findList(new GoodsType(1));//查询二级商品分类
        List<GoodsType> goodsTypeLevelThree = goodsTypeService.findList(new GoodsType(2));//查询三级商品分类
        model.addAttribute("goodsTypeLevelOne", goodsTypeLevelOne);
        model.addAttribute("goodsTypeLevelTwo", goodsTypeLevelTwo);
        model.addAttribute("goodsTypeLevelThree", goodsTypeLevelThree);
        model.addAttribute("medicine", medicine);
        return "modules/data/medicineForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Medicine medicine) {
        int save = medicineService.save(medicine);
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
            String fileName = "药品模板.xlsx";
            List<Medicine> list = Lists.newArrayList();
            new ExportExcel("药品数据", Medicine.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<Medicine> list = ei.getDataList(Medicine.class);
            for (Medicine mayApplyCost : list) {
                try {
                    medicineService.save(mayApplyCost);
                    successNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条药品记录。");
            }
            result.setSuccess(true);
            result.setMsg("已成功导入 " + successNum + " 条药品记录" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导入药品失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(Medicine medicine, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "药品" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<Medicine> list = medicineService.findList(medicine);
            if (list != null & list.size() > 0) {
                new ExportExcel("药品", Medicine.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("药品", Medicine.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
            }
            return null;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导出药品记录失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String ids) {
        System.out.println("ids:" + ids);
        String[] split = ids.split(",");
        for (String s : split) {
            Medicine medicine = medicineService.get(s);
            if (medicine == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = medicineService.deleteByLogic(medicine);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Medicine medicine = medicineService.get(s);
            if (medicine == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = medicineService.deleteByPhysics(medicine);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Medicine medicine, Model model) {
        model.addAttribute("medicine", medicine);
        return "modules/recovery/medicineRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Medicine medicine, Model model) {
        model.addAttribute("medicine", medicine);
        PageInfo<Medicine> page = medicineService.findRecoveryPage(new Page<Medicine>(), medicine);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Medicine medicine) {
        int recovery = medicineService.recovery(medicine);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
