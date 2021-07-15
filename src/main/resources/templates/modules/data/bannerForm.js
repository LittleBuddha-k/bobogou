//保存方法
function save(parentIndex) {
    $.ajax({
        url: "/bobogou/data/banner/save",    //请求的url地址
        dataType: "json",   //返回格式为json
        async: true,//请求是否异步，默认为异步，这也是ajax重要特性
        data: $("#bannerForm").serialize(),    //参数值
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

layui.use(['upload', 'element', 'form', 'layedit', 'laydate'], function() {
        var $ = layui.jquery
        , form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        ,upload = layui.upload
        ,element = layui.element;

    //拖拽上传
    upload.render({
        elem: '#test10'
        ,url: '/bobogou/file/picture?uploadPath='+"/data/banner" //改成您自己的上传接口
        ,done: function(res){
            layer.msg('上传成功');
            layui.$('#upload').removeClass('layui-hide').find('img').attr('src', res.body.url);
            $("#url").val(res.body.url);
            $("#link").val(res.body.url);
        }
    });
})