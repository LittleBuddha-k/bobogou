layui.use(['upload', 'element', 'form', 'layedit', 'laydate'], function () {
    var $ = layui.jquery
        , form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        , upload = layui.upload
        , element = layui.element;


})


//保存方法
function save(parentIndex) {
    let isValidate = rc.validateForm('#roleForm');//校验表单
    if (!isValidate) {
        return false;
    } else {
        $.ajax({
            url: "/bobogou/system/role/save",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: $("#roleForm").serialize(),    //参数值
            type: "POST",   //请求方式
            success: function (result) {
                if(200 == result.code){
                    parent.refresh();
                    //当你在iframe页面关闭自身时
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    rc.msg(result.msg)
                    setTimeout(function(){
                        parent.layer.close(index); //再执行关闭
                    }, 1000);
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