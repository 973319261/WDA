<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

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

<title>诊断小助手后台管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" href="${ctx}/Content/layui/css/layui.css" type="text/css"></link>
<link rel="shortcut icon" href="${ctx}/Content/image/logo.png">
<link rel="stylesheet" href="${ctx}/Content/css/admin.css">
<style type="text/css">
	.welcome{
		margin:auto;
		width:400px;
		height:350px;
		background:url("${ctx}/Content/image/logos.png") no-repeat center center;
		background-size: cover;
	}
	.modal {
		display: none;
		padding: 20px;
	}
	#layui-down .layui-nav-child dd.layui-this,#layui-down dd a{
		background: #fff;
		color:#333;
	}
	#layui-down dd a:hover{
		background: #e9e9e9;
	}
</style>
</head>
<body class="layui-layout-body">
    <div class="layui-layout layui-layout-admin">
        <div class="layui-header custom-header">
            <ul class="layui-nav layui-layout-left">
                <li class="layui-nav-item slide-sidebar" lay-unselect>
                    <a href="javascript:;" class="icon-font"><i class="ai ai-menufold"></i></a>
                </li>
            </ul>
            <ul class="layui-nav layui-layout-right" id="layui-down">
                <li class="layui-nav-item" style="margin-right: 45px;">
                    <a href="javascript:;"><span id="UserName">${admin.data.adminName}</span></a>
                    <dl class="layui-nav-child">
                        <dd><a onclick="updatePassword()">修改密码</a></dd>
                        <dd><a href="JavaScript:void(0)" id="logout">退出</a></dd>
                    </dl>
                </li>
            </ul>
            <input type="hidden" id="userAccount" value="${admin.data.adminAccount}" />
            <input type="hidden" id="adminType" value="${admin.data.adminType}" />
        </div>

        <div class="layui-side custom-admin">
            <div class="layui-side-scroll">
                <div class="custom-logo">
                    <img src="${ctx}/Content/image/logo.png" alt=""/>
                    <h1>诊断小助手</h1>
                </div>
                <ul id="Nav" class="layui-nav layui-nav-tree layui-inline" lay-filter="demo">
                    <li class="layui-nav-item">
                    	<a href="javascript:;">
                    		<i class="layui-icon layui-icon-app"></i>
                    		<em>模块供应商管理</em>
                    	</a>
                    	<dl class="layui-nav-child">
                            <dd><a href="${ctx}/user/toUrl.do?page=/BasicData/dataMaintain">基础数据维护</a></dd>
                        </dl>
                        <dl class="layui-nav-child">
                            <dd><a href="${ctx}/user/toUrl.do?page=/BasicData/moduleSupplier">车型模块供应商</a></dd>
                        </dl>
                   	</li>
                    
                    <li class="layui-nav-item">
                        <a href="javascript:;">
                            <i class="layui-icon layui-icon-chart-screen"></i>
                            <em>安全算法管理</em>
                        </a>
                        <dl class="layui-nav-child">
                            <dd><a href="${ctx}/user/toUrl.do?page=/BasicData/seedTurnKeyManage">Seed转Key管理</a></dd>
                        </dl>
                        <dl class="layui-nav-child">
                            <dd><a href="${ctx}/user/toUrl.do?page=/BasicData/vinTurnPinManage">Vin转Pin管理</a></dd>
                        </dl>
                        <dl class="layui-nav-child">
                            <dd><a href="${ctx}/user/toUrl.do?page=/BasicData/vinTurnEskManage">Vin转Esk管理</a></dd>
                        </dl>
                        <dl class="layui-nav-child">
                            <dd><a href="${ctx}/user/toUrl.do?page=/BasicData/seedAndPinTurnKeyManage">Seed&Pin转Key管理</a></dd>
                        </dl>
                    </li>
                   <li class="layui-nav-item">
                    	<a href="javascript:;">
                    	<i class="layui-icon layui-icon-template-1"></i>
                    		<em>数据管理</em>
                    	</a>
                    	<dl class="layui-nav-child">
                            <dd><a href="${ctx}/user/toUrl.do?page=/BasicData/faultCodeManage">故障码管理</a></dd>
                        </dl>
                    	<dl class="layui-nav-child">
                            <dd><a href="${ctx}/user/toUrl.do?page=/BasicData/didManage">DID管理</a></dd>
                        </dl>
                        <dl class="layui-nav-child">
                            <dd><a href="${ctx}/user/toUrl.do?page=/BasicData/nodeOnline">节点在线管理</a></dd>
                        </dl>
                        <dl class="layui-nav-child">
                            <dd><a href="${ctx}/user/toUrl.do?page=/BasicData/snapshotManage">快照信息管理</a></dd>
                        </dl>
                        <dl class="layui-nav-child">
                        	<dd><a href="${ctx}/user/toUrl.do?page=/BasicData/smallFlow">小流程</a></dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;">
                            <i class="layui-icon layui-icon-share"></i>
                            <em>通知分享管理</em>
                        </a>
                        <dl class="layui-nav-child">
                            <dd><a href="${ctx}/user/toUrl.do?page=/BasicData/shareIssue">分享发布</a></dd>
                            <dd><a href="${ctx}/user/toUrl.do?page=/BasicData/ecuManage">DID转换工具</a></dd>
                            <dd><a href="${ctx}/user/toUrl.do?page=/BasicData/informManage">通知管理</a></dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;">
                            <i class="layui-icon layui-icon-friends"></i>
                            <em>账户管理</em>
                        </a>
                        <dl class="layui-nav-child">
                            <dd><a href="${ctx}/user/toUrl.do?page=/BasicData/userDetail">用户管理</a></dd>
                        </dl>
                        <dl class="layui-nav-child">
                            <dd><a href="${ctx}/user/toUrl.do?page=/BasicData/hardwareManage">硬件管理</a></dd>
                        </dl>
                        <dl class="layui-nav-child">
                            <dd><a href="${ctx}/user/toUrl.do?page=/BasicData/accessAuthority">权限管理</a></dd>
                        </dl>
                        <dl class="layui-nav-child admin-box">
                            <dd><a href="${ctx}/user/toUrl.do?page=/BasicData/accountManage">网页端账号管理</a></dd>
                        </dl>
                        <dl class="layui-nav-child">
                            <dd><a href="${ctx}/user/toUrl.do?page=/BasicData/mapAllocation">映射CAN配置管理</a></dd>
                        </dl>
                    </li>     
                   	<li class="layui-nav-item">
                        <a href="javascript:;">
                            <i class="layui-icon layui-icon-cellphone"></i>
                            <em>App</em>
                        </a>
                        <dl class="layui-nav-child">
                            <dd><a href="${ctx}/user/toUrl.do?page=/BasicData/versionUpdating">版本更新</a></dd>
                        </dl>                        
                    </li>
                </ul>
            </div>
        </div>
		<!-- 嵌套层内容 -->
        <div class="layui-body" style="height: 95%;">
             <div class="layui-tab app-container" lay-allowClose="true" lay-filter="tabs">
                <ul id="appTabs" class="layui-tab-title custom-tab"></ul>
                <div class="layui-tab-content">
                	<div id="welcome" style="width:100%;height:100%;display:flex;">
                		<div class="welcome"></div>
                	</div>
                </div>
            </div>
        </div>
        <!-- <div class="layui-footer"></div> -->
        <!-- <div class="mobile-mask"></div> -->
    </div>
    
    <!-- ******************************** 修改密码模态框  *********************** -->
	<div id="updatePasswordModal" class="modal" style="padding-left:0px;">
		<form class="layui-form" id="updatePasswordInfo" action="">
			<input type="hidden" id="userid" name="adminId" value="${admin.data.adminId}" />
			<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs12 layui-col-sm12 layui-col-md12" style="padding-top: 20px;">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<label class="layui-form-label">旧密码：</label>
					</div>
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<input type="password" name="oldPassword" required lay-verify="required"
							autocomplete="off" class="layui-input" />
					</div>
				</div>
				
				<div class="layui-col-xs12 layui-col-sm12 layui-col-md12" style="padding-top: 20px;">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<label class="layui-form-label">新密码：</label>
					</div>
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<input type="password" name="newPassword" required lay-verify="required" autocomplete="off" placeholder="只能输入数字、英文和小数点"
							class="layui-input" onkeyup="value=value.replace(/[^\w\.]/ig,'')" maxlength="30" />
					</div>
				</div>
				
				<div class="layui-col-xs12 layui-col-sm12 layui-col-md12" style="padding-top: 20px;">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<label class="layui-form-label">确认密码：</label>
					</div>	
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<input type="password" id="userPassword" name="adminPassword" autocomplete="off" class="layui-input" 
							required lay-verify="required" maxlength="30" />
					</div>
				</div>
				
				<div class="layui-form-item" style="padding-top: 30px;text-align: center;">
					<div class="btn-group">
						<button class="layui-btn" lay-submit lay-filter="editPassword">提交</button>
						<button type="reset" class="layui-btn layui-btn-primary" id="resetPassword">重置</button>
					</div>
				</div>
			</div>	
		</form>
	</div>
    <script type="text/javascript" src="${ctx}/Content/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/Content/layui/layui.js"></script>
	<script type="text/javascript" src="${ctx}/Content/layui/layui.all.js"></script>
    <script src="${ctx}/Content/js/home.js"></script>
    <script type="text/javascript">
	    /*************************** layui *********************************************/
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var util = layui.util;
		var $ = layui.$;
    	$.ajaxSettings.async = false;
    	
    	$(function(){
    		var adminId=$("#adminType").val();
    		if(adminId!=0){
    			$(".admin-box").attr("style","display:none");
    		}else{
    			$(".admin-box").removeAttr("style","display:none");
    		}
    	});
    	
    	//退出登录
    	layui.use('layer',function(){
   			$("#logout").click(function(){
    			layer.confirm("确定要退出登录吗？",{icon:3,title:'提示',btn:['确定','取消'],offset:'150px'},function(index){
    				layer.close(index);
    				window.location.href="${ctx}/user/loginOut.do";
    			});
    		});
    	});
    
    	//顶部分页导航栏
    	$("#appTabs").click(function(){
    		if($("#appTabs").find('li').length==0){
    			$("#welcome").css("display","flex");
    		}
    	});
    	
    	//修改密码
    	function updatePassword(){
    		var userId=$("#userid").val();
    		var userAccount=$("#userAccount").val();
    		if(userId !='' && userId>0){
    			if(userAccount=="gl002"){
    				layer.alert("该账号暂不支持修改密码", { icon : 2,title : '提示',offset : '150px'});
    			}else{
    				var index = layer.open({
        				type : 1,
        				title : '密码修改页面',
        				content : $('#updatePasswordModal'),
        				area : [ '450px', '340px' ],
        				resize:false,//是否允许拉伸
        			});
    			}
    		}else{
    			layer.alert("您还未登录，请先登录", { icon : 0,title : '提示',offset : '150px'});
    		}
			form.render();
    	}
    	
    	//表单提交监听——新增&修改
		form.on('submit(editPassword)', function(data) {
			if(data.field.newPassword==data.field.adminPassword){
				$.getJSON("${ctx}/user/updateUserPassword.do",{adminId:data.field.adminId,oldPassword:data.field.oldPassword,
						adminPassword:data.field.adminPassword}, function(d) {
					if (d.text=="success") {
						layer.closeAll();
						layer.msg("密码修改成功，请重新登录", {icon:1,offset : '150px'});
				   		setTimeout(function(){
				   			window.location.href="${ctx}/user/loginOut.do";
				 		}, 1800);
					} else {
						layer.alert(d.text, {icon : 2,title : '提示',offset : '150px'});
					}
				});
			}else{
				layer.tips('两次密码输入不一致','#userPassword');
			}
			return false;
		});
    	
    </script>
</body>
</html>
