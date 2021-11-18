layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#goodsTable',
        url: '/bobogou/data/goods/finishedData',
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
                    type: "radio",
                    width: 150
                },
                {
                    title: '商品名',
                    field: 'name',
                    sort: true,
                    sortName: 'name',
                    align: 'center'
                },/*
                {
                    title: '图片',
                    field: 'images',
                    sort: true,
                    sortName: 'images'
                },
                {
                    title: '品牌分类ID',
                    field: 'brandId',
                    sort: true,
                    sortName: 'brandId'
                },
                {
                    title: '商品标签ID',
                    field: 'tagId',
                    sort: true,
                    sortName: 'tagId'
                },
                {
                    title: '进价',
                    field: 'purchasingPrice',
                    sort: true
                },
                {
                    title: '原价',
                    field: 'originalCost'
                },
                {
                    title: '普通会员价',
                    field: 'sellingPrice'
                },
                {
                    title: '会员价',
                    field: 'vipPrice',
                    sort: true
                },
                {
                    title: '规格',
                    field: 'specification',
                    sort: true
                },*/
                {
                    title: '数量',
                    field: 'amount',
                    sort: true,
                    align: 'center'
                },
                {
                    title: '销量',
                    field: 'salesVolume',
                    sort: true,
                    align: 'center'
                },/*
                {
                    title: '功效',
                    field: 'effect',
                    sort: true,
                    align: 'center'
                },
                {
                    title: '好评率',
                    field: 'applauseRate',
                    sort: true,
                    align: 'center'
                },
                {
                    title: '健康豆',
                    field: 'healthBeans',
                    sort: true,
                    align: 'center'
                },*/
                {
                    title: '是否销售',
                    field: 'isMarket',
                    sort: true,
                    align: 'center',
                    templet: function (data) {
                        var isMarket = data.isMarket;
                        if (0 == isMarket) {
                            return "在售";
                        } else if (1 == isMarket) {
                            return "停售";
                        } else {
                            return "未知";
                        }
                    }
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
});

/**
 * 获取layui table 复选框的id
 * @param table -- table = layui.table;
 * @param tableId -- layui table 的id
 * @returns {string}
 */
function getIdSelections(table) {
    var checkStatus = table.checkStatus('goodsTable'),
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

        var checkStatus = table.checkStatus('goodsTable'),
            data = checkStatus.data;
        let idArr = ids.toString().split(",");
        if (data.length > 1) {
            rc.alert("只能选择一条数据")
        } else if (data.length <= 0) {
            rc.alert("请至少选择一条数据")
        } else {
            ids = data[0].id;
            name = data[0].name;
        }
        /*for (let i = 0; i < data.length; i++) {
            ids = ids + data[i].id + ",";
        }*/
    })
    return ids + "," + name;
}

function refresh() {
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;

        //执行搜索重载
        table.reload('goodsTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'data');
    })
}