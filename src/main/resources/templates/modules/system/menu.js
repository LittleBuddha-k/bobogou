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
                toolbar: '#toolbarDemo',
                page: false,
                cols: [
                    [
                        {type: 'checkbox'},
                        {
                            field: 'title',
                            align: 'left',
                            title: '菜单名称'
                        },
                        {
                            field: 'href',
                            align: 'left',
                            title: '菜单url'
                        },
                        {
                            field: 'icon',
                            align: 'left',
                            title: '菜单图标',
                            templet: function (d) {
                                let html = '<i class="' + d.icon + '"></i> ';
                                return html;
                            }
                        },
                        {
                            field: 'sort',
                            align: 'left',
                            title: '排序'
                        },
                        {
                            field: 'isShow',
                            align: 'left',
                            title: '是否展示',
                            templet: function (d) {
                                if (1 == d.isShow) {
                                    return '是';
                                } else {
                                    return '否';
                                }
                            }
                        },
                        {
                            field: 'type',
                            align: 'left',
                            title: '类型',
                            templet: function (d) {
                                if (0 == d.type) {
                                    return '菜单';
                                } else if (1 == d.type) {
                                    return '按钮';
                                }
                            }
                        },/*
                        {
                            field: 'permission',
                            align: 'left',
                            title: '权限标识'
                        },*/
                        {
                            field: 'hasChildren',
                            align: 'left',
                            title: '是否有子菜单',
                            templet: function (d) {
                                if (d.hasChildren) {
                                    return '是';
                                } else {
                                    return '否';
                                }
                            }
                        },
                        {
                            field: 'permission',
                            align: 'left',
                            title: '操作',
                            templet: '#auth-state', width: 280, align: 'left'
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
        table.on('toolbar(menuTable)', function (obj) {
            var checkStatus = table.checkStatus('menuTable');
            var data = checkStatus.data;
            if (obj.event === 'add') {
                rc.openSaveDialog("/bobogou/system/menu/form/add", "添加一级菜单", '40%', '50%');
            }
        })

        //监听工具条
        table.on('tool(menuTable)', function (obj) {
            let data = obj.data;
            let layEvent = obj.event;
            let id = obj.data.id;

            if (obj.event === 'view') {
                rc.openViewDialog("/bobogou/system/menu/form/view?id=" + id, "查看菜单信息", '40%', '50%')
            } else if (obj.event === "edit") {
                rc.openSaveDialog("/bobogou/system/menu/form/edit?id=" + id, "编辑菜单信息", '40%', '50%')
                renderTable();
            } else if (obj.event === "delete") {
                rc.confirm('确认要删除该信息吗？', function () {
                    rc.post("/bobogou/system/menu/delete?ids=" + id, '', function (data) {
                        if (data.code == 200) {
                            //执行搜索重载
                            renderTable();
                        } else {
                            rc.alert(data.msg);
                        }
                    });
                })
            } else if (layEvent === 'addChildren') {
                rc.openSaveDialog("/bobogou/system/menu/form/addChildren?parent.id=" + id, "添加下级菜单", '40%', '50%')
            }
        });
    });
}