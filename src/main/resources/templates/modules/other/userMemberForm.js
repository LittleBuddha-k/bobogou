layui.use(['upload', 'element', 'form', 'layedit', 'laydate'], function () {
    var $ = layui.jquery
        , form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        , upload = layui.upload
        , element = layui.element;

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
            status = 1;
            applyStatus = 1;
            member = 0;
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
                        setTimeout(function () {
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
            status = 3;
            applyStatus = 1;
            member = 0;
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
                        setTimeout(function () {
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
            status = 5;
            applyStatus = 1;
            member = 0;
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
                        setTimeout(function () {
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
                    setTimeout(function () {
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
                    setTimeout(function () {
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
            status = 2;
            applyStatus = 3;
            member = 0;
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
                        setTimeout(function () {
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
            status = 4;
            applyStatus = 3;
            member = 0;
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
                        setTimeout(function () {
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
            status = 6;
            applyStatus = 3;
            member = 0;
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
                        setTimeout(function () {
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
                    setTimeout(function () {
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
                    setTimeout(function () {
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