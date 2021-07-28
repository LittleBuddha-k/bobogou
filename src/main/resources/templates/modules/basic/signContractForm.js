layui.use(['upload', 'element', 'form', 'layedit', 'laydate'], function() {
    var $ = layui.jquery
        , form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        ,upload = layui.upload
        ,element = layui.element;

    //身份证正照
    upload.render({
        elem: '#test1'
        ,url: '/bobogou/file/picture' //改成您自己的上传接口
        ,accept: 'images'
        ,exts: 'jpg|png|jpeg'
        ,done: function(res){
            layer.msg('上传成功');
            layui.$('#uploadDemoView1').removeClass('layui-hide').find('img').attr('src', res.body.url);
            $("#frontIdCard").val(res.body.url);
        }
    });
    //身份证反照
    upload.render({
        elem: '#test2'
        ,url: '/bobogou/file/picture' //改成您自己的上传接口
        ,accept: 'images'
        ,exts: 'jpg|png|jpeg'
        ,done: function(res){
            layer.msg('上传成功');
            layui.$('#uploadDemoView2').removeClass('layui-hide').find('img').attr('src', res.body.url);
            $("#backIdCard").val(res.body.url);
        }
    });
    //资质
    upload.render({
        elem: '#test3',
        url: '/bobogou/file/picture',
        multiple: true,
        accept: 'images',
        exts: 'jpg|png|jpeg|tif',
        done: function (res) {
            //上传完毕
            var last_url = $("#qualification").val();
            var upload_image_url = "";
            if (last_url) {
                upload_image_url = last_url + "," + res.body.url;
            } else {
                upload_image_url = res.body.url;
            }
            $("#qualification").val(upload_image_url);
            $('#demo3').append('<img src="' + res.body.url + '" alt="" style="width: 92px;height: 92px;" class="layui-upload-img">');
        }
    });
    //合同
    upload.render({
        elem: '#test4',
        url: '/bobogou/file/picture',
        multiple: true,
        accept: 'images',
        exts: 'jpg|png|jpeg|tif',
        done: function (res) {
            //上传完毕
            var last_url = $("#contract").val();
            var upload_image_url = "";
            if (last_url) {
                upload_image_url = last_url + "," + res.body.url;
            } else {
                upload_image_url = res.body.url;
            }
            $("#contract").val(upload_image_url);
            $('#demo4').append('<img src="' + res.body.url + '" alt="" style="width: 92px;height: 92px;" class="layui-upload-img">');
        }
    });
})

//保存方法
function save(parentIndex) {
    var isValidate = rc.validateForm('#signContractForm');//校验表单
    if(!isValidate){
        return false;
    }else {
        $.ajax({
            url: "/bobogou/basic/signContract/save",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: $("#signContractForm").serialize(),    //参数值
            type: "POST",   //请求方式
            success: function (result) {
                //假设这是iframe页
                if (200 == result.code){
                    //请求成功时处理
                    // 刷新整个父窗口
                    parent.refresh();
                    //假设这是iframe页
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    rc.success(result.msg)
                    setTimeout(function(){
                        parent.layer.close(index); //再执行关闭
                    }, 1000);
                }else {
                    rc.error(result.msg)
                }
            },
            error: function (result) {
                rc.error("保存出错")
            }
        });
    }
}