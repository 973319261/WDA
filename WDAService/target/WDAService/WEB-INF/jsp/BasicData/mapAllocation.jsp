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

<title>My JSP 'mapAllocation.jsp' starting page</title>

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
		padding: 20px 20px 20px 0px;
	}
	.layui-icon-ok:before{
		content:"\e605";
		color: #fff;
	}
	
	.layui-form-checked[lay-skin=primary] i{
		border-color: #5FB878!important;
    	background-color: #5FB878;
	}
	
	#insertModal .layui-form-select dl {
	    max-height: 190px;
	}
	
	#updateModal .layui-form-select dl {
	    max-height: 190px;
	}
	#layui-tab-title li{
		margin: 0px 10px 0px 0px;
		padding: 0px 6px;
	}
	.btn-one{
		margin-top: 3px;
	}
</style>
</head>

<body class="layui-view-body">
	<div class="layui-content">
		<div class="layui-page-header" style="margin-bottom: 15px;">
			<div class="pagewrap">
				<span class="layui-breadcrumb"> <a href="javascript:void(0)">首页</a>
				<a href="javascript:void(0)">账户管理</a> <a href="javascript:void(0)"><cite>映射CAN配置管理</cite></a>
				</span>
				<h2 class="title">映射CAN配置管理</h2>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-card">
				<div class="layui-card-body">
					<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
					  <ul class="layui-tab-title" id="layui-tab-title">
					    <li class="layui-this">永久映射</li>
					    <li>临时映射</li>
					  </ul>
					  <div class="layui-tab-content">
					    <div class="layui-tab-item layui-show">
					    	<div class="form-box" id="tableOne">
								<div class="layui-form layui-form-item" style="margin: 5px 0 10px">
									<div class="layui-input-inline" style="width: auto;font-size: 15px;">
										<div class="layui-form-mid" style="float: left;">永久映射DID：</div>
										<!-- <input type="text" id="canPassage" placeholder="输入CAN通道"
											autocomplete="off" class="layui-input"> -->
										<span id="didNameOne" style="line-height: 38px;">C101</span>
									</div>
									<div class="btn-group">
										<button class="layui-btn layui-btn-sm" type="button" onclick="addMapAllocation(1)">增加值</button>
										<button class="layui-btn layui-btn-sm layui-btn-warm" type="button" onclick="editMapAllocation(1)">修改DID</button>
										<button class="layui-btn layui-btn-sm layui-btn-danger" type="button" onclick="deleteCanInfo(1)">删除</button>
									</div>
							    </div>
								<table id="canConfigurationInfo" lay-filter="canConfigurationInfo"></table>
							</div>
						</div>
						<div class="layui-tab-item">
					    	<div class="form-box">
								<div class="layui-form layui-form-item" style="margin: 5px 0 10px">
									<div class="layui-input-inline" style="width: 170px;font-size: 15px;">
										<div class="layui-form-mid" style="float: left;">临时映射DID：</div>
										<!-- <input type="text" id="canPassage" placeholder="输入CAN通道"
											autocomplete="off" class="layui-input"> -->
										<span id="didNameTwo" style="line-height: 38px;"></span>
									</div>
									<div class="btn-group">
										<button class="layui-btn layui-btn-sm" type="button" onclick="addMapAllocation(2)">增加值</button>
										<button class="layui-btn layui-btn-sm layui-btn-warm" type="button" onclick="editMapAllocation(2)">修改DID</button>
										<button class="layui-btn layui-btn-sm layui-btn-danger" type="button" onclick="deleteCanInfo(2)">删除</button>
									</div>
							    </div>
								<table id="canConfigurationInfos" lay-filter="canConfigurationInfos"></table>
							</div>
					    </div>
					  </div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- ******************************** 新增模态窗体  *********************** -->
	<div id="insertModal" class="modal">
		<form class="layui-form" id="formInfo" action="">
			<input name="didName" type="hidden" />
			<input name="mapTypeId" type="hidden" />
			<input id="typeId" type="hidden" />
			<div class="layui-form-item">
				<label class="layui-form-label">CAN通道：</label>
				<div class="layui-input-block">
					<input type="text" name="canPassage" lay-verify="required" autocomplete="off" class="layui-input" maxlength="20" />
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">值：</label>
				<div class="layui-input-block">
					<input type="text" name="canValue" lay-verify="required" autocomplete="off" class="layui-input" maxlength="20" 
						onkeyup="value=value.replace(/[^\da-fA-F]/ig,'')" />
				</div>
			</div>
			<!-- <div class="layui-form-item">
				<label class="layui-form-label">描述：</label>				
				<div class="layui-input-block">
			    	<textarea placeholder="请输入内容" class="layui-textarea" id="numberId" name="canDescription" onkeyup="setLength(this,100,'numbers');"
			    		lay-verify="required" maxlength="100" style="min-height:120px;max-height:180px;resize: none;"></textarea>
			    	<span class="layui-form-label" style="position: absolute;right: 0px;">
				    	<span id="numbers">0</span>/100
				    </span>
			    </div>			    
			</div> -->
			<div class="layui-form-item" style="text-align: center;">
				<div class="btn-group">
					<button class="layui-btn" lay-submit lay-filter="formDemo">提交</button>
					<button type="reset" class="layui-btn layui-btn-primary" id="resetAdd">重置</button>
				</div>
			</div>
		</form>
	</div>
	<!-- ******************************** 修改模态窗体  *********************** -->
	<div id="updateModal" class="modal">
		<form class="layui-form" id="updateInfo" action="">
			<input id="state" type="hidden" />
			<input type="hidden" name="canConfigurationId"/>
			<input type="hidden" name="mapTypeId"/>
			<div class="layui-form-item">
				<label class="layui-form-label">CAN通道：</label>
				<div class="layui-input-inline">
					<input type="text" name="canPassage" lay-verify="required" autocomplete="off" class="layui-input" maxlength="20" />
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">值：</label>
				<div class="layui-input-inline">
					<input type="text" name="canValue" lay-verify="required" autocomplete="off" class="layui-input" maxlength="20" />
				</div>
			</div>
			<!-- <div class="layui-form-item">
				<label class="layui-form-label">描述：</label>				
				<div class="layui-input-block">
			    	<textarea placeholder="请输入内容" class="layui-textarea" id="numberId" name="canDescription" onkeyup="setLength(this,100,'numberTwo');"
			    		lay-verify="required" maxlength="100" style="min-height:120px;max-height:180px;resize: none;"></textarea>
			    	<span class="layui-form-label" style="position: absolute;right: 0px;">
				    	<span id="numberTwo">0</span>/100
				    </span>
			    </div>			    
			</div> -->
			<div class="layui-form-item" style="text-align: center;">
				<div class="btn-group">
					<button class="layui-btn" lay-submit lay-filter="updateForm">提交</button>
				</div>
			</div>
		</form>
	</div>
	<!-- ******************************** 修改DID名称模态窗体  *********************** -->
	<div id="updateDidModal" class="modal" style="padding: 20px 15px;">
		<form class="layui-form" id="updateDidInfo" action="">
			<input type="hidden" name="mapTypeId" id="mapTypeIds" />
			<div class="layui-form-item">
				<div class="layui-form-mid">请输入DID：</div>
				<div class="layui-input-inline">
					<input type="text" id="didNameThree" name="didName" lay-verify="required" autocomplete="off" class="layui-input" maxlength="20" />
				</div>
			</div>
			<div class="layui-form-item" style="text-align: center;padding-top: 10px;">
				<button class="layui-btn" lay-submit lay-filter="updateDidForm">提交</button>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="${ctx}/Content/js/jquery-3.3.1.min.js"></script>
	<script src="${ctx}/Content/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/echoforms.js"></script>
	<!----------------------- 工具条 -------------------------->
	<script type="text/html" id="barOne">
		<a class="layui-btn layui-btn-warm layui-btn-xs btn-one" lay-event="editOne">修改</a>
	</script>
	<script type="text/html" id="barTwo">
		<a class="layui-btn layui-btn-warm layui-btn-xs btn-one" lay-event="editTwo">修改</a>
	</script>

	<script>
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.$;
	 	var layedit = layui.layedit;
	 	var updateModals;//修改映射CAN配置管理
		$.ajaxSettings.async = false;

		$(function() {
			searchCanInfoOne();
			searchCanInfoTwo();
			form.render('select');
		});
	 	
		//查询永久映射CAN配置管理信息
	 	function searchCanInfoOne() {
			var canPassage = $("#canPassage").val();
			table.render({
				elem : '#canConfigurationInfo',
				url : '${ctx}/user/findCanInfo.do',
				where:{
					mapTypeId:1
				},
				page : true, //开启分页
				limit : 6,
				cols : [ [ //标题栏
					{ type:'numbers',field : '',title : '序号',align:'center'}, 
					{ field : 'cover',title : '选择',align:'center',type :'checkbox'},
					{ field : 'canPassage',title : 'CAN通道',align:'center',unresize : true}, 
					{ field : 'canValue',title : '值',align : 'center',unresize : true},
					{ field : '',title : '操作',align : 'center',width:100,unresize : true,toolbar : '#barOne'}
				] ],done:function(res,curr,count){
					if(count!=0){
						$("#didNameOne").text(res.data[0].didName);
					}else{
						$("#didNameOne").text("");
					}
				}
			});
		}
		
		//查询临时映射CAN配置管理信息
	 	function searchCanInfoTwo() {
			var canPassage = $("#canPassage").val();
			table.render({
				elem : '#canConfigurationInfos',
				url : '${ctx}/user/findCanInfo.do',
				where:{
					mapTypeId:2
				},
				page : true, //开启分页
				limit : 6,
				cols : [ [ //标题栏
					{ type:'numbers',field : '',title : '序号',align:'center'}, 
					{ field : 'cover',title : '选择',align:'center',type :'checkbox'},
					{ field : 'canPassage',title : 'CAN通道',align:'center',unresize : true}, 
					{ field : 'canValue',title : '值',align : 'center',unresize : true},
					{ field : '',title : '操作',align : 'center',width:100,unresize : true,toolbar : '#barTwo'}
				] ],done:function(res,curr,count){
					if(count!=0){
						$("#didNameTwo").text(res.data[0].didName);
					}else{
						$("#didNameTwo").text("");
					}
				}
			});
		}
	 	
	 	//新增模态窗体
	 	function addMapAllocation(typeId){
	 		document.all('resetAdd').click();
	 		$("#typeId").val(typeId);
			var index = layer.open({
				type : 1,
				title : '新增页面',
				content : $('#insertModal'),
				area : [ '450px', '300px' ],
				resize:false,//是否允许拉伸
			});
			form.render();                                                                                                                                                                                                                                                 
	 	}
	 	
	 	//修改DID模态窗体
	 	function editMapAllocation(mapTypeId){
	 		var didName;
	 		if(mapTypeId==1){
	 			didName=$("#didNameOne").text();
	 		}else{
	 			didName=$("#didNameTwo").text();
	 		}
			$("#didNameThree").val(didName);
			$("#mapTypeIds").val(mapTypeId);
			var index = layer.open({
				type : 1,
				title : '修改DID',
				content : $('#updateDidModal'),
				area : [ '400px', '200px' ],
				resize:false,//是否允许拉伸
			});
			form.render();
	 		/* var checkStatus=table.checkStatus('canConfigurationInfo'),
			data=checkStatus.data;
	 		if(data.length==0){
				layer.alert("请选择一条数据", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length>1){
				layer.alert("只能选择一条数据", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length==1){
				
			} */
	 	}
	 	
	 	//(永久映射)表单提交监听——新增
		form.on('submit(formDemo)', function(data) {
			var typeId=$("#typeId").val();
			if(typeId==1){
				data.field.didName=$("#didNameOne").text();
			}else{
				data.field.didName=$("#didNameTwo").text();
			}
			data.field.mapTypeId=typeId;
			$.getJSON("${ctx}/user/insertCanInfo.do",data.field, function(d) {
				if (d.text=="success") {
					layer.closeAll();
					layer.msg("新增成功", {icon:1,offset : '150px'});
					document.all("resetAdd").click();
					if(typeId==1){
						searchCanInfoOne();
					}else{
						searchCanInfoTwo();
					}
				} else {
					layer.alert(d.text, {icon : 2,title : '提示',offset : '150px'});
				}
			});
			return false;
		});
	 	
		//监听工具条(永久映射)
		table.on('tool(canConfigurationInfo)', function(obj) {
			var data = obj.data;
			if (obj.event == 'editOne') {
				loadDatatoForm("updateInfo", data);//回填数据
				updateModals = layer.open({
					type : 1,
					title : '修改页面',
					content : $('#updateModal'),
					area : [ '450px', '300px' ],
					resize:false,//是否允许拉伸
				});
			}
		});
	 	
	 	//表单提交监听——修改映射CAN配置管理信息
		form.on('submit(updateForm)', function(data) {
			$.getJSON("${ctx}/user/updateCanInfo.do",data.field, function(d) {
				if (d.text=="success") {
					layer.closeAll();
					layer.msg("修改成功", {icon:1,offset : '150px'});
					if(data.field.mapTypeId==1){
						searchCanInfoOne();
					}else{
						searchCanInfoTwo();
					}
				} else {
					layer.alert(d.text, {icon : 2,title : '提示',offset : '150px'});
				}
			});
			return false;
		});
	 	
	 	//表单提交监听——修改DID信息
		form.on('submit(updateDidForm)', function(data) {
			$.getJSON("${ctx}/user/updateDidIInfoOnCan.do",data.field, function(d) {
				if (d.text=="success") {
					layer.closeAll();
					layer.msg("修改成功", {icon:1,offset : '150px'});
					if(data.field.mapTypeId==1){
						searchCanInfoOne();
					}else{
						searchCanInfoTwo();
					}
				} else {
					layer.alert(d.text, {icon : 2,title : '提示',offset : '150px'});
				}
			});
			return false;
		});
	 	
		//删除映射CAN配置管理信息
	 	function deleteCanInfo(numId){
			var checkStatus;
			if(numId==1){
				checkStatus = table.checkStatus('canConfigurationInfo');//获取选中行数据
			}else{
				checkStatus = table.checkStatus('canConfigurationInfos');//获取选中行数据
			}
			var data = checkStatus.data;
		    if(data.length==0){
		    	layer.alert("请选择需要删除的数据", {icon : 0,title : '提示',offset : '150px'});
		    }else{
		    	layer.confirm('真的要删除选中的数据', {
	            	icon: 3,
	                btn: ['确定', '取消']
	            }, function (index) {
	            	layer.close(index);
	            	var num = 0;
	            	for ( var i = 0; i < data.length; i++) {
	            		$.ajax({
		                	url: "${ctx}/user/deleteCanInfo.do?canConfigurationId=" + data[i].canConfigurationId,                    
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
	            	if(numId==1){
	            		searchCanInfoOne();
	            	}else{
	            		searchCanInfoTwo();
	            	}
		   		});
		    }
	 	}
		
	 	//监听工具条(永久映射)
		table.on('tool(canConfigurationInfos)', function(obj) {
			var data = obj.data;
			if (obj.event == 'editTwo') {
				loadDatatoForm("updateInfo", data);//回填数据
				updateModals = layer.open({
					type : 1,
					title : '修改页面',
					content : $('#updateModal'),
					area : [ '450px', '300px' ],
					resize:false,//是否允许拉伸
				});
			}
		});
	 	
        //文本域字数监听
		function setLength(obj,maxlength,id){
		    var num=maxlength-obj.value.length;
		    var leng=id;
		    if(num<0){
		        num=0;
		    }
		    document.getElementById(leng).innerHTML=num;
		}
        
	</script>
</body>

</html>
