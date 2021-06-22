package com.littlebuddha.bobogou.modules.controller.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.data.GoodsExchange;
import com.littlebuddha.bobogou.modules.entity.data.City;
import com.littlebuddha.bobogou.modules.service.data.GoodsExchangeService;
import com.littlebuddha.bobogou.modules.service.data.CityService;
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
@RequestMapping("/data/goodsExchange")
public class GoodsExchangeController extends BaseController {
    
    @Autowired
    private GoodsExchangeService goodsExchangeService;
    
    @ModelAttribute
    public GoodsExchange get(@RequestParam(required = false) String id) {
        GoodsExchange goodsExchange = null;
        if (StringUtils.isNotBlank(id)) {
            goodsExchange = goodsExchangeService.get(id);
        }
        if (goodsExchange == null) {
            goodsExchange = new GoodsExchange();
        }
        return goodsExchange;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/GoodsExchange/list")
    @GetMapping(value = {"/", "/list"})
    public String list(GoodsExchange goodsExchange, Model model, HttpSession session) {
        model.addAttribute("goodsExchange", goodsExchange);
        return "modules/data/goodsExchange";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(GoodsExchange goodsExchange) {
        PageInfo<GoodsExchange> page = goodsExchangeService.findPage(new Page<GoodsExchange>(), goodsExchange);
        return getLayUiData(page);
    }

    /**
     * 返回所有数据
     * @return
     */
    @ResponseBody
    @PostMapping("/all")
    public List<GoodsExchange> all(GoodsExchange entity) {
        List<GoodsExchange> list = goodsExchangeService.findList(entity);
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
    public String form(@PathVariable(name = "mode") String mode, GoodsExchange goodsExchange, Model model) {
        model.addAttribute("goodsExchange", goodsExchange);
        return "modules/data/goodsExchangeForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(GoodsExchange goodsExchange) {
        int save = goodsExchangeService.save(goodsExchange);
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
            String fileName = "兑付商品管理模板.xlsx";
            List<GoodsExchange> list = Lists.newArrayList();
            new ExportExcel("兑付商品管理数据", GoodsExchange.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<GoodsExchange> list = ei.getDataList(GoodsExchange.class);
            for (GoodsExchange goodsExchange : list) {
                try {
                    goodsExchangeService.save(goodsExchange);
                    successNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条兑付商品管理记录。");
            }
            result.setSuccess(true);
            result.setMsg("已成功导入 " + successNum + " 条兑付商品管理记录" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导入兑付商品管理失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(GoodsExchange goodsExchange, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "兑付商品管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<GoodsExchange> list = goodsExchangeService.findList(goodsExchange);
            if (list != null & list.size() > 0) {
                new ExportExcel("兑付商品管理", GoodsExchange.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("兑付商品管理", GoodsExchange.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
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
            GoodsExchange goodsExchange = goodsExchangeService.get(s);
            if (goodsExchange == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = goodsExchangeService.deleteByLogic(goodsExchange);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            GoodsExchange goodsExchange = goodsExchangeService.get(s);
            if (goodsExchange == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = goodsExchangeService.deleteByPhysics(goodsExchange);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(GoodsExchange goodsExchange, Model model) {
        model.addAttribute("goodsExchange", goodsExchange);
        return "modules/recovery/goodsExchangeRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(GoodsExchange goodsExchange, Model model) {
        model.addAttribute("goodsExchange", goodsExchange);
        PageInfo<GoodsExchange> page = goodsExchangeService.findRecoveryPage(new Page<GoodsExchange>(), goodsExchange);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(GoodsExchange goodsExchange) {
        int recovery = goodsExchangeService.recovery(goodsExchange);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
