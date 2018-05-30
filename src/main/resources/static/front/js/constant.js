layui.define(function(exports) {
    HOST = 'http://class.zhengsj.top';
    var constant = {
        URL_LOGIN: HOST + '/user/login',
        URL_UPDATE_INFO: HOST + "/user",
        URL_GET_USER_INFO: HOST + "/user",
        URL_GET_NOTICE_LIST: HOST + '/notice',
        URL_GET_ROLLCALL_LIST: HOST + '/rollcall/record',

        ROLLCALL_STATUS: {
            0: '正常',
            1: '旷课',
            2: '请假'
        }
    };

    exports('constant', constant);
});