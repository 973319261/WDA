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

<title>My JSP 'accessAuthority.jsp' starting page</title>

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
	}
		
	button.btnstyle{
		height:28px;
		line-height:28px;
		border-radius: 15px;
		font-size: 13px;
	}
	
	.layui-table-grid-down{
		display: none;
	}
</style>
</head>

<body class="layui-view-body">
	<div class="layui-content">
		<div class="layui-page-header">
			<div class="pagewrap">
				<span class="layui-breadcrumb"> <a href="javascript:void(0)">首页</a>
					<a href="javascript:void(0)">账户管理</a> <a href="javascript:void(0)"><cite>权限管理</cite>
				</a>
				</span>
				<h2 class="title">权限管理</h2>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-card">
				<div class="layui-card-body">
					<div class="form-box">
						<div class="layui-form layui-form-item">
							<div class="layui-inline">
								<div class="layui-form-mid">角色类型:</div>
								<div class="layui-input-inline" style="width: 190px;">
									<input type="text" id="roleName" name="roleName" autocomplete="off" class="layui-input">
								</div>
								
								<div class="btn-group">
									<button class="layui-btn layui-btn-sm layui-btn-blue" onclick="SearchTab()">搜索</button>
									<button class="layui-btn layui-btn-sm" id="insertAuthority">新增</button>		
								</div>						
							</div>
						</div>
						<table id="jurisDiction" lay-filter="jurisDiction" style="max-height: 360px;overflow: auto;"></table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- ******************************** 新增模态窗体  *********************** -->
	<div id="insertModal" class="modal">
		<form class="layui-form" id="formInfo" action="">
			<div class="layui-card-body">
				<div class="layui-form-item">
					<div class="layui-inline">
						<div class="layui-form-mid" style="margin-left:50px;">权限类型:</div>
						<div class="layui-input-inline" style="width: 190px;">
							<input type="text" id="roleid" name="roleName" required lay-verify="required"
								autocomplete="off" class="layui-input" onclick="verifyInsertInput()">
						</div>								
						<div class="layui-form-mid" style="margin-left:50px;">描述:</div>
						<div class="layui-input-inline" style="width: 190px;">
							<input type="text" id="describe" name="describe" required lay-verify="required"
								autocomplete="off" class="layui-input">
						</div>					
					</div>
					<table id="moduleInfo" lay-filter="moduleInfo"></table>
				</div>
				<div class="layui-form-item" style="text-align:center;">
				   <button class="layui-btn" lay-submit lay-filter="formInfos" id="save">保存</button>
				</div>
			</div>
		</form>
	</div>
	
	<!-- ******************************** 修改模态窗体  *********************** -->
	<div id="updateModal" class="modal">
		<form class="layui-form" id="updateJurisInfo" action="">
			<div style="display: none">
				 <input type="hidden" id="updateRoleId" name="roleId" />
			</div>
			<div class="layui-card-body">
				<div class="layui-form-item">
					<div class="layui-inline">
						<div class="layui-form-mid" style="margin-left:50px;">角色类型:</div>
						<div class="layui-input-inline" style="width: 190px;">
							<input type="text" id="roleides" name="roleName" required lay-verify="required"
								autocomplete="off" class="layui-input" onclick="verifyUpdateInput()">
						</div>								
						<div class="layui-form-mid" style="margin-left:50px;">描述:</div>
						<div class="layui-input-inline" style="width: 190px;">
							<input type="text" id="describes" name="roleDescribe" required lay-verify="required"
								autocomplete="off" class="layui-input">
						</div>					
					</div>
					<table id="JurisInfo" lay-filter="JurisInfo"></table>
				</div>
				<div class="layui-form-item" style="text-align:center;">
				   <button class="layui-btn" lay-submit lay-filter="formJurisInfo" id="saves">保存</button>
				</div>
			</div>
		</form>
	</div>
	
	<!-- ******************************** 模块明细模态窗体  *********************** -->
	<div id="updateDetailModal" class="modal">
		<form class="layui-form" id="formModuleDetailInfo" action="">
			<!-- <div style="display: none">
				 <input type="hidden" id="detailRoleId" name="roleId" />
			</div> -->
			<div class="layui-card-body">
				<table id="moduleDetailInfo" lay-filter="moduleDetailInfo"></table>
				<div class="layui-form-item" style="text-align:center;">
				   <button class="layui-btn" lay-submit lay-filter="formModuleDetailInfo">保存</button>
				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript"
		src="${ctx}/Content/js/jquery-3.3.1.min.js"></script>
	<script src="${ctx}/Content/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/combobox.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/echoforms.js"></script>

	<!----------------------- 工具条 开关默认关-------------------------->
	<script type="text/html" id="toVoidNoBtn">
	  <input type="checkbox" name="userstate" value="{{d.roleId}}" lay-skin="switch" lay-text="启用|禁用" lay-filter="stateDemo" {{d.isEnable == 1 ? 'checked':''}}>
	</script>
	
	<script type="text/html" id="operation">
	  <input type="checkbox" name="operation" value="{{d.jurisdictionId}}" lay-skin="switch" lay-text="启用|禁用" lay-filter="operation" >
	</script>

	<script type="text/html" id="jurisBtn">
	  <input type="checkbox" name="jurisBtn" value="{{d.isEnable}}" lay-skin="switch" lay-text="启用|禁用" lay-filter="jurisBtn" {{d.isEnable == 1 ? 'checked':''}}>
	</script>
	
	<script type="text/html" id="operationDetail">
	  <input type="checkbox" name="operationDetail" value="{{d.isEnable}}" lay-skin="switch" lay-text="启用|禁用" lay-filter="operationDetail" {{d.isEnable == 1 ? 'checked':''}}>
	</script>
	
	<script>
		/*************************** layui *********************************************/
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.$;
		var upload = layui.upload;
		var path = "${ctx}/content/official/cover/";

		$.ajaxSettings.async = false;

		/*************************** 页面初始化  *********************************************/
		var count;
		var countJuris;//接收权限表格数据长度
		var arrJuris;//声明数组，接收权限switch状态
		var countModule;//接收模块权限明细表格数据长度
		var arrModule;//声明数组，接收模块权限明细switch状态
		var str;
		$(function() {
			SearchTab();//查询数据表格
			SearchModule()//查询模块表格
			count=table.cache['moduleInfo'].length;//获取模块表格的数量
			form.render('select');//重新渲染表格数据
		});

		/*************************** 表格数据查询方法  *********************************************/
		//查询权限主页数据
		function SearchTab() {
			var roleName = $("#roleName").val();
			table.render({
				elem : '#jurisDiction',
				url : '${ctx}/user/findRoleInfo.do',
				where : {
					roleName : roleName,
				},
				page : true, //开启分页
				limit : 10,
				limits : [ 5, 10, 15, 20 ],
				cellMinWidth : 100, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				cols : [ [ //标题栏
					{title : '序号',type:'numbers',align : 'center',unresize : true},
					{field : 'roleName',title : '角色类型',align : 'center',unresize : true}, 
					{field : 'roleDescribe',title : '描述',align : 'center',unresize : true}, 
					{field : 'isEnable',title : '是否启用',align : 'center',unresize : true,templet: '#toVoidNoBtn'},
					{field : 'tovoidno',title : '操作',align : 'center',unresize : true,
						templet:function(data){
							if(data.isEnable){
								return '<button class="layui-btn layui-btn-warm btnstyle" lay-event="edit">修改</button>'
								+ '<button class="layui-btn btnstyle" lay-event="add">模块明细</button>';
							}else{
								return "<button class='layui-btn layui-btn-danger btnstyle' lay-event='del'>删除</button>";
							}
						}
					}
				] ]
			});
		}
		
		//查询模块信息（新增）
		function SearchModule() {
			table.render({
				elem : '#moduleInfo',
				url : '${ctx}/user/findAreaInfo.do',
				page : false, //开启分页
				height:300,
				cellMinWidth : 120, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				cols : [ [ //标题栏
					{field : 'number',title : '序号',type:'numbers',align : 'center',unresize : true},
					{field : 'jurisdictionName',title : '模块名称',align : 'center',unresize : true,width : 312},
					{field : '',title : '操作',align : 'center',unresize : true,width : 314,templet : '#operation'}
				] ]
			});
		}
		
		//查询权限信息（修改）
		function searchJuris(roleId) {
			table.render({
				elem : '#JurisInfo',
				url : '${ctx}/user/findJurisDictionById.do',
				page : false, //开启分页
				height:300,
				where : {
					roleId : roleId,
				},
				cellMinWidth : 120, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				cols : [ [ //标题栏
					{field : 'number',title : '序号',type:'numbers',align : 'center',unresize : true},
					{field : 'jurisdictionName',title : '模块名称',align : 'center',unresize : true,width : 312},
					{field : 'jurisBtn',title : '操作',align : 'center',unresize : true,width : 314,templet : '#jurisBtn'} 
				] ]
			});
			countJuris=table.cache['JurisInfo'].length;//获取模块表格的数量
		}
		
		//查询模块明细信息
		function SearchModuleDetail(roleId) {
			table.render({
				elem : '#moduleDetailInfo',
				url : '${ctx}/user/findModuleDetailById.do',
				page : false, //开启分页
				height:350,
				where : {
					roleId : roleId,
				},
				cellMinWidth : 120, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				cols : [ [ //标题栏
					{field : 'number',title : '序号',type:'numbers',align : 'center',unresize : true},
					{field : 'moduleName',title : '模块明细名称',align : 'center',unresize : true,width : 312},
					{field : '',title : '操作',align : 'center',unresize : true,width : 314,templet : '#operationDetail'}
				] ]
			});
			countModule=table.cache['moduleDetailInfo'].length;//获取模块表格的长度
		}
		
		
		/*************************** 监听工具条  *********************************************/
		table.on('tool(jurisDiction)', function(obj) {
			var data = obj.data;
			/****************** 修改 *******************/
			if (obj.event === 'edit') {
				searchJuris(data.roleId);
				loadDatatoForm("updateJurisInfo", data);
				var info=table.cache['JurisInfo'];
				arrJuris=new Array(countJuris);
				for(var i=0;i<info.length;i++){
					arrJuris[i]=info[i].isEnable;
				}
				
				var index = layer.open({
					type : 1,
					title : '修改数据页面',
					content : $('#updateModal'),
					area : [ '720px', '500px' ],
					resize:false,//是否允许拉伸
					cancel : function(index, layero) {
						if (confirm('确定要关闭么，未保存的数据将会丢失！')) {
							layer.close(index);
						}
						return false;
					}
				});
				form.render();
			} else if (obj.event === 'add') {
				SearchModuleDetail(data.roleId);
				
				//获取数据表格switch数量
				var info=table.cache['moduleDetailInfo'];
				arrModule=new Array(countModule);
				for(var i=0;i<info.length;i++){
					arrModule[i]=info[i].isEnable;
				}
				
				var index = layer.open({
					type : 1,
					title : '更改模块明细权限',
					content : $('#updateDetailModal'),
					area : [ '720px', '500px' ],
					resize:false,//是否允许拉伸
					cancel : function(index, layero) {
						if (confirm('确定要关闭么，未保存的数据将会丢失！')) {
							layer.close(index);
						}
						return false;
					}
				});
				form.render();
			} else if (obj.event === 'del') {
			    /****************** 删除 *******************/
				layer.confirm("确定要删除该条数据吗？", {
					icon : 3,
					title : '提示',
					offset : '150px'
				}, function(index) {
					layer.close(index);
					$.getJSON("${ctx}/user/deleteJurisInfo.do", {
						roleId : data.roleId
					}, function(e) {
						if (e.text=="success") {
							layer.msg("已删除！", {offset : '150px'});
							SearchTab();
						} else {
							layer.alert(e.text, {icon : 2,title : '提示',offset : '150px'});
						}
					});
				});
			}
		});
		
				
		/*************************** 新增权限  *********************************************/
		$("#insertAuthority").click(function() {
			showlayer();
		});		

		/*************************** 打开新增权限窗体  *********************************************/
		function showlayer() {
			var index = layer.open({
				type : 1,
				title : '新增数据页面',
				content : $('#insertModal'),
				area : [ '720px', '500px' ],
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
		
		//声明数组接收
		var arrs=new Array(count);
		//新增switch监听事件
		form.on('switch(operation)', function(data) {
			var checked=data.elem.checked;
			var index=data.othis.parents('tr').attr('data-index');
			arrs[index]=checked;
		});

		//新增保存按钮
		form.on('submit(formInfos)', function(data) {
			var arrJurisdiction=[];
			var roleName=$("#roleid").val();
			var describe=$("#describe").val();
			var num=table.cache['moduleInfo'].length;
			
			for(var i=0;i<num;i++){
				var d=new JurisdictionFun();
				d.isEnable=arrs[i]==true?true:false;
				d.jurisdictionId=table.cache['moduleInfo'][i].jurisdictionId;
				arrJurisdiction.push(d);
			}
			$.post("${ctx}/user/insertRoleType.do",{
				roleName:roleName,describe:describe,
				arrJurisdiction:JSON.stringify(arrJurisdiction)
			},function(data){
				if(data.text=="success"){
					layer.closeAll();
					layer.msg("新增成功", {icon:1,offset : '150px'});
					SearchTab();
					$("#formInfo")[0].reset();
				}else{
					layer.msg(data.text, {icon:2,offset : '150px'});
				}
			});
			return false;
		});
		
		//声明数组接收
		//arrJuris=new Array(countJuris);
		//修改switch监听事件
		form.on('switch(jurisBtn)', function(data) {
			var checked=data.elem.checked;
			var index=data.othis.parents('tr').attr('data-index');
			arrJuris[index]=checked;
		});
		
		
		//修改保存按钮
		form.on('submit(formJurisInfo)', function(data) {
			var arrJurisdiction=[];
			var roleName=$("#roleides").val();
			var describe=$("#describes").val();
			var roleId=$("#updateRoleId").val();
			var num=table.cache['JurisInfo'].length;
			
			for(var i=0;i<num;i++){
				var d=new JurisDetailFun();
				d.isEnable=arrJuris[i]==true?true:false;
				d.jurisdictionDetailId=table.cache['JurisInfo'][i].jurisdictionDetailId;
				arrJurisdiction.push(d);
			}
			$.post("${ctx}/user/updateJurisDiction.do",{
				roleName:roleName,roleDescribe:describe,roleId:roleId,
				arrJurisdiction:JSON.stringify(arrJurisdiction)
			},function(data){
				if(data.text=="success"){
					layer.closeAll();
					layer.msg("修改成功", {icon:1,offset : '150px'});
					SearchTab();
					$("#updateJurisInfo")[0].reset();
				}else{
					layer.msg(data.text, {icon:2,offset : '150px'});
				}
			});
			return false;
		});
		
		//(权限类型)监听启用/禁用 操作
		form.on('switch(stateDemo)', function(obj){
		  	var checked = obj.elem.checked;//获取switch状态
		  	var roleid = obj.value;//获取id
		  	$.getJSON("${ctx}/user/updateRoleTypeState.do",{roleId:roleid,isEnable:checked},function(e){
		  		if(e.text=="success"){
		  			layer.msg("变更已生效",{icon:1,offset:'150px'});
		  		}else{
		  			layer.alert(e.text,{icon:5,title:'提示',offset:'150px',anim:6});
		  		}
		  		table.reload('jurisDiction');//重新渲染表格数据
		  	});
		});
		
		//新增构造函数
		function JurisdictionFun(jurisdictionId,isEnable) {
			this.jurisdictionId=jurisdictionId;
			this.isEnable=isEnable;
		}
		//修改构造函数
		function JurisDetailFun(jurisdictionDetailId,isEnable){
			this.jurisdictionDetailId=jurisdictionDetailId;
			this.isEnable=isEnable;
		}		
		
		
		//新增文本验证事件
		function verifyInsertInput(){
			document.onkeyup=function(){				
				// 合并两个空格为一个空格
	    		String.prototype.ResetBlank=function(){
	    			var regEx = /\s{2,}/g;
	    			return this.replace(regEx, ' ');
	    		};
	    		var str = $("#roleid").val();//获取文本框的值
	    		//重置文本框
	    		$("#roleid").val(str.ResetBlank().trim());//trim()去除首尾空格
	    	}
		}
		
		//修改文本验证事件
		function verifyUpdateInput(){
			document.onkeyup=function(){				
				// 合并两个空格为一个空格
	    		String.prototype.ResetBlank=function(){
	    			var regEx = /\s{2,}/g;
	    			console.log(this);
	    			return this.replace(regEx, ' ');
	    		};
	    		var str = $("#roleides").val();//获取文本框的值
	    		//重置文本框
	    		$("#roleides").val(str.ResetBlank().trim());//trim()去除首尾空格
	    	}
		}
		
		
		//模块明细修改switch监听事件
		form.on('switch(operationDetail)', function(data) {
			var checked=data.elem.checked;//获取switch状态
			var index=data.othis.parents('tr').attr('data-index');//获取switch索引
			arrModule[index]=checked;
		});
		
		
		//模块明细保存按钮
		form.on('submit(formModuleDetailInfo)', function(data) {
			var arrModuleDetail=[];//声明数组集合，用于传参
			var num=table.cache['moduleDetailInfo'].length;//获取表格数据长度
			
			for(var i=0;i<num;i++){
				var d=new ModuleDetailFun();
				d.isEnable=arrModule[i]==true?true:false;
				d.moduleJurisdictionDetailId=table.cache['moduleDetailInfo'][i].moduleJurisdictionDetailId;
				arrModuleDetail.push(d);
			}
			$.post("${ctx}/user/updateModuleDetail.do",{
				arrModuleDetail:JSON.stringify(arrModuleDetail)
			},function(data){
				if(data.text=="success"){
					layer.closeAll();
					layer.msg("修改成功", {icon:1,offset : '150px'});
					SearchTab();//重新查询刷新表格
					$("#formModuleDetailInfo")[0].reset();//重置表单
				}else{
					layer.msg(data.text, {icon:2,offset : '150px'});
				}
			});
			return false;
		});
		
		
		//修改模块权限明细构造函数
		function ModuleDetailFun(moduleJurisdictionDetailId,isEnable){
			this.moduleJurisdictionDetailId=moduleJurisdictionDetailId;
			this.isEnable=isEnable;
		}		
		
		
	</script>
</body>

</html>
