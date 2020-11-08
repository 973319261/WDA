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

<title>My JSP 'didManage.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" href="${ctx}/Content/layui/css/layui.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/Content/css/view.css" type="text/css"></link>

<style type="text/css">
	.layui-table-view .layui-form-checkbox {
		top: 14px !important;
	}
	
	.layui-table-view .layui-form-radio {
		padding-top: 15px !important;
	}
	
	.modal {
		display: none;
		padding: 20px;
	}
	
	.layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
	   top: 50%;
	   transform: translateY(-50%);
	}
	
	.layui-icon-ok:before{
		content:"\e605";
		color: #fff;
	}
	
	.layui-form-radioed>i{
		border-color: #5FB878!important;
		color: #5FB878 !important;
	}
	
	tr:nth-of-type(1) .layui-anim-scaleSpring:hover{
		color: #5FB878 !important;
	}
	
	tr .layui-anim:hover{
		color: #5FB878 !important;
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
	
	/* 隐藏表头的复选框 */
	/* #didBox th:nth-of-type(1) .layui-form-checkbox{
		display: none;
	}
	#didBox th:nth-of-type(1) .layui-table-cell:before{
		content: "选择";
	} */
	
	#allocationModal .layui-form-select dl {
		max-height: 150px;
	}
	/* #allocationModal .layui-form-select dl{
		max-width: 150px;
	} */
	
	.selectUp .layui-form-select dl{
		top: auto;
		bottom: 40px;
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
					<a href="javascript:void(0)">数据管理</a><a href="javascript:void(0)"><cite>DID管理</cite></a>
				</span>
				<h2 class="title">DID管理</h2>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-card">
				<div class="layui-card-body">
					<div class="form-box">
						<div class="layui-form layui-form-item" style="margin: 5px 0 10px;">
							<div class="layui-row">
								<!-- 左边内容栏 -->
							    <div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
								    <div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
										<button class="layui-btn layui-btn-sm layui-btn-blue" type="button" onclick="searchecuData()">默认ECU</button>
										<button class="layui-btn layui-btn-sm" type="button" onclick="addDidInfoToSesion()" id="addDidInfo">新增</button>
										<button class="layui-btn layui-btn-sm layui-btn-danger" type="button" onclick="delDidInfoInSession()">删除</button>
									</div>
									
								    <div class="layui-col-xs12 layui-col-sm12 layui-col-md12" style="padding-top: 20px;">
										<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
											<div class="layui-form-mid">选择车型:</div>
										</div>
										<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 carTypeId">
											<select id="vehicleId" name="vehicleId" lay-filter="cartype"></select>
										</div>
									</div>
									<div class="layui-col-xs12 layui-col-sm12 layui-col-md12" style="padding-top: 20px;">
										<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
											<div class="layui-form-mid">选择配置:</div>
										</div>
										<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 configurationId">
											<select id="configurationId" lay-filter="configuration"></select>
										</div>
									</div>
									<div class="layui-col-xs12 layui-col-sm12 layui-col-md12" style="margin-top:20px;">
										<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
											<div class="layui-form-mid">选择模块:</div>
										</div>
										<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 moduleId">
											<select id="moduleId" lay-filter="module"></select>
										</div>
									</div>
									<div class="layui-col-xs12 layui-col-sm12 layui-col-md12" style="padding-top: 20px;">
										<div class="layui-col-xs4 layui-col-sm4 layui-col-md4">
											<div class="layui-form-mid">选择供应商:</div>
										</div>
										<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 supplierId">
											<select id="supplierId"></select>
										</div>
									</div>
									
									<div class="layui-col-xs12 layui-col-sm12 layui-col-md12" id="didBox" style="height:300px;padding: 20px 10px 0 0;">										
										<table id="didInfo" lay-filter="didInfo"></table>
									</div>
								</div>
								 
								<!-- 右边内容栏 -->
								<div class="layui-col-xs9 layui-col-sm9 layui-col-md9" style="height:600px;padding-left:10px;border-left:1px solid #cecece;">
								     <div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
										 <div class="layui-form-mid">DID名称:</div>
										 <div class="layui-input-inline" style="width: 190px;">
											<input type="text" id="didName" name="didName" onblur="updateDidInfo()" lay-verify="required" autocomplete="off" class="layui-input"
												onkeyup="value=value.replace(/[^\w]/ig,'')" maxlength="20" />
										 </div>
										 <label class="layui-form-label" style="width: 100px;">Data ID(Hex):</label>
										 <div class="layui-input-inline" style="width: 190px;">
										 	<!-- onkeyup="value=value.replace(/[^0-9a-fA-F]/g,'')" -->
											<input type="text" id="identifier" name="identifier" lay-verify="required" autocomplete="off" 
												onblur="updateDidInfo()" class="layui-input" onkeyup="filterInput(this)" maxlength="10" size="10" />
										 </div>
									 </div>
									 <div class="layui-col-xs12 layui-col-sm12 layui-col-md12" style="padding-top:20px;">
										 <div class="layui-form-mid">描述信息:</div>
										 <div class="layui-input-inline" style="width:60%;">
										 	<textarea class="layui-textarea" id="description" name="description" lay-verify="required" placeholder="请输入内容" 
										 		style="min-height:120px;max-height:120px;resize: none;" onblur="updateDidInfo()" maxlength="100"></textarea>
										 </div>
										 <button class="layui-btn layui-btn-sm layui-btn-blue" type="button" onclick="addAllocation()">配置</button>
										 <button class="layui-btn layui-btn-sm layui-btn-warm" type="button" onclick="editAllocation()">修改</button>
										 <button class="layui-btn layui-btn-sm" type="button" onclick="saveAllocation()">保存</button>
										 <button class="layui-btn layui-btn-sm layui-btn-danger" type="button" onclick="delAllocationInSession()">删除</button>
									 </div>
									 <div class="layui-col-xs12 layui-col-sm12 layui-col-md12" style="padding-top:20px;">
									 	<table id="dataInfo" lay-filter="dataInfo"></table>
									 </div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- ******************************** 配置页面  *********************** -->
	<div id="allocationModal" class="modal" style="padding-top: 20px;">
		<form class="layui-form" id="formInfo" action="" enctype="multipart/form-data">
			<div style="display: none">
				<input type="text" name="state" /> 
				<input name="didName" type="hidden" />
				<input name="didId" type="hidden" />
				<input name="description" type="hidden" />
				<input name="didTypeId" type="hidden" />
			</div>

			<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12">
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs4 layui-col-sm4 layui-col-md4 noEmpty">Signal Type：</div>
					<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
						<select lay-filter="signal" id="signales" name="signalTypeId">
							<option selected="selected" value="1">Analog</option>
							<option value="2">State Encoded</option>
							<option value="3">Digital</option>
							<option value="4">Text</option>
						</select>
					</div>
				</div>
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 noEmpty" style="text-align: center;">Raw Value Type：</div>
					<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
						<select class="selectValues" name="rawValueType">
							<option selected="selected" value="1">Unsigned Integer</option>
							<option value="2">Signed 2's Complement Integer</option>
							<option value="3">Signed Sign/Magnitude Integer</option>
							<option value="4">32 Bit IEEE Float</option>
							<option value="6">64 Bit IEEE Float</option>
							<option value="7">Packed BCD</option>
							<option value="8">Tasking Float</option>
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
							<input type="text" class="custom-input" id="startOne" style="width:100%;" value="0" autocomplete="off"
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
							<!-- <input type="text" class="custom-input" id="bitOne" style="width:100%;" 
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
							<input type="text" step="1"  min="0" class="custom-input" id="engineeringValue" name="engineeringValue" 
								style="width:100%;" maxlength="10" autocomplete="off"
								onkeyup="this.value= this.value.match(/\d+(\.\d{0,2})?/) ? this.value.match(/\d+(\.\d{0,2})?/)[0] : ''">
							
						</div>
					</div>
					<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
						<div class="layui-col-xs5 layui-col-sm5 layui-col-md5 noEmpty" style="text-align: center;">* RawValue</div>
						<div class="layui-col-xs7 layui-col-sm7 layui-col-md7">
							<input type="text" class="custom-input" min="0" class="custom-input" id="rawValue" name="rawValue" 
								style="width:100%;" maxlength="10" step="1" autocomplete="off"
								onkeyup="this.value= this.value.match(/\d+(\.\d{0,2})?/) ? this.value.match(/\d+(\.\d{0,2})?/)[0] : ''">
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
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12 box-one">
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
						<div class="layui-col-xs11 layui-col-sm11 layui-col-md11 selectUp">
							<!-- <select id="formatOne"></select> -->
							<input type="text" name="format" id="formatOne" class="layui-input" style="position:absolute;z-index:2;width:80%;" 
								  autocomplete="off" maxlength="50">
		                    <select type="text" id="formatOne_select" lay-filter="formatOne_select" class="layui-select" lay-search>
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
						<div class="layui-col-xs12 layui-col-sm12 layui-col-md12 selectUp">
							<select id="" name="unit">
								<option selected="selected" value="km/m">km/m</option>
								<option value="V">V</option>
								<option value="℃">℃</option>
							</select>
						</div>
					</div>
				</div>
			</div>
			
			<!-- 第二个 -->
			<div style="display:none" id="signal2">
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12 bottomMargin">
					<div class="layui-col-xs1 layui-col-sm1 layui-col-md1 empty"></div>
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3" style="text-align: center;">Bit Position(0-2175)</div>
					<div class="layui-col-xs1 layui-col-sm1 layui-col-md1 empty"></div>
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3" style="text-align: center;">Byte(1-272)</div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Bit(7-0)</div>
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
							<!-- onkeyup="value=value.replace(/[^\d\.]/ig,'')" -->
							<input type="text" class="custom-input" id="rawValueOne" style="width:100%;" onclick="verifyInputValue()"
								maxlength="20">
						</div>
					</div>			
					
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3" style="padding:5px 0 0 10px;">
						<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
							<button class="layui-btn layui-btn-sm layui-btn-normal" onclick="addStateInfo()" type="button">Add</button>
							<button class="layui-btn layui-btn-sm layui-btn-danger" type="button" onclick="delStateInfo()">remove</button>
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
					<div class="layui-col-xs1 layui-col-sm1 layui-col-md1 empty"></div>
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3" style="text-align: center;">Bit Position(0-2175)</div>
					<div class="layui-col-xs1 layui-col-sm1 layui-col-md1 empty"></div>
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3" style="text-align: center;">Byte(1-272)</div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2 empty"></div>
					<div class="layui-col-xs2 layui-col-sm2 layui-col-md2">Bit(7-0)</div>
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
						<div class="layui-col-xs9 layui-col-sm9 layui-col-md9 bitsThree">
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
							  autocomplete="off" maxlength="50">
		                    <select id="formatTwo_select" lay-filter="formatTwo_select" class="layui-select" lay-search>
		                        <option selected="selected" value="True/False">True/False</option>
								<option value="On/Off">On/Off</option>
								<option value="Yes/No">Yes/No</option>
		                        <option value="Passed/Failed">Passed/Failed</option>
		                        <option value="Open/Closed">Open/Closed</option>
		                        <option value="Up/Down">Up/Down</option>
		                        <option value="Active/Inactive">Active/Inactive</option>
		                        <option value="Valid/Invalid">Valid/Invalid</option>
		                    </select>
							<!-- <select id="">
								<option selected="selected" value="1">True/False</option>
								<option value="2">On/Off</option>
								<option value="3">Yes/No</option>
							</select> -->
						</div>
					</div>						
				</div>
			</div>
			
			<!-- 第四个 -->
			<div style="display:none" id="signal4">
				<div class="layui-form-item layui-col-xs12 layui-col-sm12 layui-col-md12 bottomMargin">
					<div class="layui-col-xs1 layui-col-sm1 layui-col-md1 empty"></div>
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3" style="text-align: center;">Bit Position(0-2175)</div>
					<div class="layui-col-xs1 layui-col-sm1 layui-col-md1 empty"></div>
					<div class="layui-col-xs3 layui-col-sm3 layui-col-md3" style="text-align: center;">Byte(1-272)</div>
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
				<button type="reset" class="layui-btn layui-btn-primary" id="reset" style="display: none;">重置</button>
			</div>
		</form>
	</div>
	
	<!-- ******************************** ECU数据列表模态窗体  *********************** -->
	<div id="modalecuData" class="modal" style="padding:20px;">
		<form class="layui-form" id="formInfo" action="">
			<table id="ecuDataInfo" lay-filter="ecuDataInfo"></table>
			<div class="layui-form-item" style="text-align:right">
				<button class="layui-btn layui-btn-sm" lay-submit lay-filter="formAffirm" type="button">确认</button>
				<button class="layui-btn layui-btn-sm layui-btn-warm" lay-submit lay-filter="formCancel" type="button">取消</button>
			</div>
		</form>
	</div>
	
	<script type="text/javascript" src="${ctx}/Content/js/jquery-3.3.1.min.js"></script>
	<script src="${ctx}/Content/layui/layui.all.js"></script>
	<script type="text/javascript" src="${ctx}/Content/js/combobox.js"></script>
	<!-- 文本编辑器 -->
	<!----------------------- 工具条 -------------------------->
	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="detail">查看</a>
		<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
	</script>

	<script>
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.$;
	 	var layedit = layui.layedit;
	 	var addAllocationModal;//配置信息模态框
	 	var checkOne;
		$.ajaxSettings.async = false;

		$(function() {
			searchDataInfo();//查询数据类型信息
			searchDidInfo();//查询DID信息
			//绑定车型下拉框
			appendOption("vehicleId", "${ctx}/faultCode/findCarTypeInfo.do");
			appendOption("configurationId", "${ctx}/supplier/findConfigurationLevelInfoById.do?vehicleId=0");
			appendOption("moduleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId=0&&vehicleId=0");
			appendOption("supplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0");
			
			//Analog
			appendOptions("byteOne", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=272");
			appendOptions("bitsOne", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=64");
			appendOptions("bytesOne", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=8");
			appendOptions("bitOne", "${ctx}/dataManage/bindingDisposeInfo.do?start=0&&end=7");
			//appendOptions("formatOne", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=272");
			//State Encoded
			appendOptions("byteTwo", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=272");
			appendOptions("bitsTwo", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=64");
			appendOptions("bytesTwo", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=8");
			appendOptions("bitTwo", "${ctx}/dataManage/bindingDisposeInfo.do?start=0&&end=7");
			//Digital
			appendOptions("byteThree", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=272");
			appendOptions("bitsThree", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=64");
			appendOptions("bitThree", "${ctx}/dataManage/bindingDisposeInfo.do?start=0&&end=7");
			//appendOptions("bytesThree", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=8");
			//text
			appendOptions("byteFour", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=272");
			appendOptions("bitsFour", "${ctx}/dataManage/bindingBitsInfo.do");
			appendOptions("bytesFour", "${ctx}/dataManage/bindingDisposeInfo.do?start=1&&end=32");
			form.render('select');//重新渲染下拉框
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
				var vehicleId=$("#vehicleId").val();
				appendOption("moduleId", "${ctx}/supplier/findModuleByConfigurationLevelId.do?configurationLevelId="+data.value+"&&vehicleId="+vehicleId);
				appendOption("supplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId=0&&configurationLevelId=0&&vehicleId=0");
				form.render('select');
			}
		});
		
		//模块下拉框监听事件
		form.on('select(module)', function(data){
			if(data.value !=''){
				var vehicleId=$("#vehicleId").val();
				var configurationLevelId=$("#configurationId").val();
				appendOption("supplierId", "${ctx}/supplier/findSupplierInfoByModuleId.do?moduleId="+data.value+"&&configurationLevelId="+configurationLevelId
						+"&&vehicleId="+vehicleId);
				form.render('select');
			}
		});
		
	 	//查询数据类型信息
		function searchDataInfo() {
	 		if(checkOne==undefined){
	 			checkOne=0;
	 		}
			table.render({
				elem : '#dataInfo',
				url : '${ctx}/dataManage/findDidAllocationInfoSession.do',
				page : true, //开启分页
				where:{
					didId:checkOne
				},
				limit : 6,
				cols : [ [ //标题栏
					{ type:'numbers',field : '',title : '序号',align:'center'},
					{ field : 'didTypeId',title : '选择',unresize : true,align : 'center',type:'radio'},
					{ field : 'didName',title : 'DID名称',align:'center',},
					{ field : 'description',title : '描述',align:'center',}, 
					{ field : 'startByte',title : '字节数',width : 180,align : 'center'},
					{ field : 'lengthBit',title : '长度',width : 180,align : 'center'}
				] ],
				done:function(e){
					
				}
			});
		}
	 	
	 	//查询DID信息
		function searchDidInfo() {
			table.render({
				elem : '#didInfo',
				url : '${ctx}/dataManage/findDidInfoInSession.do',
				page : false, //开启分页
				limit : 5,
				height:300,
				cols : [ [ //标题栏
					{ field : 'didId',title : '选择',unresize : true,align : 'center',type:'radio'},
					{ field : 'didName',title : 'DID名称',align:'center'}
				] ],
				done:function(res, curr, count){
					// 监听单选单击事件,其中my_table_1是table的lay-filter的属性值
                    table.on('radio(didInfo)',function(obj){    
                    	// 其中，obj是单选后当前行的数据
                    	$("#didName").val(obj.data.didName);
                    	$("#identifier").val(obj.data.identifier);
                    	$("#description").val(obj.data.description);
                    	checkOne=obj.data.didId;
                    	searchDataInfo();
                    });
				}
			});
			//重新获取选中的单选框
			if(checkOne!=undefined){
				$(".layui-table-view[lay-id='didInfo'] .layui-table-body tr[data-index= '"+checkOne+"' ] .layui-form-radio").click();
			}
		}
	 	
	 	//查询ECU数据列表
	 	function searchEcuList() {
			table.render({
				elem : '#ecuDataInfo',
				url : '${ctx}/dataManage/findModuleDataList.do',
				page : true, //开启分页
				limit : 6,
				cols : [ [ //标题栏
					{type:'numbers',field : '',title : '序号',align:'center'},
					{field : 'moduleId',title : '选择',align:'center',type :'checkbox'}, 
					{field : 'moduleName',title : 'ECU',align:'center',unresize : false},
					{field : 'cover',title : '是否默认',align:'center',unresize : false,
						templet:function(data){
							if(data.moduleName!=''){
								return "<span style='color:blue;'>√</span>"
							}else{
								return "<span style='color:red;'>×</span>"
							}
						}
					}
				] ],
				done:function(e){
					
				}
			});
		}
	 	
	 	//ECU数据模态框
	 	function searchecuData(){
			var index = layer.open({
				type : 1,
				title : 'ECU数据列表',
				content : $('#modalecuData'),
				area : [ '660px', '380px' ],
				resize:false,//是否允许拉伸
			});
			searchEcuList();//查询ECU数据列表
			form.render();
	 	}
	 	
	 	//配置模态框
	 	function addAllocation(){
	 		var checkStatus=table.checkStatus('didInfo'),
			data=checkStatus.data;
	 		if(data.length>0){
	 			addAllocationModal = layer.open({
					type : 1,
					title : '配置信息',
					content : $('#allocationModal'),
					area : [ '54%', '85%' ],
					resize:false,//是否允许拉伸
				});
		 		document.all("reset").click();//重置表单
		 		resetModal(1);
		 		searchStateInfo();//state数据列表
		 		$("#allocationModal input[name='state']").val(1);
				form.render();
	 		}else{
	 			layer.msg("请先选择DID信息");
	 		}
	 	}
	 	
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
	 		$.post("${ctx}/dataManage/clearSession.do",function(){});
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
	 		var checkValue=$('input[name="radios"]:checked').val();
			table.render({
				elem : '#stateInfo',
				url : '${ctx}/dataManage/findStateDescriptionSession.do',
				page : false, //开启分页
				limit : 6,
				height:200,
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
	 	
	 	var count=5;
	 	var timer=null;
	 	var tt=0;
	 	//新增按钮
	 	function addDidInfoToSesion(){
	 		$("#addDidInfo").attr('disabled','disabled');
	 		//var num=table.cache['moduleDetailInfo'].length;//获取表格数据长度
 			$.post("${ctx}/dataManage/insertDidToSession.do",function(e){
 				if(e==200){
 					layer.msg("新增成功！");
 					searchDidInfo();
 				}else{
 					layer.alert("数据异常！",{icon:2,title:'提示'});
 				}
 			});
	 		setTimeout(function(){
	 			$("#addDidInfo").removeAttr('disabled','disabled');
	 		},1000);
	 	}
	 	
	 	//修改session中的DID信息(鼠标焦点事件)
	 	function updateDidInfo(){
	 		var checkStatus=table.checkStatus('didInfo'),
			data=checkStatus.data;
	 		var didName=$("#didName").val();
	 		var identifier=$("#identifier").val();
	 		var description=$("#description").val();
	 		if(identifier ==''){
 				identifier="0";
 			}
	 		if(checkOne!=undefined && didName !='' && data.length>0){
	 			$.post("${ctx}/dataManage/updateDidInfoInSession.do",{didId:checkOne,didName:didName,identifier:identifier
	 				,description:description},function(e){
					if(e==200){
						searchDidInfo();
					}else{
						layer.alert("数据异常！",{icon:2,title:'提示'});
					}
				});
	 		}
	 	}
	 	
	 	//-----------------------------配置信息  start-------------------------------------//
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
	 		var bytesFour=parseInt(data.value/8);
	 		$("#bytesFour").val(parseInt(bytesFour));
	 		form.render('select');
		});
	 	
	 	//(Text)bytes监听事件
	 	form.on('select(bytesFour)', function(data){
	 		var bitsFour=parseInt(data.value*8);
	 		$("#bitsFour").val(parseInt(bitsFour));
	 		form.render('select');
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
	 		var zero = '0000';
	 		var tmp  = 0;
	 		if(stateDescription!='' && rawValueOne!=''){
	 			if(checkValue=="Decimal"){
		 			decimals=rawValueOne;
		 			hexs=parseInt(rawValueOne,10).toString(16);
		 		}else if(checkValue=="Hex"){
		 			decimals=parseInt(rawValueOne,16).toString(10);
		 			hexs=rawValueOne;
		 		}
	 			//补零https://blog.csdn.net/qq_36722349/article/details/108651055
	 			//tmp=4-hexs.length;
	 			//hexs=zero.substr(0,tmp) + hexs;
		 		$.post("${ctx}/dataManage/insertRawValueInfoToSession.do",{stateDescription:stateDescription,decimals:decimals
		 				,hexs:hexs},function(e){
					if(e==200){
						layer.msg("新增成功！");
						searchStateInfo();
					}else{
						layer.alert("数据异常！",{icon:2,title:'提示'});
					}
				});
	 		}
	 		return false;
	 	}
	 	
	 	//从session中移除状态描述信息
	 	function delStateInfo(){
	 		var checkStatus=table.checkStatus('stateInfo'),
			data=checkStatus.data;
	 		if(data.length>0){
	 			$.post("${ctx}/dataManage/deleteRawValueInfoInSession.do",{stateDescriptionId:data[0].stateDescriptionId}
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
	  //-----------------------------配置信息 end-------------------------------------//
	  
	  //配置信息保存按钮
	  form.on('submit(formAffirm)', function(data){
			var state = $("#allocationModal input[name='state']").val();
			//新增、修改----配置信息
			var signale=$("#signales").val();
			if(checkOne!=undefined){
				data.field.didName=$("#didName").val();
				data.field.didId=checkOne;
				data.field.description=$("#description").val();
				if(signale==1){//信号类型Analog
					var EngineeringValue=$("#engineeringValue").val();
					var rawValue=$("#rawValue").val();
					var formatOne=$("#formatOne").val();
					var minValue=$("#minValue").val();
					var maxValue=$("#maxValue").val();
					if(EngineeringValue !='' && rawValue !='' && formatOne !='' && minValue !='' && maxValue !=''){
						data.field.startBitPosition=$("#startOne").val();
						data.field.startByte=$("#byteOne").val();
						data.field.startBit=$("#bitOne").val();
						data.field.lengthBit=$("#bitsOne").val();
						data.field.lengthByte=$("#bytesOne").val();
						if(state==1){
							submitDidInfoOne(data.field);
						}else{
							submitDidInfoTwo(data.field);
						}
					}else{
						layer.msg("请填写完整信息！");
					}
				}else if(signale==2){//信号类型State Encoded
					var num=table.cache['stateInfo'].length;
					if(num>0){
						data.field.startBitPosition=$("#startTwo").val();
						data.field.startByte=$("#byteTwo").val();
						data.field.startBit=$("#bitTwo").val();
						data.field.lengthBit=$("#bitsTwo").val();
						data.field.lengthByte=$("#bytesTwo").val();
						if(state==1){
							submitDidInfoOne(data.field);
						}else{
							submitDidInfoTwo(data.field);
						}
					}else{
						layer.msg("请填写完整信息");
					}
				}else if(signale==3){//信号类型Digital
					var formatTwo=$("#formatTwo").val();
					if(formatTwo!=''){
						data.field.startBitPosition=$("#startThree").val();
						data.field.startByte=$("#byteThree").val();
						data.field.startBit=$("#bitThree").val();
						data.field.lengthBit=$("#bitsThree").val();
						data.field.format=formatTwo;
						if(state==1){
							submitDidInfoOne(data.field);
						}else{
							submitDidInfoTwo(data.field);
						}
					}else{
						layer.msg("请填写完整信息");
					}
				}else if(signale==4){//信号类型Text
					data.field.startBitPosition=$("#startFour").val();
					data.field.startByte=$("#byteFour").val();
					data.field.lengthBit=$("#bitsFour").val();
					data.field.lengthByte=$("#bytesFour").val();
					if(state==1){
						submitDidInfoOne(data.field);
					}else{
						submitDidInfoTwo(data.field);
					}
				}
			}else{
				layer.msg("请先选择DID信息");
			}
			return false; //阻止表单跳转
		});
	  
	  	//把配置信息提交到后台(新增)
	  	function submitDidInfoOne(fieldInfo){
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
	  		$.post("${ctx}/dataManage/insertDidInfoToSession.do",fieldInfo,function(e){
				if(e==200){
					layer.close(addAllocationModal);
					layer.msg("新增成功！");
					searchDataInfo();
				}else{
					layer.alert("数据异常！",{icon:2,title:'提示'});
				}
			});
	  	}
	  	
	  	//把配置信息提交到后台(修改)
	  	function submitDidInfoTwo(fieldInfo){
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
	  		$.post("${ctx}/dataManage/updateDidInfoToSession.do",fieldInfo,function(e){
				if(e==200){
					layer.close(addAllocationModal);
					layer.msg("修改成功！");
					searchDataInfo();
				}else{
					layer.alert("数据异常！",{icon:2,title:'提示'});
				}
			});
	  	}
	  	
	  	//修改配置模态框
	 	function editAllocation(){
	  		//获取表格选中的信息
	 		var checkStatus=table.checkStatus('dataInfo'),
			data=checkStatus.data;
	 		if(data.length>0){
	 			addAllocationModal = layer.open({
					type : 1,
					title : '配置信息',
					content : $('#allocationModal'),
					area : [ '50%', '80%' ],
					resize:false,//是否允许拉伸
				});
		 		document.all("reset").click();//重置表单
		 		loadDatatoForm("formInfo", data[0]);//回填信息
		 		resetModal(data[0].signalTypeId);
		 		if(data[0].signalTypeId==1){
		 			$("#startOne").val(data[0].startBitPosition);
					$("#byteOne").val(data[0].startByte);
					$("#bitOne").val(data[0].startBit);
					$("#bitsOne").val(data[0].lengthBit);
					$("#bytesOne").val(data[0].lengthByte);
		 		}else if(data[0].signalTypeId==2){
		 			$("#startTwo").val(data[0].startBitPosition);
					$("#byteTwo").val(data[0].startByte);
					$("#bitTwo").val(data[0].startBit);
					$("#bitsTwo").val(data[0].lengthBit);
					$("#bytesTwo").val(data[0].lengthByte);
					transferSession(data[0].didTypeId);
		 		}else if(data[0].signalTypeId==3){
		 			$("#startThree").val(data[0].startBitPosition);
					$("#byteThree").val(data[0].startByte);
					$("#bitThree").val(data[0].startBit);
					$("#formatTwo").val(data[0].format);
		 		}else if(data[0].signalTypeId==4){
		 			$("#startFour").val(data[0].startBitPosition);
					$("#byteFour").val(data[0].startByte);
					$("#bitsFour").val(data[0].lengthBit);
					$("#bytesFour").val(data[0].lengthByte);
		 		}
		 		searchStateInfo();//state数据列表
		 		$("#allocationModal input[name='state']").val(2);
				form.render();
	 		}else{
	 			layer.msg("请先选择一条信息");
	 		}
	 	}
	  	
	  	//通过ID筛选session中的状态描述信息
	  	function transferSession(didTypeId){
	  		$.post("${ctx}/dataManage/transferSessionById.do",{didTypeId:didTypeId},function(e){});
	  	}
	  	
	  	//删除session中的DID信息
	  	function delDidInfoInSession(){
	  		//获取表格选中的信息
	 		var checkStatus=table.checkStatus('didInfo'),
			data=checkStatus.data;
	  		if(data.length>0){
	  			layer.confirm("确定要删除该条数据吗？", {
					icon : 3,
					title : '提示',
					offset : '150px'
				}, function(index) {
					layer.close(index);
					$.getJSON("${ctx}/dataManage/deleteDidInfoInSession.do", {
						didId : data[0].didId
					}, function(e) {
						if (e==200) {
							layer.msg("已删除！", {offset : '150px'});
							searchDidInfo();
							searchDataInfo();
						} else {
							layer.alert(e.text, {icon : 2,title : '提示',offset : '150px'});
						}
					});
				});
	  		}
	  	}
	  	
	  	//删除session中的配置信息
	  	function delAllocationInSession(){
	  		//获取表格选中的信息
	 		var checkStatus=table.checkStatus('dataInfo'),
			data=checkStatus.data;
	 		if(data.length>0){
	  			layer.confirm("确定要删除该条数据吗？", {
					icon : 3,
					title : '提示',
					offset : '150px'
				}, function(index) {
					layer.close(index);
					$.getJSON("${ctx}/dataManage/deleteAllocationInSession.do", {
						didTypeId : data[0].didTypeId
					}, function(e) {
						if (e==200) {
							layer.msg("已删除！", {offset : '150px'});
							searchDataInfo();
						} else {
							layer.alert(e.text, {icon : 2,title : '提示',offset : '150px'});
						}
					});
				});
	  		}
	  	}
	  	
	  	//保存按钮
	  	function saveAllocation(){
	  		var num=table.cache['dataInfo'].length;
	  		var vehicleId=$("#vehicleId").val();
	  		var moduleId=$("#moduleId").val();
	  		var supplierId=$("#supplierId").val();
	  		var configurationId=$("#configurationId").val();
	  		if(num>0){
	  			if(vehicleId=="0"){
	  				layer.tips('请选择车型','.carTypeId');
	  			}else if(configurationId=="0"){
	  				layer.tips('请选择配置','.configurationId');
	  			}else if(moduleId=="0"){
	  				layer.tips('请选择模块','.moduleId');
	  			}else if(supplierId=="0"){
	  				layer.tips('请选择供应商','.supplierId');
	  			}else{
	  				$.getJSON("${ctx}/dataManage/insertAllocationInfo.do", {vehicleId:vehicleId,moduleId:moduleId
	  					,configurationLevelId:configurationId,supplierId:supplierId,didId:checkOne}, function(d) {
						if (d.text=="success") {
							layer.closeAll();
							layer.msg("保存成功", { icon : 1, offset : '150px' });
							searchDidInfo();//查询DID信息
							searchDataInfo();
						} else {
							layer.alert(d.text, { icon : 2, title : '提示', offset : '150px' });
						}
					});
	  			}
	  		}else{
	  			layer.msg("暂无需要保存的数据");
	  		}
	  	}
	  	//$('input[name="sex"]:checked').val();
	  	
	  	function filterInput(txt){
	  		var text=txt.replace(/[^0-9a-fA-F]/g,'');
	  		if(text.length>10){
	  			text=text.substring(0,10);
	  		}
	  		text=text.toUpperCase();
	  		//txt=text;
	  		$("#identifier").val(text);
	  	} 
	</script>
</body>
</html>
