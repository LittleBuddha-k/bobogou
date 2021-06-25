layui.use(['upload', 'element', 'form', 'layedit', 'laydate'], function () {
    var $ = layui.jquery
        , form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        , upload = layui.upload
        , element = layui.element;

//多图片上传
    upload.render({
        elem: '#test2',
        url: '/bobogou/file/picture?uploadPath=' + "/data/banner",
        multiple: true,
        before: function (obj) {
            //预读本地文件示例，不支持ie8---------base64
            obj.preview(function (index, file, result) {
                $('#demo2').append('<img src="' + result + '" alt="' + file.name + '" style="width: 92px;height: 92px;" class="layui-upload-img">')
            });
        },
        done: function (res) {
            //上传完毕
            var last_url = $("#imageUrl").val();
            var upload_image_url = "";
            if (last_url) {
                upload_image_url = last_url + "," + res.body.url;
            } else {
                upload_image_url = res.body.url;
            }
            $("#imageUrl").val(upload_image_url);
        }
    });
})

/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear").click(function () {
    $('#demo2').html("");
    $("#imageUrl").val('');
});

//保存方法
function save(parentIndex) {
    var isValidate = rc.validateForm('#stickerForm');//校验表单
    if(!isValidate){
        return false;
    }else {
        $.ajax({
            url: "/bobogou/other/sticker/save",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: $("#stickerForm").serialize(),    //参数值
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