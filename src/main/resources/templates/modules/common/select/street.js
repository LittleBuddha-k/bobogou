layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    let areaIds = $("#areaIds").val();

    table.render({
        elem: '#streetTable',
        url: '/bobogou/data/street/noPageByArea?areaIds=' + areaIds,
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
                {
                    type: "checkbox",
                    width: 150
                },
                {
                    title: '街道名称',
                    field: 'name',
                    sort: true,
                    sortName: 'name'
                },
                {
                    title: '简称',
                    field: 'shortName',
                    sort: true,
                    sortName: 'shortName'
                }
            ]
        ],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 10,
        page: false,
        skin: 'line',
        where: {
            code: $("#code").val(),
            name: $("#name").val(),
            shortName: $("#shortName").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('streetTable', {
            where: {
                code: $("#code").val(),
                name: $("#name").val(),
                shortName: $("#shortName").val()
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(streetTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/data/street/form/add", "新建街道信息",'75%','70%')
            $(window).on("resize", function () {
                layer.full(index);
            });
        } else if (obj.event === 'edit') {  // 监听修改操作
            let ids = getIdSelections(table) + "";
            let idArr = ids.toString().split(",");
            if (idArr[1]) {
                rc.alert("只能选择一条数据")
            } else if (ids.length <= 0) {
                rc.alert("请至少选择一条数据")
            } else if (idArr[0]) {
                ids = idArr[0];
                rc.openSaveDialog('/bobogou/data/street/form/edit?id=' + ids, "编辑街道信息",'75%','70%');
            }
            $(window).on("resize", function () {
                layer.full(index);
            });
        } else if (obj.event === 'view') {  // 监听查看操作
            let ids = getIdSelections(table);
            let idArr = ids.toString().split(",");
            if (idArr[1]) {
                rc.alert("只能选择一条数据")
            } else if (ids.length <= 0) {
                rc.alert("请至少选择一条数据")
            } else if (idArr[0]) {
                ids = idArr[0];
                rc.openViewDialog('/bobogou/data/street/form/view?id=' + ids, "查看街道信息",'75%','70%');
            }
            $(window).on("resize", function () {
                layer.full(index);
            });
        } else if (obj.event === 'delete') {  // 监听删除操作
            let ids = getIdSelections(table);
            if (ids == null || ids == '') {
                rc.alert("请至少选择一条数据")
            } else {
                rc.confirm('确认要删除该信息吗？', function() {
                    rc.post("/bobogou/data/street/delete?ids=" + ids, '', function (data) {
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
        } else if (obj.event === 'import') {  // 监听删除操作
            rc.openImportDialog("/bobogou/data/street/importTemplate", "/bobogou/data/street/importFile")
        } else if (obj.event === 'export') {  // 监听删除操作
            rc.downloadFile("/bobogou/data/street/exportFile?" + $("#searchForm").serialize());
        }
    });

    table.on('tool(streetTableFilter)', function (obj) {
        var id = obj.data.id;
        var index = rc.openSelectionDialog("/bobogou/data/street/addRolePage?id=" + id, "设置角色")
        $(window).on("resize", function () {
            layer.full(index);
        });
        return false;
    });

    //监视列表查找单选框
    form.on('radio(status)', function(data){
        //console.log(data.value); //被点击的radio的value值
        let status = data.value;
        $("#status").val(status);
    });
});

/**
 * 获取layui table 复选框的id
 * @param table -- table = layui.table;
 * @param tableId -- layui table 的id
 * @returns {string}
 */
function getIdSelections(table) {
    var checkStatus = table.checkStatus('streetTable'),
        data = checkStatus.data;
    let ids = "";
    for (let i = 0; i < data.length; i++) {
        ids = ids + data[i].id + ",";
    }
    ;
    return ids;
}

function getSelector() {
    let ids = "";
    let name = "";
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;

        var checkStatus = table.checkStatus('streetTable'),
            data = checkStatus.data;

        if (data.length > 0){
            for (let i = 0;i < data.length ; i++){
                if (ids == ""){
                    ids = data[i].id;
                }else {
                    ids = ids + "," + data[i].id;
                }
                if (name == ""){
                    name = data[i].name;
                }else {
                    name = name + "," + data[i].name;
                }
            }
        }
    })
    return ids + "." + name;
}

function refresh() {
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;

        //执行搜索重载
        table.reload('streetTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'data');
    })
}