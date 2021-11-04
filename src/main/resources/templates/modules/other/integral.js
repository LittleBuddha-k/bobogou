layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#integralTable',
        url: '/bobogou/other/integral/data',
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
                /*{
                    type: "checkbox"
                },*/
                {
                    title: '积分',
                    field: 'integral',
                    sort: true,
                    sortName: 'integral',
                    templet:function(data){
                        var integral = data.integral;
                        return integral;
                    }
                },
                {
                    title: '天数',
                    field: 'day',
                    sort: true,
                    sortName: 'day'
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
            integral: $("#integral").val(),
            day: $("#day").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(other-search-btn)', function (other) {
        //执行搜索重载
        table.reload('integralTable', {
            where: {
                integral: $("#integral").val(),
                day: $("#day").val()
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(integralTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/other/integral/form/add", "新建积分信息",'452px','250px')
        }
    });

    table.on('tool(integralTableFilter)', function (obj) {
        let ids = obj.data.id;
        if (obj.event === 'edit') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/other/integral/form/edit?id="+ids, "编辑积分信息",'452px','250px')
        }else if (obj.event === 'delete') {
            rc.confirm('确认要删除该信息吗？', function () {
                rc.post("/bobogou/other/integral/delete?ids=" + ids, '', function (data) {
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
    var checkStatus = table.checkStatus('integralTable'),
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
        table.reload('integralTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'other');
    })
}