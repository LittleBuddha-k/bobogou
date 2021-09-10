layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#factorySterilizeTable',
        url: '/bobogou/data/factorySterilize/data',
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
                    title: '厂商',
                    field: 'factoryName',
                    sort: true,
                    sortName: 'factoryName',
                    width: '10%',
                    templet:function(data){
                        let factoryName = data.factoryName;
                        return factoryName;
                    }
                },
                {
                    title: '厂商产品类型',
                    field: 'productType',
                    sort: true,
                    sortName: 'productType',
                    width: '10%',
                    templet:function(data){
                        var productType = data.productType;
                        let dictName = rc.getDictName("data_factory_sterilize_product_type",productType);
                        if (dictName == '' || dictName == undefined){
                            return "未知";
                        }
                        return dictName;
                    }
                },
                {
                    title: '安全评估报告',
                    field: 'safetyAssessmentReport',
                    sort: true,
                    sortName: 'safetyAssessmentReport',
                    width: '10%',
                    templet: function (data) {
                        var safetyAssessmentReport = data.safetyAssessmentReport;
                        let split = safetyAssessmentReport.split(",");
                        let html = "";
                        if (safetyAssessmentReport != null || safetyAssessmentReport != ''){
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
                    title: '安全评价备案',
                    field: 'filingSafetyEvaluation',
                    sort: true,
                    sortName: 'filingSafetyEvaluation',
                    width: '10%',
                    templet: function (data) {
                        var filingSafetyEvaluation = data.filingSafetyEvaluation;
                        let split = filingSafetyEvaluation.split(",");
                        let html = "";
                        if (filingSafetyEvaluation != null || filingSafetyEvaluation != ''){
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
                    title: '企业标准',
                    field: 'companyStandard',
                    sort: true,
                    sortName: 'companyStandard',
                    width: '10%',
                    templet: function (data) {
                        var companyStandard = data.companyStandard;
                        let split = companyStandard.split(",");
                        let html = "";
                        if (companyStandard != null || companyStandard != ''){
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
                    title: '省检报告',
                    field: 'provincialInspectionReport',
                    sort: true,
                    sortName: 'provincialInspectionReport',
                    width: '10%',
                    templet: function (data) {
                        var provincialInspectionReport = data.provincialInspectionReport;
                        let split = provincialInspectionReport.split(",");
                        let html = "";
                        if (provincialInspectionReport != null || provincialInspectionReport != ''){
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
                    title: '包装备案',
                    field: 'packagingRecord',
                    sort: true,
                    sortName: 'packagingRecord',
                    width: '10%',
                    templet: function (data) {
                        var packagingRecord = data.packagingRecord;
                        let split = packagingRecord.split(",");
                        let html = "";
                        if (packagingRecord != null || packagingRecord != ''){
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
                    title: '生产厂家营业执照',
                    field: 'productionBusinessLicense',
                    sort: true,
                    sortName: 'productionBusinessLicense',
                    width: '10%',
                    templet: function (data) {
                        var productionBusinessLicense = data.productionBusinessLicense;
                        let split = productionBusinessLicense.split(",");
                        let html = "";
                        if (productionBusinessLicense != null || productionBusinessLicense != ''){
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
                    title: '消毒产品卫生许可证',
                    field: 'sanitaryLicenseDisinfectedProducts',
                    sort: true,
                    sortName: 'sanitaryLicenseDisinfectedProducts',
                    width: '10%',
                    templet: function (data) {
                        var sanitaryLicenseDisinfectedProducts = data.sanitaryLicenseDisinfectedProducts;
                        let split = sanitaryLicenseDisinfectedProducts.split(",");
                        let html = "";
                        if (sanitaryLicenseDisinfectedProducts != null || sanitaryLicenseDisinfectedProducts != ''){
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
                    title: '药品生产许可证',
                    field: 'drugManufacturingErtificate',
                    sort: true,
                    sortName: 'drugManufacturingErtificate',
                    width: '10%',
                    templet: function (data) {
                        var drugManufacturingErtificate = data.drugManufacturingErtificate;
                        let split = drugManufacturingErtificate.split(",");
                        let html = "";
                        if (drugManufacturingErtificate != null || drugManufacturingErtificate != ''){
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
                    title: '药品GMP证书',
                    field: 'gmpErtificate',
                    sort: true,
                    sortName: 'gmpErtificate',
                    width: '10%',
                    templet: function (data) {
                        var gmpErtificate = data.gmpErtificate;
                        let split = gmpErtificate.split(",");
                        let html = "";
                        if (gmpErtificate != null || gmpErtificate != ''){
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
                    title: '质量体系调查表',
                    field: 'qualitySystem',
                    sort: true,
                    sortName: 'qualitySystem',
                    width: '10%',
                    templet: function (data) {
                        var qualitySystem = data.qualitySystem;
                        let split = qualitySystem.split(",");
                        let html = "";
                        if (qualitySystem != null || qualitySystem != ''){
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
                    title: '合格供货方档案表',
                    field: 'qualifiedSupplier',
                    sort: true,
                    sortName: 'qualifiedSupplier',
                    width: '10%',
                    templet: function (data) {
                        var qualifiedSupplier = data.qualifiedSupplier;
                        let split = qualifiedSupplier.split(",");
                        let html = "";
                        if (qualifiedSupplier != null || qualifiedSupplier != ''){
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
                    title: '操作',
                    toolbar: '#operation',
                    align: "center",
                    width: '10%'
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
        table.reload('factorySterilizeTable', {
            where: {
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(factorySterilizeTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            let index = rc.openSaveDialog("/bobogou/data/factorySterilize/form/add", "新建消毒产品信息",'1010px','700px')
        }
    });

    table.on('tool(factorySterilizeTableFilter)', function (obj) {
        let id = obj.data.id;
        let event = obj.event;
        if (event === 'edit') {
            rc.openSaveDialog('/bobogou/data/factorySterilize/form/edit?id=' + id, "编辑消毒产品信息", '1010px', '700px');
        }else if (event === 'delete') {
            rc.confirm('确认要删除该消毒产品信息吗？', function () {
                rc.post("/bobogou/data/factorySterilize/delete?ids=" + id, '', function (data) {
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
    var checkStatus = table.checkStatus('factorySterilizeTable'),
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
        table.reload('factorySterilizeTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'data');
    })
}