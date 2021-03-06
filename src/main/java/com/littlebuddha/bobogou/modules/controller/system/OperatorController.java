package com.littlebuddha.bobogou.modules.controller.system;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.common.DictData;
import com.littlebuddha.bobogou.modules.entity.data.Area;
import com.littlebuddha.bobogou.modules.entity.data.City;
import com.littlebuddha.bobogou.modules.entity.data.Province;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRole;
import com.littlebuddha.bobogou.modules.entity.system.Role;
import com.littlebuddha.bobogou.modules.service.common.DictDataService;
import com.littlebuddha.bobogou.modules.service.data.AreaService;
import com.littlebuddha.bobogou.modules.service.data.CityService;
import com.littlebuddha.bobogou.modules.service.data.ProvinceService;
import com.littlebuddha.bobogou.modules.service.other.CustomerUserService;
import com.littlebuddha.bobogou.modules.service.system.OperatorRoleService;
import com.littlebuddha.bobogou.modules.service.system.OperatorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/operator")
public class OperatorController extends BaseController {

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private OperatorRoleService operatorRoleService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private CustomerUserService customerUserService;

    @Autowired
    private DictDataService dictDataService;

    @ModelAttribute
    public Operator get(@RequestParam(required = false) String id) {
        Operator operator = null;
        if (StringUtils.isNotBlank(id)) {
            operator = operatorService.get(id);
            if (operator.getCityId() != null && StringUtils.isNotBlank(operator.getCityId())){
                City city = cityService.get(new City(operator.getCityId()));
                operator.setCity(city);
            }
            if (operator.getAreaId() != null && StringUtils.isNotBlank(operator.getAreaId())){
                Area area = areaService.get(new Area(operator.getAreaId()));
                operator.setArea(area);
            }
            if(operator != null && StringUtils.isNotBlank(operator.getParentId())){
                Operator select = new Operator();
                select.setId(operator.getParentId());
                Operator parent = operatorService.get(select);
                operator.setParent(parent);
            }
        }
        if (operator == null) {
            operator = new Operator();
        }
        return operator;
    }

    /**
     * ??????????????????
     *
     * @param operator
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/operator/list")
    @GetMapping(value = {"/", "/list"})
    public String list(Operator operator, Model model, HttpSession session) {
        model.addAttribute("operator", operator);
        return "modules/system/operator";
    }

    /**
     * ??????????????????
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Operator operator) {
        Role currentUserRole = UserUtils.getCurrentUserRole();
        operator.setCurrentUserRole(currentUserRole);
        PageInfo<Operator> page = operatorService.findPage(new Page<Operator>(), operator);
        return getLayUiData(page);
    }
    /**
     * ???????????????????????????----????????????????????????????????????????????????????????????????????????
     * @return
     */
    @ResponseBody
    @GetMapping("/dataList")
    public Result dataList(Operator operator) {
        Result result = new Result();
        Role currentUserRole = UserUtils.getCurrentUserRole();
        operator.setCurrentUserRole(currentUserRole);
        Operator list = operatorService.findNoPageList(operator);
        result.setCode("200");
        result.setData(list);
        return result;
    }

    /**
     * ????????????
     *
     * @param mode
     * @param operator
     * @param model
     * @return
     */
    @GetMapping("/form/{mode}")
    public String form(@PathVariable(name = "mode") String mode, Operator operator, Model model) {
        model.addAttribute("operator", operator);
        return "modules/system/operatorForm";
    }

    /**
     * ????????????????????????
     *
     * @param mode
     * @param operator
     * @param model
     * @return
     */
    @GetMapping("/basicInfo")
    public String basicInfo(Operator operator, Model model) {
        model.addAttribute("operator", operator);
        return "modules/system/basicInfoForm";
    }

    /**
     * ????????????????????????
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/goods/list")
    @GetMapping(value = {"/select"})
    public String select(Operator operator, Model model, HttpSession session) {
        model.addAttribute("operator", operator);
        return "modules/common/select/operator";
    }

    /**
     * ????????????
     *
     * @param
     * @param operator
     * @param model
     * @return
     */
    @GetMapping("/setting")
    public String setting(Operator operator, Model model) {
        model.addAttribute("operator", operator);
        return "modules/system/operatorSetting";
    }

    /**
     * ????????????
     *
     * @param operator
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Operator operator) {
        int save = operatorService.save(operator);
        if (save > 0) {
            return new Result("200", "????????????");
        } else {
            return new Result("310", "???????????????????????????");
        }
    }

    @GetMapping("/addRolePage")
    public String addRolePage(Operator operator, Model model) {
        List<OperatorRole> operatorRole = new ArrayList<>();
        if (operator != null && StringUtils.isNotBlank(operator.getId())) {
            operatorRole = operatorRoleService.findByOperatorAndRole(new OperatorRole(operator));
            String rolesId = "";
            for (OperatorRole entity : operatorRole) {
                if (entity != null && entity.getRole() != null && StringUtils.isNotBlank(entity.getRole().getId())) {
                    rolesId = entity.getRole().getId() + "," + rolesId;
                }
            }
            model.addAttribute("rolesId", rolesId);
        }
        model.addAttribute("operator", operator);
        return "modules/system/addRolePage";
    }

    @ResponseBody
    @PostMapping("/addRole")
    public Result addRole(Operator operator) {
        int row = operatorService.addRole(operator);
        return getCommonResult(row);
    }

    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String ids) {
        int fail = 0;
        String[] split = ids.split(",");
        for (String s : split) {
            Operator operator = operatorService.get(s);
            if (operator != null) {
                if (StringUtils.isNotBlank(operator.getId()) && "1".equals(operator.getId())) {
                    return new Result("555", "????????????????????????????????????");
                } else {
                    int i = operatorService.deleteByLogic(operator);
                }
            } else {
                fail = fail + 1;
            }
        }
        return new Result("200", "??????????????????");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        int fail = 0;
        String[] split = ids.split(",");
        for (String s : split) {
            Operator operator = operatorService.get(s);
            if (operator != null) {
                if (StringUtils.isNotBlank(operator.getId()) && "1".equals(operator.getId())) {
                    return new Result("555", "????????????????????????????????????");
                } else {
                    int i = operatorService.deleteByPhysics(operator);
                }
            } else if (s != null && StringUtils.isNotBlank(s)) {
                fail = fail + 1;
            }
        }
        return new Result("200", "??????????????????");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Operator operator, Model model) {
        model.addAttribute("operator", operator);
        return "modules/recovery/operatorRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Operator operator, Model model) {
        model.addAttribute("operator", operator);
        PageInfo<Operator> page = operatorService.findRecoveryPage(new Page<Operator>(), operator);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Operator operator) {
        int recovery = operatorService.recovery(operator);
        if (recovery > 0) {
            return new Result("200", "???????????????");
        } else {
            return new Result("322", "?????????????????????????????????");
        }
    }
}
