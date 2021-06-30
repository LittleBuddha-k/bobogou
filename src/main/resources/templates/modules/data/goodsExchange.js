layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#goodsExchangeTable',
        url: '/bobogou/data/goodsExchange/data',
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
                    title: '商品名称',
                    field: 'name',
                    sort: true,
                    sortName: 'name',
                    templet: function (data) {
                        var valueArray = data.name;
                        return valueArray;
                    }
                },
                {
                    title: '图片展示',
                    field: 'imageUrl',
                    sort: true,
                    sortName: 'imageUrl',
                    templet: function (data) {
                        var imageUrl = data.imageUrl;
                        let split = imageUrl.split(",");
                        let html = "";
                        if (imageUrl != null || imageUrl != ''){
                            for (let i = 0;i<split.length;i++){
                                html += '<img src="'+ split[i] +'" alt="" style="width: 47px;height: 33px;" class="layui-upload-img">'
                            }
                            return html;
                        }else {
                            return "无图片";
                        }
                    }
                },
                {
                    title: '价格(元)',
                    field: 'price',
                    sort: true,
                    sortName: 'price'
                },
                {
                    title: '健康豆',
                    field: 'healthBeans',
                    sort: true,
                    sortName: 'healthBeans'
                },
                {
                    title: '积分',
                    field: 'integral',
                    sort: true,
                    sortName: 'integral'
                },
                {
                    title: '规格',
                    field: 'specification',
                    sort: true,
                    sortName: 'specification'
                },
                {
                    title: '数量',
                    field: 'amount',
                    sort: true,
                    sortName: 'amount'
                },
                {
                    title: '兑换量',
                    field: 'changeAmount',
                    sort: true,
                    sortName: 'changeAmount'
                },
                {
                    title: '详情',
                    field: 'content',
                    sort: true,
                    sortName: 'content'
                },
                {
                    title: '状态',
                    field: 'status',
                    sort: true,
                    sortName: 'status',
                    templet: function (data) {
                        var status = data.status;
                        if(0 == status){
                            return "可兑换";
                        }else if (1 == status){
                            return "不可兑换";
                        }else {
                            return "未知";
                        }
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
            name: $("#name").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('goodsExchangeTable', {
            where: {
                name: $("#name").val()
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(goodsExchangeTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/data/goodsExchange/form/add", "新建兑付信息", '1000px', '500px')
            $(window).on("resize", function () {
                layer.full(index);
            });
        }
    });

    table.on('tool(goodsExchangeTableFilter)', function (obj) {
        var id = obj.data.id;
        if (obj.event === 'edit') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/data/goodsExchange/form/edit?id="+id, "编辑兑付信息", '1000px', '500px')
            $(window).on("resize", function () {
                layer.full(index);
            });
        }else if (obj.event === 'delete'){
            rc.confirm('确认要删除该信息吗？', function() {
                rc.post("/bobogou/data/goodsExchange/delete?ids=" + id, '', function (data) {
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
    var checkStatus = table.checkStatus('goodsExchangeTable'),
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
        table.reload('goodsExchangeTable', {
            page: {
                curr: 1
            }
            , where: {}
        }, 'data');
    })
}