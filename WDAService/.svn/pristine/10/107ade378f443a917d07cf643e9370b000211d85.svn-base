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

<title>My JSP 'vinTurnPinManage.jsp' starting page</title>

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
	
	#insertVinTurnPinModal .layui-input-inline{
		width: 240px;
	}
	
	#insertVinTurnPinModal label{
		width: 70px;
	}
</style>
</head>

<body class="layui-view-body">
	<div class="layui-content">
		<div class="layui-page-header">
			<div class="pagewrap">
				<span class="layui-breadcrumb"> <a href="javascript:void(0)">首页</a>
					<a href="javascript:void(0)">安全算法管理</a> <a
					href="javascript:void(0)"><cite>Vin转Pin管理</cite> </a>
				</span>
				<h2 class="title">Vin转Pin管理</h2>
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
										<select id="carTypeId"></select>
									</div>
								</div>
								
								<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">
									<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
										<div class="layui-form-mid" style="width:100%">安全算法:&emsp;</div>
									</div>
									<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
										<select id="arithmeticId" name=""></select>
									</div>
								</div>
								
								<!-- 按钮组 -->
								<div class="layui-col-xs2 layui-col-sm2 layui-col-md2" style="text-align: center;">
									<div class="btn-group">
										<button class="layui-btn layui-btn-sm layui-btn-blue" onclick="searchArithmeticInfo()">搜索</button>
										<button class="layui-btn layui-btn-sm" id="addVinTurnPin">添加数据</button>
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
	<div id="insertVinTurnPinModal" class="modal">
		<form class="layui-form" id="formVinTurnPin" action="">
			<div class="layui-form-item">
				<label class="layui-form-label">车型</label>
				<div class="layui-input-inline vehicleId">
					<select id="insertCarTypeId" name="vehicleId"></select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">Dll接口</label>
				<div class="layui-input-inline">
					<input type="hidden" id="names" name="arithmeticName" />		
					<input type="text" id="arithmeticName" ondblclick="uploadInterface()" 
						placeholder="双击选择DLL接口" required lay-verify="required" 
						autocomplete="off" class="layui-input" readonly="readonly" style="cursor: pointer;" >
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">接口函数</label>
				<div class="layui-input-inline">
					<input type="text" name="entryPoint" required lay-verify="required" maxlength="30"
						autocomplete="off" class="layui-input">
				</div>
			</div>

			<div class="layui-form-item" style="text-align:center;padding-top: 130px;">
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
			appendOptionName("arithmeticId", "${ctx}/security/findArithmeticInfo.do?algorithmType=2");
			
			//新增
			appendOption("insertCarTypeId", "${ctx}/faultCode/findCarTypeInfo.do");
			
			searchArithmeticInfo();//查询数据表格
			form.render('select');//重新渲染表格数据
			//清空临时文件夹
			$.getJSON("${ctx}/security/cleanTemps.do",function(e){});
		});

		/*************************** 表格数据查询方法  *********************************************/
		function searchArithmeticInfo() {
			var vehicleId = $("#carTypeId").val();
			var arithmeticName=$("#arithmeticId").val();
			if(arithmeticName =='' || arithmeticName== undefined){
				arithmeticName=null;
			}else{
				arithmeticName=$("#arithmeticId").find("option:selected").text();
			}
			table.render({
				elem : '#arithmeticInfo',
				url : '${ctx}/security/findVinToPinInfo.do',
				where : {
					vehicleId:vehicleId,arithmeticName:arithmeticName
				},
				page : true, //开启分页
				limit : 5,
				limits : [ 5, 10, 15, 20 ],
				cellMinWidth : 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				cols : [ [ //标题栏
					{ field : 'number',title : '序号',type:'numbers',unresize : true,align : 'center'}, 
					{ field : 'vehicleName',title : '车型',unresize : true,align : 'center',},
					{ field : 'arithmeticName',title : '安全算法',align : 'center',unresize : true}, 
					{ field : 'visitorVolume',title : '访问次数',align : 'center',unresize : true,sort: true}, 
					{ field : '',title : '操作',align : 'center',unresize : true,toolbar : '#barDemo'} 
				] ]
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
	            	$.post("${ctx}/security/deleteVinTurnPinInfo.do",{arithmeticId:data.arithmeticId},function(datas){
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

		/*************************** 新增数据  *********************************************/
		$("#addVinTurnPin").click(function() {
			showlayer();
		});		

		/*************************** 打开新增数据窗体  *********************************************/
		function showlayer() {
			document.all('reset').click();
			var index = layer.open({
				type : 1,
				title : '添加数据',
				content : $('#insertVinTurnPinModal'),
				area : [ '420px', '440px' ],
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
			} else{
				$.getJSON("${ctx}/security/insertVinTurnPinInfo.do", data.field, function(d) {
					if (d.text=="success") {
						layer.closeAll();
						layer.msg("新增成功", { icon:1, offset : '150px' });
						document.all("reset").click();
						searchArithmeticInfo();
						$("#names").val("");
						//更新下拉框
						appendOptionName("arithmeticId", "${ctx}/security/findArithmeticInfo.do?algorithmType=2");
						//清空临时文件夹
						$.getJSON("${ctx}/security/cleanTemps.do",function(e){});
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
