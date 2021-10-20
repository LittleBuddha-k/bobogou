layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    let init = table.render({
        elem: '#orderFactoryTable',
        url: '/bobogou/data/orderFactory/data',
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
        //toolbar: '#toolBar',
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
                    field: 'orderNumber',
                    width: '150',
                },
                {
                    title: '厂商名字',
                    field: 'factoryName',
                    sort: true,
                    width: '150',
                },
                {
                    title: '省',
                    field: 'province',
                    width: '120',
                },
                {
                    title: '市',
                    field: 'city',
                    width: '120',
                },
                {
                    title: '区',
                    field: 'area',
                    width: '120',
                },
                {
                    title: '街道',
                    field: 'street',
                    width: '180',
                },
                {
                    title: '详细地址',
                    field: 'detailAddress',
                    width: '180',
                },
                {
                    title: '收货地址',
                    field: 'receivingAddress',
                    width: '300',
                },
                {
                    title: '总重量(g)',
                    field: 'totalWeight',
                    width: '120',
                },
                {
                    title: '付费重量(g)',
                    field: 'freightWeight',
                    width: '150',
                },
                {
                    title: '运费',
                    field: 'freight',
                    width: '120',
                },
                {
                    title: '配送方式',
                    field: 'distributionMode',
                    width: '150',
                    templet: function (data) {
                        var type = data.distributionMode;
                        let dictName = rc.getDictName("data_order_factory_distribution_mode",type);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '物流单号',
                    field: 'trackingNo',
                    width: '180',
                },
                {
                    title: '出库状态',
                    field: 'outStatus',
                    width: '120',
                    templet: function (data) {
                        var outStatus = data.outStatus;
                        let dictName = rc.getDictName("data_order_factory_out_status",outStatus);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '确认发货时间',
                    field: 'deliveryTime',
                    width: '180',
                    templet: function (data) {
                        var deliveryTime = data.deliveryTime;
                        if (deliveryTime != null && deliveryTime != '' && deliveryTime != undefined){
                            deliveryTime = rc.dateFormat(deliveryTime);
                        }else {
                            deliveryTime = "未知";
                        }
                        return deliveryTime;
                    }
                },
                {
                    title: '操作',
                    toolbar: '#operation',
                    align: "left",
                    width: '180',
                }
            ]
        ],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 10,
        page: true,
        skin: 'line',
        where: {
            trackingNo: $("#trackingNo").val(),
            orderNumber: $("#orderNumber").val(),
            outStatus: $("#outStatus").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('orderFactoryTable', {
            where: {
                trackingNo: $("#trackingNo").val(),
                orderNumber: $("#orderNumber").val(),
                outStatus: $("#outStatus").val()
            }
        });
        return false;
    });

    // 监听重置操作
    $("#resetBtn").click(function () {
        $("#trackingNo").val("");
        $("#orderNumber").val("");
        $("#outStatus").val("");
        init();
    })
    
    table.on('tool(orderFactoryTableFilter)', function (obj) {
        let id = obj.data.id;
        if (obj.event === 'detail') {
            rc.openViewDialog("/bobogou/data/orderFactory/form/detail?id=" + id, "商家订单详情",'85%','85%')
        } else if (obj.event === 'edit') {
            rc.openSaveDialog('/bobogou/data/orderFactory/form/edit?id=' + id, "编辑商家订单信息", '85%', '85%');
        }
    });
});

/**
 * 获取layui table 复选框的id
 * @param table -- table = layui.table;
 * @param tableId -- layui table 的id
 * @returns {string}
 */
function getIdSelections(table) {
    var checkStatus = table.checkStatus('orderFactoryTable'),
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
        table.reload('orderFactoryTable', {
            page: {
                curr: 1
            }
            , where: {}
        }, 'data');
    })
}