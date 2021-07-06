layui.use(['upload', 'element', 'form', 'layedit', 'laydate'], function () {
    var $ = layui.jquery
        , form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        , upload = layui.upload
        , element = layui.element;

    //拖拽上传
    upload.render({
        elem: '#test10'
        , url: '/bobogou/file/picture?uploadPath=' + "/data/banner" //改成您自己的上传接口
        , done: function (res) {
            layer.msg('上传成功');
            layui.$('#upload').removeClass('layui-hide').find('img').attr('src', res.body.url);
            $("#picture").val(res.body.url);
        }
    });

    //下拉框选中后的时间
    form.on('select(province)', function(data){
        //console.log(data.elem); //得到select原始DOM对象
        //console.log(data.value); //得到被选中的值
        //console.log(data.othis); //得到美化后的DOM对象
        let provinceId = data.value;
        $("#city").empty();//清空城市选项
        $("#area").empty();//清空城市选项
        $("#street").empty();//清空城市选项
        rc.post("/bobogou/data/city/all",{"province.id":provinceId},function(data){
            if(data.length>0) {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="">请选择</option>';
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
        //console.log(data.elem); //得到select原始DOM对象
        //console.log(data.value); //得到被选中的值
        //console.log(data.othis); //得到美化后的DOM对象
        let cityId = data.value;
        $("#area").empty();//清空城市选项
        $("#street").empty();//清空城市选项
        rc.post("/bobogou/data/area/all",{"city.id":cityId},function(data){
            if(data.length>0) {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="">请选择</option>';
                for(let i=0;i<data.length;i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#area").html(tmp);
                form.render();
            }
        })
    });

    //动态添加
    $("#phone").change(function () {
        let phone = $("#phone").val();
        if(phone != '' && phone != null){
            $.ajax({
                //请求方式
                type : "GET",
                //请求地址
                url : "/bobogou/other/customerUser/byPhone?phone="+phone,
                async : false,//改为同步
                //数据，json字符串
                //data : JSON.stringify(list),
                //请求成功
                success : function(result) {
                    if (result.data == null){
                        let html = '<div class="layui-inline">' +
                            '<label class="layui-form-label" for="areaManager">区域管理员等级</label>' +
                            '<div class="layui-input-inline">' +
                            '<select name="customerUser.areaManager" lay-verify="" >' +
                            '<option th:value="0" th:text="前端用户"/>' +
                            '<option th:value="1" th:text="省管理员"/>' +
                            '<option th:value="2" th:text="市管理员"/>' +
                            '<option th:value="3" th:text="区管理员"/>' +
                            '</select>' +
                            '</div>' +
                            '</div>' +
                            '<div class="layui-inline">' +
                            '<label class="layui-form-label" for="status">状态</label>' +
                            '<div class="layui-input-inline">' +
                            '<select name="customerUser.status" lay-verify="" >' +
                            '<option th:value="0" th:text="app未注册"/>' +
                            '<option th:value="1" th:text="app已注册"/>' +
                            '</select>' +
                            '</div>' +
                            '</div>';
                        $("#append").empty();
                        $("#append").append(html);
                        form.render();
                    }else {
                        let html = '<div class="layui-inline">' +
                            '<label class="layui-form-label" for="areaManager">区域管理员等级</label>' +
                            '<div class="layui-input-inline">' +
                            '<select name="customerUser.areaManager" lay-verify="" >' +
                            '<option th:value="0" th:text="前端用户"/>' +
                            '<option th:value="1" th:text="省管理员"/>' +
                            '<option th:value="2" th:text="市管理员"/>' +
                            '<option th:value="3" th:text="区管理员"/>' +
                            '</select>' +
                            '</div>' +
                            '</div>';
                        $("#append").empty();
                        $("#append").append(html);
                        layui.use('form', function(){
                            let form = layui.form;
                            form.render();
                        });
                    }
                },
                //请求失败，包含具体的错误信息
                error : function(e){
                    console.log("请求shibai "+e.status);
                    console.log("请求shibai "+e.responseText);
                }
            });
        }
    })
})


//保存方法
function save(parentIndex) {
    let isValidate = rc.validateForm('#operatorForm');//校验表单
    if (!isValidate) {
        return false;
    } else {
        $.ajax({
            url: "/bobogou/system/operator/save",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: $("#operatorForm").serialize(),    //参数值
            type: "POST",   //请求方式
            success: function (result) {
                //假设这是iframe页
                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                parent.layer.close(index); //再执行关闭
                parent.refresh();
                rc.alert(result.msg)
            },
            error: function (result) {
                rc.alert(result.msg)
            }
        });
    }
}

$(document).ready(function () {
})