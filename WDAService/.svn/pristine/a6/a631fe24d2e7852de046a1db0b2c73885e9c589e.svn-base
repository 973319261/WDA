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
	
	.red{
		color:red;
	}
	
	.layui-table-body{
		overflow: hidden;
	}
	
	.layui-table-view .layui-table{
		width:100%;
	}
	
	.hide{
		display: none;
	}
	
	p > a:hover {
	    color: blue;
	    text-decoration: none;
	}
</style>
  </head>
   <body>
   	<div class="layui-content">
		<div class="layui-page-header">
			<div class="pagewrap">
				<span class="layui-breadcrumb"> <a href="javascript:void(0)">首页</a>
					<a href="javascript:void(0)">APP</a>
					<a href="javascript:void(0)"><cite>版本更新</cite></a>
				</span>
				<h2 class="title">版本更新</h2>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-card">
				<div class="layui-card-body">
					<div class="form-box">
						<div class="layui-form layui-form-item">
							<div class="layui-inline">
								<div class="layui-form-mid">版本号:</div>
								<div class="layui-input-inline" style="width: 190px;">
									<input type="text" class="layui-input" id="version" name="version" autocomplete="off">
								</div>
								<div class="btn-group">
									<button class="layui-btn layui-btn-sm layui-btn-blue" onclick="SearchTab()">查询</button>
									<button class="layui-btn layui-btn-sm" id="addVersion"><i class="layui-icon">&#xe654;</i>新增</button>
								</div>
							</div>
						</div>						
						<table id="versionInfo" lay-filter="versionInfo"></table>
					</div>
				</div>
			</div>
		</div>
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
	 	 	
		$.ajaxSettings.async = false;
 		
		//页面加载事件
		$(function() {
			SearchTab();
		});

		//查询
		function SearchTab() {
			var version = $("#version").val();
			table.render({
			elem : '#versionInfo',
			url : '${ctx}/version/findVersionInfo.do',
			where : {
				versionNumber : version,
			},
			page : true, //开启分页
			limit : 10,
			limits:[5,10,15,20],
			cols : [ [ //标题栏
					{field : 'number',title : '序号',type:'numbers',unresize:true,width:100,align : 'center'},
					{field : 'versionId',hide:true,title : 'ID',type:'numbers',unresize:true,align : 'center'},
					{field : 'versionNumber',title : '版本号',unresize:true,align : 'center',sort:true},
					{field : 'versionTitle',title : '版本标题',unresize:true,align : 'center'},
					{field : 'versionContent',title : '版本内容',unresize:true,align : 'center'},
					{field : 'apkUrl',hide:true,title : '下载路径',unresize:true,align : 'center',},
					{field : 'creationDate',title : '创建时间',unresize:true,align : 'center',sort:true,
						templet:'<div>{{ layui.util.toDateString(d.creationDate.time, "yyyy-MM-dd HH:mm:ss") }}</div>'},
					{field : 'isForce',title : '是否强制更新',unresize:true,align : 'center',templet: '#checkcompel'},
					{field : 'tool',title : '操作',unresize:true,align : 'center',width:150,toolbar : '#barDemo'}
				] ]
			});
		}
		
		//新增版本按钮
		$("#addVersion").click(function() {
			window.location.href="${ctx}/user/toUrl.do?page=/BasicData/addVersion";
		});
		
		//表格事件监听
		table.on('tool(versionInfo)', function(obj) {
			var data = obj.data;
			if (obj.event === 'del') {
				layer.confirm("确定要删除该条数据吗？", {icon : 3,title : '提示',offset : '150px'}, function(index) {
					layer.close(index);
					$.getJSON("${ctx}/version/deleteVersion.do", {
						versionId : data.versionId,apkUrl:data.apkUrl
					}, function(e) {
						if (e) {
							layer.msg("已删除！", {icon:1,offset : '150px'});
							SearchTab();//查询表格数据
						} else {
							layer.alert("操作失败", {icon : 2,title : '提示',offset : '150px'});
						}
					});
				});
			}
			if (obj.event === 'download') {
				layer.open({
					type: 1,//layer提供了5种层类型：0：信息框，默认,1：页面层,2：iframe层,3：加载层,4：tips层
					title:"点击下方链接开始下载",
					content: '<p style="text-align:center;padding:20px"><a href="${ctx}/version/downloadApk.do?apkName='+data.apkUrl+'" onclick="downloadBtn()" target="_blank">立即下载</a></p>' //这里content是一个普通的String
				});
			}
		});
					
		
		//关闭下载弹窗
		function downloadBtn(){
			layer.closeAll('page');//关闭所有页面层
		}
		
		
	</script>
  </body>
</html>
