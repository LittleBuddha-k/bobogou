layui.use('form', function(){
    var form = layui.form;
    //各种基于事件的操作，下面会有进一步介绍

    //对标签的操作----点击时才生效
    //form.on('checkbox(tag)', function(data){
    //    //console.log(data.elem); //得到checkbox原始DOM对象
    //    let checked = data.elem.checked; //是否被选中，true或者false
    //    if(checked){
    //        let id = data.value; //复选框value值，也可以通过data.elem.value得到
    //        let tagId = $("#tagId").val();
    //        let get = tagId + "," + id;
    //        $("#tagId").val(get);
    //    }else {
    //        $("#tagId").val("")
    //    }
    //    //console.log($("#tagId").val())
    //    //console.log(data.othis); //得到美化后的DOM对象
    //});

    //商品分类级联下拉框
    //form.on('select(levelOne)', function(data){
    //    console.log(data.elem); //得到select原始DOM对象
    //    console.log(data.value); //得到被选中的值
    //    console.log(data.othis); //得到美化后的DOM对象
    //});

    //监视列表查找单选框
    form.on('radio(status)', function(data){
        //console.log(data.value); //被点击的radio的value值
        let status = data.value;
        $("#status").val(status);
    });
});

//保存方法
function save(parentIndex) {
    $.ajax({
        url: "/bobogou/data/province/save",    //请求的url地址
        dataType: "json",   //返回格式为json
        async: true,//请求是否异步，默认为异步，这也是ajax重要特性
        data: $("#provinceForm").serialize(),    //参数值
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