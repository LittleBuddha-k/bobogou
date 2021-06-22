package com.littlebuddha.bobogou.modules.controller.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.other.Integral;
import com.littlebuddha.bobogou.modules.service.other.IntegralService;
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
@RequestMapping("/data/integral")
public class IntegralController extends BaseController {

    @Autowired
    private IntegralService integralService;

    @ModelAttribute
    public Integral get(@RequestParam(required = false) String id) {
        Integral integral = null;
        if (StringUtils.isNotBlank(id)) {
            integral = integralService.get(id);
        }
        if (integral == null) {
            integral = new Integral();
        }
        return integral;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/Integral/list")
    @GetMapping(value = {"/", "/list"})
    public String list(Integral integral, Model model, HttpSession session) {
        model.addAttribute("integral", integral);
        return "modules/other/integral";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Integral integral) {
        PageInfo<Integral> page = integralService.findPage(new Page<Integral>(), integral);
        return getLayUiData(page);
    }

    /**
     * 返回所有数据
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/all")
    public List<Integral> all(Integral integral) {
        List<Integral> list = integralService.findList(integral);
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
    public String form(@PathVariable(name = "mode") String mode, Integral integral, Model model) {
        model.addAttribute("integral", integral);
        return "modules/other/integralForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Integral integral) {
        int save = integralService.save(integral);
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
            String fileName = "积分管理模板.xlsx";
            List<Integral> list = Lists.newArrayList();
            new ExportExcel("积分管理数据", Integral.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<Integral> list = ei.getDataList(Integral.class);
            for (Integral integral : list) {
                try {
                    integralService.save(integral);
                    successNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条积分管理记录。");
            }
            result.setSuccess(true);
            result.setMsg("已成功导入 " + successNum + " 条积分管理记录" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导入积分管理失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(Integral integral, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "积分管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<Integral> list = integralService.findList(integral);
            if (list != null & list.size() > 0) {
                new ExportExcel("积分管理", Integral.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("积分管理", Integral.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
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
            Integral integral = integralService.get(s);
            if (integral == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = integralService.deleteByLogic(integral);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Integral integral = integralService.get(s);
            if (integral == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = integralService.deleteByPhysics(integral);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Integral integral, Model model) {
        model.addAttribute("integral", integral);
        return "modules/recovery/integralRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Integral integral, Model model) {
        model.addAttribute("integral", integral);
        PageInfo<Integral> page = integralService.findRecoveryPage(new Page<Integral>(), integral);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Integral integral) {
        int recovery = integralService.recovery(integral);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
