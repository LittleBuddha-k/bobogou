layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#royaltyRecordTable',
        url: '/bobogou/basic/royaltyRecord/data',
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
                /*{
                    type: "checkbox"
                },*/
                {
                    title: '用户',
                    field: 'userName',
                    sort: true,
                    sortName: 'userName',
                    width: '12.5%',
                    templet:function(data){
                        let userName = data.userName;
                        return userName;
                    }
                },
                {
                    title: '类目',
                    field: 'title',
                    sort: true,
                    sortName: 'title',
                    width: '12.5%'
                },
                {
                    title: '提成金额',
                    field: 'money',
                    sort: true,
                    sortName: 'money',
                    width: '12.5%'
                },
                {
                    title: '提成比例',
                    field: 'atio',
                    sort: true,
                    sortName: 'atio',
                    width: '12.5%'
                },
                {
                    title: '类型',
                    field: 'type',
                    sort: true,
                    sortName: 'type',
                    width: '12.5%',
                    templet: function (data) {
                        var type = data.type;
                        let dictName = rc.getDictName("basic_royalty_record_type",type);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '微信提现单号',
                    field: 'orderNumber',
                    sort: true,
                    sortName: 'orderNumber',
                    width: '10%'
                },
                {
                    title: '提现是否成功',
                    field: 'status',
                    sort: true,
                    sortName: 'status',
                    width: '12.5%',
                    templet: function (data) {
                        var status = data.status;
                        let dictName = rc.getDictName("basic_royalty_record_status",status);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '资金状态',
                    field: 'state',
                    sort: true,
                    sortName: 'state',
                    width: '12.5%',
                    templet: function (data) {
                        var state = data.state;
                        let dictName = rc.getDictName("basic_royalty_record_state",state);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '提现成功时间',
                    field: 'successfulTime',
                    sort: true,
                    sortName: 'successfulTime',
                    width: '12.5%',
                    templet:function(data){
                        let successfulTime = data.successfulTime;
                        if (successfulTime != null && successfulTime != ''){
                            return rc.dateFormat(successfulTime);
                        }else {
                            return "";
                        }
                    }
                }/*,
                {
                    title: '操作',
                    toolbar: '#operation',
                    align: "center",
                    width: '12%'
                }*/
            ]
        ],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 10,
        page: true,
        skin: 'line',
        where: {
            userId: $("#userId").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('royaltyRecordTable', {
            where: {
                userId: $("#userId").val()
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(royaltyRecordTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            let index = rc.openSaveDialog("/bobogou/basic/royaltyRecord/form/add", "新建厂商信息",'100%','100%')
        }
    });

    table.on('tool(royaltyRecordTableFilter)', function (obj) {
        let id = obj.data.id;
        let event = obj.event;
        if (event === 'edit') {
            rc.openSaveDialog('/bobogou/basic/royaltyRecord/form/edit?id=' + id, "编辑厂商信息", '100%', '100%');
        }else if (event === 'view') {
            rc.openSaveDialog('/bobogou/basic/royaltyRecord/form/view?id=' + id, "查看厂商信息", '100%', '100%');
        }else if (event === 'delete') {
            rc.confirm('确认要删除该厂商信息吗？', function () {
                rc.post("/bobogou/basic/royaltyRecord/delete?ids=" + id, '', function (data) {
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
    var checkStatus = table.checkStatus('royaltyRecordTable'),
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
        table.reload('royaltyRecordTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'data');
    })
}