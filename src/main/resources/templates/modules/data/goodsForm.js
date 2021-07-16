layui.use(['upload', 'element', 'form', 'layedit', 'laydate'], function(){
    var $ = layui.jquery
        , form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        ,upload = layui.upload
        ,element = layui.element;
    //各种基于事件的操作，下面会有进一步介绍

    //对标签的操作----点击时才生效
    form.on('checkbox(tag)', function(data){
        //console.log(data.elem); //得到checkbox原始DOM对象
        let checked = data.elem.checked; //是否被选中，true或者false
        if(checked){
            let id = data.value; //复选框value值，也可以通过data.elem.value得到
            let tagId = $("#tagId").val();
            let get = tagId + "," + id;
            $("#tagId").val(get);
        }else {
            $("#tagId").val("")
        }
        //console.log($("#tagId").val())
        //console.log(data.othis); //得到美化后的DOM对象
    });

    //对其他分类操作----点击时才生效
    form.on('checkbox(goodsTypeId)', function(data){
        let checked = data.elem.checked; //是否被选中，true或者false
        if(checked){
            let id = data.value; //复选框value值，也可以通过data.elem.value得到
            let tagId = $("#goodsTypeId").val();
            let get = tagId + "," + id;
            $("#goodsTypeId").val(get);
        }else {
            $("#goodsTypeId").val("")
        }
    });

    //品牌一级分类联动二级分类
    form.on('select(brandId)', function(data){
        let brandId = data.value;
        $("#secondBrandId").empty();//清空二级选项
        rc.post("/bobogou/data/goodsBrand/dataList",{"parentId":brandId},function(data){
            if(data.length>0) {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="">请选择</option>';
                for(let i=0;i<data.length;i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].brandName + "</option>";
                }
                $("#secondBrandId").html(tmp);
                form.render();
            }else {
                var tmp='<option value="-1">请选择</option>';
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
    form.on('select(levelOne)', function(data){
        //console.log(data.elem); //得到select原始DOM对象
        //console.log(data.value); //得到被选中的值
        //console.log(data.othis); //得到美化后的DOM对象
        let parentId = data.value;
        $("#levelTwo").empty();//清空二级
        $("#levelThree").empty();//清空三级
        rc.post("/bobogou/data/classify/all",{"parentId":parentId},function(data){
            if(data.length>0) {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="0">请选择</option>';
                for(let i=0;i<data.length;i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#levelTwo").html(tmp);
                form.render();
            }else {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="0">请选择</option>';
                $("#levelTwo").html(tmp);
                form.render();
            }
        })
    });
    //下拉框选中后的时间
    form.on('select(levelTwo)', function(data){
        //console.log(data.elem); //得到select原始DOM对象
        //console.log(data.value); //得到被选中的值
        //console.log(data.othis); //得到美化后的DOM对象
        let parentId = data.value;
        $("#levelThree").empty();//清空城市选项
        rc.post("/bobogou/data/classify/all",{"parentId":parentId},function(data){
            if(data.length>0) {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="0">请选择</option>';
                for(let i=0;i<data.length;i++) {
                    tmp += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#levelThree").html(tmp);
                form.render();
            }else {
                //对应的值传回，拼出html下拉框语句
                var tmp='<option value="0">请选择</option>';
                $("#levelThree").html(tmp);
                form.render();
            }
        })
    });

    //多图片上传药品资质证书（带水印）
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
            var last_url = $("#certificateImageWatermark").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#certificateImageWatermark").val(upload_image_url);
        }
    });
    //多图片上传药品资质证书（无水印）
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
            var last_url = $("#certificateImage").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#certificateImage").val(upload_image_url);
        }
    });
    //轮播图
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
            var last_url = $("#images").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#images").val(upload_image_url);
        }
    });

    //商品名改变时做查询，如果是新增的商品需要填写商品规范信息
    $("#name").change(function () {
        let name = $("#name").val();
        $.ajax({
            url: "/bobogou/data/goods/findByFactoryAndName",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: {"name":name},    //参数值
            type: "GET",   //请求方式
            success: function (result) {
                //假设这是iframe页
                if (result.length > 0){
                    //不做显示，不让修改
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
                }else {
                    //表示商品第一次创建需要显示规范信息，在新建里面填写完善
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
 * 多图清除按钮点击事件---轮播图
 */
$("#btn_image_clear_test3").click(function () {
    $('#demo3').html("");
    $("#images").val('');
});

//富文本编辑
$(document).ready(function () {
    //编辑时
    $(function() {
        var editor = editormd("goodsInfoAdd", {
            width  : "100%",
            height : "400px",
            path   : "/bobogou/plugins/markdown/lib/",
            watch  : false,
            delay  : 0,
            placeholder: "请编辑商品详情",
            imageUpload          : true,          // Enable/disable upload
            imageFormats         : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL       : "/bobogou/file/markdownUpload",             // Upload url
            crossDomainUpload    : false,          // Enable/disable Cross-domain upload
            uploadCallbackURL    : "",             // Cross-domain upload callback url
        });
    });

    //查看时
    $(function() {
        var testView = editormd.markdownToHTML("goodsInfoOther", {
            // markdown : "[TOC]\n### Hello world!\n## Heading 2", // Also, you can dynamic set Markdown text
            // htmlDecode : true,  // Enable / disable HTML tag encode.
            // htmlDecode : "style,script,iframe",  // Note: If enabled, you should filter some dangerous HTML tags for website security.
        });
    });
})

function save(parentIndex) {
    var isValidate = rc.validateForm('#goodsForm');//校验表单
    if(!isValidate){
        return false;
    }else {
        $.ajax({
            url: "/bobogou/data/goods/save",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: $("#goodsForm").serialize(),    //参数值
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