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

<title>My JSP 'nodeOnline.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" href="${ctx}/Content/layui/css/layui.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/Content/css/view.css" type="text/css"></link>

<style type="text/css">
	/* 复选框的样式 */
	.layui-table-view .layui-form-checkbox {
		top: 5px !important;
	}
	
	.layui-icon-ok:before{
		content:"\e605";
		color: #fff;
	}
	
	.layui-form-checked[lay-skin=primary] i{
		border-color: #5FB878!important;
    	background-color: #5FB878;
	}
	
	.modal {
		display: none;
		padding:20px;
	}
	
	/* 拓扑图div的样式 */
	.topological-graph{
		border: 1px solid #cccccc;
		border-radius: 4px;
		overflow:scroll;		
		height: 600px;
		width:100%;
	}
	
	/* 修改label的宽度 */
	#insertNodeModal .layui-form-label{
		width:100px;
	}
	
	/* 修改下拉框的高度 */
	#insertNodeModal .layui-form-select dl {
		max-height: 190px;
	}
	
	/* 修改label的宽度 */
	#updateNodeModal .layui-form-label{
		width:100px;
	}
	
	/* 修改下拉框的高度 */
	#updateNodeModal .layui-form-select dl {
		max-height: 190px;
	}
	
	#insertAllocationModal .layui-form-label{
		width:100px;
	}
	
	#insertAllocationModal .layui-form-select dl {
		max-height: 190px;
	}
	
	#updateAllocationModal .layui-form-label{
		width:100px;
	}
	
	#updateAllocationModal .layui-form-select dl {
		max-height: 190px;
	}
	
	/* 禁止刷新后出现横向滚动条 */
	body{
		overflow-y: scroll;		
	} 
	
</style>
</head>

<body class="layui-view-body">
	<div class="layui-content" style="padding:20px;">
		<div class="layui-page-header" style="margin-bottom: 15px;">
			<div class="pagewrap">
				<span class="layui-breadcrumb"> <a href="javascript:void(0)">首页</a>
				<a href="javascript:void(0)">数据管理</a><a href="javascript:void(0)"><cite>节点在线管理</cite></a>
				</span>
				<h2 class="title">节点在线管理</h2>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-card">
				<div class="layui-card-body">
					<div class="form-box">
						<div class="layui-form layui-form-item" style="margin: 5px 0 10px">
							<div class="layui-inline">
								<div class="layui-form-mid">选择车型:</div>
								<div class="layui-input-inline" style="width: 190px;">
									<select id="vehicleId" lay-filter="cartype"></select>
								</div>
								<div class="layui-form-mid" style="margin-left: 20px;">选择配置:</div>
								<div class="layui-input-inline" style="width: 190px;">
									<select id="configurationId"></select>
								</div>
								<div class="btn-group">
									<button class="layui-btn layui-btn-sm layui-btn-blue" type="button" onclick="SearchNodeInfo()">查询</button>
									<button class="layui-btn layui-btn-sm" type="button" onclick="addNodeData()">节点配置</button>
									<button class="layui-btn layui-btn-sm" type="button" onclick="addAllocation()">添加配置</button>
									<button class="layui-btn layui-btn-sm layui-btn-blue" type="button" id="topologyDiagrams">生成拓扑图</button>
								</div>
							</div>
						</div>
						<!-- 节点配置页面 -->
						<table id="nodeInfo" lay-filter="nodeInfo"></table>
					
						<!-- 拓扑图工具栏 -->						
						<div id="content" class="content" style="text-align: center;"></div>
						<!-- 拓扑图显示 -->	
						<div class="topological-graph" id="canvas-box">
							<!-- 画布的宽和高一定要在canvas标签中设置，如果是在css样式中设置，则不起作用，画布会按照默认尺寸width：300px，height：150px显示 -->
							<%-- <canvas width="3000" height="1200" id="canvas"> --%>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- ******************************** 拓扑图详细信息模态窗体  *********************** -->
	<div id="topologicalDetailModal" class="modal">
		<form class="layui-form" id="topologicalDetailForm" action="">			
			<table id="topologicalDetail" lay-filter="topologicalDetail"></table>
		</form>
	</div>
	
	<!-- ******************************** 节点配置--模态窗体  *********************** -->
	<div id="nodeDataModal" class="modal">
		<form class="layui-form" id="nodeDataForm" action="">
			<button type="reset" class="layui-btn layui-btn-primary" id="resetBox" style="display: none;">重置</button>
			<div class="layui-form-item">
				<div class="layui-form-mid">选择车型：</div>
				<div class="layui-input-inline">
					<select id="add_vehicleId" lay-filter="add_vehicle"></select>
				</div>
				<label class="layui-form-label">选择配置：</label>
				<div class="layui-input-inline">
					<select id="add_configurationId" lay-filter="add_configuration"></select>
				</div>
				<div class="btn-group">
					<button class="layui-btn layui-btn-sm" type="button" onclick="addNodeDetailData()">新增</button>
					<button class="layui-btn layui-btn-sm layui-btn-warm" type="button" id="editNodeDetailData">修改</button>
					<button class="layui-btn layui-btn-sm layui-btn-danger" type="button" id="delNodeDetailData">删除</button>
					<button class="layui-btn layui-btn-sm layui-btn-blue" type="button" id="saveNodeDetailData">保存</button>
				</div>
			</div>
			<table id="nodeDataInfo" lay-filter="nodeDataInfo"></table>
		</form>
	</div>
	<!-- ******************************** 节点配置--新增模态窗体  *********************** -->
	<div id="insertNodeModal" class="modal">
		<form class="layui-form" id="insertNodeForm" action="">
			<input name="vehicleId" type="hidden" />
			<input name="configurationLevelId" type="hidden" />
			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
					<label class="layui-form-label">选择通道：</label>
				</div>
				<div class="layui-col-xs5 layui-col-sm5 layui-col-md5 add_canConfigurationId">
					<select id="add_canConfigurationId" name="canConfigurationId"></select>
				</div>
			</div>
			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
					<label class="layui-form-label">选择线束段：</label>
				</div>
				<div class="layui-col-xs5 layui-col-sm5 layui-col-md5 add_harness">
					<select id="add_harness" name="harness"></select>
				</div>
			</div>
			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
					<label class="layui-form-label">选择模块：</label>
				</div>	
				<div class="layui-col-xs5 layui-col-sm5 layui-col-md5 add_moduleId">
					<select id="add_moduleId" name="moduleId" lay-filter="add_module"></select>
				</div>
			</div>
			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
					<label class="layui-form-label">选择供应商：</label>
				</div>	
				<div class="layui-col-xs5 layui-col-sm5 layui-col-md5 add_supplierId">
					<select id="add_supplierId" name="supplierId"></select>
				</div>
			</div>
			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
					<label class="layui-form-label">输入CANID：</label>
				</div>
				<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
					<input type="text" name="txId" placeholder="请输入TxId"
						lay-verify="required" autocomplete="off" class="layui-input" />
				</div>
				<div class="layui-col-xs1 layui-col-sm1 layui-col-md1">&emsp;</div>				
				<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
					<input type="text" name="rxId" placeholder="请输入RxId"
						lay-verify="required" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-form-item" style="text-align:center;padding-top: 15%;">
				<button class="layui-btn" lay-submit lay-filter="insertNodeDetail">新增</button>
				<button type="reset" class="layui-btn layui-btn-primary" id="resetNodeDetail">重置</button>
			</div>
		</form>
	</div>
	<!-- ******************************** 节点配置--修改模态窗体  *********************** -->
	<div id="updateNodeModal" class="modal">
		<form class="layui-form" id="updateNodeForm" action="">
			<!-- <input name="vehicleId" type="hidden" />
			<input name="configurationLevelId" type="hidden" /> -->
			<input name="nodeId" id="nodeId" type="hidden" />
			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
					<label class="layui-form-label">选择车型：</label>
				</div>
				<div class="layui-col-xs5 layui-col-sm5 layui-col-md5 edit_vehicleId">
					<select id="edit_vehicleId" name="vehicleId" lay-filter="edit_vehicleId"></select>
				</div>
			</div>
			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
					<label class="layui-form-label">选择配置：</label>
				</div>
				<div class="layui-col-xs5 layui-col-sm5 layui-col-md5 edit_configurationId">
					<select id="edit_configurationId" name="configurationLevelId" lay-filter="edit_configurationId"></select>
				</div>
			</div>
			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
					<label class="layui-form-label">选择通道：</label>
				</div>
				<div class="layui-col-xs5 layui-col-sm5 layui-col-md5 edit_canConfigurationId">
					<select id="edit_canConfigurationId" name="canConfigurationId"></select>
				</div>
			</div>
			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
					<label class="layui-form-label">选择线束段：</label>
				</div>
				<div class="layui-col-xs5 layui-col-sm5 layui-col-md5 edit_harness">
					<select id="edit_harness" name="harness"></select>
				</div>
			</div>
			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
					<label class="layui-form-label">选择模块：</label>
				</div>	
				<div class="layui-col-xs5 layui-col-sm5 layui-col-md5 edit_moduleId">
					<select id="edit_moduleId" name="moduleId" lay-filter="edit_moduleId"></select>
				</div>
			</div>
			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
					<label class="layui-form-label">选择供应商：</label>
				</div>	
				<div class="layui-col-xs5 layui-col-sm5 layui-col-md5 edit_supplierId">
					<select id="edit_supplierId" name="supplierId"></select>
				</div>
			</div>
			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
					<label class="layui-form-label">输入CANID：</label>
				</div>
				<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
					<input type="text" name="txId" placeholder="请输入TxId"
						lay-verify="required" autocomplete="off" class="layui-input" />
				</div>
				<div class="layui-col-xs1 layui-col-sm1 layui-col-md1">&emsp;</div>				
				<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
					<input type="text" name="rxId" placeholder="请输入RxId"
						lay-verify="required" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-form-item" style="text-align:center;padding-top: 15%;">
				<button class="layui-btn" lay-submit lay-filter="updateNodeDetail" id="updateNodeDetail" style="display: none">修改</button>
				<button class="layui-btn" lay-submit lay-filter="editNodeDetail" id="editNodeDetail" style="display: none">保存</button>
			</div>
		</form>
		<span style="color:red">
			<marquee style="color:red;" scrollamount=5;>温馨提示：由于车型供应商等数据之间存在级联关系，如需更改配置等信息，请先重新选择一下车型，即从上往下操作！</marquee>
		</span>
	</div>
	<!-- ******************************** 配置页面  *********************** -->
	<div id="allocationModal" class="modal">
		<div class="layui-form layui-form-item" style="margin: 5px 0 10px">
			<div class="layui-inline">
				<div class="layui-form-mid">选择车型:</div>
				<div class="layui-input-inline" style="width: 190px;">
					<select id="harnessVehicleId" lay-filter="harnessVehicleId"></select>
				</div>
				<div class="layui-form-mid" style="margin-left: 20px;">选择配置:</div>
				<div class="layui-input-inline" style="width: 190px;">
					<select id="harnessConfigurationId"></select>
				</div>
				<div class="btn-group">
					<button class="layui-btn layui-btn-sm layui-btn-blue" type="button" onclick="searchWiringHarnessInfo()">查询</button>
					<button class="layui-btn layui-btn-sm" type="button" onclick="insertAllocation()">新增</button>
					<button class="layui-btn layui-btn-sm layui-btn-warm" type="button" id="updateAllocation">修改</button>
					<button class="layui-btn layui-btn-sm layui-btn-danger" type="button" id="deleteAllocation">删除</button>
				</div>
			</div>
		</div>
		<!-- 线束段配置页面 -->
		<table id="wiringHarnessInfo" lay-filter="wiringHarnessInfo"></table>
	</div>
	<!-- ******************************** 配置页面，新增模态框  *********************** -->
	<div id="insertAllocationModal" class="modal">
		<form class="layui-form" id="insertAllocationForm" action="">
			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 vehicleId">
					<div class="layui-col-xs5 layui-col-sm5 layui-col-md5">
						<label class="layui-form-label">选择车型：</label>
					</div>
					<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
						<select id="addVehicleId" name="vehicleId"></select>
					</div>
				</div>
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 configurationLevelId">
					<div class="layui-col-xs5 layui-col-sm5 layui-col-md5">
						<label class="layui-form-label">添加配置：</label>
					</div>
					<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
						<select id="addConfigurationId" name="configurationLevelId"></select>
					</div>	
				</div>					
			</div>

			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 canConfigurationId">
					<div class="layui-col-xs5 layui-col-sm5 layui-col-md5">
						<label class="layui-form-label">添加通道：</label>
					</div>	
					<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
						<select id="addCanConfigurationId" name="canConfigurationId"></select>
					</div>
				</div>
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs5 layui-col-sm5 layui-col-md5">
						<label class="layui-form-label">添加线束段：</label>
					</div>	
					<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
						<input type="text" name="harness" required lay-verify="required" maxlength="20"
							autocomplete="off" class="layui-input" onkeyup="this.value=this.value.replace(/[^\w]/ig,'')">
					</div>	
				</div>	
			</div>

			<div class="layui-form-item" style="text-align:center;padding-top: 25%;">
				<button class="layui-btn" lay-submit lay-filter="saveAllocation">新增</button>
				<button type="reset" class="layui-btn layui-btn-primary" id="resetAllocation">重置</button>
			</div>
		</form>
	</div>
	<!-- ******************************** 配置页面，修改模态框  *********************** -->
	<div id="updateAllocationModal" class="modal">
		<form class="layui-form" id="updateAllocationForm" action="">
			<div style="display: none">
				 <input type="hidden" id="harnessConfigurationId" name="harnessConfigurationId" />
			</div>
			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 vehicleId">
					<div class="layui-col-xs5 layui-col-sm5 layui-col-md5">
						<label class="layui-form-label">选择车型：</label>
					</div>
					<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
						<select id="editVehicleId" name="vehicleId"></select>
					</div>
				</div>
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 configurationLevelId">
					<div class="layui-col-xs5 layui-col-sm5 layui-col-md5">
						<label class="layui-form-label">添加配置：</label>
					</div>
					<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
						<select id="editConfigurationId" name="configurationLevelId"></select>
					</div>	
				</div>					
			</div>

			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 canConfigurationId">
					<div class="layui-col-xs5 layui-col-sm5 layui-col-md5">
						<label class="layui-form-label">添加通道：</label>
					</div>	
					<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
						<select id="editCanConfigurationId" name="canConfigurationId"></select>
					</div>
				</div>
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs5 layui-col-sm5 layui-col-md5">
						<label class="layui-form-label">添加线束段：</label>
					</div>	
					<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
						<input type="text" name="harness" required lay-verify="required" maxlength="20"
							autocomplete="off" class="layui-input" onkeyup="this.value=this.value.replace(/[^\w]/ig,'')">
					</div>	
				</div>	
			</div>

			<div class="layui-form-item" style="text-align:center;padding-top: 25%;">
				<button class="layui-btn" lay-submit lay-filter="editAllocation">修改</button>
			</div>
		</form>
	</div>
	
	<script type="text/javascript" src="${ctx}/Content/js/jquery-3.3.1.min.js"></script>
	<script src="${ctx}/Content/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/combobox.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/jtopo-0.4.8-min.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/toolbar.js"></script>
	
	<!----------------------- 工具条 -------------------------->
	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="detail">详情</a>
		<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="deldetail">删除</a>
	</script>
	
	<script type="text/html" id="barDetail">
		<a class="layui-btn layui-btn-xs layui-btn-warm" lay-event="edit">修改</a>
		<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
	</script>

	<script>
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.$;
		$.ajaxSettings.async = false;
		var insertAllocationModal;//添加配置模态框--新增模态框，用于关闭弹窗
	 	var updateAllocationModal;//添加配置模态框--修改模态框，用于关闭弹窗
	 	var insertNodeModal;//节点配置模态框--新增模态框，用于关闭弹窗
	 	var updateNodeModal;//节点配置模态框--修改模态框，用于关闭弹窗
	 	var nodeDataModal;//节点配置模态框--用于关闭弹窗

		//页面加载事件
		$(function() {
			//绑定车型下拉框
			appendOption("vehicleId", "${ctx}/faultCode/findCarTypeInfo.do");
			appendOption("configurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId=0");
			
			SearchNodeInfo();//查询节点数据信息
			
			form.render('select');//重新渲染下拉框
		});
		
		//绑定线束段配置模态框下拉框
	 	function bindingWiringHarnessBox(){
			appendOption("harnessVehicleId", "${ctx}/faultCode/findCarTypeInfo.do");
			appendOption("harnessConfigurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId=0");
		}
		
		
		//绑定线束段配置新增模态框下拉框
	 	function bindingHarnessAddBox(){
			appendOption("addVehicleId", "${ctx}/faultCode/findCarTypeInfo.do");
			appendOption("addConfigurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId=0");
			appendOption("addCanConfigurationId", "${ctx}/dataManage/findCanBoxInfo.do");
		}
		
	 	//绑定线束段配置修改模态框下拉框
	 	function bindingHarnessEditBox(){
			appendOption("editVehicleId", "${ctx}/faultCode/findCarTypeInfo.do");
			appendOption("editConfigurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId=0");
			appendOption("editCanConfigurationId", "${ctx}/dataManage/findCanBoxInfo.do");
		}
	 	
		
		//主页面车型下拉框监听事件
		form.on('select(cartype)', function(data){
			if(data.value !=''){
				appendOption("configurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId="+data.value);
				form.render('select');
			}
		});
		
		//节点配置模态框的车型下拉框监听事件
		form.on('select(add_vehicle)', function(data){
			if(data.value !=''){
				appendOption("add_configurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId="+data.value);
				form.render('select');
			}
		});
		
		//节点配置模态框的配置下拉框监听事件
	 	form.on('select(add_configuration)', function(data){
			if(data.value !=''){
				var vehicleId=$("#add_vehicleId").val();
				appendOption("add_moduleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId="+data.value+"&&vehicleId="+vehicleId);
				form.render('select');
			}
		});
		
	 	//节点配置模态框的模块下拉框监听事件
	 	form.on('select(add_module)', function(data){
			if(data.value !=''){
				var vehicleId=$("#add_vehicleId").val();
				var configurationId=$("#add_configurationId").val();
				appendOption("add_supplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId="+data.value+"&&configurationLevelId="+configurationId
						+"&&vehicleId="+vehicleId);
				form.render('select');
			}
		});
	 	
	 	//绑定节点配置模态框--新增模态框下拉框
	 	function bindingInsertNodeDetailBox(){
	 		var vehicleId=$("#add_vehicleId").val();
			var configurationId=$("#add_configurationId").val();
	 		
	 		appendOption("add_canConfigurationId", "${ctx}/dataManage/findCanPassageBoxInfo.do?vehicleId="+vehicleId+"&&configurationId="+configurationId);
			appendOptionName("add_harness", "${ctx}/dataManage/findHarnessBoxInfo.do?vehicleId="+vehicleId+"&&configurationId="+configurationId);
		}
	 	
	 	
	 	//绑定节点模态框--修改模态框下拉框
	 	function bindingUpdateNodeDetailBox(){
	 		appendOption("edit_vehicleId", "${ctx}/faultCode/findCarTypeInfo.do");
	 		appendOption("edit_configurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId=0");
	 		appendOption("edit_moduleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=0&&vehicleId=0");
	 		appendOption("edit_supplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0&&configurationLevelId=0&&vehicleId=0");
	 		
	 		var vehicleId=$("#edit_vehicleId").val();
			var configurationId=$("#edit_configurationId").val();	 		
	 		appendOption("edit_canConfigurationId", "${ctx}/dataManage/findCanPassageBoxInfo.do?vehicleId="+vehicleId+"&&configurationId="+configurationId);
			appendOptionName("edit_harness", "${ctx}/dataManage/findHarnessBoxInfo.do?vehicleId="+vehicleId+"&&configurationId="+configurationId);
		}
	 	
	 	//节点配置模态框的车型下拉框监听事件
		form.on('select(edit_vehicleId)', function(data){
			if(data.value !=''){
				appendOption("edit_configurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId="+data.value);
				form.render('select');
			}
		});
		
		//节点配置模态框的配置下拉框监听事件
	 	form.on('select(edit_configurationId)', function(data){
			if(data.value !=''){
				var vehicleId=$("#edit_vehicleId").val();
				appendOption("edit_moduleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId="+data.value+"&&vehicleId="+vehicleId);
				form.render('select');
			}
		});
		
	 	//节点配置模态框的模块下拉框监听事件
	 	form.on('select(edit_moduleId)', function(data){
			if(data.value !=''){
				var vehicleId=$("#edit_vehicleId").val();
				var configurationId=$("#edit_configurationId").val();
				appendOption("edit_supplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId="+data.value+"&&configurationLevelId="+configurationId
						+"&&vehicleId="+vehicleId);
				form.render('select');
			}
		});
	 		 		 	
	 	
		//查询节点数据信息
	 	function SearchNodeInfo() {
	 		var vehicleId = $("#vehicleId").val();
			var configurationLevelId = $("#configurationId").val();
			table.render({
				elem : '#nodeInfo',
				url : '${ctx}/dataManage/findNodeInfo.do',
				where:{
					vehicleId:vehicleId,configurationLevelId:configurationLevelId
				},
				page : true, //开启分页
				limit : 5,
				cols : [ [ //标题栏
					{field : '',title : '序号',type:'numbers',align:'center'},		
					{field : '',title : '选择',type :'checkbox',align:'center'},
					{field : 'vehicleName',title : '车型',align:'center',unresize : true}, 
					{field : 'configurationLevelName',title : '配置',align : 'center',unresize : true},
					{field : '',title : '操作',align : 'center',unresize : true,toolbar : '#barDemo'}
				] ],
				done:function(e){
					$(".layui-laypage-limits").hide();//隐藏分页下拉选项
				}
			});
		}
	 	
		
	 	//查询线束段配置信息
	 	function searchWiringHarnessInfo() {
	 		var vehicleId = $("#harnessVehicleId").val();
			var configurationLevelId = $("#harnessConfigurationId").val();
			table.render({
				elem : '#wiringHarnessInfo',
				url : '${ctx}/dataManage/findWiringHarnessInfo.do',
				where:{
					vehicleId:vehicleId,configurationLevelId:configurationLevelId
				},
				page : true, //开启分页
				limit : 5,
				cols : [ [ //标题栏
					{field : '',title : '序号',type:'numbers',align:'center'},
					{field : '',title : '选择',type :'checkbox',align:'center'},
					{field : 'vehicleName',title : '车型',align:'center',unresize : true}, 
					{field : 'configurationLevelName',title : '配置',align : 'center',unresize : true}, 
					{field : 'canPassage',title : '网段名称',align : 'center',unresize : true}, 
					{field : 'harness',title : '线束段',align : 'center',unresize : true}
				] ],
				done:function(e){
					$(".layui-laypage-limits").hide();//隐藏分页下拉选项
				}
			});
		}
		
	 	//查询拓扑图详细信息
	 	function searchTopologicalDetail(vehicleId) {
			table.render({
				elem : '#topologicalDetail',
				url : '${ctx}/dataManage/findNodeDetailInfo.do',
				where:{
					vehicleId:vehicleId
				},
				page : true, //开启分页
				limit : 10,
				cols : [ [ //标题栏
					{field : '',title : '序号',type:'numbers',align:'center'},
					{field : 'vehicleName',title : '车型',align:'center'}, 
					{field : 'configurationLevelName',title : '配置',align : 'center'}, 
					{field : 'canPassage',title : 'CAN',align : 'center'}, 
					{field : 'harness',title : '线束段',align : 'center'},
					{field : 'moduleName',title : '模块',align : 'center'}, 
					{field : 'txId',title : 'TX',align : 'center'}, 
					{field : 'rxId',title : 'Rx',align : 'center'},
					{field : '',title : '操作',align : 'center',unresize : true,width:150,toolbar : '#barDetail'}
				] ],
				done:function(e){
					$(".layui-laypage-limits").hide();//隐藏分页下拉选项
				}
			});
		}
	 	
	 	//查询节点配置信息
	 	function searchNodeDataInfo() {
			table.render({
				elem : '#nodeDataInfo',
				url : '${ctx}/dataManage/findNodeBySession.do',
				page : true, //开启分页
				limit : 6,
				cols : [ [ //标题栏
					{field : '',title : '序号',type:'numbers',align:'center'},
					{field : '',title : '选择',type :'checkbox',align:'center'},
					{field : 'canPassage',title : 'CAN',align:'center'}, 
					{field : 'harness',title : '线束段',align : 'center'}, 
					{field : 'moduleName',title : '模块',align : 'center'}, 
					{field : 'txId',title : 'TX',align : 'center'}, 
					{field : 'rxId',title : 'Rx',align : 'center'},
					{field : 'configurationLevelName',title : '配置',align : 'center'}
				] ],
				done:function(e){
					$(".layui-laypage-limits").hide();//隐藏分页下拉选项
				}
			});
		}
	 	 	
	 		 	
	 	//新增节点数据模态框
	 	function addNodeData(){
	 		document.all("resetBox").click();//重置下拉框
	 		//清空session
			$.getJSON("${ctx}/dataManage/clearNodeSession.do",function(e){});
	 		
	 		//绑定车型下拉框
			appendOption("add_vehicleId", "${ctx}/faultCode/findCarTypeInfo.do");
			nodeDataModal = layer.open({
				type : 1,
				title : '新增节点数据页面',
				content : $('#nodeDataModal'),
				area : [ '80%', '80%' ],
				offset: '150px',
				resize:false,//是否允许拉伸
				cancel : function(index, layero) {
					if (confirm('确定要关闭么，未保存的数据将会丢失！')) {
						layer.close(index);
					}
					return false;
				}
			});
			form.render("select");
			searchNodeDataInfo();//查询节点配置信息
	 	}
	 	
	 	//新增节点详情数据模态框
	 	function addNodeDetailData(){
	 		var vehicleId=$("#add_vehicleId").val();
			var configurationLevelId=$("#add_configurationId").val();
			bindingInsertNodeDetailBox();//绑定节点配置模态框--新增模态框下拉框
	 		
	 		if(vehicleId >0 && configurationLevelId>0){
	 			insertNodeModal = layer.open({
					type : 1,
					title : '节点详细数据新增页面',
					content : $('#insertNodeModal'),
					area : [ '650px', '550px' ],
					offset: '150px',
					resize:false,//是否允许拉伸
				});
				form.render();
	 		}else{
	 			layer.msg("请先选择车型配置下拉框信息");
	 		}
	 	}
	 	
	 	//配置页面模态框
	 	function addAllocation(){
			var index = layer.open({
				type : 1,
				title : '配置页面',
				content : $('#allocationModal'),
				area : [ '900px', '500px' ],
				offset: '150px',
				resize:false,//是否允许拉伸
			});
			bindingWiringHarnessBox();//绑定线束段配置模态框下拉框
			searchWiringHarnessInfo();//查询线束段配置信息
			form.render();
	 	}
	 	
	 	
	 	//配置页面模态框
	 	function insertAllocation(){	 		
			insertAllocationModal = layer.open({
				type : 1,
				title : '配置新增页面',
				content : $('#insertAllocationModal'),
				area : [ '650px', '400px' ],
				offset: '150px',
				resize:false,//是否允许拉伸
			});			
			bindingHarnessAddBox();//绑定线束段配置新增模态框下拉框
			form.render();
	 	}
	 	
	 	
	 	//配置页面表单提交监听——新增
		form.on('submit(saveAllocation)', function(data) {
			if(data.field.vehicleId == "0"){
				layer.tips('请选择车型','.vehicleId');
			} else if(data.field.configurationLevelId == "0"){
				layer.tips('请选择配置','.configurationLevelId');
			} else if(data.field.canConfigurationId == "0"){
				layer.tips('请选择通道','.canConfigurationId');
			} else{
				$.getJSON("${ctx}/dataManage/insertWiringHarnessInfo.do", data.field, function(d) {
					if (d.text=="success") {
						layer.close(insertAllocationModal);
						layer.msg("新增成功", {offset : '150px' });
						document.all("resetAllocation").click();//重置表单
						searchWiringHarnessInfo();//刷新表格
					} else {
						layer.alert(d.text, { icon : 2, title : '提示', offset : '150px' });
					}
				});
			}			
			return false;
		});
	 	
		
		//配置页面修改事件
		$("#updateAllocation").click(function(){
			var checkStatus=table.checkStatus('wiringHarnessInfo'),
			data=checkStatus.data;
			if(data.length==0){
				layer.alert("请先选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length>1){
				layer.alert("只能选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length==1){
				bindingHarnessEditBox();//绑定线束段配置修改模态框下拉框
				loadDatatoForm("updateAllocationForm",data[0]);			
				updateAllocationModal = layer.open({
					type : 1,
					title : '修改配置页面信息',
					content : $('#updateAllocationModal'),
					area : [ '650px', '400px' ],
					offset: '150px',
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
		});
	 	
	 	
		//修改线束段配置----修改按钮
		form.on('submit(editAllocation)', function(data) {
			$.post("${ctx}/dataManage/updateWiringHarnessInfo.do",
				data.field, function(e) {
					if (e.text=="success") {
						layer.close(updateAllocationModal);
						layer.msg("修改成功！", {offset : '150px'});
						searchWiringHarnessInfo();//刷新表格
					} else {
						layer.alert(e.text, {icon : 2,title : '提示',offset : '150px'});
					}
			});
			return false;
		});
	 	
	 	
		//删除线束段配置
		$("#deleteAllocation").click(function(){
			var checkStatus = table.checkStatus('wiringHarnessInfo')//获取选中行数据
	      	,data = checkStatus.data;
		    if(data.length==0){
		    	layer.alert("请选择需要删除的数据", {icon : 0,title : '提示',offset : '150px'});
		    	return;
		    }
			layer.confirm('真的删除选中的数据？', {
            	icon: 3,
                btn: ['确定', '取消']
            }, function (index) {
            	layer.close(index);
            	var num = 0;
            	for ( var i = 0; i < data.length; i++) {
            		$.ajax({
	                	url: "${ctx}/dataManage/deleteWiringHarnessInfo.do?harnessConfigurationId=" + data[i].harnessConfigurationId,                    
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
            	searchWiringHarnessInfo();//刷新表格
	   		});
		});
	 	
	 	
	 	
	 	//节点配置--新增保存监听事件
		form.on('submit(insertNodeDetail)', function(data){
			//传递参数
			data.field.vehicleId=$("#add_vehicleId").val();
			data.field.configurationLevelId=$("#add_configurationId").val();
			data.field.moduleId=$("#add_moduleId").val();
			data.field.supplierId=$("#add_supplierId").val();
			data.field.canConfigurationId=$("#add_canConfigurationId").val();
			data.field.harness=$("#add_harness").val();
			
			if(data.field.canConfigurationId == "0"){
				layer.tips('请选择通道','.add_canConfigurationId');
			} else if(data.field.harness == ""){
				layer.tips('请选择线束段','.add_harness');
			} else if(data.field.moduleId == "0"){
				layer.tips('请选择模块','.add_moduleId');
			} else if(data.field.supplierId == "0"){
				layer.tips('请选择供应商','.add_supplierId');
			} else{
				$.post("${ctx}/dataManage/insertNodeInfoBySession.do",data.field,function(e){
					if(e==200){
						layer.close(insertNodeModal);//关闭当前弹窗
						layer.msg("新增成功！",{offset : '150px' });
						document.all("resetNodeDetail").click();//重置表单
						//document.all("resetBox").click();//重置下拉框
						searchNodeDataInfo();//刷新表格
					}else if(e==500){
						layer.alert("该组合已存在！",{icon:0,title:'提示'});
					}
					else{
						layer.alert("数据异常！",{icon:2,title:'提示'});
					}
				});
			}
			return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
		});
		
		
		//节点详情页面修改事件
		$("#editNodeDetailData").click(function(){
			var checkStatus=table.checkStatus('nodeDataInfo'),
			data=checkStatus.data;
			if(data.length==0){
				layer.alert("请先选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length>1){
				layer.alert("只能选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length==1){
				bindingUpdateNodeDetailBox();//绑定节点修改模态框的下拉框
				loadDatatoForm("updateNodeForm",data[0]);//回显数据	
				//修改保存按钮设置
				$("#updateNodeDetail").removeAttr('style', 'display:none');//显示
				$("#editNodeDetail").attr('style', 'display:none');//隐藏
				
				updateNodeModal = layer.open({
					type : 1,
					title : '节点详细数据修改页面',
					content : $('#updateNodeModal'),
					area : [ '650px', '650px' ],
					offset: '150px',
					resize:false,//是否允许拉伸
					cancel : function(index, layero) {
						if (confirm('确定要关闭么，未保存的数据将会丢失！')) {
							layer.close(index);
						}
						return false;
					}
				});
				form.render('select');
			}
		});
	 	
		
		//节点配置--修改监听事件
		form.on('submit(updateNodeDetail)', function(data){
			//传递参数
			data.field.vehicleId=$("#edit_vehicleId").val();
			data.field.configurationLevelId=$("#edit_configurationId").val();
			data.field.moduleId=$("#edit_moduleId").val();
			data.field.supplierId=$("#edit_supplierId").val();
			data.field.canConfigurationId=$("#edit_canConfigurationId").val();
			data.field.harness=$("#edit_harness").val();
			data.field.nodeId=$("#nodeId").val();
			
			if(data.field.vehicleId == "0"){
				layer.tips('请选择车型','.edit_vehicleId');
			} else if(data.field.configurationLevelId == "0"){
				layer.tips('请选择配置','.edit_configurationId');
			} else if(data.field.canConfigurationId == "0"){
				layer.tips('请选择通道','.edit_canConfigurationId');
			} else if(data.field.harness == ""){
				layer.tips('请选择线束段','.edit_harness');
			} else if(data.field.moduleId == "0"){
				layer.tips('请选择模块','.edit_moduleId');
			} else if(data.field.supplierId == "0"){
				layer.tips('请选择供应商','.edit_supplierId');
			} else{
				$.post("${ctx}/dataManage/updateNodeInfoBySession.do",data.field,function(e){
					if(e==200){
						layer.close(updateNodeModal);//关闭当前弹窗
						layer.msg("修改成功！",{offset : '150px' });
						searchNodeDataInfo();//刷新表格
					}else if(e==500){
						layer.alert("该组合已存在！",{icon:0,title:'提示'});
					}
					else{
						layer.alert("数据异常！",{icon:2,title:'提示'});
					}
				});
			}
			return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
		});
		
	 	
		//节点详情页面删除事件
		$("#delNodeDetailData").click(function(){
			var checkStatus=table.checkStatus('nodeDataInfo'),
			data=checkStatus.data;
			if(data.length==0){
				layer.alert("请先选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			} else {
	       		layer.confirm('真的要删除选中的信息?', {
	            	icon: 3,
	                btn: ['确定', '取消']
	            }, function (index) {		
	            	layer.close(index); 
	            	var num = 0;	                
	            	for ( var i = 0; i < data.length; i++) {
	            		$.ajax({
		                	url: "${ctx}/dataManage/deleteNodeInfoBySession.do?nodeId=" + checkStatus.data[i].nodeId,                    
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
	            	layer.alert(num + "条数据删除成功，"+(data.length - num) + "条数据删除失败!",{ icon: 1, skin: 'layui-layer-molv', closeBtn: 0, anim: 4 });
	            	searchNodeDataInfo();//刷新表格
		   		});
	       	}
		});
		
		
		//节点详情页面保存事件
		$("#saveNodeDetailData").click(function(){
			$.getJSON("${ctx}/dataManage/insertNodeInfo.do", function(d) {
				if (d.code==200) {
					layer.close(nodeDataModal);//关闭当前弹窗
					layer.msg(d.text, { offset : '150px' });
					//清空session
					$.getJSON("${ctx}/dataManage/clearNodeSession.do",function(e){});
					searchNodeDataInfo();//刷新表格
					SearchNodeInfo();//查询节点数据信息
				} else {
					layer.alert(d.text, { icon : 2, title : '提示', offset : '150px' });
				}
			}); 
		});
		
		
			
		//监听节点信息操作工具条，查看详情事件
		table.on('tool(nodeInfo)', function(obj) {
			var data = obj.data;
			if (obj.event == 'detail') {
				var index = layer.open({
					type : 1,
					title : '拓扑图详细数据页面',
					content : $('#topologicalDetailModal'),
					area : [ '70%', '70%' ],
					offset: '150px',
					resize:false,//是否允许拉伸
				});
				searchTopologicalDetail(data.vehicleId);//查询节点详情配置信息
				form.render();
			} else if (obj.event == 'deldetail'){
				layer.confirm('真的要删除选中的信息?若里面含有明细信息则将会一起删除！', {
	            	icon: 3,
	                btn: ['确定', '取消']
	            }, function (index) {		
	            	layer.close(index); 
            		$.ajax({
	                	url: "${ctx}/dataManage/deleteNodeDetailInfo.do?testNodeId=" + data.testNodeId,                    
	                	type: "post",//数据传输通道的类
	                    async: false,
	                    dataType: "json",//传输的数据的类型
	                    success: function (datas) {//直接理解为回调函数
	                 		if (datas.text=="success") {
	                 			layer.msg("数据删除成功");
	                 			SearchNodeInfo();//刷新表格
	                        } else{
								layer.alert(datas.text,{ icon:2, title:'提示', offset : '150px'});
							}
	                    }
	                });
		   		});
			}
		});
		
		
		//监听节点详情数据操作工具条，修改/删除事件
		table.on('tool(topologicalDetail)', function(obj) {
			var data = obj.data;
			if(obj.event == 'edit'){
				bindingUpdateNodeDetailBox();//绑定节点修改模态框的下拉框
				loadDatatoForm("updateNodeForm",data);//回显数据	
				//修改保存按钮设置
				$("#updateNodeDetail").attr('style', 'display:none');//隐藏
				$("#editNodeDetail").removeAttr('style', 'display:none');//显示
			
				updateNodeModal = layer.open({
					type : 1,
					title : '节点详细数据修改页面',
					content : $('#updateNodeModal'),
					area : [ '650px', '650px' ],
					offset: '150px',
					resize:false,//是否允许拉伸
					cancel : function(index, layero) {
						if (confirm('确定要关闭么，未保存的数据将会丢失！')) {
							layer.close(index);
						}
						return false;
					}
				});
				form.render('select');
			} else if(obj.event == 'del'){
				layer.confirm('确定要删除该条信息?', {
	            	icon: 3,
	            	offset: '150px',
	                btn: ['确定', '取消']
	            }, function (index) {		
	            	layer.close(index);
	            	$.post("${ctx}/dataManage/deleteNodeInfo.do",{nodeId:data.nodeId},function(e){
						if(e.text=="success"){
							layer.msg("删除成功！",{offset : '150px' });
							searchTopologicalDetail(data.vehicleId);//刷新，查询节点详情配置信息
							SearchNodeInfo();//刷新，查询节点数据信息
						}else{
							layer.alert(e.text,{ icon:2, title:'提示', offset : '150px'});
						}
					});
		   		});
			}
		});
		
		
		//查看节点详情--保存监听事件
		form.on('submit(editNodeDetail)', function(data){
			//传递参数
			data.field.vehicleId=$("#edit_vehicleId").val();
			data.field.configurationLevelId=$("#edit_configurationId").val();
			data.field.moduleId=$("#edit_moduleId").val();
			data.field.supplierId=$("#edit_supplierId").val();
			data.field.canConfigurationId=$("#edit_canConfigurationId").val();
			data.field.harness=$("#edit_harness").val();
			data.field.nodeId=$("#nodeId").val();
			
			if(data.field.vehicleId == "0"){
				layer.tips('请选择车型','.edit_vehicleId');
			} else if(data.field.configurationLevelId == "0"){
				layer.tips('请选择配置','.edit_configurationId');
			} else if(data.field.canConfigurationId == "0"){
				layer.tips('请选择通道','.edit_canConfigurationId');
			} else if(data.field.harness == ""){
				layer.tips('请选择线束段','.edit_harness');
			} else if(data.field.moduleId == "0"){
				layer.tips('请选择模块','.edit_moduleId');
			} else if(data.field.supplierId == "0"){
				layer.tips('请选择供应商','.edit_supplierId');
			} else{
				$.post("${ctx}/dataManage/updateNodeInfo.do",data.field,function(e){
					if(e.code==200){
						layer.close(updateNodeModal);//关闭当前弹窗
						layer.msg("修改成功！",{offset : '150px' });
						searchTopologicalDetail(data.field.vehicleId);//刷新节点详情数据表格
						SearchNodeInfo();//刷新节点数据表格
					}else if(e.code==500){
						layer.alert("该组合已存在！",{icon:0,title:'提示'});
					}
					else{
						//layer.alert(e.text,{icon:2,title:'提示'});
						layer.confirm(e.text+'是否立即前往新增？', {
			            	icon: 3,
			            	offset: '150px',
			                btn: ['确定', '取消']
			            }, function (index) {		
			            	layer.close(index);
			            	insertAllocation();//打开新增配置页面模态框
				   		});
					}
				});
			}
			return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
		});
		
		
		//生成拓扑图事件
		$("#topologyDiagrams").click(function(){
			var checkStatus=table.checkStatus('nodeInfo'),
			data=checkStatus.data;
			if(data.length==0){
				layer.alert("请先选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length>1){
				layer.alert("只能选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length==1){
				generateTopologyDiagrams(data);//生成拓扑图
			}
		});
		
		
	 	
	 	//生成拓扑图
		//步骤：  获取canvas ——> 创建stage ——> 创建scene添加到stage ——> 创建节点添加到scene
	 	function generateTopologyDiagrams(data){
			$("#canvas-box").html("");
			$("#canvas-box").append("<canvas width='3000' height='1200' id='canvas' style='background-color: #495060;'>");
 			var canvas = document.getElementById('canvas'); // canvas节点
            var stage = new JTopo.Stage(canvas);// 创建舞台
           
            //避免重复显示
            if($(".jtopo_toolbar").length==0){
            	//显示工具栏
                showJTopoToobar(stage);//此方法需引入 toolbar.js 文件
            }
           
            var scene = new JTopo.Scene();// 创建场景
            stage.add(scene);
            //scene.background = './img/bg.jpg';// 背景图
            //scene.backgroundColor = '63,74,89';
            
            var cloudNode = new JTopo.Node('GW');// 创建普通节点（可以在这里添加文字）
            //cloudNode.text = '节点名字'; // 设置文字
            cloudNode.textPosition = 'Middle_Center'; // 文字位置，对齐方式可选择：（'Top_Left', 'Top_Center', 'Top_Right', 'Middle_Left', 'Middle_Center', 'Middle_Right', 'Bottom_Left', 'Bottom_Center', 'Bottom_Right'）
            //cloudNode.textOffsetX = -30; // 文字向左偏移 30像素（以0为基准，左负右正）
            cloudNode.textOffsetY = -8; // 文字向上偏移8像素（以0为基准，上负下正）
            cloudNode.font = '16px 微软雅黑'; // 字体设置
            cloudNode.fillColor = '255,255,255'; // 节点填充颜色（RGB）
            cloudNode.fontColor='0,0,0';//字体颜色
            cloudNode.borderWidth = 1; // 边框的宽度
            cloudNode.borderColor = '0,0,0'; // 边框颜色
            cloudNode.setSize(80,60);// 设置节点尺寸（宽高）
            cloudNode.setLocation(360,130); // 设置节点位置           
            cloudNode.layout = {type: 'tree', width:400, height: 100}//第二级节点的类型、宽高
            scene.add(cloudNode);// 把节点添加到场景
            
            var vehicleId = data[0].vehicleId;
            $.post("${ctx}/dataManage/findNodeDetialList.do",{vehicleId:vehicleId},function(e){
            	var dataOne=e.nodeListOne;
            	var dataTwo=e.nodeListTwo;
            	var dataThree=e.nodeListThree;
            	var dataFour=e.nodeListFour;
            	
            	//第一层
            	for(var i = 0; i < dataOne.length; i++){
                    var nodeOne = new JTopo.Node(dataOne[i].canPassage);// 创建普通节点
                    nodeOne.font = '16px 微软雅黑'; // 字体设置
                    nodeOne.fillColor = "255,255,255"; // 节点填充颜色（RGB）
                    nodeOne.fontColor='0,0,0';//字体颜色
                    nodeOne.borderWidth = 1; // 边框的宽度
                    nodeOne.borderColor = '0,0,0'; // 边框颜色
                    nodeOne.textPosition = 'Middle_Center';//设置文字位置
                    nodeOne.textOffsetY = -8; // 文字向上偏移8像素
                    nodeOne.setSize(80,60);// 设置节点尺寸（宽高）
                    nodeOne.setLocation(scene.width * Math.random(), scene.height * Math.random());//随机分配坐标
                    nodeOne.layout = {type: 'tree', width:200, height: 100};//第三级节点的类型、宽高
                    scene.add(nodeOne);// 把节点添加到场景      
                    
                    var link = new JTopo.Link(cloudNode, nodeOne);//创建连接线
                    //link.lineWidth = 6; // 线宽  
                    //link.dashedPattern = 5; // 虚线宽度间距   
                    //link.strokeColor = JTopo.util.randomColor(); // 线条颜色随机   
                    //link.arrowsRadius = 10; // 添加线的指向箭头（值越大箭头越大）
                    link.strokeColor='51,153,255';//指定线条颜色（RGB）
                    scene.add(link);// 把连接线添加到场景
                    
                  	//第二层
                    for(var j = 0; j < dataTwo.length; j++){                    	
                    	if(dataTwo[j].canPassage==dataOne[i].canPassage){
                    		var nodeTwo = new JTopo.CircleNode(dataTwo[j].harness);// 创建圆形节点
                            nodeTwo.radius = 25;//半径
                            nodeTwo.fillColor = "255,255,255"; // 节点填充颜色（RGB）
                            nodeTwo.font = '16px 微软雅黑'; // 字体设置
                            nodeTwo.fontColor='0,0,0';//字体颜色
                            nodeTwo.textPosition = 'Middle_Center';//设置文字位置
                            nodeTwo.textOffsetY = -8; // 文字向上偏移8像素
                            nodeTwo.setLocation(scene.width * Math.random(), scene.height * Math.random());//随机分配坐标
                            nodeTwo.layout = {type: 'tree', width:150, height: 100};//第四级节点的类型、宽高
                            scene.add(nodeTwo);// 把节点添加到场景
                            
                            var linkTwo=new JTopo.Link(nodeOne, nodeTwo);
                            linkTwo.strokeColor='51,153,255';//指定线条颜色
                            scene.add(linkTwo);// 把连接线添加到场景
                            
                          	//第三层
                            for(var k = 0; k < dataThree.length; k++){
                            	if(dataThree[k].harness==dataTwo[j].harness){
                            		var nodeThree = new JTopo.Node(dataThree[k].moduleName);// 创建普通节点
                                    nodeThree.font = '16px 微软雅黑'; // 字体设置
                                    nodeThree.fillColor = "255,255,255"; // 节点填充颜色（RGB）
                                    nodeThree.fontColor='0,0,0';//字体颜色
                                    nodeThree.borderWidth = 1; // 边框的宽度
                                    nodeThree.borderColor = '0,0,0'; // 边框颜色
                                    nodeThree.textPosition = 'Middle_Center';//设置文字位置
                                    nodeThree.textOffsetY = -8; // 文字向上偏移8像素
                                    nodeThree.setSize(80,60);// 设置节点尺寸（宽高）
                                    nodeThree.setLocation(scene.width * Math.random(), scene.height * Math.random());//随机分配坐标
                                    nodeThree.layout = {type: 'tree', width:100, height: 100};//第五级节点的类型、宽高
                                    scene.add(nodeThree);// 把节点添加到场景
                                     
                                    var linkThree=new JTopo.Link(nodeTwo, nodeThree);
                                    linkThree.strokeColor='51,153,255';//指定线条颜色
                                    scene.add(linkThree);// 把连接线添加到场景         
                                            
                                  	//第四层
                                    for(var m = 0; m < dataFour.length; m++){
                                    	if(dataFour[m].moduleName==dataThree[k].moduleName 
                                    			&& dataFour[m].harness==dataThree[k].harness
                                    			&& dataFour[m].canPassage==dataThree[k].canPassage){
                                    		var source=dataThree[k].supplierName;
                                    		var rt= /(.+)?(?:\(|（)(.+)(?=\)|）)/.exec(source);//截取（）里面的内容
                                    		var supplierName="";
                                    		//判断供应商代码是否为空
                                    		if(dataFour[m].supplierCode!="" && dataFour[m].supplierCode!=null){
                                    			supplierName=rt[2];//获取截取之后的名称
                                    			var nodeFour = new JTopo.Node(supplierName);// 创建普通节点
                                        		nodeFour.font = '16px 微软雅黑'; // 字体设置
                                        		nodeFour.fontColor='0,0,0';//字体颜色
                                        		nodeFour.textOffsetY = -8; // 文字向上偏移8像素
                                    		}else{                                    			
                                    			supplierName="×";//设置一把×
                                    			var nodeFour = new JTopo.Node(supplierName);// 创建普通节点
                                        		nodeFour.font = '60px 微软雅黑'; // 字体设置
                                        		nodeFour.fontColor='255,0,0';//字体颜色
                                        		nodeFour.textOffsetY = -35; // 文字向上偏移8像素
                                    		}                                    		
                                    		
                                    		//var nodeFour = new JTopo.Node(supplierName);// 创建普通节点
                                    		//nodeFour.font = '16px 微软雅黑'; // 字体设置
                                    		nodeFour.fillColor = "255,255,255"; // 节点填充颜色（RGB）
                                    		//nodeFour.fontColor='0,0,0';//字体颜色
                                    		nodeFour.borderWidth = 1; // 边框的宽度
                                    		nodeFour.borderColor = '0,0,0'; // 边框颜色
                                    		nodeFour.textPosition = 'Middle_Center';//设置文字位置   
                                    		//nodeFour.textOffsetY = -8; // 文字向上偏移8像素
                                    		nodeFour.setSize(80,60);// 设置节点尺寸（宽高）
                                    		nodeFour.setLocation(scene.width * Math.random(), scene.height * Math.random());//随机分配坐标
                                            scene.add(nodeFour);// 把节点添加到场景
                                             
                                            var linkFour=new JTopo.Link(nodeThree, nodeFour);
                                            linkFour.strokeColor='51,153,255';//指定线条颜色
                                            scene.add(linkFour);// 把连接线添加到场景         
                                    	}
                                    }
                            	}
                            }
                    	}
                    }
                }
            });
            
            JTopo.layout.layoutNode(scene, cloudNode, true);// scene 场景 cloudNode 定位目标
            stage.zoomIn();//缩小显示
            stage.centerAndZoom(); //缩放并居中显示，不设置默认从左边开始绘画
            
            //拖动鼠标弹起监听事件
            scene.addEventListener('mouseup', function(e){
                if(e.target && e.target.layout){
                    JTopo.layout.layoutNode(scene, e.target, true);    
                }                
            });
	            
	 	}
	 	
	 
	 	
		
	</script>
</body>
</html>
