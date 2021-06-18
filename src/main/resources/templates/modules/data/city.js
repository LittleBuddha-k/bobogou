layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#cityTable',
        url: '/bobogou/data/city/data',
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
                },
                {
                    title: '市份代码',
                    field: 'code',
                    sort: true,
                    sortName: 'code',
                    templet:function(data){
                        var valueArray = data.code;
                        return valueArray;
                    }
                },
                {
                    title: '市份名称',
                    field: 'name',
                    sort: true,
                    sortName: 'name'
                },
                {
                    title: '简称',
                    field: 'shortName',
                    sort: true,
                    sortName: 'shortName'
                },
                {
                    title: '省级代码',
                    field: 'provinceCode',
                    sort: true,
                    sortName: 'provinceCode'
                },
                {
                    title: '经度',
                    field: 'lng',
                    sort: true,
                    sortName: 'lng'
                },
                {
                    title: '纬度',
                    field: 'lat',
                    sort: true,
                    sortName: 'lat'
                },
                {
                    title: '排序',
                    field: 'sort',
                    sort: true,
                    sortName: 'sort'
                },
                {
                    title: '状态',
                    field: 'status',
                    sort: true,
                    sortName: 'status'
                },
                {
                    title: '租户ID',
                    field: 'tenantCode',
                    sort: true,
                    sortName: 'tenantCode'
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
            code: $("#code").val(),
            name: $("#name").val(),
            shortName: $("#shortName").val(),
            provinceCode: $("#provinceCode").val(),
            lng: $("#lng").val(),
            lat: $("#lat").val(),
            sort: $("#sort").val(),
            remarks: $("#remarks").val(),
            status: $("#status").val(),
            tenantCode: $("#tenantCode").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('cityTable', {
            where: {
                code: $("#code").val(),
                name: $("#name").val(),
                shortName: $("#shortName").val(),
                provinceCode: $("#provinceCode").val(),
                lng: $("#lng").val(),
                lat: $("#lat").val(),
                sort: $("#sort").val(),
                remarks: $("#remarks").val(),
                status: $("#status").val(),
                tenantCode: $("#tenantCode").val()
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(cityTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/data/city/form/add", "新建市级信息",'75%','70%')
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
                rc.openSaveDialog('/bobogou/data/city/form/edit?id=' + ids, "编辑市级信息",'75%','70%');
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
                rc.openViewDialog('/bobogou/data/city/form/view?id=' + ids, "查看市级信息",'75%','70%');
            }
            $(window).on("resize", function () {
                layer.full(index);
            });
        } else if (obj.event === 'delete') {  // 监听删除操作
            let ids = getIdSelections(table);
            if (ids == null || ids == '') {
                rc.alert("请至少选择一条数据")
            } else {
                rc.post("/bobogou/data/city/deleteByPhysics?ids=" + ids, '',function (data) {
                    if(data.code == 200){
                        //执行搜索重载
                        refresh();
                        rc.alert(data.msg);
                    }else{
                        rc.alert(data.msg);
                    }
                });
            }
        } else if (obj.event === 'import') {  // 监听删除操作
            rc.openImportDialog("/bobogou/data/city/importTemplate", "/bobogou/data/city/importFile")
        } else if (obj.event === 'export') {  // 监听删除操作
            rc.downloadFile("/bobogou/data/city/exportFile?" + $("#searchForm").serialize());
        }
    });

    table.on('tool(cityTableFilter)', function (obj) {
        var id = obj.data.id;
        var index = rc.openSelectionDialog("/bobogou/data/city/addRolePage?id=" + id, "设置角色")
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
    var checkStatus = table.checkStatus('cityTable'),
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
        table.reload('cityTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'data');
    })
}