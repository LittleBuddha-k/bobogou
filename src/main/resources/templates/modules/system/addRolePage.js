layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#roleTable',
        url: '/bobogou/system/role/data',
        method: 'GET',
        request: {
            pageName: 'pageNo', // page
            limitName: 'pageSize' // limit
        },//重命名参数名称
        done: function (res) {
            //做checkbox回显
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            /*let val = $("#rolesId").val();
            let strings = val.split(",");
            let data = res.data;
            for (var i = 0;i < data.length; i++){
                for (var j = 0;j < strings.length; j++){

                }
            }*/

            let rolesId = $("#rolesId").val().split(",");
            //遍历集合
            layui.each(res.data, function (index, item) {
                //将获取的选中行数据进行遍历
                if (rolesId.indexOf('' + item.id + '') > -1) {
                    //一:修改class属性--随缘有效
                    // $('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
                    // $('tr[data-index=' + index + '] input[type="checkbox"]').next().addClass('layui-form-checked');
                    //二：点击去属性 lay-id='table'==表格id ； index：需要回显的行数下标-从0开始
                    //$("div[lay-id='roleTable'] td .layui-form-checkbox").eq(index).click();
                    $('tr[data-index=' + index + '] input[type="radio"]').prop('checked', true);
                    $('tr[data-index=' + index + '] input[type="radio"]').next().addClass('layui-form-checked').click();
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
                    type: "radio"
                },
                {
                    title: '角色名称',
                    field: 'name'
                },
                {
                    title: '英文名称',
                    field: 'englishName',
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

        var checkStatus = table.checkStatus('roleTable'),
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
    $("#rolesId").val(ids);
    rc.post("/bobogou/system/operator/addRole",$("#hiddenForm").serializeJson(),function (data) {
        if(200 == data.code){
            //当你在iframe页面关闭自身时
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            rc.msg("设置角色成功")
            setTimeout(function(){
                parent.layer.close(index); //再执行关闭
            }, 1000);
        }else {
            rc.msg("设置角色失败")
        }
    })
}