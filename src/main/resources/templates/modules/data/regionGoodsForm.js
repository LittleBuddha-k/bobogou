layui.use('form', function(){
    var form = layui.form;
    //各种基于事件的操作，下面会有进一步介绍

    //下拉框选中后的时间
    form.on('select(province)', function(data){
        alert("省份选中")
        //console.log(data.elem); //得到select原始DOM对象
        //console.log(data.value); //得到被选中的值
        //console.log(data.othis); //得到美化后的DOM对象
    });
    //下拉框选中后的时间
    form.on('select(city)', function(data){
        alert("城市选中")
        //console.log(data.elem); //得到select原始DOM对象
        //console.log(data.value); //得到被选中的值
        //console.log(data.othis); //得到美化后的DOM对象
    });
    //下拉框选中后的时间
    form.on('select(area)', function(data){
        alert("区域选中")
        //console.log(data.elem); //得到select原始DOM对象
        //console.log(data.value); //得到被选中的值
        //console.log(data.othis); //得到美化后的DOM对象
    });
});

//保存方法
function save(parentIndex) {
    $.ajax({
        url: "/bobogou/data/regionGoods/save",    //请求的url地址
        dataType: "json",   //返回格式为json
        async: true,//请求是否异步，默认为异步，这也是ajax重要特性
        data: $("#regionGoodsForm").serialize(),    //参数值
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