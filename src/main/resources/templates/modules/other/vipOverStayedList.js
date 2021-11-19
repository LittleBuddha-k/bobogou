layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#customerUserTable',
        url: '/bobogou/other/customerUser/vipOverStayedData',
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
                    type: "checkbox"
                },*/
                {
                    title: '手机号',
                    field: 'phone',
                    minWidth: 120,
                    templet:function(data){
                        var valueArray = data.phone;
                        return valueArray;
                    }
                },
                {
                    title: '昵称',
                    field: 'nickname',
                    minWidth: 120,
                },
                {
                    title: '性别',
                    field: 'sex',
                    templet:function(data){
                        var sex = data.sex;
                        let dictName = rc.getDictName("user_member_sex",sex);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '会员等级',
                    field: 'member',
                    minWidth: 100,
                    templet:function(data){
                        var member = data.member;
                        let dictName = rc.getDictName("user_member_level",member);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '积分',
                    field: 'integral',
                },
                {
                    title: '健康豆',
                    field: 'healthBeans',
                },
                {
                    title: '签到时间',
                    field: 'signInTime',
                    minWidth: 170,
                },
                {
                    title: '消息接收状态',
                    field: 'messageStatus',
                    minWidth: 100,
                    templet:function(data){
                        var messageStatus = data.messageStatus;
                        let dictName = rc.getDictName("user_member_message_status",messageStatus);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '是否同意用户协议',
                    field: 'userAgreement',
                    minWidth: 150,
                    templet:function(data){
                        var userAgreement = data.userAgreement;
                        let dictName = rc.getDictName("user_member_user_agreement",userAgreement);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '会员申请状态',
                    field: 'applyStatus',
                    minWidth: 150,
                    templet:function(data){
                        var applyStatus = data.applyStatus;
                        let dictName = rc.getDictName("user_member_apply_status",applyStatus);
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
                    minWidth: '200'
                }
            ]
        ],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 10,
        page: true,
        skin: 'line',
        where: {
            phone: $("#phone").val(),
            nickname: $("#nickname").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(other-search-btn)', function (other) {
        //执行搜索重载
        table.reload('customerUserTable', {
            where: {
                phone: $("#phone").val(),
                nickname: $("#nickname").val()
            }
        });
        return false;
    });

    table.on('tool(customerUserTableFilter)', function (obj) {
        let id = obj.data.id;
        if (obj.event === 'recovery') {
            rc.confirm('确认要恢复该VIP吗？', function() {
                rc.post("/bobogou/other/customerUser/recoveryVip?id=" + id, '', function (data) {
                    if (data.success) {
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
    var checkStatus = table.checkStatus('customerUserTable'),
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
        table.reload('customerUserTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'other');
    })
}