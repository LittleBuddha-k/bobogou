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
            $("#header").val(res.body.url);
        }
    });


    form.on('submit(pass)', function(data){
        //获取customerUser表单中的id信息
        let id = $("#id").val();
        //修改当前customerUser的会员状态
        rc.post("/bobogou/other/customerUser/vip",{"id":id,"member":1,"applyStatus":2},function (data) {
            if (200 == data.code){
                //关闭当前页面
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
                //刷新父页面
                parent.location.reload();
                rc.msg("审核通过");
            }else {
                rc.msg('因为后台原因，审核VIP失败')
            }
        })
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
    form.on('submit(refuse)', function(data){
        //获取customerUser表单中的id信息
        let id = $("#id").val();
        //修改当前customerUser的会员状态
        rc.post("/bobogou/other/customerUser/vip",{"id":id,"member":0,"applyStatus":3},function (data) {
            if (200 == data.code){
                //关闭当前页面
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
                //刷新父页面
                parent.location.reload();
                rc.msg("已拒绝VIP升级")
            }else {
                rc.msg('因为后台原因，审核VIP失败')
            }
        })
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
})