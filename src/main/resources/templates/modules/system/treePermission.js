$(document).ready(function () {
    /*表格初始化*/
    init();
})

/*刷新表格、重新加载初始化*/
function refresh() {
    init();
}

//重新加载刷新
function init() {
    layui.use(['table', 'treetable'], function () {
        var $ = layui.jquery;
        var table = layui.table;
        var treetable = layui.treetable;

        // 渲染表格
        var renderTable = function () {
            layer.load(2);
            treetable.render({
                treeColIndex: 1,
                treeSpid: -1,
                treeIdName: 'id',
                treePidName: 'parentId',
                elem: '#menuTable',
                url: '/bobogou/system/menu/data',
                treeDefaultClose: true,	//是否默认折叠
                treeLinkage: false,		//父级展开时是否自动展开所有子级
                //toolbar: '#toolbarDemo',
                page: false,
                cols: [
                    [
                        {
                            type: 'checkbox',
                            width: 150
                        },
                        {
                            field: 'title',
                            align: 'left',
                            title: '菜单名称'
                        }
                    ]
                ],
                done: function (res) {
                    layer.closeAll('loading');
                    let menusId = $("#menusId").val().split(",");
                    //遍历集合
                    layui.each(res.data, function (index, item) {
                        //将获取的选中行数据进行遍历
                        if (menusId.indexOf('' + item.id + '') > -1) {
                            $("div[lay-id='menuTable'] td .layui-form-checkbox").eq(index).click();
                        }
                    })
                }
            });
        }

        //加载
        renderTable();

        //复选框的监视
        table.on('checkbox(menuTable)', function (obj) {
            let ids = $("menusId").val();
            let checked = obj.checked;
            let type = obj.type;
            //let id = obj.data.id;
            console.log("是否选中:"+ checked); //当前是否选中状态
            //console.log("行数据："+obj.data.id); //选中行的相关数据
            console.log("行数据："+obj.type); //如果触发的是全选，则为：all，如果触发的是单选，则为：one
            //let data = table.checkStatus('menuTable').data;
            //console.log(data); // 获取表格中选中行的数据
            if ('one' == type){
                if (checked){
                    let val = $("#menusId").val();
                    let id = obj.data.id;
                    if (val && val != ""){
                        $("#menusId").val(val + "," + id);
                    }else {
                        $("#menusId").val(id);
                    }
                }else if (!checked){
                    let val = $("#menusId").val();
                    let id = obj.data.id;
                    if (val && val != ""){
                        let split = val.split(",");
                        if (split.length > 1){
                            if (split[0] == id){
                                val = val.toString().replace(id + ",","");
                                $("#menusId").val(val);
                            }else {
                                val = val.toString().replace("," + id,"");
                                $("#menusId").val(val);
                            }
                        }else if (split.length == 1){
                            alert("进来了2")
                            val = val.toString().replace(id,"");
                            $("#menusId").val(val);
                        }
                    }else {
                        $("#menusId").val(val);
                    }
                }
            }else if ('all' == type){
                if (checked){
                    //先去除原有的值
                    let val = $("#menusId").val("");
                    //全选，将选中的值加上去
                    let data = table.checkStatus('menuTable').data;
                    for (let i = 0 ; i < data.length ; i++){
                        let original = $("#menusId").val();
                        let id = data[i].id;
                        if (val && val != ""){
                            $("#menusId").val(original + "," + id);
                        }else {
                            $("#menusId").val(id);
                        }
                    }
                }else if (!checked){
                    //全取消，去除原有的值
                    $("#menusId").val("");
                }
            }

        });
    });
}

/**
 * 保存的save方法
 * @param ids
 */
function save() {
    rc.post("/bobogou/system/role/addPermission",$("#hiddenForm").serializeJson(),function (data) {
        if(200 == data.code){
            //当你在iframe页面关闭自身时
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            rc.msg("设置权限成功")
            setTimeout(function(){
                parent.layer.close(index); //再执行关闭
            }, 500);
        }else {
            rc.msg("设置权限失败")
        }
    })
}