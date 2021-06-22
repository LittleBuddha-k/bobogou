package com.littlebuddha.bobogou.modules.controller.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.other.Sticker;
import com.littlebuddha.bobogou.modules.service.other.StickerService;
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
@RequestMapping("/data/sticker")
public class StickerController extends BaseController {

    @Autowired
    private StickerService stickerService;

    @ModelAttribute
    public Sticker get(@RequestParam(required = false) String id) {
        Sticker sticker = null;
        if (StringUtils.isNotBlank(id)) {
            sticker = stickerService.get(id);
        }
        if (sticker == null) {
            sticker = new Sticker();
        }
        return sticker;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/Sticker/list")
    @GetMapping(value = {"/", "/list"})
    public String list(Sticker sticker, Model model, HttpSession session) {
        model.addAttribute("sticker", sticker);
        return "modules/other/sticker";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Sticker sticker) {
        PageInfo<Sticker> page = stickerService.findPage(new Page<Sticker>(), sticker);
        return getLayUiData(page);
    }

    /**
     * 返回所有数据
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/all")
    public List<Sticker> all(Sticker sticker) {
        List<Sticker> list = stickerService.findList(sticker);
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
    public String form(@PathVariable(name = "mode") String mode, Sticker sticker, Model model) {
        model.addAttribute("sticker", sticker);
        return "modules/other/stickerForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Sticker sticker) {
        int save = stickerService.save(sticker);
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
            String fileName = "贴单管理模板.xlsx";
            List<Sticker> list = Lists.newArrayList();
            new ExportExcel("贴单管理数据", Sticker.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<Sticker> list = ei.getDataList(Sticker.class);
            for (Sticker sticker : list) {
                try {
                    stickerService.save(sticker);
                    successNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条贴单管理记录。");
            }
            result.setSuccess(true);
            result.setMsg("已成功导入 " + successNum + " 条贴单管理记录" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导入贴单管理失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(Sticker sticker, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "贴单管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<Sticker> list = stickerService.findList(sticker);
            if (list != null & list.size() > 0) {
                new ExportExcel("贴单管理", Sticker.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("贴单管理", Sticker.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
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
            Sticker sticker = stickerService.get(s);
            if (sticker == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = stickerService.deleteByLogic(sticker);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Sticker sticker = stickerService.get(s);
            if (sticker == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = stickerService.deleteByPhysics(sticker);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Sticker sticker, Model model) {
        model.addAttribute("sticker", sticker);
        return "modules/recovery/stickerRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Sticker sticker, Model model) {
        model.addAttribute("sticker", sticker);
        PageInfo<Sticker> page = stickerService.findRecoveryPage(new Page<Sticker>(), sticker);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Sticker sticker) {
        int recovery = stickerService.recovery(sticker);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
