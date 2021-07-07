layui.use(['upload', 'element', 'form', 'layedit', 'laydate'], function() {
    var $ = layui.jquery
        , form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        ,upload = layui.upload
        ,element = layui.element;


})

//保存方法
function save(parentIndex) {
    var isValidate = rc.validateForm('#agreementForm');//校验表单
    if(!isValidate){
        return false;
    }else {
        $.ajax({
            url: "/bobogou/other/agreement/save",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: $("#agreementForm").serialize(),    //参数值
            type: "POST",   //请求方式
            success: function (result) {
                //假设这是iframe页
                if (200 == result.code){
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.layer.close(index); //再执行关闭
                    parent.refresh();
                    rc.alert(result.msg)
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

//富文本编辑
$(document).ready(function () {
    //编辑时
    $(function() {
        var editor = editormd("content", {
            width  : "100%",
            height : "400px",
            path   : "/bobogou/plugins/markdown/lib/",
            watch  : false,
            delay  : 0,
            placeholder: "协议详情编辑",
            imageUpload          : true,          // Enable/disable upload
            imageFormats         : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL       : "/bobogou/file/markdownUpload",             // Upload url
            crossDomainUpload    : false,          // Enable/disable Cross-domain upload
            uploadCallbackURL    : "",             // Cross-domain upload callback url
        });
    });

    //查看时
    $(function() {
        var testView = editormd.markdownToHTML("goodsInfoOther", {
            // markdown : "[TOC]\n### Hello world!\n## Heading 2", // Also, you can dynamic set Markdown text
            // htmlDecode : true,  // Enable / disable HTML tag encode.
            // htmlDecode : "style,script,iframe",  // Note: If enabled, you should filter some dangerous HTML tags for website security.
        });
    });
})