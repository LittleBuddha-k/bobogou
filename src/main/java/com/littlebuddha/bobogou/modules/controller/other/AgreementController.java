package com.littlebuddha.bobogou.modules.controller.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.common.DictData;
import com.littlebuddha.bobogou.modules.entity.other.Agreement;
import com.littlebuddha.bobogou.modules.service.common.DictDataService;
import com.littlebuddha.bobogou.modules.service.other.AgreementService;
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
@RequestMapping("/other/agreement")
public class AgreementController extends BaseController {

    @Autowired
    private AgreementService agreementService;

    @Autowired
    private DictDataService dictDataService;

    @ModelAttribute
    public Agreement get(@RequestParam(required = false) String id) {
        Agreement agreement = null;
        if (StringUtils.isNotBlank(id)) {
            agreement = agreementService.get(id);
        }
        if (agreement == null) {
            agreement = new Agreement();
        }
        return agreement;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/Agreement/list")
    @GetMapping(value = {"/", "/list"})
    public String list(Agreement agreement, Model model, HttpSession session) {
        model.addAttribute("agreement", agreement);
        return "modules/other/agreement";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Agreement agreement) {
        PageInfo<Agreement> page = agreementService.findPage(new Page<Agreement>(), agreement);
        return getLayUiData(page);
    }

    /**
     * 返回所有数据
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/all")
    public List<Agreement> all(Agreement agreement) {
        List<Agreement> list = agreementService.findList(agreement);
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
    public String form(@PathVariable(name = "mode") String mode, Agreement agreement, Model model) {
        DictData select = new DictData();
        select.setType("protocol");
        List<DictData> typeList = dictDataService.findList(select);
        model.addAttribute("typeList", typeList);
        model.addAttribute("agreement", agreement);
        return "modules/other/agreementForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Agreement agreement) {
        int save = agreementService.save(agreement);
        if (save > 0) {
            return new Result("200", "保存成功");
        } else if (-1 == save){
            return new Result("320", "已有同类型数据，不可再保存第二条同类型数据");
        }else {
            return new Result("310", "未知错误！保存失败");
        }
    }

    @ResponseBody
    @GetMapping("/importTemplate")
    public Result importTemplate(HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "协议管理模板.xlsx";
            List<Agreement> list = Lists.newArrayList();
            new ExportExcel("协议管理数据", Agreement.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<Agreement> list = ei.getDataList(Agreement.class);
            for (Agreement agreement : list) {
                try {
                    agreementService.save(agreement);
                    successNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条协议管理记录。");
            }
            result.setSuccess(true);
            result.setMsg("已成功导入 " + successNum + " 条协议管理记录" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导入协议管理失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(Agreement agreement, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "协议管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<Agreement> list = agreementService.findList(agreement);
            if (list != null & list.size() > 0) {
                new ExportExcel("协议管理", Agreement.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("协议管理", Agreement.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
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
            Agreement agreement = agreementService.get(s);
            if (agreement == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = agreementService.deleteByLogic(agreement);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Agreement agreement = agreementService.get(s);
            if (agreement == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = agreementService.deleteByPhysics(agreement);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Agreement agreement, Model model) {
        model.addAttribute("agreement", agreement);
        return "modules/recovery/agreementRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Agreement agreement, Model model) {
        model.addAttribute("agreement", agreement);
        PageInfo<Agreement> page = agreementService.findRecoveryPage(new Page<Agreement>(), agreement);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Agreement agreement) {
        int recovery = agreementService.recovery(agreement);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
