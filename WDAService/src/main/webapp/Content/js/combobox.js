//为下来框添加选项
function appendOption(id, url) {
    $.getJSON(url, function (data) {
        $("#" + id).empty();
        $("#" + id).append("<option value=" + 0 + ">" + "请选择" + "</option>");
        $.each(data, function (i) {
            $("#" + id).append("<option value=" + data[i].id + " >" + data[i].name + "</option>");
        });
    });
}
function appendOptions(id, url) {
    $.getJSON(url, function (data) {
        $("#" + id).empty();
        $.each(data, function (i) {
            $("#" + id).append("<option value=" + data[i].id + " >" + data[i].name + "</option>");
        });
    });
}

function appendOptionName(id, url) {
    $.getJSON(url, function (data) {
        $("#" + id).empty();
        $("#" + id).append("<option value=''>" + "请选择" + "</option>");
        $.each(data, function (i) {
            $("#" + id).append("<option value=" + data[i].name + " >" + data[i].name + "</option>");
        });
    });
}
/**
 * jquery 根据json对象填充form表单
 * @author en
 * @param fromId form表单id
 * @param jsonDate json对象
 */
function loadDatatoForm(fromId, jsonDate) {
    var obj = jsonDate;
    var key, value, tagName, type, arr;
    for (x in obj) {//循环json对象
        key = x;
        value = obj[x];
        //$("[name='"+key+"'],[name='"+key+"[]']").each(function(){
        //更加form表单id 和 json对象中的key查找 表单控件
        $("#" + fromId + " [name='" + key + "'],#" + fromId + " [name='" + key + "[]']").each(function () {
            tagName = $(this)[0].tagName;
            type = $(this).attr('type');
            if (tagName == 'INPUT') {
                if (type == 'radio') {
                    $(this).attr('checked', $(this).val() == value);
                } else if (type == 'checkbox') {
                    try {
                        //数组
                        arr = value.split(',');
                        for (var i = 0; i < arr.length; i++) {
                            if ($(this).val() == arr[i]) {
                                $(this).prop('checked', true);
                                break;
                            }
                        }
                    } catch (e) {
                        //单个
                        $(this).prop('checked', value);
                    }
                } else {
                    $(this).val(value);
                }
            } else if (tagName == 'TEXTAREA') {
                $(this).val(value);
            } else if (tagName == 'SELECT') {
                //console.log($(this).hasClass("select2"));
                if ($(this).hasClass("select2")) {
                    //select2 插件的赋值方法
                    $(this).val(value).trigger("change");
                } else {
                    $(this).val(value);
                }
            }
        });
    }
}

//滚动条在Y轴上的滚动距离
function getScrollTop(){
    var scrollTop = 0, bodyScrollTop = 0, documentScrollTop = 0;
    if(document.body){
        bodyScrollTop = document.body.scrollTop;
    }
    if(document.documentElement){
        documentScrollTop = document.documentElement.scrollTop;
    }
    scrollTop = (bodyScrollTop - documentScrollTop > 0) ? bodyScrollTop : documentScrollTop;
    return scrollTop;
}

//文档的总高度
function getScrollHeight(){
    var scrollHeight = 0, bodyScrollHeight = 0, documentScrollHeight = 0;
    if(document.body){
        bodyScrollHeight = document.body.scrollHeight;
    }
    if(document.documentElement){
        documentScrollHeight = document.documentElement.scrollHeight;
    }
    scrollHeight = (bodyScrollHeight - documentScrollHeight > 0) ? bodyScrollHeight : documentScrollHeight;
    return scrollHeight;
}

//浏览器视口的高度
function getWindowHeight(){
    var windowHeight = 0;
    if(document.compatMode == "CSS1Compat"){
        windowHeight = document.documentElement.clientHeight;
    }else{
        windowHeight = document.body.clientHeight;
    }
    return windowHeight;
}