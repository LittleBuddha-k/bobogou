layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#roleTable',
        url: '/bobogou/system/role/data',
        method: 'GET',
        request: {
            pageName: 'pageNo', // page
            limitName: 'pageSize' // limit
        },//重命名参数名称
        done: function (res) {
            //做checkbox回显
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            //console.log(res);
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
                    title: '角色名称',
                    field: 'name'
                },
                {
                    title: '英文名称',
                    field: 'englishName',
                    sort: true
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
            englishName: $("#englishName").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('roleTable', {
            where: {
                name: $("#name").val(),
                englishName: $("#englishName").val()
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(roleTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            let index = rc.openSaveDialog("/bobogou/system/role/form/add", "新建角色信息",'75%','70%')
        } else if (obj.event === 'edit') {  // 监听修改操作
            let ids = getIdSelections(table, 'roleTable') + "";
            let idArr = ids.toString().split(",");
            if (idArr[1]) {
                rc.alert("只能选择一条数据")
            } else if (ids.length <= 0) {
                rc.alert("请至少选择一条数据")
            } else if (idArr[0]) {
                ids = idArr[0];
                rc.openSaveDialog('/bobogou/system/role/form/edit?id=' + ids, "编辑角色信息",'75%','70%');
            }
            $(window).on("resize", function () {
                layer.full(index);
            });
        } else if (obj.event === 'view') {  // 监听查看操作
            let ids = getIdSelections(table, 'roleTable');
            let idArr = ids.toString().split(",");
            if (idArr[1]) {
                rc.alert("只能选择一条数据")
            } else if (ids.length <= 0) {
                rc.alert("请至少选择一条数据")
            } else if (idArr[0]) {
                ids = idArr[0];
            }
            $(window).on("resize", function () {
                layer.full(index);
            });
        } else if (obj.event === 'delete') {  // 监听删除操作
            let ids = getIdSelections(table, 'roleTable');
            if (ids == null || ids == '') {
                rc.alert("请至少选择一条数据")
            } else {
                rc.confirm('确认要删除该信息吗？', function() {
                    rc.post("/bobogou/system/role/delete?ids=" + ids, "", 'roleTable', table);
                })
            }
        } else if (obj.event === 'import') {  // 监听删除操作
            rc.openImportDialog("/bobogou/forecast/twoColorBall/importTemplate", "/bobogou/forecast/twoColorBall/importFile")
        } else if (obj.event === 'export') {  // 监听删除操作
            rc.downloadFile("/bobogou/forecast/twoColorBall/exportFile?" + $("#twoColorBallSearchForm").serialize());
        }
    });

    table.on('tool(roleTableFilter)', function (obj) {
        let id = obj.data.id;
        let event = obj.event;
        if(event == 'edit'){
            rc.openSaveDialog('/bobogou/system/role/form/edit?id=' + id, "编辑角色信息",'75%','70%');
        }else if (event == 'view'){
            rc.openSaveDialog('/bobogou/system/role/form/view?id=' + id, "查看角色信息",'75%','70%');
        }else if (event == 'addPermission'){
            let index = rc.openSelectionDialog("/bobogou/system/role/permissionPage?id=" + id, "设置权限",'75%','70%')
        }else if (event == 'delete'){
            rc.confirm('确认要删除该信息吗？', function () {
                rc.post("/bobogou/system/role/delete?ids=" + id, '', function (data) {
                    if (data.code == 200) {
                        //执行搜索重载
                        refresh();
                    } else {
                        rc.alert(data.msg);
                    }
                });
            })
        }
    });
});

/**
 * 获取layui table 复选框的id
 * @param table -- table = layui.table;
 * @param tableId -- layui table 的id
 * @returns {string}
 */
function getIdSelections(table, tableId) {
    var checkStatus = table.checkStatus(tableId),
        data = checkStatus.data;
    let ids = "";
    for (let i = 0; i < data.length; i++) {
        ids = ids + data[i].id + ",";
    }
    return ids;
}

function refresh() {
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;

        //执行搜索重载
        table.reload('roleTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'data');
    })
}