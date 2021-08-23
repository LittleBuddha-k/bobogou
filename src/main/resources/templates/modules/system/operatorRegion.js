layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    /*//下拉框选中后的时间
    form.on('select(province)', function(data){
        let provinceId = data.value;
        $("#city").empty();//清空城市选项
        rc.post("/bobogou/data/city/all",{"province.id":provinceId},function(data){
            if(data.length>0) {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="">请选择</option>';
                for(let i=0;i<data.length;i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#city").html(tmp);
                form.render();
            }
        })
    });
    //下拉框选中后的时间
    form.on('select(city)', function(data){
        let cityId = data.value;
        $("#area").empty();//清空城市选项
        rc.post("/bobogou/data/area/all",{"city.id":cityId},function(data){
            if(data.length>0) {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="">请选择</option>';
                for(let i=0;i<data.length;i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#area").html(tmp);
                form.render();
            }
        })
    });
    //下拉框选中后的时间
    form.on('select(area)', function(data){
        let streetId = data.value;
        $("#street").empty();//清空城市选项
        rc.post("/bobogou/data/street/all",{"area.id":streetId},function(data){
            if(data.length>0) {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="">请选择</option>';
                for(let i=0;i<data.length;i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#street").html(tmp);
                form.render();
            }
        })
    });*/

    var init = table.render({
        elem: '#operatorRegionTable',
        url: '/bobogou/system/operatorRegion/data',
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
                    title: '后台用户名',
                    field: 'operatorName',
                    sort: true
                },/*
                {
                    title: '前端用户名',
                    field: 'userName',
                    sort: true
                },*/
                {
                    title: '类型',
                    field: 'type',
                    sort: true,
                    templet: function (data) {
                        var type = data.type;
                        let dictName = rc.getDictName("system_region_operator_type",type);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '省',
                    field: 'provinceName',
                    sort: true,
                },
                {
                    title: '市',
                    field: 'cityName',
                    sort: true
                },
                {
                    title: '区',
                    field: 'areaName',
                    sort: true
                },
                {
                    title: '乡镇街道',
                    field: 'streetName',
                    sort: true
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
            operatorId: $("#selectOperatorId").val(),
            type: $("#type").val()
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('operatorRegionTable', {
            where: {
                operatorId: $("#selectOperatorId").val(),
                type: $("#type").val()
            }
        });
        return false;
    });

    // 监听重置操作
    $("#reset").click(function () {
        $("#selectOperatorId").val("");
        $("#type").val("");
        init();
    })

    /**
     * toolbar监听事件
     */
    table.on('toolbar(operatorRegionTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            var index = rc.openSaveDialog("/bobogou/system/operatorRegion/form/add", "新建用户区域信息", '763px', '80%')
        }
    });

    table.on('tool(operatorRegionTableFilter)', function (obj) {
        var id = obj.data.id;
        if (obj.event === 'edit') {  // 监听修改操作
            rc.openSaveDialog('/bobogou/system/operatorRegion/form/edit?id=' + id, "编辑用户区域信息", '763px', '80%');
        } else if (obj.event === 'delete') {  // 监听删除操作
            rc.confirm('确认要删除该用户区域信息吗？', function () {
                rc.post("/bobogou/system/operatorRegion/deleteByPhysics?ids=" + id, '', function (data) {
                    if (data.code == 200) {
                        //执行搜索重载
                        refresh();
                    } else {
                        rc.alert(data.msg);
                    }
                });
            })
        }
    });
});

function selectOperator(id) {
    let openSelector = rc.openUserSelect("/bobogou/system/operator/select/", "选择用户", '50%', '45%',id);
}

/**
 * 获取layui table 复选框的id
 * @param table -- table = layui.table;
 * @param tableId -- layui table 的id
 * @returns {string}
 */
function getIdSelections(table) {
    var checkStatus = table.checkStatus('operatorRegionTable'),
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
        table.reload('operatorRegionTable', {
            page: {
                curr: 1
            }
            , where: {}
        }, 'data');
    })
}