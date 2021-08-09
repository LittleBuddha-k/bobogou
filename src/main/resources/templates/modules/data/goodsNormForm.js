layui.use(['upload', 'element', 'form', 'layedit', 'laydate'], function() {
    var $ = layui.jquery
        , form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        ,upload = layui.upload
        ,element = layui.element;
    //各种基于事件的操作，下面会有进一步介绍

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
            var last_url = $("#sampleBox").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#sampleBox").val(upload_image_url);
        }
    });

    //多图片上传
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
            var last_url = $("#outerPackingBox").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#outerPackingBox").val(upload_image_url);
        }
    });

    //多图片上传
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
            var last_url = $("#instructionBook").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#instructionBook").val(upload_image_url);
        }
    });

    //多图片上传
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
            var last_url = $("#otherData").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#otherData").val(upload_image_url);
        }
    });

    //多图片上传
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
            var last_url = $("#relatedPictures").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#relatedPictures").val(upload_image_url);
        }
    });

    //多图片上传
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
            var last_url = $("#instructions").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#instructions").val(upload_image_url);
        }
    });

    //多图片上传
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
            var last_url = $("#qualityStandard").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#qualityStandard").val(upload_image_url);
        }
    });

    //多图片上传
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
            var last_url = $("#surveyReport").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#surveyReport").val(upload_image_url);
        }
    });

    //多图片上传
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
            var last_url = $("#productionBusinessLicense").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#productionBusinessLicense").val(upload_image_url);
        }
    });

    //多图片上传
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
            var last_url = $("#productionCertificate").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#productionCertificate").val(upload_image_url);
        }
    });
});

//保存方法
function save(parentIndex) {
    var isValidate = rc.validateForm('#goodsNormForm');//校验表单
    if(!isValidate){
        return false;
    }else {
        $.ajax({
            url: "/bobogou/data/goodsNorm/save",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: $("#goodsNormForm").serialize(),    //参数值
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
    $("#sampleBox").val('');
});

/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test2").click(function () {
    $('#demo2').html("");
    $("#outerPackingBox").val('');
});

/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test3").click(function () {
    $('#demo3').html("");
    $("#instructionBook").val('');
});

/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test4").click(function () {
    $('#demo4').html("");
    $("#otherData").val('');
});

/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test5").click(function () {
    $('#demo5').html("");
    $("#relatedPictures").val('');
});

/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test6").click(function () {
    $('#demo6').html("");
    $("#instructions").val('');
});

/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test7").click(function () {
    $('#demo7').html("");
    $("#qualityStandard").val('');
});

/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test8").click(function () {
    $('#demo8').html("");
    $("#surveyReport").val('');
});

/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test9").click(function () {
    $('#demo9').html("");
    $("#productionBusinessLicense").val('');
});

/**
 * 多图清除按钮点击事件
 */
$("#btn_image_clear_test10").click(function () {
    $('#demo10').html("");
    $("#productionCertificate").val('');
});