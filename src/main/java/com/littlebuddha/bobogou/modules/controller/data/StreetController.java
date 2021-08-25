package com.littlebuddha.bobogou.modules.controller.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.data.Area;
import com.littlebuddha.bobogou.modules.entity.data.Street;
import com.littlebuddha.bobogou.modules.service.data.AreaService;
import com.littlebuddha.bobogou.modules.service.data.StreetService;
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
@RequestMapping("/data/street")
public class StreetController extends BaseController {

    @Autowired
    private StreetService streetService;

    @Autowired
    private AreaService areaService;

    @ModelAttribute
    public Street get(@RequestParam(required = false) String id) {
        Street street = null;
        if (StringUtils.isNotBlank(id)) {
            street = streetService.get(id);
        }
        if (street == null) {
            street = new Street();
        }
        return street;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/street/list")
    @GetMapping(value = {"/", "/list"})
    public String list(Street street, Model model, HttpSession session) {
        model.addAttribute("street", street);
        return "modules/data/street";
    }

    /**
     * 返回街道选择列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/area/list")
    @GetMapping(value = {"/select"})
    public String select(Street street, Model model, HttpSession session) {
        if (street != null && street.getArea() != null && StringUtils.isNotBlank(street.getArea().getId())){
            Area area = areaService.get(new Area(street.getArea().getId()));
            street.setArea(area);
        }
        model.addAttribute("street", street);
        return "modules/common/select/street";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Street street) {
        PageInfo<Street> page = streetService.findPage(new Page<Street>(), street);
        return getLayUiData(page);
    }

    /**
     * 返回不分页数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/noPage")
    public TreeResult noPage(Street street) {
        if(street != null && street.getArea() != null && StringUtils.isNotBlank(street.getArea().getId())){
            Area area = areaService.get(street.getArea().getId());
            if(area != null) {
                street.setAreaCode(area.getCode());
            }
        }
        List<Street> list = streetService.findList(street);
        TreeResult result = null;
        if (list == null || list.size() <= 0){
            result = new TreeResult(0,"无数据");
        }else if(list != null || list.size() > 0){
            result = new TreeResult(0,"查询成功",list, list.size());
        }
        return result;
    }

    /**
     * 返回所有数据
     * @return
     */
    @ResponseBody
    @PostMapping("/all")
    public List<Street> all(Street street) {
        if(street != null && street.getArea() != null && StringUtils.isNotBlank(street.getArea().getId())){
            Area area = areaService.get(street.getArea().getId());
            if (area != null) {
                street.setAreaCode(area.getCode());
            }
        }
        List<Street> list = streetService.findList(street);
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
    public String form(@PathVariable(name = "mode") String mode, Street street, Model model) {
        model.addAttribute("street", street);
        return "modules/data/streetForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Street street) {
        int save = streetService.save(street);
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
            String fileName = "街道管理模板.xlsx";
            List<Street> list = Lists.newArrayList();
            new ExportExcel("街道管理数据", Street.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<Street> list = ei.getDataList(Street.class);
            for (Street street : list) {
                try {
                    streetService.save(street);
                    successNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条街道管理记录。");
            }
            result.setSuccess(true);
            result.setMsg("已成功导入 " + successNum + " 条街道管理记录" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导入街道管理失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(Street street, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "街道管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<Street> list = streetService.findList(street);
            if (list != null & list.size() > 0) {
                new ExportExcel("街道管理", Street.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("街道管理", Street.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
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
            Street street = streetService.get(s);
            if (street == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = streetService.deleteByLogic(street);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Street street = streetService.get(s);
            if (street == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = streetService.deleteByPhysics(street);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Street street, Model model) {
        model.addAttribute("street", street);
        return "modules/recovery/streetRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Street street, Model model) {
        model.addAttribute("street", street);
        PageInfo<Street> page = streetService.findRecoveryPage(new Page<Street>(), street);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Street street) {
        int recovery = streetService.recovery(street);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
