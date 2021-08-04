layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#goodsNormTable',
        url: '/bobogou/data/goodsNorm/data',
        method: 'GET',
        request: {
            pageName: 'pageNo', // page
            limitName: 'pageSize' // limit
        },//重命名参数名称
        done: function (res) {
            //做checkbox回显
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
        },
        toolbar: '#toolBar',
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
                    title: '商品名称',
                    field: 'goodsName',
                    sort: true,
                    sortName: 'goodsName',
                    templet:function(data){
                        let goodsName = data.goodsName;
                        return goodsName;
                    }
                },
                {
                    title: '厂商名称',
                    field: 'factoryName',
                    sort: true,
                    sortName: 'factoryName'
                },/*
                {
                    title: '样盒图片地址',
                    field: 'sampleBox',
                    sort: true,
                    sortName: 'sampleBox',
                    templet: function (data) {
                        var sampleBox = data.sampleBox;
                        let split = sampleBox.split(",");
                        let html = "";
                        if (sampleBox != null || sampleBox != ''){
                            for (let i = 0;i<split.length;i++){
                                if (split[i] != '') {
                                    html += '<img src="' + split[i] + '" alt="" style="width: 47px;height: 33px;" class="layui-upload-img">'
                                }
                            }
                            return html;
                        }else {
                            return "无图片";
                        }
                    }
                },
                {
                    title: '产品外包装盒',
                    field: 'outerPackingBox',
                    sort: true,
                    sortName: 'outerPackingBox',
                    templet: function (data) {
                        var outerPackingBox = data.outerPackingBox;
                        let split = outerPackingBox.split(",");
                        let html = "";
                        if (outerPackingBox != null || outerPackingBox != ''){
                            for (let i = 0;i<split.length;i++){
                                if (split[i] != '') {
                                    html += '<img src="' + split[i] + '" alt="" style="width: 47px;height: 33px;" class="layui-upload-img">'
                                }
                            }
                            return html;
                        }else {
                            return "无图片";
                        }
                    }
                },
                {
                    title: '说明书图片',
                    field: 'instructionBook',
                    sort: true,
                    sortName: 'instructionBook',
                    templet: function (data) {
                        var instructionBook = data.instructionBook;
                        let split = instructionBook.split(",");
                        let html = "";
                        if (instructionBook != null || instructionBook != ''){
                            for (let i = 0;i<split.length;i++){
                                if (split[i] != '') {
                                    html += '<img src="' + split[i] + '" alt="" style="width: 47px;height: 33px;" class="layui-upload-img">'
                                }
                            }
                            return html;
                        }else {
                            return "无图片";
                        }
                    }
                },
                {
                    title: '其他',
                    field: 'otherData',
                    sort: true,
                    sortName: 'otherData',
                    templet: function (data) {
                        var otherData = data.otherData;
                        let split = otherData.split(",");
                        let html = "";
                        if (otherData != null || otherData != ''){
                            for (let i = 0;i<split.length;i++){
                                if (split[i] != '') {
                                    html += '<img src="' + split[i] + '" alt="" style="width: 47px;height: 33px;" class="layui-upload-img">'
                                }
                            }
                            return html;
                        }else {
                            return "无图片";
                        }
                    }
                },
                {
                    title: '相关图片',
                    field: 'relatedPictures',
                    sort: true,
                    sortName: 'relatedPictures',
                    templet: function (data) {
                        var relatedPictures = data.relatedPictures;
                        let split = relatedPictures.split(",");
                        let html = "";
                        if (relatedPictures != null || relatedPictures != ''){
                            for (let i = 0;i<split.length;i++){
                                if (split[i] != '') {
                                    html += '<img src="' + split[i] + '" alt="" style="width: 47px;height: 33px;" class="layui-upload-img">'
                                }
                            }
                            return html;
                        }else {
                            return "无图片";
                        }
                    }
                },
                {
                    title: '批件',
                    field: 'instructions',
                    sort: true,
                    sortName: 'instructions',
                    templet: function (data) {
                        var instructions = data.instructions;
                        let split = instructions.split(",");
                        let html = "";
                        if (instructions != null || instructions != ''){
                            for (let i = 0;i<split.length;i++){
                                if (split[i] != '') {
                                    html += '<img src="' + split[i] + '" alt="" style="width: 47px;height: 33px;" class="layui-upload-img">'
                                }
                            }
                            return html;
                        }else {
                            return "无图片";
                        }
                    }
                },
                {
                    title: '质量标准',
                    field: 'qualityStandard',
                    sort: true,
                    sortName: 'qualityStandard',
                    width: '10%'
                },
                {
                    title: '省检验报告',
                    field: 'surveyReport',
                    sort: true,
                    sortName: 'surveyReport',
                    width: '10%'
                },*/
                {
                    title: '物价',
                    field: 'prices',
                    sort: true,
                    sortName: 'prices'
                },/*
                {
                    title: '生产企业营业执照地址',
                    field: 'productionBusinessLicense',
                    sort: true,
                    sortName: 'productionBusinessLicense',
                    width: '20%'
                },
                {
                    title: '生产企业生产许可证地址',
                    field: 'productionCertificate',
                    sort: true,
                    sortName: 'productionCertificate',
                    width: '20%'
                },*/
                {
                    title: '操作',
                    toolbar: '#operation',
                    align: "center"
                }
            ]
        ],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 10,
        page: true,
        skin: 'line',
        where: {
        }, //如果无需传递额外参数，可不加该参数
        sort: true
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('goodsNormTable', {
            where: {
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(goodsNormTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            let index = rc.openSaveDialog("/bobogou/data/goodsNorm/form/add", "新建商品规范信息",'744px','700px')
        }
    });

    table.on('tool(goodsNormTableFilter)', function (obj) {
        let id = obj.data.id;
        let event = obj.event;
        if (event === 'edit') {
            rc.openSaveDialog('/bobogou/data/goodsNorm/form/edit?id=' + id, "编辑商品规范信息", '744px', '700px');
        } else if (event === 'delete') {
            rc.confirm('确认要删除该商品规范信息吗？', function () {
                rc.post("/bobogou/data/goodsNorm/delete?ids=" + id, '', function (data) {
                    if (data.code == 200) {
                        //执行搜索重载
                        refresh();
                        rc.alert(data.msg);
                    } else {
                        rc.alert(data.msg);
                    }
                });
            })
        }
    });
});

/**
 * 获取layui table 复选框的id
 * @param table -- table = layui.table;
 * @param tableId -- layui table 的id
 * @returns {string}
 */
function getIdSelections(table) {
    var checkStatus = table.checkStatus('goodsNormTable'),
        data = checkStatus.data;
    let ids = "";
    for (let i = 0; i < data.length; i++) {
        ids = ids + data[i].id + ",";
    }
    ;
    return ids;
}

function refresh() {
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;

        //执行搜索重载
        table.reload('goodsNormTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'data');
    })
}