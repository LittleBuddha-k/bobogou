layui.use(['upload', 'element', 'form', 'layedit', 'laydate'], function () {
    var $ = layui.jquery
        , form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        , upload = layui.upload
        , element = layui.element;

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

    form.on('select(province)', function (data) {
        let provinceId = data.value;
        $("#city").empty();//清空城市选项
        $("#area").empty();//清空城市选项
        $("#street").val("");//清空城市选项
        $("#street").empty();//清空城市选项
        $("#streetId").val("");//清空城市选项
        rc.post("/bobogou/data/city/all", {"province.id": provinceId}, function (data) {
            if (data.length > 0) {
                //对应的值传回，拼出html下拉框语句
                var tmp = '<option value="0">请选择</option>';
                for (let i = 0; i < data.length; i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#city").html(tmp);
                $("#area").html(tmp);
                $("#street").html(tmp);
                form.render();
            }
        })
    });
    //下拉框选中后的时间
    form.on('select(city)', function (data) {
        let cityId = data.value;
        $("#area").empty();//清空城市选项
        $("#street").val("");//清空城市选项
        $("#street").empty();//清空城市选项
        $("#streetId").val("");//清空城市选项
        rc.post("/bobogou/data/area/all", {"city.id": cityId}, function (data) {
            if (data.length > 0) {
                //对应的值传回，拼出html下拉框语句
                var tmp = '<option value="0">请选择</option>';
                for (let i = 0; i < data.length; i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#area").html(tmp);
                $("#street").html(tmp);
                form.render();
            }
        })
    });
    //下拉框选中后的时间
    form.on('select(area)', function (data) {
        let areaId = data.value;
        $("#street").val("0");//清空城市选项
        $("#street").empty();//清空城市选项
        $("#streetId").val("");//清空城市选项
        rc.post("/bobogou/data/street/all", {"area.id": areaId}, function (data) {
            if (data.length > 0) {
                //对应的值传回，拼出html下拉框语句
                var tmp = '<option value="0">请选择</option>';
                for (let i = 0; i < data.length; i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#street").html(tmp);
                form.render();
            }
        })
    });
})

//保存方法
function save(parentIndex) {
    let isValidate = rc.validateForm('#operatorRegionForm');//校验表单
    if (!isValidate) {
        return false;
    } else {
        //提交前设置disabled失效
        $("#province").removeAttr("disabled");
        $("#city").removeAttr("disabled");
        $("#area").removeAttr("disabled");
        $("#street").removeAttr("disabled");
        let flag = false;
        let type = $("#type").val();
        let province = $("#province").val();
        let city = $("#city").val();
        let area = $("#area").val();
        let street = $("#street").val();
        if (type == 0){
            //超级管理员助理
            rc.error("请选择类型")
        }else if (type == 1){
            //超级管理员助理
            if (province != null && province != '' && province != undefined && province != 0){
                flag = true;
            }else if (province == null || province == '' || province == undefined || province == 0){
                rc.error("请选择省级")
            }
        }else if (type == 2){
            //省级
            if (city != null && city != '' && city != undefined && city != 0){
                flag = true;
            }else if (city == null || city == '' || city == undefined || city == 0) {
                rc.error("请选择市级")
            }
        }else if (type == 3){
            //市级
            if (area != null && area != '' && area != undefined && area != 0){
                flag = true;
            }else if (area == null || area == '' || area == undefined || area == 0) {
                rc.error("请选择区级")
            }
        }else if (type == 4){
            //区级
            if (street != null && street != '' && street != undefined && street != 0){
                flag = true;
            }else if (street == null || street == '' || street == undefined || street == 0) {
                rc.error("请选择街道级")
            }
        }
        if (flag){
            $.ajax({
                url: "/bobogou/system/operatorRegion/save",    //请求的url地址
                dataType: "json",   //返回格式为json
                async: true,//请求是否异步，默认为异步，这也是ajax重要特性
                data: $("#operatorRegionForm").serialize(),    //参数值
                type: "POST",   //请求方式
                success: function (result) {
                    if(200 == result.code){
                        parent.refresh();
                        //当你在iframe页面关闭自身时
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        rc.msg(result.msg)
                        setTimeout(function(){
                            parent.layer.close(index); //再执行关闭
                        }, 500);
                    }else {
                        rc.msg(result.msg)
                    }
                },
                error: function (result) {
                    rc.alert(result.msg)
                }
            });
        }
    }
}

function selectOperator(id) {
    let openSelector = rc.openUserSelect("/bobogou/system/operator/select/", "选择用户", '90%', '90%',id);
}

function selectStreet(id) {
    let area = $("#area").val();
    if (area != null && area != '' && area != undefined){
        let openSelector = rc.openAreaSelect("/bobogou/data/street/select?area=" + area, "选择商品", '90%', '90%',id);
    }else {
        rc.error("请先选择区")
    }
}