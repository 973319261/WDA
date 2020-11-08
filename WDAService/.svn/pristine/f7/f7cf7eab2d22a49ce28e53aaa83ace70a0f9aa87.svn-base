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

<title>My JSP 'snapshotManage.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" href="${ctx}/Content/layui/css/layui.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/Content/css/view.css" type="text/css"></link>

<style type="text/css">

	.modal {
		display: none;
		padding: 20px;
	}
	
	.layui-table-view .layui-form-checkbox {
		top: 5px !important;
	}
	
	.layui-table-view .layui-form-radio {
		padding-top: 15px !important;
	}
	
	.layui-icon-ok:before{
		content:"\e605";
		color: #fff;
	}
	
	.layui-form-checked[lay-skin=primary] i{
		border-color: #5FB878!important;
    	background-color: #5FB878;
	}
	
	/* 栅格底部边距 */
	.bottomMargin{
		margin-bottom: 5px;
	}	
	
	/* 空栅格样式，防止不设置内容被占用 */
	.empty{
		border: 1px solid transparent;
	}
	
	.noEmpty{		
		line-height: 38px;
		font-weight: bold;
	}
	
	/* 自定义文本框样式 */
	.custom-input{
		height: 38px;
	    line-height: 1.3;
	    border-width: 1px;
    	border-style: solid;
	    background-color: #fff;
	    border-radius: 2px;
	    border-color: #D2D2D2!important;
	    padding-left: 10px;
	}
	
	/* 设置单选框样式 */
	.layui-form-radio {
	    line-height: 0px;
	    margin: 0px;
	}
	
	#allocationModal .layui-form-select dl {
		max-height: 150px;
	}
	
	#updateModal .input-box{
		width: 240px;
	}
	
	#updateModal .layui-form-select dl {
		max-height: 170px;
		max-width: 240px;
	}
	
	.layui-form-radio > i{
		font-size: 20px !important;
	}
</style>
</head>

<body class="layui-view-body">
	<div class="layui-content">
		<div class="layui-page-header" style="margin-bottom: 15px;">
			<div class="pagewrap">
				<span class="layui-breadcrumb"> <a href="javascript:void(0)">首页</a>
				<a href="javascript:void(0)">数据管理</a><a href="javascript:void(0)"><cite>快照信息管理</cite></a>
				</span>
				<h2 class="title">快照信息管理</h2>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-card">
				<div class="layui-card-body">
					<div class="form-box">
						<div class="layui-form layui-form-item">
							<div class="layui-col-xs10 layui-col-sm10 layui-col-md10">
								<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
									<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
										<div class="layui-form-mid" style="width:100%">选择车型:&emsp;</div>
									</div>		
									<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
										<select id="carTypeId" lay-filter="cartypeOne"></select>
									</div>
								</div>
									
								<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
									<div class="layui-col-xs5 layui-col-sm5 layui-col-md5" style="text-align: right;">
										<div class="layui-form-mid" style="width:100%">选择模块:&emsp;</div>
									</div>
									<div class="layui-col-xs7 layui-col-sm7 layui-col-md7" >
										<select id="moduleId" lay-filter="module"></select>
									</div>
								</div>
																	
								<!-- 按钮组 -->
								<div class="layui-col-xs4 layui-col-sm4 layui-col-md4" style="text-align: center;">
									<div class="btn-group">
										<button class="layui-btn layui-btn-sm layui-btn-blue" onclick="searchSnapshotInfo()">搜索</button>
										<button class="layui-btn layui-btn-sm" onclick="insertSnapshot()">新增</button>
										<button class="layui-btn layui-btn-sm layui-btn-warm" onclick="showUpdateModal()">修改</button>
										<button class="layui-btn layui-btn-sm layui-btn-danger" id="deleteSnapshot">删除</button>
										<!-- <button class="layui-btn layui-btn-sm" onclick="addAllocation()">测试配置</button> -->
									</div>
								</div>
								
							</div>
						</div>
						<table id="snapshootInfo" lay-filter="snapshootInfo"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 主页新增快照信息模态框 -->
	<div id="addSnapshootModal" class="modal">
		<div class="form-box">
			<div class="layui-form layui-form-item">
				<div class="layui-col-xs11 layui-col-sm11 layui-col-md11">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<div class="layui-col-xs4 layui-col-sm4 layui-col-md4" style="text-align: right;">
							<div class="layui-form-mid" style="width:100%">车型:&emsp;</div>
						</div>					
						<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
							<select id="vehicleIds" name="vehicleId" lay-filter="cartypeTwo"></select>
						</div>
					</div>
					
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<div class="layui-col-xs4 layui-col-sm4 layui-col-md4" style="text-align: right;">
							<div class="layui-form-mid" style="width:100%">配置:&emsp;</div>
						</div>					
						<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
							<select id="configurationIds" lay-filter="configurationTwo"></select>
						</div>
					</div>
					
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<div class="layui-col-xs4 layui-col-sm4 layui-col-md4" style="text-align: right;">
							<div class="layui-form-mid" style="width:100%">模块:&emsp;</div>
						</div>
						<div class="layui-col-xs8 layui-col-sm8 layui-col-md8" >
							<select id="moduleIds" lay-filter="moduleTwo"></select>
						</div>
					</div>
						
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<div class="layui-col-xs4 layui-col-sm4 layui-col-md4" style="text-align: right;">
							<div class="layui-form-mid" style="width:100%">供应商:&emsp;</div>
						</div>
						<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
							<select id="supplierIds"></select>
						</div>
					</div>
					<!-- <div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="btn-group">
							<button class="layui-btn layui-btn-sm" onclick="addSnapshotInfo()">新增</button>
							<button class="layui-btn layui-btn-sm layui-btn-warm" id="editSnapshotInfo">修改</button>
							<button class="layui-btn layui-btn-sm layui-btn-danger" id="deleteSnapshotInfo">删除</button>
						</div>
					</div> -->
				</div>	
			</div>
			<table id="addSnapshootInfo" lay-filter="addSnapshootInfo"></table>
		</div>
	</div>
	
	<!-- 快照信息新增页面 -->
	<div id="insertModal" class="modal">
		<form class="layui-form" id="formSnapshootInfo" action="">
			<div style="display: none">
				 <input type="hidden" id="didId" name="didId" />
				 <input name="state" />
			</div>
			
			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<label class="layui-form-label">快照名称</label>
					</div>	
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<input type="text" id="" name="didName" required lay-verify="required" autocomplete="off" class="layui-input" 
							maxlength="20" onkeyup="value=value.replace(/[^\w]/ig,'')" id="didNameOne" />
					</div>
				</div>
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<label class="layui-form-label">信号类型</label>
					</div>	
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<select lay-filter="signal" id="signalTypeTwo" name="signalTypeId">
							<option selected="selected" value="1">Analog</option>
							<option value="2">State Encoded</option>
							<option value="3">Digital</option>
							<option value="4">Text</option>
						</select>
					</div>	
				</div>					
			</div>

			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<label class="layui-form-label">Min</label>
					</div>	
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<input type="text" id="" name="min" required lay-verify="required"
							autocomplete="off" class="layui-input" maxlength="50">
					</div>
				</div>
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<label class="layui-form-label">Max</label>
					</div>	
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<input type="text" id="" name="max" required lay-verify="required"
							autocomplete="off" class="layui-input" maxlength="50">
					</div>	
				</div>					
			</div>

			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<label class="layui-form-label">Units</label>
					</div>	
					<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
						<input type="text" id="" name="unit" required lay-verify="required"
							autocomplete="off" class="layui-input" maxlength="50">
					</div>
				</div>
			</div>	

			<div class="layui-form-item" style="text-align:center;padding-top: 80px;">
				<button class="layui-btn" lay-submit lay-filter="addSnapshot" type="button">保存</button>
				<button type="reset" class="layui-btn layui-btn-primary" id="resetAdd">重置</button>
			</div>
		</form>
	</div>
	
	<!-- 配置页面 -->
	<div id="allocationModal" class="modal" style="padding-top: 15px;">
		<form class="layui-form" id="formAllocationInfo" action="">
			<div style="display: none">
				<input type="text" name="didTypeId" id="didTypeIds" /> 
			</div>
			
			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12" style="margin-bottom: 10px;">
				<span class="noEmpty">快照名称：</span>
				<span id="didNameTwo" style="color: red;font-size: 15px;"></span>
			</div>
			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4 noEmpty">Signal Type：</div>
					<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
						<select lay-filter="signal" id="" name="signalTypeId">
							<option selected="selected" value="1">Analog</option>
							<option value="2">State Encoded</option>
							<option value="3">Digital</option>
							<option value="4">text</option>
						</select>
					</div>
				</div>
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 noEmpty" style="text-align: center;">Raw Value Type：</div>
					<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
						<select id="rawValueTypeOne" name="rawValueType">
							<option selected="selected" value="1">Number</option>
							<option value="2">BCD</option>
						</select>
					</div>	
				</div>					
			</div>
			
			<hr>
			
			<!-- 第一个 -->
			<div style="display:block" id="signal1">
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12 bottomMargin">
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Bit Position</div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Byte</div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Bit</div>
				</div>
				
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs3 layui-col-sm3 layui-col-md3 noEmpty">start：</div>
						<div class="layui-col-xs9 layui-col-sm9 layui-col-md9">
							<input type="text" class="custom-input" id="startOne" style="width:100%;" value="0" 
								onkeyup="value=value.replace(/[^\d\.]/ig,'')">
							<input name="startBitPosition" type="hidden" />
						</div>
					</div>
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 noEmpty" style="text-align: center;">or</div>
						<div class="layui-col-xs10 layui-col-sm10 layui-col-md10">
							<select id="byteOne" lay-filter="byteOne"></select>
							<input name="startByte" type="hidden" />
						</div>
					</div>
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 noEmpty" style="text-align: center;">：</div>
						<div class="layui-col-xs10 layui-col-sm10 layui-col-md10">
							<!-- <input type="text" class="custom-input" id="" style="width:100%;" 
								onkeyup="value=value.replace(/[^\d\.]/ig,'')"> -->
							<select id="bitOne" lay-filter="bitOne"></select>
							<input name="startBit" type="hidden" />
						</div>
					</div>
				</div>
			
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12 bottomMargin">
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Bits</div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Bytes</div>					
				</div>
				
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs3 layui-col-sm3 layui-col-md3 noEmpty">Length：</div>
						<div class="layui-col-xs9 layui-col-sm9 layui-col-md9">
							<select id="bitsOne" lay-filter="bitsOne"></select>
							<input name="lengthBit" type="hidden" />
						</div>
					</div>
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 noEmpty" style="text-align: center;">or</div>
						<div class="layui-col-xs10 layui-col-sm10 layui-col-md10">
							<select id="bytesOne" lay-filter="bytesOne"></select>
							<input name="lengthByte" type="hidden" />
						</div>
					</div>					
				</div>
				
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 noEmpty">Scaling Type:</div>
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs10 layui-col-sm10 layui-col-md10">
							<select id="" name="scalingTypeId">
								<option selected="selected" value="1">None</option>
								<option value="2">Linear mX+ b</option>
							</select>
						</div>
					</div>					
				</div>
				
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12 bottomMargin">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Scaling</div>
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2" style="text-align: center;">Office</div>				
				</div>
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12 bottomMargin">
					<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
						<div class="layui-col-xs4 layui-col-sm4 layui-col-md4 noEmpty" style="line-height: 20px;">
							<span>Engineering </span><br/>
							<span>Value =</span>
						</div>
						<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
							<input type="text" class="custom-input" style="width:100%;" name="engineeringValue" id="engineeringValue"
								onkeyup="this.value= this.value.match(/\d+(\.\d{0,2})?/) ? this.value.match(/\d+(\.\d{0,2})?/)[0] : ''"
								maxlength="10"/>
						</div>
					</div>
					<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
						<div class="layui-col-xs5 layui-col-sm5 layui-col-md5 noEmpty" style="text-align: center;">* RawValue</div>
						<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
							<input type="text" class="custom-input" style="width:100%;" name="rawValue" id="rawValue"
								onkeyup="this.value= this.value.match(/\d+(\.\d{0,2})?/) ? this.value.match(/\d+(\.\d{0,2})?/)[0] : ''"
								maxlength="10">
						</div>	
					</div>					
				</div>
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3 empty"></div>
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">m(scaling factor)</div>
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3 empty"></div>
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">b(scaling factor)</div>
				</div>
				
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12 bottomMargin">
					<div class="layui-col-xs1 layui-col-sm1 layui-col-md1 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Format：</div>
					<div class="layui-col-xs1 layui-col-sm1 layui-col-md1 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Min</div>
					<div class="layui-col-xs1 layui-col-sm1 layui-col-md1 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Max</div>
					<div class="layui-col-xs1 layui-col-sm1 layui-col-md1 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Units</div>				
				</div>
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<div class="layui-col-xs11 layui-col-sm11 layui-col-md11">
							<input type="text" name="format" id="formatOne" class="layui-input" style="position:absolute;z-index:2;width:80%;" 
								  autocomplete="off" maxlength="50">
		                    <select type="text" id="formatOne_select" lay-filter="formatOne_select" class="layui-select selectUp" lay-search>
		                        <option value="0">0</option>
		                        <option value="0.0">0.0</option>
		                        <option value="0.00">0.00</option>
		                        <option value="0.000">0.000</option>
		                        <option value="0.0E+00">0.0E+00</option>
		                        <option value="0 eng">0 eng</option>
		                        <option value="0 engf">0 engf</option>
		                        <option value="X hex">X hex</option>
		                        <option value="X bin">X bin</option>
		                    </select>
						</div>
					</div>
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<div class="layui-col-xs11 layui-col-sm11 layui-col-md11">
							<input type="text" class="custom-input" name="min" id="minValue" maxlength="50" />
						</div>
					</div>
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<div class="layui-col-xs11 layui-col-sm11 layui-col-md11">
							<input type="text" class="custom-input" name="max" id="maxValue" maxlength="50" />
						</div>
					</div>
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
							<select id="unitOne" name="unit">
								<option selected="selected" value="1">km/m</option>
								<option value="2">V</option>
								<option value="3">℃</option>
							</select>
						</div>
					</div>
				</div>
			</div>
			
			<!-- 第二个 -->
			<div style="display:none" id="signal2">
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12 bottomMargin">
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Bit Position</div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Byte</div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Byte</div>
				</div>
				
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs3 layui-col-sm3 layui-col-md3 noEmpty">start：</div>
						<div class="layui-col-xs9 layui-col-sm9 layui-col-md9">
							<input type="text" class="custom-input" style="width:100%;" value="0" id="startTwo"
								onkeyup="value=value.replace(/[^\d\.]/ig,'')" />
						</div>
					</div>
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 noEmpty" style="text-align: center;">or</div>
						<div class="layui-col-xs10 layui-col-sm10 layui-col-md10">
							<select id="byteTwo" lay-filter="byteTwo"></select>
						</div>
					</div>
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 noEmpty" style="text-align: center;">：</div>
						<div class="layui-col-xs10 layui-col-sm10 layui-col-md10">
							<select id="bitTwo" lay-filter="bitTwo"></select>
						</div>
					</div>
				</div>
			
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12 bottomMargin">
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Bits</div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Bytes</div>					
				</div>
				
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs3 layui-col-sm3 layui-col-md3 noEmpty">Length：</div>
						<div class="layui-col-xs9 layui-col-sm9 layui-col-md9">
							<select id="bitsTwo" lay-filter="bitsTwo"></select>
						</div>
					</div>
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 noEmpty" style="text-align: center;">or</div>
						<div class="layui-col-xs10 layui-col-sm10 layui-col-md10">
							<select id="bytesTwo" lay-filter="bytesTwo"></select>
						</div>
					</div>					
				</div>
				
				<hr>
				
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12 bottomMargin">
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4" style="text-align: center;">State Description</div>
					<div class="layui-col-xs1 layui-col-sm1 layui-col-md1 empty"></div>
					<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">Valus
						<input type="radio" name="radios" checked="checked" value="Decimal" title="Decimal" lay-filter="scale">
						<input type="radio" name="radios" value="Hex" title="Hex" lay-filter="scale">
					</div>					
				</div>
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
							<input type="text" class="custom-input" id="stateDescription" style="width:100%;" maxlength="100">
						</div>
					</div>
					
					<div class="layui-col-xs1 layui-col-sm1 layui-col-md1">
						<div class="layui-col-xs12 layui-col-sm12 layui-col-md12 noEmpty" style="text-align: center;">=</div>
					</div>		
					
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
							<input type="text" class="custom-input" id="rawValueOne" style="width:100%;" onclick="verifyInputValue()" maxlength="20">
						</div>
					</div>
					
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3" style="padding:5px 0 0 10px;">
						<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
							<button class="layui-btn layui-btn-sm layui-btn-normal" onclick="addStateInfo()" type="button">Add</button>
							<button class="layui-btn layui-btn-sm layui-btn-danger" onclick="delStateInfo()" type="button">remove</button>
						</div>
					</div>
				</div>
				
				<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
			 		<table id="stateInfo" lay-filter="stateInfo"></table>
			 	</div>
			 				
			</div>
			
			<!-- 第三个 -->
			<div style="display:none" id="signal3">
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12 bottomMargin">
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Bit Position</div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Byte</div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Byte</div>
				</div>
				
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs3 layui-col-sm3 layui-col-md3 noEmpty">start：</div>
						<div class="layui-col-xs9 layui-col-sm9 layui-col-md9">
							<input type="text" class="custom-input" style="width:100%;" value="0" id="startThree"
								onkeyup="value=value.replace(/[^\d\.]/ig,'')">
						</div>
					</div>
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 noEmpty" style="text-align: center;">or</div>
						<div class="layui-col-xs10 layui-col-sm10 layui-col-md10">
							<select id="byteThree" lay-filter="byteThree"></select>
						</div>
					</div>
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 noEmpty" style="text-align: center;">：</div>
						<div class="layui-col-xs10 layui-col-sm10 layui-col-md10">
							<select id="bitThree" lay-filter="bitThree"></select>
						</div>
					</div>
				</div>
			
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12 bottomMargin">
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Bits</div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Bytes</div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Format</div>
				</div>
				
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs3 layui-col-sm3 layui-col-md3 noEmpty">Length：</div>
						<div class="layui-col-xs9 layui-col-sm9 layui-col-md9">
							<select id="bitsThree" disabled="disabled"></select>
						</div>
					</div>
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 noEmpty" style="text-align: center;">or</div>
						<div class="layui-col-xs10 layui-col-sm10 layui-col-md10">
							<select id="bytesThree" disabled="disabled">
								<option selected="selected" value="0"> </option>
							</select>
						</div>
					</div>
					
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
						<div class="layui-col-xs10 layui-col-sm10 layui-col-md10">
							<input type="text" id="formatTwo" class="layui-input" style="position:absolute;z-index:2;width:80%;" 
							  autocomplete="off">
		                    <select type="text" id="formatTwo_select" lay-filter="formatTwo_select" class="layui-select" lay-search>
		                        <option selected="selected" value="True/False">True/False</option>
								<option value="On/Off">On/Off</option>
								<option value="Yes/No">Yes/No</option>
		                        <option value="Passed/Failed">Passed/Failed</option>
		                        <option value="Open/Closed">Open/Closed</option>
		                        <option value="Up/Down">Up/Down</option>
		                        <option value="Active/Inactive">Active/Inactive</option>
		                        <option value="Valid/Invalid">Valid/Invalid</option>
		                    </select>
						</div>
					</div>			
				</div>
				
			</div>
			
			<!-- 第四个 -->
			<div style="display:none" id="signal4">
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12 bottomMargin">
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Bit Position</div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Byte</div>
				</div>
				
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs3 layui-col-sm3 layui-col-md3 noEmpty">start：</div>
						<div class="layui-col-xs9 layui-col-sm9 layui-col-md9">
							<input type="text" class="custom-input" style="width:100%;" value="0" id="startFour" readonly="readonly" />
						</div>
					</div>
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 noEmpty" style="text-align: center;">or</div>
						<div class="layui-col-xs10 layui-col-sm10 layui-col-md10">
							<select id="byteFour" lay-filter="byteFour"></select>
						</div>
					</div>
				</div>
			
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12 bottomMargin">
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Bits</div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Bytes</div>	
				</div>
				
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs3 layui-col-sm3 layui-col-md3 noEmpty">Length：</div>
						<div class="layui-col-xs9 layui-col-sm9 layui-col-md9">
							<select id="bitsFour" lay-filter="bitsFour"></select>
						</div>
					</div>
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
						<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 noEmpty" style="text-align: center;">or</div>
						<div class="layui-col-xs10 layui-col-sm10 layui-col-md10">
							<select id="bytesFour" lay-filter="bytesFour"></select>
						</div>
					</div>	
				</div>
			</div>
			
			<div class="layui-form-item" style="text-align:center;">
				<button class="layui-btn" lay-submit lay-filter="formAffirm">确认</button>
				<button type="reset" class="layui-btn layui-btn-primary" id="reset">重置</button>
			</div>
		</form>
	</div>
	
	<!-- ******************************** 修改模态窗体  *********************** -->
	<div id="updateModal" class="modal">
		<form class="layui-form" id="formUdate" action="">
			<div style="display: none">
				<input type="text" name="didId" /> 
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">车型</label>
				<div class="layui-input-inline input-box vehicleId">
					<select id="updateCarTypeId" lay-filter="updateCarType" name="vehicleId"></select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">配置</label>
				<div class="layui-input-inline input-box configurationLevelId">
					<select id="udpateConfigurationId" lay-filter="updateConfiguration" name="configurationLevelId"></select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">模块</label>
				<div class="layui-input-inline input-box moduleId">
					<select id="updateModuleId" lay-filter="updateModule" name="moduleId"></select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">供应商</label>
				<div class="layui-input-inline input-box supplierId">
					<select id="updateSupplierId" name="supplierId"></select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">快照名称</label>
				<div class="layui-input-inline input-box">
					<input type="text" id="" name="didName" required lay-verify="required"  onkeyup="value=value.replace(/[^\w]/ig,'')"
						autocomplete="off" class="layui-input" maxlength="20">
				</div>
			</div>

			<div class="layui-form-item" style="text-align:center;padding-top: 80px;margin-bottom: 0px;">
				<button class="layui-btn" lay-submit lay-filter="formDemo">确认</button>
				<button type="reset" class="layui-btn layui-btn-primary" id="resetUpdate" style="display: none;">重置</button>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="${ctx}/Content/js/jquery-3.3.1.min.js"></script>
	<script src="${ctx}/Content/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/combobox.js"></script>
	<!-- 文本编辑器 -->
	<!-- 新增表格头部自定义工具栏 -->
	<script type="text/html" id="insertSnapshotBar">
      <div class="layui-btn-container">
		<div class="layui-inline" title="新增" lay-event="insert">
 			<i class="layui-icon layui-icon-add-circle"></i>
		</div>
		<div class="layui-inline" title="修改" lay-event="update">
 			<i class="layui-icon layui-icon-edit"></i>
		</div>
		<div class="layui-inline" title="删除" lay-event="delete">
 			<i class="layui-icon layui-icon-delete"></i>
		</div>
		<div class="layui-inline saveInfo" title="保存" lay-event="save">
 			<i class="layui-icon layui-icon-file"></i>
		</div>
      </div>
	</script>
	<!----------------------- 工具条 -------------------------->
	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-xs" lay-event="edit">配置</a>
	</script>

	<script>
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.$;
		var snapshotInfoModal;
		$.ajaxSettings.async = false;

		$(function() {
			//绑定车型模块下拉框
			appendOption("carTypeId", "${ctx}/faultCode/findCarTypeInfo.do");
			appendOption("moduleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=0&&vehicleId=0");
			
			//Analog
			appendOptions("byteOne", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=272");
			appendOptions("bitsOne", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=64");
			appendOptions("bytesOne", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=8");
			appendOptions("formatOne", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=272");
			appendOptions("bitOne", "${ctx}/dataManage/bindingDisposeInfo.do?start=0&&end=7");
			//State Encoded
			appendOptions("byteTwo", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=272");
			appendOptions("bitsTwo", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=64");
			appendOptions("bytesTwo", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=8");
			appendOptions("bitTwo", "${ctx}/dataManage/bindingDisposeInfo.do?start=0&&end=7");
			//Digital
			appendOptions("byteThree", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=272");
			appendOptions("bitsThree", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=64");
			//appendOptions("bytesThree", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=8");
			appendOptions("bitThree", "${ctx}/dataManage/bindingDisposeInfo.do?start=0&&end=7");
			//text
			appendOptions("byteFour", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=272");
			appendOptions("bitsFour", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=64");
			appendOptions("bytesFour", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=8");
			
			//快照信息新增页面
			appendOption("vehicleIds", "${ctx}/faultCode/findCarTypeInfo.do");
			appendOption("configurationIds", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId=0");
			appendOption("moduleIds", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=0&&vehicleId=0");
			appendOption("supplierIds", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0");
			
			//修改
			appendOption("updateCarTypeId", "${ctx}/faultCode/findCarTypeInfo.do");
			appendOption("udpateConfigurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId=0");
			appendOption("updateModuleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=0&&vehicleId=0");
			appendOption("updateSupplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0");
			searchSnapshotInfo();//查询快照信息
			form.render('select');//重新渲染下拉框
		});
		
		//车型下拉框监听事件
		form.on('select(cartypeOne)', function(data){
			if(data.value !=''){
				appendOption("moduleId", "${ctx}/dataManage/findModuleByVehicleId.do?vehicleId="+data.value);
				form.render('select');
			}
		});
		
		//车型下拉框监听事件(快照信息新增页面)
		form.on('select(cartypeTwo)', function(data){
			if(data.value !=''){
				appendOption("configurationIds", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId="+data.value);
				appendOption("moduleIds", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=0&&vehicleId=0");
				appendOption("supplierIds", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0&&configurationLevelId=0&&vehicleId=0");
				form.render('select');
			}
		});
		
		//配置下拉框监听事件
		form.on('select(configurationTwo)', function(data){
			if(data.value !=''){
				var vehicleId=$("#vehicleIds").val();
				appendOption("moduleIds", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId="+data.value+"&&vehicleId="+vehicleId);
				appendOption("supplierIds", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0&&configurationLevelId=0&&vehicleId=0");
				form.render('select');
			}
		});
		
		//模块下拉框监听事件
		form.on('select(moduleTwo)', function(data){
			if(data.value !=''){
				var vehicleId=$("#vehicleIds").val();
				var configurationLevelId=$("#configurationIds").val();
				appendOption("supplierIds", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId="+data.value+"&&configurationLevelId="+configurationLevelId
						+"&&vehicleId="+vehicleId);
				form.render('select');
			}
		});
		
		//车型下拉框监听事件(修改)
		form.on('select(updateCarType)', function(data){
			if(data.value !=''){
				appendOption("udpateConfigurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId="+data.value);
				appendOption("updateModuleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=0&&vehicleId=0");
				appendOption("updateSupplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0&&configurationLevelId=0&&vehicleId=0");
				form.render('select');
			}
		});
		
		//配置下拉框监听事件
		form.on('select(updateConfiguration)', function(data){
			if(data.value !=''){
				var vehicleId=$("#updateCarTypeId").val();
				appendOption("updateModuleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId="+data.value+"&&vehicleId="+vehicleId);
				appendOption("updateSupplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0&&configurationLevelId=0&&vehicleId=0");
				form.render('select');
			}
		});
		
		//模块下拉框监听事件
		form.on('select(updateModule)', function(data){
			if(data.value !=''){
				var vehicleId=$("#updateCarTypeId").val();
				var configurationLevelId=$("#udpateConfigurationId").val();
				appendOption("updateSupplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId="+data.value+"&&configurationLevelId="+configurationLevelId
						+"&&vehicleId="+vehicleId);
				form.render('select');
			}
		});
		
	 	//查询快照信息
	 	function searchSnapshotInfo() {
	 		var vehicleId = $("#carTypeId").val();
			var moduleId = $("#moduleId").val();
			if(moduleId == null){
				moduleId=0;
			}
			table.render({
				elem : '#snapshootInfo',
				url : '${ctx}/dataManage/findSnapshotInfo.do',
				where:{
					vehicleId:vehicleId,moduleId:moduleId
				},
				page : true, //开启分页
				limit : 6,
				cols : [ [ //标题栏
					{field : '',title : '序号',type:'numbers',align:'center',unresize : true}, 
					{field : '',title : '选择',type:'checkbox',align:'center',unresize : true},
					{field : 'didName',title : '快照名称',align:'center',edit:'text',}, 
					{field : 'vehicleName',title : '车型',align : 'center'},
					{field : 'configurationLevelName',title : '配置',align : 'center',},
					{field : 'moduleName',title : '模块',align : 'center',}, 
					{field : 'supplierName',title : '供应商',align : 'center',},
					{field : '',title : '操作',align : 'center',toolbar : '#barDemo'} 
				] ],
				done:function(e){
					
				}
			});
		}
	 	
	 	//查询新增快照信息
	 	function searchAddSnapshotInfo() {
			table.render({
				elem : '#addSnapshootInfo',
				url : '${ctx}/dataManage/findSnapshotDetailSession.do',
				toolbar: '#insertSnapshotBar',
				defaultToolbar:null,
				page : true, //开启分页
				limit : 6,
				cols : [ [ //标题栏
					{field : '',title : '序号',type:'numbers',align:'center',unresize : true}, 
					{field : '',title : '选择',type:'checkbox',align:'center',unresize : true},
					{field : 'didName',title : '快照名称',align:'center'}, 
					{field : '',title : '信号类型',align : 'center',
						templet:function(data){
							if(data.signalTypeId==1){
								return "<span>Analog</span>";
							}else if(data.signalTypeId==2){
								return "<span>State Encoded</span>";
							}else if(data.signalTypeId==3){
								return "<span>Digital</span>";
							}else{
								return "<span>Text</span>";
							}
						}
					},
					{field : 'min',title : 'Min',align : 'center'},
					{field : 'max',title : 'Max',align : 'center'}, 
					{field : 'unit',title : 'Units',align : 'center'}
				] ],
				done:function(e){
					
				}
			});
		}
	 	
	 	/*************************** (添加数据)表格头部工具栏监听事件  *********************************************/
		table.on('toolbar(addSnapshootInfo)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    var len = checkStatus.data.length;
		    var count = obj.config.page.count;
		    switch(obj.event){
		      case 'insert':
		    	  addSnapshotInfo(1);
    	      	  $("#insertModal input[name='state']").val(0);
		      break;
		      case 'update':
		       	if(len == 0){
		       		layer.msg('请勾选需要进行修改的数据');
		       	}else if(len > 1){
		       		layer.msg('只能选择一条数据进行修改');
		       	}else{
		       		addSnapshotInfo(2);//打开新增模态框
		       		loadDatatoForm("formSnapshootInfo", checkStatus.data[0]);//回填数据#
	    	      	$("#insertModal input[name='state']").val(1);
		       	}
		      break;
		      case 'delete':
		    	if(len == 0){
		       		layer.msg('请勾选需要删除的数据');
		       	}else{
		       		layer.confirm('真的要删除选中的信息?', {
		            	icon: 3,
		                btn: ['确定', '取消']
		            }, function (index) {		
		            	layer.close(index); 
		            	var num = 0;	                
		            	for ( var i = 0; i < len; i++) {
		            		$.ajax({
			                	url: "${ctx}/dataManage/deleteSnapshotDetailInfoToSession.do?didId=" + checkStatus.data[i].didId,                    
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
		            	layer.alert(num + "条数据删除成功，"+(len - num) + "条数据删除失败!",{ icon: 1, skin: 'layui-layer-molv', closeBtn: 0, anim: 4 });
		            	//刷新表格
		            	searchAddSnapshotInfo();
			   		});
		       	}
		      break;
		      case 'save':
		    	var num=table.cache['addSnapshootInfo'].length;
		    	if(num == 0){
		       		layer.msg('暂无需要保存的数据');
		       	}else{
		       		//表单提交监听——新增
		       		insertSnapshotInfo();
		       	}
		      break;
		    };
		});
	 	
		//快照明细信息保存按钮
	  	form.on('submit(addSnapshot)', function(data){
			var state = $("#insertModal input[name='state']").val();
			//新增、修改----配置信息
			if(state==0){
				$.post("${ctx}/dataManage/insertSnapshotDetailInfoToSession.do",data.field,function(e){
					if(e==200){
						layer.close(snapshotInfoModal);
						layer.msg("新增成功！");
						searchAddSnapshotInfo();
					}else if(e==500){
						layer.msg("快照名称已存在，请重新填写");
					}else{
						layer.alert("数据异常！",{icon:2,title:'提示'});
					}
				});
			}else if(state==1){
				$.post("${ctx}/dataManage/updateSnapshotDetailInfoToSession.do",data.field,function(e){
					if(e==200){
						layer.close(snapshotInfoModal);
						layer.msg("修改成功！");
						searchAddSnapshotInfo();
					}else if(e==500){
						layer.msg("快照名称已存在，请重新填写");
					}else{
						layer.alert("数据异常！",{icon:2,title:'提示'});
					}
				});
			}
			//document.all("resetAdd").click();//重置表单
			return false; //阻止表单跳转
		});
	 	
	 	//主页新增快照信息模态框
	 	function insertSnapshot(){
	 		var index = layer.open({
				type : 1,
				title : '快照信息新增页面',
				content : $('#addSnapshootModal'),
				area : [ '88%', '82%' ],
				resize:false,//是否允许拉伸
			});
	 		searchAddSnapshotInfo();//查询新增快照信息
			form.render();
	 	}
	 	
	 	//新增、修改快照明细模态框
	 	function addSnapshotInfo(num){
	 		var name="快照明细信息新增页面";
	 		if(num==2){
	 			name="快照明细信息修改页面";
	 		}
	 		snapshotInfoModal = layer.open({
				type : 1,
				title : name,
				content : $('#insertModal'),
				area : [ '700px', '400px' ],
				resize:false,//是否允许拉伸
			});
			form.render();//重新渲染form表单
	 	}
	 	
	 	//新增故障码信息(添加数据)
		function insertSnapshotInfo(){
			var vehicleId=$("#vehicleIds").val();
			var configurationLevelId=$("#configurationIds").val();
			var moduleId=$("#moduleIds").val();
			var supplierId=$("#supplierIds").val();
			if(vehicleId >0 && configurationLevelId>0 && moduleId>0 && supplierId>0){
				var typeIds=1;
				$.getJSON("${ctx}/dataManage/insertSnapshotInfo.do", {vehicleId:vehicleId,configurationLevelId:configurationLevelId
					,moduleId:moduleId,supplierId:supplierId}, function(d) {
					if (d.code==200) {
						layer.closeAll();
						layer.msg(d.text, { icon:1, offset : '150px' });
						$("#addSnapshootModal select").val(0);
					} else {
						layer.alert(d.text, { icon : 2, title : '提示', offset : '150px' });
					}
					searchSnapshotInfo();
				});
			}else{
				layer.msg("请选择模块供应商信息");
			}
		}
	 	
		//(快照信息管理)监听工具条
		table.on('tool(snapshootInfo)', function(obj) {
			var data = obj.data;
			if (obj.event == 'edit') {
				document.all("reset").click();//重置表单
				loadDatatoForm("formAllocationInfo", data);//回填数据
				$("#didNameTwo").text(data.didName);
				resetModal(data.signalTypeId);
				var modals = layer.open({
					type : 1,
					title : '快照信息配置页面',
					content : $('#allocationModal'),
					area : [ '54%', '90%' ],
					resize:false,//是否允许拉伸
				});
				//根据信号类型进行回填数据
				if(data.signalTypeId==1 && data.startBitPosition !=''){
		 			$("#startOne").val(data.startBitPosition);
					$("#byteOne").val(data.startByte);
					$("#bitOne").val(data.startBit);
					$("#bitsOne").val(data.lengthBit);
					$("#bytesOne").val(data.lengthByte);
		 		}else if(data.signalTypeId==2 && data.startBitPosition !=''){
		 			$("#startTwo").val(data.startBitPosition);
					$("#byteTwo").val(data.startByte);
					$("#bitTwo").val(data.startBit);
					$("#bitsTwo").val(data.lengthBit);
					$("#bytesTwo").val(data.lengthByte);
					//transferSession(data[0].didTypeId);
		 		}else if(data.signalTypeId==3 && data.startBitPosition !=''){
		 			$("#startThree").val(data.startBitPosition);
					$("#byteThree").val(data.startByte);
					$("#bitThree").val(data.startBit);
					$("#formatTwo").val(data.format);
		 		}else if(data.signalTypeId==4 && data.startBitPosition !=''){
		 			$("#startFour").val(data.startBitPosition);
					$("#byteFour").val(data.startByte);
					$("#bitsFour").val(data.lengthBit);
					$("#bytesFour").val(data.lengthByte);
		 		}
				//通过ID查询状态描述信息
				$.post("${ctx}/dataManage/findStateDescribeInfoById.do",{didTypeId:data.didTypeId},function(){});
				searchStateInfo();//state数据列表
				form.render();//重新渲染form表单
			}
		});
		
		//重置配置模态框
	 	function resetModal(tagNum){
	 		var i = 1;
	 	    var el;
	 	    var tag="signal";
	 	    while (el = document.getElementById(tag + i)) {
	 	        if (i == tagNum){
	 	        	el.style.display = 'block';
	 	        }else{
	 	        	el.style.display = 'none';
	 	        }
	 	        i++;
	 	    }
	 	 	//设置默认值
	 		$("#bitOne").val(7);
	 		$("#bitsOne").val(8);
	 		$("#bytesOne").val(1);
	 		$("#bitTwo").val(7);
	 		$("#bitsTwo").val(8);
	 		$("#bitThree").val(7);
	 	}
	 	
	 	//配置模态框
	 	function addAllocation(){
	 		searchStateInfo();//state数据列表
	 		var index = layer.open({
				type : 1,
				title : '快照信息配置页面',
				content : $('#allocationModal'),
				area : [ '54%', '90%' ],
				resize:false,//是否允许拉伸
			});
			form.render();//重新渲染form表单
	 	}
	 	
	 	
	 	//信号下拉框切换tag
	 	form.on('select(signal)', function(data){
	 		var i = 1;
	 	    var el;
	 	    var tag="signal";
	 	    while (el = document.getElementById(tag + i)) {
	 	        if (i == data.value){
	 	        	el.style.display = 'block';
	 	        }else{
	 	        	el.style.display = 'none';
	 	        }
	 	        i++;
	 	    }
		});
	 	
	 	
	 	//state数据列表
	 	function searchStateInfo() {
			var didTypeId = $("#didTypeIds").val();
			var checkValue=$('input[name="radios"]:checked').val();
			table.render({
				elem : '#stateInfo',
				url : '${ctx}/dataManage/findStateDescriptionSessionTwo.do',
				where:{
					didTypeId:didTypeId
				},
				page : true, //开启分页
				limit : 6,
				cols : [ [ //标题栏
					{ field : '',type : 'numbers',title : '序号',align : 'center'},
					{ field : 'stateDescriptionId',title : '选择',unresize : true,align : 'center',type:'radio'},
					{ field : 'stateDescription',title : 'State',align : 'center'}, 
					{ field : 'decimals',title : 'Raw Value',align : 'center',
						templet:function(d){
							if(checkValue=='Decimal'){
								return '<div><span>'+d.decimals +'</span></div>';
							}else{
								return '<div><span>'+d.hexs +'</span></div>';
							}
						}
					} 
				] ],
				done:function(e){
					
				}
			});
		}
	 	
	 	//------------------------------------------配置信息  start--------------------------------------------//
	 	//(Analog)start键盘弹起事件
	 	$("#startOne").keyup(function(){
	 		var num=$("#startOne").val();
	 		startNumber(num,"startOne","byteOne","bitOne");
	 	});
	 	
	 	function startNumber(num,startOne,byteOne,bitOne){
	 		if(num!=null && num !=''){
	 			num=num.replace(/\b(0+)/gi,"");
	 			if(num>=2175){
	 				num=2175;
	 				$("#"+startOne).val(2175);
	 			}else{
	 				if(num!=''){
	 					$("#"+startOne).val(num);
	 				}else{
	 					$("#"+startOne).val(0);
	 				}
	 			}
	 			var number=num/8;
	 			var ids;
	 			
	 			var bitNum;
	 			if(number.toString().length>1){
	 				ids=number.toString().split(".");
	 				if(parseInt(ids[0])>=272){
	 					bitNum=272;
	 					$("#"+byteOne).val(272);
	 				}else{
	 					bitNum=parseInt(ids[0])+1;
	 					$("#"+byteOne).val(parseInt(ids[0])+1);
	 				}
	 			}else{
	 				bitNum=parseInt(number)+1;
	 				$("#"+byteOne).val(parseInt(number)+1);
	 			}
	 			//bit
	 			$("#"+bitOne).val(parseInt(bitNum)*8-num-1);
	 			form.render('select');
	 		}else{
	 			$("#"+startOne).val(0);
	 		}
	 	}
	 	
	 	//(Analog)Byte监听事件
	 	form.on('select(byteOne)', function(data){
	 		var bitOne=$("#bitOne").val();
			if(data.value !=''){
				var tt=data.value*8;
				$("#startOne").val(parseInt(tt)-parseInt(bitOne)-1);
				form.render('select');
			}
		});
	 	
	 	//(Analog)format
	 	form.on('select(formatOne_select)', function (data) {
			var select_text = data.elem[data.elem.selectedIndex].text;
          	$("#formatOne").val(select_text );
          	$("#formatOne_select").next().find("dl").css({ "display": "none" });
          	form.render();
      	});
	 	
	 	//(Analog)format
	    $('#formatOne').bind('input propertychange', function () {
	    	selectValue("formatOne","formatOne_select");
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

	  	//(Analog)bitsOne监听事件
	 	form.on('select(bitsOne)', function(data){
	 		bitsNumber("bytesOne",data.value);
		});
	  	
	  	function bitsNumber(id,data){
	  		if(data !=''){
				var bitsNum=data/8;
				var ids;
				if(bitsNum.toString().length>1){
	 				ids=bitsNum.toString().split(".");
	 				if(parseInt(ids[0])==0){
	 					$("#"+id).val(1);
	 				}else{
	 					$("#"+id).val(parseInt(ids[0]));
	 				}
	 			}else{
	 				$("#"+id).val(parseInt(bitsNum));
	 			}
				form.render('select');
			}
	  	}
	  	
	  	//(Analog)bitsOne监听事件
	 	form.on('select(bytesOne)', function(data){
			if(data.value !=''){
				var bitsNum=data.value*8;
				$("#bitsOne").val(parseInt(bitsNum));
				form.render('select');
			}
		});
	  	
	 	//(Analog)bitOne监听事件
	 	form.on('select(bitOne)', function(data){
	 		var byteOne=$("#byteOne").val();
			if(data.value !=''){
				var tt=parseInt(data.value)+1;
				$("#startOne").val((parseInt(byteOne)*8)-parseInt(tt));
				form.render('select');
			}
		});
	 	
	 	//(State Encoded)start键盘弹起事件
	 	$("#startTwo").keyup(function(){
	 		var num=$("#startTwo").val();
	 		startNumber(num,"startTwo","byteTwo","bitTwo");
	 	});
	 	
	 	//(State Encoded)Byte监听事件
	 	form.on('select(byteTwo)', function(data){
	 		var bitTwo=$("#bitTwo").val();
			if(data.value !=''){
				var tt=data.value*8;
				$("#startTwo").val(parseInt(tt)-parseInt(bitTwo)-1);
				form.render('select');
			}
		});
	 	
	 	//(State Encoded)bitTwo监听事件
	 	form.on('select(bitTwo)', function(data){
	 		var byteOne=$("#byteTwo").val();
			if(data.value !=''){
				var tt=parseInt(data.value)+1;
				$("#startTwo").val((parseInt(byteOne)*8)-parseInt(tt));
				form.render('select');
			}
		});
	 	
	 	//(State Encoded)bitsTwo监听事件
	 	form.on('select(bitsTwo)', function(data){
	 		bitsNumber("bytesTwo",data.value);
		});
	 	
	 	//(State Encoded)bitsOne监听事件
	 	form.on('select(bytesTwo)', function(data){
			if(data.value !=''){
				var bitsNum=data.value*8;
				$("#bitsTwo").val(parseInt(bitsNum));
				form.render('select');
			}
		});
	 	
	 	//(Digital)format
	 	form.on('select(formatTwo_select)', function (data) {
			var select_text = data.elem[data.elem.selectedIndex].text;
          	$("#formatTwo").val(select_text );
          	$("#formatTwo_select").next().find("dl").css({ "display": "none" });
          	form.render();
      	});
	 	
	 	//(Digital)format
	    $('#formatTwo').bind('input propertychange', function () {
        	selectValue("formatTwo","formatTwo_select");
	    });
	 	
	  	//(Digital)start键盘弹起事件
	 	$("#startThree").keyup(function(){
	 		var num=$("#startThree").val();
	 		startNumber(num,"startThree","byteThree","bitThree");
	 	});
	  
	 	//(Digital)Byte监听事件
	 	form.on('select(byteThree)', function(data){
	 		var bitTwo=$("#bitThree").val();
			if(data.value !=''){
				var tt=data.value*8;
				$("#startThree").val(parseInt(tt)-parseInt(bitTwo)-1);
				form.render('select');
			}
		});
	 	
	 	//(Digital)bitThree监听事件
	 	form.on('select(bitThree)', function(data){
	 		var byteOne=$("#byteThree").val();
			if(data.value !=''){
				var tt=parseInt(data.value)+1;
				$("#startThree").val((parseInt(byteOne)*8)-parseInt(tt));
				form.render('select');
			}
		});
	 	
	 	//(Text)Byte监听事件
	 	form.on('select(byteFour)', function(data){
			if(data.value !=''){
				var tt=parseInt(data.value-1)*8;
				$("#startFour").val(tt);
				form.render('select');
			}
		});
	 	
	 	//(Text)bits监听事件
	 	form.on('select(bitsFour)', function(data){
	 		bitsNumber("bytesFour",data.value);
		});
	 	
	 	//(Text)bytes监听事件
	 	form.on('select(bytesFour)', function(data){
	 		if(data.value !=''){
				var bitsNum=data.value*8;
				$("#bitsFour").val(parseInt(bitsNum));
				form.render('select');
			}
		});
	 	
	 	//(State Encoded)Valus点击事件
	 	function verifyInputValue(){
	 		var typeId=$('input[name="radios"]:checked').val();
			document.onkeyup=function(){
				var str = $("#rawValueOne").val();//获取文本框的值
	    		String.prototype.ResetBlank=function(){
	    			var regEx = /[^\d]/ig;
	    			if(typeId=="Hex"){
	    				regEx=/[^0-9a-fA-F]/ig;
	    			}
	    			return this.replace(regEx, '');
	    		};
	    		//重置文本框
	    		$("#rawValueOne").val(str.ResetBlank().trim());//trim()去除首尾空格
	    	}
		}
	 	
	 	//(Analog)format
	 	form.on('select(formatOne_select)', function (data) {
			var select_text = data.elem[data.elem.selectedIndex].text;
          	$("#formatOne").val(select_text );
          	$("#formatOne_select").next().find("dl").css({ "display": "none" });
          	form.render();
      	});
	 	
	 	//(Analog)format
	    $('#formatOne').bind('input propertychange', function () {
	    	selectValue("formatOne","formatOne_select");
	    });
	 	
	 	//监听进制切换
	 	var checkValues=$('input[name="radios"]:checked').val();
	 	form.on('radio(scale)', function(data) {
			var rawValue=$("#rawValueOne").val();
			var result="";
			if(data.value=="Decimal" && rawValue !=""){
				result = parseInt(rawValue,16).toString(10);
				
			}else if(data.value=="Hex" && rawValue !=""){
				result = parseInt(rawValue,10).toString(16).toUpperCase();
			}
			if(checkValues==data.value){
				result = rawValue;
			}
			$("#rawValueOne").val(result);
			checkValues=data.value;
			searchStateInfo();
		});
	 	
	 	//新增状态描述信息到session中
	 	function addStateInfo(){
	 		var stateDescription=$("#stateDescription").val();
	 		var checkValue=$('input[name="radios"]:checked').val();
	 		var rawValueOne=$("#rawValueOne").val();
	 		var decimals,hexs;
	 		if(stateDescription!='' && rawValueOne !=''){
	 			if(checkValue=="Decimal"){
		 			decimals=rawValueOne;
		 			hexs=parseInt(rawValueOne,10).toString(16);
		 		}else if(checkValue=="Hex"){
		 			decimals=parseInt(rawValueOne,16).toString(10);
		 			hexs=rawValueOne;
		 		}
		 		if(stateDescription !='' && rawValueOne !=''){
		 			$.post("${ctx}/dataManage/addRawValueInfoToSession.do",{stateDescription:stateDescription,decimals:decimals
		 				,hexs:hexs},function(e){
						if(e==200){
							layer.msg("新增成功！");
							searchStateInfo();
						}else{
							layer.alert("数据异常！",{icon:2,title:'提示'});
						}
					});
		 		}
	 		}
	 		return false;
	 	}
	 	
	 	//从session中移除状态描述信息
	 	function delStateInfo(){
	 		var checkStatus=table.checkStatus('stateInfo'),
			data=checkStatus.data;
	 		if(data.length>0){
	 			$.post("${ctx}/dataManage/removeRawValueInfoInSession.do",{stateDescriptionId:data[0].stateDescriptionId}
	 				,function(e){
					if(e==200){
						layer.msg("移除成功！");
						searchStateInfo();
					}else{
						layer.alert("数据异常！",{icon:2,title:'提示'});
					}
				});
	 		}else{
	 			layer.msg("请选择一条信息");
	 		}
	 	}
	 	//---------------------------------------配置信息 end-----------------------------------------//
	 	
	 	//配置信息保存按钮
	    form.on('submit(formAffirm)', function(data){
			var signale=data.field.signalTypeId;
			if(signale==1){//信号类型Analog
				var formatOne=$("#formatOne").val();
				var minValue=$("#minValue").val();
				var maxValue=$("#maxValue").val();
				var unit=$("#unitOne").val();
				var rawValueType=$("#rawValueTypeOne").val();
				if(data.field.engineeringValue !='' && data.field.rawValue !='' && data.field.format !='' && data.field.min !='' 
						&& data.field.max !='' && data.field.unit !='' && data.field.rawValueType !=''){
					data.field.startBitPosition=$("#startOne").val();
					data.field.startByte=$("#byteOne").val();
					data.field.startBit=$("#bitOne").val();
					data.field.lengthBit=$("#bitsOne").val();
					data.field.lengthByte=$("#bytesOne").val();
					//把配置信息提交到后台
					submitDidInfo(data.field);
				}else{
					layer.msg("请填写完整信息！");
				}
			}else if(signale==2){//信号类型State Encoded
				var num=table.cache['stateInfo'].length;
				if(num>0 && data.field.rawValueType !=''){
					data.field.startBitPosition=$("#startTwo").val();
					data.field.startByte=$("#byteTwo").val();
					data.field.startBit=$("#bitTwo").val();
					data.field.lengthBit=$("#bitsTwo").val();
					data.field.lengthByte=$("#bytesTwo").val();
					//把配置信息提交到后台
					submitDidInfo(data.field);
				}else{
					layer.msg("请填写完整信息");
				}
			}else if(signale==3){//信号类型Digital
				var formatTwo=$("#formatTwo").val();
				if(formatTwo!='' && data.field.rawValueType !=''){
					data.field.startBitPosition=$("#startThree").val();
					data.field.startByte=$("#byteThree").val();
					data.field.startBit=$("#bitThree").val();
					data.field.lengthBit=$("#bitsThree").val();
					data.field.format=formatTwo;
					//把配置信息提交到后台
					submitDidInfo(data.field);
				}else{
					layer.msg("请填写完整信息");
				}
			}else if(signale==4){//信号类型Text
				if(data.field.rawValueType !=''){
					data.field.startBitPosition=$("#startFour").val();
					data.field.startByte=$("#byteFour").val();
					data.field.lengthBit=$("#bitsFour").val();
					data.field.lengthByte=$("#bytesFour").val();
					//把配置信息提交到后台
					submitDidInfo(data.field);
				}else{
					layer.msg("请填写完整信息");
				}
			}
			return false; //阻止表单跳转
		});
	 	
	  	//把配置信息提交到后台(修改)
	  	function submitDidInfo(fieldInfo){
	  		//清空不必要的字段
	  		if(fieldInfo.signalTypeId!=1){
	  			fieldInfo.scalingTypeId=null;
	  			fieldInfo.engineeringValue=null;
	  			fieldInfo.rawValue=null;
	  			fieldInfo.min=null;
	  			fieldInfo.unit=null;
	  		}
	  		if(fieldInfo.signalTypeId==2 || fieldInfo.signalTypeId==4){
	  			fieldInfo.format=null;
	  		}
	  		$.post("${ctx}/dataManage/updateDidTypeInfoById.do",fieldInfo,function(e){
				if(e.text=="success"){
					layer.closeAll();
					layer.msg("保存成功", { icon : 1, offset : '150px' });
					searchSnapshotInfo();
				}else{
					layer.alert(e.text,{icon:2,title:'提示'});
				}
			});
	  	}
	  	
	  	/*************************** 打开修改数据窗体  *********************************************/
		function showUpdateModal() {
			document.all('resetUpdate').click();
			var checkStatus=table.checkStatus('snapshootInfo'),
			data=checkStatus.data;
			if(data.length==0){
				layer.alert("请先选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length>1){
				layer.alert("只能选择一条信息", {icon : 0,title : '提示',offset : '150px'});
			}else if(data.length==1){
				loadDatatoForm("formUdate",data[0]);
				var index = layer.open({
					type : 1,
					title : '新增数据页面',
					content : $('#updateModal'),
					area : [ '430px', '480px' ],
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
		}
	  	
		//表单提交监听——修改
		form.on('submit(formDemo)', function(data) {
			if(data.field.vehicleId == "0"){
				layer.tips('请选择车型','.vehicleId');
			} else if(data.field.configurationLevelId == "0"){
				layer.tips('请选择配置','.configurationLevelId');
			} else if(data.field.moduleId == "0"){
				layer.tips('请选择模块','.moduleId');
			} else if(data.field.supplierId == "0"){
				layer.tips('请选择供应商','.supplierId');
			} else if(data.field.arithmeticDelta == "0"){
				layer.tips('请选择Detail','.arithmeticDelta');
			} else{
				$.getJSON("${ctx}/dataManage/updateSnapshotInfo.do", data.field, function(d) {
					if (d.text=="success") {
						layer.closeAll();
						layer.msg("修改成功", { icon:1, offset : '150px' });
						document.all("resetUpdate").click();
						searchSnapshotInfo();
					} else {
						layer.alert(d.text, { icon : 2, title : '提示', offset : '150px' });
					}
				});
			}			
			return false;
		});
		
		//删除快照信息
		$("#deleteSnapshot").click(function(){
		    var checkStatus = table.checkStatus('snapshootInfo')//获取选中行数据
	      	,data = checkStatus.data;
		    if(data.length==0){
		    	layer.alert("请选择需要删除的数据", {icon : 0,title : '提示',offset : '150px'});
		    }else if(data.length>0){
		    	layer.confirm('真的删除选中的数据？', {
	            	icon: 3,
	                btn: ['确定', '取消']
	            }, function (index) {		
	            	layer.close(index);
	            	var num = 0;
	            	var didTypeIds="";
	            	for ( var i = 0; i < data.length; i++) {
	            		didTypeIds=didTypeIds+","+data[i].didTypeId;
	            	}
	            	
	            	$.post("${ctx}/dataManage/deleteSnapshotInfo.do",{didTypeIds:didTypeIds.substring(1)},function(e){
	    				if(e.text=="success"){
	    					layer.msg("删除成功", { icon : 1, offset : '150px' });
	    					searchSnapshotInfo();
	    				}else{
	    					layer.alert(e.text,{icon:2,title:'提示'});
	    				}
	    			});
		   		});
		    }
		});
	</script>
</body>
</html>
