layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#orderTable',
        url: '/bobogou/data/order/data',
        method: 'GET',
        request: {
            pageName: 'pageNo', // page
            limitName: 'pageSize' // limit
        },//重命名参数名称
        done: function (res) {
            //做checkbox回显
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
        },
        toolbar: '#toolBar',
        defaultToolbar: [
            'filter',
            'exports',
            'print',
            {
                title: '提示',
                layEvent: 'test',
                icon: 'layui-icon-tips'
            }
        ],
        cols: [
            [
                {
                    type: "checkbox"
                },
                {
                    title: '订单编号',
                    field: 'number',
                    width: '250',
                    templet: function (data) {
                        var valueArray = data.number;
                        return valueArray;
                    }
                },
                {
                    title: '总金额',
                    field: 'grossAmount',
                    sort: true,
                    width: '150',
                },
                {
                    title: '配送地址',
                    field: 'address',
                    width: '350',
                },
                {
                    title: '配送方式',
                    field: 'distributionMode',
                    width: '150',
                    templet: function (data) {
                        var type = data.distributionMode;
                        let dictName = rc.getDictName("order_distribution_mode",type);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '支付方式',
                    field: 'payMode',
                    width: '180',
                    templet: function (data) {
                        var type = data.payMode;
                        let dictName = rc.getDictName("order_pay_mode",type);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '类型',
                    field: 'type',
                    width: '120',
                    templet: function (data) {
                        var type = data.type;
                        let dictName = rc.getDictName("oder_type",type);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '状态',
                    field: 'status',
                    width: '200',
                    templet: function (data) {
                        var type = data.status;
                        let dictName = rc.getDictName("order_status",type);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '支付时间',
                    field: 'payTime',
                    sort: true,
                    width: '250',
                    sortName: 'payTime',
                    templet: function (data) {
                        var payTime = data.payTime;
                        if(payTime != null && payTime != ''){
                            return payTime;
                        }else {
                            return "";
                        }
                    }
                },
                {
                    title: '操作',
                    align: "left",
                    width: '180',
                    templet: function (data) {
                        var status = data.status;
                        return '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="detail">详情</a>';
                    }
                }
            ]
        ],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 10,
        page: true,
        skin: 'line',
        where: {
            number: $("#number").val(),
            addressId: $("#addressId").val(),
            status: $("#status").val(),
            distributionMode: $("#distributionMode").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('orderTable', {
            where: {
                number: $("#number").val(),
                status: $("#status").val(),
                distributionMode: $("#distributionMode").val()
            }
        });
        return false;
    });

    // 监听重置操作
    $("#resetBtn").click(function () {
        $("#number").val("");
        $("#status").val("");
        $("#distributionMode").val("");
        init();
    })

    /**
     * toolbar监听事件
     */
    table.on('toolbar(orderTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/data/order/form/add", "新建订单信息", '1320px', '650px')
            $(window).on("resize", function () {
                layer.full(index);
            });
        } else if (obj.event === 'edit') {  // 监听修改操作
            let ids = getIdSelections(table) + "";
            let idArr = ids.toString().split(",");
            if (idArr[1]) {
                rc.alert("只能选择一条数据")
            } else if (ids.length <= 0) {
                rc.alert("请至少选择一条数据")
            } else if (idArr[0]) {
                ids = idArr[0];
                rc.openSaveDialog('/bobogou/data/order/form/edit?id=' + ids, "编辑订单信息", '1320px', '650px');
            }
            $(window).on("resize", function () {
                layer.full(index);
            });
        } else if (obj.event === 'view') {  // 监听查看操作
            let ids = getIdSelections(table);
            let idArr = ids.toString().split(",");
            if (idArr[1]) {
                rc.alert("只能选择一条数据")
            } else if (ids.length <= 0) {
                rc.alert("请至少选择一条数据")
            } else if (idArr[0]) {
                ids = idArr[0];
                rc.openViewDialog('/bobogou/data/order/form/view?id=' + ids, "查看订单信息", '85%', '70%');
            }
            $(window).on("resize", function () {
                layer.full(index);
            });
        } else if (obj.event === 'delete') {  // 监听删除操作
            let ids = getIdSelections(table);
            if (ids == null || ids == '') {
                rc.alert("请至少选择一条数据")
            } else {
                rc.confirm('确认要删除该区域信息吗？', function () {
                    rc.post("/bobogou/data/order/delete?ids=" + ids, '', function (data) {
                        if (data.code == 200) {
                            //执行搜索重载
                            refresh();
                            rc.alert(data.msg);
                        } else {
                            rc.alert(data.msg);
                        }
                    });
                })
            }
        } else if (obj.event === 'import') {  // 监听删除操作
            rc.openImportDialog("/bobogou/data/order/importTemplate", "/bobogou/data/order/importFile")
        } else if (obj.event === 'export') {  // 监听删除操作
            rc.confirm('导出时间可能较长，确认要导出订单信息吗？', function(){
                var index = rc.loading("正在下载，请稍后");
                rc.downloadFile("/bobogou/data/order/exportFile?" + $("#searchForm").serialize());
                setTimeout(function(){
                    layer.close(index);
                }, 10000);
            });
        }
    });

    table.on('tool(orderTableFilter)', function (obj) {
        let id = obj.data.id;
        if (obj.event === 'detail') {
            rc.openViewDialog("/bobogou/data/order/form/detail?id=" + id, "订单详情",'85%','70%')
        } else if (obj.event === 'edit') {
            rc.openSaveDialog('/bobogou/data/order/form/edit?id=' + id, "编辑订单信息", '85%', '70%');
        } else if (obj.event === 'chargeback') {
            rc.openViewDialogNoClose('/bobogou/data/order/form/detail?id=' + id, "退单处理","700px","580px")
        }
    });
    $("#pass").click(function(data) {
        rc.success("待支付完善做处理")
        //return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })
    $("#refuse").click(function(data) {
        rc.success("待支付完善做处理")
        //return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })
    //监视列表查找单选框
    form.on('radio(status)', function (data) {
        //console.log(data.value); //被点击的radio的value值
        let status = data.value;
        $("#status").val(status);
    });
});

/**
 * 获取layui table 复选框的id
 * @param table -- table = layui.table;
 * @param tableId -- layui table 的id
 * @returns {string}
 */
function getIdSelections(table) {
    var checkStatus = table.checkStatus('orderTable'),
        data = checkStatus.data;
    let ids = "";
    for (let i = 0; i < data.length; i++) {
        ids = ids + data[i].id + ",";
    }
    ;
    return ids;
}

function refresh() {
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;

        //执行搜索重载
        table.reload('orderTable', {
            page: {
                curr: 1
            }
            , where: {}
        }, 'data');
    })
}