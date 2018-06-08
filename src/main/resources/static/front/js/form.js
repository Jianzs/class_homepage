const id = getParam("id");
if (id === -1) {
    window.location.href = "/front/notice.html";
}

var token = localStorage.getItem("token");
if (token === null) {
    window.location.href = "/front/login.html";
}

layui.config({
    base: 'js/'
}).extend({
    constant: 'constant'
});

layui.use(['constant', 'form', 'layer'], function () {
    var constant = layui.constant,
        form = layui.form,
        layer = layui.layer;

    $.ajax({
        type: "GET",
        url: constant.URL_FORM + "/" + id,
        headers: {
            'Authorization': token
        }
    }).done(function (res) {
        if (res.status !== 0) {
            location.reload();
        }
        if (res.data.formContent == null) {
            window.location.href="/front/notice.html";
        }

        $("form").append($(res.data.formContent.content));
        $("title").text(res.data.formContent.title);
        $("input[name=INITTIME]").val(new Date().getTime());
        $("input[type=submit]").attr("lay-submit", "");
        $("input[type=submit]").attr("lay-filter", "form-sub");

        form.on('submit(form-sub)', function (data) {
            $.ajax({
                url: constant.URL_FORM + "/" + id,
                type: "POST",
                data: JSON.stringify(data.field),
                headers: {
                    'Authorization': token,
                    'Content-Type': "application/json"
                }
            }).done(function (res) {
                if (res.status !== 0) {
                    layer.msg("您输入有误，请重新输入");
                } else {
                    layer.msg("提交成功");
                }
            }).fail(function (e) {
                layer.msg("服务器开小差了...");
            })
        });
    })
});

function getParam (name){
    const results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results == null) {
        return -1;
    } else{
        return decodeURI(results[1]) || -1;
    }
}