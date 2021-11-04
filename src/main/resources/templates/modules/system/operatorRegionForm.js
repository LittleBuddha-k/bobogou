layui.use(['upload', 'element', 'form', 'layedit', 'laydate'], function () {
    var $ = layui.jquery
        , form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        , upload = layui.upload
        , element = layui.element;

    //初始化情况下只有省能选择
    let type = $("#type").val();
    if (0 == type) {
        $("#provinceId").removeAttr("onclick");
        $("#cityId").removeAttr("onclick");
        $("#districtId").removeAttr("onclick");
        $("#streetId").removeAttr("onclick");
    }else if (1 == type){
        $("#cityId").removeAttr("onclick");
        $("#districtId").removeAttr("onclick");
        $("#streetId").removeAttr("onclick");
    }else if (2 == type){
        $("#cityId").removeAttr("onclick");
        $("#cityId").attr("onclick","selectCity('cityId');");
        $("#districtId").removeAttr("onclick");
        $("#streetId").removeAttr("onclick");
    }else if (3 == type){
        $("#cityId").removeAttr("onclick");
        $("#cityId").attr("onclick","selectCity('cityId');");
        $("#districtId").removeAttr("onclick");
        $("#districtId").attr("onclick","selectArea('districtId');");
        $("#streetId").removeAttr("onclick");
    }else if (4 == type){
        $("#cityId").removeAttr("onclick");
        $("#cityId").attr("onclick","selectCity('cityId');");
        $("#districtId").removeAttr("onclick");
        $("#districtId").attr("onclick","selectArea('districtId');");
        $("#streetId").removeAttr("onclick");
        $("#streetId").attr("onclick","selectStreet('streetId');");
    }

    //根据类型不同禁用对应选项
    form.on('select(type)', function (data) {
        let type = data.value;
        if (type == 0){
            $("#provinceId").removeAttr("onclick");
            $("#provinceId").val("");
            $("#province").val("0");
            $("#cityId").removeAttr("onclick");
            $("#cityId").val("");
            $("#city").val("0");
            $("#districtId").removeAttr("onclick");
            $("#districtId").val("");
            $("#district").val("0");
            $("#streetId").removeAttr("onclick");
            $("#streetId").val("");
            $("#street").val("0");
            form.render();
        }else if (type == 1){
            $("#provinceId").removeAttr("onclick");
            $("#provinceId").attr("onclick","selectProvince('provinceId');");
            $("#cityId").removeAttr("onclick");
            $("#cityId").val("");
            $("#city").val("0");
            $("#districtId").removeAttr("onclick");
            $("#districtId").val("");
            $("#district").val("0");
            $("#streetId").removeAttr("onclick");
            $("#streetId").val("");
            $("#street").val("0");
            form.render();
        }else if (type == 2){
            $("#provinceId").removeAttr("onclick");
            $("#provinceId").attr("onclick","selectProvince('provinceId');");
            $("#cityId").removeAttr("onclick");
            $("#cityId").attr("onclick","selectCity('cityId');");
            $("#districtId").removeAttr("onclick");
            $("#districtId").val("");
            $("#district").val("0");
            $("#streetId").removeAttr("onclick");
            $("#streetId").val("");
            $("#street").val("0");
            form.render();
        }else if (type == 3){
            $("#provinceId").removeAttr("onclick");
            $("#provinceId").attr("onclick","selectProvince('provinceId');");
            $("#cityId").removeAttr("onclick");
            $("#cityId").attr("onclick","selectCity('cityId');");
            $("#districtId").removeAttr("onclick");
            $("#districtId").attr("onclick","selectArea('districtId');");
            $("#streetId").removeAttr("onclick");
            $("#streetId").val("");
            $("#street").val("0");
            form.render();
        }else if (type == 4){
            $("#provinceId").removeAttr("onclick");
            $("#provinceId").attr("onclick","selectProvince('provinceId');");
            $("#cityId").removeAttr("onclick");
            $("#cityId").attr("onclick","selectCity('cityId');");
            $("#districtId").removeAttr("onclick");
            $("#districtId").attr("onclick","selectArea('districtId');");
            $("#streetId").removeAttr("onclick");
            $("#streetId").attr("onclick","selectStreet('streetId');");
            form.render();
        }
    });
})

//保存方法
function save(parentIndex) {
    let isValidate = rc.validateForm('#operatorRegionForm');//校验表单
    if (!isValidate) {
        return false;
    } else {
        //提交前设置disabled失效
        let flag = false;
        let type = $("#type").val();
        let provinceId = $("#provinceId").val();
        let cityId = $("#cityId").val();
        let areaId = $("#districtId").val();
        let streetId = $("#streetId").val();
        if (type == 0){
            //超级管理员助理
            rc.error("请选择类型")
        }else if (type == 1){
            //超级管理员助理
            if (provinceId != null && provinceId != '' && provinceId != undefined && provinceId != 0){
                flag = true;
            }else if (provinceId == null || provinceId == '' || provinceId == undefined || provinceId == 0){
                rc.error("请选择省级")
            }
        }else if (type == 2){
            //省级
            if (cityId != null && cityId != '' && cityId != undefined && cityId != 0){
                flag = true;
            }else if (cityId == null || cityId == '' || cityId == undefined || cityId == 0) {
                rc.error("请选择市级")
            }
        }else if (type == 3){
            //市级
            if (areaId != null && areaId != '' && areaId != undefined && areaId != 0){
                flag = true;
            }else if (areaId == null || areaId == '' || areaId == undefined || areaId == 0) {
                rc.error("请选择区级")
            }
        }else if (type == 4){
            //区级
            if (streetId != null && streetId != '' && streetId != undefined && streetId != 0){
                flag = true;
            }else if (streetId == null || streetId == '' || streetId == undefined || streetId == 0) {
                rc.error("请选择街道级")
            }
        }
        if (flag) {
            $.ajax({
                url: "/bobogou/system/operatorRegion/save",    //请求的url地址
                dataType: "json",   //返回格式为json
                async: true,//请求是否异步，默认为异步，这也是ajax重要特性
                data: $("#operatorRegionForm").serialize(),    //参数值
                type: "POST",   //请求方式
                success: function (result) {
                    if (200 == result.code) {
                        parent.refresh();
                        //当你在iframe页面关闭自身时
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        rc.msg(result.msg)
                        setTimeout(function () {
                            parent.layer.close(index); //再执行关闭
                        }, 500);
                    } else {
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

function selectProvince(id) {
    //打开前  判断市区街道的值 todo
    let openSelector = rc.openAreaSelect("/bobogou/data/province/select", "选择省", '90%', '90%',id);
}

function selectCity(id) {
    let provinceIds = $("#province").val();
    if (provinceIds != null && provinceIds != '' && provinceIds != 0 && provinceIds != undefined){
        let openSelector = rc.openAreaSelect("/bobogou/data/city/select?provinceIds=" + provinceIds, "选择市", '90%', '90%',id);
    }else {
        rc.warning("请先选择省级数据");
    }
}

function selectArea(id) {
    let cityIds = $("#city").val();
    if (cityIds != null && cityIds != '' && cityIds != 0 && cityIds != undefined){
        let openSelector = rc.openAreaSelect("/bobogou/data/area/select?cityIds=" + cityIds, "选择区", '90%', '90%',id);
    }else {
        rc.warning("请先选择市级数据");
    }
}

function selectStreet(id) {
    let districtIds = $("#district").val();
    if (districtIds != null && districtIds != '' && districtIds != 0 && districtIds != undefined) {
        let openSelector = rc.openAreaSelect("/bobogou/data/street/select?areaIds=" + districtIds, "选择街道", '90%', '90%', id);
    }else {
        rc.warning("请先选择区级数据");
    }
}