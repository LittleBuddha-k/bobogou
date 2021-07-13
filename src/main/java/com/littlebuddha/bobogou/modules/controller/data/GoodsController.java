package com.littlebuddha.bobogou.modules.controller.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.DateUtils;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;
import com.littlebuddha.bobogou.common.utils.excel.ExportExcel;
import com.littlebuddha.bobogou.common.utils.excel.ImportExcel;
import com.littlebuddha.bobogou.modules.base.controller.BaseController;
import com.littlebuddha.bobogou.modules.entity.basic.Factory;
import com.littlebuddha.bobogou.modules.entity.data.*;
import com.littlebuddha.bobogou.modules.service.basic.FactoryService;
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
@RequestMapping("/data/goods")
public class GoodsController extends BaseController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsTagService goodsTagService;

    @Autowired
    private GoodsBrandService goodsBrandService;

    @Autowired
    private ClassifyService classifyService;

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private GoodsClassifyService goodsClassifyService;

    @Autowired
    private RegionGoodsService regionGoodsService;

    @Autowired
    private GoodsSpecificationService goodsSpecificationService;

    @Autowired
    private FactoryService factoryService;

    @Autowired
    private GoodsTypeService goodsTypeService;

    @ModelAttribute
    public Goods get(@RequestParam(required = false) String id) {
        Goods goods = null;
        if (StringUtils.isNotBlank(id)) {
            goods = goodsService.get(new Goods(id));
            if (goods != null && goods.getBrandId()!= null && StringUtils.isNotBlank(goods.getBrandId().toString())){
                GoodsBrand fbSelect = new GoodsBrand();
                fbSelect.setId(goods.getBrandId().toString());
                GoodsBrand firstBrand = goodsBrandService.get(fbSelect);
                goods.setFirstBrand(firstBrand);
            }
            if (goods != null && goods.getSecondBrandId()!= null &&  StringUtils.isNotBlank(goods.getSecondBrandId().toString())){
                GoodsBrand sbSelect = new GoodsBrand();
                sbSelect.setId(goods.getSecondBrandId().toString());
                GoodsBrand secondBrand = goodsBrandService.get(sbSelect);
                goods.setSecondBrand(secondBrand);
            }
            if (goods != null && goods.getFactoryId()!= null &&  StringUtils.isNotBlank(goods.getFactoryId().toString())){
                Factory factory = factoryService.get(new Factory(goods.getFactoryId().toString()));
                goods.setFactory(factory);
            }
            if (goods != null){
                GoodsClassify goodsClassify = goodsClassifyService.get(new GoodsClassify(goods.getId()));
                goods.setGoodsClassify(goodsClassify);
            }
            if (goods != null){
                GoodsSpecification goodsSpecification = goodsSpecificationService.get(new GoodsSpecification(goods.getId().toString()));
                goods.setGoodsSpecification(goodsSpecification);
            }
            if (goods != null){
                GoodsInfo goodsInfo = goodsInfoService.get(new GoodsInfo(goods.getId().toString()));
                goods.setGoodsInfo(goodsInfo);
            }
        }
        if (goods == null) {
            goods = new Goods();
        }
        return goods;
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/goods/list")
    @GetMapping(value = {"/", "/list"})
    public String list(Goods goods, Model model, HttpSession session) {
        model.addAttribute("goods", goods);
        return "modules/data/goods";
    }

    /**
     * 返回药品列表
     *
     * @param
     * @param model
     * @param session
     * @return
     */
    //@RequiresPermissions("system/goods/list")
    @GetMapping(value = {"/select"})
    public String select(Goods goods, Model model, HttpSession session) {
        model.addAttribute("goods", goods);
        return "modules/common/select/goods";
    }

    /**
     * 返回数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public TreeResult data(Goods goods) {
        PageInfo<Goods> page = goodsService.findPage(new Page<Goods>(), goods);
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
    public String form(@PathVariable(name = "mode") String mode, Goods goods, Model model) {
        //查询所有标签
        List<GoodsTag> commodityTagList = goodsTagService.findList(new GoodsTag());
        model.addAttribute("commodityTagList", commodityTagList);
        //查询品牌一级分类
        GoodsBrand entity = new GoodsBrand();
        entity.setParentId("0");
        List<GoodsBrand> goodsBrandList = goodsBrandService.findList(entity);
        model.addAttribute("goodsBrandList", goodsBrandList);
        //查询所有厂商列表
        List<Factory> factoryList = factoryService.findList(new Factory());
        model.addAttribute("factoryList", factoryList);
        //查询商品分类数据：分一级、二级、三级
        List<Classify> goodsTypeLevelOne = classifyService.findList(new Classify(1));//查询一级商品分类
        model.addAttribute("goodsTypeLevelOne", goodsTypeLevelOne);
        //查询所有其他分类---GoodsType数据
        List<GoodsType> goodsTypeList = goodsTypeService.findList(new GoodsType());
        model.addAttribute("goodsTypeList", goodsTypeList);
        //查询当前商品的商品分类详情
        if (goods != null && StringUtils.isNotBlank(goods.getId())){
            GoodsClassify select = new GoodsClassify();
            select.setGoodsId(goods.getId());
            GoodsClassify goodsClassify = goodsClassifyService.getByGoods(select);
            goods.setGoodsClassify(goodsClassify);
        }
        //查询商品详情
        if (goods != null && StringUtils.isNotBlank(goods.getId())){
            GoodsInfo select = new GoodsInfo();
            select.setGoodsId(goods.getId());
            GoodsInfo goodsInfo = goodsInfoService.getByGoods(new GoodsInfo(goods));
            goods.setGoodsInfo(goodsInfo);
        }
        //查询商品规格
        if (goods != null && StringUtils.isNotBlank(goods.getId())){
            GoodsSpecification select = new GoodsSpecification();
            select.setId(goods.getId());
            GoodsSpecification goodsSpecification = goodsSpecificationService.get(select);
            goods.setGoodsSpecification(goodsSpecification);
        }
        return "modules/data/goodsForm";
    }

    /**
     * 数据保存
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result save(Goods goods) {
        int save = goodsService.save(goods);
        if (save > 0) {
            return new Result("200", "保存成功");
        } else {
            return new Result("310", "未知错误！保存失败");
        }
    }

    /**
     * 进行上架or下架操作
     * @return
     */
    @ResponseBody
    @PostMapping("/onTheShelf")
    public Result onTheShelf(Goods goods){
        Result result = new Result();
        if (goods != null && StringUtils.isNotBlank(goods.getId())){
            int row = goodsService.onTheShelf(goods);
            //修改区域商品上下架
            RegionGoods regionGoods = new RegionGoods();
            if (goods.getIsMarket() == 0){
                //在售
                regionGoods.setIsMarket(0);
                regionGoods.setGoodsId(goods.getId());
            }else if (goods.getIsMarket() == 2){
                //停售
                regionGoods.setIsMarket(1);
                regionGoods.setGoodsId(goods.getId());
            }
            regionGoodsService.updateIsMarket(regionGoods);
            result = getCommonResult(row);
        }else {
            result.setCode("222");
            result.setMsg("未知原因，操作失败");
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/importTemplate")
    public Result importTemplate(HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "药品模板.xlsx";
            List<Goods> list = Lists.newArrayList();
            new ExportExcel("药品数据", Goods.class, 1).setDataList(list).write(response, fileName).dispose();
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
            List<Goods> list = ei.getDataList(Goods.class);
            for (Goods mayApplyCost : list) {
                try {
                    goodsService.save(mayApplyCost);
                    successNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条药品记录。");
            }
            result.setSuccess(true);
            result.setMsg("已成功导入 " + successNum + " 条药品记录" + failureMsg);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("导入药品失败！失败信息：" + e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @GetMapping("/exportFile")
    public Result exportFile(Goods goods, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String fileName = "药品" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<Goods> list = goodsService.findList(goods);
            if (list != null & list.size() > 0) {
                new ExportExcel("药品", Goods.class).setDataList(list).write(response, fileName).dispose();
            } else {
                new ExportExcel("药品", Goods.class).setDataList(new ArrayList<>()).write(response, fileName).dispose();
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
            Goods goods = goodsService.get(s);
            if (goods == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = goodsService.deleteByLogic(goods);
        }
        return new Result("200", "数据清除成功");
    }

    @ResponseBody
    @PostMapping("/deleteByPhysics")
    public Result deleteByPhysics(String ids) {
        String[] split = ids.split(",");
        for (String s : split) {
            Goods goods = goodsService.get(s);
            if (goods == null) {
                return new Result("311", "数据不存在,或已被删除，请刷新试试！");
            }
            int i = goodsService.deleteByPhysics(goods);
        }
        return new Result("200", "数据清除成功");
    }

    @GetMapping("/recoveryList")
    public String recoveryList(Goods goods, Model model) {
        model.addAttribute("goods", goods);
        return "modules/recovery/goodsRecovery";
    }

    @ResponseBody
    @PostMapping("/recoveryData")
    public Map recoveryData(Goods goods, Model model) {
        model.addAttribute("goods", goods);
        PageInfo<Goods> page = goodsService.findRecoveryPage(new Page<Goods>(), goods);
        return getBootstrapData(page);
    }

    @ResponseBody
    @PostMapping("/recovery")
    public Result recovery(Goods goods) {
        int recovery = goodsService.recovery(goods);
        if (recovery > 0) {
            return new Result("200", "数据已恢复");
        } else {
            return new Result("322", "未知错误，数据恢复失败");
        }
    }
}
