package com.littlebuddha.bobogou.modules.controller.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;
import com.littlebuddha.bobogou.modules.entity.other.Sticker;
import com.littlebuddha.bobogou.modules.service.other.CustomerUserService;
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
@RequestMapping("/other/sticker")
public class StickerController extends BaseController {

    @Autowired
    private StickerService stickerService;

    @Autowired
    private CustomerUserService customerUserService;

    @ModelAttribute
    public Sticker get(@RequestParam(required = false) String id) {
        Sticker sticker = null;
        if (StringUtils.isNotBlank(id)) {
            sticker = stickerService.get(new Sticker(id));
        }
        if (sticker == null) {
            sticker = new Sticker();
        }
        if (sticker != null && sticker.getUserId() != null){
            CustomerUser customerUser = customerUserService.get(sticker.getUserId().toString());
            sticker.setCustomerUser(customerUser);
        }
        return sticker;
    }

    /**
     * ??????????????????
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
     * ????????????
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
     * ??????????????????
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
     * ????????????
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
     * ????????????
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Sticker sticker) {
        int save = stickerService.save(sticker);
        if (save > 0) {
            return new Result("200", "????????????");
        } else {
            return new Result("310", "???????????????????????????");
        }
    }

    @ResponseBody
    @GetMapping("/importTemplate")
    public Result importTemplate(HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "??????????????????.xlsx";
            List<Sticker> list = Lists.newArrayList();
            new ExportExcel("??????????????????", Sticker.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            result.setSuccess(true);
            result.setMsg("??????????????????????????????????????????" + e.getMessage());
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
                failureMsg.insert(0, "????????? " + failureNum + " ????????????????????????");
            }
            result.setSuccess(true);
            result.setMsg("??????????????? " + successNum + " ?????????????????????" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("??????????????????????????????????????????" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(Sticker sticker, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "????????????" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<Sticker> list = stickerService.findList(sticker);
            if (list != null & list.size() > 0) {
                new ExportExcel("????????????", Sticker.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("????????????", Sticker.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
            }
            return null;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("??????????????????????????????????????????" + e.getMessage());
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
                return new Result("311", "???????????????,????????????????????????????????????");
            }
            int i = stickerService.deleteByLogic(sticker);
        }
        return new Result("200", "??????????????????");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Sticker sticker = stickerService.get(s);
            if (sticker == null) {
                return new Result("311", "???????????????,????????????????????????????????????");
            }
            int i = stickerService.deleteByPhysics(sticker);
        }
        return new Result("200", "??????????????????");
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
            return new Result("200", "???????????????");
        } else {
            return new Result("322", "?????????????????????????????????");
        }
    }
}
