layui.use(['upload', 'element', 'form', 'layedit', 'laydate'], function() {
    var $ = layui.jquery
        , form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        ,upload = layui.upload
        ,element = layui.element;

    //多图片上传
    upload.render({
        elem: '#test1',
        url: '/bobogou/file/upload-watermark',
        multiple: true,
        accept: 'images',
        exts: 'jpg|png|jpeg',
        done: function(res){
            //上传完毕
            var last_url = $("#qualification").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#qualification").val(upload_image_url);
            $('#demo1').append('<img src="' + res.body.url + '" alt="" style="width: 92px;height: 92px;" class="layui-upload-img">');
        }
    });

    /**
     * 多图清除按钮点击事件
     */
    $("#btn_image_clear_test1").click(function () {
        $('#demo1').html("");
        $("#qualification").val('');
    });
})

//保存方法
function save(parentIndex) {
    var isValidate = rc.validateForm('#qualificationForm');//校验表单
    if(!isValidate){
        return false;
    }else {
        $.ajax({
            url: "/bobogou/basic/qualification/save",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: $("#qualificationForm").serialize(),    //参数值
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
                    }, 1000);
                }else {
                    rc.alert(result.msg)
                }
            },
            error: function (result) {
                rc.alert("请求出错，请联系管理员")
            }
        });
    }
}