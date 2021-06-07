$(document).ready(function () {
    $(function () {
        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();

        //2.初始化Button的点击事件
        var oButtonInit = new ButtonInit();
        oButtonInit.Init();
    });

    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#menuTable').bootstrapTable({
                url: '/recruit/system/menu/allData',         //请求后台的URL（*）
                method: 'GET',                      //请求方式（*）
                //类型json
                dataType: "json",
                contentType: "application/x-www-form-urlencoded",
                toolbar: '#toolbar',                //工具按钮用哪个容器
                striped: true,                      //是否显示行间隔色
                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,                   //是否显示分页（*）
                sortable: false,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                queryParams: function (params) {
                    var searchParam = $("#menuSearchForm").serializeJson();
                    searchParam.pageNo = params.limit === undefined ? "1" : params.offset / params.limit + 1;
                    searchParam.pageSize = params.limit === undefined ? -1 : params.limit;
                    searchParam.orderBy = params.sort === undefined ? "" : params.sort + " " + params.order;
                    return searchParam;
                },//传递参数（*）
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                pageNumber: 1,                       //初始化加载第一页，默认第一页
                pageSize: 10,                       //每页的记录行数（*）
                pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
                search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                strictSearch: true,
                showColumns: true,                  //是否显示所有的列
                showRefresh: true,                  //是否显示刷新按钮
                minimumCountColumns: 2,             //最少允许的列数
                clickToSelect: true,                //是否启用点击选中行
                //height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
                showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
                cardView: false,                    //是否显示详细视图
                detailView: false,                   //是否显示父子表
                columns: [
                    {
                        title: '',
                        checkbox: true,
                        formatter: function stateFormatter(value, row, index) {
                            let val = $("#menusId").val();
                            let strings = val.toString().split(",");
                            for (var i = 0; i < strings.length; i++) {
                                if (row.id == strings[i]) {
                                    return {
                                        disabled: false,//设置是否可用
                                        checked: true//设置选中
                                    };
                                }
                            }
                        }
                    }, {
                        field: 'parent.id',
                        title: '父级id'
                    }, {
                        field: 'title',
                        title: '菜单名字'
                    }, {
                        field: 'href',
                        title: '链接'
                    }, {
                        field: 'target',
                        title: '目标'
                    }, {
                        field: 'icon',
                        title: '图标',
                        formatter: function (value, row, index) {
                            let strings = row.icon.toString().split(" ");
                            let html = '<i class="' + row.icon + '"></i> ';
                            return html;
                        }
                    }, {
                        field: 'sort',
                        title: '排序'
                    }, {
                        field: 'isShow',
                        title: '是否显示',
                        formatter: function (value, row, index) {
                            if ('1' == row.isShow) {
                                return '是';
                            } else {
                                return '否';
                            }
                        }
                    }, {
                        field: 'type',
                        title: '菜单类型'
                    }, {
                        field: 'permission',
                        title: '权限标识',
                    }, {
                        field: 'hasChildren',
                        title: '是否有子类',
                        formatter: function (value, row, index) {
                            if (row.hasChildren) {
                                return '是';
                            } else {
                                return '否';
                            }
                        }
                    }
                ]
            });
        };

        return oTableInit;
    };

    var ButtonInit = function () {
        var oInit = new Object();
        var postdata = {};

        oInit.Init = function () {
            //初始化页面上面的按钮事件
        };

        return oInit;
    };
})

//获取点击的行的数据id
function getIdSelections() {
    return $.map($("#menuTable").bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}

//刷新列表
function refresh() {
    $('#menuTable').bootstrapTable('refresh');
}

//树形数据选用后的提交
function save(ids) {
    $("#menusId").val(ids);
    rc.post("/recruit/system/role/addPermission",$("#hiddenForm").serializeJson());
}