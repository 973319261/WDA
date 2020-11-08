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
    
    <title>后台登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<style type="text/css">
		* {
			margin: 0;
			padding: 0;
			outline: none;
			font-family: \5FAE\8F6F\96C5\9ED1, 宋体;
			-webkit-user-select: none;
			-moz-user-select: none;
			-ms-user-select: none;
			-khtml-user-select: none;
			user-select: none;
			cursor: default;
			font-weight: lighter;
		}
		
		.center {
			margin: 0 auto;
		}
		
		.whole {
			width: 100%;
			height: 100%;
			line-height: 100%;
			position: fixed;
			bottom: 0;
			left: 0;
			z-index: -1000;
			overflow: hidden;
		}
		
		.whole img {
			width: 100%;
			height: 100%;
		}
		
		.mask {
			width: 100%;
			height: 100%;
			position: absolute;
			top: 0;
			left: 0;
			background: #000;
			opacity: 0.6;
			filter: alpha(opacity = 60);
		}
		
		.b {
			width: 100%;
			text-align: center;
			height: 400px;
			position: absolute;
			top: 50%;
			margin-top: -230px
		}
		
		.a {
			width: 150px;
			height: 50px;
			margin-top: 30px
		}
		
		.a a {
			display: block;
			float: left;
			width: 150px;
			height: 50px;
			background: #fff;
			text-align: center;
			line-height: 50px;
			font-size: 18px;
			border-radius: 25px;
			color: #333
		}
		
		.a a:hover {
			color: #000;
			box-shadow: #fff 0 0 20px
		}
		
		p {
			color: #fff;
			margin-top: 40px;
			font-size: 24px;
		}
		
		#num {
			margin: 0 5px;
			font-weight: bold;
		}
	</style>
  </head>
  
  <body onLoad="redirect();">
    
    <div class="whole">
		<img src="${ctx}/Content/error/images/123.png" />
	    <div class="mask"></div>
	</div>
	<div class="b">
		<img src="${ctx}/Content/error/images/404.png" class="center"/>
		<p>
			暂时未能找到您需要下载的文件<br>
			可能目标文件不存在或者丢失<br>
            <span id="num"></span>秒后自动关闭当前页
		</p>
	</div>
    
    <script type="text/javascript">
		var num=6;
		function redirect(){
			num--;
			document.getElementById("num").innerHTML=num;
			if(num<0){
				document.getElementById("num").innerHTML=0;
				//location.href="http://www.jsdaima.com";
				window.close();
			}
		}
		setInterval("redirect()", 1000);
	</script>
    
</body>
</html>
