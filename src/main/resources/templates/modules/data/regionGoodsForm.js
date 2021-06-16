layui.use('form', function(){
    var form = layui.form;
    //各种基于事件的操作，下面会有进一步介绍

    //下拉框选中后的时间
    form.on('select(province)', function(data){
        //console.log(data.elem); //得到select原始DOM对象
        //console.log(data.value); //得到被选中的值
        //console.log(data.othis); //得到美化后的DOM对象
        let provinceId = data.value;
        $("#city").empty();//清空城市选项
        rc.post("/bobogou/data/city/all",{"province.id":provinceId},function(data){
            if(data.length>0) {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="">请选择</option>';
                for(let i=0;i<data.length;i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#city").html(tmp);
                form.render();
            }
        })
    });
    //下拉框选中后的时间
    form.on('select(city)', function(data){
        //console.log(data.elem); //得到select原始DOM对象
        //console.log(data.value); //得到被选中的值
        //console.log(data.othis); //得到美化后的DOM对象
        let cityId = data.value;
        $("#area").empty();//清空城市选项
        rc.post("/bobogou/data/area/all",{"city.id":cityId},function(data){
            if(data.length>0) {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="">请选择</option>';
                for(let i=0;i<data.length;i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#area").html(tmp);
                form.render();
            }
        })
    });
    //下拉框选中后的时间
    form.on('select(area)', function(data){
        //console.log(data.elem); //得到select原始DOM对象
        //console.log(data.value); //得到被选中的值
        //console.log(data.othis); //得到美化后的DOM对象
        let streetId = data.value;
        $("#street").empty();//清空城市选项
        rc.post("/bobogou/data/street/all",{"area.id":streetId},function(data){
            if(data.length>0) {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="">请选择</option>';
                for(let i=0;i<data.length;i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#street").html(tmp);
                form.render();
            }
        })
    });

    /*form.on('radio(isMarket)', function(data){
        alert("选择了")
        console.log(data.elem); //得到radio原始DOM对象
        console.log(data.value); //被点击的radio的value值
    });*/
});

$(document).ready(function () {
    $("#goods").click(function () {

    })
})

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