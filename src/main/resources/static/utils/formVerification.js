function isNumber(id) {
    var reg = new RegExp("^[0-9]*$");
    var obj = document.getElementById(id);
    if (!reg.test(obj.value)) {
        rc.error("请输入数字");
        return false;
    }
}