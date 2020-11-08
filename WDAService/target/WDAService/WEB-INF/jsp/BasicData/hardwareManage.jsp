<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'hardwareManage.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" href="${ctx}/Content/layui/css/layui.css"
	type="text/css"></link>
<link rel="stylesheet" href="${ctx}/Content/css/view.css"
	type="text/css"></link>

<style type="text/css">
	.modal {
		display: none;
		padding: 20px;
	}
	
	#insertModal .layui-form-label{
		width:90px;
	}
	
	#updateModal .layui-form-label{
		width:90px;
	}
	
	.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
	   top: 50%;
	   transform: translateY(-50%);
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
		<div class="layui-page-header">
			<div class="pagewrap">
				<span class="layui-breadcrumb"> <a href="javascript:void(0)">首页</a>
					<a href="javascript:void(0)">账户管理</a> <a href="javascript:void(0)"><cite>硬件管理</cite>
				</a>
				</span>
				<h2 class="title">硬件管理</h2>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-card">
				<div class="layui-card-body">
					<div class="form-box">
						<div class="layui-form layui-form-item">
							<div class="layui-inline">
								<div class="layui-form-mid">硬件ID:</div>
								<div class="layui-input-inline" style="width: 190px;">
									<input type="text" id="hardware" autocomplete="off" class="layui-input" 
										placeholder="输入硬件ID/名称">
								</div>			
								<div class="btn-group">
									<button class="layui-btn layui-btn-sm layui-btn-normal" onclick="searchHardwareInfo()">搜索</button>
									<button class="layui-btn layui-btn-sm" id="insertHardware">新增</button>
									<button class="layui-btn layui-btn-sm layui-btn-warm" id="updateHardware">修改</button>
									<button class="layui-btn layui-btn-sm layui-btn-danger" id="deleteHardware">删除</button>
								</div>
							</div>
						</div>
						<table id="hardwareInfo" lay-filter="hardwareInfo"></table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- ******************************** 新增模态窗体  *********************** -->
	<div id="insertModal" class="modal">
		<form class="layui-form" id="insertHardwareInfo" action="">
			<div class="layui-form-item">
				<label class="layui-form-label">硬件ID</label>
				<div class="layui-input-inline">
					<input type="text" name="hardwareEquipmentId" required lay-verify="required" maxlength="100"
						autocomplete="off" class="layui-input" onkeyup="value=value.replace(/[^\w]/ig,'')">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">硬件名称</label>
				<div class="layui-input-inline">
					<input type="text" name="hardwareEquipmentName" required lay-verify="required" maxlength="50"
						autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">硬件使用状态</label>
				<div class="layui-input-inline">
					<select id="" name="isDisabled">
						<option selected="selected" value="true">启用</option>
						<option value="false">禁用</option>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">使用区域</label>
				<div class="layui-input-inline">
					<input type="text" name="useArea" required lay-verify="required" maxlength="50"
						autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">保管人</label>
				<div class="layui-input-inline">
					<input type="text" name="preserver" required lay-verify="required" maxlength="50"
						autocomplete="off" class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item" style="text-align: center;">
				<button class="layui-btn" lay-submit lay-filter="insertForm">确认</button>
				<button type="reset" class="layui-btn layui-btn-primary" id="resetAdd">重置</button>
			</div>
		</form>
	</div>
	
	<!-- ******************************** 修改模态窗体  *********************** -->
	<div id="updateModal" class="modal">
		<form class="layui-form" id="updateHardwareInfo" action="">
			<div style="display: none">
				<input type="text" name="hardwareManagementId" value="" />
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">硬件ID</label>
				<div class="layui-input-inline">
					<input type="text" name="hardwareEquipmentId" required lay-verify="required" maxlength="100"
						autocomplete="off" class="layui-input" onkeyup="value=value.replace(/[^\w]/ig,'')">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">硬件名称</label>
				<div class="layui-input-inline">
					<input type="text" name="hardwareEquipmentName" required lay-verify="required" maxlength="50"
						autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">硬件使用状态</label>
				<div class="layui-input-inline">
					<select id="state" name="isDisabled">
						<option value="0">启用</option>
						<option value="1">禁用</option>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">使用区域</label>
				<div class="layui-input-inline">
					<input type="text" name="useArea" required lay-verify="required" maxlength="50"
						autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">保管人</label>
				<div class="layui-input-inline">
					<input type="text" name="preserver" required lay-verify="required" maxlength="50"
						autocomplete="off" class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item" style="text-align: center;">
				<button class="layui-btn" lay-submit lay-filter="editHardware">确认</button>
			</div>
		</form>
	</div>

	<script type="text/javascript"
		src="${ctx}/Content/js/jquery-3.3.1.min.js"></script>
	<script src="${ctx}/Content/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/combobox.js"></script>

	<!----------------------- 工具条 -------------------------->
	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
	</script>
	<script type="text/html" id="toVoidNoBtn">
	  <input type="checkbox" name="usestate" value="{{d.hardwareManagementId}}" lay-skin="switch" lay-text="启用|禁用" lay-filter="stateDemo" {{d.isDisabled == 1 ? 'checked':''}}>
	</script>

	<script>
		/*************************** layui *********************************************/
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.$;

		$.ajaxSettings.async = false;

		/*************************** 页面初始化  *********************************************/
		$(function() {
			searchHardwareInfo();//查询数据表格
			form.render('select');//重新渲染表格数据
		});

		/*************************** 表格数据查询方法  *********************************************/
		function searchHardwareInfo() {
			var hardware = $("#hardware").val();
			table.render({
				elem : '#hardwareInfo',
				url : '${ctx}/user/findHardwareManageInfo.do',
				where : {
					hardware : hardware,
				},
				page : true, //开启分页
				limit : 5,
				limits : [ 5, 10, 15, 20 ],
				cellMinWidth : 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				cols : [ [ //标题栏
					{ field : '',title : '序号',type:'numbers',unresize : true,align : 'center'},
					{ field : '',title : '选择',unresize : true,align : 'center',type:'checkbox'}, 
					{ field : 'hardwareEquipmentName',title : '硬件名称',align : 'center',unresize : true}, 
					{ field : 'hardwareEquipmentId',title : '硬件ID',align : 'center',unresize : true}, 
					{ field : 'preserver',title : '保管人',align : 'center',unresize : true}, 
					{ field : 'userAccount',title : '最近使用者ID',align : 'center',unresize : true,
						templet:function(d){
							if(d.userAccount!=''){
								return '<span>'+d.userAccount+'</span>'
							}else{
								return '<span>暂无</span>'
							}
						}
					},
					{ field : 'useArea',title : '使用区域',align : 'center',unresize : true}, 
					{ field : 'useDate',title : '使用时间',align : 'center',unresize : true,
						 templet:function(d){
							var tt=layui.util.toDateString(d.useDate.time, "yyyy-MM-dd");
							if(tt=="2000-01-01"){
								tt="暂无";
							}
							return '<div>'+tt+'</div>'
						},
					},  
					{ field : 'isDisabled',title : '状态',align : 'center',unresize : true,toolbar : '#toVoidNoBtn'} 
				] ]
			});
		}

		/*************************** 新增硬件  *********************************************/
		$("#insertHardware").click(function() {
			$("#fun").val("addaddPro");
			showlayer();
		});		

		/*************************** 打开新增硬件管理窗体  *********************************************/
		function showlayer() {
			document.all('resetAdd').click();
			var index = layer.open({
				type : 1,
				title : '新增数据页面',
				content : $('#insertModal'),
				area : [ '400px', '420px' ],
				resize:false,//是否允许拉伸
				cancel : function(index, layero) {
					if (confirm('确定要关闭么，未保存的数据将会丢失！')) { //只有当点击confirm框的确定时，该层才会关闭
						layer.close(index);
					}
					return false;
				}
			});
			form.render();
		}
		
		//表单提交监听——新增
		form.on('submit(insertForm)', function(data) {
			$.getJSON("${ctx}/user/insertHardwareManageInfo.do",data.field, function(d) {
				if (d.text=="success") {
					layer.closeAll();
					layer.msg("新增成功", {icon:1,offset : '150px'});
					document.all("resetAdd").click();
					searchHardwareInfo();
				} else {
					layer.alert(d.text, {icon : 2,title : '提示',offset : '150px'});
				}
			});
			return false;
		});
		
		
		//修改硬件管理模态框
		$("#updateHardware").click(function(){
			var checkStatus=table.checkStatus('hardwareInfo'),
			data=checkStatus.data;
			if(data.length==0){
				layer.alert("请先选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length>1){
				layer.alert("只能选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length==1){
				loadDatatoForm("updateHardwareInfo",data[0]);	
				
				if(data[0].isDisabled){
					$("#state").val(0);
				}else{
					$("#state").val(1);
				}
				
				var index = layer.open({
					type : 1,
					title : '修改硬件管理信息',
					content : $('#updateModal'),
					area : [ '400px', '420px' ],
					resize:false,//是否允许拉伸
					cancel : function(index, layero) {
						if (confirm('确定要关闭么，未保存的数据将会丢失！')) {
							layer.close(index);
						}
						return false;
					}
				});
				form.render();
			}
		});
		
		
		//修改硬件管理----提交按钮
		form.on('submit(editHardware)', function(data) {			
			if (data.field.isDisabled==0) {
				data.field.isDisabled=true;
			}else{
				data.field.isDisabled=false;
			}
			$.post("${ctx}/user/updateHardwareManageInfo.do", data.field, function(e) {
				if (e.text=="success") {
					layer.closeAll();
					layer.msg("提交成功！", {icon : 1,offset : '150px'});
					searchHardwareInfo();
				} else {
					layer.alert(e.text, {icon : 2,title : '提示',offset : '150px'});
				}
			});
			return false;
		});
		
		
		//删除硬件管理信息
		$("#deleteHardware").click(function(){
		    var checkStatus = table.checkStatus('hardwareInfo')//获取选中行数据
	      	,data = checkStatus.data;
		    if(data.length==0){
		    	layer.alert("请选择需要删除的数据", {icon : 0,title : '提示',offset : '150px'});
		    	return;
		    }
			layer.confirm('真的要删除该数据么？', {
            	icon: 3,
                btn: ['确定', '取消']
            }, function (index) {		
            	layer.close(index); 
            	var num = 0;	                
            	for ( var i = 0; i < data.length; i++) {
            		$.ajax({
	                	url: "${ctx}/user/deleteHardwareManageInfo.do?hardwareManagementId=" + data[i].hardwareManagementId,                    
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
            	searchHardwareInfo();
	   		});
		});
		
		
		//(硬件管理)监听启用/禁用 操作
		form.on('switch(stateDemo)', function(obj){
		  	var checked = obj.elem.checked;//获取switch状态
		  	var Id = obj.value;//获取id
		  	$.getJSON("${ctx}/user/updateIsDisabledState.do",{hardwareManagementId:Id,isDisabled:checked},function(e){
		  		if(e.text=="success"){
		  			layer.msg("变更已生效",{icon:1,offset:'150px'});
		  		}else{
		  			layer.alert(e.text,{icon:5,title:'提示',offset:'150px',anim:6});
		  		}
		  		table.reload('hardwareInfo');//重新渲染表格数据
		  	});
		});
		
		
	</script>
</body>

</html>
