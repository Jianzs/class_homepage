layui.config({
    base: 'js/'
}).extend({
    constant: 'constant'
});

layui.use(['layer', 'form', 'constant'], function(){
    var layer = layui.layer
        ,form = layui.form
        ,constant = layui.constant;

    form.on('submit(login)', function (data) {
        $.ajax({
            url: constant.URL_LOGIN,
            type: 'POST',
            data: JSON.stringify(data.field),
            headers: {
                'Content-Type': 'application/json'
            }
        }).done(function (res) {
            if (res.status === 1) {
                layer.msg('用户名或密码错误');
                return ;
            }
            var token = res.data.token;
            localStorage.setItem('token', token);
            window.location.href="/front/notice.html";
        }).fail(function () {
            layer.msg('服务器开小差了...');
        });
        return false;
    });
});