package com.littlebuddha.bobogou.modules.controller.basic;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.file.FileUtils;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.basic.Contract;
import com.littlebuddha.bobogou.modules.entity.common.DictData;
import com.littlebuddha.bobogou.modules.service.basic.ContractService;
import com.littlebuddha.bobogou.modules.service.common.DictDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 合同controller
 */
@Controller
@RequestMapping("/basic/contract")
public class ContractController extends BaseController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private DictDataService dictDataService;

    @Autowired
    private GlobalSetting globalSetting;

    @ModelAttribute
    public Contract get(@RequestParam(required = false) String id) {
        Contract contract = null;
        if (StringUtils.isNotBlank(id)) {
            contract = contractService.get(new Contract(id));
        }
        if (contract == null) {
            contract = new Contract();
        }
        return contract;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    @GetMapping(value = {"/", "/list"})
    public String list(Contract contract, Model model, HttpSession session) {
        model.addAttribute("contract", contract);
        return "modules/basic/contract";
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    @GetMapping(value = {"/downloadList"})
    public String downloadList(Contract contract, Model model, HttpSession session) {
        model.addAttribute("contract", contract);
        return "modules/basic/contractDownloadList";
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    @ResponseBody
    @GetMapping(value = {"/download"})
    public Result download(Contract contract, Model model, HttpSession session, HttpServletResponse response) {
        Result result = new Result();
        if (contract != null && StringUtils.isNotBlank(contract.getAddress())) {
            String address = contract.getAddress();
            String suffix = address.substring(contract.getAddress().lastIndexOf("."));
            String filename = contract.getName() + suffix;
            String path = globalSetting.getUploadImage() + address;
            FileUtils.download(response, filename, path);
        } else {
            result.setSuccess(false);
            result.setMsg("无合同文件");
            return result;
        }
        return null;
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Contract contract) {
        PageInfo<Contract> page = contractService.findPage(new Page<Contract>(), contract);
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
    public String form(@PathVariable(name = "mode") String mode, Contract contract, Model model) {
        model.addAttribute("contract", contract);
        return "modules/basic/contractForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Contract contract) {
        int save = contractService.save(contract);
        if (save > 0) {
            return new Result("200", "保存成功");
        } else {
            return new Result("310", "未知错误！保存失败");
        }
    }

    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String ids) {
        System.out.println("ids:" + ids);
        String[] split = ids.split(",");
        for (String s : split) {
            Contract contract = contractService.get(s);
            if (contract == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = contractService.deleteByLogic(contract);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Contract contract = contractService.get(s);
            if (contract == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = contractService.deleteByPhysics(contract);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Contract contract, Model model) {
        model.addAttribute("contract", contract);
        return "modules/recovery/contractRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Contract contract, Model model) {
        model.addAttribute("contract", contract);
        PageInfo<Contract> page = contractService.findRecoveryPage(new Page<Contract>(), contract);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Contract contract) {
        int recovery = contractService.recovery(contract);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
