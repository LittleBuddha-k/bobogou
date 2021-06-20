layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#regionGoodsTable',
        url: '/bobogou/data/regionGoods/data',
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
                    title: '省',
                    field: 'provinceName',
                    sort: true,
                    sortName: 'provinceName'/*,
                    templet:function(data){
                        var valueArray = data.provinceName;
                        return valueArray;
                    }*/
                },
                {
                    title: '市',
                    field: 'cityName',
                    sort: true,
                    sortName: 'cityName'
                },
                {
                    title: '区',
                    field: 'areaName',
                    sort: true,
                    sortName: 'areaName'
                },
                {
                    title: '街道',
                    field: 'streetName',
                    sort: true,
                    sortName: 'streetName'
                },
                {
                    title: '商品',
                    field: 'goodsName',
                    sort: true,
                    sortName: 'goodsName'
                },
                {
                    title: '商品数量',
                    field: 'amount',
                    sort: true,
                    sortName: 'amount'
                },
                {
                    title: '销量',
                    field: 'salesVolume',
                    sort: true,
                    sortName: 'salesVolume'
                },
                {
                    title: '是否在售',
                    field: 'isMarket',
                    sort: true,
                    sortName: 'isMarket'
                },
                {
                    title: '修改人',
                    field: 'updateByName',
                    sort: true,
                    sortName: 'updateByName'
                },
                {
                    title: '操作',
                    toolbar: '#operation',
                    align: "center"
                }
            ]
        ],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 10,
        page: true,
        skin: 'line',
        where: {
            provinceId: $("#code").val(),
            cityId: $("#name").val(),
            districtId: $("#shortName").val(),
            streetId: $("#cityCode").val(),
            goodsId: $("#lng").val(),
            amount: $("#lat").val(),
            salesVolume: $("#sort").val(),
            isMarket: $("#remarks").val(),
            accountId: $("#status").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('regionGoodsTable', {
            where: {
                provinceId: $("#code").val(),
                cityId: $("#name").val(),
                districtId: $("#shortName").val(),
                streetId: $("#cityCode").val(),
                goodsId: $("#lng").val(),
                amount: $("#lat").val(),
                salesVolume: $("#sort").val(),
                isMarket: $("#remarks").val(),
                accountId: $("#status").val()
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(regionGoodsTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/data/regionGoods/form/add", "新建地域商品信息",'75%','70%')
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
                rc.openSaveDialog('/bobogou/data/regionGoods/form/edit?id=' + ids, "编辑地域商品信息",'75%','70%');
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
                rc.openViewDialog('/bobogou/data/regionGoods/form/view?id=' + ids, "查看地域商品信息",'75%','70%');
            }
            $(window).on("resize", function () {
                layer.full(index);
            });
        } else if (obj.event === 'delete') {  // 监听删除操作
            let ids = getIdSelections(table);
            if (ids == null || ids == '') {
                rc.alert("请至少选择一条数据")
            } else {
                rc.confirm('确认要删除该信息吗？', function() {
                    rc.post("/bobogou/data/regionGoods/delete?ids=" + ids, '', function (data) {
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
            rc.openImportDialog("/bobogou/data/regionGoods/importTemplate", "/bobogou/data/regionGoods/importFile")
        } else if (obj.event === 'export') {  // 监听删除操作
            rc.downloadFile("/bobogou/data/regionGoods/exportFile?" + $("#searchForm").serialize());
        }
    });

    table.on('tool(regionGoodsTableFilter)', function (obj) {
        var id = obj.data.id;
        var index = rc.openSelectionDialog("/bobogou/data/regionGoods/addRolePage?id=" + id, "设置角色")
        $(window).on("resize", function () {
            layer.full(index);
        });
        return false;
    });
});

/**
 * 获取layui table 复选框的id
 * @param table -- table = layui.table;
 * @param tableId -- layui table 的id
 * @returns {string}
 */
function getIdSelections(table) {
    var checkStatus = table.checkStatus('regionGoodsTable'),
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
        table.reload('regionGoodsTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'data');
    })
}