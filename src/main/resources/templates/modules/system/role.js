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
                    field: 'name',
                    sort: true
                },
                {
                    title: '英文名称',
                    field: 'englishName',
                    sort: true,
                },
                {
                    align: 'left',
                    title: '操作',
                    templet: '#operation', align: 'left'
                },
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

    /*监听左上角工具栏*/
    table.on('toolbar(roleTableFilter)', function (obj) {
        var checkStatus = table.checkStatus('roleTable');
        var data = checkStatus.data;
        if (obj.event === 'add') {
            rc.openSaveDialog("/bobogou/system/role/form/add", "添加一级角色", '670px', '340px');
        }
    })

    //监听工具条
    table.on('tool(roleTableFilter)', function (obj) {
        let data = obj.data;
        let layEvent = obj.event;
        let id = obj.data.id;

        if (obj.event === 'view') {
            rc.openViewDialog("/bobogou/system/role/form/view?id=" + id, "查看角色信息", '670px', '340px')
        } else if (obj.event === "edit") {
            rc.openSaveDialog("/bobogou/system/role/form/edit?id=" + id, "编辑角色信息", '670px', '340px')
        }  else if (layEvent === 'addPermission') {
            let index = rc.openSelectionDialog("/bobogou/system/role/permissionPage?id=" + id, "设置权限",'75%','70%')
        }  else if (layEvent === 'addChildren') {
            rc.openSaveDialog("/bobogou/system/role/form/addChildren?parent.id=" + id, "添加下级角色", '670px', '340px')
        } else if (obj.event === "delete") {
            rc.confirm('确认要删除该信息吗？', function () {
                rc.post("/bobogou/system/role/delete?ids=" + id, '', function (data) {
                    if (data.code == 200) {
                        //执行搜索重载
                        renderTable();
                    } else {
                        rc.alert(data.msg);
                    }
                });
            })
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
    var checkStatus = table.checkStatus('roleTable'),
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
        table.reload('roleTable', {
            page: {
                curr: 1
            }
            , where: {}
        }, 'data');
    })
}