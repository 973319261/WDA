<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<c:set var="path" value="${pageContext.request.servletPath}"></c:set>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'addNotice.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


<link rel="stylesheet" href="${ctx}/Content/layui/css/layui.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/Content/css/view.css" type="text/css"></link>

<style type="text/css">
	.setVersion{
		display:block;
		margin:0 0 20px 30px;
		color:#177ce3;
		font-size:25px;
	}
</style>
  </head>
   <body>
   	<div class="layui-content">
		<div class="layui-page-header">
			<div class="pagewrap">
				<span class="layui-breadcrumb"> <a href="javascript:void(0)">首页</a>
					<a href="javascript:void(0)"><cite>App</cite></a>
					<a href="javascript:void(0)" onclick="comeBack()">版本更新</a>
					<a href="javascript:void(0)"><cite>新增版本</cite></a>
				</span>
				<h2 class="title">新增版本</h2>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-card">
				<div class="layui-card-body">
					<div class="form-box">
						<form class="layui-form" id="formInfo" action="">
							<fieldset class="layui-elem-field layui-field-title">
								<legend>版本号</legend>
							</fieldset>
							<span class="setVersion" id="versionNumber" name="versionNumber">&nbsp;</span>
							
							<fieldset class="layui-elem-field layui-field-title">
								<legend>版本标题</legend>
							</fieldset>
							<span class="setVersion" id="versionTitle" name="versionTitle">&nbsp;</span>
							<!-- <input id="checkNum" type="hidden" value="false" /> -->
							
							<fieldset class="layui-elem-field layui-field-title">
								<legend>是否强制更新</legend>
							</fieldset>
							 <div class="layui-form-item">
								<div class="layui-input-block" style="margin-left: 10px">
									<input type="checkbox" lay-filter="checkCon" lay-text="是|否" lay-skin="switch">
									<input id="isForce" name="isForce" type="hidden" value="false" />
								</div>
							</div>
							
							<fieldset class="layui-elem-field layui-field-title">
								<legend>版本特性</legend>
							</fieldset>
							<div class="layui-input-block" style="margin-left: 0px;">
								<textarea class="layui-textarea" id="versionContent" name="versionContent"
									required lay-verify="required" style="min-height: 140px;max-height: 140px;resize: none" 
									maxlength="500" onkeyup="setLength(this,500,'wordsLength');"></textarea>
								<span id="wordsLength" style="position:absolute; right:5px; bottom:-3vh; font-size:12px; color:#BDCADA;">0/500</span>
							</div>
							
							<fieldset class="layui-elem-field layui-field-title">
								<legend>版本apk</legend>
							</fieldset>
							
							<button class="layui-btn layui-btn-blue file-open" type="button" onclick="clickupatt()">添加 apk 文件</button>
							<span id="apkName" style="color:red;margin-left: 20px">未选择文件</span>
							<input name="apkUrl" id="apkUrl" type="hidden" />
														
							<div class="layui-form-item" style="margin-top: 50px;">
								<div class="layui-input-block">
									<button class="layui-btn layui-btn-blue" lay-submit lay-filter="formDemo">提交</button>
									<button type="reset" class="layui-btn layui-btn-primary" id="ret">取消</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
  	
  	<div style="display: none">
  		<form method="post" id="apkfile" action="" enctype="multipart/form-data">
  			<button type="button" id="upattach"></button>
  		</form>
  	</div>
	  			
	<script type="text/javascript" src="${ctx}/Content/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/Content/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/combobox.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/jquery.form.js"></script>
	
	<!----------------------- 工具条 -------------------------->
	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-info layui-btn-xs" lay-event="download">下载</a>
		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
		
	</script>
	<script type="text/html" id="checkcompel">
	  	<span>{{d.isForce == 1 ? '是':'否'}}</span>
	</script>
	
	<script type="text/javascript">
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.$;
	 	var layedit = layui.layedit;
	 	var upload = layui.upload;
	 	var laybuild;
	 	$.ajaxSettings.async = false; 	
	 	
	 			
	 	//apk文件上传
	 	upload.render({ 
			elem: '#upattach',
			url: '${ctx}/version/uploadTheApk.do',
			accept: 'file', //普通文件
			size:209715200,
			exts:"apk",//允许上传的文件后缀
			field:"uploadFile",
			before:function(obj){
				onload();//显示加载层
			},done: function(res, index, upload){
			    onclose();//关闭加载层
		      	if(res.code==0){
		      		var apkVersion = res.data[1];//版本标题
		      		var apkName  =  res.src;//截取后的apk名称
		      		
		      		if(apkVersion!=null && apkVersion!=""){
		      			$.getJSON("${ctx}/version/findVersionByNum.do",{versionTitle:apkVersion},function(data){
							if(data){
								layer.msg("该版本apk已存在，请重新上传！",{offset:'150px'});							
							}else{
								layer.msg("添加成功！");
								$("#versionNumber").html(res.data[0]);//版本号
					      		$("#versionTitle").html(apkVersion);//版本标题
					      		$("#apkUrl").val(res.src);//apk-回显数据库链接
					      		$("#apkName").html(apkName);//apk-只供页面显示
					      		$("#apkName").css("color","#28ABEB");//apk-设置显示颜色
							}
						});
		      		}			      		
	 		  	}
		    }
		});
	 	
	 	
	 	$("#filesUpload").click(function(){
	 		document.all('upattach').click();
	 	})
	 	//添加apk文件按钮事件
	 	function clickupatt(){
	 		document.all('upattach').click();
	 	}
	 	
	 	//取消按钮事件
		$("#ret").click(function(){
			layer.confirm("确定要取消当前页面的操作吗？",{icon:3,title:'提示',offset:'150px'},function(index){
				layer.close(index);
				$.getJSON("${ctx}/version/cleanTempApk.do",function(e){});//清空临时文件夹内容
				window.location.href="${ctx}/user/toUrl.do?page=/BasicData/versionUpdating";
			});
		});
		
		//点击版本更新时返回上一页面事件
		function comeBack(){
			$("#ret").click();
		}
		
		
		//新增提交
		form.on('submit(formDemo)',function(data){
			var apkUrl = $("#apkUrl").val();//获取apk文件			
			if(apkUrl==""){
				layer.msg("请选择apk文件！",{offset:'150px'});
			}
			else{
				var versionNumber = $("#versionNumber").html();//版本号
				var versionTitle= $("#versionTitle").text();//版本标题
				var versionContent= data.field.versionContent;//版本内容
				var apkUrl= data.field.apkUrl;//apk文件	
				var isForce= data.field.isForce;//是否强制更新			
				$.post("${ctx}/version/addVersion.do",{versionNumber:versionNumber,versionTitle:versionTitle,
					versionContent:versionContent,apkUrl:apkUrl,isForce:isForce},function(e){
					if(e.text=="success"){
						layer.alert("新增成功！",{offset:'150px',icon:1,title:'提示'});
						setTimeout(function(){
							window.location.href="${ctx}/user/toUrl.do?page=/BasicData/versionUpdating";
						}, 1500);
					}else{
						layer.alert(e.text,{offset:'150px',icon:2,title:'提示'});
					}
				});
			}
			return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
		});
		
		//switch监听
		form.on('switch(checkCon)', function(data){
			$("#isForce").val(data.elem.checked);
		}); 
		
		
      	//文本域字数监听(新增)
		function setLength(obj,maxlength,id){
		    var num=maxlength-obj.value.length;
		    var leng=id;
		    if(num<0){
		        num=0;
		    }
		    document.getElementById(leng).innerHTML=num+"/500";
		}
 		
		
		/**************************************  加载层    *************************************/
		//显示加载层
		function onload(){
			layer.msg("正在上传", {
				icon:16,
				shade:[0.2, '#000'],
				time:false//取消自动关闭
			});
		}
		
		//关闭加载层
		function onclose(){
			layer.close(layer.index);
		}
		
	</script>
  </body>
</html>
