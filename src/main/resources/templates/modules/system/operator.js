layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#operatorTable',
        url: '/bobogou/system/operator/data',
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
                    title: '登录名',
                    field: 'loginName',
                    sort: true,
                    width: '200'
                },
                {
                    title: '昵称',
                    field: 'nickname',
                    sort: true,
                    width: '200'
                },
                {
                    title: '电话',
                    field: 'phone',
                    sort: true,
                    width: '200'
                },
                {
                    title: '性别',
                    field: 'sex',
                    sort: true,
                    width: '80',
                    templet: function (data) {
                        var sex = data.sex;
                        let dictName = rc.getDictName("system_operator_sex",sex);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '消息状态',
                    field: 'messageStatus',
                    sort: true,
                    width: '200',
                    templet: function (data) {
                        var messageStatus = data.messageStatus;
                        let dictName = rc.getDictName("system_operator_message_status",messageStatus);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '工号',
                    field: 'workNumber',
                    sort: true,
                    width: '120'
                },
                {
                    title: '登录标识',
                    field: 'loginFlag',
                    sort: true,
                    width: '150',
                    templet: function (data) {
                        var loginFlag = data.loginFlag;
                        let dictName = rc.getDictName("system_operator_login_flag",loginFlag);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '操作',
                    toolbar: '#operation',
                    align: "center",
                    width: '250'
                }
            ]
        ],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 10,
        page: true,
        skin: 'line',
        where: {
            loginName: $("#loginName").val(),
            nickname: $("#nickname").val(),
            phone: $("#phone").val(),
            sex: $("#sex").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('operatorTable', {
            where: {
                loginName: $("#loginName").val(),
                nickname: $("#nickname").val(),
                phone: $("#phone").val(),
                sex: $("#sex").val()
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(operatorTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/system/operator/form/add", "新建用户信息", '730px', '85%')
        }
    });

    table.on('tool(operatorTableFilter)', function (obj) {
        var id = obj.data.id;
        if (obj.event === 'edit') {  // 监听修改操作
            rc.openSaveDialog('/bobogou/system/operator/form/edit?id=' + id, "编辑用户信息", '730px', '85%');
        } else if (obj.event === 'view') {  // 监听查看操作
            rc.openViewDialog('/bobogou/system/operator/form/view?id=' + id, "查看用户信息", '730px', '85%');
        } else if (obj.event === 'delete') {  // 监听删除操作
            rc.confirm('确认要删除该信息吗？', function () {
                rc.post("/bobogou/system/operator/delete?ids=" + id, '', function (data) {
                    if (data.code == 200) {
                        //执行搜索重载
                        refresh();
                    } else {
                        rc.alert(data.msg);
                    }
                });
            })
        } else if (obj.event === 'setRole') {
            var index = rc.openSelectionDialog("/bobogou/system/operator/addRolePage?id=" + id, "设置角色", '100%', '100%')
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
    var checkStatus = table.checkStatus('operatorTable'),
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
        table.reload('operatorTable', {
            page: {
                curr: 1
            }
            , where: {}
        }, 'data');
    })
}