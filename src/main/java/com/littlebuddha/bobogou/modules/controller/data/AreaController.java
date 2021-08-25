package com.littlebuddha.bobogou.modules.controller.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.data.Area;
import com.littlebuddha.bobogou.modules.entity.data.City;
import com.littlebuddha.bobogou.modules.service.data.AreaService;
import com.littlebuddha.bobogou.modules.service.data.CityService;
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
@RequestMapping("/data/area")
public class AreaController extends BaseController {

    @Autowired
    private AreaService areaService;

    @Autowired
    private CityService cityService;

    @ModelAttribute
    public Area get(@RequestParam(required = false) String id) {
        Area area = null;
        if (StringUtils.isNotBlank(id)) {
            area = areaService.get(id);
        }
        if (area == null) {
            area = new Area();
        }
        return area;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/area/list")
    @GetMapping(value = {"/", "/list"})
    public String list(Area area, Model model, HttpSession session) {
        model.addAttribute("area", area);
        return "modules/data/area";
    }

    /**
     * 返回区选择列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/area/list")
    @GetMapping(value = {"/select"})
    public String select(Area area, Model model, HttpSession session) {
        model.addAttribute("area", area);
        return "modules/common/select/area";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Area area) {
        PageInfo<Area> page = areaService.findPage(new Page<Area>(), area);
        return getLayUiData(page);
    }

    /**
     * 返回所有数据
     * @return
     */
    @ResponseBody
    @PostMapping("/all")
    public List<Area> all(Area entity) {
        if(entity != null && entity.getCity() != null && StringUtils.isNotBlank(entity.getCity().getId())){
            City city = cityService.get(entity.getCity().getId());
            if (city != null) {
                entity.setCityCode(city.getCode());
            }
        }
        List<Area> list = areaService.findList(entity);
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
    public String form(@PathVariable(name = "mode") String mode, Area area, Model model) {
        model.addAttribute("area", area);
        return "modules/data/areaForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Area area) {
        int save = areaService.save(area);
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
            String fileName = "区级管理模板.xlsx";
            List<Area> list = Lists.newArrayList();
            new ExportExcel("区级管理数据", Area.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<Area> list = ei.getDataList(Area.class);
            for (Area area : list) {
                try {
                    areaService.save(area);
                    successNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条区级管理记录。");
            }
            result.setSuccess(true);
            result.setMsg("已成功导入 " + successNum + " 条区级管理记录" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导入区级管理失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(Area area, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "区级管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<Area> list = areaService.findList(area);
            if (list != null & list.size() > 0) {
                new ExportExcel("区级管理", Area.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("区级管理", Area.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
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
            Area area = areaService.get(s);
            if (area == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = areaService.deleteByLogic(area);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Area area = areaService.get(s);
            if (area == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = areaService.deleteByPhysics(area);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Area area, Model model) {
        model.addAttribute("area", area);
        return "modules/recovery/areaRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Area area, Model model) {
        model.addAttribute("area", area);
        PageInfo<Area> page = areaService.findRecoveryPage(new Page<Area>(), area);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Area area) {
        int recovery = areaService.recovery(area);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
