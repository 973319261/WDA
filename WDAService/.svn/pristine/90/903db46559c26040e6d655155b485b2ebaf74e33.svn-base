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

<title>My JSP 'product-data.jsp' starting page</title>

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
	
	.layui-form-checked[lay-skin=primary] i{
		border-color: #5FB878!important;
    	background-color: #5FB878;
	}
	
	.layui-icon-ok:before{
		content:"\e605";
		color: #fff;
	}
	
	/* 设置文本域样式 */
	.textField{
		width: 100%;
		min-height: 76px;
		max-height: 76px;
		resize: none;
		padding: 8px;
		border-color: #e6e6e6;
	}
	
	#intsertFlowData input{
		width: 240px;
	}
	
	.layui-table-body{
		overflow: hidden;
	}
	
	.hide{
		display: none;
	}
	
	.orde-select .layui-form-select .layui-edge{
		right: -38px !important;
	}
	
	.orde-select .layui-form-select dl{
		min-width: 240px !important;
		max-width: 240px !important;
		max-height: 150px;
	}
	
	.select-data .layui-form-select dl,#insertFlowModal .layui-form-select dl{
		max-width: 210px !important;
		max-height: 250px;
	}
</style>
</head>

<body class="layui-view-body">
	<div class="layui-content" style="padding:14px;">
		<div class="layui-page-header" style="margin-bottom: 15px;">
			<div class="pagewrap">
				<span class="layui-breadcrumb"> <a href="javascript:void(0)">首页</a>
					<a href="javascript:void(0)">数据管理</a><a href="javascript:void(0)"><cite>小流程</cite></a>
				</span>
				<h2 class="title">小流程</h2>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-card">
				<div class="layui-card-body">
					<div class="form-box">
						<div class="layui-form layui-form-item" style="margin: 5px 0 10px">
							<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 select-data">
								<div class="layui-form-mid">车型:</div>
								<div class="layui-input-inline" style="width: 190px;">
									<select id="vehicleId"></select>
								</div>
								<label class="layui-form-label" style="width: 60px;">小流程:</label>
								<div class="layui-input-inline" style="width: 190px;">
									<select id="flowId" lay-search></select>
								</div>
							</div>
							<div class="layui-col-xs6 layui-col-sm6 layui-col-md6" style="padding: 5px 0 0 0;">
								<button class="layui-btn layui-btn-sm layui-btn-normal" type="button" onclick="searchflowInfo()">查询</button>
								<button class="layui-btn layui-btn-sm" type="button" onclick="addModalSupplier()">新增</button>
								<button class="layui-btn layui-btn-sm" type="button" onclick="openResponseOrderModal()">响应指令数据</button>
								<button class="layui-btn layui-btn-sm" type="button" onclick="openFlowStepModal(1)">小流程步骤</button>
							</div>
						</div>
						<table id="flowInfoOne" lay-filter="flowInfoOne"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- ******************************** 新增模态窗体  *********************** -->
	<div id="insertFlowModal" class="modal" style="padding: 15px 15px;">
		<form class="layui-form" id="formInfo" action="">
			<input type="hidden" id="userstate" name=userstate value="true"/>
			<div class="layui-col-xs12 layui-col-sm12 layui-col-md12 layui-form-item">
				<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3" style="text-align: right;">
						<div class="layui-form-mid" style="width:100%">车型：</div>
					</div>					
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<select id="vehicleIds" name="vehicleId" lay-filter="cartype"></select>
					</div>
				</div>
				
				<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3" style="text-align: right;">
						<div class="layui-form-mid" style="width:100%">模块：</div>
					</div>
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8" >
						<select id="moduleIds" lay-filter="module"></select>
					</div>
				</div>
					
				<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
					<div class="layui-col-xs3 layui-col-sm4 layui-col-md4" style="text-align: right;">
						<div class="layui-form-mid" style="width:100%">供应商：</div>
					</div>
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<select id="supplierIds"></select>
					</div>
				</div>
			</div>
			<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-form-item">
					<div class="layui-form-mid">小流程名称:</div>
					<div class="layui-col-xs5 layui-col-sm5 layui-col-md5">
						<input type="text" class="layui-input" required lay-verify="required" autocomplete="off" id="">
					</div>
					<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="padding: 5px 0 0 10px;">
						<button class="layui-btn layui-btn-sm layui-btn-blue" type="button" onclick="openFlowStepModal(2)" style="margin-left: 10px;">选择</button>
						<button class="layui-btn layui-btn-sm" type="button" onclick="intsertFlowData(3)">添加</button>
						<button class="layui-btn layui-btn-sm layui-btn-danger" type="button" onclick="addVehicleImages()">删除</button>
						<button class="layui-btn layui-btn-sm" type="button" onclick="addVehicleImage()">保存</button>
					</div>
				</div>
			</div>
			<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
				<table id="flowStepInfo" lay-filter="flowStepInfo"></table>
			</div>
		</form>
	</div>
	
	<!-- ******************************** 小流程具体数据新增、修改模态窗体  *********************** -->
	<div id="intsertFlowData" class="modal" style="padding: 15px 6px;">
		<form class="layui-form" id="formFlowDataInfo" action="">
			<input type="hidden" name="flowStepId" />
			<input type="hidden" id="stateOne" name="stateOne" />
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 115px;">操作名称：</label>
				<div class="layui-input-inline">
					<input type="text" name="operationNames" lay-verify="required" autocomplete="off" 
						class="layui-input" />
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 115px;">发送指令：</label>
				<div class="layui-input-inline">
					<input type="text" id="sendOrders" name="sendOrders" onclick="verifyInsertInput('sendOrders')"
						lay-verify="required" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="width: 115px;">响应指令：</label>
				<div class="layui-col-xs8 layui-col-sm8 layui-col-md8 orde-select">
					<div class="layui-input-inline">
						<input type="text" id="responseDatas" class="layui-input" style="position:absolute;z-index:2;width:108%;padding-right: 0px;" 
								 autocomplete="off" maxlength="50" onclick="verifyInsertInput('responseDatas')" name="responseOrders" lay-verify="required">
	                    <select id="responseData" lay-filter="response_select" class="layui-select" lay-search></select>
					</div>
				</div>
			</div>
			<div class="layui-form-item" style="padding-top: 120px;text-align: center;">
				<button class="layui-btn layui-btn-sm" lay-submit lay-filter="addSmallFlow">提交</button>
				<button type="reset" class="layui-btn layui-btn-sm layui-btn-primary" layui-btn-primary" 
					id="addSmallFlowReset">重置</button>
			</div>
		</form>
	</div>
	
	<!-- ******************************** 小流程步骤  *************************** -->
	<div id="flowStepModal" class="modal">
		<div class="layui-form layui-form-item" style="margin: 5px 0 10px;text-align: right;">
			<div class="btn-one">
				<button class="layui-btn layui-btn-sm" type="button" onclick="intsertFlowDatas(1)">添加</button>
				<button class="layui-btn layui-btn-sm layui-btn-warm" type="button" onclick="intsertFlowDatas(2)">修改</button>
				<button class="layui-btn layui-btn-sm layui-btn-danger" type="button" id="delFlowDatas">删除</button>
			</div>
			<button class="layui-btn layui-btn-sm btn-two" type="button" id="exportDidBtn">导入</button>
			<input name="stateId" id="stateId" type="hidden" />
		</div>
		<table id="flowStepInfoTwo" lay-filter="flowStepInfoTwo"></table>
	</div>
	
	<!-- ******************************** 响应指令数据页面  *************************** -->
	<div id="responseOrderModal" class="modal">
		<div class="layui-form layui-form-item" style="margin: 5px 0 10px;text-align: right;">
			<button class="layui-btn layui-btn-sm" type="button" onclick="editResponseOrder(1)">添加</button>
			<button class="layui-btn layui-btn-sm layui-btn-warm" type="button" onclick="editResponseOrder(2)">修改</button>
			<button class="layui-btn layui-btn-sm layui-btn-danger" type="button" onclick="deleteResponseOrder()">删除</button>
			<input name="stateId" id="stateId" type="hidden" />
		</div>
		<table id="responseOrderInfo" lay-filter="responseOrderInfo"></table>
	</div>
	
	<!-- ******************************** 响应详细数据新增模态窗体  *********************** -->
	<div id="editresponseOrderModal" class="modal" style="padding: 15px 6px;">
		<form class="layui-form" id="formResponseOrder" action="">
			<input type="hidden" name="responseOrderId" />
			<div class="layui-form-item">
				<label class="layui-form-label">响应指令：</label>
				<div class="layui-input-inline" style="width: 260px;">
					<input type="text" id="" name="responseInstructions" lay-verify="required" autocomplete="off" 
						class="layui-input" onclick="verifyInsertInput('responseDatas')" />
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">详情：</label>
				<div class="layui-input-block">
			      <textarea placeholder="请输入内容" name="particulars" class="layui-textarea" lay-verify="required" 
			      		style="width:260px;min-height:150px;max-height:150px;resize: none;" maxlength="100" ></textarea>
			      <span id="wordsLength" style="position:absolute; right:82px; bottom:0vh; font-size:12px; color:#BDCADA;">0/100</span>
			    </div>
			</div>
			<div class="layui-form-item" style="padding-top: 60px;text-align: center;">
				<button class="layui-btn layui-btn-sm" lay-submit lay-filter="saveResponseOrder" type="button">提交</button>
				<button type="reset" class="layui-btn layui-btn-sm layui-btn-primary" layui-btn-primary" id="resetResponseOrder">重置</button>
			</div>
		</form>
	</div>
	
	<script type="text/javascript" src="${ctx}/Content/js/jquery-3.3.1.min.js"></script>
	<script src="${ctx}/Content/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/combobox.js"></script>
	<!-- 文本编辑器 -->
	<!----------------------- 工具条 -------------------------->
	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
	</script>

	<script>
		//layui.use(['element','table','form','layer','jquery','layedit','upload'],function(){
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.$;
	 	var layedit = layui.layedit;
	 	var upload = layui.upload;
	 	var flowDetailDeatil;
	 	var responseOrderModals;//响应指令新增、修改模态框
		$.ajaxSettings.async = false;

		$(function() {
			appendOption("vehicleId", "${ctx}/faultCode/findCarTypeInfo.do");//绑定车型下拉框
			appendOption("flowId", "${ctx}/dataManage/findFlowInfoToDownBox.do");//绑定小流程下拉框
			appendOptions("responseData", "${ctx}/dataManage/findAllResponseOrder.do");//绑定响应数据拉框
			//小流程新增页面
			appendOption("vehicleIds", "${ctx}/faultCode/findCarTypeInfo.do");//绑定车型下拉框
			appendOption("moduleIds", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=5&&vehicleId=0");
			appendOption("supplierIds", "${ctx}/dataManage/findSupplierById.do?moduleId=0");
			
			searchflowInfo();
			//flowStepInfo();
			form.render('select');
		});
		
		//车型下拉框监听事件
		form.on('select(cartype)', function(data){
			if(data.value !=''){
				appendOption("moduleIds", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=5&&vehicleId="+data.value);
				appendOption("supplierIds", "${ctx}/dataManage/findSupplierById.do?moduleId=0&&configurationLevelId=5&&vehicleId="+data.value);
				form.render('select');
			}
		});
		
		//模块下拉框监听事件
		form.on('select(module)', function(data){
			if(data.value !=''){
				var vehicleId=$("#vehicleIds").val();
				appendOption("supplierIds", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId="+data.value+"&&configurationLevelId=5"
						+"&&vehicleId="+vehicleId);
				form.render('select');
			}
		});
		
		//监听工具条
		table.on('tool(exampleinfo)', function(obj) {
			var data = obj.data;
			if (obj.event == 'detail') {
				$.getJSON("${ctx}/example/toDetail",{exampleid:data.exampleid},function(e){
					if(e!=false){
						window.open("${ctx}/user/toUrl?page=reception/caseDetail");
					}
				});
			} else if (obj.event === 'edit') {
				$.getJSON("${ctx}/example/toDetail",{exampleid:data.exampleid},function(e){
					if(e){
						window.location.href="${ctx}/user/toUrl?page=backstage/editexample";
					}
				});
			} else if (obj.event === 'del') {
				layer.confirm("确定要删除该条数据吗？", {
					icon : 0,
					title : '提示',
					offset : '150px'
				}, function(index) {
					layer.close(index);
					$.getJSON("${ctx}/example/delExample", {
						exampleid : data.exampleid
					}, function(e) {
						if (e) {
							layer.msg("已删除！", {
								offset : '150px'
							});
							SearchTab();
						} else {
							layer.alert("操作失败", {
								icon : 2,
								title : '提示',
								offset : '150px'
							});
						};
					});
				});
			};
		});
	 	
		//查询小流程信息
	 	function searchflowInfo() {
			var vehicleId = $("#vehicleId").val();
			var flowId = $("#flowId").val();
			table.render({
				elem : '#flowInfoOne',
				url : '${ctx}/dataManage/findFlowInfo.do',
				where:{
					vehicleId:vehicleId,flowId:flowId
				},
				page : true, //开启分页
				limit : 6,
				cols : [ [ //标题栏
					{ type:'numbers',field : '',title : '序号',align:'center'},
					{ field : 'vehicleName',title : '车型',align:'center',unresize : true}, 
					{ field : 'moduleName',title : '模块',align:'center',unresize : true}, 
					{ field : 'supplierName',title : '供应商',align:'center',unresize : true}, 
					{ field : 'flowName',title : '小流程名称',width : 180,align : 'center',unresize : true},
					{ field : 'tool',title : '操作',width : 180,align : 'center',toolbar : '#barDemo',unresize : true} 
				] ],
				done:function(e){
					
				}
			});
		}
	 	
	 	//流程步骤信息(小流程新增页面)
	 	function flowStepInfo() {
			table.render({
				elem : '#flowStepInfo',
				url : '${ctx}/dataManage/findSmallFlowInfoSession.do',
				page : true, //开启分页
				limit : 6,
				cols : [ [ //标题栏
					{field : '',title : '选择',type:'checkbox',align:'center',unresize : true},
					{ field : 'didName',title : '操作名称',edit:'text',align:'center',},
					{ field : 'moduleName',title : '发送指令',width : 180,align : 'center'}, 
					{ field : 'tool',title : '响应指令',width : 180,align : 'center',},
					{ field : 'tool',title : '标识',align : 'center',},
				] ],
				done:function(e){
					
				}
			});
		}
	 	
	 	//查询小流程步骤信息
		function searchFlowStepInfo(){
			table.render({
				elem : '#flowStepInfoTwo',
				url : '${ctx}/dataManage/findSmallFlowStepInfo.do',
				page : true, //开启分页
				limit : 5,
				defaultToolbar:null,
				cols : [ [ //标题栏
					{ type:'numbers',field : '',title : '序号',align:'center'}, 
					{ field : '',title : '选择',align:'center',type :'checkbox'}, 
					{ field : 'operationNames',title : '操作名称',align : 'center',unresize : true}, 
					{ field : 'sendOrders',title : '发送指令',align : 'center',unresize : true}, 
					{ field : 'responseOrders',title : '响应指令',align : 'center',unresize : true},
				] ],done:function(res,curr,count){
					$('.layui-laypage-limits').hide();//隐藏分页选项下拉框
				}
			});
		}
	 	
	 	//查询响应指令信息
		function searchResponseOrderInfo(){
			table.render({
				elem : '#responseOrderInfo',
				url : '${ctx}/dataManage/findResponseOrderInfo.do',
				page : true, //开启分页
				limit : 5,
				defaultToolbar:null,
				cols : [ [ //标题栏
					{ type:'numbers',field : '',title : '序号',align:'center'}, 
					{ field : '',title : '选择',align:'center',type :'checkbox'}, 
					{ field : 'responseInstructions',title : '响应指令',align : 'center',unresize : true}, 
					{ field : 'particulars',title : '详情',align : 'center',unresize : true},
				] ],done:function(res,curr,count){
					$('.layui-laypage-limits').hide();//隐藏分页选项下拉框
				}
			});
		}
	 	
	 	//新增模态框
	 	function addModalSupplier(){
			var index = layer.open({
				type : 1,
				title : '新增页面',
				content : $('#insertFlowModal'),
				area : [ '75%', '85%' ],
				success:function(layero,idex){
					$(':focus').blur();
				}
			});
			//清空session中小流程步骤信息
			//$.post("${ctx}/dataManage/clearFlowDetailInfoSession.do",function(e){});
			flowStepInfo();
			form.render();
	 	}
	 	
	 	//小流程具体数据新增模态框
	 	function intsertFlowDatas(num){
	 		var checkStatus=table.checkStatus('flowStepInfoTwo'),
			data=checkStatus.data;
	 		document.all("addSmallFlowReset").click();//重置表单
	 		var txt="小流程具体数据新增页面";
	 		if(num==2){
	 			txt="小流程具体数据修改页面";
	 		}
	 		
	 		if(num==2 && data.length==1){
				loadDatatoForm("formFlowDataInfo",data[0]);
			}else if(num ==2 && data.length>1){
				layer.alert("只能选择一条信息", {icon : 0,title : '提示',offset : '150px'});
				return;
			}else if(num ==2 && data.length==0){
				layer.alert("请选择一条信息", {icon : 0,title : '提示',offset : '150px'});
				return;
			}
	 		$("#stateOne").val(num);
	 		flowDetailDeatil = layer.open({
				type : 1,
				title : txt,
				content : $('#intsertFlowData'),
				area : [ '460px', '400px' ]
			});
			form.render();
	 	}
	 	
	 	//小流程步骤模态框
	 	function openFlowStepModal(num){
	 		var flowStepModal = layer.open({
				type : 1,
				title : '小流程步骤模页面',
				content : $('#flowStepModal'),
				area : [ '700px', '520px' ]
			});
	 		if(num==1){
				$(".btn-two").css("display","none");
				$(".btn-one").css("display","block");//隐藏导入按钮
			}else{
				$(".btn-two").css("display","block");
				$(".btn-one").css("display","none");//显示导入按钮
			}
	 		searchFlowStepInfo();
			form.render();
	 	}
	 	
	 	//响应指令模态框
	 	function openResponseOrderModal(){
	 		var flowStepModal = layer.open({
				type : 1,
				title : '响应指令数据页面',
				content : $('#responseOrderModal'),
				area : [ '700px', '520px' ]
			});
	 		searchResponseOrderInfo();
			form.render();
	 	}
	 	
	 	//响应详细数据新增、修改模态框
	 	function editResponseOrder(num){
	 		var checkStatus=table.checkStatus('responseOrderInfo'),
			data=checkStatus.data;
	 		document.all("resetResponseOrder").click();//重置表单
	 		var txt="响应详细数据新增页面";
	 		if(num==2){
	 			txt="响应详细数据修改页面";
	 		}
	 		
	 		if(num==2 && data.length==1){
				loadDatatoForm("formResponseOrder",data[0]);
			}else if(num ==2 && data.length>1){
				layer.alert("只能选择一条信息", {icon : 0,title : '提示',offset : '150px'});
				return;
			}else if(num ==2 && data.length==0){
				layer.alert("请选择一条信息", {icon : 0,title : '提示',offset : '150px'});
				return;
			}
	 		responseOrderModals = layer.open({
				type : 1,
				title : txt,
				content : $('#editresponseOrderModal'),
				area : [ '460px', '400px' ]
			});
			form.render();
	 	}
	 	
	 	//小流程步骤新增页面----下拉框监听 
	 	form.on('select(response_select)', function (data) {
			var select_text = data.elem[data.elem.selectedIndex].text;
          	$("#responseDatas").val(select_text );
          	$("#response_select").next().find("dl").css({ "display": "none" });
          	form.render();
      	});
	 	
	 	//小流程步骤新增页面----响应数据输入框
	    $('#responseDatas').bind('input propertychange', function () {
	    	selectValue("responseDatas","responseData");
	    });
	 	
	 	function selectValue(formate,formateSelect){
	 		var value = $("#"+formate).val();
            $("#"+formateSelect).val(value);
            form.render();
            $("#"+formateSelect).next().find("dl").css({ "display": "block" });
            var dl = $("#"+formateSelect).next().find("dl").children();
            var j = -1;
            for (var i = 0; i < dl.length; i++) {
              if (dl[i].innerHTML.indexOf(value) <= -1) {
                  dl[i].style.display = "none";
                  j++;
              }
              if (j == dl.length-1) {
                  $("#"+formateSelect).next().find("dl").css({ "display": "none" });
              }
            }
	 	}
	 	
	 	//新增文本验证事件
		function verifyInsertInput(ids){
			document.onkeyup=function(){				
				// 合并两个空格为一个空格
	    		String.prototype.ResetBlank=function(){
	    			var regEx = /\s{2,}/g;
	    			
	    			var regexTwo=/[^\w\s]/ig;
	    			var tt= this.replace(regexTwo, '');
	    			return tt.replace(regEx, ' ');
	    		};
	    		var str = $("#"+ids).val();//获取文本框的值
	    		//重置文本框
	    		$("#"+ids).val(str.ResetBlank());//trim()去除首尾空格
	    	}
		}
	 	
		//小流程具体数据新增页面----提交按钮
		form.on('submit(addSmallFlow)', function(data) {
			var field = data.field;
			//去除首尾空格
			field.sendOrders=field.sendOrders.trim();
			field.responseOrders=field.responseOrders.trim();
			if(field.stateOne==1 || field.stateOne==2){
				//新增--小流程具体数据新增
				$.post("${ctx}/dataManage/saveSmallFlowStepInfo.do",field,function(e){
					if(e.text=="success"){
						layer.msg("已保存！",{icon:1,offset:'150px'});
						layer.close(flowDetailDeatil);
						searchFlowStepInfo();
						document.all("addSmallFlowReset").click();//重置表单
					}else{
						layer.msg(e.text,{icon:0,offset:'150px'});
					}
				}); 
			}else if(field.stateOne==3){
				 
			}
			return false;
		});
		
		//删除小流程步骤信息
		$("#delFlowDatas").click(function(){
		    var checkStatus = table.checkStatus('flowStepInfoTwo')//获取选中行数据
	      	,data = checkStatus.data;
		    if(data.length==0){
		    	layer.alert("请选择需要删除的数据", {icon : 0,title : '提示',offset : '150px'});
		    }else if(data.length>0){
		    	layer.confirm('真的要删除选中的信息吗', {
	            	icon: 3,
	                btn: ['确定', '取消']
	            }, function (index) {
	            	layer.close(index);
	            	var ids = "";
		       		for(var i=0;i<data.length;i++){
		       			ids = ids+data[i].flowStepId+",";
		       		}
		       		ids=ids.substring(0,ids.length-1);
		       		$.post("${ctx}/dataManage/deleteSmallFlowStepInfo.do",{flowStepIds:ids},function(e){
		       			if(e.text=="success"){
							layer.msg("删除成功！",{icon:1,offset:'150px'});
						}else{
							layer.alert(e.text,{icon:2,offset:'150px'});
						}
		       		});
	            	//刷新表格
	            	searchFlowStepInfo();
		   		});
		    }
		});
		
		//小流程具体数据新增页面----提交按钮
		form.on('submit(addSmallFlow)', function(data) {
			var field = data.field;
			//去除首尾空格
			field.sendOrders=field.sendOrders.trim();
			field.responseOrders=field.responseOrders.trim();
			if(field.stateOne==1 || field.stateOne==2){
				//新增--小流程具体数据新增
				$.post("${ctx}/dataManage/saveSmallFlowStepInfo.do",field,function(e){
					if(e.text=="success"){
						layer.msg("已保存！",{icon:1,offset:'150px'});
						layer.close(flowDetailDeatil);
						searchFlowStepInfo();
						document.all("addSmallFlowReset").click();//重置表单
					}else{
						layer.msg(e.text,{icon:0,offset:'150px'});
					}
				}); 
			}else if(field.stateOne==3){
				 
			}
			return false;
		});
		
		//响应详细数据新增、修改页面----提交按钮
		form.on('submit(saveResponseOrder)', function(data) {
			var field = data.field;
			//去除首尾空格
			field.responseInstructions=field.responseInstructions.trim();
			//新增--小流程具体数据新增
			$.post("${ctx}/dataManage/saveResponseOrderInfo.do",field,function(e){
				if(e.text=="success"){
					layer.msg("已保存！",{icon:1,offset:'150px'});
					layer.close(responseOrderModals);
					searchResponseOrderInfo();
					document.all("resetResponseOrder").click();//重置表单
				}else{
					layer.msg(e.text,{icon:0,offset:'150px'});
				}
			}); 
			return false;
		});
		
		//删除小流程步骤信息
		function deleteResponseOrder(){
			var checkStatus = table.checkStatus('responseOrderInfo')//获取选中行数据
	      	,data = checkStatus.data;
		    if(data.length==0){
		    	layer.alert("请选择需要删除的数据", {icon : 0,title : '提示',offset : '150px'});
		    }else if(data.length>0){
		    	layer.confirm('真的要删除选中的信息吗', {
	            	icon: 3,
	                btn: ['确定', '取消']
	            }, function (index) {
	            	layer.close(index);
	            	var ids = "";
		       		for(var i=0;i<data.length;i++){
		       			ids = ids+data[i].responseOrderId+",";
		       		}
		       		ids=ids.substring(0,ids.length-1);
		       		$.post("${ctx}/dataManage/deleteResponseOrdernfo.do",{responseOrdernfoIds:ids},function(e){
		       			if(e.text=="success"){
							layer.msg("删除成功！",{icon:1,offset:'150px'});
							//刷新表格
			            	searchResponseOrderInfo();
						}else{
							layer.alert(e.text,{icon:2,offset:'150px'});
						}
		       		});
		   		});
		    }
		}
	 	
	 	//小流程具体数据提交按钮
	  	form.on('submit(formDemo)', function(data){
			$.post("${ctx}/dataManage/insertFlowDetailInfoToSession.do",data.field,function(e){
				if(e==200){
					layer.close(flowDetailDeatil);
					layer.msg("提交成功！");
					flowStepInfo();
				}else if(e==500){
					layer.msg("快照名称已存在，请重新填写");
				}else{
					layer.alert("数据异常！",{icon:2,title:'提示'});
				}
			});
			//document.all("resetAdd").click();//重置表单
			return false; //阻止表单跳转
		});
	 	
	    //监听单元格编辑
	    table.on('edit(flowStepInfo)', function(obj){
	      var value = obj.value //得到修改后的值
	      ,data = obj.data //得到所在行所有键值
	      ,field = obj.field //得到字段
	      ,flag=false;//是否变更标识
	      if(value!=undefined && value !=''){
	    	  flag=true;
	      }
	      //如果变更就提交数据
	      if(flag){
	      	$.getJSON("${ctx}/dataManage/updateFlowDetailInfoToSession.do",data,function(e){
	      		if(e==200){
	      			layer.msg("变更已生效！",{icon:1,offset:'150px'});
	      		}else{
	      			layer.alert("未知异常！变更未生效！",{icon:5,offset:'150px',title:'提示',anim:6});
	      		}
	      		table.reload('flowStepInfo');//重新渲染表格数据
	      	});
	      }
	    });
	</script>
</body>

</html>
