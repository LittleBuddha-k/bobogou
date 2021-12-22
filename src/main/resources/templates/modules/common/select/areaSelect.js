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
        elem: '#test12'
        , data: eval(getData())
        , showCheckbox: true  //是否显示复选框
        , id: 'demoId1'
        , isJump: true //是否允许点击节点时弹出新窗口跳转
        , click: function (obj) {
            var data = obj.data;  //获取当前点击的节点数据
            layer.msg('状态：' + obj.state + '<br>节点数据：' + JSON.stringify(data));
        }
    });

    //按钮事件
    util.event('lay-demo', {
        getChecked: function(othis){
            var checkedData = tree.getChecked('demoId1'); //获取选中节点的数据

            layer.alert(JSON.stringify(checkedData), {shade:0});
            console.log(checkedData);
        }
        ,setChecked: function(){
            tree.setChecked('demoId1', [12, 16]); //勾选指定节点
        }
        ,reload: function(){
            //重载实例
            tree.reload('demoId1', {

            });

        }
    });
})