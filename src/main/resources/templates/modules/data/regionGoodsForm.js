layui.use('form', function(){
    var form = layui.form;
    //各种基于事件的操作，下面会有进一步介绍
    //初始化情况下只有省能选择
    let type = $("#type").val();
    if (1 == type){
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
        if (type == 1){
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
            $("#cityId").removeAttr("onclick");
            $("#cityId").attr("onclick","selectCity('cityId');");
            $("#districtId").removeAttr("onclick");
            $("#districtId").attr("onclick","selectArea('districtId');");
            $("#streetId").removeAttr("onclick");
            $("#streetId").val("");
            $("#street").val("0");
            form.render();
        }else if (type == 4){
            $("#cityId").removeAttr("onclick");
            $("#cityId").attr("onclick","selectCity('cityId');");
            $("#districtId").removeAttr("onclick");
            $("#districtId").attr("onclick","selectArea('districtId');");
            $("#streetId").removeAttr("onclick");
            $("#streetId").attr("onclick","selectStreet('streetId');");
            form.render();
        }
    });
});

//保存方法
function save(parentIndex) {
    var isValidate = rc.validateForm('#regionGoodsForm');//校验表单
    if(!isValidate){
        return false;
    }else {
        //提交前设置disabled失效
        $("#provinceId").removeAttr("disabled");
        $("#cityId").removeAttr("disabled");
        $("#districtId").removeAttr("disabled");
        $("#streetId").removeAttr("disabled");
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