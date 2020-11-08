<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>诊断小助手后台登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="${ctx}/Content/image/logo.png">
	<link rel="stylesheet" href="${ctx}/Content/layui/css/layui.css" type="text/css"></link>
    <link rel="stylesheet" href="${ctx}/Content/css/login.css">
    <link rel="icon" href="${ctx}/Content/favicon.ico">
	<style type="text/css">
		#formLogin{
			padding-top: 10px;
		}
		.title-name{
			font-size: 18px;
			color: #303030;
		}
		.group-one{
			text-align: center;
		}
		
	</style>
  </head>
  
  <body class="login-wrap" onkeydown="Loginbtn()">
    <div class="login-container">
        <form class="login-form" id="formLogin" action="${ctx}/user/login.do" method="post">
        	<div class="input-group" style="text-align: center;padding-bottom: 0px;">
                <img id="img-box" src="${ctx}/Content/image/logo.png" style="width: 60px;height: 60px;" alt=""/>
            </div>
        	<div class="input-group group-one" style="padding: 2px 10px;">
                <span class="label-title title-name">诊断小助手</span>
            </div>
            <div class="input-group">
                <input type="text" id="userAccount" name="adminAccount" autocomplete="off" class="input-field">
                <label for="username" class="input-label">
                    <span class="label-title">账号</span>
                </label>
            </div>
            <div class="input-group">
                <input type="password" id="userPassword" name="adminPassword"  autocomplete="off" class="input-field">
                <label for="userPassword" class="input-label">
                    <span class="label-title">密码</span>
                </label>
            </div>
            <button type="button" class="login-button" id="btnLogin" >登录<i class="ai ai-enter"></i></button>
        </form>
    </div>
    
    <script type="text/javascript" src="${ctx}/Content/layui/layui.js"></script>
    <script type="text/javascript" src="${ctx}/Content/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/Content/js/jquery.form.js"></script>
    <script>
    
    layui.use('layer',function(){
	    var $ = layui.$,layer=layui.layer;
	    //登陆信息输入框的文字切换
	    $('.input-field').on('change',function(){
	        var $this = $(this),
	            value = $.trim($this.val()),
	            $parent = $this.parent();
	        	$parent.removeClass('field-focus');
	
	        if(value !== '' && !$parent.hasClass('field-focus')){
	            $parent.addClass('field-focus');
	        }else{
	            $parent.removeClass('field-focus');
	        }
	    });
	    
	    //登录按钮点击事件
	    $("#btnLogin").click(function() {
			var userAccount = $("#userAccount").val();
			var userPassword = $("#userPassword").val();
			if(userAccount!="" && userAccount!=null){
				if(userPassword!="" && userPassword!=null){
					 $("#formLogin").ajaxSubmit(function(data){
					 	if(data.code!="200"){
					 		layer.alert(data.text,{offset:'150px',icon:2});
					 	}else{
					 		layer.msg("登录成功！",{offset:'150px'});
					 		setTimeout(function(){
					 			window.location.href="${ctx}/user/toUrl.do?page=/BasicData/index";
					 		}, 1000);
					 	}
					});
				}else{
					layer.msg("密码不能为空！",{offset:'150px'});
				}
			}else{
				layer.msg("用户名不能为空！",{offset:'150px'});
			}
		});
	});
       
    
	//按Enter登录
	function Loginbtn() {
		if (window.event.keyCode == 13) {
			document.all('btnLogin').click();
		}
	}
	
	//防止session失效后登录页面可能会嵌套一起
	$(function(){
		if(top !=window){
			top.location.href=window.location.href;
		}
	});
	</script>
    
</body>
</html>
