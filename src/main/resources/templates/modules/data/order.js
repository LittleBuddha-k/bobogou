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
                    sort: true,
                    width: '7.5%',
                    sortName: 'number',
                    templet: function (data) {
                        var valueArray = data.number;
                        return valueArray;
                    }
                },
                {
                    title: '总金额',
                    field: 'grossAmount',
                    sort: true,
                    width: '7.5%',
                    sortName: 'grossAmount'
                },
                {
                    title: '积分',
                    field: 'integral',
                    sort: true,
                    width: '7.5%',
                    sortName: 'integral'
                },
                {
                    title: '健康豆',
                    field: 'healthBeans',
                    sort: true,
                    width: '7.5%',
                    sortName: 'healthBeans'
                },
                {
                    title: '配送地址',
                    field: 'addressId',
                    sort: true,
                    width: '7.5%',
                    sortName: 'addressId'
                },
                {
                    title: '配送方式',
                    field: 'distributionMode',
                    sort: true,
                    width: '7.5%',
                    sortName: 'distributionMode',
                    templet: function (data) {
                        let distributionMode = data.distributionMode;
                        if(1 == distributionMode){
                            return "顺丰";
                        }else if (2 == distributionMode){
                            return "京东";
                        }
                    }
                },
                {
                    title: '物流单号',
                    field: 'trackingNo',
                    sort: true,
                    width: '7.5%',
                    sortName: 'trackingNo'
                },
                {
                    title: '支付方式',
                    field: 'payMode',
                    sort: true,
                    width: '7.5%',
                    sortName: 'payMode',
                    templet: function (data) {
                        let payMode = data.payMode;
                        if(0 == payMode){
                            return "兑换";
                        }else if (1 == payMode){
                            return "微信";
                        }else if (2 == payMode){
                            return "支付宝";
                        }else if (3 == payMode){
                            return "银行卡";
                        }else {
                            return "其他";
                        }
                    }
                },
                {
                    title: '类型',
                    field: 'type',
                    sort: true,
                    width: '7.5%',
                    sortName: 'type',
                    templet: function (data) {
                        let type = data.type;
                        if(0 == type){
                            return "购买";
                        }else if (1 == type){
                            return "健康豆兑换";
                        }else if (2 == type){
                            return "积分兑换";
                        }
                    }
                },
                {
                    title: '状态',
                    field: 'status',
                    sort: true,
                    width: '7.5%',
                    sortName: 'status',
                    templet: function (data) {
                        let status = data.status;
                        if(0 == status){
                            return "已取消";
                        }else if (1 == status){
                            return "待付款";
                        }else if (2 == status){
                            return "待收货";
                        }else if (3 == status){
                            return "已完成";
                        }else if (4 == status){
                            return "申请退款";
                        }else if (5 == status){
                            return "已退款";
                        }
                    }
                },
                {
                    title: '支付时间',
                    field: 'payTime',
                    sort: true,
                    width: '10%',
                    sortName: 'payTime',
                    templet: function (data) {
                        var payTime = data.payTime;
                        return payTime;
                    }
                },
                {
                    title: '操作',
                    align: "left",
                    width: '15%',
                    templet: function (data) {
                        var status = data.status;
                        if (4 == status){
                            return '\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="detail">详情</a>\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="edit">编辑</a>\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="chargeback">退单处理</a>';
                        }else {
                            return '\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="detail">详情</a>\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="edit">编辑</a>\n'
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
            grossAmount: $("#grossAmount").val(),
            integral: $("#integral").val(),
            healthBeans: $("#healthBeans").val(),
            addressId: $("#addressId").val(),
            distributionMode: $("#distributionMode").val(),
            trackingNo: $("#trackingNo").val(),
            payMode: $("#payMode").val(),
            type: $("#type").val(),
            status: $("#status").val(),
            payTime: $("#payTime").val(),
            refundReason: $("#refundReason").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('orderTable', {
            where: {
                number: $("#number").val(),
                grossAmount: $("#grossAmount").val(),
                integral: $("#integral").val(),
                healthBeans: $("#healthBeans").val(),
                addressId: $("#addressId").val(),
                distributionMode: $("#distributionMode").val(),
                trackingNo: $("#trackingNo").val(),
                payMode: $("#payMode").val(),
                type: $("#type").val(),
                status: $("#status").val(),
                payTime: $("#payTime").val(),
                refundReason: $("#refundReason").val()
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(orderTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/data/order/form/add", "新建订单信息", '100%', '100%')
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
                rc.openSaveDialog('/bobogou/data/order/form/edit?id=' + ids, "编辑订单信息", '100%', '100%');
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
                rc.openViewDialog('/bobogou/data/order/form/view?id=' + ids, "查看订单信息", '100%', '100%');
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
            rc.downloadFile("/bobogou/data/order/exportFile?" + $("#searchForm").serialize());
        }
    });

    table.on('tool(orderTableFilter)', function (obj) {
        let id = obj.data.id;
        if (obj.event === 'detail') {
            rc.openViewDialog("/bobogou/data/order/form/detail?id=" + id, "订单详情",'100%','100%')
        } else if (obj.event === 'edit') {
            rc.openSaveDialog('/bobogou/data/order/form/edit?id=' + id, "编辑订单信息", '100%', '100%');
        } else if (obj.event === 'chargeback') {

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