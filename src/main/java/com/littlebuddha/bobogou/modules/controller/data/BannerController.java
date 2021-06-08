package com.littlebuddha.bobogou.modules.controller.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.data.Banner;
import com.littlebuddha.bobogou.modules.service.data.BannerService;
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
@RequestMapping("/data/banner")
public class BannerController extends BaseController {

    @Autowired
    private BannerService bannerService;

    @ModelAttribute
    public Banner get(@RequestParam(required = false) String id) {
        Banner banner = null;
        if (StringUtils.isNotBlank(id)) {
            banner = bannerService.get(id);
        }
        if (banner == null) {
            banner = new Banner();
        }
        return banner;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/Banner/list")
    @GetMapping(value = {"/", "/list"})
    public String list(Banner banner, Model model, HttpSession session) {
        model.addAttribute("banner", banner);
        return "modules/data/banner";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Banner banner) {
        PageInfo<Banner> page = bannerService.findPage(new Page<Banner>(), banner);
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
    public String form(@PathVariable(name = "mode") String mode, Banner banner, Model model) {
        model.addAttribute("banner", banner);
        return "modules/data/bannerForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Banner banner) {
        int save = bannerService.save(banner);
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
            String fileName = "轮播图模板.xlsx";
            List<Banner> list = Lists.newArrayList();
            new ExportExcel("轮播图数据", Banner.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<Banner> list = ei.getDataList(Banner.class);
            for (Banner banner : list) {
                try {
                    bannerService.save(banner);
                    successNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条轮播图记录。");
            }
            result.setSuccess(true);
            result.setMsg("已成功导入 " + successNum + " 条轮播图记录" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导入轮播图失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(Banner banner, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "轮播图" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<Banner> list = bannerService.findList(banner);
            if (list != null & list.size() > 0) {
                new ExportExcel("轮播图", Banner.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("轮播图", Banner.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
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
            Banner banner = bannerService.get(s);
            if (banner == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = bannerService.deleteByLogic(banner);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Banner banner = bannerService.get(s);
            if (banner == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = bannerService.deleteByPhysics(banner);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Banner banner, Model model) {
        model.addAttribute("banner", banner);
        return "modules/recovery/bannerRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Banner banner, Model model) {
        model.addAttribute("banner", banner);
        PageInfo<Banner> page = bannerService.findRecoveryPage(new Page<Banner>(), banner);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Banner banner) {
        int recovery = bannerService.recovery(banner);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
