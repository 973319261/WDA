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

<title>My JSP 'seedTurnKeyManage.jsp' starting page</title>

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

	#insertmod .input-box{
		width: 240px;
	}
</style>
</head>

<body class="layui-view-body">
	<div class="layui-content">
		<div class="layui-page-header">
			<div class="pagewrap">
				<span class="layui-breadcrumb"> <a href="javascript:void(0)">首页</a>
					<a href="javascript:void(0)">安全算法管理</a> <a
					href="javascript:void(0)"><cite>Seed转Key管理</cite> </a>
				</span>
				<h2 class="title">Seed转Key管理</h2>
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
										<select id="carTypeId" lay-filter="cartype"></select>
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
										<select id="supplierId" ></select>
									</div>
								</div>
									
								<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">
									<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
										<div class="layui-form-mid" style="width:100%">安全算法:&emsp;</div>
									</div>
									<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
										<select id="arithmeticId" ></select>
									</div>
								</div>
									
								<!-- 按钮组 -->
								<div class="layui-col-xs2 layui-col-sm2 layui-col-md2" style="text-align: center;">
									<div class="btn-group">
										<button class="layui-btn layui-btn-sm layui-btn-blue" onclick="searchArithmeticInfo()">查询</button>
										<button class="layui-btn layui-btn-sm" id="addSeed">添加数据</button>
									</div>
								</div>
								
							</div>
						</div>
						<table id="arithmeticInfo" lay-filter="arithmeticInfo"></table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- ******************************** 新增模态窗体  *********************** -->
	<div id="insertmod" class="modal">
		<form class="layui-form" id="formInfo" action=""
			enctype="multipart/form-data">
			<div class="layui-form-item">
				<label class="layui-form-label">车型</label>
				<div class="layui-input-inline input-box vehicleId">
					<select id="insertCarTypeId" lay-filter="insertCarType" name="vehicleId"></select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">配置</label>
				<div class="layui-input-inline input-box configurationLevelId">
					<select id="insertConfigurationId" lay-filter="insertConfiguration" name="configurationLevelId"></select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">模块</label>
				<div class="layui-input-inline input-box moduleId">
					<select id="insertModuleId" lay-filter="insertModule" name="moduleId"></select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">供应商</label>
				<div class="layui-input-inline input-box supplierId">
					<select id="insertSupplierId" name="supplierId"></select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">算法级别</label>
				<div class="layui-input-inline input-box arithmeticLevelId">
					<select id="arithmeticLevelId" name="arithmeticLevelId" lay-filter="aihao"></select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">DLL接口</label>
				<div class="layui-input-inline input-box">
					<input type="hidden" id="names" name="arithmeticName" />		
					<input type="text" id="arithmeticName" ondblclick="uploadInterface()" 
						placeholder="双击选择DLL接口" required lay-verify="required"
						autocomplete="off" class="layui-input" readonly="readonly" style="cursor: pointer;" >
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">接口函数</label>
				<div class="layui-input-inline input-box">
					<input type="text" id="" name="entryPoint" required lay-verify="required"  onkeyup="value=value.replace(/[\u0391-\uFFE5]/gi,'')"
						autocomplete="off" class="layui-input" maxlength="30">
				</div>
			</div>

			<div class="layui-form-item" style="text-align:center;padding-top: 80px;">
				<button class="layui-btn" lay-submit lay-filter="formDemo">确认</button>
				<button type="reset" class="layui-btn layui-btn-primary" id="reset">重置</button>
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
		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
	</script>

	<script>
		/*************************** layui *********************************************/
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.$;
		var upload = layui.upload;
		$.ajaxSettings.async = false;

		/*************************** 页面初始化  *********************************************/
		$(function() {
			appendOption("carTypeId", "${ctx}/faultCode/findCarTypeInfo.do");
			appendOption("configurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId=0");
			appendOption("moduleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=0&&vehicleId=0");
			appendOption("supplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0");
			appendOption("arithmeticLevelId", "${ctx}/security/findArithmeticLevelInfo.do");
			appendOptionName("arithmeticId", "${ctx}/security/findArithmeticInfo.do?algorithmType=1");
			
			//新增
			appendOption("insertCarTypeId", "${ctx}/faultCode/findCarTypeInfo.do");
			appendOption("insertConfigurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId=0");
			appendOption("insertModuleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=0&&vehicleId=0");
			appendOption("insertSupplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0");
			
			searchArithmeticInfo();//查询数据表格
			form.render('select');//重新渲染表格数据
			//清空临时文件夹
			$.getJSON("${ctx}/security/cleanTemp.do",function(e){});
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

		/*************************** 表格数据查询方法  *********************************************/
		function searchArithmeticInfo() {
			var vehicleId = $("#carTypeId").val();
			var configurationLevelId=$("#configurationId").val();
			var moduleId=$("#moduleId").val();
			var supplierId=$("#supplierId").val();
			var arithmeticName=$("#arithmeticId").val();
			if(arithmeticName =='' || arithmeticName== undefined){
				arithmeticName=null;
			}else{
				arithmeticName=$("#arithmeticId").find("option:selected").text();
			}
			table.render({
				elem : '#arithmeticInfo',
				url : '${ctx}/security/findSeedToKeyInfo.do',
				where : {
					vehicleId:vehicleId,configurationLevelId:configurationLevelId,moduleId:moduleId
					,supplierId:supplierId,arithmeticName:arithmeticName
				},
				page : true, //开启分页
				limit : 5,
				limits : [ 5, 10, 15, 20 ],
				cellMinWidth : 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				cols : [ [ //标题栏
					{ field : 'number',title : '序号',type:'numbers',unresize : true,align : 'center'}, 
					{ field : 'vehicleName',title : '车型',unresize : true,align : 'center'}, 
					{ field : 'configurationLevelName',title : '配置等级',unresize : true,align : 'center'}, 
					{ field : 'moduleName',title : '模块',align : 'center',unresize : true}, 
					{ field : 'supplierName',title : '供应商',align : 'center',unresize : true}, 
					{ field : 'arithmeticName',title : '安全算法',align : 'center',unresize : true}, 
					{ field : 'arithmeticLevelName',title : '算法等级',align : 'center',unresize : true}, 
					{ field : 'visitorVolume',title : '访问次数',align : 'center',unresize : true,sort: true}, 
					{ field : '',title : '操作',align : 'center',unresize : true,toolbar : '#barDemo'}
				] ],done:function(res,curr,count){
					$('.layui-table').css("width","100%");
					$("th[data-field='bar']").css("border-right",'none');
				}
			});
		}
		
		//监听排序事件 
		table.on('sort(arithmeticInfo)', function(obj) { //注：sort 是工具条事件名	
			table.reload('arithmeticInfo', {
				initSort : obj, //记录初始排序，如果不设的话，将无法标记表头的排序状态。
				where : { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
					strName : obj.field, //排序字段
					sortType : obj.type  //排序方式
				}
			});
		});
		
		//监听工具条
		table.on('tool(arithmeticInfo)', function(obj) {
			var data = obj.data;
			if (obj.event == 'del') {
				layer.confirm('确定要删除该条信息？', {
	            	icon: 3,
	                btn: ['确定', '取消']
	            }, function (index) {		
	            	layer.close(index); 
	            	$.post("${ctx}/security/deleteSeedToKeyInfo.do",{arithmeticId:data.arithmeticId,arithmeticLevelId:data.arithmeticLevelId}
	            		,function(datas){
	            		if (datas.text=="success") {
	            			layer.alert("删除成功",{ icon: 1, skin: 'layui-layer-molv', closeBtn: 0, anim: 4 });
	            			searchArithmeticInfo();
                        }else{
                        	layer.alert(datas.text, {icon : 2,title : '提示',offset : '150px'});
                        }
	            	});
		   		});
			}
		});
		
		/*************************** 新增Seed转Key管理  *********************************************/
		$("#addSeed").click(function() {
			showlayer();
		});

		/*************************** 打开新增数据窗体  *********************************************/
		function showlayer() {
			document.all('reset').click();
			var index = layer.open({
				type : 1,
				title : '新增页面',
				content : $('#insertmod'),
				area : [ '430px', '595px' ],
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
		
		//新增----车型下拉框监听事件
		form.on('select(insertCarType)', function(data){
			if(data.value !=''){
				appendOption("insertConfigurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId="+data.value);
				appendOption("insertModuleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=0&&vehicleId=0");
				appendOption("insertSupplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0&&configurationLevelId=0&&vehicleId=0");
				form.render('select');
			}
		});
		
		//新增----配置下拉框监听事件
		form.on('select(insertConfiguration)', function(data){
			if(data.value !=''){
				var vehicleId=$("#insertCarTypeId").val();
				appendOption("insertModuleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId="+data.value+"&&vehicleId="+vehicleId);
				appendOption("insertSupplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0&&configurationLevelId=0&&vehicleId=0");
				form.render('select');
			}
		});
		
		//新增----模块下拉框监听事件
		form.on('select(insertModule)', function(data){
			if(data.value !=''){
				var vehicleId=$("#insertCarTypeId").val();
				var configurationLevelId=$("#insertConfigurationId").val();
				appendOption("insertSupplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId="+data.value+"&&configurationLevelId="+configurationLevelId
						+"&&vehicleId="+vehicleId);
				form.render('select');
			}
		});
		
		//上传DLL接口
		function uploadInterface(){
			$("#uploadFile").click();
		}
		
		//监听DLL文件上传
		var upcover = upload.render({
			elem : '#formDll',
			url : '${ctx}/security/uploadDllFile.do',
			accept: 'file', //普通文件
		    exts: 'dll|DLL', //允许上传的文件后缀
			field:"uploadFile", //默认文件域是file,也可以自己定义,这个和后台struts中获取文件名有关
			before:function(obj){
				onload();
			},
			done : function(res) {
				if (res.code > 0) {
					return layer.msg('上传失败');
				} else {
					var name="";
					var str=res.data[0].split("_");
					var len=str[0].length;
					$("#arithmeticName").val(res.data[0].substring(len+1));
					$("#names").val(res.data[0]);
				}
				onclose();
			}
		});
		
		//表单提交监听——新增
		form.on('submit(formDemo)', function(data) {
			if(data.field.vehicleId == "0"){
				layer.tips('请选择车型','.vehicleId');
			} else if(data.field.configurationLevelId == "0"){
				layer.tips('请选择配置','.configurationLevelId');
			} else if(data.field.moduleId == "0"){
				layer.tips('请选择模块','.moduleId');
			} else if(data.field.supplierId == "0"){
				layer.tips('请选择供应商','.supplierId');
			} else if(data.field.arithmeticLevelId == "0"){
				layer.tips('请选择算法级别','.arithmeticLevelId');
			} else{
				$.getJSON("${ctx}/security/insertSeedToKeyInfo.do", data.field, function(d) {
					if (d.text=="success") {
						layer.closeAll();
						layer.msg("新增成功", { icon:1, offset : '150px' });
						document.all("reset").click();
						searchArithmeticInfo();
						$("#names").val("");
						//更新下拉框
						appendOptionName("arithmeticId", "${ctx}/security/findArithmeticInfo.do?algorithmType=1");
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
