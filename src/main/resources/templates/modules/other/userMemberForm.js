layui.use(['upload', 'element', 'form', 'layedit', 'laydate'], function () {
    var $ = layui.jquery
        , form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        , upload = layui.upload
        , element = layui.element;

    /*//多图片上传
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
            var last_url = $("#businessCertificate").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#businessCertificate").val(upload_image_url);
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
            var last_url = $("#foodBusinessLicense").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#foodBusinessLicense").val(upload_image_url);
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
            var last_url = $("#authorityPurchase").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#authorityPurchase").val(upload_image_url);
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
            var last_url = $("#mandatary").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#mandatary").val(upload_image_url);
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
            var last_url = $("#groupPhoto").val();
            var upload_image_url = "";
            if(last_url){
                upload_image_url = last_url+","+res.body.url;
            }else {
                upload_image_url = res.body.url;
            }
            $("#groupPhoto").val(upload_image_url);
        }
    });

    /!**
     * 多图清除按钮点击事件
     *!/
    $("#btn_image_clear_test1").click(function () {
        $('#demo1').html("");
        $("#businessCertificate").val('');
    });/!**
     * 多图清除按钮点击事件
     *!/
    $("#btn_image_clear_test2").click(function () {
        $('#demo2').html("");
        $("#foodBusinessLicense").val('');
    });/!**
     * 多图清除按钮点击事件
     *!/
    $("#btn_image_clear_test3").click(function () {
        $('#demo3').html("");
        $("#authorityPurchase").val('');
    });/!**
     * 多图清除按钮点击事件
     *!/
    $("#btn_image_clear_test4").click(function () {
        $('#demo4').html("");
        $("#mandatary").val('');
    });/!**
     * 多图清除按钮点击事件
     *!/
    $("#btn_image_clear_test5").click(function () {
        $('#demo5').html("");
        $("#groupPhoto").val('');
    });*/

    form.on('submit(pass)', function (data) {
        let currentUserAreaManager = $("#currentUserAreaManager").val();
        let type = $("#type").val();
        //获取customerUser表单中的id信息
        let id = $("#userId").val();
        let member = 0;
        let applyStatus = 0;
        let userMember = $("#id").val();
        let status = 0;
        //获取意见
        let provincePassReason = $("#provincePassReason").val();
        let cityPassReason = $("#cityPassReason").val();
        let districtPassReason = $("#districtPassReason").val();
        let provinceRefuseReason = $("#provinceRefuseReason").val();
        let cityRefuseReason = $("#cityRefuseReason").val();
        let districtRefuseReason = $("#districtRefuseReason").val();
        if (currentUserAreaManager == 3) {
            //成功
            if (districtPassReason) {
                status = 1;
                applyStatus = 1;
                member = 0;
            }
            if (districtPassReason != null && districtPassReason != '') {
                //修改当前customerUser的会员状态
                rc.post("/bobogou/other/customerUser/doTask", {
                    "id": id,
                    "member": member,
                    "applyStatus": applyStatus,
                    "userMember.id": userMember,
                    "userMember.type": type,
                    "userMember.provinceRefuseReason": provinceRefuseReason,
                    "userMember.provincePassReason": provincePassReason,
                    "userMember.cityRefuseReason": cityRefuseReason,
                    "userMember.cityPassReason": cityPassReason,
                    "userMember.districtRefuseReason": districtRefuseReason,
                    "userMember.districtPassReason": districtPassReason,
                    "userMember.status": status
                }, function (data) {
                    if (200 == data.code) {
                        //关闭当前页面
                        var index = parent.layer.getFrameIndex(window.name);
                        //刷新父页面
                        rc.msg(data.msg);
                        setTimeout(function(){
                            parent.layer.close(index); //再执行关闭
                            parent.location.reload();
                        }, 1000);
                    } else {
                        rc.msg('因为后台原因，审核VIP失败')
                    }
                })
            } else {
                rc.error("请填写区级通过原因")
            }
        } else if (currentUserAreaManager == 2) {
            //成功
            if (cityPassReason) {
                status = 3;
                applyStatus = 1;
                member = 0;
            }
            if (cityPassReason != null && cityPassReason != '') {
                //修改当前customerUser的会员状态
                rc.post("/bobogou/other/customerUser/doTask", {
                    "id": id,
                    "member": member,
                    "applyStatus": applyStatus,
                    "userMember.id": userMember,
                    "userMember.type": type,
                    "userMember.provinceRefuseReason": provinceRefuseReason,
                    "userMember.provincePassReason": provincePassReason,
                    "userMember.cityRefuseReason": cityRefuseReason,
                    "userMember.cityPassReason": cityPassReason,
                    "userMember.districtRefuseReason": districtRefuseReason,
                    "userMember.districtPassReason": districtPassReason,
                    "userMember.status": status
                }, function (data) {
                    if (200 == data.code) {
                        //关闭当前页面
                        var index = parent.layer.getFrameIndex(window.name);
                        rc.msg(data.msg);
                        setTimeout(function(){
                            parent.layer.close(index); //再执行关闭
                            parent.location.reload();
                        }, 1000);
                    } else {
                        rc.msg('因为后台原因，审核VIP失败')
                    }
                })
            } else {
                rc.error("请填写市级通过原因")
            }
        } else if (currentUserAreaManager == 1) {
            //成功
            if (provincePassReason) {
                status = 5;
                applyStatus = 1;
                member = 0;
            }
            if (provincePassReason != null && provincePassReason != '') {
                //修改当前customerUser的会员状态
                rc.post("/bobogou/other/customerUser/doTask", {
                    "id": id,
                    "member": member,
                    "applyStatus": applyStatus,
                    "userMember.id": userMember,
                    "userMember.type": type,
                    "userMember.provinceRefuseReason": provinceRefuseReason,
                    "userMember.provincePassReason": provincePassReason,
                    "userMember.cityRefuseReason": cityRefuseReason,
                    "userMember.cityPassReason": cityPassReason,
                    "userMember.districtRefuseReason": districtRefuseReason,
                    "userMember.districtPassReason": districtPassReason,
                    "userMember.status": status
                }, function (data) {
                    if (200 == data.code) {
                        //关闭当前页面
                        var index = parent.layer.getFrameIndex(window.name);
                        //刷新父页面
                        rc.msg(data.msg);
                        setTimeout(function(){
                            parent.layer.close(index); //再执行关闭
                            parent.location.reload();
                        }, 1000);
                    } else {
                        rc.msg('因为后台原因，审核VIP失败')
                    }
                })
            } else {
                rc.error("请填写省级通过原因")
            }
        } else if (currentUserAreaManager == 4) {
            //成功
            status = 7;
            applyStatus = 2;
            member = 1;
            //修改当前customerUser的会员状态
            rc.post("/bobogou/other/customerUser/doTask", {
                "id": id,
                "member": member,
                "applyStatus": applyStatus,
                "userMember.id": userMember,
                "userMember.type": type,
                "userMember.provinceRefuseReason": provinceRefuseReason,
                "userMember.provincePassReason": provincePassReason,
                "userMember.cityRefuseReason": cityRefuseReason,
                "userMember.cityPassReason": cityPassReason,
                "userMember.districtRefuseReason": districtRefuseReason,
                "userMember.districtPassReason": districtPassReason,
                "userMember.status": status
            }, function (data) {
                if (200 == data.code) {
                    //关闭当前页面
                    var index = parent.layer.getFrameIndex(window.name);
                    //刷新父页面
                    rc.msg(data.msg);
                    setTimeout(function(){
                        parent.layer.close(index); //再执行关闭
                        parent.location.reload();
                    }, 1000);
                } else {
                    rc.msg('因为后台原因，审核VIP失败')
                }
            })
        } else if (currentUserAreaManager == 5) {
            //成功
            status = 9;
            applyStatus = 2;
            member = 1;
            //修改当前customerUser的会员状态
            rc.post("/bobogou/other/customerUser/doTask", {
                "id": id,
                "member": member,
                "applyStatus": applyStatus,
                "userMember.id": userMember,
                "userMember.type": type,
                "userMember.provinceRefuseReason": provinceRefuseReason,
                "userMember.provincePassReason": provincePassReason,
                "userMember.cityRefuseReason": cityRefuseReason,
                "userMember.cityPassReason": cityPassReason,
                "userMember.districtRefuseReason": districtRefuseReason,
                "userMember.districtPassReason": districtPassReason,
                "userMember.status": status
            }, function (data) {
                if (200 == data.code) {
                    //关闭当前页面
                    var index = parent.layer.getFrameIndex(window.name);
                    //刷新父页面
                    rc.msg(data.msg);
                    setTimeout(function(){
                        parent.layer.close(index); //再执行关闭
                        parent.location.reload();
                    }, 1000);
                } else {
                    rc.msg('因为后台原因，审核VIP失败')
                }
            })
        }
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
    form.on('submit(refuse)', function (data) {
        let currentUserAreaManager = $("#currentUserAreaManager").val();
        let type = $("#type").val();
        //获取customerUser表单中的id信息
        let id = $("#userId").val();
        let member = 0;
        let applyStatus = 0;
        let userMember = $("#id").val();
        let status = 0;
        //获取意见
        let provincePassReason = $("#provincePassReason").val();
        let cityPassReason = $("#cityPassReason").val();
        let districtPassReason = $("#districtPassReason").val();
        let provinceRefuseReason = $("#provinceRefuseReason").val();
        let cityRefuseReason = $("#cityRefuseReason").val();
        let districtRefuseReason = $("#districtRefuseReason").val();
        if (currentUserAreaManager == 3) {
            //成功
            if (districtRefuseReason) {
                status = 2;
                applyStatus = 3;
                member = 0;
            }
            if (districtPassReason != null && districtPassReason != '') {
                //修改当前customerUser的会员状态
                rc.post("/bobogou/other/customerUser/doTask", {
                    "id": id,
                    "member": member,
                    "applyStatus": applyStatus,
                    "userMember.id": userMember,
                    "userMember.type": type,
                    "userMember.provinceRefuseReason": provinceRefuseReason,
                    "userMember.provincePassReason": provincePassReason,
                    "userMember.cityRefuseReason": cityRefuseReason,
                    "userMember.cityPassReason": cityPassReason,
                    "userMember.districtRefuseReason": districtRefuseReason,
                    "userMember.districtPassReason": districtPassReason,
                    "userMember.status": status
                }, function (data) {
                    if (200 == data.code) {
                        //关闭当前页面
                        var index = parent.layer.getFrameIndex(window.name);
                        rc.msg(data.msg);
                        setTimeout(function(){
                            parent.layer.close(index); //再执行关闭
                            //刷新父页面
                            parent.location.reload();
                        }, 1000);
                    } else {
                        rc.msg('因为后台原因，审核VIP失败')
                    }
                })
            } else {
                rc.error("请填写区级拒绝原因")
            }
        } else if (currentUserAreaManager == 2) {
            //成功
            if (cityRefuseReason) {
                status = 4;
                applyStatus = 3;
                member = 0;
            }
            if (cityPassReason != null && cityPassReason != '') {
                //修改当前customerUser的会员状态
                rc.post("/bobogou/other/customerUser/doTask", {
                    "id": id,
                    "member": member,
                    "applyStatus": applyStatus,
                    "userMember.id": userMember,
                    "userMember.type": type,
                    "userMember.provinceRefuseReason": provinceRefuseReason,
                    "userMember.provincePassReason": provincePassReason,
                    "userMember.cityRefuseReason": cityRefuseReason,
                    "userMember.cityPassReason": cityPassReason,
                    "userMember.districtRefuseReason": districtRefuseReason,
                    "userMember.districtPassReason": districtPassReason,
                    "userMember.status": status
                }, function (data) {
                    if (200 == data.code) {
                        //关闭当前页面
                        var index = parent.layer.getFrameIndex(window.name);
                        rc.msg(data.msg);
                        setTimeout(function(){
                            parent.layer.close(index); //再执行关闭
                            //刷新父页面
                            parent.location.reload();
                        }, 1000);
                    } else {
                        rc.msg('因为后台原因，审核VIP失败')
                    }
                })
            } else {
                rc.error("请填写市级级拒绝原因")
            }
        } else if (currentUserAreaManager == 1) {
            //成功
            if (provinceRefuseReason) {
                status = 6;
                applyStatus = 3;
                member = 0;
            }
            if (provincePassReason != null && provincePassReason != '') {
                //修改当前customerUser的会员状态
                rc.post("/bobogou/other/customerUser/doTask", {
                    "id": id,
                    "member": member,
                    "applyStatus": applyStatus,
                    "userMember.id": userMember,
                    "userMember.type": type,
                    "userMember.provinceRefuseReason": provinceRefuseReason,
                    "userMember.provincePassReason": provincePassReason,
                    "userMember.cityRefuseReason": cityRefuseReason,
                    "userMember.cityPassReason": cityPassReason,
                    "userMember.districtRefuseReason": districtRefuseReason,
                    "userMember.districtPassReason": districtPassReason,
                    "userMember.status": status
                }, function (data) {
                    if (200 == data.code) {
                        //关闭当前页面
                        var index = parent.layer.getFrameIndex(window.name);
                        rc.msg(data.msg);
                        setTimeout(function(){
                            parent.layer.close(index); //再执行关闭
                            //刷新父页面
                            parent.location.reload();
                        }, 1000);
                    } else {
                        rc.msg('因为后台原因，审核VIP失败')
                    }
                })
            } else {
                rc.error("请填写省级拒绝原因")
            }
        } else if (currentUserAreaManager == 4) {
            //成功
            status = 8;
            applyStatus = 3;
            member = 0;
            //修改当前customerUser的会员状态
            rc.post("/bobogou/other/customerUser/doTask", {
                "id": id,
                "member": member,
                "applyStatus": applyStatus,
                "userMember.id": userMember,
                "userMember.type": type,
                "userMember.provinceRefuseReason": provinceRefuseReason,
                "userMember.provincePassReason": provincePassReason,
                "userMember.cityRefuseReason": cityRefuseReason,
                "userMember.cityPassReason": cityPassReason,
                "userMember.districtRefuseReason": districtRefuseReason,
                "userMember.districtPassReason": districtPassReason,
                "userMember.status": status
            }, function (data) {
                if (200 == data.code) {
                    //关闭当前页面
                    var index = parent.layer.getFrameIndex(window.name);
                    rc.msg(data.msg);
                    setTimeout(function(){
                        parent.layer.close(index); //再执行关闭
                        //刷新父页面
                        parent.location.reload();
                    }, 1000);
                } else {
                    rc.msg('因为后台原因，审核VIP失败')
                }
            })
        } else if (currentUserAreaManager == 5) {
            //成功
            status = 10;
            applyStatus = 3;
            member = 0;
            //修改当前customerUser的会员状态
            rc.post("/bobogou/other/customerUser/doTask", {
                "id": id,
                "member": member,
                "applyStatus": applyStatus,
                "userMember.id": userMember,
                "userMember.type": type,
                "userMember.provinceRefuseReason": provinceRefuseReason,
                "userMember.provincePassReason": provincePassReason,
                "userMember.cityRefuseReason": cityRefuseReason,
                "userMember.cityPassReason": cityPassReason,
                "userMember.districtRefuseReason": districtRefuseReason,
                "userMember.districtPassReason": districtPassReason,
                "userMember.status": status
            }, function (data) {
                if (200 == data.code) {
                    //关闭当前页面
                    var index = parent.layer.getFrameIndex(window.name);
                    rc.msg(data.msg);
                    setTimeout(function(){
                        parent.layer.close(index); //再执行关闭
                        //刷新父页面
                        parent.location.reload();
                    }, 1000);
                } else {
                    rc.msg('因为后台原因，审核VIP失败')
                }
            })
        }
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
})