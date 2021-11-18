layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#qualificationTable',
        url: '/bobogou/basic/qualification/downloadData',
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
        //toolbar: '#toolBar',
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
                    toolbar: '#operation'
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

    table.on('tool(qualificationTableFilter)', function (obj) {
        let ids = obj.data.id;
        if (obj.event === 'download') {  // 监听下载操作
            rc.downloadFile("/bobogou/basic/qualification/download?id=" + ids);
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