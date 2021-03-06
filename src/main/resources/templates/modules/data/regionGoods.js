layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    //下拉框选中后的时间
    form.on('select(provinceSelect)', function (data) {
        //console.log(data.elem); //得到select原始DOM对象
        //console.log(data.value); //得到被选中的值
        //console.log(data.othis); //得到美化后的DOM对象
        let provinceId = data.value;
        $("#citySelect").empty();//清空城市选项
        $("#areaSelect").empty();//清空城市选项
        $("#streetSelect").empty();//清空城市选项
        rc.post("/bobogou/data/city/all", {"province.id": provinceId}, function (data) {
            if (data.length > 0) {
                //对应的值传回，拼出html下拉框语句
                var tmp = '<option value="0">---请选择---</option>';
                for (let i = 0; i < data.length; i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#citySelect").html(tmp);
                form.render();
            }
        })
    });
    //下拉框选中后的时间
    form.on('select(citySelect)', function (data) {
        //console.log(data.elem); //得到select原始DOM对象
        //console.log(data.value); //得到被选中的值
        //console.log(data.othis); //得到美化后的DOM对象
        let cityId = data.value;
        $("#areaSelect").empty();//清空城市选项
        $("#streetSelect").empty();//清空城市选项
        rc.post("/bobogou/data/area/all", {"city.id": cityId}, function (data) {
            if (data.length > 0) {
                //对应的值传回，拼出html下拉框语句
                var tmp = '<option value="0">---请选择---</option>';
                for (let i = 0; i < data.length; i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#areaSelect").html(tmp);
                form.render();
            }
        })
    });
    //下拉框选中后的时间
    form.on('select(areaSelect)', function (data) {
        //console.log(data.elem); //得到select原始DOM对象
        //console.log(data.value); //得到被选中的值
        //console.log(data.othis); //得到美化后的DOM对象
        let streetId = data.value;
        $("#streetSelect").empty();//清空城市选项
        rc.post("/bobogou/data/street/all", {"area.id": streetId}, function (data) {
            if (data.length > 0) {
                //对应的值传回，拼出html下拉框语句
                var tmp = '<option value="0">---请选择---</option>';
                for (let i = 0; i < data.length; i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#streetSelect").html(tmp);
                form.render();
            }
        })
    });

    var init = table.render({
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
                    title: '省',
                    field: 'provinceName',
                    minWidth: '100',
                },
                {
                    title: '市',
                    field: 'cityName',
                    minWidth: '100',
                },
                {
                    title: '区',
                    field: 'areaName',
                    minWidth: '100',
                },
                {
                    title: '街道',
                    field: 'streetName',
                    minWidth: '100',
                },
                {
                    title: '商品',
                    field: 'goodsName',
                    minWidth: '100',
                },
                {
                    title: '操作',
                    toolbar: '#operation',
                    align: "center",
                    minWidth: '100',
                }
            ]
        ],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 10,
        page: true,
        skin: 'line',
        where: {
            provinceSelect: $("#provinceSelect").val(),
            citySelect: $("#citySelect").val(),
            areaSelect: $("#areaSelect").val(),
            streetSelect: $("#streetSelect").val(),
            goodsId: $("#goodsId").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('regionGoodsTable', {
            where: {
                provinceSelect: $("#provinceSelect").val(),
                citySelect: $("#citySelect").val(),
                areaSelect: $("#areaSelect").val(),
                streetSelect: $("#streetSelect").val(),
                goodsId: $("#goods").val()
            }
        });
        return false;
    });

    // 监听重置操作
    $("#reset").click(function () {
        provinceSelect: $("#provinceSelect").val();
        citySelect: $("#citySelect").val();
        areaSelect: $("#areaSelect").val();
        streetSelect: $("#streetSelect").val();
        goodsId: $("#goods").val("");
        init();
    })

    /**
     * toolbar监听事件
     */
    table.on('toolbar(regionGoodsTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            let index = rc.openSaveDialog("/bobogou/data/regionGoods/form/add", "新建区域商品信息", '795px', '50%')
        }
    });

    table.on('tool(regionGoodsTableFilter)', function (obj) {
        let id = obj.data.id;
        let event = obj.event;

        if ('setArea' == event) {
            rc.openSetAreaSelect(id,'/bobogou/data/areaSelect/list', "/bobogou/data/regionGoods/setArea/","选择区域", '300px', '75%');
        }else if ('edit' == event) {
            rc.openSaveDialog('/bobogou/data/regionGoods/form/edit?id=' + id, "编辑商品区域信息", '795px', '75%');
        } else if ('delete' == event) {
            rc.confirm('确认要删除该信息吗？', function () {
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
            , where: {}
        }, 'data');
    })
}

function selectGoods(id) {
    let openSelector = rc.openGoodsSelect("/bobogou/data/goods/select/", "选择商品", '50%', '50%', id);
}

function treeSave() {
    alert("列表页面执行成功")
}