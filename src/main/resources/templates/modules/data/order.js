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
            /*'filter',
            'exports',
            'print',
            {
                title: '提示',
                layEvent: 'test',
                icon: 'layui-icon-tips'
            }*/
        ],
        cols: [
            [
                /*{
                    type: "radio"
                },*/
                {
                    title: '订单编号',
                    field: 'number',
                    minWidth: 180,
                    templet: function (data) {
                        var valueArray = data.number;
                        return valueArray;
                    }
                },
                {
                    title: '用户',
                    field: 'userName',
                    minWidth: 130
                },
                {
                    title: '用户电话',
                    field: 'phone',
                    minWidth: 120
                },
                {
                    title: '总金额',
                    minWidth: 100,
                    field: 'grossAmount',
                    sort: true
                },
                {
                    title: '配送地址',
                    field: 'address',
                    minWidth: 400
                },
                {
                    title: '配送方式',
                    minWidth: 100,
                    field: 'distributionMode',
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
                    minWidth: 100,
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
                    minWidth: 100,
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
                    minWidth: 100,
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
                    minWidth: 170,
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
                    minWidth: 200,
                    templet:function(data){
                        let status = data.status;
                        if (5 == status){
                            return '<a class="layui-btn layui-btn-xs data-count-edit" lay-event="detail"> 【详 情】 </a>\n' +
                                '<a class="layui-btn layui-btn-xs data-count-edit" lay-event="refund"> 【确认退款】 </a>\n';
                        }else {
                            return '<a class="layui-btn layui-btn-xs data-count-edit" lay-event="detail"> 【详 情】 </a>\n';
                        }
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
        /*if (obj.event === 'add') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/data/order/form/add", "新建订单信息", '1320px', '650px')
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
        } else
        if (obj.event === 'refund') {  // 监听删除操作
            let ids = getIdSelections(table);
            if (ids != null && ids != undefined && ids != ''){
                rc.confirm('确认对该订单执行退款操作吗', function(){
                    rc.post("/bobogou/data/order/refund", {"id":ids}, function (data) {
                        if (data.success) {
                            //执行搜索重载
                            refresh();
                            rc.success(data.msg);
                        } else {
                            rc.error(data.msg);
                        }
                    });
                });
            }else {
                rc.error("请选择一条数据");
            }
        }*/
    });

    table.on('tool(orderTableFilter)', function (obj) {
        let id = obj.data.id;
        let status = obj.data.status;
        if (obj.event === 'detail') {
            if (status != null && status != '' && status == 2){//状态为代发货时显示确认发货按钮
                rc.openOrderViewDialog("/bobogou/data/order/form/detail?id=" + id, "订单详情",'85%','70%')
            }else {
                rc.openViewDialog("/bobogou/data/order/form/detail?id=" + id, "订单详情",'85%','70%')
            }
        } else if (obj.event === 'chargeBack') {
            rc.openSaveDialog('/bobogou/data/order/form/chargeBack?id=' + id, "退单处理","85%","70%")
        }else if (obj.event === 'refund') {  // 监听删除操作
            rc.confirm('确认对该订单执行退款操作吗', function(){
                rc.post("/bobogou/data/order/refund", {"id":id}, function (data) {
                    if (data.code == 200) {
                        //执行搜索重载
                        refresh();
                        rc.success("退款成功");
                    } else {
                        rc.error("退款失败");
                    }
                });
            });
        }
    });
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
        ids = data[i].id;
    };
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