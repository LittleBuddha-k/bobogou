layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#goodsTable',
        url: '/bobogou/data/goods/data',
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
                    title: '药名',
                    field: 'name',
                    sort: true,
                    sortName: 'name',
                    align: 'center',
                    width: '8%'
                }/*,
                {
                    title: '图片',
                    field: 'images',
                    sort: true,
                    sortName: 'images'
                },
                {
                    title: '品牌分类ID',
                    field: 'brandId',
                    sort: true,
                    sortName: 'brandId'
                },
                {
                    title: '商品标签ID',
                    field: 'tagId',
                    sort: true,
                    sortName: 'tagId'
                }*/,
                {
                    title: '进价',
                    field: 'purchasingPrice',
                    sort: true,
                    width: '8%'
                },
                {
                    title: '原价',
                    field: 'originalCost',
                    width: '8%'
                },
                {
                    title: '普通会员价',
                    field: 'sellingPrice',
                    width: '8%'
                },
                {
                    title: '会员价',
                    field: 'vipPrice',
                    sort: true,
                    width: '8%'
                },
                {
                    title: '规格',
                    field: 'specification',
                    sort: true,
                    width: '8%'
                },
                {
                    title: '数量',
                    field: 'amount',
                    sort: true,
                    align: 'center',
                    width: '8%'
                },/*
                {
                    title: '销量',
                    field: 'salesVolume',
                    sort: true,
                    align: 'center'
                },
                {
                    title: '功效',
                    field: 'effect',
                    sort: true,
                    align: 'center'
                },*/
                {
                    title: '好评率',
                    field: 'applauseRate',
                    sort: true,
                    align: 'center',
                    width: '8%'
                },
                {
                    title: '健康豆',
                    field: 'healthBeans',
                    sort: true,
                    align: 'center',
                    width: '8%'
                },
                {
                    title: '是否销售',
                    field: 'isMarket',
                    sort: true,
                    align: 'center',
                    width: '8%',
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
                    title: '操作',
                    //toolbar: '#operation',
                    align: 'left',
                    width: '20%',
                    templet:function(data){
                        let isMarket = data.isMarket;
                        if(0 == isMarket){
                            return '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="edit">修改</a>\n' +
                                   '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="delete">删除</a>\n' +
                                   '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="detail">详情</a>\n' +
                                   '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="offShelf">商品下架</a>';
                        }else if (2 == isMarket){
                            return '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="edit">修改</a>\n' +
                                   '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="delete">删除</a>\n' +
                                   '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="detail">详情</a>\n' +
                                   '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="onShelves">商品上架</a>\n'
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
            name: $("#name").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('goodsTable', {
            where: {
                name: $("#name").val()
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(goodsTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/data/goods/form/add", "新建药品信息",'100%','100%')
        }
    });

    table.on('tool(goodsTableFilter)', function (obj) {
        let id = obj.data.id;
        let event = obj.event;
        if (event === 'edit') {
            rc.openSaveDialog('/bobogou/data/goods/form/edit?id=' + id, "编辑药品信息",'100%','100%');
        } else if (event === 'onShelves') {
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
            rc.openViewDialog("/bobogou/data/goods/form/view?id="+id, "查看药品信息",'100%','100%')
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