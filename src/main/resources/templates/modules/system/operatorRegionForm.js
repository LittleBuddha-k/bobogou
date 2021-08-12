layui.use(['upload', 'element', 'form', 'layedit', 'laydate'], function () {
    var $ = layui.jquery
        , form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        , upload = layui.upload
        , element = layui.element;

    //根据管理员类型，展示不同的区域设置项
    form.on('select(type)', function (data) {
        let type = data.value;
        if (type == 1){
            $("#provinceShow").show();
            $("#cityShow").hide();
            $("#areaShow").hide();
            $("#streetShow").hide();
        }if (type == 2){
            $("#provinceShow").show();
            $("#cityShow").show();
            $("#areaShow").hide();
            $("#streetShow").hide();
        }else if (type == 3){
            $("#provinceShow").show();
            $("#cityShow").show();
            $("#areaShow").show();
            $("#streetShow").hide();
        }else if (type == 4){
            $("#provinceShow").show();
            $("#cityShow").show();
            $("#areaShow").show();
            $("#streetShow").show();
        }
    });

    form.on('select(province)', function (data) {
        let provinceId = data.value;
        $("#city").empty();//清空城市选项
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
        let streetId = data.value;
        $("#street").empty();//清空城市选项
        rc.post("/bobogou/data/street/all", {"area.id": streetId}, function (data) {
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

function selectOperator(id) {
    let openSelector = rc.openUserSelect("/bobogou/system/operator/select/", "选择用户", '90%', '80%',id);
}