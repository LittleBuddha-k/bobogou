layui.use(['upload', 'element', 'form', 'layedit', 'laydate'], function() {
    var $ = layui.jquery
        , form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        ,upload = layui.upload
        ,element = layui.element;
    //各种基于事件的操作，下面会有进一步介绍

    var posterWidth = 55, posterHeight = 55;
    //拖拽上传
    upload.render({
        elem: '#test10'
        ,url: '/bobogou/file/picture?uploadPath='+"/data/banner" //改成您自己的上传接口
        ,choose: function (obj) {
            flag = true;
            //读取本地文件
            obj.preview(function (index, file, result) {
                var img = new Image();
                img.onload = function () {
                    if (posterWidth == img.width && posterHeight == img.height) {
                        obj.upload(index, file); //满足条件调用上传方法
                    } else {
                        rc.error('商品分类图片必须为：' + posterWidth + 'px  ' + 'x' + posterHeight + 'px,请重新上传分辨率正确分类图标图片');
                        flag = false;
                    }
                };
                if (!flag) {
                    return false;
                }
                img.src = result;
            });
        }
        ,done: function(res){
            //layer.msg('上传成功');
            layui.$('#upload').removeClass('layui-hide').find('img').attr('src', res.body.url);
            $("#icon").val(res.body.url);
        }
    });

    //下拉框选中后的时间
    /*form.on('select(level)', function(data){
        let level = data.value;
        if(3 == level){
            $("#administrativeFee").show();
            $("#addProvinceRatio").show();
            $("#addCityRatio").show();
            $("#addDistrictRatio").show();
        }else {
            $("#administrativeFee").hide();
            $("#addProvinceRatio").hide();
            $("#addCityRatio").hide();
            $("#addDistrictRatio").hide();
        }
    });*/
});

//保存方法
function save(parentIndex) {
    var isValidate = rc.validateForm('#classifyForm');//校验表单
    if(!isValidate){
        return false;
    }else {
        $.ajax({
            url: "/bobogou/data/classify/save",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: $("#classifyForm").serialize(),    //参数值
            type: "POST",   //请求方式
            success: function (result) {
                //假设这是iframe页
                if (200 == result.code){
                    //请求成功时处理
                    // 刷新整个父窗口
                    parent.refresh();
                    //假设这是iframe页
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    rc.msg(result.msg)
                    setTimeout(function(){
                        parent.layer.close(index); //再执行关闭
                    }, 500);
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