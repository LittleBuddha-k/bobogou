layui.use(['form','tree', 'util'], function() {
    var form = layui.form;
    var tree = layui.tree
        , layer = layui.layer
        , util = layui.util;


    function getData(){
        var data ;
        $.ajax({
            url:"/bobogou/data/areaSelect/data",
            type:"get",
            async:false,
            success:function(result){
                data=result;
            }
        });
        return data;
    };

    //基本演示
    tree.render({
        elem: '#treeTable'
        , data: eval(getData())
        , showCheckbox: true  //是否显示复选框
        , id: 'areaSelect'
        , isJump: true //是否允许点击节点时弹出新窗口跳转
        , click: function (obj) {
            var data = obj.data;  //获取当前点击的节点数据
            layer.msg('状态：' + obj.state + '<br>节点数据：' + JSON.stringify(data));
        }
    });

    //按钮事件
    util.event('lay-demo', {
        getChecked: function(othis){
            var checkedData = tree.getChecked('areaSelect'); //获取选中节点的数据
            layer.alert(JSON.stringify(checkedData), {shade:0});
            console.log(checkedData);
            var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
            iframeWin.treeSave();
        }
        ,close: function(){
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
    });
})

function getChecked() {
    var data;
    layui.use(['form','tree', 'util'], function() {
        var form = layui.form;
        var tree = layui.tree
            , layer = layui.layer
            , util = layui.util;

        data = tree.getChecked('areaSelect');
    })
    return data;
}

function save(id,saveUrl,data) {
    alert(data)
    $.ajax({
        url: saveUrl + id,    //请求的url地址
        dataType: "json",   //返回格式为json
        async: true,//请求是否异步，默认为异步，这也是ajax重要特性
        data: {"data":JSON.stringify(data)},    //参数值
        type: "POST",   //请求方式
        success:function () {
            alert("请求成功")
        }
    });
}

