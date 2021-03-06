layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#qualificationTable',
        url: '/bobogou/basic/qualification/data',
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
                    type: "radio"
                },*/
                {
                    title: '名称',
                    field: 'name',
                    sort: true,
                    sortName: 'name'
                },
                {
                    title: '审核状态',
                    field: 'status',
                    sortName: 'status',
                    align: "left",
                    templet: function (data) {
                        var status = data.status;
                        let dictName = rc.getDictName("act_status",status);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '操作',
                    align: "left",
                    templet: function (data) {
                        var status = data.status;
                        if(0 == status){
                            return '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="subTask">提交</a>\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="edit">修改</a>\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="delete">删除</a>\n';
                        }else if (3 == status || 5 == status || 7 == status || 9 == status || 11 == status){
                            return '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="edit">修改</a>\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="subTask">重新提交</a>\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="flow">跟踪</a>\n'+
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="delete">删除</a>\n';
                        }else if (8 == status || 10 == status){
                            return '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="flow">审核通过</a>\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="reSubTask">重新审核</a>\n';
                        }else{
                            return '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="">已提交</a>\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="flow">跟踪</a>\n';
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
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(other-search-btn)', function (other) {
        //执行搜索重载
        table.reload('qualificationTable', {
            where: {
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(qualificationTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/basic/qualification/form/add", "新建资质信息",'650px','400px')
        }
    });

    table.on('tool(qualificationTableFilter)', function (obj) {
        let ids = obj.data.id;
        if (obj.event === 'edit') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/basic/qualification/form/edit?id="+ids, "编辑资质信息",'650px','400px')
        }else if (obj.event === 'download') {  // 监听下载操作
            rc.downloadFile("/bobogou/basic/qualification/download?id=" + ids);
        }else if (obj.event === 'flow') {  // 监听添加操作
            var index = rc.openViewDialog("/bobogou/basic/signContract/flow?id="+ids, "合同签署审核历史信息",'1000px','600px')
        }else if (obj.event === 'subTask') {
            rc.confirm('是否提交处理？', function () {
                rc.post("/bobogou/basic/qualification/subTask?id=" + ids, '', function (data) {
                    if (data.code == 200) {
                        //执行搜索重载
                        refresh();
                        rc.success(data.msg);
                    } else {
                        rc.error(data.msg);
                    }
                });
            })
        } else if (obj.event === 'reSubTask') {
            rc.confirm('是否重审资质数据？', function() {
                rc.post("/bobogou/basic/qualification/reSubTask",{"id":ids} , function (data) {
                    if (data.code == 200) {
                        //执行搜索重载
                        refresh();
                        rc.success(data.msg);
                    } else {
                        rc.error(data.msg);
                    }
                });
            })
        }else if (obj.event === 'delete') {
            rc.confirm('确认要删除该资质信息吗？', function () {
                rc.post("/bobogou/basic/qualification/delete?ids=" + ids, '', function (data) {
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
    var checkStatus = table.checkStatus('qualificationTable'),
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
        table.reload('qualificationTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'other');
    })
}