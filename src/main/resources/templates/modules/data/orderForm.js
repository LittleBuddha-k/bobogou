layui.use(['form', 'layedit', 'laydate'], function () {
    var form = layui.form
        , layer = layui.layer
        , laydate = layui.laydate;

    form.on('submit(confirmDeliver)', function(data){
        let id = $("#id").val();
        rc.confirm('是否确认发货？', function() {
            rc.post("/bobogou/data/order/confirmDeliver",{"id":id} , function (data) {
                if (data.code == 200) {
                    // 刷新整个父窗口
                    parent.refresh();
                    //假设这是iframe页
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    rc.msg(data.msg)
                    setTimeout(function(){
                        parent.layer.close(index); //再执行关闭
                    }, 1000);
                } else {
                    rc.alert(data.msg);
                }
            });
        })
        return false;
    });

    //日期
    laydate.render({
        elem: '#outTime'
        , type: 'datetime'
    });
    //日期
    laydate.render({
        elem: '#payTime'
        , type: 'datetime'
    });
});

function selectGoods(id) {
    let openSelector = rc.openGoodsSelect("/bobogou/data/goods/select/", "选择商品", '75%', '85%',id);
}

function exportFile() {
    let id = $("#id").val();
    rc.confirm('即将导出数据，确认要导出订单信息吗？', function(){
        var index = rc.loading("正在下载，请稍后");
        rc.downloadFile("/bobogou/data/order/exportFile?id=" + id);
        setTimeout(function(){
            layer.close(index);
        }, 1000);
    });
}

//保存方法
function save(parentIndex) {
    var isValidate = rc.validateForm('#orderForm');//校验表单
    if(!isValidate){
        return false;
    }else {
        $.ajax({
            url: "/bobogou/data/order/save",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: $("#orderForm").serialize(),    //参数值
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
}

function addRow(list, idx, tpl, row) {
    $(list).append(Mustache.render(tpl, {
        idx: idx, delBtn: true, row: row
    }));
    $(list + idx).find("select").each(function () {
        $(this).val($(this).attr("data-value"));
    });
    $(list + idx).find("input[type='checkbox'], input[type='radio']").each(function () {
        var ss = $(this).attr("data-value").split(',');
        for (var i = 0; i < ss.length; i++) {
            if ($(this).val() == ss[i]) {
                $(this).attr("checked", "checked");
            }
        }
    });
    $(list + idx).find(".form_datetime").each(function () {
        $(this).datetimepicker({
            format: "YYYY-MM-DD HH:mm:ss"
        });
    });
}

function delRow(obj, prefix) {
    var id = $(prefix + "_id");
    var delFlag = $(prefix + "_delFlag");
    if (id.val() == "") {
        $(obj).parent().parent().remove();
    } else if (delFlag.val() == "0") {
        delFlag.val("1");
        $(obj).html("&divide;").attr("title", "撤销删除");
        $(obj).parent().parent().addClass("error");
    } else if (delFlag.val() == "1") {
        delFlag.val("0");
        $(obj).html("&times;").attr("title", "删除");
        $(obj).parent().parent().removeClass("error");
    }
}