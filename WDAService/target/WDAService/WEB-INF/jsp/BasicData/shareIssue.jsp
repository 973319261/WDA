<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<c:set var="path" value="${pageContext.request.servletPath}"></c:set>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'shareIssue.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

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
	
	button.btnstyle{
		height:28px;
		line-height:28px;
		border-radius: 15px;
		font-size: 13px;
	}
	
	.layui-laypage-limits{
		display: none !important;
	}
	
	.layui-table-cell a{
		color: blue;
		text-decoration: underline;
	}
	.layui-table-cell a:hover{
		color: blue;
	}
	.layui-icon-ok:before{
		content:"\e605";
		color: #fff;
	}
	
	.layui-form-checked[lay-skin=primary] i{
		border-color: #5FB878!important;
    	background-color: #5FB878;
	}
	
</style>
</head>

<body class="layui-view-body">
	<div class="layui-content">
		<div class="layui-page-header" style="margin-bottom: 15px;">
			<div class="pagewrap">
				<span class="layui-breadcrumb"> 
					<a href="javascript:void(0)">首页</a>
					<a href="javascript:void(0)">通知分享管理</a>
					<a href="javascript:void(0)">分享发布</a>
				</span>
				<h2 class="title">分享发布</h2>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-card">
				<div class="layui-card-body">
					<div class="form-box">
						<div class="layui-form layui-form-item" style="margin: 5px 0 10px">
							<div class="layui-form-mid">公告主题:</div>
							<div class="layui-input-inline">
								<input type="text" id="noticeTheme" autocomplete="off" class="layui-input">
							</div>
							<div class="layui-col-xs8 layui-col-sm8 layui-col-md8 btn-group">
								<button class="layui-btn layui-btn-sm layui-btn-normal" type="button" onclick="searchNoticeInfo()">查询</button>
								<button class="layui-btn layui-btn-sm" type="button" 
									onclick="window.location.href='${ctx}/user/toUrl.do?page=/BasicData/addNotice'">新增</button>
								<button class="layui-btn layui-btn-sm layui-btn-warm" type="button" onclick="updateNotice()">修改</button>
								<button class="layui-btn layui-btn-sm layui-btn-danger" type="button" onclick="deleteNoticeInfo()">删除</button>
							</div>
						</div>
						<table id="noticeinfo" lay-filter="noticeinfo"></table>
						<table id="noticeDetailInfo" lay-filter="noticeDetailInfo"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/Content/js/jquery-3.3.1.min.js"></script>
	<script src="${ctx}/Content/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/combobox.js"></script>
	<!-- 文本编辑器 -->
	
	<!----------------------- 工具条 -------------------------->
	<script type="text/html" id="barDemo">
		<button class="layui-btn layui-btn-normal btnstyle" lay-event="noticeDetail">详情</button>
	</script>

	<script type="text/html" id="comment">
		<button class="layui-btn layui-btn-normal btnstyle" lay-event="commentDetail">详情</button>
	</script>

	<script>
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.$;
	 	var layedit = layui.layedit;
		$.ajaxSettings.async = false;

		$(function() {
			searchNoticeInfo();
		});
		
		//监听工具条
		table.on('tool(noticeinfo)', function(obj) {
			var data = obj.data;
			if (obj.event == 'noticeDetail') {
				searchNoticDetail(data.noticeId);
			}else if(obj.event == 'commentDetail'){
				window.location.href="${ctx}/share/pageToComment.do?noticeId=" + data.noticeId;
			}
		});
	 	
		//查询公告信息
	 	function searchNoticeInfo() {
			var noticeTheme = $("#noticeTheme").val();
			table.render({
				elem : '#noticeinfo',
				url : '${ctx}/share/findNoticeInfo.do',
				where:{
					noticeTheme:noticeTheme
				},
				page : true, //开启分页
				limit : 6,
				cellMinWidth : 100, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				cols : [ [ //标题栏
				{	
					type:'numbers',
					field : '',
					title : '序号',
					align:'center'
				}, {
					field : 'noticeId',
					title : '选择',
					unresize : true,
					align : 'center',
					type:'checkbox',
				}, {
					field : 'noticeName',
					title : '公告主题',
					align:'center',
					unresize:true
				},{
					field : 'noticeDescribe',
					title : '公告描述',
					align:'center',
					unresize:true
				}, {
					field : 'adminName',
					title : '编制者',
					align : 'center',
					width : 135,
					unresize:true
				}, {
					field : 'releaseTime',
					title : '发布时间',
					align : 'center',
					width : 135,
					unresize:true,
					templet:'<div>{{ layui.util.toDateString(d.releaseTime.time, "yyyy-MM-dd") }}</div>',
				}, {
					field : 'tool',
					title : '公告详情',
					align : 'center',
					toolbar : '#barDemo',
					width : 120,
					unresize:true
				}, {
					field : 'tool',
					title : '评论详情',
					align : 'center',
					width : 120,
					unresize:true,
					toolbar : '#comment',
				} ] ]
			});
		}
	 	
	 	//查询附件信息
	 	function searchNoticDetail(noticeId) {
	 		table.render({
				elem : '#noticeDetailInfo',
				url : '${ctx}/share/findFileByNoticeId.do',
				where:{
					noticeId:noticeId
				},
				page : false, //开启分页
				cols : [ [ 
				{	
					type:'numbers',
					field : '',
					title : '序号',
					align:'center',
					width:100
				}, {
					field : 'noticeName',
					title : '公告主题',
					align:'center',
					unresize:true
				},{
					field : 'fileTypeName',
					title : '文件类型',
					align:'center',
					unresize:true
				}, {
					field : 'fileName',
					title : '文件',
					align : 'center',
					unresize:true,
					templet:function(data){
						return '<a href="${ctx}/share/downloadFile.do?fileName='+data.fileName+'" target="_blank">'+ 
								data.name +'</a>';
					}
					
				} ] ]
			});
		}
	 	
	 	//修改按钮
	 	function updateNotice(){
	 		var checkStatus=table.checkStatus('noticeinfo'),
			data=checkStatus.data;
	 		if(data.length==0){
				layer.alert("请先选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length>1){
				layer.alert("只能选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length==1){
				$.getJSON("${ctx}/share/findNoticeInfoById.do",{noticeId:data[0].noticeId},function(e){
					if(e){
						window.location.href="${ctx}/user/toUrl.do?page=/BasicData/editNotice";
					}
				});
			}
	 	}
		
	 	//删除公告信息
	 	function deleteNoticeInfo(){
	 		var checkStatus = table.checkStatus('noticeinfo')//获取选中行数据
	      	,data = checkStatus.data;
		    if(data.length==0){
		    	layer.alert("请选择需要删除的数据", {
					icon : 0,
					title : '提示',
					offset : '150px'
				});
		    	return;
		    }
			layer.confirm('真的要删除选中的公告信息?', {
            	icon: 3,
                btn: ['确定', '取消']
            }, function (index) {		
            	layer.close(index); 
            	var num = 0;	                
            	for ( var i = 0; i < data.length; i++) {
            		$.ajax({
	                	url: "${ctx}/share/deleteNoticeInfo.do?noticeId=" + data[i].noticeId,                    
	                	type: "post",//数据传输通道的类
	                    async: false,
	                    dataType: "json",//传输的数据的类型
	                    success: function (datas) {//直接理解为回调函数
	                 		if (datas.text=="success") {
	                 			num++;	//删除成功
	                        }
	                    }
	                });
            	}
            	layer.alert(num + "条数据删除成功，"+(data.length - num) + "条数据删除失败!",{ icon: 1, skin: 'layui-layer-molv', closeBtn: 0, anim: 4 });
            	//刷新表格
            	searchNoticeInfo();
            	//重新渲染
            	table.reload('noticeDetailInfo');
	   		});
	 	}
	 	
	</script>
</body>

</html>
