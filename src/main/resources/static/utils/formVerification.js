function isNumber(id) {
    var reg = new RegExp("^[0-9]*$");
    var obj = document.getElementById(id);
    if (!reg.test(obj.value)) {
        rc.error('请输入数字')
        return false;
    }
}

function notEmpty(id) {
    if ($(id).val() == ''){
        rc.error("该项不能为空")
        return false;
    }else {
        return false;
    }
}