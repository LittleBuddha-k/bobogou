package com.littlebuddha.bobogou.modules.controller.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.data.*;
import com.littlebuddha.bobogou.modules.service.data.*;
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
@RequestMapping("/data/regionGoods")
public class RegionGoodsController extends BaseController {

    @Autowired
    private RegionGoodsService regionGoodsService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private StreetService streetService;

    @Autowired
    private GoodsService medicineService;

    @ModelAttribute
    public RegionGoods get(@RequestParam(required = false) String id) {
        RegionGoods regionGoods = null;
        if (StringUtils.isNotBlank(id)) {
            regionGoods = regionGoodsService.get(id);
        }
        if (regionGoods == null) {
            regionGoods = new RegionGoods();
        }
        return regionGoods;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/RegionGoods/list")
    @GetMapping(value = {"/", "/list"})
    public String list(RegionGoods regionGoods, Model model, HttpSession session) {
        //查询省级数据
        List<Province> provinceList = provinceService.findList(new Province());
        model.addAttribute("provinceList", provinceList);
        /*//查询省级数据
        List<City> cityList = cityService.findList(new City());
        model.addAttribute("cityList", cityList);
        //查询省级数据
        List<Area> areaList = areaService.findList(new Area());
        model.addAttribute("areaList", areaList);
        //查询省级数据
        List<Street> streetList = streetService.findList(new Street());
        model.addAttribute("streetList", streetList);*/
        model.addAttribute("regionGoods", regionGoods);
        return "modules/data/regionGoods";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(RegionGoods regionGoods) {
        PageInfo<RegionGoods> page = regionGoodsService.findPage(new Page<RegionGoods>(), regionGoods);
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
    public String form(@PathVariable(name = "mode") String mode, RegionGoods regionGoods, Model model) {
        //查询省级数据---前端做地域级联动
        List<Province> provinceList = provinceService.findList(new Province());
        model.addAttribute("provinceList", provinceList);
        if (regionGoods != null && StringUtils.isNotBlank(regionGoods.getGoodsId())) {
            regionGoods.setMedicine(medicineService.get(new Goods(regionGoods.getGoodsId())));
        }
        model.addAttribute("regionGoods", regionGoods);
        return "modules/data/regionGoodsForm";
    }

    /**
     * 返回商品区域信息列表json字符串
     *
     * @param
     * @return
     */
    @ResponseBody
    @GetMapping("/regionGoodsInfoList")
    public List<RegionGoods> regionGoodsList(String regionGoodsId) {
        List<RegionGoods> list = new ArrayList<>();
        if (StringUtils.isNotBlank(regionGoodsId)) {
            //获取商品区域信息
            RegionGoods regionGoods = regionGoodsService.get(new RegionGoods(regionGoodsId));
            if (regionGoods != null && StringUtils.isNotBlank(regionGoods.getGoodsId())) {
                //根据商品信息中的省、市、区、街道设置查询信息
                RegionGoods selectOption = new RegionGoods();
                if (regionGoods != null) {
                    selectOption.setProvinceId(regionGoods.getProvinceId());
                    selectOption.setCityId(regionGoods.getCityId());
                    selectOption.setDistrictId(regionGoods.getDistrictId());
                    selectOption.setStreetId(regionGoods.getStreetId());
                }
                //根据查询信息获取查询结果
                list = regionGoodsService.findList(selectOption);
            }
        }
        return list;
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(RegionGoods regionGoods) {
        int save = regionGoodsService.save(regionGoods);
        if (save > 0) {
            return new Result("200", "保存成功");
        } else if (save == -1) {
            return new Result("222", "分配数量已超过商品本身库存数，请确认");
        } else {
            return new Result("310", "未知错误！保存失败");
        }
    }

    @ResponseBody
    @GetMapping("/importTemplate")
    public Result importTemplate(HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "地域商品模板.xlsx";
            List<RegionGoods> list = Lists.newArrayList();
            new ExportExcel("地域商品数据", RegionGoods.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<RegionGoods> list = ei.getDataList(RegionGoods.class);
            for (RegionGoods regionGoods : list) {
                try {
                    regionGoodsService.save(regionGoods);
                    successNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条地域商品记录。");
            }
            result.setSuccess(true);
            result.setMsg("已成功导入 " + successNum + " 条地域商品记录" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导入地域商品失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(RegionGoods regionGoods, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "地域商品" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<RegionGoods> list = regionGoodsService.findList(regionGoods);
            if (list != null & list.size() > 0) {
                new ExportExcel("地域商品", RegionGoods.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("地域商品", RegionGoods.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
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
            RegionGoods regionGoods = regionGoodsService.get(s);
            if (regionGoods == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = regionGoodsService.deleteByLogic(regionGoods);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            RegionGoods regionGoods = regionGoodsService.get(s);
            if (regionGoods == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = regionGoodsService.deleteByPhysics(regionGoods);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(RegionGoods RegionGoods, Model model) {
        model.addAttribute("RegionGoods", RegionGoods);
        return "modules/recovery/RegionGoodsRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(RegionGoods regionGoods, Model model) {
        model.addAttribute("regionGoods", regionGoods);
        PageInfo<RegionGoods> page = regionGoodsService.findRecoveryPage(new Page<RegionGoods>(), regionGoods);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(RegionGoods regionGoods) {
        int recovery = regionGoodsService.recovery(regionGoods);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
