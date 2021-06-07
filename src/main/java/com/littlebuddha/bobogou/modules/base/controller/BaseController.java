package com.littlebuddha.bobogou.modules.base.controller;

import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.TreeResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ck
 * @date 2021/2/24 14:43
 */
public abstract class BaseController {

    /*public<T> JsonResult getLayUiData(PageInfo pageInfo){
        List data = pageInfo.getList();
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(0);
        jsonResult.setMsg("成功");
        jsonResult.setData(data);
        int pages = pageInfo.getPages();
        int pageSize = pageInfo.getPageSize();
        BigDecimal data1 = new BigDecimal(pages);
        BigDecimal data2 = new BigDecimal(pageSize);
        jsonResult.setCount((data1.multiply(data2)).intValue());
        return jsonResult;
    }*/

    /**
     * 获取bootstrap data分页数据
     * @param
     * @return map对象
     */
    public <E> Map<String, Object> getBootstrapData(PageInfo<E> pageInfo){
        Map<String, Object> map = new HashMap<String, Object>();
        List<E> list = pageInfo.getList();
        map.put("rows", list);
        map.put("total", pageInfo.getTotal());
        return map;
    }

    /**
     * 根据sql操作的返回值，返回result
     * @param row
     * @return
     */
    public Result getCommonResult(int row){
        Result result = new Result();
        if (row > 0){
            result = new Result<>("200", "操作成功");
        }else {
            result = new Result<>("333", "未知错误，数据操作失败");
        }
        return result;
    }

    public TreeResult getLayUiData(PageInfo pageInfo){
        TreeResult result = null;
        List list = pageInfo.getList();
        if (list == null || list.size() <= 0){
            result = new TreeResult(0,"无数据");
        }else if(list != null || pageInfo.getTotal() > 0){
            result = new TreeResult(0,"无数据",list, (int) pageInfo.getTotal());
        }
        return result;
    }
}
