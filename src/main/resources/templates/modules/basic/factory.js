layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    let init = table.render({
        elem: '#factoryTable',
        url: '/bobogou/basic/factory/data',
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
                    type: "checkbox"
                },*/
                {
                    title: '厂家类型',
                    field: 'factoryType',
                    templet: function (data) {
                        var factoryType = data.factoryType;
                        let dictName = rc.getDictName("basic_factory_type",factoryType);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '厂家产品类型',
                    field: 'productType',
                    templet: function (data) {
                        var productType = data.productType;
                        let dictName = rc.getDictName("basic_factory_product_type",productType);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '厂家名称',
                    field: 'factoryName',
                },
                {
                    title: '联系人',
                    field: 'linkman',
                    minWidth: '100',
                },
                {
                    title: '联系人手机号',
                    field: 'phone',
                    minWidth: '120',
                },
                {
                    title: '身份证号',
                    field: 'idNumber',
                    minWidth: '180',
                },
                {
                    title: '资质过期日期',
                    field: 'effectiveDate',
                    minWidth: '180',
                    templet: function (data) {
                        var effectiveDate = data.effectiveDate;
                        if (effectiveDate != null){
                            let dateFormat = rc.dateFormat(effectiveDate);
                            return dateFormat;
                        }else {
                            return "";
                        }
                    }
                },
                {
                    title: '是否委托人采购或者销售',
                    field: 'isBailor',
                    minWidth: '220',
                    templet: function (data) {
                        var isBailor = data.isBailor;
                        let dictName = rc.getDictName("basic_factory_is_bailor",isBailor);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '操作',
                    toolbar: '#operation',
                    align: "center",
                    minWidth: '200'
                }
            ]
        ],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 10,
        page: true,
        skin: 'line',
        where: {
            factoryName: $("#factoryName").val(),
            factoryType: $("#factoryType").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('factoryTable', {
            where: {
                factoryName: $("#factoryName").val(),
                factoryType: $("#factoryType").val()
            }
        });
        return false;
    });
    //重置按钮
    $("#reset").click(function () {
        factoryName: $("#factoryName").val("");
        factoryType: $("#factoryType").val("");
        init();
    })
    /**
     * toolbar监听事件
     */
    table.on('toolbar(factoryTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            let index = rc.openSaveDialog("/bobogou/basic/factory/form/add", "新建厂家信息",'720px','80%')
        }
    });

    table.on('tool(factoryTableFilter)', function (obj) {
        let id = obj.data.id;
        let event = obj.event;
        if (event === 'edit') {
            rc.openSaveDialog('/bobogou/basic/factory/form/edit?id=' + id, "编辑厂家信息", '720px', '80%');
        }else if (event === 'view') {
            rc.openViewDialog('/bobogou/basic/factory/form/view?id=' + id, "查看厂家信息", '720px', '80%');
        }else if (event === 'delete') {
            rc.confirm('确认要删除该厂家信息吗？', function () {
                rc.post("/bobogou/basic/factory/delete?ids=" + id, '', function (data) {
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
    var checkStatus = table.checkStatus('factoryTable'),
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
        table.reload('factoryTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'data');
    })
}