package com.littlebuddha.bobogou.modules.controller.basic;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.file.FileUtils;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.basic.Contract;
import com.littlebuddha.bobogou.modules.entity.basic.SignContract;
import com.littlebuddha.bobogou.modules.entity.common.DictData;
import com.littlebuddha.bobogou.modules.service.basic.ContractService;
import com.littlebuddha.bobogou.modules.service.basic.SignContractService;
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
@RequestMapping("/basic/signContract")
public class SignContractController extends BaseController {

    @Autowired
    private SignContractService signContractService;

    @Autowired
    private DictDataService dictDataService;

    @ModelAttribute
    public SignContract get(@RequestParam(required = false) String id) {
        SignContract signContract = null;
        if (StringUtils.isNotBlank(id)) {
            signContract = signContractService.get(new SignContract(id));
        }
        if (signContract == null) {
            signContract = new SignContract();
        }
        return signContract;
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
    public String list(SignContract signContract, Model model, HttpSession session) {
        model.addAttribute("signContract", signContract);
        return "modules/basic/signContract";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(SignContract signContract) {
        PageInfo<SignContract> page = signContractService.findPage(new Page<SignContract>(), signContract);
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
    public String form(@PathVariable(name = "mode") String mode, SignContract signContract, Model model) {
        model.addAttribute("signContract", signContract);
        return "modules/basic/signContractForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(SignContract signContract) {
        int save = signContractService.save(signContract);
        if (save > 0) {
            return new Result("200", "保存成功");
        } else {
            return new Result("310", "未知错误！保存失败");
        }
    }

    /**
     * 提交审核
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/subTask")
    public Result subTask(SignContract signContract) {
        return null;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            SignContract signContract = signContractService.get(s);
            if (signContract == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = signContractService.deleteByLogic(signContract);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            SignContract signContract = signContractService.get(s);
            if (signContract == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = signContractService.deleteByPhysics(signContract);
        }
        return new Result("200", "数据清除成功");
    }
}
