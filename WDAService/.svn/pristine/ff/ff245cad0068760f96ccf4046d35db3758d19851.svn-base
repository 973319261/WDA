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

<title>My JSP 'accountManage.jsp' starting page</title>

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
		padding: 20px 20px 20px 0px;
	}
	
	.layui-icon-ok:before{
		content:"\e605";
		color: #fff;
	}
	
	.layui-form-checked[lay-skin=primary] i{
		border-color: #5FB878!important;
    	background-color: #5FB878;
	}
	
	#layui-tab-title li{
		margin: 0px 10px 0px 0px;
		padding: 0px 6px;
	}
	
	.btn-one{
		margin-top: 3px;
	}
</style>
</head>

<body class="layui-view-body">
	<div class="layui-content">
		<div class="layui-page-header" style="margin-bottom: 15px;">
			<div class="pagewrap">
				<span class="layui-breadcrumb"> <a href="javascript:void(0)">首页</a>
				<a href="javascript:void(0)">账户管理</a> <a href="javascript:void(0)"><cite>网页端账号管理</cite></a>
				</span>
				<h2 class="title">网页端账号管理</h2>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-card">
				<div class="layui-card-body">
					<div class="form-box" id="tableOne">
						<div class="layui-form layui-form-item" style="margin: 5px 0 10px">
							<div class="layui-inline">
								<div class="layui-form-mid">账号:</div>
								<div class="layui-input-inline">
									<input type="text" id="adminAccount" autocomplete="off" 
										class="layui-input" placeholder="输入用户账号">
								</div>
								<div class="btn-group">
									<button class="layui-btn layui-btn-sm layui-btn-normal" id="selectUser" onclick="searchAdminInfo()">搜索</button>
									<button class="layui-btn layui-btn-sm" onclick="addAccount()">新增</button>
									<button class="layui-btn layui-btn-sm layui-btn-warm" onclick="editAccount()">修改</button>
									<button class="layui-btn layui-btn-sm layui-btn-danger" onclick="deleteAccount()">删除</button>
								</div>
							</div>
					    </div>
						<table id="adminInfo" lay-filter="adminInfo"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- ******************************** 新增模态窗体  *********************** -->
	<div id="insertModal" class="modal">
		<form class="layui-form" id="insertForm" action="">
			<div class="layui-form-item">
				<label class="layui-form-label">用户账号：</label>
				<div class="layui-input-block">
					<input type="text" name="adminAccount" lay-verify="required" autocomplete="off" class="layui-input" maxlength="50" />
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">用户名：</label>
				<div class="layui-input-block">
					<input type="text" name="adminName" lay-verify="required" autocomplete="off" class="layui-input" maxlength="50" />
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">密码：</label>
				<div class="layui-input-block">
					<input type="password" name="adminPassword" lay-verify="required" autocomplete="off" class="layui-input" maxlength="50"
						placeholder="只能输入数字、英文和小数点" onkeyup="value=value.replace(/[^\w\.]/ig,'')" />
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">公司：</label>
				<div class="layui-input-block">
					<input type="text" name="company" autocomplete="off" class="layui-input" maxlength="20" />
				</div>
			</div>
			<div class="layui-form-item" style="text-align: center;">
				<div class="btn-group">
					<button class="layui-btn" lay-submit lay-filter="formDemo">提交</button>
					<button type="reset" class="layui-btn layui-btn-primary" id="resetAdd">重置</button>
				</div>
			</div>
		</form>
	</div>
	<!-- ******************************** 修改模态窗体  *********************** -->
	<div id="updateModal" class="modal">
		<form class="layui-form" id="updateForm" action="">
			<input name="adminId" type="hidden" />
			<div class="layui-form-item">
				<label class="layui-form-label">用户账号：</label>
				<div class="layui-input-block">
					<input type="text" name="adminAccount" lay-verify="required" autocomplete="off" class="layui-input" maxlength="50" />
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">用户名：</label>
				<div class="layui-input-block">
					<input type="text" name="adminName" lay-verify="required" autocomplete="off" class="layui-input" maxlength="50" />
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">密码：</label>
				<div class="layui-input-block">
					<input type="password" id="password" name="adminPassword" autocomplete="off" class="layui-input" maxlength="50"
						onkeyup="value=value.replace(/[^\w\.]/ig,'')" />
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">公司：</label>
				<div class="layui-input-block">
					<input type="text" name="company" autocomplete="off" class="layui-input" maxlength="20" />
				</div>
			</div>
			<div class="layui-form-item" style="text-align: center;">
				<div class="btn-group">
					<button class="layui-btn" lay-submit lay-filter="edit">修改</button>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="${ctx}/Content/js/jquery-3.3.1.min.js"></script>
	<script src="${ctx}/Content/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/echoforms.js"></script>
	<script>
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.$;
		$.ajaxSettings.async = false;

		$(function() {
			searchAdminInfo();//查询网页端账号信息
		});
	 	
		//查询网页端账号信息
	 	function searchAdminInfo() {
			var adminAccount = $("#adminAccount").val();
			table.render({
				elem : '#adminInfo',
				url : '${ctx}/user/findAccountInfo.do',
				where:{
					adminAccount:adminAccount
				},
				page : true, //开启分页
				limit : 6,
				cols : [ [ //标题栏
					{ type:'numbers',field : '',title : '序号',align:'center'}, 
					{ field : 'cover',title : '选择',align:'center',type :'checkbox'},
					{ field : 'adminAccount',title : '用户账号',align:'center',unresize : true}, 
					{ field : 'adminName',title : '用户名',align : 'center',unresize : true},
					{ field : 'company',title : '公司',align : 'center',unresize : true,
						templet:function(d){
							if(d.company!=''){
								return '<span>'+d.company+'</span>'
							}else{
								return '<span>暂无</span>'
							}
						}
					}
				] ],done:function(res,curr,count){
					$(".layui-laypage-limits").hide();//隐藏分页下拉选项
				}
			});
		}
	 	
	 	//新增模态窗体
	 	function addAccount(){
	 		document.all('resetAdd').click();
			var index = layer.open({
				type : 1,
				title : '新增页面',
				content : $('#insertModal'),
				area : [ '450px', '370px' ],
				resize:false,//是否允许拉伸
			});
			form.render();                                                                                                                                                                                                                                                 
	 	}
	 	
	 		 	
	 	//表单提交监听——新增账号管理信息
		form.on('submit(formDemo)', function(data) {
			$.getJSON("${ctx}/user/insertAdminInfo.do",data.field, function(d) {
				if (d.text=="success") {
					layer.closeAll();
					layer.msg("新增成功", {icon:1,offset : '150px'});
					document.all("resetAdd").click();//重置表单
					searchAdminInfo();//刷新表格
				} else {
					layer.alert(d.text, {icon : 2,title : '提示',offset : '150px'});
				}
			});
			return false;
		});
	 	
		//修改模态窗体
	 	function editAccount(){
	 		var checkStatus=table.checkStatus('adminInfo'),
			data=checkStatus.data;
			if(data.length==0){
				layer.alert("请先选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length>1){
				layer.alert("只能选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length==1){
				loadDatatoForm("updateForm",data[0]);//回显数据
				$("#password").val("");
				$("#password").attr('placeholder',"如需更改请输入");
				var index = layer.open({
					type : 1,
					title : '修改页面',
					content : $('#updateModal'),
					area : [ '450px', '370px' ],
					resize:false,//是否允许拉伸
				});
				form.render();
			}
	 	}
	 	
	 	//表单提交监听——修改账号管理信息
		form.on('submit(edit)', function(data) {
			$.getJSON("${ctx}/user/updateAdminInfo.do",data.field, function(d) {
				if (d.text=="success") {
					layer.closeAll();
					layer.msg("修改成功", {icon:1,offset : '150px'});
					searchAdminInfo();//刷新表格
				} else {
					layer.alert(d.text, {icon : 2,title : '提示',offset : '150px'});
				}
			});
			return false;
		});
	 		 	
		//删除账号管理信息
	 	function deleteAccount(){
			var checkStatus = table.checkStatus('adminInfo');//获取选中行数据			
			var data = checkStatus.data;
		    if(data.length==0){
		    	layer.alert("请选择需要删除的数据", {icon : 0,title : '提示',offset : '150px'});
		    }else{
		    	layer.confirm('真的要删除选中的数据吗？', {
	            	icon: 3,
	                btn: ['确定', '取消']
	            }, function (index) {
	            	layer.close(index);
	            	var num = 0;
	            	for ( var i = 0; i < data.length; i++) {
	            		$.ajax({
		                	url: "${ctx}/user/deleteAdminInfo.do?adminId=" + data[i].adminId,                    
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
	            	searchAdminInfo();//刷新表格
		   		});
		    }
	 	}
		
	 	//监听工具条(永久映射)
		table.on('tool(canConfigurationInfos)', function(obj) {
			var data = obj.data;
			if (obj.event == 'editTwo') {
				loadDatatoForm("updateInfo", data);//回填数据
				updateModals = layer.open({
					type : 1,
					title : '修改页面',
					content : $('#updateModal'),
					area : [ '450px', '300px' ],
					resize:false,//是否允许拉伸
				});
			}
		});
	 	
        //文本域字数监听啦啦啦
		function setLength(obj,maxlength,id){
		    var num=maxlength-obj.value.length;
		    var leng=id;
		    if(num<0){
		        num=0;
		    }
		    document.getElementById(leng).innerHTML=num;
		}
		
	 	//监听工具条(永久映射)
		table.on('tool(canConfigurationInfos)', function(obj) {
			var data = obj.data;
			if (obj.event == 'editTwo') {
				loadDatatoForm("updateInfo", data);//回填数据
				updateModals = layer.open({
					type : 1,
					title : '修改页面',
					content : $('#updateModal'),
					area : [ '450px', '300px' ],
					resize:false,//是否允许拉伸
				});
			}
		});
	 	
        //文本域字数监听
		function setLength(obj,maxlength,id){
		    var num=maxlength-obj.value.length;
		    var leng=id;
		    if(num<0){
		        num=0;
		    }
		    document.getElementById(leng).innerHTML=num;
		}
        
	</script>
</body>

</html>
