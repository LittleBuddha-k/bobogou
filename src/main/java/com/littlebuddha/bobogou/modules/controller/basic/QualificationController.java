package com.littlebuddha.bobogou.modules.controller.basic;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.common.utils.file.FileUtils;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.basic.Qualification;
import com.littlebuddha.bobogou.modules.entity.common.ActHistory;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRole;
import com.littlebuddha.bobogou.modules.entity.system.Role;
import com.littlebuddha.bobogou.modules.mapper.system.OperatorRoleMapper;
import com.littlebuddha.bobogou.modules.mapper.system.RoleMapper;
import com.littlebuddha.bobogou.modules.service.basic.QualificationService;
import com.littlebuddha.bobogou.modules.service.common.ActHistoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 公司资质controller
 */
@Controller
@RequestMapping("/basic/qualification")
public class QualificationController extends BaseController {

    @Autowired
    private GlobalSetting globalSetting;

    @Autowired
    private QualificationService qualificationService;

    @Autowired
    private ActHistoryService actHistoryService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private OperatorRoleMapper operatorRoleMapper;

    @ModelAttribute
    public Qualification get(@RequestParam(required = false) String id) {
        Qualification qualification = null;
        if (StringUtils.isNotBlank(id)) {
            qualification = qualificationService.get(new Qualification(id));
        }
        if (qualification == null) {
            qualification = new Qualification();
        }
        return qualification;
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
    public String list(Qualification qualification, Model model, HttpSession session) {
        model.addAttribute("qualification", qualification);
        return "modules/basic/qualification";
    }

    /**
     * 返回资质下载列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    @GetMapping(value = {"/downloadList"})
    public String downloadList(Qualification qualification, Model model, HttpSession session) {
        model.addAttribute("qualification", qualification);
        return "modules/basic/qualificationDownloadList";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Qualification qualification) {
        Operator currentUser = UserUtils.getCurrentUser();
        Role currentUserRole = UserUtils.getCurrentUserRole();
        qualification.setCurrentUser(currentUser);
        qualification.setCurrentUserRole(currentUserRole);
        PageInfo<Qualification> page = qualificationService.findPage(new Page<Qualification>(), qualification);
        return getLayUiData(page);
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/downloadData")
    public TreeResult downloadData(Qualification qualification) {
        Operator currentUser = UserUtils.getCurrentUser();
        Role currentUserRole = UserUtils.getCurrentUserRole();
        qualification.setCurrentUser(currentUser);
        qualification.setCurrentUserRole(currentUserRole);
        PageInfo<Qualification> page = qualificationService.findDownloadDataPage(new Page<Qualification>(), qualification);
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
    public String form(@PathVariable(name = "mode") String mode, Qualification qualification, Model model) {
        model.addAttribute("qualification", qualification);
        return "modules/basic/qualificationForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Qualification qualification) {
        int save = qualificationService.save(qualification);
        if (save > 0) {
            return new Result("200", "保存成功");
        } else {
            return new Result("310", "未知错误！保存失败");
        }
    }/**
     * 提交审核
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/subTask")
    public Result subTask(Qualification qualification) {
        Result result = new Result();
        //点击提交时，新建审核历史记录
        ActHistory actHistory = new ActHistory();
        actHistory.setDataId(qualification.getId());
        //获取当前角色
        Operator currentUser = UserUtils.getCurrentUser();
        List<OperatorRole> byOperatorAndRole = operatorRoleMapper.getByOperatorAndRole(new OperatorRole(currentUser));
        if (byOperatorAndRole != null) {
            if (byOperatorAndRole.get(0) != null && byOperatorAndRole.get(0).getRole() != null && StringUtils.isNotBlank(byOperatorAndRole.get(0).getRole().getId())) {
                Role currentRole = roleMapper.get(new Role(byOperatorAndRole.get(0).getRole().getId()));
                //设置下一个审核角色
                qualification.setNextRoleId(currentRole.getParentId());
                if (StringUtils.isNotBlank(currentRole.getName())) {
                    actHistory.setExecutionLink(currentRole.getName() + "提交资质审核");
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
        if ("0".equals(qualification.getStatus()) || "3".equals(qualification.getStatus()) || "5".equals(qualification.getStatus()) || "7".equals(qualification.getStatus()) || "9".equals(qualification.getStatus()) || "11".equals(qualification.getStatus())) {
            qualification.setStatus("1");
        }
        int save = qualificationService.save(qualification);
        if (save > 0) {
            result.setCode("200");
            result.setMsg("提交资质审核成功");
        } else {
            result.setCode("500");
            result.setMsg("系统保存时出错，提交资质审核失败");
        }
        return result;
    }

    /**
     * @return
     */
    @GetMapping("/flow")
    public String flow(Qualification qualification, Model model) {
        model.addAttribute("qualification", qualification);
        return "modules/basic/qualificationAct";
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
    public String todoList(Qualification qualification, Model model) {
        model.addAttribute("qualification", qualification);
        return "modules/basic/qualificationTodoList";
    }

    /**
     * 查询审核数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/todoData")
    public TreeResult todoData(Qualification qualification) {
        Operator currentUser = UserUtils.getCurrentUser();
        Role currentUserRole = UserUtils.getCurrentUserRole();
        qualification.setCurrentUser(currentUser);
        qualification.setCurrentUserRole(currentUserRole);
        PageInfo<Qualification> page = qualificationService.findTodoPage(new Page<Qualification>(), qualification);
        return getLayUiData(page);
    }

    /**
     * 审核通过或者拒绝时
     *
     * @return
     */
    @GetMapping("/todoListForm")
    public String todoListForm(Qualification qualification, Model model) {
        Operator currentUser = UserUtils.getCurrentUser();
        model.addAttribute("currentUserAreaManager", currentUser.getAreaManager());
        model.addAttribute("qualification", qualification);
        return "modules/basic/qualificationTodoListForm";
    }

    @ResponseBody
    @PostMapping("/doTask")
    public Result doTask(Qualification qualification) {
        Result result = new Result();
        //点击提交时，新建审核历史记录
        ActHistory actHistory = new ActHistory();
        //获取当前角色
        Operator currentUser = UserUtils.getCurrentUser();
        List<OperatorRole> byOperatorAndRole = operatorRoleMapper.getByOperatorAndRole(new OperatorRole(currentUser));
        String reason = "";
        actHistory.setDataId(qualification.getId());
        if (byOperatorAndRole != null) {
            if (byOperatorAndRole.get(0) != null && byOperatorAndRole.get(0).getRole() != null && StringUtils.isNotBlank(byOperatorAndRole.get(0).getRole().getId())) {
                Role currentRole = roleMapper.get(new Role(byOperatorAndRole.get(0).getRole().getId()));
                //设置下一个审核角色
                qualification.setNextRoleId(currentRole.getParentId());
                if (StringUtils.isNotBlank(currentRole.getName())) {
                    if ("2".equals(qualification.getStatus()) || "4".equals(qualification.getStatus()) || "6".equals(qualification.getStatus()) || "8".equals(qualification.getStatus()) || "10".equals(qualification.getStatus())) {
                        reason = "通过";
                        result.setMsg("已通过审核");
                    }
                    if ("3".equals(qualification.getStatus()) || "5".equals(qualification.getStatus()) || "7".equals(qualification.getStatus()) || "9".equals(qualification.getStatus()) || "11".equals(qualification.getStatus())) {
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
        int save = qualificationService.save(qualification);
        if (save > 0) {
            result.setCode("200");
        } else {
            result.setCode("500");
            result.setMsg("系统保存时出错，提交审核失败");
        }
        return result;
    }

    /**
     * 资质下载
     */
    @ResponseBody
    @RequestMapping(value = "/download")
    public Result download(Qualification qualification,HttpServletResponse response) throws IOException {
        Result result = new Result();
        if (qualification != null && StringUtils.isNotBlank(qualification.getQualification())) {
            String [] filename = qualification.getQualification().split(",");
            String [] path = qualification.getQualification().split(",");
            if (path != null && path.length > 0){
                String [] realPath = new String[path.length];
                for (int i =0;i<path.length;i++) {
                    realPath[i] = (globalSetting.getUploadImage() + path[i]).replaceAll(globalSetting.getRootPath(),"");
                }
                FileUtils.imgDownload(response,filename, realPath);
            }else {

            }
        }else {
            result.setSuccess(false);
            result.setMsg("无资质图片");
            return result;
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Qualification qualification = qualificationService.get(s);
            if (qualification == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = qualificationService.deleteByLogic(qualification);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Qualification qualification = qualificationService.get(s);
            if (qualification == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = qualificationService.deleteByPhysics(qualification);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Qualification qualification, Model model) {
        model.addAttribute("qualification", qualification);
        return "modules/recovery/qualificationRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Qualification qualification, Model model) {
        model.addAttribute("qualification", qualification);
        PageInfo<Qualification> page = qualificationService.findRecoveryPage(new Page<Qualification>(), qualification);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Qualification qualification) {
        int recovery = qualificationService.recovery(qualification);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
