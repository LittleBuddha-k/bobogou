layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    //下拉框选中后的时间
    form.on('select(province)', function(data){
        //console.log(data.elem); //得到select原始DOM对象
        //console.log(data.value); //得到被选中的值
        //console.log(data.othis); //得到美化后的DOM对象
        let provinceId = data.value;
        $("#cityId").empty();//清空城市选项
        rc.post("/bobogou/data/city/all",{"province.id":provinceId},function(data){
            if(data.length>0) {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="">请选择</option>';
                for(let i=0;i<data.length;i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#cityId").html(tmp);
                form.render();
            }
        })
    });
    //下拉框选中后的时间
    form.on('select(city)', function(data){
        //console.log(data.elem); //得到select原始DOM对象
        //console.log(data.value); //得到被选中的值
        //console.log(data.othis); //得到美化后的DOM对象
        let cityId = data.value;
        $("#districtId").empty();//清空城市选项
        rc.post("/bobogou/data/area/all",{"city.id":cityId},function(data){
            if(data.length>0) {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="">请选择</option>';
                for(let i=0;i<data.length;i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#districtId").html(tmp);
                form.render();
            }
        })
    });
    //下拉框选中后的时间
    form.on('select(area)', function(data){
        //console.log(data.elem); //得到select原始DOM对象
        //console.log(data.value); //得到被选中的值
        //console.log(data.othis); //得到美化后的DOM对象
        let streetId = data.value;
        $("#streetId").empty();//清空城市选项
        rc.post("/bobogou/data/street/all",{"area.id":streetId},function(data){
            if(data.length>0) {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="">请选择</option>';
                for(let i=0;i<data.length;i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#streetId").html(tmp);
                form.render();
            }
        })
    });

    table.render({
        elem: '#regionGoodsTable',
        url: '/bobogou/data/regionGoods/data',
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
                    title: '省',
                    field: 'provinceName',
                    sort: true,
                    width: '10%',
                    sortName: 'provinceName'/*,
                    templet:function(data){
                        var valueArray = data.provinceName;
                        return valueArray;
                    }*/
                },
                {
                    title: '市',
                    field: 'cityName',
                    sort: true,
                    width: '10%',
                    sortName: 'cityName'
                },
                {
                    title: '区',
                    field: 'areaName',
                    sort: true,
                    width: '10%',
                    sortName: 'areaName'
                },
                {
                    title: '街道',
                    field: 'streetName',
                    sort: true,
                    width: '10%',
                    sortName: 'streetName'
                },
                {
                    title: '商品',
                    field: 'goodsName',
                    sort: true,
                    width: '10%',
                    sortName: 'goodsName'
                },
                {
                    title: '商品数量',
                    field: 'amount',
                    sort: true,
                    width: '10%',
                    sortName: 'amount'
                },
                {
                    title: '销量',
                    field: 'salesVolume',
                    sort: true,
                    width: '10%',
                    sortName: 'salesVolume'
                },
                {
                    title: '是否在售',
                    field: 'isMarket',
                    sort: true,
                    width: '10%',
                    sortName: 'isMarket',
                    templet:function(data){
                        let isMarket = data.isMarket;
                        if(0 == isMarket){
                            return "在售";
                        }else if(1 == isMarket){
                            return "停售";
                        }else {
                            return ""
                        }
                    }
                },/*
                {
                    title: '修改人',
                    field: 'updateByName',
                    sort: true,
                    sortName: 'updateByName'
                },*/
                {
                    title: '操作',
                    toolbar: '#operation',
                    align: "center",
                    width: '20%'
                }
            ]
        ],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 10,
        page: true,
        skin: 'line',
        where: {
            provinceId: $("#provinceId").val(),
            cityId: $("#cityId").val(),
            districtId: $("#districtId").val(),
            streetId: $("#streetId").val(),
            goodsId: $("#goodsId").val(),
            isMarket: $("#isMarket").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('regionGoodsTable', {
            where: {
                provinceId: $("#provinceId").val(),
                cityId: $("#cityId").val(),
                districtId: $("#districtId").val(),
                streetId: $("#streetId").val(),
                goodsId: $("#goods").val(),
                isMarket: $("#isMarket").val()
            }
        });
        return false;
    });

    // 监听重置操作
    form.on('submit(data-reset-btn)', function (data) {
        //执行搜索重载
        table.reload('regionGoodsTable', {
            where: {
                provinceId: $("#provinceId").val(""),
                cityId: $("#cityId").val(""),
                districtId: $("#districtId").val(""),
                streetId: $("#streetId").val(""),
                goodsId: $("#goods").val(""),
                isMarket: $("#isMarket").val("")
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(regionGoodsTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            let index = rc.openSaveDialog("/bobogou/data/regionGoods/form/add", "新建地域商品信息",'65%','65%')
        }
    });

    table.on('tool(regionGoodsTableFilter)', function (obj) {
        let id = obj.data.id;
        let event = obj.event;

        if('edit' == event){
            rc.openSaveDialog('/bobogou/data/regionGoods/form/edit?id=' + id, "编辑商品区域信息",'65%','65%');
        }else if ('delete' == event){
            rc.confirm('确认要删除该信息吗？', function() {
                rc.post("/bobogou/data/regionGoods/delete?ids=" + id, '', function (data) {
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
    var checkStatus = table.checkStatus('regionGoodsTable'),
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
        table.reload('regionGoodsTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'data');
    })
}

function selectGoods(id) {
    let openSelector = rc.openGoodsSelect("/bobogou/data/goods/select/", "选择商品", '100%', '100%',id);
}