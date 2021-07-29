package com.littlebuddha.bobogou.modules.controller.basic;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.common.utils.file.FileUtils;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.basic.Contract;
import com.littlebuddha.bobogou.modules.entity.basic.SignContract;
import com.littlebuddha.bobogou.modules.entity.common.ActHistory;
import com.littlebuddha.bobogou.modules.entity.common.DictData;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRole;
import com.littlebuddha.bobogou.modules.entity.system.Role;
import com.littlebuddha.bobogou.modules.mapper.system.OperatorRoleMapper;
import com.littlebuddha.bobogou.modules.mapper.system.RoleMapper;
import com.littlebuddha.bobogou.modules.service.basic.ContractService;
import com.littlebuddha.bobogou.modules.service.basic.SignContractService;
import com.littlebuddha.bobogou.modules.service.common.ActHistoryService;
import com.littlebuddha.bobogou.modules.service.common.DictDataService;
import com.littlebuddha.bobogou.modules.service.system.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
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

    @Autowired
    private ActHistoryService actHistoryService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private OperatorRoleMapper operatorRoleMapper;

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
        Result result = new Result();
        //点击提交时，新建审核历史记录
        ActHistory actHistory = new ActHistory();
        actHistory.setDataId(signContract.getId());
        //获取当前角色
        Operator currentUser = UserUtils.getCurrentUser();
        List<OperatorRole> byOperatorAndRole = operatorRoleMapper.getByOperatorAndRole(new OperatorRole(currentUser));
        if (byOperatorAndRole != null) {
            if (byOperatorAndRole.get(0) != null && byOperatorAndRole.get(0).getRole() != null && StringUtils.isNotBlank(byOperatorAndRole.get(0).getRole().getId())) {
                Role currentRole = roleMapper.get(new Role(byOperatorAndRole.get(0).getRole().getId()));
                //设置下一个审核角色
                signContract.setNextRoleId(currentRole.getParentId());
                if (StringUtils.isNotBlank(currentRole.getName())) {
                    actHistory.setExecutionLink(currentRole.getName() + "提交审核");
                }
                actHistory.setRoleId(currentRole.getId());
                actHistory.setRoleName(currentRole.getName());
            }
        }
        actHistory.setExecutionId(currentUser.getId());
        actHistory.setExecutionName(currentUser.getNickname());
        actHistory.setBeginDate(new Date());
        actHistory.setEndDate(new Date());
        //保存提交审核的历史记录
        actHistoryService.save(actHistory);
        //走到这里来 设置初始审核状态、设置下一个审核角色id
        //初始保存的时候设置初始状态----进入审核
        if ("0".equals(signContract.getStatus()) || "3".equals(signContract.getStatus()) || "5".equals(signContract.getStatus()) || "7".equals(signContract.getStatus()) || "9".equals(signContract.getStatus()) || "11".equals(signContract.getStatus())) {
            signContract.setStatus("1");
        }
        int save = signContractService.save(signContract);
        if (save > 0) {
            result.setCode("200");
            result.setMsg("提交审核成功");
        } else {
            result.setCode("500");
            result.setMsg("系统保存时出错，提交审核失败");
        }
        return result;
    }

    /**
     * @return
     */
    @GetMapping("/flow")
    public String flow(SignContract signContract, Model model) {
        model.addAttribute("signContract", signContract);
        return "modules/basic/signContractAct";
    }

    /**
     * @return
     */
    @ResponseBody
    @GetMapping("/flowData")
    public TreeResult flowData(ActHistory actHistory, Model model) {
        PageInfo<ActHistory> page = actHistoryService.findPage(new Page<ActHistory>(), actHistory);
        return getLayUiData(page);
    }

    /**
     * @return
     */
    @GetMapping("/todoList")
    public String todoList(SignContract signContract, Model model) {
        model.addAttribute("signContract", signContract);
        return "modules/basic/signContractTodoList";
    }

    /**
     * 查询审核数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/todoData")
    public TreeResult todoData(SignContract signContract) {
        Operator currentUser = UserUtils.getCurrentUser();
        Role currentUserRole = UserUtils.getCurrentUserRole();
        signContract.setCurrentUser(currentUser);
        signContract.setCurrentUserRole(currentUserRole);
        PageInfo<SignContract> page = signContractService.findTodoPage(new Page<SignContract>(), signContract);
        return getLayUiData(page);
    }

    /**
     * 审核通过或者拒绝时
     *
     * @return
     */
    @GetMapping("/todoListForm")
    public String todoListForm(SignContract signContract, Model model) {
        Operator currentUser = UserUtils.getCurrentUser();
        model.addAttribute("currentUserAreaManager", currentUser.getAreaManager());
        model.addAttribute("signContract", signContract);
        return "modules/basic/signContractTodoListForm";
    }

    @ResponseBody
    @PostMapping("/doTask")
    public Result doTask(SignContract signContract) {
        Result result = new Result();
        //点击提交时，新建审核历史记录
        ActHistory actHistory = new ActHistory();
        //获取当前角色
        Operator currentUser = UserUtils.getCurrentUser();
        List<OperatorRole> byOperatorAndRole = operatorRoleMapper.getByOperatorAndRole(new OperatorRole(currentUser));
        String reason = "";
        actHistory.setDataId(signContract.getId());
        if (byOperatorAndRole != null) {
            if (byOperatorAndRole.get(0) != null && byOperatorAndRole.get(0).getRole() != null && StringUtils.isNotBlank(byOperatorAndRole.get(0).getRole().getId())) {
                Role currentRole = roleMapper.get(new Role(byOperatorAndRole.get(0).getRole().getId()));
                //设置下一个审核角色
                signContract.setNextRoleId(currentRole.getParentId());
                if (StringUtils.isNotBlank(currentRole.getName())) {
                    if ("2".equals(signContract.getStatus()) || "4".equals(signContract.getStatus()) || "6".equals(signContract.getStatus()) || "8".equals(signContract.getStatus()) || "10".equals(signContract.getStatus())) {
                        reason = "通过";
                        result.setMsg("已通过审核");
                    }
                    if ("3".equals(signContract.getStatus()) || "5".equals(signContract.getStatus()) || "7".equals(signContract.getStatus()) || "9".equals(signContract.getStatus()) || "11".equals(signContract.getStatus())) {
                        reason = "拒绝";
                        result.setMsg("已拒绝审核");
                    }
                    actHistory.setExecutionLink(currentRole.getName() + "审核" + reason);
                }
                actHistory.setRoleId(currentRole.getId());
                actHistory.setRoleName(currentRole.getName());
            }
        }
        actHistory.setExecutionId(currentUser.getId());
        actHistory.setExecutionName(currentUser.getNickname());
        actHistory.setBeginDate(new Date());
        actHistory.setEndDate(new Date());
        //保存提交审核的历史记录
        actHistoryService.save(actHistory);
        int save = signContractService.save(signContract);
        if (save > 0) {
            result.setCode("200");
        } else {
            result.setCode("500");
            result.setMsg("系统保存时出错，提交审核失败");
        }
        return result;
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
