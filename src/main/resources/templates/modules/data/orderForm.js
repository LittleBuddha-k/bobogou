layui.use(['form', 'layedit', 'laydate'], function(){
    var form = layui.form
        ,layer = layui.layer
        ,laydate = layui.laydate;

    //日期
    laydate.render({
        elem: '#payTime'
        ,type: 'datetime'
    });
});

//保存方法
function save(parentIndex) {
    $.ajax({
        url: "/bobogou/data/order/save",    //请求的url地址
        dataType: "json",   //返回格式为json
        async: true,//请求是否异步，默认为异步，这也是ajax重要特性
        data: $("#orderForm").serialize(),    //参数值
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