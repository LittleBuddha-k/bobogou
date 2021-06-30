layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#bannerTable',
        url: '/bobogou/data/banner/data',
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
                },/*
                {
                    title: '地址',
                    field: 'url',
                    sort: true,
                    sortName: 'url',
                    templet:function(data){
                        var valueArray = data.url;
                        return valueArray;
                    }
                },*/
                {
                    title: '展示位置',
                    field: 'type',
                    sort: true,
                    sortName: 'type',
                    templet:function(data){
                        let type = data.type;
                        if(1 == type){
                            return "首页";
                        }else {
                            return "未知";
                        }
                    }
                },/*
                {
                    title: '链接地址',
                    field: 'link',
                    sort: true,
                    sortName: 'link'
                },*/
                {
                    title: '状态',
                    field: 'status',
                    sort: true,
                    sortName: 'status',
                    templet:function(data){
                        let status = data.status;
                        if(0 == status){
                            return "隐藏";
                        }else if (1 == status){
                            return "显示";
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
            type: $("#type").val(),
            status: $("#status").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('bannerTable', {
            where: {
                type: $("#type").val(),
                status: $("#status").val()
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(bannerTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            let index = rc.openSaveDialog("/bobogou/data/banner/form/add", "新建轮播图信息",'670px','335px')
        }
    });

    table.on('tool(bannerTableFilter)', function (obj) {
        let id = obj.data.id;
        let event = obj.event;

        if('edit' == event){
            rc.openSaveDialog('/bobogou/data/banner/form/edit?id=' + id, "编辑轮播图信息",'670px','335px');
        }else if ('delete' == event){
            rc.confirm('确认要删除该轮播图信息吗？', function() {
                rc.post("/bobogou/data/banner/delete?ids=" + id, '', function (data) {
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
    var checkStatus = table.checkStatus('bannerTable'),
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
        table.reload('bannerTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'data');
    })
}