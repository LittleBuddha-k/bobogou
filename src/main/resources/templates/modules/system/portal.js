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
        rc.confirm('确定退出系统吗？', function () {
            rc.post('/bobogou/system/logout','',function (data) {
                if (data.code == 200){
                    window.location = "/bobogou/system/loginPage";
                    rc.msg("退出登录成功");
                }else {
                    rc.msg("退出登录失败");
                }
            })
        });
    });
});

$("#information").click(function () {
    let currentUser = $("#currentUser").val();
    rc.openSaveDialog('/bobogou/system/operator/form/edit?id=' + currentUser, "编辑用户信息",'75%','70%');
})