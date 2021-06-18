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
                            title: '类型'
                        },
                        {
                            field: 'permission',
                            align: 'left',
                            title: '权限标识'
                        },
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
                            title: '权限标识',
                            width: '180px',
                            templet: '#auth-state', width: 200, align: 'left', title: '操作'
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

        $('#btn-expand').click(function () {
            treetable.expandAll('#menuTable');
        });

        $('#btn-fold').click(function () {
            treetable.foldAll('#menuTable');
        });

        /*监听左上角工具栏*/
        table.on('toolbar(menuTable)', function (obj) {
            var checkStatus = table.checkStatus('menuTable');
            var data = checkStatus.data;
            if (obj.event === 'add') {
                rc.openSaveDialog("/bobogou/system/menu/form/add", "添加一级菜单",'75%','70%');
            } else if (obj.event === 'edit') {
                if (data.length <= 0) {
                    rc.confirm("请至少选择一条数据")
                } else if (data.length >= 2) {
                    rc.confirm("只能选择一条数据")
                } else {
                    var id = data[0].id;
                    rc.openSaveDialog("/bobogou/system/menu/form/edit?id=" + id, "编辑菜单信息",'75%','70%')
                }
            } else if (obj.event === "delete") {
                var ids = "";
                for (var i = 0; i < data.length; i++) {
                    ids = ids + "," + data[i].id;
                }
                rc.treeTablePost("/bobogou/system/menu/deleteByPhysics?ids=" + ids)
                renderTable();
            } else if (obj.event === "refresh") {
                renderTable();
            }
        })

        //监听工具条
        table.on('tool(menuTable)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;

            if (layEvent === 'addChildren') {
                let parentId = data.id;
                rc.openTreeSaveDialog("/bobogou/system/menu/form/addChildren?parent.id=" + parentId, "添加下级菜单",'75%','70%')
            } else if (layEvent === 'del') {
                let id = data.id;
                rc.treeTablePost("/bobogou/system/menu/deleteByPhysics?ids=" + id);
                renderTable();
            }
        });
    });
}