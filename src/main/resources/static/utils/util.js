(function ($) {
    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeArray();
        var str = this.serialize();
        $(array).each(function () {
            if (serializeObj[this.name]) {
                if ($.isArray(serializeObj[this.name]) && this.value != "") {
                    serializeObj[this.name].push(this.value);
                } else {
                    if (this.value != "") {
                        serializeObj[this.name] = [serializeObj[this.name], this.value];
                    }
                }
            } else {
                if (this.value != "") {
                    serializeObj[this.name] = this.value;
                }
            }
        });
        return serializeObj;
    };

    //转换时间格式
    rc = {
        dateFormat: function resolvingDate(date) {
            var d = new Date(date);

            var month = (d.getMonth() + 1) < 10 ? '0' + (d.getMonth() + 1) : (d.getMonth() + 1);
            var day = d.getDate() < 10 ? '0' + d.getDate() : d.getDate();
            var hours = d.getHours() < 10 ? '0' + d.getHours() : d.getHours();
            var min = d.getMinutes() < 10 ? '0' + d.getMinutes() : d.getMinutes();
            var sec = d.getSeconds() < 10 ? '0' + d.getSeconds() : d.getSeconds();

            var times = d.getFullYear() + '-' + month + '-' + day + ' ' + hours + ':' + min + ':' + sec;

            return times
        },

        post:function(url,data,callback){
            $.ajax({
                url:url,
                method:"post",
                data:data,
                error:function(xhr,textStatus){
                    if(xhr.status == 0){
                        rc.msg("连接失败，请检查网络!")
                    }else if(xhr.status == 404){
                        var errDetail ="<font color='red'>404,请求地址不存在！</font>";
                        rc.alert("请求出错")
                    }else if(xhr.status && xhr.responseText){
                        var errDetail ="<font color='red'>"+ xhr.responseText.replace(/[\r\n]/g,"<br>").replace(/[\r]/g,"<br>").replace(/[\n]/g,"<br>")+"</font>";
                        rc.alert(xhr.status+"错误")
                    }else{
                        var errDetail ="<font color='red'>未知错误!</font>";
                        rc.alert("真悲剧，后台抛出异常了")
                    }
                },
                success:function(data,textStatus,jqXHR){
                    if(data.indexOf == "_login_page_"){//登录超时
                        location.reload(true);
                    }else{
                        callback(data);
                    }
                }
            });
        },

        get:function(url,callback){
            $.ajax({
                url:url,
                method:"get",
                error:function(xhr,textStatus){
                    if(xhr.status == 0){
                        rc.msg("连接失败，请检查网络!")
                    }else if(xhr.status == 404){
                        var errDetail ="<font color='red'>404,请求地址不存在！</font>";
                        rc.alert("请求出错")
                    }else if(xhr.status && xhr.responseText){
                        var errDetail ="<font color='red'>"+ xhr.responseText.replace(/[\r\n]/g,"<br>").replace(/[\r]/g,"<br>").replace(/[\n]/g,"<br>")+"</font>";
                        rc.alert(xhr.status+"错误")
                    }else{
                        var errDetail ="<font color='red'>未知错误!</font>";
                        rc.alert("真悲剧，后台抛出异常了")
                    }

                },
                success:function(data,textStatus,jqXHR){
                    if(data.indexOf == "_login_page_"){//返回首页内容代表登录超时
                        rc.alert("登录超时！")
                        location.reload(true);
                    }else{
                        callback(data);
                    }

                }
            });
        },
        treeTablePost: function post(url, data) {
            $.ajax({
                url: url,    //请求的url地址
                dataType: "json",   //返回格式为json
                async: true,//请求是否异步，默认为异步，这也是ajax重要特性
                data: data,    //参数值
                type: "POST",   //请求方式
                success: function (result) {
                    //请求成功时处理
                    rc.msg(result.msg)
                    //重新刷新页面
                }
            });
        },
        alert: function alert(msg) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg(msg);
            });
        },
        confirm: function confirm(msg) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.confirm(msg, function (index) {
                    layer.close(index);
                });
            });
        },
        msg: function msg(msg) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg(msg, {
                    icon: 1,
                    time: 3000 //2秒关闭（如果不配置，默认是3秒）
                }, function () {
                    //do something
                });
            });
        },
        openSaveDialog: function open(url, title) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.open({
                    type: 2,
                    title: title,
                    content: url,
                    skin: 'demo-class',
                    area: ['75%', '70%'],
                    offset: 'auto',
                    btn: ['确定', '关闭'],
                    yes: function (index, layero) {
                        //点击确定后，将执行子页面的save（）方法，需要在子页面定义save（）
                        var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
                        iframeWin.save(index);
                    }
                    , btn2: function (index, layero) {
                        //按钮【按钮二】的回调
                        layer.close(index);
                    },
                    //按钮1、2、3的位置
                    btnAlign: 'c',
                    //关闭按钮的风格
                    closeBtn: 2,
                    shade: [0.8, '#393D49'],
                    //设置延时关闭时间
                    //time: 5000,
                    shift: 4,
                    //配置最大化最小化按钮
                    maxmin: true
                });
            });
        },
        openViewDialog: function open(url, title) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.open({
                    type: 2,
                    title: title,
                    content: url,
                    skin: 'demo-class',
                    area: ['75%', '70%'],
                    offset: 'auto',
                    btn: ['关闭'],
                    cancel: function (index) {
                    },
                    //按钮1、2、3的位置
                    btnAlign: 'c',
                    //关闭按钮的风格
                    closeBtn: 2,
                    shade: [0.8, '#393D49'],
                    //设置延时关闭时间
                    //time: 5000,
                    shift: 4,
                    //配置最大化最小化按钮
                    maxmin: true
                });
            });
        },
        openSelectionDialog: function open(url, title) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.open({
                    type: 2,
                    title: title,
                    content: url,
                    skin: 'demo-class',
                    area: ['75%', '70%'],
                    offset: 'auto',
                    btn: ['确定', '关闭'],
                    yes: function (index, layero) {
                        //点击确定后，将执行子页面的save（）方法，需要在子页面定义save（）
                        var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
                        let ids = iframeWin.getIdSelections();
                        iframeWin.save(ids,index);
                        layer.close(index);
                    }
                    , btn2: function (index, layero) {
                        //按钮【按钮二】的回调
                        layer.close(index);
                    },
                    //按钮1、2、3的位置
                    btnAlign: 'c',
                    //关闭按钮的风格
                    closeBtn: 2,
                    shade: [0.8, '#393D49'],
                    //设置延时关闭时间
                    //time: 5000,
                    shift: 4,
                    //配置最大化最小化按钮
                    maxmin: true
                });
            });
        },
        openTreeSaveDialog: function open(url, title) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.open({
                    type: 2,
                    title: title,
                    content: url,
                    skin: 'demo-class',
                    area: ['75%', '70%'],
                    offset: 'auto',
                    btn: ['确定', '关闭'],
                    yes: function (index, layero) {
                        //点击确定后，将执行子页面的save（）方法，需要在子页面定义save（）
                        var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
                        iframeWin.save();//调用子页面的save（）方法
                        layer.close(index);
                    }
                    , btn2: function (index, layero) {
                        //按钮【按钮二】的回调
                        layer.close(index);
                    },
                    //按钮1、2、3的位置
                    btnAlign: 'c',
                    //关闭按钮的风格
                    closeBtn: 2,
                    shade: [0.8, '#393D49'],
                    //设置延时关闭时间
                    //time: 5000,
                    shift: 4,
                    //配置最大化最小化按钮
                    maxmin: true
                });
            });
        },
        openImportDialog: function open(templateUrl, uploadUrl, title) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.open({
                    type: 2,
                    title: title,
                    content: "/recruit/portal/importTemplate",//下载页面
                    skin: 'demo-class',
                    area: ['75%', '70%'],
                    offset: 'auto',
                    btn: ['下载模板', '确定', '关闭'],
                    yes: function (index, layero) {
                        //按钮【按钮一】的回调
                        rc.downloadFile(templateUrl);
                    }
                    , btn2: function (index, layero) {
                        //按钮【按钮二】的回调
                        //return false 开启该代码可禁止点击该按钮关闭
                        var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
                        //调用子页面的方法
                        iframeWin.importExcel(uploadUrl, function (result) {
                                if (result.success) {
                                    rc.alert(result.msg);
                                    refresh();
                                } else {
                                    rc.alert(result.msg);
                                }
                                rc.close(index);
                            }
                        )
                    }
                    , btn3: function (index, layero) {
                        //按钮【按钮三】的回调
                        //return false 开启该代码可禁止点击该按钮关闭
                    }
                    , cancel: function () {
                        //右上角关闭回调
                        //return false 开启该代码可禁止点击该按钮关闭
                    },
                    //按钮1、2、3的位置
                    btnAlign: 'c',
                    //关闭按钮的风格
                    closeBtn: 2,
                    shade: [0.8, '#393D49'],
                    //设置延时关闭时间
                    //time: 5000,
                    shift: 4,
                    //配置最大化最小化按钮
                    maxmin: true
                });
            });
        },
        //ajax上传文件
        uploadFile: function (fileObj, url, callback) {
            var data = new FormData(fileObj);
            // data.append("CustomField", "This is some extra data, testing");//如果要添加参数
            $.ajax({
                type: "POST",
                enctype: 'multipart/form-data',
                url: url,
                data: data,
                processData: false, //prevent jQuery from automatically transforming the data into a query string
                contentType: false,
                cache: false,
                timeout: 600000,
                success: function (result) {
                    callback(result);
                },
                error: function (xhr, textStatus) {
                    if (xhr.status == 0) {
                        rc.info("连接失败，请检查网络!")
                    } else if (xhr.status == 404) {
                        var errDetail = "<font color='red'>404,请求地址不存在！</font>";
                        top.layer.alert(errDetail, {
                            icon: 2,
                            area: ['auto', 'auto'],
                            title: "请求出错"
                        })
                    } else if (xhr.status && xhr.responseText) {
                        var errDetail = "<font color='red'>" + xhr.responseText.replace(/[\r\n]/g, "<br>").replace(/[\r]/g, "<br>").replace(/[\n]/g, "<br>") + "</font>";
                        top.layer.alert(errDetail, {
                            icon: 2,
                            area: ['80%', '70%'],
                            title: xhr.status + "错误"
                        })
                    } else {
                        var errDetail = xhr.responseText == "<font color='red'>未知错误!</font>";
                        top.layer.alert(errDetail, {
                            icon: 2,
                            area: ['auto', 'auto'],
                            title: "真悲剧，后台抛出异常了"
                        })
                    }

                }
            })
        },
        downloadFile: function (url, name) {
            var $a = $("<a></a>").attr("href", url).attr("download", name);
            $a[0].click();
        },

        close:function(index){
            if(index){
                top.layer.close(index);
            }else{
                top.layer.closeAll();
            }

        },

        /**
         * 返回当前活跃的tab页面关联的iframe的Windows对象，方便layer弹窗调用父页面的方法。
         */
        getParent: function () {
            return rc.getActiveTab()[0].contentWindow;
        },

        getActiveTab:function(){
            return $(".J_iframe:visible");
        }
    }
})(jQuery);