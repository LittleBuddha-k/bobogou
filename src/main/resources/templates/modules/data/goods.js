layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    var init = table.render({
        elem: '#goodsTable',
        url: '/bobogou/data/goods/currentData',
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
                    type: "checkbox"
                },*/
                {
                    title: '商品名',
                    field: 'name',
                    align: 'center',
                    minWidth: '200'
                },
                {
                    title: '商品通用名称',
                    field: 'commonName',
                    align: 'center',
                    minWidth: '200'
                },
                {
                    title: '集采价',
                    field: 'purchasingPrice',
                    sort: true,
                    minWidth: '100'
                },
                {
                    title: '进价',
                    field: 'originalCost',
                    align: 'center',
                    minWidth: '80'
                },
                {
                    title: '零售价',
                    field: 'sellingPrice',
                    align: 'center',
                    minWidth: '80'
                },
                {
                    title: '会员价',
                    field: 'vipPrice',
                    sort: true,
                    align: 'center',
                    minWidth: '80'
                },
                {
                    title: '规格',
                    field: 'specification',
                    minWidth: '120'
                },
                {
                    title: '数量',
                    field: 'amount',
                    sort: true,
                    align: 'center',
                    minWidth: '80'
                },
                {
                    title: '已分配',
                    field: 'usedAmount',
                    align: 'center',
                    minWidth: '100'
                },
                {
                    title: '库存数',
                    field: 'stockAmount',
                    sort: true,
                    align: 'center',
                    minWidth: '100'
                },
                {
                    title: '是否销售',
                    field: 'isMarket',
                    align: 'center',
                    minWidth: '120',
                    templet: function (data) {
                        var isMarket = data.isMarket;
                        let dictName = rc.getDictName("data_goods_is_market",isMarket);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '审核状态',
                    field: 'actStatus',
                    align: "left",
                    minWidth: '150',
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
                    minWidth: '300',
                    align: 'center',
                    templet:function(data){
                        let isMarket = data.isMarket;
                        let actStatus = data.actStatus;
                        if (0 == actStatus){
                            return  '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="subTask">提交</a>\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="edit">修改</a>\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="delete">删除</a>\n';
                        }else if(1 == actStatus || 2 == actStatus || 4 == actStatus || 6 == actStatus || 8 == actStatus){
                            return '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="flow">跟踪</a>\n';
                        }else if (3 == actStatus || 5 == actStatus || 7 == actStatus || 9 == actStatus || 11 == actStatus){
                            return '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="edit">修改</a>\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="subTask">重新提交</a>\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="flow">跟踪</a>\n'+
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="delete">删除</a>\n';
                        }else if(10 == actStatus && 0 == isMarket){
                            return '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="detail">详情</a>\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="reSubTask">重审</a>\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="offShelf">商品下架</a>' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="delete">删除</a>\n';
                        }else if (10 == actStatus && 2 == isMarket){
                            return '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="detail">详情</a>\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="reSubTask">重审</a>\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="onShelves">商品上架</a>\n'+
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="delete">删除</a>\n';
                        }else {
                            return "未知";
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
        table.reload('goodsTable', {
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

    /**
     * toolbar监听事件
     */
    table.on('toolbar(goodsTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/data/goods/form/add", "新建商品信息",'1010px','85%')
        }
    });

    table.on('tool(goodsTableFilter)', function (obj) {
        let id = obj.data.id;
        let event = obj.event;
        if (event === 'edit') {
            rc.openSaveDialog('/bobogou/data/goods/form/edit?id=' + id, "编辑商品信息",'1010px','85%');
        } else if (event === 'subTask') {
            rc.confirm('是否提交商品审核？', function() {
                rc.post("/bobogou/data/goods/subTask",{"id":id} , function (data) {
                    if (data.code == 200) {
                        //执行搜索重载
                        refresh();
                        rc.alert(data.msg);
                    } else {
                        rc.alert(data.msg);
                    }
                });
            })
        } else if (event === 'reSubTask') {
            rc.confirm('是否驳回商品审核？', function() {
                rc.post("/bobogou/data/goods/reSubTask",{"id":id} , function (data) {
                    if (data.code == 200) {
                        //执行搜索重载
                        refresh();
                        rc.alert(data.msg);
                    } else {
                        rc.alert(data.msg);
                    }
                });
            })
        }else if (obj.event === 'flow') {  // 监听跟踪操作
            var index = rc.openViewDialog("/bobogou/data/goods/flow?id="+id, "商品审核历史信息",'1000px','600px')
        }else if (event === 'onShelves') {
            rc.confirm('是否确认上架商品？', function() {
                rc.post("/bobogou/data/goods/onTheShelf",{"id":id,"isMarket":0} , function (data) {
                    if (data.code == 200) {
                        //执行搜索重载
                        refresh();
                        rc.alert(data.msg);
                    } else {
                        rc.alert(data.msg);
                    }
                });
            })
        }else if (event === 'offShelf') {
            rc.confirm('确认要下架该商品？', function() {
                rc.post("/bobogou/data/goods/onTheShelf", {"id":id,"isMarket":2}, function (data) {
                    if (data.code == 200) {
                        //执行搜索重载
                        refresh();
                        rc.alert(data.msg);
                    } else {
                        rc.alert(data.msg);
                    }
                });
            })
        } else if (event === 'delete') {
            rc.confirm('确认要删除该商品信息吗？', function() {
                rc.post("/bobogou/data/goods/delete?ids=" + id, '', function (data) {
                    if (data.code == 200) {
                        //执行搜索重载
                        refresh();
                        rc.alert(data.msg);
                    } else {
                        rc.alert(data.msg);
                    }
                });
            })
        }else if (obj.event === 'detail') {
            rc.openViewDialog("/bobogou/data/goods/form/detail?id="+id, "查看药品信息",'1010px','85%')
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
    var checkStatus = table.checkStatus('goodsTable'),
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

        var checkStatus = table.checkStatus('goodsTable'),
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
        table.reload('goodsTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'data');
    })
}