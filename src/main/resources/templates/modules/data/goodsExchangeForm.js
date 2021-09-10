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
        elem: '#test2',
        url: '/bobogou/file/picture?uploadPath='+"/data/banner",
        multiple: true,
        before: function(obj){
            //预读本地文件示例，不支持ie8---------base64
            obj.preview(function(index, file, result){
                $('#demo2').append('<img src="'+ result +'" alt="'+ file.name +'" style="width: 92px;height: 92px;" class="layui-upload-img">')
            });
        },
        done: function(res){
            //上传完毕
            var last_url = $("#imageUrl").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#imageUrl").val(upload_image_url);
        }
    });
})

$(document).ready(function () {
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;
        let idsSelections;
        $("#goods").click(function () {
            let openSelector = rc.openSelector("/bobogou/data/medicine/select","选择商品",'95%','95%');
        })
    })

    let markdownWidth = 120, markdownHeight = 120;
    //编辑时
    $(function () {
        var editor = editormd("contentOriginal", {
            width: "100%",
            height: "400px",
            path: "/bobogou/plugins/markdown/lib/",
            watch: false,
            delay: 0,
            placeholder: "请确认商品详情图片插入之前应满足要求(长:宽):375:375",
            imageUpload: true,          // Enable/disable upload
            imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL: "/bobogou/file/goodsInfoMarkdownUpload",             // Upload url
            crossDomainUpload: false,          // Enable/disable Cross-domain upload
            uploadCallbackURL: "",             // Cross-domain upload callback url
        });
    });
})

//保存方法
function save(parentIndex) {
    var isValidate = rc.validateForm('#goodsExchangeForm');//校验表单
    if(!isValidate){
        return false;
    }else {
        $.ajax({
            url: "/bobogou/data/goodsExchange/save",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: $("#goodsExchangeForm").serialize(),    //参数值
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
                rc.alert(result.msg)
            }
        });
    }
}

/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear").click(function () {
    $('#demo2').html("");
    $("#imageUrl").val('');
});