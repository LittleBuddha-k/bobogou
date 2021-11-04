layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#promptMessageTable',
        url: '/bobogou/other/promptMessage/data',
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
                /*{
                    type: "checkbox"
                },*/
                {
                    title: '标题',
                    field: 'title',
                    sort: true,
                    sortName: 'title',
                    templet:function(data){
                        var title = data.title;
                        return title;
                    }
                },/*
                {
                    title: '密码',
                    field: 'password',
                    sort: true,
                    sortName: 'password'
                },*/
                {
                    title: '概要信息',
                    field: 'outline',
                    sort: true,
                    sortName: 'outline'
                },
                {
                    title: '内容',
                    field: 'content',
                    sort: true,
                    sortName: 'content'
                },
                {
                    title: '类型',
                    field: 'type',
                    sort: true,
                    sortName: 'type',
                    templet:function(data){
                        var type = data.type;
                        let dictName = rc.getDictName("other_prompt_message_type",type);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },/*
                {
                    title: '更新人',
                    field: 'updateByName',
                    sort: true,
                    sortName: 'updateByName'
                },*/
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
            title: $("#title").val(),
            outline: $("#outline").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(other-search-btn)', function (other) {
        //执行搜索重载
        table.reload('promptMessageTable', {
            where: {
                title: $("#title").val(),
                outline: $("#outline").val()
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(promptMessageTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/other/promptMessage/form/add", "新建信息",'675px','340px')
        }
    });

    table.on('tool(promptMessageTableFilter)', function (obj) {
        let ids = obj.data.id;
        if (obj.event === 'delete') {
            rc.confirm('确认要删除该信息吗？', function () {
                rc.post("/bobogou/other/promptMessage/delete?ids=" + ids, '', function (data) {
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
    var checkStatus = table.checkStatus('promptMessageTable'),
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
        table.reload('promptMessageTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'other');
    })
}