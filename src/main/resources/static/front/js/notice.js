var token = localStorage.getItem("token");
if (token === null) {
    window.location.href = "/front/login.html";
}

hideAll();

layui.config({
    base: 'js/'
}).extend({
    constant: 'constant'
});

layui.use(['element', 'flow', 'table', 'form', 'layer', 'constant'], function(){
    var element = layui.element,
        flow = layui.flow,
        table = layui.table,
        form = layui.form,
        layer = layui.layer,
        constant = layui.constant;
    const functionFactory = {
        'class': clickNotice,
        'shudian': clickNotice,
        'c++': clickNotice,
        'rollcall': clickRollcall,
        'logout': clickLogout,
        'change-info': clickChangeInfo,
    };
    const TYPE = {
        'class': 0,
        'shudian': 1,
        'c++': 2,
    };

    // 初始化为班级公告
    functionFactory['class'](TYPE.class);
    // 侧边栏点击事件
    element.on('nav(tool-type)', function(elem){
        var index = $(elem).attr('id');
        if (index === undefined) return;
        functionFactory[index](TYPE[index]);
    });

    form.on('submit(change-info)', function (data) {
        data = data.field;
        $.ajax({
            url: constant.URL_UPDATE_INFO,
            type: 'PUT',
            data: JSON.stringify(data),
            headers: {
                'Authorization': token,
                'Content-Type': 'application/json'
            }
        }).done(function (res) {
            if (res.status === 0) {
                layer.msg("修改成功.");
            } else {
                layer.msg("出错了...");
            }
        }).fail(function() {
            layer.msg("服务器开小差了...");
        });
        return false;
    });

    // 点击公告
    function clickNotice(type) {
        hideAll();
        $('#notice-content').show();

        $('#timeline').empty(); // 删除所有子元素
        loadFlow(type);
    }

    // 点击点名情况
    function clickRollcall() {
        hideAll();
        $('#rollcall-board').show();
        getRollcallRecord()
            .done(function(res) {
                var records = res.data.records;
                for (var i = 0, len = records.length; i < len; i++) {
                    records[i].id = i+1;
                    records[i].createTime = dateFormat(records[i].createTime);
                    records[i].status = constant.ROLLCALL_STATUS[records[i].status];
                }

                table.render({
                    elem: '#rollcall-table'
                    ,data: res.data.records
                    ,page: true //开启分页
                    ,limit: 8
                    ,limits: [8, 12, 30]
                    ,cols: [[ //表头
                        {field: 'id', title: 'ID', sort: true, width: 50, fixed: 'left'}
                        ,{field: 'status', title: '状态', sort: true,  fixed: 'left'}
                        ,{field: 'createTime', title: '时间',  sort: true}
                    ]]
                });
            });
    }

    function clickChangeInfo() {
        hideAll();
        $("#personal-info").show();
        $.ajax({
            url: constant.URL_GET_USER_INFO,
            type: "GET",
            headers: {
                'Authorization': token
            }
        }).done(function (res) {
            if (res.status !== 0) return;
            var data = res.data.info;
            form.val("infoForm", {
                "name": data.name,
                "number": data.number,
                "gender": data.gender == null ? "0" : data.gender.toString(),
                "politicalStatus": data.politicalStatus == null ? "" : data.politicalStatus.toString(),
                "home": data.home,
                "phone": data.phone,
                "fatherPhone": data.fatherPhone,
                "matherPhone": data.matherPhone
            });
        })
    }

    function clickLogout() {
        localStorage.clear();
        window.location.href="/front/login.html";
    }

    // 下滑分页
    function loadFlow(type) {
        flow.load({
            elem: '#timeline' //指定列表容器
            , isAuto: true
            , md: 500,
            scrollElem: '#timeline'
            , done: function (page, next) { //到达临界点（默认滚动触发），触发下一页
                var lis = [];

                //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
                getNoticeContent(type, page)
                    .done(function (res) {
                        //假设你的列表返回在data集合中
                        layui.each(res.data.notices, function (index, item) {
                            lis.push(buildNoticeItem(item));
                        });

                        //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                        //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                        next(lis.join(''), page < res.data.count);
                    });
            }
        });
    }

    // 获取各类通知内容
    function getNoticeContent(type, page, limit = 8) {
        return $.ajax({
            url: constant.URL_GET_NOTICE_LIST,
            type: 'GET',
            data: {
                type: type,
                page: page,
                limit: limit
            },
            headers: {
                "Authorization": token
            }
        });
    }

    // 获得点名记录
    function getRollcallRecord() {
        return $.ajax({
            url: constant.URL_GET_ROLLCALL_LIST,
            type: "GET",
            headers: {
                "Authorization": token
            }
        });
    }

    function buildNoticeItem(item) {
        var content = markdown.toHTML(item.content);

        var endTime = dateFormat(item.endTime);

        return `<li class="layui-timeline-item">
                    <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                    <div class="layui-timeline-content layui-text">
                        <h3 class="layui-timeline-title">${endTime}</h3>
                        ${content}
                    </div>
                </li>`;
    }
});

function hideAll() {
    $("#notice-content").hide();
    $("#rollcall-board").hide();
    $("#personal-info").hide();
}

function dateFormat(datetime) {
    datetime = new Date(datetime);
    var date = datetime.toLocaleDateString('zh').replace(/\//g, '-');
    var times = datetime.toLocaleTimeString('zh', {hour12: false}).split(":");
    var time = times[0] + ":" + times[2];
    return date + " " + time;
}

if (window.screen.width < 768) {
    $('#slide-bar').hide();
    $(document).click(function(elem) {
        var target = $(elem.target);
        if (target.attr("class") === 'layui-icon' ||
            target.attr("class") === 'unclick-mark') return;

        if ($('#slide-bar').css('display') !== 'none') {
            $('#slide-bar').hide(300);
        }
    });

    layui.use(['layer', 'util'], function() {
        var layer = layui.layer,
            util = layui.util;
        util.fixbar({
            bar1: '&#xe602;'
            ,click: function(type){
                var bar = $('#slide-bar');
                if (bar.css('display') === 'none') {
                    bar.show(300);
                } else {
                    bar.hide(300);
                }
            },
            css: {
                right: 10,
                bottom: 10
            }
        });
    });
}