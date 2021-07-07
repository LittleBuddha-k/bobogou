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
                elem: '#roleTable',
                url: '/bobogou/system/role/allData',
                treeDefaultClose: true,	//是否默认折叠
                treeLinkage: false,		//父级展开时是否自动展开所有子级
                toolbar: '#toolbar',
                page: false,
                cols: [
                    [
                        {type: 'checkbox'},
                        {
                            field: 'name',
                            align: 'left',
                            title: '角色名称'
                        },
                        {
                            field: 'englishName',
                            align: '英文名称',
                            title: '排序'
                        },
                        {
                            align: 'left',
                            title: '操作',
                            templet: '#operation', align: 'left'
                        },
                    ]
                ],
                done: function () {
                    layer.closeAll('loading');
                }
            });
        }

        //加载
        renderTable();

        /*监听左上角工具栏*/
        table.on('toolbar(roleTable)', function (obj) {
            var checkStatus = table.checkStatus('roleTable');
            var data = checkStatus.data;
            if (obj.event === 'add') {
                rc.openSaveDialog("/bobogou/system/role/form/add", "添加一级角色", '670px', '340px');
            }
        })

        //监听工具条
        table.on('tool(roleTable)', function (obj) {
            let data = obj.data;
            let layEvent = obj.event;
            let id = obj.data.id;

            if (obj.event === 'view') {
                rc.openViewDialog("/bobogou/system/role/form/view?id=" + id, "查看角色信息", '670px', '340px')
            } else if (obj.event === "edit") {
                rc.openSaveDialog("/bobogou/system/role/form/edit?id=" + id, "编辑角色信息", '670px', '340px')
            }  else if (layEvent === 'addPermission') {
                let index = rc.openSelectionDialog("/bobogou/system/role/permissionPage?id=" + id, "设置权限",'75%','70%')
            }  else if (layEvent === 'addChildren') {
                rc.openSaveDialog("/bobogou/system/role/form/addChildren?parent.id=" + id, "添加下级角色", '670px', '340px')
            } else if (obj.event === "delete") {
                rc.confirm('确认要删除该信息吗？', function () {
                    rc.post("/bobogou/system/role/delete?ids=" + id, '', function (data) {
                        if (data.code == 200) {
                            //执行搜索重载
                            renderTable();
                        } else {
                            rc.alert(data.msg);
                        }
                    });
                })
            }
        });
    });
}