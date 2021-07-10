layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#factoryTable',
        url: '/bobogou/basic/factory/data',
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
                    title: '厂商类型',
                    field: 'factoryType',
                    sort: true,
                    sortName: 'factoryType',
                    width: '10%',
                    templet:function(data){
                        let factoryType = data.factoryType;
                        return factoryType;
                    }
                },
                {
                    title: '厂商产品类型',
                    field: 'productType',
                    sort: true,
                    sortName: 'productType',
                    width: '10%'
                },
                {
                    title: '厂商名称',
                    field: 'factoryName',
                    sort: true,
                    sortName: 'factoryName',
                    width: '10%'
                },
                {
                    title: '联系人',
                    field: 'linkman',
                    sort: true,
                    sortName: 'linkman',
                    width: '10%'
                },
                {
                    title: '联系人手机号',
                    field: 'phone',
                    sort: true,
                    sortName: 'phone',
                    width: '10%'
                },
                {
                    title: '身份证号',
                    field: 'idNumber',
                    sort: true,
                    sortName: 'idNumber',
                    width: '10%'
                },
                {
                    title: '身份证正面',
                    field: 'cardFront',
                    sort: true,
                    sortName: 'cardFront',
                    width: '10%',
                    templet: function (data) {
                        var cardFront = data.cardFront;
                        let split = cardFront.split(",");
                        let html = "";
                        if (cardFront != null || cardFront != ''){
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
                    title: '身份证背面',
                    field: 'cardBack',
                    sort: true,
                    sortName: 'cardBack',
                    width: '10%',
                    templet: function (data) {
                        var cardBack = data.cardBack;
                        let split = cardBack.split(",");
                        let html = "";
                        if (cardBack != null || cardBack != ''){
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
                    title: '联系地址',
                    field: 'address',
                    sort: true,
                    sortName: 'address',
                    width: '10%'
                },
                {
                    title: '营业执照复印件',
                    field: 'businessLicense',
                    sort: true,
                    sortName: 'businessLicense',
                    width: '10%',
                    templet: function (data) {
                        var businessLicense = data.businessLicense;
                        let split = businessLicense.split(",");
                        let html = "";
                        if (businessLicense != null || businessLicense != ''){
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
                    title: '上年度报告',
                    field: 'annualReport',
                    sort: true,
                    sortName: 'annualReport',
                    width: '10%',
                    templet: function (data) {
                        var annualReport = data.annualReport;
                        let split = annualReport.split(",");
                        let html = "";
                        if (annualReport != null || annualReport != ''){
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
                    title: '经营许可证复印件',
                    field: 'businessPermit',
                    sort: true,
                    sortName: 'businessPermit',
                    width: '10%',
                    templet: function (data) {
                        var businessPermit = data.businessPermit;
                        let split = businessPermit.split(",");
                        let html = "";
                        if (businessPermit != null || businessPermit != ''){
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
                    title: '基本户复印件',
                    field: 'basicAccount',
                    sort: true,
                    sortName: 'basicAccount',
                    width: '10%',
                    templet: function (data) {
                        var basicAccount = data.basicAccount;
                        let split = basicAccount.split(",");
                        let html = "";
                        if (basicAccount != null || basicAccount != ''){
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
                    title: '开票信息',
                    field: 'billingInformation',
                    sort: true,
                    sortName: 'billingInformation',
                    width: '10%',
                    templet: function (data) {
                        var billingInformation = data.billingInformation;
                        let split = billingInformation.split(",");
                        let html = "";
                        if (billingInformation != null || billingInformation != ''){
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
                    title: '增值税发票票样',
                    field: 'sampleInvoiceTicket',
                    sort: true,
                    sortName: 'sampleInvoiceTicket',
                    width: '10%',
                    templet: function (data) {
                        var sampleInvoiceTicket = data.sampleInvoiceTicket;
                        let split = sampleInvoiceTicket.split(",");
                        let html = "";
                        if (sampleInvoiceTicket != null || sampleInvoiceTicket != ''){
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
                    title: '质保协议',
                    field: 'qualityGuarantee',
                    sort: true,
                    sortName: 'qualityGuarantee',
                    width: '10%',
                    templet: function (data) {
                        var qualityGuarantee = data.qualityGuarantee;
                        let split = qualityGuarantee.split(",");
                        let html = "";
                        if (qualityGuarantee != null || qualityGuarantee != ''){
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
                    title: '印章模板',
                    field: 'sealImpression',
                    sort: true,
                    sortName: 'sealImpression',
                    width: '10%',
                    templet: function (data) {
                        var sealImpression = data.sealImpression;
                        let split = sealImpression.split(",");
                        let html = "";
                        if (sealImpression != null || sealImpression != ''){
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
                    title: '销售委托书',
                    field: 'powerAttorney',
                    sort: true,
                    sortName: 'powerAttorney',
                    width: '10%',
                    templet: function (data) {
                        var powerAttorney = data.powerAttorney;
                        let split = powerAttorney.split(",");
                        let html = "";
                        if (powerAttorney != null || powerAttorney != ''){
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
                    title: '出货同行单样票',
                    field: 'invoiceCounterparts',
                    sort: true,
                    sortName: 'invoiceCounterparts',
                    width: '10%',
                    templet: function (data) {
                        var invoiceCounterparts = data.invoiceCounterparts;
                        let split = invoiceCounterparts.split(",");
                        let html = "";
                        if (invoiceCounterparts != null || invoiceCounterparts != ''){
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
                    title: '委托人身份证复印件',
                    field: 'bailorCard',
                    sort: true,
                    sortName: 'bailorCard',
                    width: '10%',
                    templet: function (data) {
                        var bailorCard = data.bailorCard;
                        let split = bailorCard.split(",");
                        let html = "";
                        if (bailorCard != null || bailorCard != ''){
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
                    title: '被委托人身份证复印件',
                    field: 'mandataryCard',
                    sort: true,
                    sortName: 'mandataryCard',
                    width: '10%',
                    templet: function (data) {
                        var mandataryCard = data.mandataryCard;
                        let split = mandataryCard.split(",");
                        let html = "";
                        if (mandataryCard != null || mandataryCard != ''){
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
                    title: '收货委托书',
                    field: 'takeDeliveryBailment',
                    sort: true,
                    sortName: 'takeDeliveryBailment',
                    width: '10%',
                    templet: function (data) {
                        var takeDeliveryBailment = data.takeDeliveryBailment;
                        let split = takeDeliveryBailment.split(",");
                        let html = "";
                        if (takeDeliveryBailment != null || takeDeliveryBailment != ''){
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
                    title: '食品经营许可证',
                    field: 'foodBusinessLicense',
                    sort: true,
                    sortName: 'foodBusinessLicense',
                    width: '10%',
                    templet: function (data) {
                        var foodBusinessLicense = data.foodBusinessLicense;
                        let split = foodBusinessLicense.split(",");
                        let html = "";
                        if (foodBusinessLicense != null || foodBusinessLicense != ''){
                            for (let i = 0;i<split.length;i++){
                                if (split[i] != ''){
                                    html += '<img src="'+ split[i] +'" alt="" style="width: 47px;height: 33px;" class="layui-upload-img">'
                                }
                            }
                            return html;
                        }else {
                            return "无图片";
                        }
                    }
                },
                {
                    title: '是否委托人采购或则销售',
                    field: 'isBailor',
                    sort: true,
                    sortName: 'isBailor',
                    width: '10%',
                    templet: function (data) {
                        var isBailor = data.isBailor;
                        if (0 == isBailor){
                            return "否";
                        }else if (1 == isBailor){
                            return "是";
                        }else {
                            return "未知";
                        }
                    }
                },
                {
                    title: '操作',
                    toolbar: '#operation',
                    align: "center",
                    width: '12%'
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
        table.reload('factoryTable', {
            where: {
            }
        });
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(factoryTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            let index = rc.openSaveDialog("/bobogou/basic/factory/form/add", "新建厂商信息",'100%','100%')
        }
    });

    table.on('tool(factoryTableFilter)', function (obj) {
        let id = obj.data.id;
        let event = obj.event;
        if (event === 'edit') {
            rc.openSaveDialog('/bobogou/basic/factory/form/edit?id=' + id, "编辑厂商信息", '100%', '100%');
        }else if (event === 'view') {
            rc.openSaveDialog('/bobogou/basic/factory/form/view?id=' + id, "查看厂商信息", '100%', '100%');
        }else if (event === 'delete') {
            rc.confirm('确认要删除该厂商信息吗？', function () {
                rc.post("/bobogou/basic/factory/delete?ids=" + id, '', function (data) {
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
    var checkStatus = table.checkStatus('factoryTable'),
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
        table.reload('factoryTable', {
            page: {
                curr: 1
            }
            , where: {

            }
        }, 'data');
    })
}