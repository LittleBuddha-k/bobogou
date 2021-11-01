package com.littlebuddha.bobogou.modules.controller.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.data.Province;
import com.littlebuddha.bobogou.modules.service.data.ProvinceService;
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
@RequestMapping("/data/province")
public class ProvinceController extends BaseController {

    @Autowired
    private ProvinceService provinceService;

    @ModelAttribute
    public Province get(@RequestParam(required = false) String id) {
        Province province = null;
        if (StringUtils.isNotBlank(id)) {
            province = provinceService.get(id);
        }
        if (province == null) {
            province = new Province();
        }
        return province;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/Province/list")
    @GetMapping(value = {"/", "/list"})
    public String list(Province province, Model model, HttpSession session) {
        model.addAttribute("province", province);
        return "modules/data/province";
    }

    /**
     * 返回省选择列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/area/list")
    @GetMapping(value = {"/select"})
    public String select(Province province, Model model, HttpSession session) {
        model.addAttribute("province", province);
        return "modules/common/select/province";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Province province) {
        PageInfo<Province> page = provinceService.findPage(new Page<Province>(), province);
        return getLayUiData(page);
    }

    /**
     * 返回所有数据
     * @return
     */
    @ResponseBody
    @GetMapping("/noPage")
    public TreeResult noPage(Province province) {
        List<Province> list = provinceService.findList(province);
        if (list != null && !list.isEmpty()) {
            TreeResult treeResult = new TreeResult(0,"",list,list.size());
            return treeResult;
        }else {
            TreeResult treeResult = new TreeResult(0,"无数据");
            return treeResult;
        }
    }

    /**
     * 返回所有数据
     * @return
     */
    @ResponseBody
    @GetMapping("/all")
    public List<Province> all(Province province) {
        List<Province> list = provinceService.findList(province);
        return list;
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
    public String form(@PathVariable(name = "mode") String mode, Province province, Model model) {
        model.addAttribute("province", province);
        return "modules/data/provinceForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Province province) {
        int save = provinceService.save(province);
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
            String fileName = "省级管理模板.xlsx";
            List<Province> list = Lists.newArrayList();
            new ExportExcel("省级管理数据", Province.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<Province> list = ei.getDataList(Province.class);
            for (Province province : list) {
                try {
                    provinceService.save(province);
                    successNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条省级管理记录。");
            }
            result.setSuccess(true);
            result.setMsg("已成功导入 " + successNum + " 条省级管理记录" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导入省级管理失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(Province province, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "省级管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<Province> list = provinceService.findList(province);
            if (list != null & list.size() > 0) {
                new ExportExcel("省级管理", Province.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("省级管理", Province.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
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
            Province province = provinceService.get(s);
            if (province == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = provinceService.deleteByLogic(province);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Province province = provinceService.get(s);
            if (province == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = provinceService.deleteByPhysics(province);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Province Province, Model model) {
        model.addAttribute("Province", Province);
        return "modules/recovery/ProvinceRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Province province, Model model) {
        model.addAttribute("province", province);
        PageInfo<Province> page = provinceService.findRecoveryPage(new Page<Province>(), province);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Province province) {
        int recovery = provinceService.recovery(province);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
