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

<title>My JSP 'ecuManage.jsp' starting page</title>

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
		padding: 16px;
	}
		
	.layui-icon-ok:before{
		content:"\e605";
		color: #fff;
	}
	
	.layui-form-checked[lay-skin=primary] i{
		border-color: #5FB878!important;
    	background-color: #5FB878;
	}
	
	.layui-form-item .layui-form-checkbox[lay-skin=primary]{
		margin-top: 0px !important;
	}
	
	.modal {
		display: none;
	}
	
	#insertModal .layui-form-label{
		width:100px;
	}

	.layui-form-mid{
		float: right;
	}

	#addDidForm .layui-form-mid{
		text-align: center;
	}
	
	.noborder{
		display:inline-block;
		border:none;
		height: 38px;
    	line-height: 38px;
    	color:red;
    	width: auto;
    	padding: 0 30px 0 0;
	}
	
	#updateSupplierModal .layui-input-inline{
		width: 250px;
	}
	
	#updateSupplierModal .layui-form-select dl {
		max-height: 190px;
	}
</style>
</head>

<body class="layui-view-body">
	<div class="layui-content">
		<div class="layui-page-header" style="margin-bottom: 15px;">
			<div class="pagewrap">
				<span class="layui-breadcrumb"> <a href="javascript:void(0)">首页</a>
					<a href="javascript:void(0)">通知分享管理</a>
					<a href="javascript:void(0)">DID转换工具</a>
				</span>
				<h2 class="title">DID转换工具</h2>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-card">
				<div class="layui-card-body">
					<div class="layui-form layui-form-item" style="margin: 5px 0 10px">
						<div class="layui-inline">
							<div class="layui-col-xs10 layui-col-sm10 layui-col-md10">
								<div class="layui-col-xs1 layui-col-sm1 layui-col-md1">
									<div class="layui-form-mid">车型:</div>
								</div>
									
								<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">
									<select id="vehicleId" name="vehicleId" lay-filter="cartype"></select>
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
									<select id="supplierId"></select>
								</div>
							</div>
							<div class="btn-group layui-col-xs2 layui-col-sm2 layui-col-md2">
								<button class="layui-btn layui-btn-sm" onclick="searchDidConversionInfo()">搜索</button>
								<button id="showWareMod" class="layui-btn layui-btn-sm layui-btn-blue">DID库</button>
							</div>		
						</div>
						<table id="didContent" lay-filter="didContent"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 新增DID内容 -->
	<div id="insertDidContentMod" class="modal">
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
									<select id="add_vehicleId" name="vehicleId" lay-filter="add_vehicle"></select>
								</div>
								
								<div class="layui-col-xs1 layui-col-sm1 layui-col-md1">
									<div class="layui-form-mid">配置:</div>
								</div>
								
								<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">
									<select id="add_configurationId" name="configurationLevelId" lay-filter="add_configuration"></select>
								</div>
								
								<div class="layui-col-xs1 layui-col-sm1 layui-col-md1">
									<div class="layui-form-mid">模块:</div>
								</div>
								<div class="layui-col-xs2 layui-col-sm2 layui-col-md2" >
									<select id="add_moduleId" name="moduleId" lay-filter="add_module"></select>
								</div>
								
								<div class="layui-col-xs1 layui-col-sm1 layui-col-md1">
									<div class="layui-form-mid">供应商:</div>
								</div>
								<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">
									<select id="add_supplierId" name="supplierId"></select>
								</div>
							</div>
							<div class="layui-col-xs4 layui-col-sm4 layui-col-md4 btn-group">
								<button class="layui-btn layui-btn-sm" id="importDID">导入DID信息</button>
								<button class="layui-btn layui-btn-sm" id="DidContentSave">保存</button>
							</div>	
						</div>
						<table id="insertDidContent" lay-filter="insertDidContent"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- ******************************** DID仓库 *********************** -->
	<div id="didWarehouseMod" class="modal">
		<div class="layui-form layui-form-item" style="margin: 5px 0 10px;">
			<div class="layui-form-mid box-hide" style="float: left;">DID名称:</div>
			<div class="layui-input-inline box-hide">
				<input type="text" id="didName" autocomplete="off" placeholder="DID名称"
					 class="layui-input" />
			</div>
			<div class="btn-group">
				<button class="layui-btn layui-btn-sm layui-btn-normal box-hide" type="button" onclick="SearchDidTab(1)">查询</button>
				<button class="layui-btn layui-btn-sm" type="button" id="exportDidBtn" style="display: none;">导出DID</button>
			</div>
			<input name="stateId" id="stateId" type="hidden" />
		</div>
		<table id="didWarehouse" lay-filter="didWarehouse"></table>
	</div>
	
	<!-- ******************************** 查看DID列表信息  *********************** -->
	<div id="viewDidListModal" class="modal">
		<form class="layui-form" id="viewDidList" action="">
			<input name="relevanceId" id="relevanceIds" type="hidden" />
			<div class="layui-form layui-form-item">
				<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
					<div style="float: left;">
						<div class="layui-form-mid" style="float: left;">车型:&emsp;</div>
						<input name="vehicleId" id="vehicleIdes" type="hidden" />
						<span id="vehicleThree" name="vehicleName" class="noborder"></span>
					</div>
					
					<div style="float: left;">
						<div class="layui-form-mid" style="float: left;">配置:&emsp;</div>
						<input name="configurationLevelId" id="configurationLevelIdes" type="hidden" />
						<span id="configurationThree" name="configurationLevelName" class="noborder"></span>
					</div>
					
					<div style="float: left;">
						<div class="layui-form-mid" style="float: left;">模块:&emsp;</div>
						<input name="moduleId" id="moduleIdes" type="hidden" />
						<span id="moduleThree" name="moduleName" class="noborder"></span>
					</div>
						
					<div style="float: left;">
						<div class="layui-form-mid" style="float: left;">供应商:&emsp;</div>
						<input name="relevanceId" id="relevanceIdes" type="hidden" />
						<span id="supplierThree" name="supplierName" class="noborder"></span>
					</div>
			
					<div style="float:left;margin-left: 30px;">
						<input type="text" id="searchDidName" autocomplete="off" onkeyup="searchDidListInfo()" placeholder="搜索 ..." class="layui-input" />
					</div>
				</div>
			</div>
			<table id="viewDidListInfo" lay-filter="viewDidListInfo"></table>
		</form>
	</div>
	
	<!-- 新增DID -->
	<div id="addDidWarehouseMod" class="modal">
		<form class="layui-form" id="addDidForm" action="">
			<input type="hidden" name="didConversionId" value="0">
			<div class="layui-form-item">			
				<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
					<label class="layui-form-mid">DID(hex)：</label>
				</div>
				
				<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
					<input type="text" name="didName" lay-verify="noCN" autocomplete="off" class="layui-input" maxlength="20">
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
					<label class="layui-form-mid">DID Description：</label>
				</div>
				<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
					<input type="text" name="didDescription" lay-verify="required" autocomplete="off" class="layui-input" maxlength="100">
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
					<label class="layui-form-mid">Data Type：</label>
				</div>
				<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
					<select name="didType" lay-verify="required">
						<option value="">----请选择----</option>
						<option value="HEX">HEX</option>
						<option value="ASCII">ASCII</option>
						<option value="BCD">BCD</option>
					</select> 
				</div>
			</div>
			
			<div style="text-align:center;padding-top: 120px;">
				<button class="layui-btn" lay-submit lay-filter="addDidSub">提交</button>
				<button type="reset" class="layui-btn layui-btn-primary" id="resetAdd">重置</button>
			</div>
		</form>
	</div>
	<!-- ******************************** 模块供应商修改模态窗体  *********************** -->
	<div id="updateSupplierModal" class="modal">
		<form class="layui-form" id="formUpdateSupplier" action="">
			<input type="hidden" id="relevanceIdes" name="relevanceId" />			
			<div class="layui-form-item">
				<label class="layui-form-label">车型：</label>
				<div class="layui-input-inline updateCarTypeId">
					<select id="updateCarTypeId" name="vehicleId" lay-filter="cartypes"></select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">配置：</label>
				<div class="layui-input-inline updateConfigurationId">
					<select id="updateConfigurationId" name="configurationLevelId" lay-filter="configurations"></select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">模块：</label>
				<div class="layui-input-inline updateModuleId">
					<select id="updateModuleId" name="moduleId" lay-filter="modules"></select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">供应商：</label>
				<div class="layui-input-inline updateSupplierId">
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
	
	<!-- 新增表格头部自定义工具栏 -->
	<script type="text/html" id="DidBar">
      <div class="layui-btn-container">
		<div class="layui-inline" title="新增" lay-event="insert">
 			<i class="layui-icon layui-icon-add-circle"></i>
		</div>
		<div class="layui-inline edits" title="编辑" lay-event="update">
 			<i class="layui-icon layui-icon-edit"></i>
		</div>
		<div class="layui-inline" title="删除" lay-event="delete">
 			<i class="layui-icon layui-icon-delete"></i>
		</div>
      </div>
	</script>
	<!-- 新增表格头部自定义工具栏(查看DID列表信息) -->
	<script type="text/html" id="DidListBar">
      <div class="layui-btn-container">
		<div class="layui-inline" title="导入DID信息" lay-event="insert">
 			<i class="layui-icon layui-icon-add-circle"></i>
		</div>
		<div class="layui-inline" title="删除" lay-event="delete">
 			<i class="layui-icon layui-icon-delete"></i>
		</div>
      </div>
	</script>
	<!-- 新增did信息自定义表格 -->
	<script type="text/html" id="addDidBar">
		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
	</script>
	<!----------------------- 工具条 -------------------------->
	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn layui-btn-xs" lay-event="sel">查看</a>
	</script>
	<script type="text/javascript" src="${ctx}/Content/js/jquery-3.3.1.min.js"></script>
	<script src="${ctx}/Content/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/combobox.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/echoforms.js"></script>
	<script>
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.$;
		var addWareMod;//新增did仓库弹窗
		var insertDid;//新增Did弹窗
		var WareMod;//DID仓管弹窗
		var viewDidListModals;
		$.ajaxSettings.async = false;
		
		//自动加载事件
		$(function() {
			appendOption("add_vehicleId", "${ctx}/faultCode/findCarTypeInfo.do");//绑定车型下拉框
			appendOption("vehicleId", "${ctx}/faultCode/findCarTypeInfo.do");//绑定车型下拉框
			searchDidConversionInfo();//查询DID转换信息
			
			//修改模块供应商模态框
			appendOption("updateCarTypeId", "${ctx}/faultCode/findCarTypeInfo.do");
			appendOption("updateConfigurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId=0");
			appendOption("updateModuleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=0&&vehicleId=0");
			appendOption("updateSupplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0");
			form.render('select');//重新渲染表格数据
		});
		
		//查询DID转换信息
		function searchDidConversionInfo(){
			var vehicleId=$("#vehicleId").val();
			var configurationLevelId=$("#configurationId").val();
			var moduleId=$("#moduleId").val();
			var supplierId=$("#supplierId").val();
			table.render({
				elem : '#didContent',
				url : '${ctx}/share/findDidConversionInfo.do',
				where:{
					vehicleId:vehicleId,configurationLevelId:configurationLevelId,moduleId:moduleId,supplierId:supplierId
				},
				toolbar: '#DidBar',
				defaultToolbar:null,
				page : true, //开启分页
				limit : 10,
				cols : [ [ //标题栏
					{ field : 'number',type:'numbers',field : '',title : '序号',align:'center'}, 
					{ field : 'cover',title : '选择',align:'center',type :'checkbox'}, 
					{ field : 'vehicleName',title : '车型',align : 'center',unresize : true}, 
					{ field : 'configurationLevelName',title : '配置',align : 'center',unresize : true},
					{ field : 'moduleName',title : '模块',align : 'center',unresize : true},
					{ field : 'supplierName',title : '供应商',align : 'center',unresize : true},
					{ field : '',title : '操作',align : 'center',unresize : true,toolbar : '#barDemo',width:120} 
				]]
			});
		}
		
		//监听工具条
		table.on('tool(didContent)', function(obj) {
			var data = obj.data;
			if (obj.event == 'sel') {
				loadDatatoForm("viewDidList", data);//回填数据
				$("#vehicleThree").html(data.vehicleName);
				$("#configurationThree").html(data.configurationLevelName);
				$("#moduleThree").html(data.moduleName);
				$("#supplierThree").html(data.supplierName);
				viewDidListModals = layer.open({
					type : 1,
					title : '查看DID列表信息',
					content : $('#viewDidListModal'),
					area : [ '88%', '82%' ],
					resize:false,//是否允许拉伸
				});
				searchDidListInfo();
			}
		});
		
		//查询DiD列表信息
		function searchDidListInfo(){
			var didName=$("#searchDidName").val();
			var relevanceId=$("#relevanceIdes").val();
			table.render({
				elem : '#viewDidListInfo',
				url : '${ctx}/share/findDidListInfo.do',
				where:{
					relevanceId:relevanceId,didName:didName
				},
				toolbar: '#DidListBar',
				defaultToolbar:null,
				page : true, //开启分页
				limit : 10,
				cellMinWidth : 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				cols : [ [ //标题栏
					{ type:'numbers',field : '',title : '序号',align:'center'}, 
					{ field : 'cover',title : '选择',align:'center',type :'checkbox'}, 
					{ field : 'didName',title : 'DID(hex)',align : 'center',unresize : true}, 
					{ field : 'didDescription',title : 'DID Description',align : 'center',unresize : true},
					{ field : 'didType',title : 'DID Type',align : 'center',unresize : true}
				] ],done:function(res,curr,count){
					$('.layui-laypage-limits').hide();//隐藏分页选项下拉框
					$('table').css("width","100%");
				}
			});
	 	}
		
		/**************************************************** DID 部分  **********************************************************************/	
		
		/************************ 下拉框 ************************/
		
		//车型下拉框监听事件
		form.on('select(cartype)', function(data){
			if(data.value !=''){
				appendOption("configurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId="+data.value);
				form.render('select');
			}
		});
		form.on('select(add_vehicle)', function(data){
			if(data.value !=''){
				appendOption("add_configurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId="+data.value);
				form.render('select');
			}
		});
		
		//配置下拉框监听事件
		form.on('select(configuration)', function(data){
			if(data.value !=''){
				var vehicleId=$("#vehicleId").val();
				appendOption("moduleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId="+data.value+"&&vehicleId="+vehicleId);
				form.render('select');
			}
		});
		form.on('select(add_configuration)', function(data){
			if(data.value !=''){
				var vehicleId=$("#add_vehicleId").val();
				appendOption("add_moduleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId="+data.value+"&&vehicleId="+vehicleId);
				form.render('select');
			}
		});
		
		//模块下拉框监听事件
		form.on('select(module)', function(data){
			if(data.value !=''){
				var vehicleId=$("#vehicleId").val();
				var configurationLevelId=$("#configurationId").val();
				appendOption("supplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId="+data.value+"&&configurationLevelId="+configurationLevelId
						+"&&vehicleId="+vehicleId);
				form.render('select');
			}
		});
		form.on('select(add_module)', function(data){
			if(data.value !=''){
				var vehicleId=$("#add_vehicleId").val();
				var configurationLevelId=$("#add_configurationId").val();
				appendOption("add_supplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId="+data.value+"&&configurationLevelId="+configurationLevelId
						+"&&vehicleId="+vehicleId);
				form.render('select');
			}
		});
		
		//------------------------------------修改模块供应商部分-------------------------------------
		//车型下拉框监听事件
		form.on('select(cartypes)', function(data){
			if(data.value !=''){
				appendOption("updateConfigurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId="+data.value);
				appendOption("updateModuleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=0&&vehicleId=0");
				appendOption("updateSupplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0&&configurationLevelId=0&&vehicleId=0");
				form.render('select');
			}
		});
		
		//配置下拉框监听事件
		form.on('select(configurations)', function(data){
			if(data.value !=''){
				var vehicleId=$("#updateCarTypeId").val();
				appendOption("updateModuleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId="+data.value+"&&vehicleId="+vehicleId);
				appendOption("updateSupplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0&&configurationLevelId=0&&vehicleId=0");
				form.render('select');
			}
		});
		
		//模块下拉框监听事件
		form.on('select(modules)', function(data){
			if(data.value !=''){
				var vehicleId=$("#updateCarTypeId").val();
				var configurationLevelId=$("#updateConfigurationId").val();
				appendOption("updateSupplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId="+data.value+"&&configurationLevelId="+configurationLevelId
						+"&&vehicleId="+vehicleId);
				form.render('select');
			}
		});
		/************************ 弹出层 ************************/
		
		//打开DID仓库模态窗
		$("#showWareMod").click(function(){
			showWareModal(1);
		});
		
		function showWareModal(typeId){
			WareMod = layer.open({
				type : 1,
				title : 'DID列表',
				content : $('#didWarehouseMod'),
				area : [ '950px', '550px' ],
				resize:false,//是否允许拉伸
			});
			$("#didName").val("");//清空模糊查询
			if(typeId==1){
				$(".box-hide").css("display","block");
				$("#exportDidBtn").css("display","none");//隐藏导出按钮
			}else{
				$(".box-hide").css("display","none");
				$("#exportDidBtn").css("display","inline-block");//显示导出按钮
			}
			SearchDidTab(typeId);
		}
		
		//打开新增did
		function showAddDidWareMod(){
			addWareMod = layer.open({
				type : 1,
				title : 'DID列表',
				content : $('#addDidWarehouseMod'),
				area : [ '500px', '400px' ],
				resize:false,//是否允许拉伸
			});
			$("#resetAdd").click();//清空表单
		}
		//新增DID窗体  
		function showInsertDidModal() {
			insertDid = layer.open({
				type : 1,
				title : '新增',
				content : $('#insertDidContentMod'),
				area : [ '90%', '80%' ],
				cancel : function(index, layero) {
					layer.close(index);
				}
			});
			$("#insertDidContentMod select").val(0);
			//清除session
			$.get("${ctx}/share/cleanSession.do",function(){});
			//查询Session
			searchDidSession();
			form.render('select');
		}
		
		/************************ 表格 ************************/
		
		//查询DID库表格
		function SearchDidTab(typeId){
			var didName = $("#didName").val();
			table.render({
				elem : '#didWarehouse',
				url : '${ctx}/share/findDIDContent.do',
				where:{
					didName:didName,typeId:typeId
				},
				page : true, //开启分页
				limit : 5,
				toolbar: '#DidBar',
				defaultToolbar:null,
				cols : [ [ //标题栏
					{ type:'numbers',field : '',title : '序号',align:'center',width:80}, 
					{ field : '',title : '选择',align:'center',type :'checkbox',width:50}, 
					{ field : 'didConversionId',title : 'DID ID',align : 'center',unresize : true,width:120,hide:true}, 
					{ field : 'didName',title : 'DID(hex)',align : 'center',unresize : true,width:120}, 
					{ field : 'didDescription',title : 'DID Description',align : 'center',unresize : true},
					{ field : 'didType',title : 'Data Type',align : 'center',unresize : true,width:120}
				] ],done:function(res,curr,count){
					$('.layui-laypage-limits').hide();//隐藏分页选项下拉框
				}
			});
		}
		
		//didSession表格
		function searchDidSession(){
			table.render({
				elem : '#insertDidContent',
				url : '${ctx}/share/selectDidSession.do',
				page : true, //开启分页
				limit : 2,
				cols : [ [ //标题栏
					{ type:'numbers',field : '',title : '序号',align:'center',width:80}, 
					{ field : '',title : '选择',align:'center',type :'checkbox',width:50}, 
					{ field : 'didConversionId',title : 'DID ID',align : 'center',unresize : true,hide:true}, 
					{ field : 'didName',title : 'DID(hex)',align : 'center',unresize : true,width:200}, 
					{ field : 'didDescription',title : 'DID Description',align : 'center',unresize : true},
					{ field : 'didType',title : 'Data Type',align : 'center',unresize : true,width:200},
					{ fixed: 'right',title : '操作',align : 'center',width:80,unresize : true, toolbar: '#addDidBar'} //这里的toolbar值是模板元素的选择器
				] ],done:function(res,curr,count){
					$('.layui-laypage-limits').hide();//隐藏分页选项下拉框
				}
			});
		}
		
		/************************ 表格监听 ************************/
		
		//DID仓库表格头部工具栏监听事件
		table.on('toolbar(didWarehouse)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    var len = checkStatus.data.length;
		    var count = obj.config.page.count;
		    switch(obj.event){
		      case 'insert':
		    	  showAddDidWareMod();
		       	  $("#addDidForm input[name='didConversionId']").val(0);
		      break;
		      case 'update':
		       	if(len == 0){
		       		layer.msg('请勾选需要进行修改的数据');
		       	}else if(len > 1){
		       		layer.msg('只能选择一条数据进行修改');
		       	}else{
		       		showAddDidWareMod();//打开新增模态框
		       		loadDatatoForm("addDidForm", checkStatus.data[0]);//回填数据#
		       	}
		      break;
		      case 'delete':
		    	if(len == 0){
		       		layer.msg('请勾选需要删除的数据');
		       	}else{
		       		layer.confirm("确定要删除"+len+"条数据吗？",{icon: 3, title:'提示',offset:'150px'},function(index){
		       			layer.close(index);
		       			var ids = "";
			       		for(var i=0;i<checkStatus.data.length;i++){
			       			ids = ids+checkStatus.data[i].didConversionId+",";
			       		}
			       		$.post("${ctx}/share/delDIDContent.do",{ids:ids},function(e){
			       			if(e.code==200){
								layer.msg("已删除！",{icon:1,offset:'150px'});
								SearchDidTab();
							}else{
								layer.alert("数据异常！",{icon:2,offset:'150px'});
							}
			       		});
		       		});
		       		
		       	}
		      break;
		    };
		 });
	
		//DID列表表格头部工具栏监听事件
		table.on('toolbar(didContent)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    var len = checkStatus.data.length;
		    var count = obj.config.page.count;
		    switch(obj.event){
		      case 'insert':
		    	  showInsertDidModal();//打开新增弹窗
		      break;
		      case 'update':
		    	  showUpdateDidModal();//打开修改弹窗
		      break;
		      case 'delete':
		    	if(len == 0){
		       		layer.msg('请勾选需要删除的数据');
		       	}else{
		       		layer.confirm("确定要删除"+len+"条数据吗？",{icon: 3, title:'提示',offset:'150px'},function(index){
		       			layer.close(index);
		       			var ids = "";
			       		for(var i=0;i<checkStatus.data.length;i++){
			       			ids = ids+checkStatus.data[i].relevanceId+",";
			       		}
			       		$.post("${ctx}/share/deleteDidInfoById.do",{ids:ids},function(e){
			       			if(e.text=="success"){
								layer.msg("已删除！",{icon:1,offset:'150px'});
								searchDidConversionInfo();
							}else{
								layer.alert(e.text,{icon:2,offset:'150px'});
							}
			       		});
		       		});
		       		
		       	}
		      break;
		     
		    };
		 });
		
		//DIDSession列表表格操作按钮监听事件
		table.on('tool(insertDidContent)', function(obj){
			var data = obj.data;
			if (obj.event === 'del') {
				layer.confirm("确定要删除该条数据吗？", {
					icon : 3,
					title : '提示',
					offset : '150px'
				}, function(index) {
					layer.close(index);
					$.getJSON("${ctx}/share/delDidSession.do", {
						didConversionId : data.didConversionId
					}, function(e) {
						if (e) {
							layer.msg("已删除！", {
								icon:1,
								offset : '150px'
							});
							searchDidSession();
						} else {
							layer.alert("操作失败", {
								icon : 2,
								title : '提示',
								offset : '150px'
							});
						}
					});
				});
			}
		 });
		
		//DID列表信息表格头部工具栏监听事件
		table.on('toolbar(viewDidListInfo)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    var len = checkStatus.data.length;
		    var count = obj.config.page.count;
		    switch(obj.event){
		      case 'insert':
		    	  $("#stateId").val(2);
		    	  showWareModal(3);//打开DID仓库弹窗
		      break;
		      case 'delete':
		    	if(len == 0){
		       		layer.msg('请勾选需要删除的数据');
		       	}else{
		       		layer.confirm("确定要删除"+len+"条数据吗？",{icon: 3, title:'提示',offset:'150px'},function(index){
		       			layer.close(index);
		       			var ids = "";
			       		for(var i=0;i<checkStatus.data.length;i++){
			       			ids = ids+checkStatus.data[i].reDidRelevanceId+",";
			       		}
			       		$.post("${ctx}/share/deleteDidConversionInfo.do",{ids:ids},function(e){
			       			if(e.text=="success"){
								layer.msg("已删除！",{icon:1,offset:'150px'});
								searchDidListInfo();
							}else{
								layer.alert("数据异常！",{icon:2,offset:'150px'});
							}
			       		});
		       		});
		       	}
		      break;
		    };
		});
		
		/************************ 表单提交监听 ************************/
		//新增单条DID
		form.on('submit(addDidSub)', function(data) {
			var field = data.field;
			if(field.didConversionId!=0){
				//修改
				$.post("${ctx}/share/findDidByNameType.do",{didConversionId:field.didConversionId,didName:field.didName,didType:field.didType},function(e){
					if(e.code==200){
						//提交 
						$.post("${ctx}/share/updateDIDContent.do",{didConversionId:field.didConversionId,didName:field.didName,didDescription:field.didDescription,didType:field.didType},function(msg){
							if(msg.code==200){
								layer.msg("已保存！",{icon:1,offset:'150px'});
								layer.close(addWareMod);
								SearchDidTab(1);
							}else{
								layer.alert("数据异常！",{icon:2,offset:'150px'});
							}
						});
					}else{
						layer.msg("数据已存在",{icon:0,offset:'150px'});
					}
				}); 
			}else{
				//新增
				 $.post("${ctx}/share/findDidByNameType.do",{didName:field.didName,didType:field.didType},function(e){
					if(e.code==200){
						//提交 
						$.post("${ctx}/share/insertDIDContent.do",{didName:field.didName,didDescription:field.didDescription,didType:field.didType},function(msg){
							if(msg.code==200){
								layer.msg("已保存！",{icon:1,offset:'150px'});
								layer.close(addWareMod);
								SearchDidTab(1);
							}else{
								layer.alert("数据异常！",{icon:2,offset:'150px'});
							}
						});
					}else{
						layer.msg("数据已存在",{icon:0,offset:'150px'});
					}
				}); 
			}
			
			return false;
		});
		
		/************************ 其他 ************************/
		
		//导入DID信息
		$("#importDID").click(function(){
			$("#stateId").val(1);
			showWareModal(2);//打开DID
		});
		//导出DID信息
		$("#exportDidBtn").click(function(){
			var stateId=$("#stateId").val();
			var data = table.checkStatus('didWarehouse').data;
			var len = table.checkStatus('didWarehouse').data.length//获取选中行数量，可作为是否有选中行的条件
			if(len==0){
				layer.msg("请选择需要导出的数据",{icon:0,title:'提示',offset:'150px'});
			}else{
				if(stateId==1){
					$.post("${ctx}/share/saveDidSession.do",{dids:JSON.stringify(data)},function(e){
						if(e){
							layer.close(WareMod);
							searchDidSession();
						}
					});
				}else if(stateId==2){
					var relevanceId=$("#relevanceIdes").val();
					$.post("${ctx}/share/insertDidConversionInfo.do",{dids:JSON.stringify(data)
						,relevanceId:relevanceId},function(e){
						if(e.text=="success"){
							layer.msg("导入成功！",{icon:1,offset:'150px'});
						}else{
							layer.msg(e.text,{icon:1,offset:'150px'});
						}
						layer.close(WareMod);
						searchDidListInfo();
					});
				}
			}
		});
		
		//新增DID转换信息
		$("#DidContentSave").click(function(){
			var vehicleId = $("#add_vehicleId").val();
			var configurationLevelId = $("#add_configurationId").val();
			var moduleId = $("#add_moduleId").val();
			var supplierId = $("#add_supplierId").val();
			var num=table.cache['insertDidContent'].length;
			if(num>0){
				if(vehicleId >0 && configurationLevelId>0 && moduleId>0 && supplierId>0){
					$.post("${ctx}/share/saveDidConversionInfo.do",{vehicleId:vehicleId,configurationLevelId:configurationLevelId
						,moduleId:moduleId,supplierId:supplierId},function(e){
						if(e.text=="success"){
							layer.msg("保存成功！",{icon:1,offset:'150px'});
							layer.close(insertDid);
							searchDidConversionInfo();
						}else{
							layer.msg(e.text,{icon:1,offset:'150px'});
						}
					});
				}else{
					layer.msg("请选择模块供应商信息");
				}
			}else{
				layer.msg("暂无需要保存的数据");
			}
		});
		 
        //表单验证（自定义）
		form.verify({
			noCN:[
				/^[A-Za-z0-9]+$/,
				'只能输入英文字母和数字哦'
			]
		});
        
		//修改DID窗体  
		function showUpdateDidModal() {
			var checkStatus = table.checkStatus('didContent')//获取选中行数据
	      	,data = checkStatus.data;
			if(data.length==0){
		    	layer.alert("请选择需要修改的数据", {icon : 0,title : '提示',offset : '150px'});
		    }else if(data.length>1){
		    	layer.alert("只能选择一条数据进行修改", {icon : 0,title : '提示',offset : '150px'});
		    }else{
		    	loadDatatoForm("formUpdateSupplier", data[0]);//回填信息
		    	var updateDid = layer.open({
					type : 1,
					title : '修改模块供应商信息',
					content : $('#updateSupplierModal'),
					area : [ '440px', '480px' ],
					cancel : function(index, layero) {
						layer.close(index);
					}
				});
				form.render('select');
		    }
		}
		
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
				$.getJSON("${ctx}/share/updateDidSupplierInfo.do", data.field, function(d) {
					if (d.text=="success") {
						layer.closeAll();
						layer.msg("修改成功", { icon : 1, offset : '150px' });
						searchDidConversionInfo();
					} else {
						layer.alert(d.text, { icon : 2, title : '提示', offset : '150px' });
					}
				});
			}			
			return false;
		});
	</script>
</body>

</html>
