layui.use(['upload', 'element', 'form', 'layedit', 'laydate'], function() {
    var $ = layui.jquery
        , form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        ,upload = layui.upload
        ,element = layui.element;
    //各种基于事件的操作，下面会有进一步介绍

    //处理厂商类型不同时显示不同上传资料
    form.on('select(factoryType)', function(data){
        //console.log(data.elem); //得到select原始DOM对象
        //console.log(data.value); //得到被选中的值
        //console.log(data.othis); //得到美化后的DOM对象
        let factoryType = data.value;
        if (1 == factoryType){
            $("#sampleInvoiceTicketShow").show();
            $("#qualityGuaranteeShow").show();
            $("#invoiceCounterpartsShow").show();
        }else if (2 == factoryType){
            $("#sampleInvoiceTicketShow").hide();
            $("#qualityGuaranteeShow").hide();
            $("#invoiceCounterpartsShow").hide();
        }
    });
    //处理厂商产品类型不同时显示不同上传资料
    form.on('select(productType)', function(data){
    });
    //处理是否委托人采购或则销售不同时显示不同上传资料
    form.on('select(isBailor)', function(data){
        let isBailor = data.value;
        if (1 == isBailor){
            $("#bailorCardShow").show();
            $("#mandataryCardShow").show();
            $("#takeDeliveryBailmentShow").show();
        }else if (0 == isBailor){
            $("#bailorCardShow").hide();
            $("#mandataryCardShow").hide();
            $("#takeDeliveryBailmentShow").hide();
        }
    });

    //下拉框选中后的时间
    form.on('select(province)', function(data){
        let provinceId = data.value;
        $("#city").empty();//清空城市选项
        $("#area").empty();//清空城市选项
        $("#street").empty();//清空城市选项
        rc.post("/bobogou/data/city/all",{"province.id":provinceId},function(data){
            if(data.length>0) {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="">请选择</option>';
                for(let i=0;i<data.length;i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#city").html(tmp);
                form.render();
            }
        })
    });
    //下拉框选中后的时间
    form.on('select(city)', function(data){
        let cityId = data.value;
        $("#area").empty();//清空城市选项
        $("#street").empty();//清空城市选项
        rc.post("/bobogou/data/area/all",{"city.id":cityId},function(data){
            if(data.length>0) {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="">请选择</option>';
                for(let i=0;i<data.length;i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#area").html(tmp);
                form.render();
            }
        })
    });
    //下拉框选中后的时间
    form.on('select(area)', function(data){
        let streetId = data.value;
        $("#street").empty();//清空城市选项
        rc.post("/bobogou/data/street/all",{"area.id":streetId},function(data){
            if(data.length>0) {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="">请选择</option>';
                for(let i=0;i<data.length;i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#street").html(tmp);
                form.render();
            }
        })
    });

    //多图片上传
    upload.render({
        elem: '#test1',
        url: '/bobogou/file/picture?uploadPath='+"/data/banner",
        multiple: true,
        before: function(obj){
            //预读本地文件示例，不支持ie8---------base64
            obj.preview(function(index, file, result){
                $('#demo1').append('<img src="'+ result +'" alt="'+ file.name +'" style="width: 92px;height: 92px;" class="layui-upload-img">')
            });
        },
        done: function(res){
            //上传完毕
            var last_url = $("#cardFront").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#cardFront").val(upload_image_url);
        }
    });//多图片上传
    upload.render({
        elem: '#test2',
        url: '/bobogou/file/picture?uploadPath='+"/data/banner",
        multiple: true,
        before: function(obj){
            //预读本地文件示例，不支持ie8---------base64
            obj.preview(function(index, file, result){
                $('#demo2').append('<img src="'+ result +'" alt="'+ file.name +'" style="width: 92px;height: 92px;" class="layui-upload-img">')
            });
        },
        done: function(res){
            //上传完毕
            var last_url = $("#cardBack").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#cardBack").val(upload_image_url);
        }
    });//多图片上传
    upload.render({
        elem: '#test3',
        url: '/bobogou/file/picture?uploadPath='+"/data/banner",
        multiple: true,
        before: function(obj){
            //预读本地文件示例，不支持ie8---------base64
            obj.preview(function(index, file, result){
                $('#demo3').append('<img src="'+ result +'" alt="'+ file.name +'" style="width: 92px;height: 92px;" class="layui-upload-img">')
            });
        },
        done: function(res){
            //上传完毕
            var last_url = $("#businessLicense").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#businessLicense").val(upload_image_url);
        }
    });//多图片上传
    upload.render({
        elem: '#test4',
        url: '/bobogou/file/picture?uploadPath='+"/data/banner",
        multiple: true,
        before: function(obj){
            //预读本地文件示例，不支持ie8---------base64
            obj.preview(function(index, file, result){
                $('#demo4').append('<img src="'+ result +'" alt="'+ file.name +'" style="width: 92px;height: 92px;" class="layui-upload-img">')
            });
        },
        done: function(res){
            //上传完毕
            var last_url = $("#annualReport").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#annualReport").val(upload_image_url);
        }
    });//多图片上传
    upload.render({
        elem: '#test5',
        url: '/bobogou/file/picture?uploadPath='+"/data/banner",
        multiple: true,
        before: function(obj){
            //预读本地文件示例，不支持ie8---------base64
            obj.preview(function(index, file, result){
                $('#demo5').append('<img src="'+ result +'" alt="'+ file.name +'" style="width: 92px;height: 92px;" class="layui-upload-img">')
            });
        },
        done: function(res){
            //上传完毕
            var last_url = $("#businessPermit").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#businessPermit").val(upload_image_url);
        }
    });//多图片上传
    upload.render({
        elem: '#test6',
        url: '/bobogou/file/picture?uploadPath='+"/data/banner",
        multiple: true,
        before: function(obj){
            //预读本地文件示例，不支持ie8---------base64
            obj.preview(function(index, file, result){
                $('#demo6').append('<img src="'+ result +'" alt="'+ file.name +'" style="width: 92px;height: 92px;" class="layui-upload-img">')
            });
        },
        done: function(res){
            //上传完毕
            var last_url = $("#basicAccount").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#basicAccount").val(upload_image_url);
        }
    });//多图片上传
    upload.render({
        elem: '#test7',
        url: '/bobogou/file/picture?uploadPath='+"/data/banner",
        multiple: true,
        before: function(obj){
            //预读本地文件示例，不支持ie8---------base64
            obj.preview(function(index, file, result){
                $('#demo7').append('<img src="'+ result +'" alt="'+ file.name +'" style="width: 92px;height: 92px;" class="layui-upload-img">')
            });
        },
        done: function(res){
            //上传完毕
            var last_url = $("#billingInformation").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#billingInformation").val(upload_image_url);
        }
    });//多图片上传
    upload.render({
        elem: '#test8',
        url: '/bobogou/file/picture?uploadPath='+"/data/banner",
        multiple: true,
        before: function(obj){
            //预读本地文件示例，不支持ie8---------base64
            obj.preview(function(index, file, result){
                $('#demo8').append('<img src="'+ result +'" alt="'+ file.name +'" style="width: 92px;height: 92px;" class="layui-upload-img">')
            });
        },
        done: function(res){
            //上传完毕
            var last_url = $("#sampleInvoiceTicket").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#sampleInvoiceTicket").val(upload_image_url);
        }
    });//多图片上传
    upload.render({
        elem: '#test9',
        url: '/bobogou/file/picture?uploadPath='+"/data/banner",
        multiple: true,
        before: function(obj){
            //预读本地文件示例，不支持ie8---------base64
            obj.preview(function(index, file, result){
                $('#demo9').append('<img src="'+ result +'" alt="'+ file.name +'" style="width: 92px;height: 92px;" class="layui-upload-img">')
            });
        },
        done: function(res){
            //上传完毕
            var last_url = $("#qualityGuarantee").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#qualityGuarantee").val(upload_image_url);
        }
    });//多图片上传
    upload.render({
        elem: '#test10',
        url: '/bobogou/file/picture?uploadPath='+"/data/banner",
        multiple: true,
        before: function(obj){
            //预读本地文件示例，不支持ie8---------base64
            obj.preview(function(index, file, result){
                $('#demo10').append('<img src="'+ result +'" alt="'+ file.name +'" style="width: 92px;height: 92px;" class="layui-upload-img">')
            });
        },
        done: function(res){
            //上传完毕
            var last_url = $("#sealImpression").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#sealImpression").val(upload_image_url);
        }
    });//多图片上传
    upload.render({
        elem: '#test11',
        url: '/bobogou/file/picture?uploadPath='+"/data/banner",
        multiple: true,
        before: function(obj){
            //预读本地文件示例，不支持ie8---------base64
            obj.preview(function(index, file, result){
                $('#demo11').append('<img src="'+ result +'" alt="'+ file.name +'" style="width: 92px;height: 92px;" class="layui-upload-img">')
            });
        },
        done: function(res){
            //上传完毕
            var last_url = $("#powerAttorney").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#powerAttorney").val(upload_image_url);
        }
    });
    //多图片上传
    upload.render({
        elem: '#test12',
        url: '/bobogou/file/picture?uploadPath='+"/data/banner",
        multiple: true,
        before: function(obj){
            //预读本地文件示例，不支持ie8---------base64
            obj.preview(function(index, file, result){
                $('#demo12').append('<img src="'+ result +'" alt="'+ file.name +'" style="width: 92px;height: 92px;" class="layui-upload-img">')
            });
        },
        done: function(res){
            //上传完毕
            var last_url = $("#invoiceCounterparts").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#invoiceCounterparts").val(upload_image_url);
        }
    });
    //多图片上传
    upload.render({
        elem: '#test13',
        url: '/bobogou/file/picture?uploadPath='+"/data/banner",
        multiple: true,
        before: function(obj){
            //预读本地文件示例，不支持ie8---------base64
            obj.preview(function(index, file, result){
                $('#demo13').append('<img src="'+ result +'" alt="'+ file.name +'" style="width: 92px;height: 92px;" class="layui-upload-img">')
            });
        },
        done: function(res){
            //上传完毕
            var last_url = $("#bailorCard").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#bailorCard").val(upload_image_url);
        }
    });
    //多图片上传
    upload.render({
        elem: '#test14',
        url: '/bobogou/file/picture?uploadPath='+"/data/banner",
        multiple: true,
        before: function(obj){
            //预读本地文件示例，不支持ie8---------base64
            obj.preview(function(index, file, result){
                $('#demo14').append('<img src="'+ result +'" alt="'+ file.name +'" style="width: 92px;height: 92px;" class="layui-upload-img">')
            });
        },
        done: function(res){
            //上传完毕
            var last_url = $("#mandataryCard").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#mandataryCard").val(upload_image_url);
        }
    });
    //多图片上传
    upload.render({
        elem: '#test15',
        url: '/bobogou/file/picture?uploadPath='+"/data/banner",
        multiple: true,
        before: function(obj){
            //预读本地文件示例，不支持ie8---------base64
            obj.preview(function(index, file, result){
                $('#demo15').append('<img src="'+ result +'" alt="'+ file.name +'" style="width: 92px;height: 92px;" class="layui-upload-img">')
            });
        },
        done: function(res){
            //上传完毕
            var last_url = $("#takeDeliveryBailment").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#takeDeliveryBailment").val(upload_image_url);
        }
    });
    //多图片上传
    upload.render({
        elem: '#test16',
        url: '/bobogou/file/picture?uploadPath='+"/data/banner",
        multiple: true,
        before: function(obj){
            //预读本地文件示例，不支持ie8---------base64
            obj.preview(function(index, file, result){
                $('#demo16').append('<img src="'+ result +'" alt="'+ file.name +'" style="width: 92px;height: 92px;" class="layui-upload-img">')
            });
        },
        done: function(res){
            //上传完毕
            var last_url = $("#foodBusinessLicense").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#foodBusinessLicense").val(upload_image_url);
        }
    });
});

//保存方法
function save(parentIndex) {
    var isValidate = rc.validateForm('#factoryForm');//校验表单
    if(!isValidate){
        return false;
    }else {
        $.ajax({
            url: "/bobogou/basic/factory/save",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: $("#factoryForm").serialize(),    //参数值
            type: "POST",   //请求方式
            success: function (result) {
                //假设这是iframe页
                if (200 == result.code){
                    //请求成功时处理
                    // 刷新整个父窗口
                    parent.refresh();
                    //假设这是iframe页
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    rc.msg(result.msg)
                    setTimeout(function(){
                        parent.layer.close(index); //再执行关闭
                    }, 1000);
                }else {
                    rc.alert(result.msg)
                }
            },
            error: function (result) {
                rc.alert(result.msg)
            }
        });
    }
}

/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test1").click(function () {
    $('#demo1').html("");
    $("#cardFront").val('');
});/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test2").click(function () {
    $('#demo2').html("");
    $("#cardBack").val('');
});/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test3").click(function () {
    $('#demo3').html("");
    $("#businessLicense").val('');
});/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test4").click(function () {
    $('#demo4').html("");
    $("#annualReport").val('');
});/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test5").click(function () {
    $('#demo5').html("");
    $("#businessPermit").val('');
});/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test6").click(function () {
    $('#demo6').html("");
    $("#basicAccount").val('');
});/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test7").click(function () {
    $('#demo7').html("");
    $("#billingInformation").val('');
});/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test8").click(function () {
    $('#demo8').html("");
    $("#sampleInvoiceTicket").val('');
});/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test9").click(function () {
    $('#demo9').html("");
    $("#sampleInvoiceTicket").val('');
});/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test10").click(function () {
    $('#demo10').html("");
    $("#sealImpression").val('');
});
/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test11").click(function () {
    $('#demo11').html("");
    $("#powerAttorney").val('');
});
/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test12").click(function () {
    $('#demo12').html("");
    $("#invoiceCounterparts").val('');
});
/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test13").click(function () {
    $('#demo13').html("");
    $("#bailorCard").val('');
});
/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test14").click(function () {
    $('#demo14').html("");
    $("#mandataryCard").val('');
});
/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test15").click(function () {
    $('#demo15').html("");
    $("#takeDeliveryBailment").val('');
});
/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test16").click(function () {
    $('#demo16').html("");
    $("#foodBusinessLicense").val('');
});