layui.use(['upload', 'element', 'form', 'layedit', 'laydate'], function () {
    var $ = layui.jquery
        , form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        , upload = layui.upload
        , element = layui.element;
    //各种基于事件的操作，下面会有进一步介绍

    //数组对象定义一个函数
    Array.prototype.indexOf = function(val) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == val) return i;
        }
        return -1;
    };
    //得到这个元素的索引,数组自己固有的函数去删除这个元素
    Array.prototype.remove = function(val) {
        var index = this.indexOf(val);
        if (index > -1) {
            this.splice(index, 1);
        }
    };

    //对标签的操作----点击时才生效
    form.on('checkbox(tag)', function (data) {
        //console.log(data.elem); //得到checkbox原始DOM对象
        let checked = data.elem.checked; //是否被选中，true或者false
        let id = data.value; //复选框value值，也可以通过data.elem.value得到
        if (checked) {
            /*let tagId = $("#tagId").val();
            let get = tagId + "," + id;
            $("#tagId").val(get);*/
            let tagId = $("#tagId").val();
            var select = "";
            if (tagId) {
                select = tagId + "," + id;
            } else {
                select = id;
            }
            $("#tagId").val(select);
        } else {
            let tagId = $("#tagId").val();
            let split = tagId.split(",");
            var select = "";
            if (tagId) {
                select = id;
                for(var i = 0;i<split.length;i++){
                    if (select == split[i]){
                        split.remove(select);
                        $("#tagId").val(split);
                    }
                }
            } else {

            }
        }
        //console.log($("#tagId").val())
        //console.log(data.othis); //得到美化后的DOM对象
    });

    //对其他分类操作----点击时才生效
    form.on('checkbox(goodsTypeId)', function (data) {
        let checked = data.elem.checked; //是否被选中，true或者false
        let id = data.value; //复选框value值，也可以通过data.elem.value得到
        if (checked) {
            let goodsType = $("#goodsTypeId").val();
            /*let get = tagId + "," + id;*/
            var select = "";
            if (goodsType) {
                select = goodsType + "," + id;
            } else {
                select = id;
            }
            $("#goodsTypeId").val(select);
        } else {
            let goodsType = $("#goodsTypeId").val();
            let split = goodsType.split(",");
            var select = "";
            if (goodsType) {
                select = id;
                for(var i = 0;i<split.length;i++){
                    if (select == split[i]){
                        split.remove(select);
                        $("#goodsTypeId").val(split);
                    }
                }
            } else {

            }
        }
    });

    //品牌一级分类联动二级分类
    form.on('select(brandId)', function (data) {
        let brandId = data.value;
        $("#secondBrandId").empty();//清空二级选项
        rc.post("/bobogou/data/goodsBrand/dataList", {"parentId": brandId}, function (data) {
            if (data.length > 0) {
                //对应的值传回，拼出html下拉框语句
                var tmp = '<option value="">请选择</option>';
                for (let i = 0; i < data.length; i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].brandName + "</option>";
                }
                $("#secondBrandId").html(tmp);
                form.render();
            } else {
                var tmp = '<option value="-1">请选择</option>';
                $("#secondBrandId").html(tmp);
                form.render();
            }
        })
    });

    //生产日期
    laydate.render({
        elem: '#productTime'
        , type: 'datetime'
    });

    //商品分类级联下拉框
    //下拉框选中后的时间
    form.on('select(levelOne)', function (data) {
        let parentId = data.value;
        $("#levelTwo").empty();//清空二级
        $("#levelThree").empty();//清空三级
        rc.post("/bobogou/data/classify/all", {"parentId": parentId}, function (data) {
            if (data.length > 0) {
                //对应的值传回，拼出html下拉框语句
                var tmp = '<option value="0">请选择</option>';
                for (let i = 0; i < data.length; i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#levelTwo").html(tmp);
                form.render();
            } else {
                //对应的值传回，拼出html下拉框语句
                var tmp = '<option value="0">请选择</option>';
                $("#levelTwo").html(tmp);
                form.render();
            }
        })
    });
    //下拉框选中后的时间
    form.on('select(levelTwo)', function (data) {
        let parentId = data.value;
        $("#levelThree").empty();//清空城市选项
        rc.post("/bobogou/data/classify/all", {"parentId": parentId}, function (data) {
            if (data.length > 0) {
                //对应的值传回，拼出html下拉框语句
                var tmp = '<option value="0">请选择</option>';
                for (let i = 0; i < data.length; i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#levelThree").html(tmp);
                form.render();
            } else {
                //对应的值传回，拼出html下拉框语句
                var tmp = '<option value="0">请选择</option>';
                $("#levelThree").html(tmp);
                form.render();
            }
        })
    });

    //多图片上传药品资质证书（带水印）
    //拖拽上传
    upload.render({
        elem: '#test1',
        url: '/bobogou/file/upload-watermark', //改成您自己的上传接口
        multiple: true,
        accept: 'images',
        exts: 'jpg|png|jpeg|tif',
        done: function (res) {
            layer.msg('上传成功');
            layui.$('#uploadDemoView').removeClass('layui-hide').find('img').attr('src', res.body.url);
            $("#certificateImageWatermark").val(res.body.url)
        }
    });
    //upload.render({
    //    elem: '#test1',
    //    url: '/bobogou/file/upload-watermark',
    //    multiple: true,
    //    accept: 'images',
    //    exts: 'jpg|png|jpeg|tif',
    //    done: function(res){
    //        //上传完毕
    //        var last_url = $("#certificateImageWatermark").val();
    //        var upload_image_url = "";
    //        if(last_url){
    //            upload_image_url = last_url+","+res.body.url;
    //        }else {
    //            upload_image_url = res.body.url;
    //        }
    //        $("#certificateImageWatermark").val(upload_image_url);
    //        $('#demo1').append('<img src="'+ res.body.url +'" alt="" style="width: 92px;height: 92px;" class="layui-upload-img">');
    //    }
    //});
    //多图片上传药品资质证书（无水印）
    //拖拽上传
    upload.render({
        elem: '#test2',
        url: '/bobogou/file/picture', //改成您自己的上传接口
        multiple: true,
        accept: 'images',
        exts: 'jpg|png|jpeg|tif',
        done: function (res) {
            layer.msg('上传成功');
            layui.$('#uploadDemoView2').removeClass('layui-hide').find('img').attr('src', res.body.url);
            $("#certificateImage").val(res.body.url)
        }
    });
    /*upload.render({
        elem: '#test2',
        url: '/bobogou/file/picture',
        multiple: true,
        accept: 'images',
        exts: 'jpg|png|jpeg|tif',
        done: function(res){
            //上传完毕
            var last_url = $("#certificateImage").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#certificateImage").val(upload_image_url);
            $('#demo2').append('<img src="'+ res.body.url +'" alt="" style="width: 92px;height: 92px;" class="layui-upload-img">');
        }
    });*/

    var posterWidth = 120, posterHeight = 120;
    var flag = true;
    //商品正图
    upload.render({
        elem: '#test3',
        url: '/bobogou/file/picture',
        multiple: true,
        accept: 'images',
        exts: 'jpg|png|jpeg|tif',
        auto: false,
        size: 10240,
        choose: function (obj) {
            flag = true;
            //读取本地文件
            obj.preview(function (index, file, result) {
                var img = new Image();
                img.onload = function () {
                    if (posterWidth == img.width && posterHeight == img.height || img.width == img.height) {
                        obj.upload(index, file); //满足条件调用上传方法
                    } else {
                        rc.error('商品正面图片必须为：' + posterWidth + 'px  ' + 'x' + posterHeight + 'px,或者保持1：1比例，请清空确认图片分辨率正确后再次上传');
                        flag = false;
                    }
                };
                if (!flag) {
                    return false;
                }
                img.src = result;
            });
        },
        done: function (res) {
            //上传完毕
            var last_url = $("#frontImages").val();
            var upload_image_url = "";
            if (last_url) {
                upload_image_url = last_url + "," + res.body.url;
            } else {
                upload_image_url = res.body.url;
            }
            if (flag) {
                $("#frontImages").val(upload_image_url);
                $('#demo3').append('<img src="' + res.body.url + '" alt="" style="width: 92px;height: 92px;" class="layui-upload-img">');
            }
        }
    });
    //商品背图
    upload.render({
        elem: '#test14',
        url: '/bobogou/file/picture',
        multiple: true,
        accept: 'images',
        exts: 'jpg|png|jpeg|tif',
        auto: false,
        size: 10240,
        choose: function (obj) {
            flag = true;
            //读取本地文件
            obj.preview(function (index, file, result) {
                var img = new Image();
                img.onload = function () {
                    if (posterWidth == img.width && posterHeight == img.height || img.width == img.height) {
                        obj.upload(index, file); //满足条件调用上传方法
                    } else {
                        rc.error('商品反面图片必须为：' + posterWidth + 'px  ' + 'x' + posterHeight + 'px,或者保持1：1比例,请清空确认图片分辨率正确后再次上传');
                        flag = false;
                    }
                };
                if (!flag) {
                    return false;
                }
                img.src = result;
            });
        },
        done: function (res) {
            //上传完毕
            var last_url = $("#backImages").val();
            var upload_image_url = "";
            if (last_url) {
                upload_image_url = last_url + "," + res.body.url;
            } else {
                upload_image_url = res.body.url;
            }
            if (flag) {
                $("#backImages").val(upload_image_url);
                $('#demo14').append('<img src="' + res.body.url + '" alt="" style="width: 92px;height: 92px;" class="layui-upload-img">');
            }
        }
    });
    //商品底图
    upload.render({
        elem: '#test15',
        url: '/bobogou/file/picture',
        multiple: true,
        accept: 'images',
        exts: 'jpg|png|jpeg|tif',
        auto: false,
        size: 10240,
        choose: function (obj) {
            flag = true;
            //读取本地文件
            obj.preview(function (index, file, result) {
                var img = new Image();
                img.onload = function () {
                    if (posterWidth == img.width && posterHeight == img.height || img.width == img.height) {
                        obj.upload(index, file); //满足条件调用上传方法
                    } else {
                        rc.error('商品背面图片必须为：' + posterWidth + 'px  ' + 'x' + posterHeight + 'px,或者保持1：1比例,请清空确认图片分辨率正确后再次上传');
                        flag = false;
                    }
                };
                if (!flag) {
                    return false;
                }
                img.src = result;
            });
        },
        done: function (res) {
            //上传完毕
            var last_url = $("#bottomImages").val();
            var upload_image_url = "";
            if (last_url) {
                upload_image_url = last_url + "," + res.body.url;
            } else {
                upload_image_url = res.body.url;
            }
            if (flag) {
                $("#bottomImages").val(upload_image_url);
                $('#demo15').append('<img src="' + res.body.url + '" alt="" style="width: 92px;height: 92px;" class="layui-upload-img">');
            }
        }
    });
    //轮播图
    upload.render({
        elem: '#test4',
        url: '/bobogou/file/picture',
        multiple: true,
        accept: 'images',
        exts: 'jpg|png|jpeg|tif',
        done: function (res) {
            //上传完毕
            var last_url = $("#goodsNormSampleBox").val();
            var upload_image_url = "";
            if (last_url) {
                upload_image_url = last_url + "," + res.body.url;
            } else {
                upload_image_url = res.body.url;
            }
            $("#goodsNormSampleBox").val(upload_image_url);
            $('#demo4').append('<img src="' + res.body.url + '" alt="" style="width: 92px;height: 92px;" class="layui-upload-img">');
        }
    });
    //轮播图
    upload.render({
        elem: '#test5',
        url: '/bobogou/file/picture',
        multiple: true,
        accept: 'images',
        exts: 'jpg|png|jpeg|tif',
        done: function (res) {
            //上传完毕
            var last_url = $("#goodsNormOuterPackingBox").val();
            var upload_image_url = "";
            if (last_url) {
                upload_image_url = last_url + "," + res.body.url;
            } else {
                upload_image_url = res.body.url;
            }
            $("#goodsNormOuterPackingBox").val(upload_image_url);
            $('#demo5').append('<img src="' + res.body.url + '" alt="" style="width: 92px;height: 92px;" class="layui-upload-img">');
        }
    });
    //轮播图
    upload.render({
        elem: '#test6',
        url: '/bobogou/file/picture',
        multiple: true,
        accept: 'images',
        exts: 'jpg|png|jpeg|tif',
        done: function (res) {
            //上传完毕
            var last_url = $("#goodsNormInstructionBook").val();
            var upload_image_url = "";
            if (last_url) {
                upload_image_url = last_url + "," + res.body.url;
            } else {
                upload_image_url = res.body.url;
            }
            $("#goodsNormInstructionBook").val(upload_image_url);
            $('#demo6').append('<img src="' + res.body.url + '" alt="" style="width: 92px;height: 92px;" class="layui-upload-img">');
        }
    });
    //轮播图
    upload.render({
        elem: '#test7',
        url: '/bobogou/file/picture',
        multiple: true,
        accept: 'images',
        exts: 'jpg|png|jpeg|tif',
        done: function (res) {
            //上传完毕
            var last_url = $("#goodsNormOtherData").val();
            var upload_image_url = "";
            if (last_url) {
                upload_image_url = last_url + "," + res.body.url;
            } else {
                upload_image_url = res.body.url;
            }
            $("#goodsNormOtherData").val(upload_image_url);
            $('#demo7').append('<img src="' + res.body.url + '" alt="" style="width: 92px;height: 92px;" class="layui-upload-img">');
        }
    });
    //轮播图
    upload.render({
        elem: '#test8',
        url: '/bobogou/file/picture',
        multiple: true,
        accept: 'images',
        exts: 'jpg|png|jpeg|tif',
        done: function (res) {
            //上传完毕
            var last_url = $("#goodsNormRelatedPictures").val();
            var upload_image_url = "";
            if (last_url) {
                upload_image_url = last_url + "," + res.body.url;
            } else {
                upload_image_url = res.body.url;
            }
            $("#goodsNormRelatedPictures").val(upload_image_url);
            $('#demo8').append('<img src="' + res.body.url + '" alt="" style="width: 92px;height: 92px;" class="layui-upload-img">');
        }
    });
    //轮播图
    upload.render({
        elem: '#test9',
        url: '/bobogou/file/picture',
        multiple: true,
        accept: 'images',
        exts: 'jpg|png|jpeg|tif',
        done: function (res) {
            //上传完毕
            var last_url = $("#goodsNormInstructions").val();
            var upload_image_url = "";
            if (last_url) {
                upload_image_url = last_url + "," + res.body.url;
            } else {
                upload_image_url = res.body.url;
            }
            $("#goodsNormInstructions").val(upload_image_url);
            $('#demo9').append('<img src="' + res.body.url + '" alt="" style="width: 92px;height: 92px;" class="layui-upload-img">');
        }
    });
    //轮播图
    upload.render({
        elem: '#test10',
        url: '/bobogou/file/picture',
        multiple: true,
        accept: 'images',
        exts: 'jpg|png|jpeg|tif',
        done: function (res) {
            //上传完毕
            var last_url = $("#goodsNormQualityStandard").val();
            var upload_image_url = "";
            if (last_url) {
                upload_image_url = last_url + "," + res.body.url;
            } else {
                upload_image_url = res.body.url;
            }
            $("#goodsNormQualityStandard").val(upload_image_url);
            $('#demo10').append('<img src="' + res.body.url + '" alt="" style="width: 92px;height: 92px;" class="layui-upload-img">');
        }
    });
    //轮播图
    upload.render({
        elem: '#test11',
        url: '/bobogou/file/picture',
        multiple: true,
        accept: 'images',
        exts: 'jpg|png|jpeg|tif',
        done: function (res) {
            //上传完毕
            var last_url = $("#goodsNormSurveyReport").val();
            var upload_image_url = "";
            if (last_url) {
                upload_image_url = last_url + "," + res.body.url;
            } else {
                upload_image_url = res.body.url;
            }
            $("#goodsNormSurveyReport").val(upload_image_url);
            $('#demo11').append('<img src="' + res.body.url + '" alt="" style="width: 92px;height: 92px;" class="layui-upload-img">');
        }
    });
    //轮播图
    upload.render({
        elem: '#test12',
        url: '/bobogou/file/picture',
        multiple: true,
        accept: 'images',
        exts: 'jpg|png|jpeg|tif',
        done: function (res) {
            //上传完毕
            var last_url = $("#goodsNormProductionBusinessLicense").val();
            var upload_image_url = "";
            if (last_url) {
                upload_image_url = last_url + "," + res.body.url;
            } else {
                upload_image_url = res.body.url;
            }
            $("#goodsNormProductionBusinessLicense").val(upload_image_url);
            $('#demo12').append('<img src="' + res.body.url + '" alt="" style="width: 92px;height: 92px;" class="layui-upload-img">');
        }
    });
    //轮播图
    upload.render({
        elem: '#test13',
        url: '/bobogou/file/picture',
        multiple: true,
        accept: 'images',
        exts: 'jpg|png|jpeg|tif',
        done: function (res) {
            //上传完毕
            var last_url = $("#goodsNormProductionCertificate").val();
            var upload_image_url = "";
            if (last_url) {
                upload_image_url = last_url + "," + res.body.url;
            } else {
                upload_image_url = res.body.url;
            }
            $("#goodsNormProductionCertificate").val(upload_image_url);
            $('#demo13').append('<img src="' + res.body.url + '" alt="" style="width: 92px;height: 92px;" class="layui-upload-img">');
        }
    });

    //商品名改变时做查询，如果是新增的商品需要填写商品规范信息
    $("#name").change(function () {
        let name = $("#name").val();
        $.ajax({
            url: "/bobogou/data/goods/findByFactoryAndName",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: {"name": name},    //参数值
            type: "GET",   //请求方式
            success: function (result) {
                //假设这是iframe页
                if (result.length >= 1) {
                    //不做显示，不让修改
                    $("#goodsNormPrices").hide();
                    $("#sampleBoxShow").hide();
                    $("#outerPackingBoxShow").hide();
                    $("#instructionBookShow").hide();
                    $("#otherDataShow").hide();
                    $("#relatedPicturesShow").hide();
                    $("#instructionsShow").hide();
                    $("#qualityStandardShow").hide();
                    $("#surveyReportShow").hide();
                    $("#productionBusinessLicenseShow").hide();
                    $("#productionCertificateShow").hide();
                } else {
                    //表示商品第一次创建需要显示规范信息，在新建里面填写完善
                    $("#goodsNormPrices").show();
                    $("#sampleBoxShow").show();
                    $("#outerPackingBoxShow").show();
                    $("#instructionBookShow").show();
                    $("#otherDataShow").show();
                    $("#relatedPicturesShow").show();
                    $("#instructionsShow").show();
                    $("#qualityStandardShow").show();
                    $("#surveyReportShow").show();
                    $("#productionBusinessLicenseShow").show();
                    $("#productionCertificateShow").show();
                }
            },
            error: function (result) {
                rc.alert(result.msg)
            }
        });
    })
});

/**
 * 多图清除按钮点击事件---有水印
 */
$("#btn_image_clear_test1").click(function () {
    $('#demo1').html("");
    $("#certificateImageWatermark").val('');
});

/**
 * 多图清除按钮点击事件--无水印
 */
$("#btn_image_clear_test2").click(function () {
    $('#demo2').html("");
    $("#certificateImage").val('');
});

/**
 * 正面多图清除按钮点击事件---轮播图
 */
$("#btn_image_clear_test3").click(function () {
    $('#demo3').html("");
    $("#frontImages").val('');
});

/**
 * 反面多图清除按钮点击事件---轮播图
 */
$("#btn_image_clear_test14").click(function () {
    $('#demo14').html("");
    $("#backImages").val('');
});

/**
 * 底面多图清除按钮点击事件---轮播图
 */
$("#btn_image_clear_test15").click(function () {
    $('#demo15').html("");
    $("#bottomImages").val('');
});

/**
 * 多图清除按钮点击事件---轮播图
 */
$("#btn_image_clear_test4").click(function () {
    $('#demo4').html("");
    $("#goodsNormSampleBox").val('');
});

/**
 * 多图清除按钮点击事件---轮播图
 */
$("#btn_image_clear_test5").click(function () {
    $('#demo5').html("");
    $("#goodsNormOuterPackingBox").val('');
});

/**
 * 多图清除按钮点击事件---轮播图
 */
$("#btn_image_clear_test6").click(function () {
    $('#demo6').html("");
    $("#goodsNormInstructionBook").val('');
});

/**
 * 多图清除按钮点击事件---轮播图
 */
$("#btn_image_clear_test7").click(function () {
    $('#demo7').html("");
    $("#goodsNormOtherData").val('');
});

/**
 * 多图清除按钮点击事件---轮播图
 */
$("#btn_image_clear_test8").click(function () {
    $('#demo8').html("");
    $("#goodsNormRelatedPictures").val('');
});

/**
 * 多图清除按钮点击事件---轮播图
 */
$("#btn_image_clear_test9").click(function () {
    $('#demo9').html("");
    $("#goodsNormInstructions").val('');
});

/**
 * 多图清除按钮点击事件---轮播图
 */
$("#btn_image_clear_test10").click(function () {
    $('#demo10').html("");
    $("#goodsNormQualityStandard").val('');
});

/**
 * 多图清除按钮点击事件---轮播图
 */
$("#btn_image_clear_test11").click(function () {
    $('#demo11').html("");
    $("#goodsNormSurveyReport").val('');
});

/**
 * 多图清除按钮点击事件---轮播图
 */
$("#btn_image_clear_test12").click(function () {
    $('#demo12').html("");
    $("#goodsNormProductionBusinessLicense").val('');
});

/**
 * 多图清除按钮点击事件---轮播图
 */
$("#btn_image_clear_test13").click(function () {
    $('#demo13').html("");
    $("#goodsNormProductionCertificate").val('');
});

//富文本编辑
$(document).ready(function () {
    var markdownWidth = 120, markdownHeight = 120;
    //编辑时
    $(function () {
        var editor = editormd("goodsInfoAdd", {
            width: "100%",
            height: "400px",
            path: "/bobogou/plugins/markdown/lib/",
            watch: false,
            delay: 0,
            placeholder: "请确认商品详情图片插入之前应满足要求(长:宽):375:375",
            imageUpload: true,          // Enable/disable upload
            imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL: "/bobogou/file/goodsInfoMarkdownUpload",             // Upload url
            crossDomainUpload: false,          // Enable/disable Cross-domain upload
            uploadCallbackURL: "",             // Cross-domain upload callback url
        });
    });

    //查看时
    $(function () {
        var testView = editormd.markdownToHTML("goodsInfoOther", {
            // markdown : "[TOC]\n### Hello world!\n## Heading 2", // Also, you can dynamic set Markdown text
            // htmlDecode : true,  // Enable / disable HTML tag encode.
            // htmlDecode : "style,script,iframe",  // Note: If enabled, you should filter some dangerous HTML tags for website security.
        });
    });
})

function save(parentIndex) {
    var isValidate = rc.validateForm('#goodsForm');//校验表单
    if (!isValidate) {
        return false;
    } else {
        $.ajax({
            url: "/bobogou/data/goods/save",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: $("#goodsForm").serialize(),    //参数值
            type: "POST",   //请求方式
            success: function (result) {
                //假设这是iframe页
                if (200 == result.code) {
                    //请求成功时处理
                    // 刷新整个父窗口
                    parent.refresh();
                    //假设这是iframe页
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    rc.msg(result.msg)
                    setTimeout(function () {
                        parent.layer.close(index); //再执行关闭
                    }, 500);
                } else {
                    rc.alert(result.msg)
                }
            },
            error: function (result) {
                rc.alert("请求出错，请联系管理员")
            }
        });
    }
}