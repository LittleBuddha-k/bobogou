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
                    templet:function(other){
                        var valueArray = other.phone;
                        return valueArray;
                    }
                },
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
                },
                {
                    title: '昵称',
                    field: 'nickname',
                    sort: true,
                    sortName: 'nickname'
                },
                {
                    title: '性别',
                    field: 'sex',
                    sort: true,
                    sortName: 'sex'
                },
                {
                    title: '会员等级',
                    field: 'member',
                    sort: true,
                    sortName: 'member'
                },
                {
                    title: '积分',
                    field: 'integral',
                    sort: true,
                    sortName: 'integral'
                },
                {
                    title: '健康豆',
                    field: 'healthBeans',
                    sort: true,
                    sortName: 'healthBeans'
                },
                {
                    title: '收藏数量',
                    field: 'collectNumber',
                    sort: true,
                    sortName: 'collectNumber'
                },
                {
                    title: '签到时间',
                    field: 'signInTime',
                    sort: true,
                    sortName: 'signInTime'
                },
                {
                    title: '消息接收状态',
                    field: 'messageStatus',
                    sort: true,
                    sortName: 'messageStatus'
                },
                {
                    title: '是否同意用户协议',
                    field: 'userAgreement',
                    sort: true,
                    sortName: 'userAgreement'
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
            let index = rc.openSaveDialog("/bobogou/other/customerUser/form/detail?id=" + id, "详情")
        } else if (obj.event === 'vip') {
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