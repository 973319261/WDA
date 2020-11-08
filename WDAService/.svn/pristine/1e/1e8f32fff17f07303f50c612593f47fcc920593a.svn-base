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

<title>My JSP 'moduleSupplier.jsp' starting page</title>

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
	
	table td{
		height:60px;
		position: relative!important;
	}
	
	table td .layui-table-cell{
		height:100%;
		line-height: 50px;
	}
	
	table td .imgbox img{
		max-width:150px;
		width:80px!important;
		height:50px;
	}
	
	.layui-table-grid-down{
		display: none;
	}
	
	.btn-style{
		padding:0px 12px;
		height:30px;
		border-radius:15px;
		line-height: 30px;
		font-size: 13px;
	}	
	
	#insertSupplierModal .layui-input-inline{
		width: 250px;
	}
	
	#insertSupplierModal .layui-form-select dl {
		max-height: 190px;
	}
	
	#updateSupplierModal .layui-input-inline{
		width: 250px;
	}
	
	#updateSupplierModal .layui-form-select dl {
		max-height: 190px;
	}
	
	.layui-form-mid{
		float: right;
	}
	
	.layui-col-md4{
		padding: 4px 0!important;
	}
	
</style>
</head>

<body class="layui-view-body">
	<div class="layui-content">
		<div class="layui-page-header" style="margin-bottom: 15px;">
			<div class="pagewrap">
				<span class="layui-breadcrumb"> <a href="javascript:void(0)">首页</a>
					<a href="javascript:void(0)">模块供应商</a>
				</span>
				<h2 class="title">模块供应商</h2>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-card">
				<div class="layui-card-body">
					<div class="form-box">
						<div class="layui-form layui-form-item">
						<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
								<div class="layui-col-xs1 layui-col-sm1 layui-col-md1">
									<div class="layui-form-mid">车型:</div>
								</div>								
								<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">
									<select id="carTypeId" lay-filter="cartype"></select>
								</div>
																
								<div class="layui-col-xs1 layui-col-sm1 layui-col-md1">
									<div class="layui-form-mid">配置:</div>
								</div>								
								<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">
									<select id="configurationId" lay-filter="configuration"></select>
								</div>								
								
								<div class="layui-col-xs1 layui-col-sm1 layui-col-md1">
									<div class="layui-form-mid">模块:</div>
								</div>
								<div class="layui-col-xs2 layui-col-sm2 layui-col-md2" >
									<select id="moduleId" lay-filter="module"></select>
								</div>								
								
								<div class="layui-col-xs1 layui-col-sm1 layui-col-md1">
									<div class="layui-form-mid">供应商:</div>
								</div>
								<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">
									<select id="supplierId" ></select>
								</div>
							</div>
							
							<!-- 按钮组 -->
							<div class="layui-col-xs4 layui-col-sm4 layui-col-md4" style="text-align: center;padding: 0!important;">
								<div class="btn-group">
									<button class="layui-btn layui-btn-sm layui-btn-normal" type="button" onclick="SearchTab()">查询</button>
									<button class="layui-btn layui-btn-sm" type="button" onclick="addModalSupplier()">模块供应商添加</button>
								</div>
							</div>							
						</div>
						<table id="supplierInfo" lay-filter="supplierInfo"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- ******************************** 模块供应商添加模态窗体  *********************** -->
	<div id="insertSupplierModal" class="modal">
		<form class="layui-form" id="formInsertSupplier" action="">
			<div class="layui-form-item vehicleId">
				<label class="layui-form-label">车型：</label>
				<div class="layui-input-inline">
					<!-- lay-search 查询 -->
					<select id="insertCarTypeId" name="vehicleId"></select>
				</div>
			</div>
			<div class="layui-form-item configurationLevelId">
				<label class="layui-form-label">配置：</label>
				<div class="layui-input-inline">
					<select id="insertConfigurationId" name="configurationLevelId"></select>
				</div>
			</div>
			<div class="layui-form-item moduleId">
				<label class="layui-form-label">模块：</label>
				<div class="layui-input-inline">
					<select id="insertModuleId" name="moduleId"></select>
				</div>
			</div>
			<div class="layui-form-item supplierId">
				<label class="layui-form-label">供应商：</label>
				<div class="layui-input-inline">
					<select id="insertSupplierId" name="supplierId"></select>
				</div>				
			</div>			
			<div class="layui-form-item">
				<div style="text-align: center;margin-top: 150px;">
					<button class="layui-btn" lay-submit lay-filter="insertSupplier">提交</button>
					<button type="reset" class="layui-btn layui-btn-primary" id="resetAdd">重置</button>
				</div>
			</div>
		</form>
	</div>
	<!-- ******************************** 模块供应商修改模态窗体  *********************** -->
	<div id="updateSupplierModal" class="modal">
		<form class="layui-form" id="formUpdateSupplier" action="">
			<input type="hidden" id="relevanceId" name="relevanceId" />			
			<div class="layui-form-item updateCarTypeId">
				<label class="layui-form-label">车型：</label>
				<div class="layui-input-inline">
					<select id="updateCarTypeId" name="vehicleId"></select>
				</div>
			</div>
			<div class="layui-form-item updateConfigurationId">
				<label class="layui-form-label">配置：</label>
				<div class="layui-input-inline">
					<select id="updateConfigurationId" name="configurationLevelId"></select>
				</div>
			</div>
			<div class="layui-form-item updateModuleId">
				<label class="layui-form-label">模块：</label>
				<div class="layui-input-inline">
					<select id="updateModuleId" name="moduleId"></select>
				</div>
			</div>
			<div class="layui-form-item updateSupplierId">
				<label class="layui-form-label">供应商：</label>
				<div class="layui-input-inline">
					<select id="updateSupplierId" name="supplierId"></select>
				</div>
			</div>
			<div class="layui-form-item">
				<div style="text-align: center;margin-top: 150px;">
					<button class="layui-btn" lay-submit lay-filter="updateSupplier">修改</button>
				</div>
			</div>
		</form>
	</div>
	<!---------------------- 隐藏表单 ----------------------->
	<div style="display: none;">
		<form method="post" id="formImage" action=""
			enctype="multipart/form-data">
			<button type="button" id="uploadImage"></button>
		</form>
	</div>
	<script type="text/javascript" src="${ctx}/Content/js/jquery-3.3.1.min.js"></script>
	<script src="${ctx}/Content/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/combobox.js"></script>
	<!-- 文本编辑器 -->
	
	<!----------------------- 工具条 -------------------------->
	<script type="text/html" id="operation">
		<a class="layui-btn layui-btn-warm btn-style" lay-event="edit">编辑</a>
		<a class="layui-btn layui-btn-danger btn-style" lay-event="del">删除</a>
	</script>

	<script>
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.$;
	 	var layedit = layui.layedit;
	 	var upload = layui.upload;
	 	var path = "${ctx}/fileDir/vehicleImage/";
		$.ajaxSettings.async = false;

		//绑定车型下拉框
		$(function() {
			appendOption("carTypeId", "${ctx}/faultCode/findCarTypeInfo.do");
			appendOption("configurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId=0");
			appendOption("moduleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=0&&vehicleId=0");
			appendOption("supplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0");
			
			//新增供应商模态框
			appendOption("insertCarTypeId", "${ctx}/faultCode/findCarTypeInfo.do");
			appendOption("insertConfigurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId=0");
			appendOption("insertModuleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=0&&vehicleId=0");
			appendOption("insertSupplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0");
			
			//修改供应商模态框
			appendOption("updateCarTypeId", "${ctx}/faultCode/findCarTypeInfo.do");
			appendOption("updateConfigurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId=0");
			appendOption("updateModuleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=0&&vehicleId=0");
			appendOption("updateSupplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0");
			
			SearchTab();
			form.render('select');
		});
		
		//车型下拉框监听事件
		form.on('select(cartype)', function(data){
			if(data.value !=''){
				appendOption("configurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId="+data.value);
				appendOption("moduleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=0&&vehicleId=0");
				appendOption("supplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0&&configurationLevelId=0&&vehicleId=0");
				form.render('select');
			}
		});
		
		//配置下拉框监听事件
		form.on('select(configuration)', function(data){
			if(data.value !=''){
				var vehicleId=$("#carTypeId").val();
				appendOption("moduleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId="+data.value+"&&vehicleId="+vehicleId);
				appendOption("supplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0&&configurationLevelId=0&&vehicleId=0");
				form.render('select');
			}
		});
		
		//模块下拉框监听事件
		form.on('select(module)', function(data){
			if(data.value !=''){
				var vehicleId=$("#carTypeId").val();
				var configurationLevelId=$("#configurationId").val();
				appendOption("supplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId="+data.value+"&&configurationLevelId="+configurationLevelId
						+"&&vehicleId="+vehicleId);
				form.render('select');
			}
		});
		
		//监听工具条
		table.on('tool(supplierInfo)', function(obj) {
			var data = obj.data;
			if (obj.event === 'edit') {//编辑
				loadDatatoForm("formUpdateSupplier",data);		
				var index = layer.open({
					type : 1,
					title : '修改模块供应商信息',
					content : $('#updateSupplierModal'),
					area : [ '440px', '490px' ],
					resize:false,//是否允许拉伸
				});
			} else if (obj.event === 'del') {//删除
				layer.confirm("确定要删除该条数据吗？", {icon : 3,title : '提示',offset : '150px'}, function(index) {
					layer.close(index);
					$.getJSON("${ctx}/supplier/deleteSupplierInfo.do", {
						relevanceId : data.relevanceId
					}, function(e) {
						if (e.text=="success") {
							layer.msg("已删除！", {offset : '150px'});
							SearchTab();
						} else {
							layer.alert("存在有关联的数据，不能删除", {icon : 2,title : '提示',offset : '150px'});
						};
					});
				});
			};
			form.render();//重新渲染form表单
		});
	 	
		//查询模块供应商信息
	 	function SearchTab() {
			var vehicleId = $("#carTypeId").val();
			var configurationLevelId = $("#configurationId").val();
			var moduleId = $("#moduleId").val();
			var supplierId = $("#supplierId").val();
			if(moduleId == null){
				moduleId=0;
			}
			if(supplierId == null){
				supplierId=0;
			}
			table.render({
				elem : '#supplierInfo',
				url : '${ctx}/supplier/findSupplierInfo.do',
				where:{
					vehicleId:vehicleId,configurationLevelId:configurationLevelId,moduleId:moduleId,supplierId:supplierId
				},
				page : true, //开启分页
				limit : 6,
				cols : [ [ //标题栏
					{ type:'numbers',field : '',title : '序号',align:'center'}, 
					{ field : 'cover',title : '车型缩约图',align:'center',width:160,
						templet:function(d){
							if(d.vehiclePicture !=''){
								return '<div class="imgbox"><img src="'+ path + d.vehiclePicture +'" /></div>';
							}else{
								return '<div><span>暂无</span></div>';
							}
						}
					},
					{ field : 'vehicleName',title : '车型',align:'center'}, 
					{ field : 'configurationLevelName',title : '配置等级',align:'center'},
					{ field : 'moduleName',title : '模块',align : 'center'}, 
					{ field : 'supplierName',title : '供应商',align : 'center'}, 
					{ field : 'contacts',title : '联系人',width : 180,align : 'center'}, 
					{ field : '',title : '操作',width : 180,align : 'center',toolbar : '#operation'}
				] ],done:function(res,curr,count){
					$('.layui-laypage-limits').hide();
				}
			});
		}
	 	
	 	//监听车型下拉框(维护车型模态框)
	 	form.on('select(carTypePicture)', function(data){
			if(data.value !="0"){
				$.post("${ctx}/supplier/findCarTypeImageById.do",{carTypeId:data.value},function(data){
					if(data.imagepath !=null){
						$("#showImage").attr("src","${ctx}/fileDir/vehicleImage/" + data.imagepath);
						$("#imagepath").val(data.imagepath);
					}else{
						$("#showImage").attr("src","");
						$("#imagepath").val("");
					}
				});
			}else {
				$("#showImage").attr("src","");
				$("#imagepath").val("");
			}
		});
	 	
	 	//模块供应商添加模态框
	 	function addModalSupplier(){
	 		document.all("resetAdd").click();
			var index = layer.open({
				type : 1,
				title : '模块供应商添加页面',
				content : $('#insertSupplierModal'),
				area : [ '440px', '490px' ],
				resize:false,//是否允许拉伸
			});	
			form.render();
	 	}
	 	
	 	
	 	//表单提交监听——新增
		form.on('submit(insertSupplier)', function(data) {
			if(data.field.vehicleId == "0"){
				layer.tips('请选择车型','.vehicleId');
			} else if(data.field.configurationLevelId == "0"){
				layer.tips('请选择配置','.configurationLevelId');
			} else if(data.field.moduleId == "0"){
				layer.tips('请选择模块','.moduleId');
			} else if(data.field.supplierId == "0"){
				layer.tips('请选择供应商','.supplierId');
			} else{
				$.getJSON("${ctx}/supplier/insertSupplierInfo.do", data.field, function(d) {
					if (d.text=="success") {
						layer.closeAll();
						layer.msg("新增成功", { icon : 1, offset : '150px' });
						document.all("resetAdd").click();
						SearchTab();
					} else {
						layer.alert(d.text, { icon : 2, title : '提示', offset : '150px' });
					}
				});
			}			
			return false;
		});
	 	
	 	
		//表单提交监听——修改
		form.on('submit(updateSupplier)', function(data) {
			if(data.field.vehicleId == "0"){
				layer.tips('请选择车型','.updateCarTypeId');
			} else if(data.field.configurationLevelId == "0"){
				layer.tips('请选择配置','.updateConfigurationId');
			} else if(data.field.moduleId == "0"){
				layer.tips('请选择模块','.updateModuleId');
			} else if(data.field.supplierId == "0"){
				layer.tips('请选择供应商','.updateSupplierId');
			} else{
				$.getJSON("${ctx}/supplier/updateSupplierInfo.do", data.field, function(d) {
					if (d.text=="success") {
						layer.closeAll();
						layer.msg("修改成功", { icon : 1, offset : '150px' });
						SearchTab();
					} else {
						layer.alert(d.text, { icon : 2, title : '提示', offset : '150px' });
					}
				});
			}			
			return false;
		});
	 	
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
