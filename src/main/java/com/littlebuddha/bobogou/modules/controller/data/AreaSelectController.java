package com.littlebuddha.bobogou.modules.controller.data;

import com.littlebuddha.bobogou.modules.entity.data.Province;
import com.littlebuddha.bobogou.modules.entity.data.utils.AreaSelect;
import com.littlebuddha.bobogou.modules.service.data.AreaSelectService;
import com.littlebuddha.bobogou.modules.service.data.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域选择包含省市区街道的返回树形数据控制器
 */
@Controller
@RequestMapping("/data/areaSelect")
public class AreaSelectController {

    @Autowired
    private AreaSelectService areaSelectService;

    /**
     * 返回选择页面
     *
     * @return
     */
    @GetMapping("/list")
    public String list() {
        return "modules/common/select/areaSelect";
    }

    /**
     * 返回树形数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/data")
    public List<AreaSelect> data() {
        List<AreaSelect> result = areaSelectService.findCurrentUserAreas();
        return result;
    }
}
