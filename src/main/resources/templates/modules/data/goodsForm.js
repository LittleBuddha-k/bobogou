layui.use('form', function(){
    var form = layui.form;
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

    //商品分类级联下拉框
    //下拉框选中后的时间
    form.on('select(levelOne)', function(data){
        //console.log(data.elem); //得到select原始DOM对象
        //console.log(data.value); //得到被选中的值
        //console.log(data.othis); //得到美化后的DOM对象
        let parentId = data.value;
        $("#levelTwo").empty();//清空二级
        $("#levelThree").empty();//清空三级
        rc.post("/bobogou/data/goodsType/all",{"parentId":parentId},function(data){
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
        rc.post("/bobogou/data/goodsType/all",{"parentId":parentId},function(data){
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
                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                parent.layer.close(index); //再执行关闭
                parent.refresh();
                rc.alert(result.msg)
            },
            error: function (result) {
                rc.alert(result.msg)
            }
        });
    }
}