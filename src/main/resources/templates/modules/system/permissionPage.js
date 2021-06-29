layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#menuTable',
        url: '/bobogou/system/menu/data',
        method: 'GET',
        request: {
            pageName: 'pageNo', // page
            limitName: 'pageSize' // limit
        },//重命名参数名称
        done: function (res) {
            //做checkbox回显
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            /*let val = $("#menusId").val();
            let strings = val.split(",");
            let data = res.data;
            for (var i = 0;i < data.length; i++){
                for (var j = 0;j < strings.length; j++){

                }
            }*/

            let menusId = $("#menusId").val().split(",");
            //遍历集合
            layui.each(res.data, function (index, item) {
                //将获取的选中行数据进行遍历
                if (menusId.indexOf('' + item.id + '') > -1) {
                    //一:修改class属性--随缘有效
                    // $('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
                    // $('tr[data-index=' + index + '] input[type="checkbox"]').next().addClass('layui-form-checked');
                    //二：点击去属性 lay-id='table'==表格id ； index：需要回显的行数下标-从0开始
                    $("div[lay-id='menuTable'] td .layui-form-checkbox").eq(index).click();
                }
            })
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
                {
                    type: "checkbox"
                },
                {
                    title: '父级id',
                    field: 'parent.id'
                },
                {
                    title: '菜单名字',
                    field: 'title',
                    sort: true
                },
                {
                    title: '链接',
                    field: 'href',
                    sort: true
                },
                {
                    title: '目标',
                    field: 'target',
                    sort: true
                },
                {
                    title: '图标',
                    field: 'icon',
                    sort: true
                },
                {
                    title: '排序',
                    field: 'sort',
                    sort: true
                },
                {
                    title: '是否显示',
                    field: 'isShow',
                    sort: true
                },
                {
                    title: '菜单类型',
                    field: 'type',
                    sort: true
                },
                {
                    title: '权限标识',
                    field: 'permission',
                    sort: true
                },
                {
                    title: '是否有子类',
                    field: 'hasChildren',
                    sort: true
                }/*,
                {
                    title: '操作',
                    toolbar: '#operation',
                    align: "center"
                }*/
            ]
        ],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 10,
        page: false,
        skin: 'line',
        where: {
            name: $("#name").val(),
            englishName: $("#englishName").val()
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
function getIdSelections() {
    let ids = "";
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;

        var checkStatus = table.checkStatus('menuTable'),
            data = checkStatus.data;
        for (let i = 0; i < data.length; i++) {
            ids = ids + data[i].id + ",";
        }
    })
    return ids;
}

/**
 * 保存的save方法
 * @param ids
 */
function save(ids) {
    $("#menusId").val(ids);
    rc.post("/bobogou/system/role/addPermission",$("#hiddenForm").serializeJson(),function (data) {
        if(200 == data.code){
            rc.msg("设置权限成功")
        }else {
            rc.msg("设置权限失败")
        }
    })
}