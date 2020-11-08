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
	.layui-table-view .layui-form-checkbox {
		top: 5px !important;
	}
	
	.modal {
		display: none;
		padding: 20px;
	}
	.btn-style{
		height: 26px;
		line-height: 28px;
	}
</style>
  </head>
   <body>
  	<div class="layui-content" style="padding-bottom: 28px;">
		<div class="layui-page-header">
			<div class="pagewrap">
				<span class="layui-breadcrumb"> 
					<a href="javascript:void(0)">首页</a>
					<a href="javascript:void(0)">通知分享管理</a> 
					<a href="javascript:void(0)" onclick="comeBack()">分享发布</a>
					<a href="javascript:void(0)"><cite>新增公告</cite></a> 
				</span>
				<h2 class="title">新增公告</h2>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-card">
				<div class="layui-card-body">
					<div class="form-box ">
						<form class="layui-form" id="formInfo" action="">
							<fieldset class="layui-elem-field layui-field-title">
								<legend>公告标题</legend>
							</fieldset>
							<div class="layui-form-item">
							    <div class="layui-input-block" style="margin-left: 0px;">
							      <input type="text" id="numberId" name="noticeName" autocomplete="off" placeholder="请输入标题"
							        class="layui-input" style="padding-right: 80px;" maxlength="150" required lay-verify="required" />
							    </div>
							    <span class="layui-form-label" style="position: absolute;right: 10px;top: 74px;width: 55px;">
							    	<span id="numbers">0</span>/150
							    </span>
							</div>
							
							<fieldset class="layui-elem-field layui-field-title">
								<legend>公告内容</legend>
							</fieldset>							
							<div class="layui-input-block" style="margin-left: 0px;">
								<textarea class="layui-textarea" placeholder="请输入内容(500字)" name="noticeDescribe" 
									required lay-verify="required" style="min-height: 140px;max-height: 140px;resize: none" 
									maxlength="500" onkeyup="setLength(this,500,'wordsLength');"></textarea>
								<span id="wordsLength" style="position:absolute; right:5px; bottom:-3vh; font-size:12px; color:#BDCADA;">0/500</span>
							</div>
							
							<fieldset class="layui-elem-field layui-field-title">
								<legend>附件</legend>
							</fieldset>
							<table id="accessory" lay-filter="accessory"></table>
							<button class="layui-btn layui-btn-blue" type="button" onclick="clickupatt()">添加附件</button>
							<div class="layui-inline layui-word-aux">
							  上传文件大小要小于100M
							</div>
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
  		<form method="post" id="examfile" action="" enctype="multipart/form-data">
  			<button type="button" id="upattach"></button>
  		</form>
  	</div>
  	
  	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-danger layui-btn-xs btn-style" lay-event="del">删除</a>
  	</script>
  	<script type="text/html" id="filename">
		{{d.filename.split("__")[1]}}
  	</script>
		
	<script type="text/javascript" src="${ctx}/Content/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/Content/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/combobox.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/jquery.form.js"></script>
	
	<script type="text/javascript">
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.$;
	 	var layedit = layui.layedit;
	 	var upload = layui.upload;
	 	var laybuild;
	 	 	
	 	
 		$(function(){
 			$.ajaxSettings.async = false;
 			$.getJSON("${ctx}/share/cleanTemp.do",function(e){});

 			table.render({
				elem : '#accessory',
				url : '${ctx}/share/findFileInfo.do',
				page : true, //开启分页
				limit : 5,
				cols : [ [ //标题栏
				{
					type:'numbers',
					field : 'number',
					title : '序号',
					align:'center',
					width:50
				},
				{
					field : 'name',
					title : '附件名称',
					align:'center',
				}, {
					field : 'fileTypeName',
					title : '附件类型',
					align:'center',
					minWidth : 100
				},{
					field : 'tool',
					title : '操作',
					width : 100,
					align : 'center',
					toolbar : '#barDemo'
				} ] ]
 			});
		});
  		
 		var loading;
 		//文件上传
		upload.render({ 
			elem: '#upattach',
		    url: '${ctx}/share/uploadAttachment.do',
		    accept: 'file', //普通文件
		    exts: 'rmvb|flv|avi|mp4|doc|docx|xls|xlsx|pdf|ppt|pptx', //允许上传的文件后缀
		    field:"uploadFile",
		    size:102400,//最大上传文件大小
		    before:function(obj){
		    	onload();
			}
		    ,done: function(res){
		      if(res){
		      	setTimeout(function(){
		      		onclose();
		      		if(res){
			      		layer.msg("添加成功！");
		 				table.reload('accessory');
			      	}else{
			      		layer.msg("上传附件失败！请重试！");
			      	}
				}, 500);
	 		  }
		    } 
		});
	 	//监听工具条
		table.on('tool(accessory)', function(obj) {
			var data = obj.data;
			if (obj.event === 'del') {
				layer.confirm("确定要删除吗？",{icon:3,title:'提示',offset:'150px'},function(index){
					layer.close(index);
					var name = data.fileName;
					$.getJSON("${ctx}/share/deleteRedundant.do",{upFileFileName:name},function(e){
						if(e){
							layer.msg("已移除",{offset:'150px'});
							table.reload('accessory');
						}
					});
				});
			}
		});	
	 	
		//提交按钮
		form.on('submit(formDemo)',function(data){
			var fileInfo=table.cache['accessory'];
			if(fileInfo.length==0){
				layer.msg("请上传公告附件信息！",{offset:'150px'});
			}
			else{
				var noticeName = data.field.noticeName;
				var noticeDescribe= data.field.noticeDescribe;
				var fileTypeId = fileInfo[0].fileTypeId;
				var fileName = fileInfo[0].fileName;
				$.post("${ctx}/share/insertNoticeInfo.do",{noticeName:noticeName,noticeDescribe:noticeDescribe,fileTypeId:fileTypeId
					,fileName:fileName},function(e){
					if(e.text=="success"){
						layer.alert("发布成功！",{offset:'150px',icon:1,title:'提示'});
						setTimeout(function(){
							window.location.href="${ctx}/user/toUrl.do?page=/BasicData/shareIssue";
						}, 1500);
					}else{
						layer.alert(e.text,{offset:'150px',icon:2,title:'提示'});
					}
				}); 
			}
			return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
		});
		
		//添加附件按钮
	 	function clickupatt(){
	 		var num=table.cache['accessory'].length;
	 		if(num==0){
	 			document.all('upattach').click();
	 		}else{
	 			layer.msg("只能添加一个附件");
	 		}
	 	}
		
	 	//取消按钮
		$("#ret").click(function(){
			layer.confirm("确定要取消当前页面的操作吗？",{icon:3,title:'提示',offset:'150px'},function(index){
				layer.close(index);
				$.getJSON("${ctx}/share/cleanTemp.do",function(e){});
				window.location.href="${ctx}/user/toUrl.do?page=/BasicData/shareIssue";
			});
		});
	 	
		//监听input文本可输入字数
        $("#numberId").on("input",function(e){
            //获取input输入的值
            if(e.delegateTarget.value.length<=150){
            	var show = document.getElementById("numbers");
                show.innerHTML = Math.floor(e.delegateTarget.value.length);
            }
            if(e.delegateTarget.value.length>=150){
            	$('#numbers').attr("style","color:red");
            }else{
            	$('#numbers').removeAttr("style","color:red");
            }
        });
		
        //文本域字数监听
		function setLength(obj,maxlength,id){
		    var num=maxlength-obj.value.length;
		    var leng=id;
		    if(num<0){
		        num=0;
		    }
		    document.getElementById(leng).innerHTML=num+"/500";
		}
		
		//返回
		function comeBack(){
			$("#ret").click();
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
