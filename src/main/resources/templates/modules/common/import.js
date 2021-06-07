function importExcel(url, fn) {
    let fileForm = $("#fileForm")[0];
    rc.alert('  正在导入，请稍等...');
    rc.uploadFile(fileForm, url,function (data) {
        fn(data);
    })
}
