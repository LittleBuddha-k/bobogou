layui.use('form', function(){
    var form = layui.form;
    //各种基于事件的操作，下面会有进一步介绍

    //根据类型不同禁用对应选项
    form.on('select(type)', function (data) {
        let type = data.value;
        if (type == 0){
            $("#province").attr("disabled","disabled");
            $("#city").attr("disabled","disabled");
            $("#area").attr("disabled","disabled");
            $("#street").attr("disabled","disabled");
            form.render();
        }else if (type == 1){
            $("#province").removeAttr("disabled");
            $("#city").attr("disabled","disabled");
            $("#area").attr("disabled","disabled");
            $("#street").attr("disabled","disabled");
            form.render();
        }else if (type == 2){
            $("#province").removeAttr("disabled");
            $("#city").removeAttr("disabled");
            $("#area").attr("disabled","disabled");
            $("#street").attr("disabled","disabled");
            form.render();
        }else if (type == 3){
            $("#province").removeAttr("disabled");
            $("#city").removeAttr("disabled");
            $("#area").removeAttr("disabled");
            $("#street").attr("disabled","disabled");
            form.render();
        }else if (type == 4){
            $("#province").removeAttr("disabled");
            $("#city").removeAttr("disabled");
            $("#area").removeAttr("disabled");
            $("#street").removeAttr("disabled");
            form.render();
        }
    });

    //下拉框选中后的时间
    form.on('select(province)', function(data){
        let provinceId = data.value;
        $("#city").empty();//清空城市选项
        $("#area").empty();//清空城市选项
        $("#street").val("");//清空城市选项
        $("#streetId").val("");//清空城市选项
        rc.post("/bobogou/data/city/all",{"province.id":provinceId},function(data){
            if(data.length>0) {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="0">请选择</option>';
                for(let i=0;i<data.length;i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#city").html(tmp);
                form.render();
            }
        })
    });
    //下拉框选中后的时间
    form.on('select(city)', function(data){
        let cityId = data.value;
        $("#area").empty();//清空城市选项
        $("#street").val("");//清空城市选项
        $("#streetId").val("");//清空城市选项
        rc.post("/bobogou/data/area/all",{"city.id":cityId},function(data){
            if(data.length>0) {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="0">请选择</option>';
                for(let i=0;i<data.length;i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#area").html(tmp);
                form.render();
            }
        })
    });
    //下拉框选中后的时间
    form.on('select(area)', function(data){
        let streetId = data.value;
        $("#street").val("");//清空城市选项
        $("#streetId").val("");//清空城市选项
        rc.post("/bobogou/data/street/all",{"area.id":streetId},function(data){
            if(data.length>0) {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="0">请选择</option>';
                for(let i=0;i<data.length;i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#street").html(tmp);
                form.render();
            }
        })
    });
});

$(document).ready(function () {
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;
        let idsSelections;
        $("#goods").click(function () {
            let openSelector = rc.openSelector("/bobogou/data/medicine/select","选择商品",'85%','80%');
        })
    })
})

//保存方法
function save(parentIndex) {
    var isValidate = rc.validateForm('#regionGoodsForm');//校验表单
    if(!isValidate){
        return false;
    }else {
        $.ajax({
            url: "/bobogou/data/regionGoods/save",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: $("#regionGoodsForm").serialize(),    //参数值
            type: "POST",   //请求方式
            success: function (result) {
                if (200 == result.code){
                    //请求成功时处理
                    // 刷新整个父窗口
                    parent.refresh();
                    //假设这是iframe页
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    rc.msg(result.msg)
                    setTimeout(function(){
                        parent.layer.close(index); //再执行关闭
                    }, 1000);
                }else {
                    rc.alert(result.msg)
                }
            },
            error: function (result) {
                rc.alert(result.msg)
            }
        });
    }
}

function selectGoods(id) {
    let openSelector = rc.openGoodsSelect("/bobogou/data/goods/select/", "选择商品", '95%', '88%',id);
}

function selectStreet(id) {
    let area = $("#area").val();
    if (area != null && area != '' && area != undefined){
        let openSelector = rc.openAreaSelect("/bobogou/data/street/select?area=" + area, "选择商品", '90%', '90%',id);
    }else {
        rc.error("请先选择区")
    }
}