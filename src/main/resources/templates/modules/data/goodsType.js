layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#goodsTypeTable',
        url: '/bobogou/data/goodsType/data',
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
                    title: '图标展示',
                    field: 'icon',
                    sort: true,
                    sortName: 'icon',
                    templet: function (data) {
                        var icon = data.icon;
                        return '<img src="' + icon + '" alt="" style="width: 47px;height: 33px;" class="layui-upload-img">'
                    }
                },
                {
                    title: '父级',
                    field: 'parentName',
                    sort: true,
                    sortName: 'goodsType.name'
                },/*
                {
                    title: '最后操作人ID',
                    field: 'accountId',
                    sort: true,
                    sortName: 'accountId'
                },*/
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
            level: $("#level").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('goodsTypeTable', {
            where: {
                name: $("#name").val(),
                level: $("#level").val()
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(goodsTypeTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            let index = rc.openSaveDialog("/bobogou/data/goodsType/form/add", "新建商品分类信息",'75%','70%')
        }
    });

    table.on('tool(goodsTypeTableFilter)', function (obj) {
        let id = obj.data.id;
        let event = obj.event;
        if (event === 'edit') {
            rc.openSaveDialog('/bobogou/data/goodsType/form/edit?id=' + id, "编辑信息", '100%', '100%');
        } else if (event === 'delete') {
            rc.confirm('确认要删除该信息吗？', function () {
                rc.post("/bobogou/data/goodsType/delete?ids=" + id, '', function (data) {
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
    });
});

/**
 * 获取layui table 复选框的id
 * @param table -- table = layui.table;
 * @param tableId -- layui table 的id
 * @returns {string}
 */
function getIdSelections(table) {
    var checkStatus = table.checkStatus('goodsTypeTable'),
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
        table.reload('goodsTypeTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'data');
    })
}