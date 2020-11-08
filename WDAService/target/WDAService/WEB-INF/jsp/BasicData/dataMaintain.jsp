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

<title>My JSP 'dataMaintain.jsp' starting page</title>

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
	
	/* 设置车型选项卡表格行高 */
	#tableOne table td .layui-table-cell{
		height:50px;
		line-height: 50px;
	}
		
	/* 车型选项卡复选框居中 */
	#tableOne table td .laytable-cell-checkbox {
     	transform: translateY(20%);
    }
    
    /* 隐藏车型选项卡纵向滚动条 */
    .layui-table-body {
    	overflow: hidden;
    }
		
	/* 设置车型选项卡表格图片的样式 */
	#tableOne table td .imgbox img{
		width:80px!important;
		height:100%;
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
	
	#modalUpdateSupplier .layui-input-inline{
		width: 210px;
	}
	
	#layui-tab-title li{
		margin: 0px 10px 0px 0px;
		padding: 0px 6px;
	}
	
	/* 表格复选框选中样式 */
	.layui-icon-ok:before{
		content:"\e605";
		color: #fff;
	}
	
	/* 表格复选框选中样式 */
	.layui-form-checked[lay-skin=primary] i{
		border-color: #5FB878!important;
    	background-color: #5FB878;
	}
	
	#modalSupplier .layui-form-label{
		width:100px;
	}
	
	#modalUpdateSupplier .layui-form-label{
		width:100px;
	}
</style>
</head>

<body class="layui-view-body">
	<div class="layui-content">
		<div class="layui-page-header" style="margin-bottom: 15px;">
			<div class="pagewrap">
				<span class="layui-breadcrumb"> <a href="javascript:void(0)">首页</a>
					<a href="${ctx}/user/toUrl.do?page=/BasicData/moduleSupplier">模块供应商</a>
					<a href="javascript:void(0)">更多操作</a>
				</span>
				<h2 class="title">更多操作</h2>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-card">
				<div class="layui-card-body">				
					<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
					  <ul class="layui-tab-title" id="layui-tab-title">
					    <li class="layui-this">车型</li>
					    <li>配置</li>
					    <li>模块</li>
					    <li>供应商</li>
					  </ul>
					  <div class="layui-tab-content">
					    <div class="layui-tab-item layui-show">
					    	<div class="form-box" id="tableOne">
								<div class="layui-form layui-form-item" style="margin: 5px 0 10px">
									<div class="layui-inline">
										<div class="layui-form-mid">车型:</div>
										<div class="layui-input-inline" style="width: 190px;">
											<input type="text" id="vehicleName" autocomplete="off" class="layui-input" 
												placeholder="输入车型名称">
										</div>
										<div style="padding-top: 5px;padding-left:20px;display:inline-block;">
											<button class="layui-btn layui-btn-sm layui-btn-blue" type="button" onclick="searchCarTypeInfo()">查询</button>
											<button class="layui-btn layui-btn-sm" id="insertCarType">新增</button>
											<button class="layui-btn layui-btn-sm layui-btn-warm" id="updateCarType">修改</button>
											<button class="layui-btn layui-btn-sm layui-btn-danger" id="deleteCarType">删除</button>
											<button class="layui-btn layui-btn-sm layui-btn-blue" type="button" onclick="addVehicleImage()">维护车型图片</button>
										</div>
										
									</div>
								</div>
								<table id="carTypeInfo" lay-filter="carTypeInfo"></table>
							</div>
						</div>
						<div class="layui-tab-item">
					    	<div class="form-box">
								<div class="layui-form layui-form-item" style="margin: 5px 0 10px">
									<div class="layui-inline">
										<div class="layui-form-mid">配置名称:</div>
										<div class="layui-input-inline" style="width: 190px;">
											<input type="text" id="configurationLevelName" autocomplete="off" class="layui-input" 
												placeholder="输入配置名称">
										</div>
										<div style="padding-top: 5px;padding-left:20px;display:inline-block;">
											<button class="layui-btn layui-btn-sm layui-btn-blue" type="button" onclick="searchConfigurationInfo()">查询</button>
											<button class="layui-btn layui-btn-sm" id="addConfiguration">新增</button>
											<button class="layui-btn layui-btn-sm layui-btn-warm" id="updateConfiguration">修改</button>
											<button class="layui-btn layui-btn-sm layui-btn-danger" id="deleteConfiguration">删除</button>
										</div>
									</div>
								</div>
								<table id="configurationInfo" lay-filter="configurationInfo"></table>
							</div>
					    </div>
					    <div class="layui-tab-item">
					    	<div class="form-box">
								<div class="layui-form layui-form-item" style="margin: 5px 0 10px">
									<div class="layui-inline">
										<div class="layui-form-mid">模块名称:</div>
										<div class="layui-input-inline" style="width: 190px;">
											<input type="text" id="moduleName" autocomplete="off" class="layui-input" 
												placeholder="输入模块名称">
										</div>
										<div style="padding-top: 5px;padding-left:20px;display:inline-block;">
											<button class="layui-btn layui-btn-sm layui-btn-blue" type="button" onclick="searchModuleInfo()">查询</button>
											<button class="layui-btn layui-btn-sm" id="addModule">新增</button>
											<button class="layui-btn layui-btn-sm layui-btn-warm" id="updateModule">修改</button>
											<button class="layui-btn layui-btn-sm layui-btn-danger" id="deleteModule">删除</button>
										</div>
									</div>
								</div>
								<table id="moduleInfo" lay-filter="moduleInfo"></table>
							</div>
					    </div>
					    <div class="layui-tab-item">
					    	<div class="form-box">
								<div class="layui-form layui-form-item" style="margin: 5px 0 10px">
									<div class="layui-inline">
										<div class="layui-form-mid">供应商名称:</div>
										<div class="layui-input-inline">
											<input type="text" id="supplierName" autocomplete="off" class="layui-input" 
												placeholder="输入供应商名称">
										</div>		
										<div style="padding-top: 5px;padding-left:20px;display:inline-block;">								
											<button class="layui-btn layui-btn-sm layui-btn-blue" type="button" onclick="searchSupplierInfo()">查询</button>
											<button class="layui-btn layui-btn-sm" id="addSupplier">新增</button>
											<button class="layui-btn layui-btn-sm layui-btn-warm" id="updateSuppliers">修改</button>
											<button class="layui-btn layui-btn-sm layui-btn-danger" id="deleteSupplier">删除</button>
										</div>
									</div>
								</div>
								<table id="supplierInfo" lay-filter="supplierInfo"></table>
							</div>
					    </div>
					  </div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- ******************************** 维护车型图片  *********************** -->
	<div id="insertmod" class="modal" style="padding:20px 0 0">
		<form class="layui-form" id="formInfo" action="" enctype="multipart/form-data">
			<div class="layui-form-item">
				<label class="layui-form-label">选择车型</label>
				<div class="layui-input-inline cartypeImage" style="width:250px;">
					<select lay-filter="carTypePicture" name="vehicleId" id="carTypeIds"></select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">选择图片</label> 
				<input name="vehiclePicture" id="imagepath" type="hidden" />
				<div class="layui-input-block">
					<img src="" width="250" height="150" id="showImage" style="cursor: pointer;" onclick="selCarTypeImage(3)" />
				</div>
			</div>
			<div class="layui-form-item">
				<div style="text-align: center;padding-top: 80px;">
					<button class="layui-btn" lay-submit lay-filter="uploadPicture">提交</button>
					<button type="reset" class="layui-btn layui-btn-primary" style="display: none;" id="resetPicture">重置</button>
				</div>
			</div>
		</form>
	</div>
	<!-- ******************************** 新增车型模态框  *********************** -->
	<div id="insertVehicle" class="modal" style="padding:20px 0 0">
		<form class="layui-form" id="vehicleInfo" action="" enctype="multipart/form-data">
			<div class="layui-form-item">
				<label class="layui-form-label">选择车型</label>
				<div class="layui-input-inline">
					<input type="text" name="vehicleName" autocomplete="off" class="layui-input" required lay-verify="required"
						placeholder="输入车型名称(100字内)" style="width:250px;" maxlength="100">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">选择图片</label> 
				<input id="insertImagePath" name="vehiclePicture" type="hidden" />
				<div class="layui-input-block">
					<img src="" width="250" height="150" id="insertShowImage" style="cursor: pointer;" 
						onclick="selCarTypeImage(1)" />
				</div>
			</div>
			<div class="layui-form-item">
				<div style="text-align: center;padding-top: 80px;">
					<button class="layui-btn" lay-submit lay-filter="insertVehicleInfo">提交</button>
					<button type="reset" class="layui-btn layui-btn-primary" style="display: none;" id="addPicture">重置</button>
				</div>
			</div>
		</form>
	</div>
	<!-- ******************************** 修改车型模态框  *********************** -->
	<div id="updateVehicle" class="modal" style="padding:20px 0 0">
		<form class="layui-form" id="vehicleInfos" action="" enctype="multipart/form-data">
			<input type="hidden" name="vehicleId" />
			<div class="layui-form-item">
				<label class="layui-form-label">选择车型</label>
				<div class="layui-input-inline">
					<input type="text" name="vehicleName" autocomplete="off" class="layui-input" required lay-verify="required"
						placeholder="输入用车型名称" style="width: 250px;" maxlength="100">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">选择图片</label> 
				<input id="updateImagePath" name="vehiclePicture" type="hidden" />
				<div class="layui-input-block">
					<img src="" width="250" height="150" id="updateShowImage" style="cursor: pointer;" onclick="selCarTypeImage(2)" />
				</div>
			</div>
			<div class="layui-form-item">
				<div style="text-align: center;padding-top: 80px;">
					<button class="layui-btn" lay-submit lay-filter="updateVehicleInfo">提交</button>
					<button type="reset" class="layui-btn layui-btn-primary" style="display: none;" id="updatePicture">重置</button>
				</div>
			</div>
		</form>
	</div>
	<!-- ******************************** 新增配置模态框  *********************** -->
	<div id="insertConfigurationModal" class="modal">
		<form class="layui-form" id="insertConfigurationInfo" action="">
			<div class="layui-form-item">
				<label class="layui-form-label">配置名称：</label>
				<div class="layui-input-inline">
					<input type="text" name="configurationLevelName" required lay-verify="required" maxlength="10"
						autocomplete="off" class="layui-input"/>
				</div>
			</div>
			
			<div class="layui-form-item">
				<div style="text-align: center;padding-top: 50px;">
					<button class="layui-btn" lay-submit lay-filter="insertConfiguration">提交</button>
					<button type="reset" class="layui-btn layui-btn-primary" id="resetConfiguration">重置</button>
				</div>
			</div>
		</form>
	</div>
	<!-- ******************************** 修改配置模态框  *********************** -->
	<div id="updateConfigurationModal" class="modal">
		<form class="layui-form" id="updateConfigurationInfo" action="">
			<input type="hidden" name="configurationLevelId" />
			<div class="layui-form-item">
				<label class="layui-form-label">配置名称：</label>
				<div class="layui-input-inline">
					<input type="text" name="configurationLevelName" required lay-verify="required" maxlength="10"
						autocomplete="off" class="layui-input"/>
				</div>
			</div>
			
			<div class="layui-form-item">
				<div style="text-align: center;padding-top: 50px;">
					<button class="layui-btn" lay-submit lay-filter="saveConfiguration">提交</button>
				</div>
			</div>
		</form>
	</div>
	<!-- ******************************** 新增模块模态框  *********************** -->
	<div id="insertModuleModal" class="modal">
		<form class="layui-form" id="insertModuleInfo" action="">
			<div class="layui-form-item">
				<label class="layui-form-label">模块简称：</label>
				<div class="layui-input-inline">
					<input type="text" name="moduleName" required lay-verify="required" maxlength="100"
						autocomplete="off" class="layui-input" style="width:350px;"/>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">模块全称：</label>
				<div class="layui-input-block">
			      <textarea placeholder="请输入内容" name="moduleFullName" class="layui-textarea" 
			      	required lay-verify="required" style="width:350px;min-height:120px;max-height:120px;resize: none;" 
			      	maxlength="250" onkeyup="setLength(this,250,'wordsLength');"></textarea>
			      <span id="wordsLength" style="position:absolute; right:35px; bottom:0vh; font-size:12px; color:#BDCADA;">0/250</span>
			    </div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">Canid值：</label>
				<div class="layui-input-inline">
					<input type="text" name="canidValue" autocomplete="off" class="layui-input" style="width:350px;"/>
				</div>
			</div>
			<div class="layui-form-item">
				<div style="text-align: center;padding-top: 50px;">
					<button class="layui-btn" lay-submit lay-filter="insertModule">提交</button>
					<button type="reset" class="layui-btn layui-btn-primary" id="resetModule">重置</button>
				</div>
			</div>
		</form>
	</div>
	<!-- ******************************** 修改模块模态框  *********************** -->
	<div id="updateModuleModal" class="modal">
		<form class="layui-form" id="updateModuleInfo" action="">
			<input type="hidden" name="moduleId" />
			<div class="layui-form-item">
				<label class="layui-form-label">模块简称：</label>
				<div class="layui-input-inline">
					<input type="text" name="moduleName" required lay-verify="required" maxlength="100"
						autocomplete="off" class="layui-input" style="width:350px;"/>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">模块全称：</label>
				<div class="layui-input-block">
			      <textarea placeholder="请输入内容" name="moduleFullName" class="layui-textarea" 
			      	required lay-verify="required" style="width:350px;min-height:120px;max-height:120px;resize: none"
			      	maxlength="250" onkeyup="setLength(this,250,'wordsLengthEdit');"></textarea>
			      <span id="wordsLengthEdit" style="position:absolute; right:35px; bottom:0vh; font-size:12px; color:#BDCADA;">0/250</span>
			    </div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">Canid值：</label>
				<div class="layui-input-inline">
					<input type="text" name="canidValue" autocomplete="off" class="layui-input" style="width:350px;"/>
				</div>
			</div>
			<div class="layui-form-item">
				<div style="text-align: center;padding-top: 50px;">
					<button class="layui-btn" lay-submit lay-filter="saveModule">提交</button>
				</div>
			</div>
		</form>
	</div>
	<!-- ******************************** 供应商添加模态窗体  *********************** -->
	<div id="modalSupplier" class="modal">
		<form class="layui-form" id="formInfo" action="">			
			<div class="layui-form-item">
				<label class="layui-form-label">供应商名称：</label>
				<div class="layui-input-inline">
					<input type="text" id="supplierNames" name="supplierName" onclick="verifyInsertInput()" required maxlength="100"
						lay-verify="required" autocomplete="off" class="layui-input" style="width:320px;"/>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">供应商代码：</label>
				<div class="layui-input-inline">
					<input type="text" id="" name="supplierCode" onkeyup="value=value.replace(/[^\w\.]/ig,'')"
						autocomplete="off"class="layui-input" maxlength="7" style="width:320px;"/>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">联系人：</label>
				<div class="layui-input-inline">
					<input type="text" id="" name="contacts" onkeyup="this.value=this.value.replace(/\s+/g,'')" required maxlength="50"
						lay-verify="required" autocomplete="off" class="layui-input" style="width:320px;"/>
				</div>
			</div>
			<div class="layui-form-item">
				<div style="text-align: center;padding-top: 120px;">
					<button class="layui-btn" lay-submit lay-filter="insertSupplier">提交</button>
					<button type="reset" class="layui-btn layui-btn-primary" id="resetSupplierAdd">重置</button>
				</div>
			</div>
		</form>
	</div>
	<!-- ******************************** 供应商修改模态窗体  *********************** -->
	<div id="modalUpdateSupplier" class="modal">
		<form class="layui-form" id="updateSupplierInfo" action="">
			<input type="hidden" name="supplierId" />	
			<div class="layui-form-item">
				<label class="layui-form-label">供应商名称：</label>
				<div class="layui-input-inline">
					<input type="text" id="supplierNameTwo" name="supplierName" onclick="verifyUpdateInput()" required maxlength="100"
						lay-verify="required" autocomplete="off" class="layui-input" style="width:320px;"/>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">供应商代码：</label>
				<div class="layui-input-inline">
					<input type="text" id="" name="supplierCode" onkeyup="value=value.replace(/[^\w\.]/ig,'')" maxlength="7"
						 autocomplete="off"class="layui-input" style="width:320px;"/>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">联系人：</label>
				<div class="layui-input-inline">
					<input type="text" id="" name="contacts" onkeyup="this.value=this.value.replace(/\s+/g,'')" required maxlength="50"
						lay-verify="required" autocomplete="off" class="layui-input" style="width:320px;"/>
				</div>
			</div>
			<div class="layui-form-item">
				<div style="text-align: center;padding-top: 120px;">
					<button class="layui-btn" lay-submit lay-filter="updateSupplier">提交</button>
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
	
	<!----------------------- 工具条 -------------------------->
	<script type="text/html" id="operation">
		<a class="layui-btn layui-btn-warm btn-style" lay-event="edit">编辑</a>
		<a class="layui-btn layui-btn-danger btn-style" lay-event="del">删除</a>
	</script>

	<script>
		var typeId;
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.$;
	 	var layedit = layui.layedit;
	 	var upload = layui.upload;
	 	var path = "${ctx}/fileDir/vehicleImage/";
		$.ajaxSettings.async = false;
		
		//自定义验证提示内容
		form.verify({
		  	picture : function(){
		  		var insertPicture=$("#insertShowImage").val();
			    if(insertPicture==""){
			    	return '请选择图片';
			    }			  
	  		}
		});      
		

		//绑定车型下拉框
		$(function() {
			searchCarTypeInfo();//查询车型信息
			searchConfigurationInfo()//查询配置信息
			searchModuleInfo();//查询模块信息
			searchSupplierInfo();//查询供应商信息
			form.render('select');//重新渲染下拉框
		});
		
		//监听工具条
		table.on('tool(supplierInfo)', function(obj) {
			var data = obj.data;
			if (obj.event == 'edit') {
				loadDatatoForm("updateSupplierInfo",data);
				$("#readImage").html("");
				if(data.imagepath !=null && data.imagepath !=''){
					$("#readImage").append("<img src='"+ path + data.imagepath +"' "+
							"style='cursor: pointer;width: 210px;height: 120px;' />");
					$(".readImage").removeAttr("style");
				}else{
					$("#readImage").append("<img style='cursor: pointer;width: 20px;height: 20px;' alt='暂无' />"+
						"<span> 暂无！</span>");
					$(".readImage").attr("style","line-height:34px;")
				}
				var index = layer.open({
					type : 1,
					title : '修改模块供应商信息',
					content : $('#modalUpdateSupplier'),
					area : [ '480px'],
					resize:false,//是否允许拉伸					
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
							layer.msg("已删除！", {offset : '150px'});
							SearchTab();
						} else {
							layer.alert("操作失败", {icon : 2,title : '提示',offset : '150px'});
						};
					});
				});
			};
		});
		
		//查询车型信息
	 	function searchCarTypeInfo() {
			var vehicleName=$("#vehicleName").val();
			table.render({
				elem : '#carTypeInfo',
				url : '${ctx}/supplier/findVehicleInfo.do',
				where:{
					vehicleName:vehicleName
				},
				page : true, //开启分页
				limit : 6,
				cols : [ [ //标题栏
					{ type:'numbers',field : '',title : '序号',align:'center'}, 
					{ field : 'userId',title : '选择',unresize : true,align : 'center',type:'checkbox'},
					{ field : 'cover',title : '车型缩约图',align:'center',width:180,unresize : true,
						templet:function(d){
							if(d.vehiclePicture !=''){
								return '<div class="imgbox"><img src="'+path+d.vehiclePicture+'" /></div>';
							}else{
								return '<div><span>暂无</span></div>';
							}
						}
					},
					{ field : 'vehicleName',title : '车型',align:'center',unresize : true}
				] ],done:function(res,curr,count){
					$('.layui-laypage-limits').hide();
				}
			});
		}
		
	 	//查询配置信息
	 	function searchConfigurationInfo() {
	 		var configurationLevelName=$("#configurationLevelName").val();
			table.render({
				elem : '#configurationInfo',
				url : '${ctx}/supplier/findConfigurationInfo.do',
				where:{
					configurationLevelName:configurationLevelName
				},
				page : true, //开启分页
				limit : 6,
				cols : [ [ //标题栏
					{ type:'numbers',field : '',title : '序号',align:'center'}, 
					{ field : 'configurationLevelId',title : '选择',unresize : true,align : 'center',type:'checkbox'},
					{ field : 'configurationLevelName',title : '配置名称',align : 'center'}
				] ],done:function(res,curr,count){
					$('.layui-laypage-limits').hide();
				}
			});
		}
		
		//查询模块信息
	 	function searchModuleInfo() {
	 		var moduleName=$("#moduleName").val();
			table.render({
				elem : '#moduleInfo',
				url : '${ctx}/supplier/findModuleInfo.do',
				where:{
					moduleName:moduleName
				},
				page : true, //开启分页
				limit : 6,
				cols : [ [ //标题栏
					{ type:'numbers',field : '',title : '序号',align:'center'}, 
					{ field : 'moduleId',title : '选择',unresize : true,align : 'center',type:'checkbox'},
					{ field : 'moduleName',title : '模块名称',width : 180,align : 'center'},
					{ field : 'moduleFullName',title : '模块全称',align : 'center'},
					{ field : 'canidValue',title : 'Canid值',align : 'center',
						templet:function(d){
							if(d.canidValue !=''){
								return '<div><span>'+d.canidValue +'</span></div>';
							}else{
								return '<div><span>暂无</span></div>';
							}
						}
					}
				] ],done:function(res,curr,count){
					$('.layui-laypage-limits').hide();
				}
			});
		}
	 			
		//查询供应商信息
	 	function searchSupplierInfo() {		
	 		var supplierName=$("#supplierName").val();
			table.render({
				elem : '#supplierInfo',
				url : '${ctx}/supplier/findSupplierMessage.do',
				where:{
					supplierName:supplierName
				},
				page : true, //开启分页
				limit : 6,
				cols : [ [ //标题栏
					{ type:'numbers',field : '',title : '序号',align:'center'},
					{ field : 'supplierId',title : '选择',unresize : true,align : 'center',type:'checkbox'},
					{ field : 'supplierName',title : '供应商',align : 'center'}, 
					{ field : 'supplierCode',title : '供应商代码',align : 'center',
						templet:function(d){
							if(d.supplierCode !=''){
								return '<div><span>'+d.supplierCode +'</span></div>';
							}else{
								return '<div><span>暂无</span></div>';
							}
						}
					}, 
					{ field : 'contacts',title : '联系人',width : 180,align : 'center'}
				] ],done:function(res,curr,count){
					$('.layui-laypage-limits').hide();
				}
			});
		}
		
		//新增车型模态框
		$("#insertCarType").click(function(){
			document.all("addPicture").click();
			$("#insertImagePath").val("");
			$("#insertShowImage").attr("src","");
			var index = layer.open({
				type : 1,
				title : '新增车型信息',
				content : $('#insertVehicle'),
				area : [ '435px', '435px' ],
				resize:false,//是否允许拉伸
				cancel : function(index, layero) {
					if (confirm('确定要关闭么，未保存的数据将会丢失！')) { //只有当点击confirm框的确定时，该层才会关闭
						layer.close(index);
					}
					return false;
				}
			});
			form.render();
		});
		
		//新增车型----提交按钮
		form.on('submit(insertVehicleInfo)', function(data) {
			if(data.field.vehiclePicture==""){
				data.field.vehiclePicture=null;
			}
			$.post("${ctx}/supplier/insertVehicleInfo.do",
				data.field, function(e) {
					if (e.text=="success") {
						layer.closeAll();
						layer.msg("提交成功！", {offset : '150px'});
						document.all("addPicture").click();
						$("#insertImagePath").val("");
						$("#insertShowImage").attr("src","");
						searchCarTypeInfo();
					} else {
						layer.alert(e.text, {icon : 2,title : '提示',offset : '150px'});
					}
			});
			return false;
		});
		
		//修改车型模态框
		$("#updateCarType").click(function(){
			var checkStatus=table.checkStatus('carTypeInfo'),
			data=checkStatus.data;
			if(data.length==0){
				layer.alert("请先选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length>1){
				layer.alert("只能选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length==1){
				loadDatatoForm("vehicleInfos",data[0]);
				if(data[0].vehiclePicture!=''){
					//$("#updateImagePath").val(data[0].vehiclePicture);
					$("#updateShowImage").attr("src","${ctx}/fileDir/vehicleImage/"+data[0].vehiclePicture);
				}else{
					//$("#updateImagePath").val("");
					$("#updateShowImage").attr("src","");
				}
				var index = layer.open({
					type : 1,
					title : '修改车型信息',
					content : $('#updateVehicle'),
					area : [ '435px', '435px' ],
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
		
		//修改车型----提交按钮
		form.on('submit(updateVehicleInfo)', function(data) {
			if(data.field.vehiclePicture==""){
				data.field.vehiclePicture=null;
			}
			$.post("${ctx}/supplier/updateVehicleInfo.do",
				data.field, function(e) {
					if (e.text=="success") {
						layer.closeAll();
						layer.msg("提交成功！", {offset : '150px'});
						document.all("updatePicture").click();
						$("#updateImagePath").val("");
						$("#updateShowImage").attr("src","");
						searchCarTypeInfo();
					} else {
						layer.alert(e.text, {icon : 2,title : '提示',offset : '150px'});
					}
			});
			return false;
		});
		
		//删除车型信息
		$("#deleteCarType").click(function(){
			var checkStatus = table.checkStatus('carTypeInfo')//获取选中行数据
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
	                	url: "${ctx}/supplier/deleteVehicleInfo.do?vehicleId=" + data[i].vehicleId,                    
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
            	if(num==data.length){
            		layer.alert(num + "条数据删除成功，"+(data.length - num) + "条数据删除失败!",
                			{ icon: 1, skin: 'layui-layer-molv', closeBtn: 0, anim: 4 });
            	}else{
            		layer.alert(num + "条数据删除成功，"+(data.length - num) + "条数据删除失败!失败原因：可能存在关联的数据,不能删除",
                			{ icon: 1, skin: 'layui-layer-molv', closeBtn: 0, anim: 4 });
            	}
            	//刷新表格
            	searchCarTypeInfo();
	   		});
		});
		
	 	//维护车型图片模态框
	 	function addVehicleImage(){
	 		appendOption("carTypeIds", "${ctx}/faultCode/findCarTypeInfo.do");
			var index = layer.open({
				type : 1,
				title : '维护车型图片',
				content : $('#insertmod'),
				area : [ '435px', '435px' ],
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
	 	
	 	//监听车型下拉框(维护车型模态框)
	 	form.on('select(carTypePicture)', function(datas){
			if(datas.value !="0"){
				$.post("${ctx}/supplier/findCarTypeImageById.do",{vehicleId:datas.value},function(data){
					if(data.vehiclePicture !=undefined){
						$("#showImage").attr("src","${ctx}/fileDir/vehicleImage/" + data.vehiclePicture);
						$("#imagepath").val(data.vehiclePicture);
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
	 	
	 	//上传车型图片
	 	function selCarTypeImage(id) {
	 		typeId=id;
			$("#uploadImage").click();
		}
		
	 	//监听车型图片文件上传
		var upcover = upload.render({
			elem : '#formImage',
			url : '${ctx}/supplier/uploadCarTypeImage.do',
			accept : 'images',
			field:"uploadFile", //默认文件域是file,也可以自己定义,这个和后台struts中获取文件名有关
			before:function(obj){
				onload();
			},
			done : function(res) {
				if (res.code > 0) {
					return layer.msg('上传失败');
				} else {
					if(typeId==1){
						$("#insertShowImage").attr("src","${ctx}/fileDir/temp/" + res.data[0]);
						$("#insertImagePath").val(res.data[0]);
					}else if(typeId==2){
						$("#updateShowImage").attr("src","${ctx}/fileDir/temp/" + res.data[0]);
						$("#updateImagePath").val(res.data[0]);
					}else if(typeId==3){
						$("#showImage").attr("src","${ctx}/fileDir/temp/" + res.data[0]);
						$("#imagepath").val(res.data[0]);
					}
				}
				onclose();
			}
		});
	 	
	 	//维护车型图片----提交按钮
		form.on('submit(uploadPicture)', function(data) {
			if(data.field.vehicleId == "0"){
				layer.tips('请选择车型','.cartypeImage');
				return false;
			}
			if (data.field.vehiclePicture == "") {
				layer.msg("请选择车型图片", {offset : '150px'});
				return false;
			}
			$.post("${ctx}/supplier/saveCarTypeImage.do",
				data.field, function(e) {
					if (e.text=="success") {
						layer.closeAll();
						layer.msg("保存成功！", {offset : '150px'});
						document.all("resetPicture").click();//重置表单
						$("#imagepath").val("");
						$("#showImage").attr("src","");
						searchCarTypeInfo();
					} else {
						layer.alert("保存失败", {icon : 2,title : '提示',offset : '150px'});
					}
			});
			return false;
		});
	 	
		
		//新增配置模态框
		$("#addConfiguration").click(function(){
			document.all("resetConfiguration").click();
			var index = layer.open({
				type : 1,
				title : '模块添加页面',
				content : $('#insertConfigurationModal'),
				area : [ '350px', '250px' ],
				resize:false,//是否允许拉伸
				cancel : function(index, layero) {
					if (confirm('确定要关闭么，未保存的数据将会丢失！')) { //只有当点击confirm框的确定时，该层才会关闭
						layer.close(index);
					}
					return false;
				}
			});
			form.render();
		});
		
		//新增配置----提交按钮
		form.on('submit(insertConfiguration)', function(data) {			
			$.post("${ctx}/supplier/insertConfigurationInfo.do",
				data.field, function(e) {
					if (e.text=="success") {
						layer.closeAll();
						layer.msg("提交成功！", {offset : '150px'});
						document.all("resetConfiguration").click();//重置表单
						searchConfigurationInfo();//刷新表格
					} else {
						layer.alert(e.text, {icon : 2,title : '提示',offset : '150px'});
					}
			});
			return false;
		});
	 	
		//修改配置模态框
		$("#updateConfiguration").click(function(){
			var checkStatus=table.checkStatus('configurationInfo'),
			data=checkStatus.data;
			if(data.length==0){
				layer.alert("请先选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length>1){
				layer.alert("只能选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length==1){
				loadDatatoForm("updateConfigurationInfo",data[0]);				
				var index = layer.open({
					type : 1,
					title : '修改模块信息',
					content : $('#updateConfigurationModal'),
					area : [ '350px', '250px' ],
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
		
		//修改配置----提交按钮
		form.on('submit(saveConfiguration)', function(data) {		
			$.post("${ctx}/supplier/updateConfigurationInfo.do",
				data.field, function(e) {
					if (e.text=="success") {
						layer.closeAll();
						layer.msg("修改成功！", {offset : '150px'});
						searchConfigurationInfo();//刷新表格
					} else {
						layer.alert(e.text, {icon : 2,title : '提示',offset : '150px'});
					}
			});
			return false;
		});
	 	
		//删除配置信息
		$("#deleteConfiguration").click(function(){
			var checkStatus = table.checkStatus('configurationInfo')//获取选中行数据
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
	                	url: "${ctx}/supplier/deleteConfigurationInfo.do?configurationLevelId=" + data[i].configurationLevelId,                    
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
            	if(num==data.length){
            		layer.alert(num + "条数据删除成功，"+(data.length - num) + "条数据删除失败!",
                			{ icon: 1, skin: 'layui-layer-molv', closeBtn: 0, anim: 4 });
            	}else{
            		layer.alert(num + "条数据删除成功，"+(data.length - num) + "条数据删除失败!失败原因：可能存在关联的数据,不能删除",
                			{ icon: 1, skin: 'layui-layer-molv', closeBtn: 0, anim: 4 });
            	}
            	//刷新表格
            	searchConfigurationInfo();
	   		});
		});
	 	
		//新增模块模态框
		$("#addModule").click(function(){
			document.all("resetModule").click();
			var index = layer.open({
				type : 1,
				title : '模块添加页面',
				content : $('#insertModuleModal'),
				area : [ '530px', '435px' ],
				resize:false,//是否允许拉伸
				cancel : function(index, layero) {
					if (confirm('确定要关闭么，未保存的数据将会丢失！')) { //只有当点击confirm框的确定时，该层才会关闭
						layer.close(index);
					}
					return false;
				}
			});
			form.render();
		});
		
		//新增模块----提交按钮
		form.on('submit(insertModule)', function(data) {			
			$.post("${ctx}/supplier/insertModuleInfo.do",
				data.field, function(e) {
					if (e.text=="success") {
						layer.closeAll();
						layer.msg("提交成功！", {offset : '150px'});
						document.all("resetModule").click();//重置表单
						searchModuleInfo();//刷新表格
					} else {
						layer.alert(e.text, {icon : 2,title : '提示',offset : '150px'});
					}
			});
			return false;
		});
		
		//修改模块模态框
		$("#updateModule").click(function(){
			var checkStatus=table.checkStatus('moduleInfo'),
			data=checkStatus.data;
			if(data.length==0){
				layer.alert("请先选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length>1){
				layer.alert("只能选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length==1){
				loadDatatoForm("updateModuleInfo",data[0]);			
				//回填文本域内容字数
				var inputLen=data[0].moduleFullName.length;	
				$("#wordsLengthEdit").text(100-inputLen+"/100");
				var index = layer.open({
					type : 1,
					title : '修改模块信息',
					content : $('#updateModuleModal'),
					area : [ '530px', '435px' ],
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
		
		//修改模块----提交按钮
		form.on('submit(saveModule)', function(data) {		
			$.post("${ctx}/supplier/updateModuleInfo.do",
				data.field, function(e) {
					if (e.text=="success") {
						layer.closeAll();
						layer.msg("修改成功！", {offset : '150px'});
						searchModuleInfo();//刷新表格
					} else {
						layer.alert(e.text, {icon : 2,title : '提示',offset : '150px'});
					}
			});
			return false;
		});
	 	
		//删除模块信息
		$("#deleteModule").click(function(){
			var checkStatus = table.checkStatus('moduleInfo')//获取选中行数据
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
	                	url: "${ctx}/supplier/deleteModuleInfo.do?moduleId=" + data[i].moduleId,                    
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
            	if(num==data.length){
            		layer.alert(num + "条数据删除成功，"+(data.length - num) + "条数据删除失败!",
                			{ icon: 1, skin: 'layui-layer-molv', closeBtn: 0, anim: 4 });
            	}else{
            		layer.alert(num + "条数据删除成功，"+(data.length - num) + "条数据删除失败!失败原因：可能存在关联的数据,不能删除",
                			{ icon: 1, skin: 'layui-layer-molv', closeBtn: 0, anim: 4 });
            	}
            	//刷新表格
            	searchModuleInfo();
	   		});
		});
		
		//新增供应商模态框
		$("#addSupplier").click(function(){
			document.all("resetSupplierAdd").click();
			var index = layer.open({
				type : 1,
				title : '供应商添加页面',
				content : $('#modalSupplier'),
				area : [ '530px', '435px' ],
				resize:false,//是否允许拉伸
				cancel : function(index, layero) {
					if (confirm('确定要关闭么，未保存的数据将会丢失！')) { //只有当点击confirm框的确定时，该层才会关闭
						layer.close(index);
					}
					return false;
				}
			});
			form.render();
		});
	 	
		
		//表单提交监听——新增
		form.on('submit(insertSupplier)', function(data) {
			if(data.field.supplierCode!=''){
				if(data.field.supplierCode.length<7){
					layer.alert("供应商代码应为7位", {icon : 0,title : '提示',offset : '150px'});
					return false;
				}
			}
			$.getJSON("${ctx}/supplier/insertSupplierMessage.do",data.field, function(d) {
				if (d.text=="success") {
					layer.closeAll();
					layer.msg("新增成功", {icon:1,offset : '150px'});
					document.all("resetSupplierAdd").click();
					searchSupplierInfo();
				} else {
					layer.alert(d.text, {icon : 2,title : '提示',offset : '150px'});
				}
			});
			return false;
		});
	
		//修改供应商模态框
		$("#updateSuppliers").click(function(){
			var checkStatus=table.checkStatus('supplierInfo'),
			data=checkStatus.data;
			if(data.length==0){
				layer.alert("请先选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			} else if (data.length>1){
				layer.alert("只能选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			} else if (data.length==1){
				loadDatatoForm("updateSupplierInfo",data[0]);				
				var index = layer.open({
					type : 1,
					title : '修改供应商信息',
					content : $('#modalUpdateSupplier'),
					area : [ '530px', '435px' ],
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
		
		//修改供应商----提交按钮
		form.on('submit(updateSupplier)', function(data) {	
			if(data.field.supplierCode!=''){
				if(data.field.supplierCode.length<7){
					layer.alert("供应商代码应为7位", {icon : 0,title : '提示',offset : '150px'});
					return false;
				}
			}
			$.post("${ctx}/supplier/updateSupplierMessage.do",
				data.field, function(e) {
					if (e.text=="success") {
						layer.closeAll();
						layer.msg("修改成功！", {offset : '150px'});
						searchSupplierInfo();//刷新表格
					} else {
						layer.alert(e.text, {icon : 2,title : '提示',offset : '150px'});
					}
			});
			return false;
		});
		
		//删除模块信息
		$("#deleteSupplier").click(function(){
			var checkStatus = table.checkStatus('supplierInfo')//获取选中行数据
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
	                	url: "${ctx}/supplier/deleteSupplierMessage.do?supplierId=" + data[i].supplierId,                    
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
            	if(num==data.length){
            		layer.alert(num + "条数据删除成功，"+(data.length - num) + "条数据删除失败!",
                			{ icon: 1, skin: 'layui-layer-molv', closeBtn: 0, anim: 4 });
            	}else{
            		layer.alert(num + "条数据删除成功，"+(data.length - num) + "条数据删除失败!失败原因：可能存在关联的数据,不能删除",
                			{ icon: 1, skin: 'layui-layer-molv', closeBtn: 0, anim: 4 });
            	}
            	//刷新表格
            	searchSupplierInfo();
	   		});
		});
		
		//新增文本验证事件
		function verifyInsertInput(){
			document.onkeyup=function(){				
				// 合并两个空格为一个空格
	    		String.prototype.ResetBlank=function(){
	    			var regEx = /\s{2,}/g;
	    			return this.replace(regEx, ' ');
	    		};
	    		var str = $("#supplierNames").val();//获取文本框的值
	    		//重置文本框
	    		$("#supplierNames").val(str.ResetBlank().trim());//trim()去除首尾空格
	    	}
		}
		
		//修改文本验证事件
		function verifyUpdateInput(){
			document.onkeyup=function(){				
				// 合并两个空格为一个空格
	    		String.prototype.ResetBlank=function(){
	    			var regEx = /\s{2,}/g;
	    			return this.replace(regEx, ' ');
	    		};
	    		var str = $("#supplierNameTwo").val();//获取文本框的值
	    		//重置文本框
	    		$("#supplierNameTwo").val(str.ResetBlank().trim());//trim()去除首尾空格
	    	}
		}
		
		//文本域字数监听
		function setLength(obj,maxlength,id){
		    var num=maxlength-obj.value.length;
		    var leng=id;
		    if(num<0){
		        num=0;
		    }
		    document.getElementById(leng).innerHTML=num+"/250";
		}
		
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
