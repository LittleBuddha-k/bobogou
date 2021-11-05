package com.littlebuddha.bobogou.modules.controller.basic;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.basic.RoyaltyRecord;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;
import com.littlebuddha.bobogou.modules.mapper.other.CustomerUserMapper;
import com.littlebuddha.bobogou.modules.service.basic.RoyaltyRecordService;
import com.littlebuddha.bobogou.modules.service.other.CustomerUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 厂商controller
 */
@Controller
@RequestMapping("/basic/royaltyRecord")
public class RoyaltyRecordController extends BaseController {

    @Autowired
    private RoyaltyRecordService royaltyRecordService;

    @Autowired
    private CustomerUserService customerUserService;

    @ModelAttribute
    public RoyaltyRecord get(@RequestParam(required = false) String id) {
        RoyaltyRecord royaltyRecord = null;
        if (StringUtils.isNotBlank(id)) {
            royaltyRecord = royaltyRecordService.get(new RoyaltyRecord(id));
        }
        if (royaltyRecord == null) {
            royaltyRecord = new RoyaltyRecord();
        }
        return royaltyRecord;
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
    public String list(RoyaltyRecord royaltyRecord, Model model, HttpSession session) {
        //查询所有用户列表，前端作下拉显示
        List<CustomerUser> customerUserList = customerUserService.findCurrentUserSubordinateArea(new CustomerUser());
        model.addAttribute("customerUserList", customerUserList);
        model.addAttribute("royaltyRecord", royaltyRecord);
        return "modules/basic/royaltyRecord";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(RoyaltyRecord royaltyRecord) {
        PageInfo<RoyaltyRecord> page1 = royaltyRecordService.findPage(new Page<RoyaltyRecord>(), royaltyRecord);
        return getLayUiData(page1);
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
    public String form(@PathVariable(name = "mode") String mode, RoyaltyRecord royaltyRecord, Model model) {
        model.addAttribute("royaltyRecord", royaltyRecord);
        return "modules/basic/royaltyRecordForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(RoyaltyRecord royaltyRecord) {
        int save = royaltyRecordService.save(royaltyRecord);
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
            RoyaltyRecord royaltyRecord = royaltyRecordService.get(s);
            if (royaltyRecord == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = royaltyRecordService.deleteByLogic(royaltyRecord);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            RoyaltyRecord royaltyRecord = royaltyRecordService.get(s);
            if (royaltyRecord == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = royaltyRecordService.deleteByPhysics(royaltyRecord);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(RoyaltyRecord royaltyRecord, Model model) {
        model.addAttribute("royaltyRecord", royaltyRecord);
        return "modules/recovery/royaltyRecordRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(RoyaltyRecord royaltyRecord, Model model) {
        model.addAttribute("royaltyRecord", royaltyRecord);
        PageInfo<RoyaltyRecord> page = royaltyRecordService.findRecoveryPage(new Page<RoyaltyRecord>(), royaltyRecord);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(RoyaltyRecord royaltyRecord) {
        int recovery = royaltyRecordService.recovery(royaltyRecord);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
