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

<title>My JSP 'faultCodeManage.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" href="${ctx}/Content/layui/css/layui.css"
	type="text/css"></link>
<link rel="stylesheet" href="${ctx}/Content/css/view.css"
	type="text/css"></link>
<link rel="stylesheet" href="${ctx}/Content/css/admin.css"
type="text/css"></link> 

<style type="text/css">
	.modal {
		display: none;
		padding: 16px;
	}
	
	#insertModal .layui-form-label{
		width:100px;
	}
	
	/* 设置表头复选框居中 */
	.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
	   top: 50%;
	   transform: translateY(-50%);
	}
	.layui-form-mid{
		float: right;
	}
	.layui-nav{
		background: #fff;
		color:#fff;
		display: inline;
		font-size: 14px;
		padding-left: 10px;
	}
	.layui-nav .layui-nav-item{
		color:#fff;
		line-height: 30px;
		padding: 0
	}
	.layui-nav-item .layui-nav-more{
		right:10px;
	}
	
	.layui-icon-ok:before{
		content:"\e605";
		color: #fff;
	}
	
	.layui-form-checked[lay-skin=primary] i{
		border-color: #5FB878!important;
    	background-color: #5FB878;
	}
	
	.layui-table-tool{
		text-align: right;
	}
	.layui-table-tool-temp{
		padding-right: 0;
	}
	
	/* 设置文本域样式 */
	.textField{
		width: 100%;
		min-height: 60px;
		max-height: 60px;
		resize: none;
		padding-left: 10px;
		border-color: #e6e6e6;
	}
</style>
</head>

<body class="layui-view-body">
	<div class="layui-content">
		<div class="layui-page-header">
			<div class="pagewrap">
				<span class="layui-breadcrumb"> 
				<a href="javascript:void(0)">首页</a>
				<a href="javascript:void(0)">数据管理</a><a href="javascript:void(0)"><cite>故障码管理</cite></a>
				</span>
				<h2 class="title">故障码管理</h2>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-card">
				<div class="layui-card-body">
					<div class="form-box">
						<div class="layui-form layui-form-item">
							<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
								<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">
									<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
										<div class="layui-form-mid" style="width:100%">车型:&emsp;</div>
									</div>
									<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
										<select id="vehicleId" name="vehicleId" lay-filter="cartype"></select>
									</div>
								</div>
								
								<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">	
									<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
										<div class="layui-form-mid" style="width:100%">配置:&emsp;</div>
									</div>
									<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
										<select id="configurationId" lay-filter="configuration"></select>
									</div>
								</div>	
									
								<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">	
									<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
										<div class="layui-form-mid" style="width:100%">模块:&emsp;</div>
									</div>
									<div class="layui-col-xs7 layui-col-sm7 layui-col-md7" >
										<select id="moduleId" lay-filter="module"></select>
									</div>
								</div>
									
								<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">	
									<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
										<div class="layui-form-mid" style="width:100%">供应商:&emsp;</div>
									</div>
									<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
										<select id="supplierId"></select>
									</div>
								</div>
							
								<div class="layui-col-xs4 layui-col-sm4 layui-col-md4" style="text-align: center;">	
									<div class="btn-group">
										<button class="layui-btn layui-btn-sm layui-btn-normal" onclick="SearchTab()">搜索</button>
										<button class="layui-btn layui-btn-sm" id="addData">添加数据</button>
										<button class="layui-btn layui-btn-sm" id="faultCodeDetail">故障码详情</button>
										<button class="layui-btn layui-btn-sm" onclick="downloadTheTemplate()">下载模板</button>
									</div>
								</div>
							</div>
						</div>
						<table id="faultCodeInfo" lay-filter="faultCodeInfo"></table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- ******************************** 故障码详情页面  *********************** -->
	<div id="faultCodeDetailModal" class="modal">
		<div class="form-box">
			<div class="layui-form layui-form-item">
				<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">
						<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
							<div class="layui-form-mid" style="width:100%">车型:&emsp;</div>
						</div>					
						<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
							<select id="vehicleIds" name="vehicleId" lay-filter="cartypeTwo"></select>
						</div>
					</div>
					
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">
						<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
							<div class="layui-form-mid" style="width:100%">配置:&emsp;</div>
						</div>					
						<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
							<select id="configurationIds" lay-filter="configurationTwo"></select>
						</div>
					</div>
					
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">
						<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
							<div class="layui-form-mid" style="width:100%">模块:&emsp;</div>
						</div>
						<div class="layui-col-xs7 layui-col-sm7 layui-col-md7" >
							<select id="moduleIds" lay-filter="moduleTwo"></select>
						</div>
					</div>
						
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">
						<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
							<div class="layui-form-mid" style="width:100%">供应商:&emsp;</div>
						</div>
						<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
							<select id="supplierIds"></select>
						</div>
					</div>
			
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">
						<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
							<div class="layui-form-mid" style="width:100%">故障码:&emsp;</div>
						</div>
						<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
							<input type="text" id="dtcName" autocomplete="off" class="layui-input" />
						</div>
					</div>
					
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">
						<div class="btn-group">
							<button class="layui-btn layui-btn-sm" onclick="SearchFaultCodeDetail()">查询</button>
						</div>
					</div>
				</div>	
			</div>
			<table id="faultCodeDetailInfo" lay-filter="faultCodeDetailInfo"></table>
		</div>
	</div>

	<!-- ******************************** 查看故障码模态框  *********************** -->
	<div id="viewFaultCodeModal" class="modal">
		<form class="layui-form" id="viewFaultCode" action="">
			<input name="relevanceId" id="relevanceIds" type="hidden" />
			<div class="form-box">
				<div class="layui-form layui-form-item">
					<div class="layui-col-xs10 layui-col-sm10 layui-col-md10">
						<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
							<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
								<div class="layui-form-mid" style="width:100%">车型:&emsp;</div>
							</div>							
							<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
								<input name="vehicleId" id="vehicleIdes" type="hidden" />
								<input type="text" id="vehicleThree" name="vehicleName" autocomplete="off" class="layui-input" readonly="readonly" />
							</div>
						</div>
						
						<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
							<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
								<div class="layui-form-mid" style="width:100%">配置:&emsp;</div>
							</div>						
							<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
								<input name="configurationLevelId" id="configurationLevelIdes" type="hidden" />
								<input type="text" id="configurationThree" name="configurationLevelName" autocomplete="off" class="layui-input" readonly="readonly" />
							</div>
						</div>
						
						<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
							<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
								<div class="layui-form-mid" style="width:100%">模块:&emsp;</div>
							</div>
							<div class="layui-col-xs7 layui-col-sm7 layui-col-md7" >
								<input name="moduleId" id="moduleIdes" type="hidden" />
								<input type="text" id="moduleThree" name="moduleName" autocomplete="off" class="layui-input" readonly="readonly" />
							</div>
						</div>
							
						<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
							<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
								<div class="layui-form-mid" style="width:100%">供应商:&emsp;</div>
							</div>
							<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
								<input name="supplierId" id="supplierIdes" type="hidden" />
								<input name="relevanceId" id="relevanceIdes" type="hidden" />
								<input type="text" id="supplierThree" name="supplierName" autocomplete="off" class="layui-input" readonly="readonly" />
							</div>
						</div>
						
					</div>
				</div>
				<table id="viewFaultCodeInfo" lay-filter="viewFaultCodeInfo"></table>
			</div>
		</form>
	</div>

	<!-- ******************************** 添加数据页面  *********************** -->
	<div id="insertFaultCodeModal" class="modal">
		<form class="layui-form" id="formInsertFaultCode" action="">
			<div class="layui-form layui-form-item">
				<div class="layui-col-xs10 layui-col-sm10 layui-col-md10">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
							<div class="layui-form-mid" style="width:100%">车型:&emsp;</div>
						</div>							
						<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
							<select id="add_vehicleId" name="vehicleId" lay-filter="add_vehicle"></select>
						</div>
					</div>
						
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">				
						<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
							<div class="layui-form-mid" style="width:100%">配置:&emsp;</div>
						</div>					
						<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
							<select id="add_configurationId" lay-filter="add_configuration"></select>
						</div>
					</div>
								
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">		
						<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
							<div class="layui-form-mid" style="width:100%">模块:&emsp;</div>
						</div>
						<div class="layui-col-xs7 layui-col-sm7 layui-col-md7" >
							<select id="add_moduleId" lay-filter="add_module"></select>
						</div>
					</div>			
					
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
							<div class="layui-form-mid" style="width:100%">供应商:&emsp;</div>
						</div>
						<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
							<select id="add_supplierId"></select>
						</div>
					</div>
					
				</div>
			</div>
			<table id="insertInfo" lay-filter="insertInfo"></table>
			<button type="reset" class="layui-btn layui-btn-primary" id="resetFaultCode" style="display: none;" >重置</button>
		</form>	
	</div>

	<!-- ******************************** 故障码新增、修改数据页面  *********************** -->
	<div id="insertModal" class="modal" style="padding: 20px;">
		<form class="layui-form" id="insertFaultForm" action="">
			<input name="faultCodeId" type="hidden" />
			<input name="relevanceId" type="hidden" />
			<input name="state" type="hidden" />
			<input name="typeId" id="typeIds" type="hidden" />
			
			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<label class="layui-form-label">故障码（DTC）</label>
					</div>
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<input type="text" onkeyup="dtckeyUp(1)" name="dtc" required lay-verify="required" maxlength="20"
							autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<label class="layui-form-label">故障码（Hex）</label>
					</div>
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<input type="text" name="hexDtc" required lay-verify="required"
							autocomplete="off" placeholder="输入正确的DTC自动生成" class="layui-input" readonly style="background-color: #eee">
					</div>	
				</div>					
			</div>

			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<label class="layui-form-label">故障码英文描述</label>
					</div>	
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<textarea class="textField" name="chineseDescription" required lay-verify="required"
							autocomplete="off" maxlength="500"></textarea>
					</div>
				</div>
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<label class="layui-form-label">故障码中文描述</label>
					</div>	
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<textarea class="textField" name="englishDescription" required lay-verify="required"
							autocomplete="off" maxlength="500"></textarea>
					</div>	
				</div>	
			</div>
		
			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<label class="layui-form-label">故障码运行条件</label>
					</div>	
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<textarea class="textField" name="operatingConditions" required lay-verify="required"
							autocomplete="off" maxlength="500"></textarea>
					</div>
				</div>
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<label class="layui-form-label">故障码设置条件</label>
					</div>	
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<textarea class="textField" name="settingConditions" required lay-verify="required"
							autocomplete="off" maxlength="500"></textarea>
					</div>
				</div>
			</div>

			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<label class="layui-form-label">故障码设置时发生的操作</label>
					</div>	
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<textarea class="textField" name="settingAfterConditions" required lay-verify="required"
							autocomplete="off" maxlength="500"></textarea>
					</div>
				</div>
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<label class="layui-form-label">故障恢复条件</label>
					</div>
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<textarea class="textField" name="restoreConditions" required lay-verify="required"
							autocomplete="off" maxlength="500"></textarea>
					</div>
				</div>
			</div>			

			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<label class="layui-form-label">点亮故障灯原则</label>
					</div>
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<textarea class="textField" name="activateMilRegulations" required lay-verify="required"
							autocomplete="off" maxlength="500"></textarea>
					</div>
				</div>
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<label class="layui-form-label">熄灭故障灯原则</label>
					</div>	
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<textarea class="textField" name="milOffRegulations" required lay-verify="required"
							autocomplete="off" maxlength="500"></textarea>
					</div>
				</div>
			</div>
			
			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<label class="layui-form-label">清除故障码条件</label>
					</div>	
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<textarea class="textField" name="clearConditions" required lay-verify="required"
							autocomplete="off" maxlength="500"></textarea>
					</div>
				</div>				
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6" style="text-align: center;line-height: 60px;">
					<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
						<button class="layui-btn" lay-submit lay-filter="formFault">保存</button>
						<button type="reset" class="layui-btn layui-btn-primary" id="reset">重置</button>
					</div>
				</div>
			</div>
		</form>
	</div>
	<!---------------------- 隐藏表单 ----------------------->
	<div style="display: none;">
		<form method="post" id="formDll" action=""
			enctype="multipart/form-data">
			<button type="button" id="uploadFile"></button>
		</form>
	</div>
	
	<script type="text/javascript" src="${ctx}/Content/js/jquery-3.3.1.min.js"></script>
	<script src="${ctx}/Content/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/combobox.js"></script>

	<!----------------------- 工具条 -------------------------->
	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-xs" lay-event="sel">查看</a>
		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
	</script>
	<!-- 新增表格头部自定义工具栏 -->
	<script type="text/html" id="insertFaultBar">
      <div class="layui-btn-container">
		<div class="layui-inline" title="新增" lay-event="insert">
 			<i class="layui-icon layui-icon-add-circle"></i>
		</div>
		<div class="layui-inline" title="修改" lay-event="update">
 			<i class="layui-icon layui-icon-edit"></i>
		</div>
		<div class="layui-inline" title="删除" lay-event="delete">
 			<i class="layui-icon layui-icon-delete"></i>
		</div>
		
        <div class="layui-inline" title="导入" lay-event="import">
 			<i class="layui-icon layui-icon-upload-drag"></i>
		</div>
        <div class="layui-inline" title="导出" lay-event="export">
 			<i class="layui-icon layui-icon-download-circle"></i>
		</div>
		<div class="layui-inline saveInfo" title="保存" lay-event="save">
 			<i class="layui-icon layui-icon-file"></i>
		</div>
      </div>
	</script>

	<script>
		$.ajaxSettings.async = false;
		var typeId;
		/*************************** layui *********************************************/
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.$;
		var upload = layui.upload;
		var insertFaultModal;
		var viewFaultCodeModals;
		
		/*************************** 页面初始化  *********************************************/
		$(function() {
			//绑定车型下拉框
			appendOption("vehicleId", "${ctx}/faultCode/findCarTypeInfo.do");
			appendOption("configurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId=0");
			appendOption("moduleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=0&&vehicleId=0");
			appendOption("supplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0");
			SearchTab();//查询数据表格
			SearchFaultCodeDetail();//添加数据页面
			
			//故障码详情
			appendOption("vehicleIds", "${ctx}/faultCode/findCarTypeInfo.do");
			appendOption("configurationIds", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId=0");
			appendOption("moduleIds", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=0&&vehicleId=0");
			appendOption("supplierIds", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0");
			
			form.render('select');//重新渲染表格数据
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
		form.on('select(cartypeTwo)', function(data){
			if(data.value !=''){
				appendOption("configurationIds", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId="+data.value);
				appendOption("moduleIds", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=0&&vehicleId=0");
				appendOption("supplierIds", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0&&configurationLevelId=0&&vehicleId=0");
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
				appendOption("supplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0&&configurationLevelId=0&&vehicleId=0");
				form.render('select');
			}
		});
		form.on('select(configurationTwo)', function(data){
			if(data.value !=''){
				var vehicleId=$("#vehicleIds").val();
				appendOption("moduleIds", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId="+data.value+"&&vehicleId="+vehicleId);
				appendOption("supplierIds", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0&&configurationLevelId=0&&vehicleId=0");
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
		form.on('select(moduleTwo)', function(data){
			if(data.value !=''){
				var vehicleId=$("#vehicleIds").val();
				var configurationLevelId=$("#configurationIds").val();
				appendOption("supplierIds", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId="+data.value+"&&configurationLevelId="+configurationLevelId
						+"&&vehicleId="+vehicleId);
				form.render('select');
			}
		});
		form.on('select(add_module)', function(data){
			if(data.value !=''){
				var vehicleId=$("#add_vehicleId").val();
				var configurationId=$("#add_configurationId").val();
				appendOption("add_supplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId="+data.value+"&&configurationLevelId="+configurationId
						+"&&vehicleId="+vehicleId);
				form.render('select');
			}
		});
		

		/*************************** 表格数据查询方法  *********************************************/
		function SearchTab() {
			var vehicleId = $("#vehicleId").val();
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
				elem : '#faultCodeInfo',
				url : '${ctx}/faultCode/findSupFault.do',
				where : {
					vehicleId:vehicleId,configurationLevelId:configurationLevelId,moduleId:moduleId,supplierId:supplierId
				},
				page : true, //开启分页
				limit : 5,
				limits : [ 5, 10, 15, 20 ],
				cellMinWidth : 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				cols : [ [ //标题栏
					{ type:'numbers',field : '',title : '序号',unresize : true,align : 'center'}, 
					{ field : 'vehicleName',title : '车型',unresize : true,align : 'center'},
					{ field : 'configurationLevelName',title : '配置等级',align:'center'},
					{ field : 'moduleName',title : '模块',align : 'center',unresize : true}, 
					{ field : 'supplierName',title : '供应商',align : 'center',unresize : true}, 
					{ field : '',title : '操作',align : 'center',unresize : true,toolbar : '#barDemo'} 
				] ]
			}); 
		}
		
		//监听工具条
		table.on('tool(faultCodeInfo)', function(obj) {
			var data = obj.data;
			if (obj.event == 'sel') {
				loadDatatoForm("viewFaultCode", data);//回填数据#
				$("#supplierThree").attr("title",data.supplierName);
				findFaultCodeDetailInfo();
				viewFaultCodeModals = layer.open({
					type : 1,
					title : '查看故障码页面',
					content : $('#viewFaultCodeModal'),
					area : [ '88%', '82%' ],
					resize:false,//是否允许拉伸
					cancel : function(index, layero) {
						if (confirm('确定要关闭么，未保存的数据将会丢失！')) { //只有当点击confirm框的确定时，该层才会关闭
							layer.close(viewFaultCodeModals);
							//清空session
							$.getJSON("${ctx}/faultCode/clearFaultCodeSession.do",function(e){});
						}
						return false;
					}
				});
			}else if(obj.event == 'del'){
				layer.confirm('删除后将无法找回，确定要删除该条信息?', {
	            	icon: 3,
	                btn: ['确定', '取消']
	            }, function (index) {		
	            	layer.close(index);
	            	$.post("${ctx}/faultCode/deleteFaultCodeInfoTwo.do",{relevanceId:data.relevanceId},function(e){
						if(e.text=="success"){
							layer.msg("删除成功！");
							SearchTab();
						}else{
							layer.alert(e.text,{icon:2,title:'提示'});
						}
					});
		   		});
			}
		});

		/*************************** 故障码详情页面  *********************************************/
		function SearchFaultCodeDetail() {
			var vehicleId = $("#vehicleIds").val();
			var configurationLevelId=$("#configurationIds").val();
			var moduleId=$("#moduleIds").val();
			var supplierId=$("#supplierIds").val();
			var dtc=$("#dtcName").val();
			table.render({
				elem : '#faultCodeDetailInfo',
				url : '${ctx}/faultCode/findfaultCodeDetailInfo.do',
				where : {
					vehicleId : vehicleId,configurationLevelId:configurationLevelId,
					moduleId:moduleId,supplierId:supplierId,dtc:dtc
				},
				page : true, //开启分页
				limit : 7,
				limits : [ 5, 10, 15, 20 ],
				cellMinWidth : 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				cols : [ [ //标题栏
					{ type:'numbers',title : '序号',unresize : true,align : 'center'}, 
					{ field : 'vehicleName',title : '车型',unresize : true,align : 'center',width:80}, 
					{ field : 'configurationLevelName',title : '配置等级',unresize : true,align : 'center',width:100}, 
					{ field : 'moduleName',title : '模块',align : 'center',unresize : true,width:80}, 
					{ field : 'supplierName',title : '供应商',align : 'center',unresize : true,width:180}, 
					{ field : 'dtc',title : '故障码DTC',align : 'center',unresize : true,width:120}, 
					{ field : 'hexDtc',title : '故障码Hex',align : 'center',unresize : true,width:100}, 
					{ field : 'englishDescription',title : '故障码英文描述',align : 'center',unresize : true,width:180}, 
					{ field : 'chineseDescription',title : '故障码中文描述',align : 'center',unresize : true,width:180}, 
					{ field : 'operatingConditions',title : '故障码运行条件',align : 'center',unresize : true,width:180}, 
					{ field : 'settingConditions',title : '故障码设置条件',align : 'center',unresize : true,width:180}, 
					{ field : 'settingAfterConditions',title : '故障码设置时发生的操作',align : 'center',unresize : true,width:180}, 
					{ field : 'visitorVolume',title : '访问次数',align : 'center',unresize : true,sort: true,width:120}, 
					{ field : 'restoreConditions',title : '故障恢复条件',align : 'center',unresize : true,width:180}, 
					{ field : 'activateMilRegulations',title : '点亮故障灯原则',align : 'center',unresize : true,width:180}, 
					{ field : 'milOffRegulations',title : '熄灭故障灯原则',align : 'center',unresize : true,width:180}, 
					{ field : 'clearConditions',title : '清除故障码条件',align : 'center',unresize : true,width:180} 
				] ],done:function(res,curr,count){
					$(".layui-laypage-limits").hide();//隐藏分页下拉选项
				}
			});
		}
		
		//监听排序事件 
		table.on('sort(faultCodeDetailInfo)', function(obj) { //注：sort 是工具条事件名	
			table.reload('faultCodeDetailInfo', {
				initSort : obj, //记录初始排序，如果不设的话，将无法标记表头的排序状态。
				where : { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
					strName : obj.field, //排序字段
					sortType : obj.type  //排序方式
				}
			});
		});
		
		/*************************** 添加数据页面  *********************************************/
		function SearchFaultCode() {
			var vehicleId = $("#add_vehicleId").val();
			var configurationLevelId = $("#add_configurationId").val();
			var moduleId = $("#add_moduleId").val();
			var supplierId = $("#add_supplierId").val();
			if(moduleId == null){
				moduleId=0;
			}
			if(supplierId == null){
				supplierId=0;
			}
				
			table.render({
				elem : '#insertInfo',
				id:'insertInfo',
				url : '${ctx}/faultCode/findFaultCodeSession.do',
				toolbar: '#insertFaultBar',
				defaultToolbar:null,
				page : true, //不开启分页
				limit : 6,
				cellMinWidth : 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				cols : [ [ //标题栏
					{ type:'numbers',field : '',title : '序号',unresize : true,align : 'center'}, 
					{ field : '',title : '选择',unresize : true,align : 'center',type:'checkbox'}, 
					{ field : 'dtc',title : '故障码DTC',align : 'center',unresize : true,width:110}, 
					{ field : 'hexDtc',title : '故障码Hex',align : 'center',unresize : true,width:100},
					{ field : 'chineseDescription',title : '故障码中文描述',align : 'center',unresize : true,width:145},  
					{ field : 'englishDescription',title : '故障码英文描述',align : 'center',unresize : true,width:145}, 
					{ field : 'operatingConditions',title : '故障码运行条件',align : 'center',unresize : true,width:150}, 
					{ field : 'settingConditions',title : '故障码设置条件',align : 'center',unresize : true,width:150},
					{ field : 'settingAfterConditions',title : '故障码设置时发生的操作',align : 'center',unresize : true,width:185}, 
					{ field : 'restoreConditions',title : '故障恢复条件',align : 'center',unresize : true,width:150}, 
					{ field : 'activateMilRegulations',title : '点亮故障灯原则',align : 'center',unresize : true,width:150}, 
					{ field : 'milOffRegulations',title : '熄灭故障灯原则',align : 'center',unresize : true,width:150}, 
					{ field : 'clearConditions',title : '清除故障码条件',align : 'center',unresize : true,width:150} 
				] ],done:function(res,curr,count){
					$(".layui-laypage-limits").hide();//隐藏分页下拉选项
				}
			});
		}
		
		/*************************** 查看故障码页面  *********************************************/
		function findFaultCodeDetailInfo() {
			var vehicleId=$("#vehicleIdes").val();
			var configurationLevelId=$("#configurationLevelIdes").val();
			var moduleId=$("#moduleIdes").val();
			var supplierId=$("#supplierIdes").val();
			var dtc=null;
			table.render({
				elem : '#viewFaultCodeInfo',
				url : '${ctx}/faultCode/findfaultCodeDetailInfoTwo.do',
				where : {
					vehicleId : vehicleId,configurationLevelId:configurationLevelId,
					moduleId:moduleId,supplierId:supplierId,dtc:dtc
				},
				page : true, //开启分页
				limit : 6,
				limits : [ 5, 10, 15, 20 ],
				toolbar: '#insertFaultBar',
				defaultToolbar:null,
				cellMinWidth : 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				cols : [ [ //标题栏
					{ type:'numbers',title : '序号',unresize : true,align : 'center'}, 
					{ field : '',title : '选择',unresize : true,align : 'center',type:'checkbox'},
					{ field : 'dtc',title : '故障码DTC',align : 'center',unresize : true,width:120}, 
					{ field : 'hexDtc',title : '故障码Hex',align : 'center',unresize : true,width:100}, 
					{ field : 'englishDescription',title : '故障码英文描述',align : 'center',unresize : true,width:180}, 
					{ field : 'chineseDescription',title : '故障码中文描述',align : 'center',unresize : true,width:180}, 
					{ field : 'operatingConditions',title : '故障码运行条件',align : 'center',unresize : true,width:180}, 
					{ field : 'settingConditions',title : '故障码设置条件',align : 'center',unresize : true,width:180}, 
					{ field : 'settingAfterConditions',title : '故障码设置时发生的操作',align : 'center',unresize : true,width:180}, 
					{ field : 'restoreConditions',title : '故障恢复条件',align : 'center',unresize : true,width:180}, 
					{ field : 'activateMilRegulations',title : '点亮故障灯原则',align : 'center',unresize : true,width:180}, 
					{ field : 'milOffRegulations',title : '熄灭故障灯原则',align : 'center',unresize : true,width:180}, 
					{ field : 'clearConditions',title : '清除故障码条件',align : 'center',unresize : true,width:180}, 
					{ field : 'typeId',title : 'typeId',align : 'center',unresize : true,width:180} 
				] ],done:function(res,curr,count){
					$(".layui-laypage-limits").hide();//隐藏分页下拉选项
				}
			});
		}
		
		/*************************** (查看故障码页面)表格头部工具栏监听事件  *********************************************/
		table.on('toolbar(viewFaultCodeInfo)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    var len = checkStatus.data.length;
		    var count = obj.config.page.count;
		    switch(obj.event){
		      case 'insert':
		    	  showInsertModal(1);
    	      	  $("#insertModal input[name='state']").val(2);
		      break;
		      case 'update':
		       	if(len == 0){
		       		layer.msg('请勾选需要进行修改的数据');
		       	}else if(len > 1){
		       		layer.msg('只能选择一条数据进行修改');
		       	}else{
		       		showInsertModal(2);//打开新增模态框
		       		$("#insertModal input[name='state']").val(3);
		       		loadDatatoForm("insertFaultForm", checkStatus.data[0]);//回填数据
		       	}
		      break;
		      case 'delete':
		    	if(len == 0){
		       		layer.msg('请勾选需要删除的数据');
		       	}else{
		       		layer.confirm('真的要删除选中的信息?', {
		            	icon: 3,
		                btn: ['确定', '取消']
		            }, function (index) {		
		            	layer.close(index); 
		            	var num = 0;
		            	var supplierId=$("#supplierIdes").val();
		            	for ( var i = 0; i < len; i++) {
		            		$.ajax({
			                	url: "${ctx}/faultCode/deleteFaultCodeInfo.do?faultCodeId=" + checkStatus.data[i].faultCodeId +"&reId="+checkStatus.data[i].reId+
			                			"&typeId="+checkStatus.data[i].typeId,                    
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
		            	layer.alert(num + "条数据删除成功，"+(len - num) + "条数据删除失败!",{ icon: 1, skin: 'layui-layer-molv', closeBtn: 0, anim: 4 });
		            	//刷新表格
		            	findFaultCodeDetailInfo();
		            	SearchTab();
		            	//获取表格数据的长度
		            	var num=table.cache['viewFaultCodeInfo'].length;
		            	if(num==0){
		            		layer.close(viewFaultCodeModals)
		            	}
			   		});
		       	}
		      break;
		      case 'import':
	       		uploadExcelFile(2);
		      break;
		      case 'export':
		    	  var relevanceId=$("#relevanceIdes").val();
		    	  if(relevanceId>0){
		    		  window.open("${ctx}/faultCode/exportToExcel.do?relevanceId="+relevanceId);
		    	  }
		      break;
		      case 'save':
		    	  insertFaultCodeInfoTwo();
		      break;
		    };
		  });
		
		//添加数据按钮点击事件
		$("#addData").click(function() {
			showFaultCodeModal();
		});
		
		//故障码详情按钮点击事件
		$("#faultCodeDetail").click(function() {
			showFaultCodeDetailModal();
		});

		/*************************** 打开故障码详情页面窗体  *********************************************/
		function showFaultCodeDetailModal() {
			document.all('reset').click();
			var index = layer.open({
				type : 1,
				title : '故障码详情页面',
				content : $('#faultCodeDetailModal'),
				area : [ '88%', '82%' ],
				resize:false,//是否允许拉伸
				cancel : function(index, layero) {
					layer.close(index);
				}
			});
		}

		/*************************** 打开添加数据页面窗体  *********************************************/
		function showFaultCodeModal() {
			//绑定车型下拉框
			appendOption("add_vehicleId", "${ctx}/faultCode/findCarTypeInfo.do");
			document.all('resetFaultCode').click();
			layer.open({
				type : 1,
				title : '添加数据页面',
				content : $('#insertFaultCodeModal'),
				area : [ '88%', '82%' ],
				resize:false,//是否允许拉伸
				cancel : function(index, layero) {
					if (confirm('确定要关闭么，未保存的数据将会丢失！')) { //只有当点击confirm框的确定时，该层才会关闭
						layer.close(index);
					}
					return false;
				}
			});
			SearchFaultCode();
		}
		
		/*************************** 打开故障码新增数据页面窗体  *********************************************/
		function showInsertModal(num) {
			var titleName="";
			document.all('reset').click();
			if(num==1){
				titleName="故障码新增数据页面";
			}else{
				titleName="故障码修改数据页面";
			}
			insertFaultModal = layer.open({
				type : 1,
				title : titleName,
				content : $('#insertModal'),
				area : [ '1000px', '500px' ],
				resize:false,//是否允许拉伸
				cancel : function(index, layero) {
					if (confirm('确定要关闭么，未保存的数据将会丢失！')) { //只有当点击confirm框的确定时，该层才会关闭
						layer.close(index);
					}
					return false;
				}
			});
		}
		
		
		/*************************** (添加数据)表格头部工具栏监听事件  *********************************************/
		table.on('toolbar(insertInfo)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    var len = checkStatus.data.length;
		    var count = obj.config.page.count;
		    switch(obj.event){
		      case 'insert':
		    	  showInsertModal(1);
    	      	  $("#insertModal input[name='state']").val(0);
		      break;
		      case 'update':
		       	if(len == 0){
		       		layer.msg('请勾选需要进行修改的数据');
		       	}else if(len > 1){
		       		layer.msg('只能选择一条数据进行修改');
		       	}else{
		       		showInsertModal(2);//打开新增模态框
		       		loadDatatoForm("insertModal", checkStatus.data[0]);//回填数据#
	    	      	$("#insertModal input[name='state']").val(1);
		       	}
		      break;
		      case 'delete':
		    	if(len == 0){
		       		layer.msg('请勾选需要删除的数据');
		       	}else{
		       		layer.confirm('真的要删除选中的信息?', {
		            	icon: 3,
		                btn: ['确定', '取消']
		            }, function (index) {		
		            	layer.close(index); 
		            	var num = 0;	                
		            	for ( var i = 0; i < len; i++) {
		            		$.ajax({
			                	url: "${ctx}/faultCode/deleteFaultCodeSession.do?faultCodeId=" + checkStatus.data[i].faultCodeId,                    
			                	type: "post",//数据传输通道的类
			                    async: false,
			                    dataType: "json",//传输的数据的类型
			                    success: function (datas) {//直接理解为回调函数
			                 		if (datas==200) {
			                 			num++;	//删除成功
			                        }
			                    }
			                });
		            	}
		            	layer.alert(num + "条数据删除成功，"+(len - num) + "条数据删除失败!",{ icon: 1, skin: 'layui-layer-molv', closeBtn: 0, anim: 4 });
		            	//刷新表格
		            	SearchFaultCode();
			   		});
		       	}
		      break;
		      case 'import':
	       		uploadExcelFile(1);
		      break;
		      case 'export':
		    	  var num=table.cache['insertInfo'].length;
		    	  if(num==0){
		    		  layer.msg('暂无可导出的数据');
		    	  }else{
		    		  layer.confirm('确定要导出当前的数据？', {
		              	icon: 3,
		                  btn: ['确定', '取消']
		              }, function (index) {		
		              	layer.close(index); 
		              	window.open("${ctx}/faultCode/exportToExcel.do?relevanceId=0");
		  	   		});
		    	  }
		      break;
		      case 'save':
		    	var num=table.cache['insertInfo'].length;
		    	if(num == 0){
		       		layer.msg('暂无需要保存的数据');
		       	}else{
		       		//表单提交监听——新增
		       		insertFaultCodeInfo();
		       	}
		      break;
		    };
		});
		
		/*************************** 单条故障码新增  *********************************************/
		form.on('submit(formFault)', function(data){
			var state = $("#insertModal input[name='state']").val();
			if(state==0){
				//新增----添加数据模态框
				data.field.typeId=null;
				$.post("${ctx}/faultCode/insertFaultCode.do",data.field,function(e){
					if(e==200){
						layer.close(insertFaultModal);
						layer.msg("新增成功！");
						SearchFaultCode();
					}else if(e==500){
						layer.alert("该故障码已存在！",{icon:0,title:'提示'});
					}
					else{
						layer.alert("数据异常！",{icon:2,title:'提示'});
					}
				});
			}else if(state==1){
				//修改----添加数据模态框
				data.field.typeId=null;
				$.post("${ctx}/faultCode/updateSessionFaultCode.do",data.field,function(e){
					if(e==200){
						layer.close(insertFaultModal);
						layer.msg("已修改！");
						SearchFaultCode();
					}else if(e==500){
						layer.alert("该故障码已存在！",{icon:0,title:'提示'});
					}else{
						layer.alert("数据异常！",{icon:2,title:'提示'});
					}
				});
			}else if(state==2){
				//新增----查看故障码模态框
				data.field.typeId=null;
				data.field.relevanceId=$("#relevanceIds").val();
				$.post("${ctx}/faultCode/addFaultCodeInfo.do",data.field,function(e){
					if(e.text=="success"){
						layer.close(insertFaultModal);
						layer.msg("新增成功！");
						findFaultCodeDetailInfo();
					}else{
						layer.alert(e.text,{icon:2,title:'提示'});
					}
				});
			}else if(state==3){
				//修改----查看故障码模态框
				data.field.relevanceId=$("#relevanceIds").val();
				$.post("${ctx}/faultCode/updateFaultCodeInfo.do",data.field,function(e){
					if(e.text=="success"){
						layer.close(insertFaultModal);
						layer.msg("修改成功！");
						findFaultCodeDetailInfo();
					}else{
						layer.alert(e.text,{icon:2,title:'提示'});
					}
				});
			}
			return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
		});
		
		//检查输入的故障码是否正确
		 var HEXarray = ["B0", "B1", "B2", "B3", "C0", "C1", "C2", "C3", "P0", "P1", "P2", "P3", "U0", "U1", "U2", "U3"];
		 function dtckeyUp(key) {
            var dtcVaule = null, HEX = null, DTC = null;
            if (key == 1) {
                HEX = $("#insertFaultForm [name = hexDtc]");
                DTC = $("#insertFaultForm [name = dtc]");
                dtcVaule = DTC.val().toString();
                if (dtcVaule.length == 0) HEX.val("");
                dtcbol = true;
            }
            else {
                HEX = $("#formUpateFaultCode [name = hexDtc]");
                DTC = $("#formUpateFaultCode [name = dtc]");
                dtcVaule = DTC.val().toString();
                if (dtcVaule.length == 0) HEX.val("");
                dtcbol = true;
            }
            if (dtcVaule.length == 2) {
                var dtcHEX = dtcVaule.toString().substr(0, dtcVaule.length).toUpperCase();
                HEX.val(dtcChange(dtcHEX));
            }else if (dtcVaule.length < 2){
            	HEX.val("");
            }
            else if (dtcVaule.length > 2) {
                var ZhuanHuanDTC = dtcVaule.toString().substr(0, 2).toUpperCase();
                var index = HEXarray.indexOf(ZhuanHuanDTC);
                if (index > -1) {
                    var lens = dtcVaule.length - 2;
                    var endHex = dtcVaule.toString().substr(2, lens);
                    HEX.val(dtcChange(ZhuanHuanDTC));
                    var hexVaule = HEX.val() + endHex;
                    HEX.val(hexVaule);
                } else {
                    dtcbol = false;
                    emp = "";
                    layer.msg("请输入正确故障码DTC  !", { icon: 0, offset: '220px' });
                    $("#insertFaultForm [name = hexDtc]").val("");
                }
            }
        }
		 
		 //验证DTC
		function dtcChange(DTC) {
            if (DTC == "B0") {
                emp = "8"
            }
            else if (DTC == "B1") {
                emp = "9"
            }
            else if (DTC == "B2") {
                emp = "A"
            }
            else if (DTC == "B3") {
                emp = "B"
            }
            else if (DTC == "C0") {
                emp = "4"
            }
            else if (DTC == "C1") {
                emp = "5"
            }
            else if (DTC == "C2") {
                emp = "6"
            }
            else if (DTC == "C3") {
                emp = "7"
            }
            else if (DTC == "P0") {
                emp = "0"
            }
            else if (DTC == "P1") {
                emp = "1"
            }
            else if (DTC == "P2") {
                emp = "2"
            }
            else if (DTC == "P3") {
                emp = "3"
            }
            else if (DTC == "U0") {
                emp = "C"
            }
            else if (DTC == "U1") {
                emp = "D"
            }
            else if (DTC == "U2") {
                emp = "E"
            }
            else if (DTC == "U3") {
                emp = "F"
            }
            else {
                dtcbol = false;
                emp = "";
                layer.msg("请输入正确故障码DTC !", { icon: 0, offset: '220px' });
                $("#insertFaultForm [name = hexDtc]").val("");
            }
            return emp;
        }
		
		 //下载模板
		function downloadTheTemplate(){
			layer.confirm('确定要下载吗？', {
            	icon: 3,
                btn: ['确定', '取消']
            }, function (index) {		
            	layer.close(index); 
            	window.open("${ctx}/faultCode/downloadTemplate.do");
	   		});
		}
		 
		//新增故障码信息(添加数据)
		function insertFaultCodeInfo(){
			var vehicleId=$("#add_vehicleId").val();
			var configurationLevelId=$("#add_configurationId").val();
			var moduleId=$("#add_moduleId").val();
			var supplierId=$("#add_supplierId").val();
			if(vehicleId >0 && configurationLevelId>0 && moduleId>0 && supplierId>0){
				var typeIds=1;
				$.getJSON("${ctx}/faultCode/insertFaultCodeInfo.do", {vehicleId:vehicleId,configurationLevelId:configurationLevelId
					,moduleId:moduleId,supplierId:supplierId,typeId:typeIds}, function(d) {
					if (d.code==200) {
						layer.closeAll();
						layer.msg(d.text, { icon:1, offset : '150px' });
						document.all("resetFaultCode").click();
						SearchTab();
						
						//清空session
						$.getJSON("${ctx}/faultCode/clearFaultCodeSession.do",function(e){});
					} else {
						layer.alert(d.text, { icon : 2, title : '提示', offset : '150px' });
					}
				}); 
			}else{
				layer.msg("请选择模块供应商信息");
			}
		}
		
		//新增故障码信息(查看故障码保存按钮)
		function insertFaultCodeInfoTwo(){
			var vehicleId=$("#vehicleIdes").val();
			var configurationLevelId=$("#configurationLevelIdes").val();
			var moduleId=$("#moduleIdes").val();
			var supplierId=$("#supplierIdes").val();
			var typeIds=2;
			$.getJSON("${ctx}/faultCode/insertFaultCodeInfo.do", {vehicleId:vehicleId,configurationLevelId:configurationLevelId
				,moduleId:moduleId,supplierId:supplierId,typeId:typeIds}, function(d) {
				if (d.code==200) {
					layer.msg(d.text, { icon:1, offset : '150px' });
					//清空session
					$.getJSON("${ctx}/faultCode/clearFaultCodeSession.do",function(e){});
					findFaultCodeDetailInfo();
					SearchTab();
				} else {
					layer.alert(d.text, { icon : 2, title : '提示', offset : '150px' });
				}
			}); 
		}
		
		//上传Excel文件(添加数据)
		function uploadExcelFile(id){
			typeId=id;
			$("#uploadFile").click();
		}
		
		//监听文件上传
		 upload.render({
			elem : '#formDll',
			url : '${ctx}/faultCode/importExcelFileTwo.do',
			accept: 'file', //普通文件
			data: {
				typeId: function(){
				    return typeId;
				  }
				},
		    exts: 'xls|xlsx|XLS', //允许上传的文件后缀
			field:"uploadFile", //默认文件域是file,也可以自己定义,这个和后台struts中获取文件名有关
			before:function(obj){
				onload();
			},
			done : function(res) {
				if (res.code ==200) {
					return layer.msg(res.text);
				} else if(res.code ==201){
					layer.alert(res.text,{ icon: 1, skin: 'layui-layer-molv', closeBtn: 0, anim: 4 });
				}else{
					layer.alert(res.text, {icon : 2,title : '提示',offset : '150px'});
				}
				if(typeId==1){
					SearchFaultCode();
				}else{
					findFaultCodeDetailInfo();
				}
				onclose();
			}
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
			layer.closeAll('loading');
		}
	</script>
</body>

</html>
