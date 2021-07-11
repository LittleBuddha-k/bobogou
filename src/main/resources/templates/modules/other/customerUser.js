layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#customerUserTable',
        url: '/bobogou/other/customerUser/data',
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
                    type: "checkbox"
                },
                {
                    title: '手机号',
                    field: 'phone',
                    sort: true,
                    sortName: 'phone',
                    width: '10%',
                    templet:function(data){
                        var valueArray = data.phone;
                        return valueArray;
                    }
                },/*
                {
                    title: '密码',
                    field: 'password',
                    sort: true,
                    sortName: 'password'
                },
                {
                    title: '头像地址',
                    field: 'header',
                    sort: true,
                    sortName: 'header'
                },*/
                {
                    title: '昵称',
                    field: 'nickname',
                    sort: true,
                    sortName: 'nickname',
                    width: '10%'
                },
                {
                    title: '性别',
                    field: 'sex',
                    sort: true,
                    sortName: 'sex',
                    width: '5%',
                    templet:function(data){
                        let sex = data.sex;
                        if(0 == sex){
                            return "未知"
                        }else if(1 == sex){
                            return "男"
                        }else if(2 == sex){
                            return "女"
                        }else {
                            return "未知"
                        }
                    }
                },
                {
                    title: '会员等级',
                    field: 'member',
                    sort: true,
                    sortName: 'member',
                    width: '10%',
                    templet:function(data){
                        let member = data.member;
                        if(0 == member){
                            return "普通会员"
                        }else if(1 == member){
                            return "VIP"
                        }else {
                            return "未知"
                        }
                    }
                },
                {
                    title: '积分',
                    field: 'integral',
                    sort: true,
                    width: '10%',
                    sortName: 'integral'
                },
                {
                    title: '健康豆',
                    field: 'healthBeans',
                    sort: true,
                    width: '10%',
                    sortName: 'healthBeans'
                },/*
                {
                    title: '收藏数量',
                    field: 'collectNumber',
                    sort: true,
                    sortName: 'collectNumber'
                },*/
                {
                    title: '签到时间',
                    field: 'signInTime',
                    sort: true,
                    sortName: 'signInTime',
                    width: '10%',
                },
                {
                    title: '消息接收状态',
                    field: 'messageStatus',
                    sort: true,
                    sortName: 'messageStatus',
                    width: '10%',
                    templet:function(data){
                        let messageStatus = data.messageStatus;
                        if(0 == messageStatus){
                            return "关闭"
                        }else if(1 == messageStatus){
                            return "打开"
                        }else {
                            return "未知"
                        }
                    }
                },
                {
                    title: '是否同意用户协议',
                    field: 'userAgreement',
                    sort: true,
                    sortName: 'userAgreement',
                    width: '10%',
                    templet:function(data){
                        let userAgreement = data.userAgreement;
                        if(0 == userAgreement){
                            return "未同意"
                        }else if(1 == userAgreement){
                            return "已同意"
                        }else {
                            return "未知"
                        }
                    }
                },
                {
                    title: '会员申请状态',
                    field: 'applyStatus',
                    sort: true,
                    sortName: 'applyStatus',
                    width: '10%',
                    templet:function(data){
                        let applyStatus = data.applyStatus;
                        if(0 == applyStatus){
                            return '未申请'
                        }else if(1 == applyStatus){
                            return "已申请"
                        }else if(2 == applyStatus){
                            return "已同意"
                        }else if(3 == applyStatus){
                            return "已拒绝"
                        }else {
                            return "未知"
                        }
                    }
                },
                {
                    title: '操作',
                    //toolbar: '#operation',
                    align: "center",
                    width: '10%',
                    templet:function(data){
                        let applyStatus = data.applyStatus;
                        if(1 == applyStatus){
                            return '\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs other-count-edit" lay-event="vip">VIP审核</a>\n' +
                                '<a class="layui-btn layui-btn-normal layui-btn-xs other-count-edit" lay-event="detail">详情</a>'
                        }else if(2 == applyStatus){
                            return "已同意"
                        }else if(3 == applyStatus){
                            return "已拒绝"
                        }else if(0 == applyStatus){
                            return "未申请"
                        }else {
                            return "未知"
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
        if (obj.event === 'detail') {
            let index = rc.openViewDialog("/bobogou/other/customerUser/form/detail?id=" + id, "详情","700px","560px")
        } else if (obj.event === 'vip') {
            let index = rc.openViewDialogNoClose("/bobogou/other/customerUser/vipPage?userId=" + id, "VIP审核","700px","580px")
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