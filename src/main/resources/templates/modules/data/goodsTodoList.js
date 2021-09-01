layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    var init = table.render({
        elem: '#goodsTodoTable',
        url: '/bobogou/data/goods/todoData',
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
                    title: '商品名',
                    field: 'name',
                    sort: true,
                    sortName: 'name',
                    align: 'center',
                    width: '200'
                },
                {
                    title: '商品通用名称',
                    field: 'commonName',
                    sort: true,
                    sortName: 'commonName',
                    align: 'center',
                    width: '200'
                },
                {
                    title: '集采价',
                    field: 'purchasingPrice',
                    sort: true,
                    width: '80'
                },
                {
                    title: '进价',
                    field: 'originalCost',
                    align: 'center',
                    width: '100'
                },
                {
                    title: '零售价',
                    field: 'sellingPrice',
                    align: 'center',
                    width: '100'
                },
                {
                    title: '会员价',
                    field: 'vipPrice',
                    sort: true,
                    align: 'center',
                    width: '100'
                },
                {
                    title: '规格',
                    field: 'specification',
                    sort: true,
                    width: '200'
                },
                {
                    title: '数量',
                    field: 'amount',
                    sort: true,
                    align: 'center',
                    width: '80'
                },
                {
                    title: '已分配',
                    field: 'usedAmount',
                    sort: true,
                    align: 'center',
                    width: '100'
                },
                {
                    title: '库存数',
                    field: 'stockAmount',
                    sort: true,
                    align: 'center',
                    width: '100'
                },
                {
                    title: '是否销售',
                    field: 'isMarket',
                    sort: true,
                    align: 'center',
                    width: '120',
                    templet:function(data){
                        let isMarket = data.isMarket;
                        if(0 == isMarket){
                            return "在售";
                        }else if (2 == isMarket){
                            return "停售";
                        }else {
                            return "未知";
                        }
                    }
                },
                {
                    title: '审核状态',
                    field: 'actStatus',
                    sortName: 'actStatus',
                    align: "left",
                    width: '120',
                    templet: function (data) {
                        var actStatus = data.actStatus;
                        let dictName = rc.getDictName("act_status",actStatus);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '操作',
                    align: 'left',
                    width: '200',
                    align: 'center',
                    templet:function(data){
                        let isMarket = data.isMarket;
                        let actStatus = data.actStatus;
                        if (1 == actStatus || 2 == actStatus || 4 == actStatus || 6 == actStatus || 8 == actStatus){
                            return '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="doTask">【任务办理】</a>\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="flow">【跟踪】</a>\n';
                        }else if (3 == actStatus || 5 == actStatus || 7 == actStatus || 9 == actStatus || 11 == actStatus){
                            return '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="flow">【已拒绝】</a>\n';
                        }else if (10 == actStatus){
                            return '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="flow">【已通过】</a>\n';
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
            name: $("#name").val(),
            factoryId: $("#factoryId").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('goodsTodoTable', {
            where: {
                name: $("#name").val(),
                factoryId: $("#factoryId").val()
            }
        });
        return false;
    });

    $("#reset").click(function () {
        $("#name").val("");
        $("#factoryId").val("");
        init();
    })

    table.on('tool(goodsTodoTableFilter)', function (obj) {
        let id = obj.data.id;
        let event = obj.event;
        if (obj.event === 'doTask') {  // 监听添加操作
            var index = rc.openViewDialogNoClose("/bobogou/data/goods/todoListForm?id=" + id, "商品审核办理", '1010px','90%')
        } else if (obj.event === 'flow') {  // 监听添加操作
            var index = rc.openViewDialog("/bobogou/data/goods/flow?id=" + id, "商品审核历史信息", '1000px', '90%')
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
    var checkStatus = table.checkStatus('goodsTodoTable'),
        data = checkStatus.data;
    let ids = "";
    for (let i = 0; i < data.length; i++) {
        ids = ids + data[i].id + ",";
    }
    ;
    return ids;
}

function getSelector() {
    let ids = "";
    let name = "";
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;

        var checkStatus = table.checkStatus('goodsTodoTable'),
            data = checkStatus.data;
        let idArr = ids.toString().split(",");
        if (data.length > 1) {
            rc.alert("只能选择一条数据")
        } else if (data.length <= 0) {
            rc.alert("请至少选择一条数据")
        } else {
            ids = data[0].id;
            name = data[0].name;
        }
        /*for (let i = 0; i < data.length; i++) {
            ids = ids + data[i].id + ",";
        }*/
    })
    return ids + "," + name;
}

function refresh() {
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;

        //执行搜索重载
        table.reload('goodsTodoTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'data');
    })
}