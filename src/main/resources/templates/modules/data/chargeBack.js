layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#chargeBackTable',
        url: '/bobogou/data/chargeBack/data',
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
                    title: '订单id',
                    field: 'orderId',
                    width: '150'
                },
                {
                    title: '订单编号',
                    field: 'number',
                    sort: true,
                    width: '150',
                },
                {
                    title: '订单商品Id',
                    field: 'orderInfoId',
                    width: '150',
                },
                {
                    title: '购买者id',
                    field: 'userId',
                    width: '150'
                },
                {
                    title: '购买人',
                    field: 'userName',
                    sort: true,
                    width: '150',
                },
                {
                    title: '商品id',
                    field: 'goodsId',
                    width: '150',
                },
                {
                    title: '商品名字',
                    field: 'goodsName',
                    width: '150'
                },
                {
                    title: '退单数量',
                    field: 'count',
                    sort: true,
                    width: '150',
                },
                {
                    title: '商品单价',
                    field: 'price',
                    width: '150',
                },
                {
                    title: '管理费',
                    field: 'managementExpense',
                    width: '150'
                },
                {
                    title: '运费',
                    field: 'transportationCost',
                    sort: true,
                    width: '150',
                },
                {
                    title: '操作',
                    align: "left",
                    width: '200',
                    toolbar: '#operation'
                }
            ]
        ],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 10,
        page: true,
        skin: 'line',
        where: {
            userName: $("#userName").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('chargeBackTable', {
            where: {
                userName: $("#userName").val()
            }
        });
        return false;
    });

    // 监听重置操作
    $("#resetBtn").click(function () {
        $("#number").val("");
        init();
    })

    /**
     * toolbar监听事件
     */
    table.on('toolbar(chargeBackTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/data/chargeBack/form/add", "新建退单信息", '1320px', '650px')
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
                rc.openSaveDialog('/bobogou/data/chargeBack/form/edit?id=' + ids, "编辑退单信息", '1320px', '650px');
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
                rc.openViewDialog('/bobogou/data/chargeBack/form/view?id=' + ids, "查看退单信息", '85%', '70%');
            }
            $(window).on("resize", function () {
                layer.full(index);
            });
        } else if (obj.event === 'delete') {  // 监听删除操作
            let ids = getIdSelections(table);
            if (ids == null || ids == '') {
                rc.alert("请至少选择一条数据")
            } else {
                rc.confirm('确认要删除该区域信息吗？', function () {
                    rc.post("/bobogou/data/chargeBack/delete?ids=" + ids, '', function (data) {
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
            rc.openImportDialog("/bobogou/data/chargeBack/importTemplate", "/bobogou/data/chargeBack/importFile")
        } else if (obj.event === 'export') {  // 监听删除操作
            rc.confirm('导出时间可能较长，确认要导出退单信息吗？', function(){
                var index = rc.loading("正在下载，请稍后");
                rc.downloadFile("/bobogou/data/chargeBack/exportFile?" + $("#searchForm").serialize());
                setTimeout(function(){
                    layer.close(index);
                }, 10000);
            });
        }
    });

    table.on('tool(chargeBackTableFilter)', function (obj) {
        let id = obj.data.id;
        if (obj.event === 'detail') {
            rc.openViewDialog("/bobogou/data/chargeBack/form/detail?id=" + id, "退单详情",'85%','70%')
        } else if (obj.event === 'edit') {
            rc.openSaveDialog('/bobogou/data/chargeBack/form/edit?id=' + id, "编辑退单信息", '85%', '70%');
        } else if (obj.event === 'chargeBack') {
            //rc.openViewDialogNoClose('/bobogou/data/chargeBack/form/chargeBack?id=' + id, "退单处理","85%","70%")
            rc.openSaveDialog('/bobogou/data/chargeBack/form/chargeBack?id=' + id, "退单处理","85%","70%")
        }
    });
    $("#pass").click(function(data) {
        rc.success("待支付完善做处理")
        //return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })
    $("#refuse").click(function(data) {
        rc.success("待支付完善做处理")
        //return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })
    //监视列表查找单选框
    form.on('radio(status)', function (data) {
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
    var checkStatus = table.checkStatus('chargeBackTable'),
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
        table.reload('chargeBackTable', {
            page: {
                curr: 1
            }
            , where: {}
        }, 'data');
    })
}