layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#contractTable',
        url: '/bobogou/basic/contract/data',
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
                    title: '名称',
                    field: 'name',
                    sort: true,
                    sortName: 'name'
                },
                {
                    title: '类型',
                    field: 'type',
                    sort: true,
                    sortName: 'type',
                    templet: function (data) {
                        var type = data.type;
                        if (1 == type){
                            return "药房合同";
                        }else if (2 == type){
                            return "诊所合同";
                        }else if (3 == type){
                            return "医院合同";
                        }else if (4 == type){
                            return "经纪人合同";
                        }else if (5 == type){
                            return "经理人合同";
                        }else if (6 == type){
                            return "厂商合同";
                        }else {
                            return "未知";
                        }
                    }
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
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(other-search-btn)', function (other) {
        //执行搜索重载
        table.reload('contractTable', {
            where: {
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(contractTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/basic/contract/form/add", "新建合同信息",'718px','500px')
        }
    });

    table.on('tool(contractTableFilter)', function (obj) {
        let ids = obj.data.id;
        if (obj.event === 'edit') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/basic/contract/form/edit?id="+ids, "编辑合同信息",'718px','500px')
        }else if (obj.event === 'download') {  // 监听下载操作
            rc.downloadFile("/bobogou/basic/contract/download?id=" + ids);
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
    var checkStatus = table.checkStatus('contractTable'),
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
        table.reload('contractTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'other');
    })
}