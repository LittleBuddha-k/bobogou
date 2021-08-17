layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#vipTable',
        url: '/bobogou/other/vip/data',
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

        ],
        cols: [
            [
                {
                    type: "checkbox"
                },
                {
                    title: '名称',
                    field: 'name',
                    sort: true,
                    sortName: 'name',
                    templet:function(data){
                        var name = data.name;
                        return name;
                    }
                },
                {
                    title: '时间',
                    field: 'time',
                    sort: true,
                    sortName: 'time',
                    templet:function(data){
                        var time = data.time;
                        return time + "天";
                    }
                },
                {
                    title: '类型',
                    field: 'type',
                    sort: true,
                    sortName: 'type',
                    templet:function(data){
                        var type = data.type;
                        let dictName = rc.getDictName("other_vip_rule",type);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
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
        table.reload('vipTable', {
            where: {
                
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(vipTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/other/vip/form/add", "新建vip规则信息",'675px','270px')
        }
    });

    table.on('tool(vipTableFilter)', function (obj) {
        let id = obj.data.id;
        if (obj.event === 'edit') {
            var index = rc.openSaveDialog("/bobogou/other/vip/form/edit?id=" + id, "编辑vip规则信息",'675px','270px')
        }else if (obj.event === 'delete') {
            rc.confirm('确认要删除该vip规则信息吗？', function () {
                rc.post("/bobogou/other/vip/delete?ids=" + id, '', function (data) {
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
    var checkStatus = table.checkStatus('vipTable'),
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
        table.reload('vipTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'other');
    })
}