layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    var init = table.render({
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
                        if(0 == status){
                            return "未提交";
                        }else if(1 == status){
                            return "已提交";
                        }else if(2 == status){
                            return "区级经纪人已通过";
                        }else if(3 == status){
                            return "区级经纪人已拒绝";
                        }else if(4 == status){
                            return "市级经纪人已通过";
                        }else if(5 == status){
                            return "市级经纪人已通过";
                        }else if(6 == status){
                            return "省级经纪人已通过";
                        }else if(7 == status){
                            return "省级经纪人已通过";
                        }else if(8 == status){
                            return "超级管理员助理已通过";
                        }else if(9 == status){
                            return "超级管理员助理已拒绝";
                        }else if(10 == status){
                            return "超级管理员已通过";
                        }else if(11 == status){
                            return "超级管理员已拒绝";
                        }else {
                            return "未知";
                        }
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
                        }else{
                            return '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="">已提交</a>\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="flow">跟踪</a>\n';
                        }
                        return 0;
                    }
                }
            ]
        ],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 10,
        page: true,
        skin: 'line',
        where: {
            partAName: $("#partAName").val(),
            partBName: $("#partBName").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(other-search-btn)', function (other) {
        //执行搜索重载
        table.reload('signContractTable', {
            where: {
                partAName: $("#partAName").val(),
                partBName: $("#partBName").val()
            }
        });
        return false;
    });

    $("#reset").click(function () {
        $("#partAName").val("");
        $("#partBName").val("");
        init();
    })

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
        }else if (obj.event === 'flow') {  // 监听添加操作
            var index = rc.openViewDialog("/bobogou/basic/signContract/flow?dataId="+ids, "合同签署审核历史信息",'1000px','600px')
        }else if (obj.event === 'subTask') {
            rc.confirm('是否提交处理？', function () {
                rc.post("/bobogou/basic/signContract/subTask?id=" + ids, '', function (data) {
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