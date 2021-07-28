layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#signContractTable',
        url: '/bobogou/basic/signContract/data',
        method: 'GET',
        request: {
            pageName: 'pageNo', // page
            limitName: 'pageSize' // limit
        },//重命名参数名称
        done: function (res) {
            //做checkbox回显
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{other: [], count: 99} other为当前页数据、count为数据总长度
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
                    type: "radio"
                },
                {
                    title: '甲方',
                    field: 'partAName',
                    align: "left",
                    sortName: 'partAName'
                },
                {
                    title: '乙方',
                    field: 'partBName',
                    align: "left",
                    sortName: 'partBName'
                },
                {
                    title: '审核状态',
                    field: 'status',
                    sortName: 'status',
                    align: "left",
                    templet: function (data) {
                        var status = data.status;
                        return 0;
                    }
                },
                {
                    title: '操作',
                    toolbar: '#operation',
                    align: "left"
                }
            ]
        ],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 10,
        page: true,
        skin: 'line',
        where: {
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(other-search-btn)', function (other) {
        //执行搜索重载
        table.reload('signContractTable', {
            where: {
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(signContractTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/basic/signContract/form/add", "新建合同签署信息",'718px','600px')
        }
    });

    table.on('tool(signContractTableFilter)', function (obj) {
        let ids = obj.data.id;
        if (obj.event === 'edit') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/basic/signContract/form/edit?id="+ids, "编辑合同签署信息",'718px','600px')
        }else if (obj.event === 'delete') {
            rc.confirm('确认要删除该合同签署信息吗？', function () {
                rc.post("/bobogou/basic/signContract/delete?ids=" + ids, '', function (data) {
                    if (data.code == 200) {
                        //执行搜索重载
                        refresh();
                        rc.success(data.msg);
                    } else {
                        rc.error(data.msg);
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
    var checkStatus = table.checkStatus('signContractTable'),
        other = checkStatus.other;
    let ids = "";
    for (let i = 0; i < other.length; i++) {
        ids = ids + other[i].id + ",";
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
        table.reload('signContractTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'other');
    })
}