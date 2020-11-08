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

<title>My JSP 'userDetail.jsp' starting page</title>

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
	
	#insertModal .layui-form-label{
		width:115px;
	}
	
	.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
	   top: 50%;
	   transform: translateY(-50%);
	}
	
	.layui-table-cell{
		height:30px;
		line-height: 30px;
	}
	
	.layui-icon-ok:before{
		content:"\e605";
		color: #fff;
	}
	
	.layui-form-checked[lay-skin=primary] i{
		border-color: #5FB878!important;
    	background-color: #5FB878;
	}
	
	#insertModal input{
		width:210px;
	}
	
	.layui-form-select .layui-edge{
		right:-12px;
	}
	.layui-laypage-limits{
		display: none !important;
	}
	
</style>
</head>

<body class="layui-view-body">
	<div class="layui-content">
		<div class="layui-page-header">
			<div class="pagewrap">
				<span class="layui-breadcrumb"> <a href="javascript:void(0)">首页</a>
					<a href="javascript:void(0)">账户管理</a><a href="javascript:void(0)"><cite>用户管理</cite></a>
				</span>
				<h2 class="title">账户管理</h2>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-card">
				<div class="layui-card-body">
					<div class="form-box">
						<div class="layui-form layui-form-item">
							<div class="layui-inline">
								<div class="layui-form-mid">查询:</div>
								<div class="layui-input-inline">
									<input type="text" id="userName" name="userName"
										autocomplete="off" class="layui-input" placeholder="输入用户账号/区域">
								</div>
								<div class="btn-group">
									<button class="layui-btn layui-btn-sm layui-btn-normal" id="selectUser" onclick="SearchTab()">搜索</button>
									<button class="layui-btn layui-btn-sm" id="insertUserDetail">新增</button>
									<button class="layui-btn layui-btn-sm layui-btn-warm" id="updateUserDetail">修改</button>
									<button class="layui-btn layui-btn-sm layui-btn-danger" id="deleteUserDetail">删除</button>
									<button class="layui-btn layui-btn-sm layui-btn-primary" id="cleanAndroidId">清除AndroidID</button>
								</div>
							</div>
						</div>
						<table class="layui-table-cell" id="userDetailInfo" lay-filter="userDetailInfo"></table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- ******************************** 新增、修改模态窗体  *********************** -->
	<div id="insertModal" class="modal">
		<form class="layui-form" id="formInfo" action="">
			<div style="display: none">
				 <input type="hidden" id="userid" name="userId" />
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label" style="width: 90px;">用户账号</label>
					<div class="layui-input-inline">
						<input type="text" id="loginname" name="userAccount" required lay-verify="required" 
							autocomplete="off" class="layui-input" onkeyup="value=value.replace(/[^\w]/ig,'')" maxlength="20">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">用户姓名</label>
					<div class="layui-input-inline">
						<input type="text" id="username" name="userName" required lay-verify="required" maxlength="50"
							autocomplete="off" class="layui-input" onkeyup="this.value=this.value.replace(/\s+/g,'')">
					</div>	
				</div>					
			</div>

			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label" style="width: 90px;">密码</label>
					<div class="layui-input-inline input-group">
						<input type="password" id="password" name="userPassword" placeholder="只能输入数字、英文和小数点"
							autocomplete="off" class="layui-input" onkeyup="value=value.replace(/[^\w\.]/ig,'')" maxlength="50">
					</div>
				</div>
				<div class="layui-inline roleid">
					<label class="layui-form-label">职务</label>
					<div class="layui-input-inline">
						<select id="roleid" name="roleId"></select>
					</div>	
				</div>					
			</div>

			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label" style="width: 90px;">员工编号</label>
					<div class="layui-input-inline">
						<input type="text" id="usernum" name="userNumber" required lay-verify="required" maxlength="20"
							autocomplete="off" class="layui-input" onkeyup="this.value=this.value.replace(/\s+/g,'')">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">软件使用停止期限</label>
					<div class="layui-input-inline">
						<input type="text" id="userdate" name="expirationDate" required lay-verify="required"
							autocomplete="off" class="layui-input">
					</div>	
				</div>					
			</div>	
			
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label" style="width: 90px;">用途</label>
					<div class="layui-input-inline">
						<input type="text" id="purpose" name="purpose" required lay-verify="required" maxlength="50"
							autocomplete="off" class="layui-input" onkeyup="this.value=this.value.replace(/\s+/g,'')">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">员工区域</label>
					<div class="layui-input-inline">
						<input type="text" id="userarea" name="area" required lay-verify="required" maxlength="50"
							autocomplete="off" class="layui-input" onkeyup="this.value=this.value.replace(/\s+/g,'')">
					</div>	
				</div>
			</div>
			
			<div class="layui-form-item" style="text-align:center;padding-top: 120px;">
				<button class="layui-btn" lay-submit lay-filter="formDemo" id="save">保存</button>
				<button type="reset" class="layui-btn layui-btn-primary" id="resetAdd">重置</button>
			</div>
		</form>
	</div>
	<script src="${ctx}/Content/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/combobox.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/echoforms.js"></script>
	<script>
		$.ajaxSettings.async = false;//取消异步
		//绑定时间控件
		layui.use('laydate', function(){
	        var laydate = layui.laydate;
	        //软件使用停止期限
	        laydate.render({
	            elem: '#userdate',
	            type: 'date',
	            min: 0,//只能选择当前日期往后的日期
	        });
	    });
		
		/*************************** layui *********************************************/
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var util = layui.util;
		var $ = layui.$;

		/*************************** 页面初始化  *********************************************/
		$(function() {
			SearchTab();//查询数据表格
			form.render('select');//重新渲染表格数据
		});

		/*************************** 表格数据查询方法  *********************************************/
		function SearchTab() {
			var name = $("#userName").val();

			table.render({
				elem : '#userDetailInfo',
				url : '${ctx}/user/findUserDetail.do',
				where : {
					name : name,
				},
				page : true, //开启分页
				limit : 10,
				limits : [ 5, 10, 15, 20 ],
				cellMinWidth : 120, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				cols : [ [ //标题栏
				{
					field : 'number',
					title : '序号',
					type:'numbers',
					unresize:true,//是否禁用拖拽列宽（默认：false）
					align : 'center',
					fixed: 'left',
				}, {
					field : 'userId',
					title : '选择',
					unresize : true,
					align : 'center',
					type:'checkbox',
					fixed: 'left',
				}, {
					field : 'userAccount',
					title : '用户账号',
					align : 'center',
					unresize : false,
				}, {
					field : 'userName',
					title : '用户姓名',
					align : 'center',
					unresize : true,
				}, {
					field : 'roleName',
					title : '角色',
					align : 'center',
					width:200,
					unresize : true,
				}, {
					field : 'purpose',
					title : '用途',
					align : 'center',
					unresize : true
				}, {
					field : 'userNumber',
					title : '员工编号',
					align : 'center',
				}, {
					field : 'area',
					title : '区域',
					align : 'center',
					unresize : true,
				}, {
					field : 'arithmeticVisit',
					title : '安全算法访问次数',
					align : 'center',
					width:160,
					unresize : true,
					sort: true,//排序
				}, {
					field : 'faultcodeVisit',
					title : '故障码访问次数',
					align : 'center',
					unresize : true,
					width:150,
					sort: true,
				}, {
					field : 'shareVisit',
					title : '分享访问次数',
					align : 'center',
					unresize : true,
					width:130,
					sort: true,
				}, {
					field : 'userPassword',
					title : '密码',
					align : 'center',
					unresize : true,
					templet:function(data){
						return "<span>******</span>";
					}
				}, {
					field : 'androidId',
					title : 'AndroidID',
					align : 'center',
				}, {
					field : 'expirationDate',
					title : '软件使用期限',
					align : 'center',
					width:120,
					unresize : false,
					templet:'<div>{{ layui.util.toDateString(d.expirationDate.time, "yyyy-MM-dd") }}</div>',
				}, {
					field : 'isExpires',
					title : '在线状态',
					align : 'center',
					width:100,
					unresize : true,
					fixed: 'right',
					templet:function(data){
						if(data.isOnline){
							return "<span style='color:green;'>在线</span>"
						}else{
							return "<span style='color:gray;'>离线</span>"
							
						}
					}
				}, {
					field : 'isExpires',
					title : '过期状态',
					align : 'center',
					width:100,
					unresize : true,
					fixed: 'right',
					templet:function(data){
						if(data.isExpires){
							return "<span style='color:red;'>已过期</span>"
						}else{
							return "<span style='color:blue;'>未过期</span>"
						}
					}
				}] ],done:function(res,curr,count){
					//$(".layui-laypage-limits").hide();//隐藏分页下拉选项
				}
			});
		}

		
		/*************************** 新增用户信息  *********************************************/
		$("#insertUserDetail").click(function() {
			showlayer(1);
		});
		
		/*************************** 修改用户信息  *********************************************/
		$("#updateUserDetail").click(function() {
			showlayer(2);
		});

		/*************************** 打开新增用户信息窗体  *********************************************/
		function showlayer(num) {
			var headling;//标题
			document.all("resetAdd").click();//重置表单
			//绑定职务下拉框
			appendOption("roleid", "${ctx}/user/selectRoleInfo.do");
			if(num==1){
				headling="新增数据页面";
				$("#loginname").removeAttr('readonly','readonly');
				$("#password").attr('placeholder',"只能输入数字、英文和小数点");
				$("#password").attr('lay-verify', 'required');
			}else{
				headling="修改数据页面";
				$("#loginname").attr('readonly','readonly');
				$("#password").attr('placeholder',"如需更改请输入");
				$("#password").removeAttr('lay-verify', 'required');
				$("#save").empty().append("修改");
				$("#resetAdd").hide();
			}
			var checkStatus=table.checkStatus('userDetailInfo'),
			data=checkStatus.data;
			if(data.length==0 && num==2){
				layer.alert("请先选择一条信息", {icon : 0,title : '提示',offset : '150px'});
				return;
			}
			if(num==2 && data.length==1){
				var password=data[0].password;
				loadDatatoForm("formInfo",data[0]);
				$("#password").val("");
				$("#userdate").val(layui.util.toDateString(data[0].expirationDate.time, "yyyy-MM-dd"));				
			}else if(num !=1){
				layer.alert("只能选择一条信息", {icon : 0,title : '提示',offset : '150px'});
				return;
			}
			
			var index = layer.open({
				type : 1,
				title : headling,
				content : $('#insertModal'),
				area : [ '780px', '500px' ],
				resize:false,//是否允许拉伸
				cancel : function(index, layero) {
					if (confirm('确定要关闭么，未保存的数据将会丢失！')) { //只有当点击confirm框的确定时，该层才会关闭
						layer.close(index);
					}
					return false;
				},
				end:function(res){
					$("#insertModal").css("display","none");
				}
			});
			form.render();
		}
		
		//表单提交监听——新增&修改
		form.on('submit(formDemo)', function(data) {
			if(data.field.roleId == "0"){
				layer.tips('请选择职务','.roleid');
			}else{
				$.getJSON("${ctx}/user/saveUserInfo.do",data.field, function(d) {
					if (d.code==200) {
						layer.closeAll();
						layer.msg(d.text, {
							icon:1,
							offset : '150px'
						});
						document.all("resetAdd").click();
						SearchTab();
					} else {
						layer.alert(d.text, {
							icon : 2,
							title : '提示',
							offset : '150px'
						});
					}
				});
			}
			return false;
		});
		
		//删除用户信息
		$("#deleteUserDetail").click(function(){
		    var checkStatus = table.checkStatus('userDetailInfo')//获取选中行数据
	      	,data = checkStatus.data;
		    if(data.length==0){
		    	layer.alert("请选择需要删除的数据", {
					icon : 0,
					title : '提示',
					offset : '150px'
				});
		    	return;
		    }
			layer.confirm('真的删除行么', {
            	icon: 3,
                btn: ['确定', '取消']
            }, function (index) {		
            	layer.close(index);
            	var num = 0;	                
            	for ( var i = 0; i < data.length; i++) {
            		$.ajax({
	                	url: "${ctx}/user/deleteUserInfo.do?userId=" + data[i].userId,                    
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
            	SearchTab();
	   		});
		});
		
		//清除AndroidID
		$("#cleanAndroidId").click(function(){
			 var checkStatus = table.checkStatus('userDetailInfo')//获取选中行数据
		      	,data = checkStatus.data;
			 if(data.length==0){
			    layer.alert("请选择需要清除AndroidID的用户", {
					icon : 0,
					title : '提示',
					offset : '150px'
				});
			 }else if(data.length>1){
				 layer.alert("只能选择一条用户信息进行操作", {
					icon : 0,
					title : '提示',
					offset : '150px'
				});
			 }else if(data.length==1){
				 layer.confirm('确定要清除该用户的AndroidID？', {
		            	icon: 3,
		                btn: ['确定', '取消']
		            }, function (index) {		
		            	layer.close(index); 
		            	$.post("${ctx}/user/cleanAndroidId.do",{userId:data[0].userId},function(e){
							if(e.text=="success"){
								layer.msg("清除成功", {
									icon:1,
									offset : '150px'
								});
								SearchTab();
							}else{
								layer.alert(d.text, {icon : 2,title : '提示',offset : '150px'});
							}
						 });
			   		});
			 }
		});
		
		//debugger;
		//监听排序事件 
		table.on('sort(userDetailInfo)', function(obj) { //注：sort 是工具条事件名		
			//console.log(obj.field); //当前排序的字段名
			//console.log(obj.type); //当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
			//console.log(this); //当前排序的 th 对象
			table.reload('userDetailInfo', {
				initSort : obj, //记录初始排序，如果不设的话，将无法标记表头的排序状态。
				where : { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
					strName : obj.field, //排序字段
					sortType : obj.type  //排序方式
				}
			});
		});
		
	</script>
</body>

</html>
