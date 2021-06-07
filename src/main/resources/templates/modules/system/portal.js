layui.use(['jquery', 'layer', 'miniAdmin', 'miniTongji'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        miniAdmin = layui.miniAdmin,
        miniTongji = layui.miniTongji;

    var options = {
        //iniUrl: "/bobogou/plugins/layui/api/init.json",    // 初始化接口
        iniUrl: "/bobogou/portal/data",
        clearUrl: "/bobogou/plugins/layui/api/clear.json", // 缓存清理接口
        urlHashLocation: true,      // 是否打开hash定位
        bgColorDefault: false,      // 主题默认配置
        multiModule: true,          // 是否开启多模块
        menuChildOpen: false,       // 是否默认展开菜单
        loadingTime: 0,             // 初始化加载时间
        pageAnim: true,             // iframe窗口动画
        maxTabNum: 20,              // 最大的tab打开数量
    };
    miniAdmin.render(options);

    // 百度统计代码，只统计指定域名
    miniTongji.render({
        specific: true,
        domains: [
            '99php.cn',
            'layuimini.99php.cn',
            'layuimini-onepage.99php.cn',
        ],
    });

    $('.login-out').on("click", function () {
        layer.msg('退出登录成功', function () {
            window.location = '/bobogou/system/loginPage';
        });
    });
});

//获取点击的行的数据id
function getIdSelections() {
    return $.map($("#portalTable").bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}

//刷新列表
function refresh() {
    $('#portalTable').bootstrapTable('refresh');
}

function add() {
    window.open('/portal/manager/portal/form/add', "新建招聘信息", 'height=600, width=800, top=30%,left=30%, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
}

function edit() {
    let id = getIdSelections();
    if (id.toString().length > 32) {
        alert("只能选择一条数据")
    } else if (id.toString().length < 32) {
        alert("请至少选择一条数据")
    } else if (id.toString().length = 32) {
        window.open('/portal/manager/portal/form/edit?id=' + id, "编辑招聘信息", 'height=600, width=800, top=30%,left=30%, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
    }

}

function view() {
    let id = getIdSelections();
    if (id.toString().length > 32) {
        alert("只能选择一条数据")
    } else if (id.toString().length < 32) {
        alert("请至少选择一条数据")
    } else if (id.toString().length = 32) {
        window.open('/portal/manager/portal/form/view?id=' + id, "查看招聘信息", 'height=600, width=800, top=30%,left=30%, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
    }
}

function del() {
    let ids = getIdSelections();
    if (ids == null || ids == '') {
        alert("请至少选择一条数据")
    } else {
        $.ajax({
            url: "/portal/manager/portal/delete?ids=" + ids,    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: "",    //参数值
            type: "POST",   //请求方式
            success: function (result) {
                //请求成功时处理
                alert(result.msg);
                //重新刷新页面
                //window.location.reload();
                refresh();
            }
        });
    }
}

function showSearchButton() {
    //$("#operatorSearchForm").attr();---也可以给标签设置属性值
    let attr = $("#portalSearchForm").data("collapse");
    if (attr) {
        //1.搜索表里有指定的属性值，此时搜索表为展开状态
        //2.判断属性值有否,需要移除data属性值，并移除”in“类
        $("#portalSearchForm").removeData("collapse");
        $("#portalSearchForm").removeClass("in");
    } else {
        //1.搜索表里没有指定的属性值，此时搜索表为隐藏状态
        //2.需要修改属性值，并且添加打开类”in“
        $("#portalSearchForm").data("collapse", "in");
        $("#portalSearchForm").addClass("in");
    }
}

function delivery(id) {
    rc.post("/bobogou/manager/bobogou/applybobogou", {"id": id});
}

function detail(id) {
    rc.open("/bobogou/manager/bobogou/detail?id=" + id, "查看招聘详情");
}