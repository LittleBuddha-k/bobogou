package com.littlebuddha.bobogou.modules.controller.basic;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.common.utils.file.FileUtils;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.basic.Factory;
import com.littlebuddha.bobogou.modules.entity.common.DictData;
import com.littlebuddha.bobogou.modules.entity.data.Province;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.Role;
import com.littlebuddha.bobogou.modules.service.basic.FactoryService;
import com.littlebuddha.bobogou.modules.service.common.DictDataService;
import com.littlebuddha.bobogou.modules.service.data.ProvinceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 厂商controller
 */
@Controller
@RequestMapping("/basic/factory")
public class FactoryController extends BaseController {

    @Autowired
    private FactoryService factoryService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private DictDataService dictDataService;

    @Autowired
    private GlobalSetting globalSetting;

    @ModelAttribute
    public Factory get(@RequestParam(required = false) String id) {
        Factory factory = null;
        if (StringUtils.isNotBlank(id)) {
            factory = factoryService.get(new Factory(id));
        }
        if (factory == null) {
            factory = new Factory();
        }
        return factory;
    }

    /**
     * 返回厂家列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    @GetMapping(value = {"/", "/list"})
    public String list(Factory factory, Model model, HttpSession session) {
        model.addAttribute("factory", factory);
        return "modules/basic/factory";
    }

    /**
     * 返回厂家资质过期列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    @GetMapping(value = "/listExpire")
    public String listExpire(Factory factory, Model model, HttpSession session) {
        model.addAttribute("factory", factory);
        return "modules/basic/factoryExpire";
    }

    /**
     * 返回数据-----返回当前用户及其所有下级角色的数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Factory factory) {
        PageInfo<Factory> page = factoryService.findPage(new Page<Factory>(), factory);
        return getLayUiData(page);
    }

    /**
     * 返回数据-----返回当前用户及其所有下级角色的资质过期数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/dataExpire")
    public TreeResult dataExpire(Factory factory) {
        PageInfo<Factory> page = factoryService.findExpirePage(new Page<Factory>(), factory);
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
    public String form(@PathVariable(name = "mode") String mode, Factory factory, Model model) {
        model.addAttribute("factory", factory);
        return "modules/basic/factoryForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Factory factory) {
        int save = factoryService.save(factory);
        if (save > 0) {
            return new Result("200", "保存成功");
        } else {
            return new Result("310", "未知错误！保存失败");
        }
    }

    /**
     * 厂家过期资质下载
     */
    @ResponseBody
    @RequestMapping(value = "/download")
    public Result download(Factory factory, HttpServletResponse response) throws IOException {
        Result result = new Result();
        if (factory != null) {
            StringJoiner stringJoiner = new StringJoiner(",");
            if (StringUtils.isNotBlank(factory.getBusinessLicense())) {
                stringJoiner.add(factory.getBusinessLicense());
            }
            if (StringUtils.isNotBlank(factory.getAnnualReport())) {
                stringJoiner.add(factory.getAnnualReport());
            }
            if (StringUtils.isNotBlank(factory.getBusinessPermit())) {
                stringJoiner.add(factory.getBusinessPermit());
            }
            if (StringUtils.isNotBlank(factory.getBasicAccount())) {
                stringJoiner.add(factory.getBasicAccount());
            }
            if (StringUtils.isNotBlank(factory.getBillingInformation())) {
                stringJoiner.add(factory.getBillingInformation());
            }
            if (StringUtils.isNotBlank(factory.getSampleInvoiceTicket())) {
                stringJoiner.add(factory.getSampleInvoiceTicket());
            }
            if (StringUtils.isNotBlank(factory.getQualityGuarantee())) {
                stringJoiner.add(factory.getQualityGuarantee());
            }
            if (StringUtils.isNotBlank(factory.getSealImpression())) {
                stringJoiner.add(factory.getSealImpression());
            }
            if (StringUtils.isNotBlank(factory.getPowerAttorney())) {
                stringJoiner.add(factory.getPowerAttorney());
            }
            if (StringUtils.isNotBlank(factory.getInvoiceCounterparts())) {
                stringJoiner.add(factory.getInvoiceCounterparts());
            }
            if (StringUtils.isNotBlank(factory.getBailorCard())) {
                stringJoiner.add(factory.getBailorCard());
            }
            if (StringUtils.isNotBlank(factory.getMandataryCard())) {
                stringJoiner.add(factory.getMandataryCard());
            }
            if (StringUtils.isNotBlank(factory.getTakeDeliveryBailment())) {
                stringJoiner.add(factory.getTakeDeliveryBailment());
            }
            if (StringUtils.isNotBlank(factory.getFoodBusinessLicense())) {
                stringJoiner.add(factory.getFoodBusinessLicense());
            }
            String[] split = stringJoiner.toString().split(",");
            String[] filename = new String[split.length];
            if (split != null && split.length > 0) {
                String[] realPath = new String[split.length];
                for (int i = 0; i < split.length; i++) {
                    realPath[i] = (globalSetting.getUploadImage() + split[i]).replaceAll(globalSetting.getRootPath(), "");
                    filename[i] = (split[i]).replaceAll(globalSetting.getRootPath(), "");
                }
                FileUtils.imgDownload(response, filename, realPath);
            }
        } else {
            result.setSuccess(false);
            result.setMsg("无资质图片");
            return result;
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String ids) {
        System.out.println("ids:" + ids);
        String[] split = ids.split(",");
        for (String s : split) {
            Factory factory = factoryService.get(s);
            if (factory == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = factoryService.deleteByLogic(factory);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Factory factory = factoryService.get(s);
            if (factory == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = factoryService.deleteByPhysics(factory);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Factory factory, Model model) {
        model.addAttribute("factory", factory);
        return "modules/recovery/factoryRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Factory factory, Model model) {
        model.addAttribute("factory", factory);
        PageInfo<Factory> page = factoryService.findRecoveryPage(new Page<Factory>(), factory);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Factory factory) {
        int recovery = factoryService.recovery(factory);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
