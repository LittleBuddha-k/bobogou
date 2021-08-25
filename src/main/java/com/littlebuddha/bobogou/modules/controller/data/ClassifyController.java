package com.littlebuddha.bobogou.modules.controller.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.data.Classify;
import com.littlebuddha.bobogou.modules.entity.data.Goods;
import com.littlebuddha.bobogou.modules.entity.data.GoodsClassify;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsClassifyMapper;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsMapper;
import com.littlebuddha.bobogou.modules.service.data.ClassifyService;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/data/classify")
public class ClassifyController extends BaseController {

    @Autowired
    private ClassifyService classifyService;

    @Resource
    private GoodsClassifyMapper goodsClassifyMapper;

    @ModelAttribute
    public Classify get(@RequestParam(required = false) String id) {
        Classify classify = null;
        if (StringUtils.isNotBlank(id)) {
            classify = classifyService.get(new Classify(id));
        }
        if (classify == null) {
            classify = new Classify();
        }
        return classify;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/classify/list")
    @GetMapping(value = {"/", "/list"})
    public String list(Classify classify, Model model, HttpSession session) {
        model.addAttribute("classify", classify);
        return "modules/data/classify";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Classify classify) {
        PageInfo<Classify> page = classifyService.findPage(new Page<Classify>(), classify);
        return getLayUiData(page);
    }

    /**
     * 返回所有数据
     * @return
     */
    @ResponseBody
    @PostMapping("/all")
    public List<Classify> all(Classify entity) {
        List<Classify> result = new ArrayList<>();
        if(entity != null && entity.getParentId() != null && StringUtils.isNotBlank(entity.getParentId())){
            Classify parent = new Classify();
            parent.setParentId(entity.getParentId());
            result = classifyService.findList(parent);
        }
        return result;
    }

    /**
     * 根据父类信息查询子数据
     * @param classify
     * @return
     */
    @ResponseBody
    @GetMapping("/getChildren")
    public List<Classify> getChildren(Classify classify){
        List<Classify> classifys = classifyService.findList(classify);
        return classifys;
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
    public String form(@PathVariable(name = "mode") String mode, Classify classify, Model model) {
        //查询所有分类项
        List<Classify> classifyList = classifyService.findList(new Classify());
        model.addAttribute("classifyList", classifyList);
        model.addAttribute("classify", classify);
        return "modules/data/classifyForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Classify classify) {
        int save = classifyService.save(classify);
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
            List<Classify> list = Lists.newArrayList();
            new ExportExcel("商品分类数据", Classify.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<Classify> list = ei.getDataList(Classify.class);
            for (Classify classify : list) {
                try {
                    classifyService.save(classify);
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
    public Result exportFile(Classify classify, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "商品分类" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<Classify> list = classifyService.findList(classify);
            if (list != null & list.size() > 0) {
                new ExportExcel("商品分类", Classify.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("商品分类", Classify.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
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
        String[] split = ids.split(",");
        for (String s : split) {
            Classify classify = classifyService.get(s);
            if (classify == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            //删除的时候查询商品分类关联表中是否有关联的分类数据--如果有则不能删除
            GoodsClassify findByClassify = new GoodsClassify();
            findByClassify.setClassifyId(s);
            List<GoodsClassify> byClassify = goodsClassifyMapper.findByClassify(findByClassify);
            if (byClassify != null && !byClassify.isEmpty()){
                return new Result("355", "已有商品数据中使用了此项分类，分类数据不能删除");
            }else {
                int i = classifyService.deleteByLogic(classify);
            }
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Classify classify = classifyService.get(s);
            if (classify == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = classifyService.deleteByPhysics(classify);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Classify classify, Model model) {
        model.addAttribute("classify", classify);
        return "modules/recovery/classifyRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Classify classify, Model model) {
        model.addAttribute("classify", classify);
        PageInfo<Classify> page = classifyService.findRecoveryPage(new Page<Classify>(), classify);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Classify classify) {
        int recovery = classifyService.recovery(classify);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
