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

<title>My JSP 'informManage.jsp' starting page</title>

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
	
	.layui-icon-ok:before{
		content:"\e605";
		color: #fff;
	}
	
	.layui-form-checked[lay-skin=primary] i{
		border-color: #5FB878!important;
    	background-color: #5FB878;
	}
	
</style>
</head>

<body class="layui-view-body">
	<div class="layui-content">
		<div class="layui-page-header" style="margin-bottom: 15px;">
			<div class="pagewrap">
				<span class="layui-breadcrumb"> <a href="javascript:void(0)">首页</a>
					<a href="javascript:void(0)">通知分享管理</a>
					<a href="javascript:void(0)">通知管理</a>
				</span>
				<h2 class="title">通知管理</h2>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-card">
				<div class="layui-card-body">
					<div class="form-box">
						<div class="layui-form layui-form-item" style="margin: 5px 0 10px">
							<div class="layui-inline">
								<div class="layui-form-mid">通知标题:</div>
								<div class="layui-input-inline">
									<input type="text" id="informTitle" name="informtitle"
										lay-verify="required" autocomplete="off" class="layui-input">
								</div>
								
								<div class="btn-group">
									<button class="layui-btn layui-btn-sm layui-btn-blue" type="button" onclick="SearchTab()" style="margin-right:50px;">查询</button>
									<button class="layui-btn layui-btn-sm" type="button" onclick="insertInform()">发送通知</button>
									<button class="layui-btn layui-btn-sm layui-btn-danger" type="button" onclick="deleteInform()">删除</button>
								</div>
							</div>
						</div>
						<table id="inform" lay-filter="inform"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- ******************************** 新增通知模态窗体  *********************** -->
	<div id="insertInform" class="modal" style="padding-left:0px;">
		<form class="layui-form" id="formInfo" action="">
			<div class="layui-form-item">
				<label class="layui-form-label">通知标题：</label>
				<div class="layui-input-inline">
					<input type="text" id="titleId" name="informTitle" required lay-verify="required"
						autocomplete="off" class="layui-input" maxlength="100" style="width:570px;padding-right: 80px;"/>
				</div>		
				<span class="layui-form-label" style="position: absolute;right: 15px;top: 20px;">
			    	<span id="numbers">0</span>/100
			    </span>		
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">通知内容：</label>
				<div class="layui-input-block">
					<textarea class="layui-textarea" placeholder="请输入内容" name="informContent"
						required lay-verify="required" style="min-height:200px;max-height:200px;resize: none;"
						maxlength="500" onkeyup="setLength(this,500,'wordsLength');"></textarea>
					<span id="wordsLength" style="position:absolute; right:5px; bottom:-2vh; font-size:12px; color:#BDCADA;">0/500</span>
				</div>
			</div>
			<div class="layui-form-item">
				<div style="text-align: center;padding-top: 40px;">
					<button class="layui-btn" lay-submit lay-filter="send">发送</button>
					<button type="reset" class="layui-btn layui-btn-primary" id="resetAdd">重置</button>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="${ctx}/Content/js/jquery-3.3.1.min.js"></script>
	<script src="${ctx}/Content/layui/layui.all.js"></script>
	<script>
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.$;
		$.ajaxSettings.async = false;

		$(function() {
			SearchTab();
		});
		
		//查询通知管理信息
	 	function SearchTab() {
			var informTitle = $("#informTitle").val();
			table.render({
				elem : '#inform',
				url : '${ctx}/share/findInform.do',
				where:{
					informTitle:informTitle
				},
				page : true, //开启分页
				limit : 10,
				cols : [ [ //标题栏
				{ field : '',type:'numbers',title : '序号',align:'center' }, 
				{ field : 'informId',title : '选择',align:'center',type :'checkbox' },
				{ field : 'informTitle',title : '通知标题',align:'center' }, 
				{ field : 'informContent',title : '通知内容',align : 'center' }, 
				{ field : 'creationTime',title : '创建时间',width : 180,align : 'center',
					templet:'<div>{{ layui.util.toDateString(d.creationTime.time, "yyyy-MM-dd HH:mm:ss") }}</div>'}, 
				{ field : 'adminName',title : '编辑者',width : 180,align : 'center'} 
				] ],done:function(res,curr,count){
					$(".layui-laypage-limits").hide();//隐藏分页下拉选项
				}
			});
		}
		
	 	//发送通知模态窗体
	 	function insertInform(){
			var index = layer.open({
				type : 1,
				title : '新增通知',
				content : $('#insertInform'),
				area : [ '700px', '450px' ],
				resize:false,//是否允许拉伸
			});
			form.render();
	 	}
		
	 	//表单提交监听——新增
		form.on('submit(send)', function(data) {
			var loading = layer.msg('正在发送', {icon: 16, shade: 0.3, time:0});
			$.getJSON("${ctx}/share/insertInform.do",data.field, function(d) {
				if (d.text=="success") {
					layer.closeAll();
					layer.msg("发送成功", {
						icon:1,
						offset : '150px'
					});
					document.all("resetAdd").click();
					SearchTab();
				} else {
					layer.alert(d.text, {icon : 2,title : '提示',offset : '150px'});
				}
			});
			return false;
		});
	 	
		//删除通知管理信息
		function deleteInform(){
			var checkStatus = table.checkStatus('inform')//获取选中行数据
	      	,data = checkStatus.data;
		    if(data.length==0){
		    	layer.alert("请选择需要删除的数据", {icon : 0,title : '提示',offset : '150px'});
		    	return;
		    }
			layer.confirm('真的要删除么？', {
            	icon: 3,
                btn: ['确定', '取消']
            }, function (index) {		
            	layer.close(index); 
            	var num = 0;	                
            	/* for ( var i = 0; i < data.length; i++) {
            		$.ajax({
	                	url: "${ctx}/share/deleteInform.do?informId=" + data[i].informId,                    
	                	type: "post",//数据传输通道的类型
	                    async: false,
	                    dataType: "json",//传输的数据的类型
	                    success: function (datas) {//直接理解为回调函数
	                 		if (datas.text=="success") {
	                 			num++;	//删除成功
	                        }
	                    }
	                });
            	} */
            	layer.alert(num + "条数据删除成功，"+(data.length - num) + "条数据删除失败!",{ icon: 1, skin: 'layui-layer-molv', closeBtn: 0, anim: 4 });
            	//刷新表格
            	SearchTab();
	   		});
		}
		
		
		//监听input文本可输入字数
        $("#titleId").on("input",function(e){
            //获取input输入的值
            if(e.delegateTarget.value.length<=100){
            	var show = document.getElementById("numbers");
                show.innerHTML = Math.floor(e.delegateTarget.value.length);
            }
            if(e.delegateTarget.value.length>=100){
            	$('#numbers').attr("style","color:red");
            }else{
            	$('#numbers').removeAttr("style","color:red");
            }
        });
		
		
      //文本域字数监听
		function setLength(obj,maxlength,id){
		    var num=maxlength-obj.value.length;
		    var leng=id;
		    if(num<0){
		        num=0;
		    }
		    document.getElementById(leng).innerHTML=num+"/500";
		}
		
	</script>
</body>

</html>
