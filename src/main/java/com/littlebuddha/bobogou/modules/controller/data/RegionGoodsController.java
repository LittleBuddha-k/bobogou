package com.littlebuddha.bobogou.modules.controller.data;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.common.DictData;
import com.littlebuddha.bobogou.modules.entity.data.*;
import com.littlebuddha.bobogou.modules.entity.data.utils.AreaSelect;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.Role;
import com.littlebuddha.bobogou.modules.service.common.DictDataService;
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

    @Autowired
    private DictDataService dictDataService;

    @ModelAttribute
    public RegionGoods get(@RequestParam(required = false) String id) {
        RegionGoods regionGoods = null;
        if (StringUtils.isNotBlank(id)) {
            regionGoods = regionGoodsService.get(new RegionGoods(id));
        }
        if (regionGoods == null) {
            regionGoods = new RegionGoods();
        }
        return regionGoods;
    }

    /**
     * ??????????????????
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/RegionGoods/list")
    @GetMapping(value = {"/", "/list"})
    public String list(RegionGoods regionGoods, Model model, HttpSession session) {
        //??????????????????
        List<Province> provinceList = provinceService.findList(new Province());
        model.addAttribute("provinceList", provinceList);
        model.addAttribute("regionGoods", regionGoods);
        return "modules/data/regionGoods";
    }

    /**
     * ????????????
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(RegionGoods regionGoods) {
        Operator currentUser = UserUtils.getCurrentUser();
        Role currentUserRole = UserUtils.getCurrentUserRole();
        regionGoods.setCurrentUser(currentUser);
        regionGoods.setCurrentUserRole(currentUserRole);
        PageInfo<RegionGoods> page = regionGoodsService.findPage(new Page<RegionGoods>(), regionGoods);
        return getLayUiData(page);
    }

    /**
     * ????????????
     *
     * @param mode
     * @param
     * @param model
     * @return
     */
    @GetMapping("/form/{mode}")
    public String form(@PathVariable(name = "mode") String mode, RegionGoods regionGoods, Model model) {
        model.addAttribute("regionGoods", regionGoods);
        return "modules/data/regionGoodsForm";
    }

    /**
     * ??????????????????????????????json?????????
     *
     * @param
     * @return
     */
    @ResponseBody
    @GetMapping("/regionGoodsInfoList")
    public List<RegionGoods> regionGoodsList(String regionGoodsId) {
        List<RegionGoods> list = new ArrayList<>();
        if (StringUtils.isNotBlank(regionGoodsId)) {
            //????????????????????????
            RegionGoods regionGoods = regionGoodsService.get(new RegionGoods(regionGoodsId));
            if (regionGoods != null && StringUtils.isNotBlank(regionGoods.getGoodsId())) {
                //??????????????????????????????????????????????????????????????????
                RegionGoods selectOption = new RegionGoods();
                if (regionGoods != null) {
                    selectOption.setProvinceId(regionGoods.getProvinceId());
                    selectOption.setCityId(regionGoods.getCityId());
                    selectOption.setDistrictId(regionGoods.getDistrictId());
                    selectOption.setStreetId(regionGoods.getStreetId());
                }
                //????????????????????????????????????
                list = regionGoodsService.findList(selectOption);
            }
        }
        return list;
    }

    /**
     * ????????????
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(RegionGoods regionGoods) {
        int save = regionGoodsService.save(regionGoods);
        if (save > 0) {
            return new Result("200", "????????????");
        } else if (save == -1) {
            return new Result("222", "??????????????????????????????????????????????????????");
        } else {
            return new Result("310", "???????????????????????????");
        }
    }

    /**
     * ????????????
     * @param id ??????????????????id
     * @param data  List<AreaSelect>??????????????????
     * @return
     */
    @ResponseBody
    @PostMapping("/setArea/{id}")
    public Result setArea(@PathVariable("id")String id, @RequestParam(value = "data",required = false) String data) {
        List<AreaSelect> areaSelects = JSONArray.parseArray(data, AreaSelect.class);
        return null;
    }

    @ResponseBody
    @GetMapping("/importTemplate")
    public Result importTemplate(HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "??????????????????.xlsx";
            List<RegionGoods> list = Lists.newArrayList();
            new ExportExcel("??????????????????", RegionGoods.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            result.setSuccess(true);
            result.setMsg("??????????????????????????????????????????" + e.getMessage());
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
                failureMsg.insert(0, "????????? " + failureNum + " ????????????????????????");
            }
            result.setSuccess(true);
            result.setMsg("??????????????? " + successNum + " ?????????????????????" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("??????????????????????????????????????????" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(RegionGoods regionGoods, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "????????????" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<RegionGoods> list = regionGoodsService.findList(regionGoods);
            if (list != null & list.size() > 0) {
                new ExportExcel("????????????", RegionGoods.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("????????????", RegionGoods.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
            }
            return null;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("??????????????????????????????????????????" + e.getMessage());
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
                return new Result("311", "???????????????,????????????????????????????????????");
            }
            int i = regionGoodsService.deleteByLogic(regionGoods);
        }
        return new Result("200", "??????????????????");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            RegionGoods regionGoods = regionGoodsService.get(s);
            if (regionGoods == null) {
                return new Result("311", "???????????????,????????????????????????????????????");
            }
            int i = regionGoodsService.deleteByPhysics(regionGoods);
        }
        return new Result("200", "??????????????????");
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
            return new Result("200", "???????????????");
        } else {
            return new Result("322", "?????????????????????????????????");
        }
    }
}
