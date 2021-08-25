package com.littlebuddha.bobogou.modules.controller.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.data.City;
import com.littlebuddha.bobogou.modules.entity.data.Province;
import com.littlebuddha.bobogou.modules.service.data.CityService;
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
@RequestMapping("/data/city")
public class CityController extends BaseController {

    @Autowired
    private CityService cityService;

    @Autowired
    private ProvinceService provinceService;

    @ModelAttribute
    public City get(@RequestParam(required = false) String id) {
        City city = null;
        if (StringUtils.isNotBlank(id)) {
            city = cityService.get(id);
        }
        if (city == null) {
            city = new City();
        }
        return city;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/city/list")
    @GetMapping(value = {"/", "/list"})
    public String list(City city, Model model, HttpSession session) {
        model.addAttribute("city", city);
        return "modules/data/city";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(City city) {
        PageInfo<City> page = cityService.findPage(new Page<City>(), city);
        return getLayUiData(page);
    }

    /**
     * 返回所有数据
     * @return
     */
    @ResponseBody
    @PostMapping("/all")
    public List<City> all(City city) {
        if(city != null && StringUtils.isNotBlank(city.getProvince().getId())){
            Province province = provinceService.get(city.getProvince().getId());
            if (province != null) {
                city.setProvinceCode(province.getCode());
            }
        }
        List<City> list = cityService.findList(city);
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
    public String form(@PathVariable(name = "mode") String mode, City city, Model model) {
        if(city != null && city.getProvince() != null && StringUtils.isNotBlank(city.getProvince().getId())){
            Province province = provinceService.get(city.getProvince().getId());
            city.setProvince(province);
        }
        model.addAttribute("city", city);
        return "modules/data/cityForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(City city) {
        int save = cityService.save(city);
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
            String fileName = "市级管理模板.xlsx";
            List<City> list = Lists.newArrayList();
            new ExportExcel("市级管理数据", City.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<City> list = ei.getDataList(City.class);
            for (City city : list) {
                try {
                    cityService.save(city);
                    successNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条市级管理记录。");
            }
            result.setSuccess(true);
            result.setMsg("已成功导入 " + successNum + " 条市级管理记录" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导入市级管理失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(City city, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "市级管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<City> list = cityService.findList(city);
            if (list != null & list.size() > 0) {
                new ExportExcel("市级管理", City.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("市级管理", City.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
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
            City city = cityService.get(s);
            if (city == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = cityService.deleteByLogic(city);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            City city = cityService.get(s);
            if (city == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = cityService.deleteByPhysics(city);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(City city, Model model) {
        model.addAttribute("city", city);
        return "modules/recovery/cityRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(City city, Model model) {
        model.addAttribute("city", city);
        PageInfo<City> page = cityService.findRecoveryPage(new Page<City>(), city);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(City city) {
        int recovery = cityService.recovery(city);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
