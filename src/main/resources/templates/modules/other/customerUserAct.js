layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    var init = table.render({
        elem: '#customerUserActTable',
        url: '/bobogou/other/customerUser/flowData',
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
                    title: '执行环节',
                    field: 'executionLink',
                    align: "left",
                    sortName: 'executionLink'
                },
                {
                    title: '执行人',
                    field: 'executionName',
                    align: "left",
                    sortName: 'executionName'
                },
                {
                    title: '执行人角色名称',
                    field: 'roleName',
                    align: "left",
                    sortName: 'roleName'
                },
                {
                    title: '开始时间',
                    field: 'beginDate',
                    align: "left",
                    sortName: 'beginDate',
                    templet: function (data) {
                        var beginDate = data.beginDate;
                        return rc.dateFormat(beginDate);
                    }
                }/*,
                {
                    title: '结束时间',
                    field: 'endDate',
                    align: "left",
                    sortName: 'endDate',
                    templet: function (data) {
                        var endDate = data.endDate;
                            return rc.dateFormat(endDate);
                    }
                }*/
            ]
        ],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 10,
        page: false,
        skin: 'line',
        where: {
            dataId: $("#id").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });
});