package com.littlebuddha.bobogou.modules.controller.system;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.system.utils.Icon;
import com.littlebuddha.bobogou.modules.service.system.IconService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 图标controller
 */
@Controller
@RequestMapping("/system/icon")
public class IconController extends BaseController {

    @Autowired
    private IconService iconService;

    @ModelAttribute
    public Icon get(@RequestParam(required = false) String id) {
        Icon icon = null;
        if (StringUtils.isNotBlank(id)) {
            icon = iconService.get(id);
        }
        if (icon == null) {
            icon = new Icon();
        }
        return icon;
    }

    /**
     * 返回图标列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/Icon/list")
    @GetMapping(value = {"/", "/list"})
    public String list(Icon icon, Model model, HttpSession session) {
        model.addAttribute("icon", icon);
        return "modules/system/icon";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Icon icon) {
        PageInfo<Icon> page = iconService.findPage(new Page<Icon>(), icon);
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
    public String form(@PathVariable(name = "mode") String mode, Icon icon, Model model) {
        model.addAttribute("icon", icon);
        return "modules/system/iconForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Icon icon) {
        int save = iconService.save(icon);
        if (save > 0) {
            return new Result("200", "保存成功");
        } else {
            return new Result("310", "未知错误！保存失败");
        }
    }

    /*@ResponseBody
    @GetMapping("/importTemplate")
    public Result importTemplate(HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "图标模板.xlsx";
            List<Icon> list = Lists.newArrayList();
            new ExportExcel("图标数据", Icon.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<Icon> list = ei.getDataList(Icon.class);
            for (Icon icon : list) {
                try {
                    iconService.save(icon);
                    successNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条图标记录。");
            }
            result.setSuccess(true);
            result.setMsg("已成功导入 " + successNum + " 条图标记录" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导入图标失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(Icon icon, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "图标" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<Icon> list = iconService.findList(icon);
            if (list != null & list.size() > 0) {
                new ExportExcel("图标", Icon.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("图标", Icon.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
            }
            return null;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导出图标记录失败！失败信息：" + e.getMessage());
        }
        return result;
    }*/

    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String ids) {
        System.out.println("ids:" + ids);
        String[] split = ids.split(",");
        for (String s : split) {
            Icon icon = iconService.get(s);
            if (icon == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = iconService.deleteByLogic(icon);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Icon icon = iconService.get(s);
            if (icon == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = iconService.deleteByPhysics(icon);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Icon icon, Model model) {
        model.addAttribute("icon", icon);
        return "modules/recovery/iconRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Icon icon, Model model) {
        model.addAttribute("icon", icon);
        PageInfo<Icon> page = iconService.findRecoveryPage(new Page<Icon>(), icon);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Icon icon) {
        int recovery = iconService.recovery(icon);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
