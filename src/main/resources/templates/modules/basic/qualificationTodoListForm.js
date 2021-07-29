layui.use(['upload', 'element', 'form', 'layedit', 'laydate'], function() {
    var $ = layui.jquery
        , form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        ,upload = layui.upload
        ,element = layui.element;



    form.on('submit(pass)', function(data){
        let currentUserAreaManager = $("#currentUserAreaManager").val();
        if (1 == currentUserAreaManager){
            $("#status").val(6);//省级同意
        }else if (2 == currentUserAreaManager){
            $("#status").val(4);//市级同意
        }else if (3 == currentUserAreaManager){
            $("#status").val(2);//区级同意
        }else if (4 == currentUserAreaManager){
            $("#status").val(8);//超级管理员助理级同意
        }else if (5 == currentUserAreaManager){
            $("#status").val(10);//超级管理员同意
        }
        save();
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
    form.on('submit(refuse)', function(data){
        let currentUserAreaManager = $("#currentUserAreaManager").val();
        if (1 == currentUserAreaManager){
            $("#status").val(7);//省级同意
        }else if (2 == currentUserAreaManager){
            $("#status").val(5);//市级同意
        }else if (3 == currentUserAreaManager){
            $("#status").val(3);//区级同意
        }else if (4 == currentUserAreaManager){
            $("#status").val(9);//超级管理员助理级同意
        }else if (5 == currentUserAreaManager){
            $("#status").val(11);//超级管理员同意
        }
        save();
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
})

//保存方法
function save(parentIndex) {
    var isValidate = rc.validateForm('#signContractTodoListForm');//校验表单
    if(!isValidate){
        return false;
    }else {
        $.ajax({
            url: "/bobogou/basic/signContract/doTask",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: $("#signContractTodoListForm").serialize(),    //参数值
            type: "POST",   //请求方式
            success: function (result) {
                //假设这是iframe页
                if (200 == result.code){
                    //请求成功时处理
                    // 刷新整个父窗口
                    parent.refresh();
                    //假设这是iframe页
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    rc.success(result.msg)
                    setTimeout(function(){
                        parent.layer.close(index); //再执行关闭
                    }, 1000);
                }else {
                    rc.error(result.msg)
                }
            },
            error: function (result) {
                rc.error("保存出错")
            }
        });
    }
}