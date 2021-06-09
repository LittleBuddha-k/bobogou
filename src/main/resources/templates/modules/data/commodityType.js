layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#commodityTypeTable',
        url: '/bobogou/data/commodityType/data',
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
                    title: '分类名称',
                    field: 'name',
                    sort: true,
                    sortName: 'name',
                    templet:function(data){
                        var valueArray = data.name;
                        return valueArray;
                    }
                },
                {
                    title: '分类图标地址',
                    field: 'icon',
                    sort: true,
                    sortName: 'icon'
                },
                {
                    title: '父级分类ID',
                    field: 'parentId',
                    sort: true,
                    sortName: 'parentId'
                },
                {
                    title: '最后操作人ID',
                    field: 'accountId',
                    sort: true,
                    sortName: 'accountId'
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
            name: $("#name").val(),
            icon: $("#icon").val(),
            parentId: $("#parentId").val(),
            accountId: $("#accountId").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('commodityTypeTable', {
            where: {
                name: $("#name").val(),
                icon: $("#icon").val(),
                parentId: $("#parentId").val(),
                accountId: $("#accountId").val()
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(commodityTypeTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/data/commodityType/form/add", "新建商品分类信息")
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
                rc.openSaveDialog('/bobogou/data/commodityType/form/edit?id=' + ids, "编辑商品分类信息");
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
                rc.openViewDialog('/bobogou/data/commodityType/form/view?id=' + ids, "查看商品分类信息");
            }
            $(window).on("resize", function () {
                layer.full(index);
            });
        } else if (obj.event === 'delete') {  // 监听删除操作
            let ids = getIdSelections(table);
            if (ids == null || ids == '') {
                rc.alert("请至少选择一条数据")
            } else {
                rc.post("/bobogou/data/commodityType/deleteByPhysics?ids=" + ids, '',function (data) {
                    if(data.code == 200){
                        //执行搜索重载
                        refresh();
                        rc.alert(data.msg);
                    }else{
                        rc.alert(data.msg);
                    }
                });
            }
        } else if (obj.event === 'import') {  // 监听删除操作
            rc.openImportDialog("/bobogou/data/commodityType/importTemplate", "/bobogou/data/commodityType/importFile")
        } else if (obj.event === 'export') {  // 监听删除操作
            rc.downloadFile("/bobogou/data/commodityType/exportFile?" + $("#searchForm").serialize());
        }
    });

    table.on('tool(commodityTypeTableFilter)', function (obj) {
        var id = obj.data.id;
        var index = rc.openSelectionDialog("/bobogou/data/commodityType/addRolePage?id=" + id, "设置角色")
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
    var checkStatus = table.checkStatus('commodityTypeTable'),
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
        table.reload('commodityTypeTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'data');
    })
}