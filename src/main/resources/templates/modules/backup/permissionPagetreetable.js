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
                url: '/recruit/system/menu/data',
                treeDefaultClose: true,	//是否默认折叠
                treeLinkage: false,		//父级展开时是否自动展开所有子级
                //toolbar: '#toolbarDemo',
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
                                let strings = d.icon.toString().split(" ");
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
                            //一:修改class属性--随缘有效
                            // $('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
                            // $('tr[data-index=' + index + '] input[type="checkbox"]').next().addClass('layui-form-checked');
                            //二：点击去属性 lay-id='table'==表格id ； index：需要回显的行数下标-从0开始
                            $("div[lay-id='menuTable'] td .layui-form-checkbox").eq(index).click();
                        }
                    })
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
    });
}

function getIdSelections() {
    let ids = "";
    layui.use(['table', 'treetable'], function () {
        var $ = layui.jquery;
        var table = layui.table;
        var treetable = layui.treetable;

        var checkStatus = table.checkStatus('menuTable');
        var data = checkStatus.data;
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
function save() {
    var ids = getIdSelections();
    $("#menusId").val(ids);
    rc.post("/recruit/system/role/addPermission",$("#hiddenForm").serializeJson(),'roleTable',layui.table)
}