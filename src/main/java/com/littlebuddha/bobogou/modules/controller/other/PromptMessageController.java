package com.littlebuddha.bobogou.modules.controller.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.other.PromptMessage;
import com.littlebuddha.bobogou.modules.service.other.PromptMessageService;
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
@RequestMapping("/other/promptMessage")
public class PromptMessageController extends BaseController {

    @Autowired
    private PromptMessageService promptMessageService;

    @ModelAttribute
    public PromptMessage get(@RequestParam(required = false) String id) {
        PromptMessage promptMessage = null;
        if (StringUtils.isNotBlank(id)) {
            promptMessage = promptMessageService.get(id);
        }
        if (promptMessage == null) {
            promptMessage = new PromptMessage();
        }
        return promptMessage;
    }

    /**
     * 返回系统消息列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/PromptMessage/list")
    @GetMapping(value = {"/", "/list"})
    public String list(PromptMessage promptMessage, Model model, HttpSession session) {
        model.addAttribute("promptMessage", promptMessage);
        return "modules/other/promptMessage";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(PromptMessage promptMessage) {
        PageInfo<PromptMessage> page = promptMessageService.findPage(new Page<PromptMessage>(), promptMessage);
        return getLayUiData(page);
    }

    /**
     * 返回所有数据
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/all")
    public List<PromptMessage> all(PromptMessage promptMessage) {
        List<PromptMessage> list = promptMessageService.findList(promptMessage);
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
    public String form(@PathVariable(name = "mode") String mode, PromptMessage promptMessage, Model model) {
        model.addAttribute("promptMessage", promptMessage);
        return "modules/other/promptMessageForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(PromptMessage promptMessage) {
        int save = promptMessageService.save(promptMessage);
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
            String fileName = "系统消息管理模板.xlsx";
            List<PromptMessage> list = Lists.newArrayList();
            new ExportExcel("系统消息管理数据", PromptMessage.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<PromptMessage> list = ei.getDataList(PromptMessage.class);
            for (PromptMessage promptMessage : list) {
                try {
                    promptMessageService.save(promptMessage);
                    successNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条系统消息管理记录。");
            }
            result.setSuccess(true);
            result.setMsg("已成功导入 " + successNum + " 条系统消息管理记录" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导入系统消息管理失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(PromptMessage promptMessage, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "系统消息管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<PromptMessage> list = promptMessageService.findList(promptMessage);
            if (list != null & list.size() > 0) {
                new ExportExcel("系统消息管理", PromptMessage.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("系统消息管理", PromptMessage.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
            }
            return null;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导出系统消息记录失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String ids) {
        System.out.println("ids:" + ids);
        String[] split = ids.split(",");
        for (String s : split) {
            PromptMessage promptMessage = promptMessageService.get(s);
            if (promptMessage == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = promptMessageService.deleteByLogic(promptMessage);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            PromptMessage promptMessage = promptMessageService.get(s);
            if (promptMessage == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = promptMessageService.deleteByPhysics(promptMessage);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(PromptMessage promptMessage, Model model) {
        model.addAttribute("promptMessage", promptMessage);
        return "modules/recovery/promptMessageRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(PromptMessage promptMessage, Model model) {
        model.addAttribute("promptMessage", promptMessage);
        PageInfo<PromptMessage> page = promptMessageService.findRecoveryPage(new Page<PromptMessage>(), promptMessage);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(PromptMessage promptMessage) {
        int recovery = promptMessageService.recovery(promptMessage);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
