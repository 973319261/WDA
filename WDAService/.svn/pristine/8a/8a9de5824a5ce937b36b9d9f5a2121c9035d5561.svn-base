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

<title>My JSP 'commentDetail.jsp' starting page</title>

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
	
	.layui-icon-ok:before{
		content:"\e605";
		color: #fff;
	}
	
	.layui-form-checked[lay-skin=primary] i{
		border-color: #5FB878!important;
    	background-color: #5FB878;
	}
	#insertReply .layui-input-inline{
		width: 400px;
		margin-right: 0;
	}
	
	button.btnstyle{
		height:28px;
		line-height:28px;
		border-radius: 15px;
		font-size: 13px;
	}

</style>
</head>

<body class="layui-view-body">
	<div class="layui-content">
		<div class="layui-page-header" style="margin-bottom: 15px;">
			<div class="pagewrap">
				<span class="layui-breadcrumb"> <a href="javascript:void(0)">首页</a>
					<a href="javascript:void(0)">通知分享管理</a>
					<a href="${ctx}/user/toUrl.do?page=/BasicData/shareIssue">分享发布</a>
					<a href="javascript:void(0)">评论详情</a>
				</span>
				<h2 class="title">评论详情</h2>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-card">
				<div class="layui-card-body">
					<div class="form-box">
						<div class="layui-form layui-form-item" style="margin: 5px 0 10px">
							<div class="layui-form-mid">用户名:</div>
							<div class="layui-input-inline">
								<input type="text" id="userName" placeholder="输入用户名"
									autocomplete="off" class="layui-input">
							</div>
							<div class="btn-group">
								<button class="layui-btn layui-btn-sm layui-btn-blue" type="button" onclick="searchCommentInfo()" style="margin-right:50px;">查询</button>
								<button class="layui-btn layui-btn-sm" type="button" onclick="insertReplyInfo()">回复</button>
								<button class="layui-btn layui-btn-sm layui-btn-danger" type="button" onclick="deleteCommentInfo()">删除</button>
							</div>
						</div>
						<table id="commentInfo" lay-filter="commentInfo"></table>
						<table id="commentDetailInfo" lay-filter="commentDetailInfo" style="margin-top:30px;"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- ******************************** 新增回复模态窗体  *********************** -->
	<div id="insertReply" class="modal" style="padding-left:0px;">
		<form class="layui-form" id="formInfo" action="">
			<input type="hidden" name="commentId" />
			<input type="hidden" name="noticeId" />
			<input type="hidden" name="userId" />
			<input type="hidden" name="adminId" />
			<input type="hidden" name="tierType" />
			<div class="layui-form-item">
				<label class="layui-form-label">公告主题：</label>
				<div class="layui-input-inline">
					<input type="text" id="noticeName" name="noticeName" style="text-overflow: ellipsis;" class="layui-input" readonly />
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">公告描述：</label>
				<div class="layui-input-inline">
					<textarea class="layui-textarea" id="noticeDescribe" name="noticeDescribe" readonly style="min-height:100px;max-height: 100px;"></textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">用户名：</label>
				<div class="layui-input-inline">
					<input type="text" id="userNames" name="userName" class="layui-input" readonly />
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">评论内容：</label>
				<div class="layui-input-inline">
					<textarea class="layui-textarea" name="content" readonly style="min-height:70px;max-height:70px;"></textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">回复内容：</label>
				<div class="layui-input-inline">
					<textarea placeholder="请输入内容" id="replayInfor" class="layui-textarea" required lay-verify="required"
					style="min-height:70px;max-height:70px;" maxlength="500"></textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit lay-filter="formDemo">发送</button>
					<button type="reset" class="layui-btn layui-btn-primary" id="resetAdd">重置</button>
				</div>
			</div>
		</form>
	</div>
	<div style="display: none">
  		<input type="hidden" id="noticeId" value="${noticeId}" />
  	</div>
  	<!----------------------- 工具条 -------------------------->
  	<script type="text/html" id="barDemo">
		<button class="layui-btn layui-btn-normal btnstyle" lay-event="replyDetail">详情</button>
	</script>
	
	<script type="text/html" id="replyOperation">
		<button class="layui-btn layui-btn-normal btnstyle" lay-event="replyInfo">回复</button>
		<button class="layui-btn layui-btn-danger btnstyle" lay-event="delReplyInfo">删除</button>
	</script>
	
	<script type="text/javascript" src="${ctx}/Content/js/jquery-3.3.1.min.js"></script>
	<script src="${ctx}/Content/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/echoforms.js"></script>
	<script>
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var tierType;//层级类型
		var $ = layui.$;
		$.ajaxSettings.async = false;

		$(function() {
			searchCommentInfo();
		});
		
		//查询公告评论信息
	 	function searchCommentInfo() {
			var userName = $("#userName").val();
			var noticeId=$("#noticeId").val();
			table.render({
				elem : '#commentInfo',
				url : '${ctx}/share/findCommentInfo.do',
				where:{
					userName:userName,noticeId:noticeId
				},
				page : true, //开启分页
				limit : 6,
				cols : [ [ //标题栏
					{ field : '',type:'numbers',title : '序号',align:'center' }, 
					{ field : 'commentId',title : '选择',align:'center',type :'checkbox' },
					{ field : 'noticeName',title : '公告主题',align:'center' }, 
					{ field : 'noticeDescribe',title : '公告描述',align : 'center' }, 
					{ field : 'userName',title : '评论人',width : 180,align : 'center' }, 
					{ field : 'content',title : '评论内容',width : 180,align : 'center'},
					{ field : 'creationTime',title : '评论时间',width : 180,align : 'center',
					  templet:'<div>{{ layui.util.toDateString(d.creationTime.time, "yyyy-MM-dd HH:mm:ss") }}</div>'},
					{ field : 'manager',title : '回复内容',width : 180,align : 'center',toolbar : '#barDemo',},
				] ]
			});
		}
		
	 	//监听工具条
		table.on('tool(commentInfo)', function(obj) {
			var data = obj.data;
			if (obj.event == 'replyDetail') {
				tierType=data.commentId;
				searchCommentDetailInfo(data.noticeId,data.commentId);
			}
		});
	 	
		//查询评论回复信息
	 	function searchCommentDetailInfo(noticeId,replyId) {
			table.render({
				elem : '#commentDetailInfo',
				url : '${ctx}/share/findCommentReplyInfo.do',
				where:{
					noticeId:noticeId,replyId:replyId
				},
				page : true, //开启分页
				limit : 6,
				cols : [ [ //标题栏
					{ field : '',type:'numbers',title : '序号',align:'center' }, 
					{ field : 'noticeName',title : '公告主题',align:'center' }, 
					{ field : '',title : '回复人',width : 180,align : 'center',
						templet:function(data){
							if(data.userName!=""){
								return "<span>"+data.userName+"</span>";
							}else{
								return "<span>"+data.adminName+"</span>";
								
							}
						}
					}, 
					{ field : 'cirticName',title : '回复对象',align : 'center',
						templet:function(data){
							if(data.commentType==0){
								return "<span>回复："+data.cirticName+"</span>";
							}else{
								return "<span>回复："+data.cirticNameTwo+"</span>";
								
							}
						}
					}, 
					{ field : 'content',title : '回复内容',width : 180,align : 'center'},
					{ field : 'creationTime',title : '回复时间',width : 180,align : 'center',
					  templet:'<div>{{ layui.util.toDateString(d.creationTime.time, "yyyy-MM-dd HH:mm:ss") }}</div>'},
					{ field : 'manager',title : '操作',width : 180,align : 'center',toolbar : '#replyOperation',},
				] ]
				//templet:'<span>回复：{{d.cirticName}}</span>'
			});
		}
		
	 	//监听工具条
		table.on('tool(commentDetailInfo)', function(obj) {
			var data = obj.data;
			if (obj.event == 'replyInfo') {
				loadDatatoForm("formInfo",data);
				if(data.userName==''){
					$("#userNames").val(data.adminName);
				}
				var index = layer.open({
					type : 1,
					title : '回复信息',
					content : $('#insertReply'),
					area : [ '580px', '540px' ]
				});
				form.render();
			}else if(obj.event == 'delReplyInfo'){
				layer.confirm('确定要删除该回复信息？', {
	            	icon: 3,
	                btn: ['确定', '取消']
	            }, function (index) {		
	            	layer.close(index); 
	            	$.post("${ctx}/share/deleteCommentInfo.do",{commentId:data.commentId},function(datas){
	            		if (datas.text=="success") {
	            			layer.alert("删除成功",{ icon: 1, skin: 'layui-layer-molv', closeBtn: 0, anim: 4 });
	            			//重新渲染
	    	            	table.reload('commentDetailInfo');
                        }else{
                        	layer.alert(datas.text, {icon : 2,title : '提示',offset : '150px'});
                        }
	            	});
		   		});
			}
		});
		
	 	//回复模态窗体
	 	function insertReplyInfo(){
	 		var checkStatus=table.checkStatus('commentInfo'),
			data=checkStatus.data;
	 		if(data.length==0){
	 			layer.alert("请先选择一条信息", {icon : 0,title : '提示',offset : '150px'});
	 		}else if(data.length>1){
	 			layer.alert("只能选择一条信息", {icon : 0,title : '提示',offset : '150px'});
	 		}else{
	 			loadDatatoForm("formInfo",data[0]);
	 			tierType=data[0].commentId;
	 			$("#noticeName").attr("title",data[0].noticeName);
	 			$("#noticeDescribe").attr("title",data[0].noticeDescribe);
	 			var index = layer.open({
					type : 1,
					title : '回复信息',
					content : $('#insertReply'),
					area : [ '580px', '540px' ]
				});
				form.render();
	 		}
	 	}
	 	
	 	//表单提交监听——回复
		form.on('submit(formDemo)', function(data) {
			var relayInfo=$("#replayInfor").val();
			var commentId=data.field.commentId;
			var noticeId=data.field.noticeId;
			var criticId=data.field.userId;
			var commentType=0;
			if(criticId == null || criticId =='0'){
				criticId=data.field.adminId;
				commentType=1;
			}
			$.getJSON("${ctx}/share/insertReplyInfo.do",{noticeId:noticeId,content:relayInfo,replyId:commentId,criticId:criticId
				,commentType:commentType,tierType:tierType}, function(d) {
				if (d.text=="success") {
					layer.closeAll();
					layer.msg("发送成功", {
						icon:1,
						offset : '150px'
					});
					document.all("resetAdd").click();
					//重新渲染
	            	table.reload('commentDetailInfo');
				} else {
					layer.alert(d.text, {
						icon : 2,
						title : '提示',
						offset : '150px'
					});
				}
			});
			return false;
		});
	 	
		//删除评论信息
		function deleteCommentInfo(){
			var checkStatus = table.checkStatus('commentInfo')//获取选中行数据
	      	,data = checkStatus.data;
		    if(data.length==0){
		    	layer.alert("请选择需要删除的数据", {
					icon : 0,
					title : '提示',
					offset : '150px'
				});
		    	return;
		    }
			layer.confirm('确定要删除选中的信息吗？', {
            	icon: 3,
                btn: ['确定', '取消']
            }, function (index) {		
            	layer.close(index); 
            	var num = 0;	                
            	for ( var i = 0; i < data.length; i++) {
            		$.ajax({
	                	url: "${ctx}/share/deleteCommentInfo.do?commentId=" + data[i].commentId,                    
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
            	searchCommentInfo();
            	//重新渲染
            	table.reload('commentDetailInfo');
	   		});
		}
		
	</script>
</body>

</html>
