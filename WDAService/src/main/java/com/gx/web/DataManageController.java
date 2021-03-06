package com.gx.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gx.po.Did;
import com.gx.po.DidType;
import com.gx.po.FlowStep;
import com.gx.po.HarnessConfiguration;
import com.gx.po.Module;
import com.gx.po.ReVehicleSupplier;
import com.gx.po.ResponseOrder;
import com.gx.po.StateDescription;
import com.gx.service.IDataManageService;
import com.gx.vo.AppendOptionVo;
import com.gx.vo.DidVo;
import com.gx.vo.FaultCodeVo;
import com.gx.vo.FlowVo;
import com.gx.vo.JsonReturn;
import com.gx.vo.LayuiJSON;
import com.gx.vo.NodeVo;
import com.gx.vo.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/dataManage")
@Api(value = "数据管理接口", description = "数据管理相关api")
public class DataManageController {
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	private Object result;

	@Autowired
	private IDataManageService dataManageService;

	/**
	 * 新增DID信息到session中
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertDidToSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增DID信息到session中", notes = "DID管理", httpMethod = "POST", response = Gson.class)
	public Object insertDidToSession(HttpSession session) {
		boolean state = false;
		Did did = new Did();
		List<Did> didInfo = (List<Did>) session.getAttribute("listDid");
		if (didInfo == null) {
			didInfo = new ArrayList<Did>();
			did.setDidId(0);
			did.setDidName("C101");
		} else {
			int num = didInfo.size();
			int id = 0;
			if (num > 0) {
				id = didInfo.get(num - 1).getDidId() + 1;
			}
			did.setDidId(id);
			did.setDidName("C" + (101 + id));
		}
		did.setIdentifier("1");
		state = didInfo.add(did);
		session.setAttribute("listDid", didInfo);
		if (state) {
			return gson.toJson(200);
		} else {
			return gson.toJson(404);
		}
	}

	/**
	 * 从session中查询DID信息
	 * 
	 * @param page
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findDidInfoInSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "从session中查询DID信息", notes = "DID管理", httpMethod = "POST", response = JSONSerializer.class)
	public Object findDidInfoInSession(Page page, HttpSession session) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<Did> didInfo = (List<Did>) session.getAttribute("listDid");
		if (didInfo == null) {
			didInfo = new ArrayList<Did>();
		}

		List<Did> didList = new ArrayList<Did>();
		// 分页
		/*
		 * if (didInfo.size() > 0) { int start = 0; for (int i = 0; i < page.getLimit();
		 * i++) { start = page.getStartIndex() + i; if (start < didInfo.size()) {
		 * didList.add(didInfo.get(start)); } else { break; } } } else { didList =
		 * didInfo; }
		 */
		didList = didInfo;
		LayuiJSON<Did> info = new LayuiJSON<Did>(didInfo.size(), didList);
		return JSONSerializer.toJSON(info);
	}

	/**
	 * 修改session中的DID信息
	 * 
	 * @param didId
	 *            索引
	 * @param didName
	 *            DID名称
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateDidInfoInSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改session中的DID信息", notes = "DID管理", httpMethod = "POST", response = Gson.class)
	public Object updateDidInfoInSession(int didId, String didName, String identifier, String description,
			HttpSession session) {
		boolean state = false;
		List<Did> didInfo = (List<Did>) session.getAttribute("listDid");
		if (description == null) {
			description = "";
		}
		if (didId >= 0 && didName != null && didInfo != null) {
			for (int i = 0; i < didInfo.size(); i++) {
				if (didInfo.get(i).getDidId() == didId) {
					didInfo.get(i).setDidName(didName);
					didInfo.get(i).setIdentifier(identifier);
					didInfo.get(i).setDescription(description);
					state = true;
				}
			}
			session.setAttribute("listDid", didInfo);
		}
		// 获取session中的配置信息
		List<DidType> listDid = (List<DidType>) session.getAttribute("listDidAllocation");
		if (listDid != null) {
			for (int i = 0; i < listDid.size(); i++) {
				if (listDid.get(i).getDidId().equals(didId)) {
					// 修改配置信息
					listDid.get(i).setDidName(didName);
					listDid.get(i).setDescription(description);
				}
			}
		}
		if (state) {
			return gson.toJson(200);
		} else {
			return gson.toJson(404);
		}
	}

	/**
	 * 查询CAN信息(绑定下拉框)
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findCanBoxInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询CAN信息", notes = "用于绑定下拉框", httpMethod = "POST", response = Gson.class)
	public Object findCanBoxInfo() {
		result = dataManageService.selectCanBoxInfo();
		return gson.toJson(result);
	}

	/**
	 * 查询节点信息
	 * 
	 * @param vehicleId
	 *            车型id
	 * @param configurationLevelId
	 *            配置id
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findNodeInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询节点信息", notes = "节点在线管理", httpMethod = "POST", response = JSONSerializer.class)
	public Object findNodeInfo(Integer vehicleId, Integer configurationLevelId, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<NodeVo> node = dataManageService.selectNodeInfo(vehicleId, configurationLevelId, page.getStartIndex(),
				page.getLimit());

		List<NodeVo> nodeTwo = new ArrayList<NodeVo>();// 临时容器

		// 合并同一车型下的配置名称
		for (int i = 0; i < node.size(); i++) {
			if (i == 0) {
				int nodeId = node.get(i).getNodeId();
				node.get(i).setTestNodeId(String.valueOf(nodeId));
				nodeTwo.add(node.get(i));
			} else {
				int num = 1;
				for (int j = 0; j < nodeTwo.size(); j++) {
					// 如果是同一车型id，就将配置名称拼接显示
					if (node.get(i).getVehicleId() == nodeTwo.get(j).getVehicleId()) {
						String name = nodeTwo.get(j).getConfigurationLevelName();
						String nameTwo = node.get(i).getConfigurationLevelName();

						String idOne = nodeTwo.get(j).getTestNodeId();
						Integer idTwo = node.get(i).getNodeId();

						nodeTwo.get(j).setConfigurationLevelName(name + "&" + nameTwo);
						// 合并节点id
						nodeTwo.get(j).setTestNodeId(idOne + "," + idTwo);

						num = 0;
						break;
					}
				}
				if (num == 1) {
					nodeTwo.add(node.get(i));
				}
			}
		}

		int totalRows = dataManageService.selectNodeInfoRows(vehicleId, configurationLevelId);
		LayuiJSON<NodeVo> nodeInfo = new LayuiJSON<NodeVo>(totalRows, nodeTwo);
		return JSONSerializer.toJSON(nodeInfo);
	}

	/**
	 * 查询线束段配置信息
	 * 
	 * @param vehicleId
	 *            车型id
	 * @param configurationLevelId
	 *            配置id
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findWiringHarnessInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询线束段配置信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findWiringHarnessInfo(Integer vehicleId, Integer configurationLevelId, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<NodeVo> harness = dataManageService.selectWiringHarnessInfo(vehicleId, configurationLevelId,
				page.getStartIndex(), page.getLimit());
		int totalRows = dataManageService.selectWiringHarnessInfoRows(vehicleId, configurationLevelId);
		LayuiJSON<NodeVo> harnessInfo = new LayuiJSON<NodeVo>(totalRows, harness);
		return JSONSerializer.toJSON(harnessInfo);
	}

	/**
	 * 新增线束段配置信息
	 * 
	 * @param harnessConfiguration
	 *            线束段配置信息实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertWiringHarnessInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增线束段配置信息", httpMethod = "POST", response = Gson.class)
	public Object insertWiringHarnessInfo(HarnessConfiguration harnessConfiguration) {
		result = dataManageService.insertWiringHarnessInfo(harnessConfiguration);
		return gson.toJson(result);
	}

	/**
	 * 绑定DID管理配置信息的下拉框
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/bindingDisposeInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "绑定DID管理配置信息的下拉框(1)", httpMethod = "POST", response = Gson.class)
	public Object bindingDisposeInfo(Integer start, Integer end) {
		List<AppendOptionVo> byteInfo = new ArrayList<AppendOptionVo>();
		AppendOptionVo info = null;
		for (int i = start; i <= end; i++) {
			info = new AppendOptionVo();
			info.setId(i);
			info.setName(String.valueOf(i));
			byteInfo.add(info);
		}
		return gson.toJson(byteInfo);
	}

	/**
	 * 绑定DID管理配置信息的下拉框02
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/bindingBitsInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "绑定DID管理配置信息的下拉框(2)", httpMethod = "POST", response = Gson.class)
	public Object bindingBitsInfo() {
		List<AppendOptionVo> byteInfo = new ArrayList<AppendOptionVo>();
		AppendOptionVo info = null;
		for (int i = 1; i <= 32; i++) {
			info = new AppendOptionVo();
			info.setId(i * 8);
			info.setName(String.valueOf(i * 8));
			byteInfo.add(info);
		}
		return gson.toJson(byteInfo);
	}

	/**
	 * 修改线束段配置信息
	 * 
	 * @param harnessConfiguration
	 *            线束段配置信息实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateWiringHarnessInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改线束段配置信息", httpMethod = "PATCH", response = Gson.class)
	public Object updateWiringHarnessInfo(HarnessConfiguration harnessConfiguration) {
		result = dataManageService.updateWiringHarnessInfo(harnessConfiguration);
		return gson.toJson(result);
	}

	/**
	 * 删除线束段配置信息
	 * 
	 * @param harnessConfigurationId
	 *            线束段配置id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteWiringHarnessInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除线束段配置信息", httpMethod = "DELETE", response = Gson.class)
	public Object deleteWiringHarnessInfo(Integer harnessConfigurationId) {
		result = dataManageService.deleteWiringHarnessInfo(harnessConfigurationId);
		return gson.toJson(result);
	}

	/**
	 * 绑定CAN通道下拉框
	 * 
	 * @param vehicleId
	 *            车型id
	 * @param configurationId
	 *            配置id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findCanPassageBoxInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "绑定CAN通道下拉框", httpMethod = "POST", response = Gson.class)
	public Object findCanPassageBoxInfo(Integer vehicleId, Integer configurationId) {
		result = dataManageService.selectCanPassageBoxInfo(vehicleId, configurationId);
		return gson.toJson(result);
	}

	/**
	 * 绑定线束段下拉框
	 * 
	 * @param vehicleId
	 *            车型id
	 * @param configurationId
	 *            配置id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findHarnessBoxInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "绑定线束段下拉框", httpMethod = "POST", response = Gson.class)
	public Object findHarnessBoxInfo(Integer vehicleId, Integer configurationId) {
		result = dataManageService.selectHarnessBoxInfo(vehicleId, configurationId);
		return gson.toJson(result);
	}

	/**
	 * 保存节点信息到session中
	 * 
	 * @param node
	 *            节点实体类
	 * @param vehicleId
	 *            车型id
	 * @param configurationLevelId
	 *            配置id
	 * @param moduleId
	 *            模块id
	 * @param supplierId
	 *            供应商id
	 * @param canConfigurationId
	 *            CAN通道id
	 * @param harness
	 *            线束段
	 * @param session
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	@ResponseBody
	@RequestMapping(value = "/insertNodeInfoBySession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "保存节点信息到session中", httpMethod = "POST", response = Gson.class)
	public Object insertNodeInfoBySession(NodeVo node, Integer vehicleId, Integer configurationLevelId,
			Integer moduleId, Integer supplierId, Integer canConfigurationId, String harness, HttpSession session) {
		boolean state = false;
		List<NodeVo> listNode = (List<NodeVo>) session.getAttribute("listNode");
		if (listNode == null) {
			listNode = new ArrayList<NodeVo>();
			node.setNodeId(0);
		} else {
			node.setNodeId(listNode.size());
		}
		// 根据id查询name值，用于查询表格
		// List<NodeVo> nodes = dataManageService.selectNodeInfoById(canConfigurationId,
		// moduleId, configurationLevelId);
		NodeVo nodes = dataManageService.selectNameInfoById(canConfigurationId, moduleId, configurationLevelId);
		// 判断输入的值与session的是否重复
		for (int i = 0; i < listNode.size(); i++) {
			if (node.getVehicleId().equals(listNode.get(i).getVehicleId())
					&& node.getConfigurationLevelId().equals(listNode.get(i).getConfigurationLevelId())
					&& node.getCanConfigurationId().equals(listNode.get(i).getCanConfigurationId())
					&& node.getHarness().equalsIgnoreCase(listNode.get(i).getHarness())
					&& node.getModuleId().equals(listNode.get(i).getModuleId())
					&& node.getSupplierId().equals(listNode.get(i).getSupplierId())
					&& node.getTxId().equalsIgnoreCase(listNode.get(i).getTxId())
					&& node.getRxId().equalsIgnoreCase(listNode.get(i).getRxId())) {
				return gson.toJson(500);
			}
		}
		// 根据下拉框的id，设置查询表格的name值
		node.setCanPassage(nodes.getCanPassage());
		node.setModuleName(nodes.getModuleName());
		node.setConfigurationLevelName(nodes.getConfigurationLevelName());
		state = listNode.add(node);
		session.setAttribute("listNode", listNode);

		if (state) {
			return gson.toJson(200);
		} else {
			return gson.toJson(404);
		}
	}

	/**
	 * 查询session中的节点配置信息
	 * 
	 * @param node
	 *            节点vo
	 * @param page
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/findNodeBySession")
	@ApiOperation(value = "查询session中的节点配置信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findNodeBySession(NodeVo node, Page page, HttpSession session) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		// 从session获取数据集
		List<NodeVo> listNode = (List<NodeVo>) session.getAttribute("listNode");
		if (listNode == null) {
			listNode = new ArrayList<NodeVo>();
		}
		// 分页
		List<NodeVo> nodeInfo = new ArrayList<NodeVo>();
		if (listNode.size() > 0) {
			int start = 0;
			for (int i = 0; i < page.getLimit(); i++) {
				start = page.getStartIndex() + i;
				if (start < listNode.size()) {
					nodeInfo.add(listNode.get(start));
				} else {
					break;
				}
			}
		} else {
			nodeInfo = listNode;
		}
		LayuiJSON<NodeVo> faultInfo = new LayuiJSON<NodeVo>(listNode.size(), nodeInfo);
		return JSONSerializer.toJSON(faultInfo);
	}

	/**
	 * 修改session中的节点信息
	 * 
	 * @param node
	 *            节点实体类
	 * @param vehicleId
	 *            车型id
	 * @param configurationLevelId
	 *            配置id
	 * @param moduleId
	 *            模块id
	 * @param supplierId
	 *            供应商id
	 * @param canConfigurationId
	 *            CAN通道id
	 * @param harness
	 *            线束段
	 * @param session
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	@ResponseBody
	@RequestMapping(value = "/updateNodeInfoBySession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改session中的节点信息", httpMethod = "PATCH", response = Gson.class)
	public Object updateNodeInfoBySession(NodeVo node, Integer vehicleId, Integer configurationLevelId,
			Integer moduleId, Integer supplierId, Integer canConfigurationId, String harness, HttpSession session) {
		boolean state = false;
		List<NodeVo> listNode = (List<NodeVo>) session.getAttribute("listNode");
		if (listNode != null) {
			// 根据id查询name值，用于查询表格
			// List<NodeVo> nodes = dataManageService.selectNodeInfoById(canConfigurationId,
			// moduleId,
			// configurationLevelId);
			NodeVo nodes = dataManageService.selectNameInfoById(canConfigurationId, moduleId, configurationLevelId);
			// 判断输入的值与session的是否重复
			for (int i = 0; i < listNode.size(); i++) {
				if (node.getVehicleId().equals(listNode.get(i).getVehicleId())
						&& node.getConfigurationLevelId().equals(listNode.get(i).getConfigurationLevelId())
						&& node.getCanConfigurationId().equals(listNode.get(i).getCanConfigurationId())
						&& node.getHarness().equalsIgnoreCase(listNode.get(i).getHarness())
						&& node.getModuleId().equals(listNode.get(i).getModuleId())
						&& node.getSupplierId().equals(listNode.get(i).getSupplierId())
						&& node.getTxId().equalsIgnoreCase(listNode.get(i).getTxId())
						&& node.getRxId().equalsIgnoreCase(listNode.get(i).getRxId())
						&& node.getNodeId() != listNode.get(i).getNodeId()) {
					return gson.toJson(500);
				}
			}
			// 先移除再重新添加
			int index = node.getNodeId();
			NodeVo nodeVo = listNode.remove(index);
			if (nodeVo != null) {
				state = true;
			}
			// 根据下拉框的id，设置查询表格的name值
			// node.setCanPassage(nodes.get(0).getCanPassage());
			// node.setModuleName(nodes.get(0).getModuleName());
			// node.setConfigurationLevelName(nodes.get(0).getConfigurationLevelName());
			node.setCanPassage(nodes.getCanPassage());
			node.setModuleName(nodes.getModuleName());
			node.setConfigurationLevelName(nodes.getConfigurationLevelName());

			listNode.add(node.getNodeId(), node);
			session.setAttribute("listNode", listNode);
		}
		if (state) {
			return gson.toJson(200);
		} else {
			return gson.toJson(404);
		}
	}

	/**
	 * 删除session中的节点信息
	 * 
	 * @param session
	 * @param nodeId
	 *            节点id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/deleteNodeInfoBySession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除session中的节点信息", httpMethod = "DELETE", response = Gson.class)
	public Object deleteNodeInfoBySession(HttpSession session, int nodeId) {
		boolean state = false;
		List<NodeVo> listNode = (List<NodeVo>) session.getAttribute("listNode");
		if (nodeId >= 0) {
			NodeVo nodeVo = null;
			for (int i = 0; i < listNode.size(); i++) {
				if (listNode.get(i).getNodeId() == nodeId) {
					nodeVo = listNode.remove(i);
					break;
				}
			}
			if (nodeVo != null) {
				state = true;
			}
		}
		if (state) {
			return gson.toJson(200);
		} else {
			return gson.toJson(404);
		}
	}

	/**
	 * 新增节点配置信息（将session的数据保存到数据库）
	 * 
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/insertNodeInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增节点配置信息", notes = "将session的数据保存到数据库", httpMethod = "POST", response = Gson.class)
	public Object insertNodeInfo(HttpSession session) {
		List<NodeVo> listNode = new ArrayList<NodeVo>();
		listNode = (List<NodeVo>) session.getAttribute("listNode");
		result = dataManageService.insertNodeInfo(listNode);
		return gson.toJson(result);
	}

	/**
	 * 清空节点信息session
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/clearNodeSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "清空节点信息session", httpMethod = "DELETE", response = Gson.class)
	public Object clearNodeSession(HttpServletRequest request) {
		request.getSession().removeAttribute("listNode");
		return gson.toJson(true);
	}

	/**
	 * 删除节点信息
	 * 
	 * @param nodeId
	 *            节点id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteNodeInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除节点信息", httpMethod = "DELETE", response = Gson.class)
	public Object deleteNodeInfo(Integer nodeId) {
		result = dataManageService.deleteNodeInfo(nodeId);
		return gson.toJson(result);
	}

	/**
	 * 查询节点详情信息（查看详情）
	 * 
	 * @param vehicleId
	 *            车型id
	 * @param page
	 *            分页实体类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findNodeDetailInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询节点详情信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findNodeDetailInfo(Integer vehicleId, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<NodeVo> node = dataManageService.selectNodeDetailInfo(vehicleId, page.getStartIndex(), page.getLimit());
		int totalRows = dataManageService.selectNodeDetailInfoRows(vehicleId);
		LayuiJSON<NodeVo> nodeInfo = new LayuiJSON<NodeVo>(totalRows, node);
		return JSONSerializer.toJSON(nodeInfo);
	}

	/**
	 * 修改节点信息
	 * 
	 * @param nodeVo
	 *            节点vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateNodeInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改节点信息", httpMethod = "PATCH", response = JSONSerializer.class)
	public Object updateNodeInfo(NodeVo nodeVo) {
		result = dataManageService.updateNodeInfo(nodeVo);
		return gson.toJson(result);
	}

	/**
	 * 新增DID配置信息(保存到session中)
	 * 
	 * @param didInfo
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/insertDidInfoToSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增DID配置信息到session中)", httpMethod = "POST", response = Gson.class)
	public Object insertDidInfoToSession(DidType didInfo, HttpSession session) {
		boolean state = false;
		List<DidType> listDid = (List<DidType>) session.getAttribute("listDidAllocation");
		if (listDid == null) {
			listDid = new ArrayList<DidType>();
			didInfo.setDidTypeId(0);
		} else {
			int num = listDid.size();
			int id = 0;
			if (num > 0) {
				id = listDid.get(num - 1).getDidTypeId() + 1;
			}
			didInfo.setDidTypeId(id);
		}

		if (didInfo.getSignalTypeId() == 2) {
			// 获取session中的状态描述信息
			List<StateDescription> stateOne = (List<StateDescription>) session.getAttribute("stateInfoOne");
			for (int i = 0; i < stateOne.size(); i++) {
				stateOne.get(i).setDidTypeId(didInfo.getDidTypeId());
			}
			// 将获取到的信息添加到另一个session
			List<StateDescription> stateTwo = (List<StateDescription>) session.getAttribute("stateInfoTwo");
			if (stateTwo == null) {
				stateTwo = new ArrayList<StateDescription>();
			}
			stateTwo.addAll(stateOne);
			session.setAttribute("stateInfoTwo", stateTwo);
		}
		state = listDid.add(didInfo);
		session.setAttribute("listDidAllocation", listDid);
		if (state) {
			return gson.toJson(200);
		} else {
			return gson.toJson(404);
		}
	}

	/**
	 * 查询session中的DID配置信息
	 * 
	 * @param page
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findDidAllocationInfoSession")
	@ApiOperation(value = "查询session中的DID配置信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findDidAllocationInfoSession(int didId, Page page, HttpSession session) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<DidType> listDid = (List<DidType>) session.getAttribute("listDidAllocation");
		if (listDid == null) {
			listDid = new ArrayList<DidType>();
		}
		// 通过didId进行筛选信息
		List<DidType> listDids = new ArrayList<DidType>();
		for (int i = 0; i < listDid.size(); i++) {
			if (listDid.get(i).getDidId() == didId) {
				listDids.add(listDid.get(i));
			}
		}
		List<DidType> didInfo = new ArrayList<DidType>();
		// 分页
		if (listDids.size() > 0) {
			int start = 0;
			for (int i = 0; i < page.getLimit(); i++) {
				start = page.getStartIndex() + i;
				if (start < listDids.size()) {
					didInfo.add(listDids.get(start));
				} else {
					break;
				}
			}
		} else {
			didInfo = listDids;
		}

		LayuiJSON<DidType> didInfos = new LayuiJSON<DidType>(listDids.size(), didInfo);
		return JSONSerializer.toJSON(didInfos);
	}

	/**
	 * 添加状态描述信息到session中(DID管理)
	 * 
	 * @param stateInfo
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/insertRawValueInfoToSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "添加状态描述信息到session中(DID管理)", httpMethod = "POST", response = Gson.class)
	public Object insertRawValueInfoToSession(StateDescription stateInfo, HttpSession session) {
		boolean state = false;
		List<StateDescription> listState = (List<StateDescription>) session.getAttribute("stateInfoOne");
		if (listState == null) {
			listState = new ArrayList<StateDescription>();
			stateInfo.setStateDescriptionId(0);
		} else {
			int num = listState.size();
			int id = 0;
			if (num > 0) {
				id = listState.get(num - 1).getStateDescriptionId() + 1;
			}
			stateInfo.setStateDescriptionId(id);
		}

		// 转换为大写
		String number = stateInfo.getHexs().toUpperCase();
		if (number.length() > 1) {
			number = "0x" + number;
		} else {
			number = "0x" + number;
		}

		stateInfo.setHexs(number);

		state = listState.add(stateInfo);
		session.setAttribute("stateInfoOne", listState);
		if (state) {
			return gson.toJson(200);
		} else {
			return gson.toJson(404);
		}
	}

	/**
	 * 查询session中状态描述信息(DID管理)
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findStateDescriptionSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询session中状态描述信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findStateDescriptionSession(HttpSession session) {
		List<StateDescription> listState = (List<StateDescription>) session.getAttribute("stateInfoOne");
		if (listState == null) {
			listState = new ArrayList<StateDescription>();
		}
		LayuiJSON<StateDescription> stateInfos = new LayuiJSON<StateDescription>(listState.size(), listState);
		return JSONSerializer.toJSON(stateInfos);
	}

	/**
	 * 查询拓扑图信息
	 * 
	 * @param vehicleId
	 *            车型id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findNodeDetialList", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询拓扑图信息", httpMethod = "POST", response = Gson.class)
	public Object findNodeDetialList(Integer vehicleId) {
		result = dataManageService.selectNodeDetialList(vehicleId);
		return gson.toJson(result);
	}

	/**
	 * 删除session中的状态描述信息
	 * 
	 * @param stateDescriptionId
	 *            状态描述ID
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/deleteRawValueInfoInSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除session中的状态描述信息", httpMethod = "DELETE", response = Gson.class)
	public Object deleteRawValueInfoInSession(int stateDescriptionId, HttpSession session) {
		boolean state = false;
		StateDescription stateInfo = null;
		List<StateDescription> listState = (List<StateDescription>) session.getAttribute("stateInfoOne");
		if (listState != null) {
			for (int i = 0; i < listState.size(); i++) {
				if (listState.get(i).getStateDescriptionId() == stateDescriptionId) {
					stateInfo = listState.remove(i);
				}
			}
		}
		if (stateInfo != null) {
			state = true;
		}
		session.setAttribute("stateInfoOne", listState);
		if (state) {
			return gson.toJson(200);
		} else {
			return gson.toJson(404);
		}
	}

	/**
	 * 清除session中的数据
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/clearSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "清除session中的状态描述信息", httpMethod = "DELETE", response = Gson.class)
	public Object clearSession(HttpServletRequest request) {
		request.getSession().removeAttribute("stateInfoOne");
		return gson.toJson(true);
	}

	/**
	 * 通过ID筛选session中的状态描述信息
	 * 
	 * @param didTypeId
	 *            DID类型ID
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/transferSessionById", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过ID筛选session中的状态描述信息", httpMethod = "POST", response = Gson.class)
	public Object transferSessionById(int didTypeId, HttpSession session) {
		List<StateDescription> stateTwo = (List<StateDescription>) session.getAttribute("stateInfoTwo");
		List<StateDescription> stateOne = new ArrayList<StateDescription>();
		if (stateTwo != null && didTypeId >= 0) {
			for (int i = 0; i < stateTwo.size(); i++) {
				if (stateTwo.get(i).getDidTypeId() == didTypeId) {
					stateOne.add(stateTwo.get(i));
				}
			}
		}
		session.setAttribute("stateInfoOne", stateOne);
		return gson.toJson(true);
	}

	/**
	 * 修改DID配置信息(保存到session中)
	 * 
	 * @param didInfo
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/updateDidInfoToSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改DID配置信息(保存到session中)", httpMethod = "POST", response = Gson.class)
	public Object updateDidInfoToSession(DidType didInfo, HttpSession session) {
		boolean state = false;
		List<DidType> listDid = (List<DidType>) session.getAttribute("listDidAllocation");

		if (listDid != null) {
			for (int i = 0; i < listDid.size(); i++) {
				if (listDid.get(i).getDidTypeId().equals(didInfo.getDidTypeId())) {
					listDid.remove(i);
				}
			}
		}

		if (didInfo.getSignalTypeId() == 2) {
			// 获取session中的状态描述信息
			List<StateDescription> stateOne = (List<StateDescription>) session.getAttribute("stateInfoOne");
			for (int i = 0; i < stateOne.size(); i++) {
				stateOne.get(i).setDidTypeId(didInfo.getDidTypeId());
			}
			// 将获取到的信息添加到另一个session
			List<StateDescription> stateTwo = (List<StateDescription>) session.getAttribute("stateInfoTwo");
			if (stateTwo != null) {
				for (int i = 0; i < stateTwo.size(); i++) {
					if (stateTwo.get(i).getDidTypeId().equals(didInfo.getDidTypeId())) {
						stateTwo.remove(i);
					}
				}
			}
			stateTwo.addAll(stateOne);
			session.setAttribute("stateInfoTwo", stateTwo);
		}

		state = listDid.add(didInfo);
		session.setAttribute("listDidAllocation", listDid);
		if (state) {
			return gson.toJson(200);
		} else {
			return gson.toJson(404);
		}
	}

	/**
	 * 删除session中的DID信息
	 * 
	 * @param didId
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/deleteDidInfoInSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除session中的DID信息", httpMethod = "DELETE", response = Gson.class)
	public Object deleteDidInfoInSession(int didId, HttpSession session) {
		boolean state = false;
		Did didOne = null;
		// 获取session中的DID信息
		List<Did> didInfo = (List<Did>) session.getAttribute("listDid");
		if (didInfo != null) {
			for (int i = 0; i < didInfo.size(); i++) {
				if (didInfo.get(i).getDidId().equals(didId)) {
					didOne = didInfo.remove(i);
				}
			}
		}
		// 获取session中的配置信息
		List<DidType> listDid = (List<DidType>) session.getAttribute("listDidAllocation");
		if (listDid != null) {
			for (int i = 0; i < listDid.size(); i++) {
				if (listDid.get(i).getDidId().equals(didId)) {
					listDid.remove(i);
				}
			}
		}
		if (didOne != null) {
			state = true;
		}
		session.setAttribute("listDid", didInfo);
		session.setAttribute("listDidAllocation", listDid);
		if (state) {
			return gson.toJson(200);
		} else {
			return gson.toJson(404);
		}
	}

	/**
	 * 删除session中的配置信息
	 * 
	 * @param didTypeId
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/deleteAllocationInSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除session中的配置信息", httpMethod = "DELETE", response = Gson.class)
	public Object deleteAllocationInSession(int didTypeId, HttpSession session) {
		boolean state = false;
		DidType didType = null;
		// 获取session中的配置信息
		List<DidType> listDid = (List<DidType>) session.getAttribute("listDidAllocation");
		if (listDid != null) {
			for (int i = 0; i < listDid.size(); i++) {
				if (listDid.get(i).getDidTypeId().equals(didTypeId)) {
					didType = listDid.remove(i);
				}
			}
		}
		if (didType != null) {
			state = true;
		}
		session.setAttribute("listDidAllocation", listDid);
		if (state) {
			return gson.toJson(200);
		} else {
			return gson.toJson(404);
		}
	}

	/**
	 * 新增DID类型信息
	 * 
	 * @param vehicleSupplier
	 * @param session
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/insertAllocationInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增DID类型信息", httpMethod = "POST", response = Gson.class)
	public Object insertAllocationInfo(ReVehicleSupplier vehicleSupplier, Integer didId, HttpSession session,
			HttpServletRequest request) {
		JsonReturn jsonReturn = new JsonReturn();
		// 获取session中的DID信息
		List<Did> didInfo = (List<Did>) session.getAttribute("listDid");
		Did did = new Did();
		int num = 0;
		for (int i = 0; i < didInfo.size(); i++) {
			if (didInfo.get(i).getDidId().equals(didId)) {
				did = didInfo.get(i);
				num = i;
				break;
			}
		}
		// 获取session中的配置信息
		List<DidType> listDid = (List<DidType>) session.getAttribute("listDidAllocation");
		// 获取session中的状态描述信息
		List<StateDescription> stateTwo = (List<StateDescription>) session.getAttribute("stateInfoTwo");
		// jsonReturn = dataManageService.insertAllocationInfo(vehicleSupplier, did,
		// listDid, stateTwo);
		jsonReturn.setText("success");
		// 移除session中的数据
		if (jsonReturn.getText().equals("success")) {
			for (int i = 0; i < listDid.size(); i++) {
				if (listDid.get(i).getDidId().equals(did.getDidId())) {
					int id = listDid.get(i).getDidTypeId();
					listDid.remove(i);
					if (stateTwo != null) {
						for (int j = 0; j < stateTwo.size(); j++) {
							if (stateTwo.get(j).getDidTypeId().equals(id)) {
								stateTwo.remove(j);
							}
						}
					}
				}
			}
			// didInfo.get(num).setDidId(didId);
			// 重新设置session中的信息
			didInfo.remove(num);
			if (didInfo.size() == 0) {
				listDid = new ArrayList<DidType>();
			}
			session.setAttribute("listDid", didInfo);
			session.setAttribute("listDidAllocation", listDid);
			session.setAttribute("stateInfoTwo", stateTwo);
		}
		result = jsonReturn;
		return gson.toJson(result);
	}

	/**
	 * 通过车型ID查询模块信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findModuleByVehicleId", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过车型ID查询模块信息", httpMethod = "POST", response = Gson.class)
	public Object findModuleByVehicleId(Integer vehicleId) {
		result = dataManageService.selectModuleByVehicleId(vehicleId);
		return gson.toJson(result);
	}

	/**
	 * 查询快照信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param moduleId
	 *            模块ID
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findSnapshotInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询快照信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findSnapshotInfo(Integer vehicleId, Integer moduleId, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<DidVo> snapshot = dataManageService.selectSnapshotInfo(vehicleId, moduleId, page.getStartIndex(),
				page.getLimit());
		int totalRows = dataManageService.selectSnapshotInfoRows(vehicleId, moduleId);
		LayuiJSON<DidVo> snapshotInfo = new LayuiJSON<DidVo>(totalRows, snapshot);
		return JSONSerializer.toJSON(snapshotInfo);
	}

	/**
	 * 新增快照明细信息到session中
	 * 
	 * @param snapshot
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertSnapshotDetailInfoToSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增快照明细信息到session中", httpMethod = "POST", response = Gson.class)
	public Object insertSnapshotDetailInfoToSession(DidVo snapshot, HttpSession session) {
		boolean state = false;
		List<DidVo> listSnapshot = (List<DidVo>) session.getAttribute("listSnapshot");
		if (listSnapshot == null) {
			listSnapshot = new ArrayList<DidVo>();
			snapshot.setDidId(1);
		} else {
			int num = listSnapshot.size();
			int id = 1;
			if (num > 0) {
				id = listSnapshot.get(num - 1).getDidId() + 1;
			}
			snapshot.setDidId(id);
			// 查询是否有重复的快照名称
			for (int i = 0; i < listSnapshot.size(); i++) {
				if (listSnapshot.get(i).getDidName().equals(snapshot.getDidName())) {
					return gson.toJson(500);
				}
			}
		}

		state = listSnapshot.add(snapshot);
		session.setAttribute("listSnapshot", listSnapshot);
		if (state) {
			return gson.toJson(200);
		} else {
			return gson.toJson(404);
		}
	}

	/**
	 * 从session中查询快照明细信息
	 * 
	 * @param page
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findSnapshotDetailSession")
	@ApiOperation(value = "从session中查询快照明细信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findSnapshotDetailSession(Page page, HttpSession session) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<DidVo> listSnapshot = (List<DidVo>) session.getAttribute("listSnapshot");
		if (listSnapshot == null) {
			listSnapshot = new ArrayList<DidVo>();
		}
		List<DidVo> snapshotInfo = new ArrayList<DidVo>();
		if (listSnapshot.size() > 0) {
			int start = 0;
			for (int i = 0; i < page.getLimit(); i++) {
				start = page.getStartIndex() + i;
				if (start < listSnapshot.size()) {
					snapshotInfo.add(listSnapshot.get(start));
				} else {
					break;
				}
			}
		} else {
			snapshotInfo = listSnapshot;
		}

		LayuiJSON<DidVo> info = new LayuiJSON<DidVo>(listSnapshot.size(), snapshotInfo);
		return JSONSerializer.toJSON(info);
	}

	/**
	 * 修改session中的快照明细信息
	 * 
	 * @param snapshot
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateSnapshotDetailInfoToSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改session中的快照明细信息", httpMethod = "POST", response = Gson.class)
	public Object updateSnapshotDetailInfoToSession(DidVo snapshot, HttpSession session) {
		boolean state = false;
		List<DidVo> listSnapshot = (List<DidVo>) session.getAttribute("listSnapshot");
		if (listSnapshot != null) {
			// 查询是否有重复的快照名称
			for (int i = 0; i < listSnapshot.size(); i++) {
				if (listSnapshot.get(i).getDidName().equals(snapshot.getDidName())
						&& !listSnapshot.get(i).getDidId().equals(snapshot.getDidId())) {
					return gson.toJson(500);
				}

				if (listSnapshot.get(i).getDidId().equals(snapshot.getDidId())) {
					listSnapshot.set(i, snapshot);
					state = true;
				}
			}
		}

		session.setAttribute("listSnapshot", listSnapshot);
		if (state) {
			return gson.toJson(200);
		} else {
			return gson.toJson(404);
		}
	}

	/**
	 * 删除session中的快照明细信息
	 * 
	 * @param session
	 * @param faultCodeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteSnapshotDetailInfoToSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除session中的快照明细信息", httpMethod = "DELETE", response = Gson.class)
	public Object deleteSnapshotDetailInfoToSession(HttpSession session, int didId) {
		boolean state = false;
		List<DidVo> listSnapshot = (List<DidVo>) session.getAttribute("listSnapshot");
		if (didId > 0) {
			for (int i = 0; i < listSnapshot.size(); i++) {
				if (listSnapshot.get(i).getDidId() == didId) {
					listSnapshot.remove(i);
					state = true;
					break;
				}
			}
		}
		if (state) {
			return gson.toJson(200);
		} else {
			return gson.toJson(404);
		}
	}

	/**
	 * 新增快照信息
	 * 
	 * @param session
	 * @param supplier
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/insertSnapshotInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增快照信息", notes = "从session中获取数据", httpMethod = "POST", response = Gson.class)
	public Object insertSnapshotInfo(HttpServletRequest request, HttpSession session, ReVehicleSupplier supplier) {
		// 从session中获取数据
		List<DidVo> listSnapshot = (List<DidVo>) session.getAttribute("listSnapshot");
		JsonReturn jsonReturn = dataManageService.insertSnapshotInfo(listSnapshot, supplier);
		// 清空session
		if (jsonReturn.getCode() == 200 || jsonReturn.getCode() == 501) {
			request.getSession().removeAttribute("listSnapshot");
		}
		return gson.toJson(jsonReturn);
	}

	/**
	 * 修改快照的配置信息
	 * 
	 * @param didInfo
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/updateDidTypeInfoById", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改快照的配置信息", httpMethod = "PATCH", response = Gson.class)
	public Object updateDidTypeInfoById(DidType didInfo, HttpSession session) {
		List<StateDescription> stateInfo = new ArrayList<StateDescription>();
		if (didInfo.getSignalTypeId() == 2) {
			// 获取session中的状态描述信息
			stateInfo = (List<StateDescription>) session.getAttribute("stateInfoThree");
			for (int i = 0; i < stateInfo.size(); i++) {
				stateInfo.get(i).setDidTypeId(didInfo.getDidTypeId());
			}
		}
		JsonReturn jsonReturn = dataManageService.updateDidTypeInfoById(didInfo, stateInfo);
		// 清空session中的状态描述信息
		if (jsonReturn.getText().equals("success")) {
			session.removeAttribute("stateInfoThree");
		}
		return gson.toJson(jsonReturn);
	}

	/**
	 * 查询状态描述信息(快照)
	 * 
	 * @param didTypeId
	 *            DID类型ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findStateDescribeInfoById", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询状态描述信息(快照)", httpMethod = "POST", response = Gson.class)
	public Object findStateDescribeInfoById(Integer didTypeId, HttpServletRequest request) {
		List<StateDescription> state = dataManageService.selectStateDescribeInfoById(didTypeId);
		// 将数据保存到session中
		request.getSession().setAttribute("stateInfoThree", state);
		/*
		 * if(state.size()==0) { request.getSession().removeAttribute("stateInfoThree");
		 * }
		 */
		return gson.toJson(200);
	}

	/**
	 * 查询session中状态描述信息(快照信息)
	 * 
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/findStateDescriptionSessionTwo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询session中状态描述信息(快照信息)", httpMethod = "POST", response = JSONSerializer.class)
	public Object findStateDescriptionSessionTwo(HttpSession session) {
		List<StateDescription> listState = (List<StateDescription>) session.getAttribute("stateInfoThree");
		if (listState == null) {
			listState = new ArrayList<StateDescription>();
		}
		LayuiJSON<StateDescription> stateInfos = new LayuiJSON<StateDescription>(listState.size(), listState);
		return JSONSerializer.toJSON(stateInfos);
	}

	/**
	 * 添加状态描述信息到session中(快照信息)
	 * 
	 * @param stateInfo
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/addRawValueInfoToSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "添加状态描述信息到session中(快照信息)", httpMethod = "POST", response = Gson.class)
	public Object addRawValueInfoToSession(StateDescription stateInfo, HttpSession session) {
		boolean state = false;
		List<StateDescription> listState = (List<StateDescription>) session.getAttribute("stateInfoThree");
		if (listState == null) {
			listState = new ArrayList<StateDescription>();
			stateInfo.setStateDescriptionId(1);
		} else {
			int num = listState.size();
			int id = 1;
			if (num > 0) {
				id = listState.get(num - 1).getStateDescriptionId() + 1;
			}
			stateInfo.setStateDescriptionId(id);
		}
		// 转换为大写
		String number = "0x0" + stateInfo.getHexs().toUpperCase();
		stateInfo.setHexs(number);

		state = listState.add(stateInfo);
		session.setAttribute("stateInfoThree", listState);
		if (state) {
			return gson.toJson(200);
		} else {
			return gson.toJson(404);
		}
	}

	/**
	 * 删除session中的状态描述信息
	 * 
	 * @param stateDescriptionId
	 *            状态描述ID
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/removeRawValueInfoInSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除session中的状态描述信息", httpMethod = "DELETE", response = Gson.class)
	public Object removeRawValueInfoInSession(int stateDescriptionId, HttpSession session) {
		boolean state = false;
		StateDescription stateInfo = null;
		List<StateDescription> listState = (List<StateDescription>) session.getAttribute("stateInfoThree");
		if (listState != null) {
			for (int i = 0; i < listState.size(); i++) {
				if (listState.get(i).getStateDescriptionId() == stateDescriptionId) {
					stateInfo = listState.remove(i);
				}
			}
		}
		if (stateInfo != null) {
			state = true;
		}
		session.setAttribute("stateInfoThree", listState);
		if (state) {
			return gson.toJson(200);
		} else {
			return gson.toJson(404);
		}
	}

	/**
	 * 批量删除节点明细信息
	 * 
	 * @param testNodeId
	 *            合并的节点id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteNodeDetailInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "批量删除节点明细信息", httpMethod = "DELETE", response = Gson.class)
	public Object deleteNodeDetailInfo(String testNodeId) {
		result = dataManageService.deleteNodeDetailInfo(testNodeId);
		return gson.toJson(result);
	}

	/**
	 * 修改快照信息
	 * 
	 * @param supplier
	 * @param did
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateSnapshotInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改快照信息", httpMethod = "PATCH", response = Gson.class)
	public Object updateSnapshotInfo(ReVehicleSupplier supplier, Did did) {
		result = dataManageService.updateSnapshotInfo(supplier, did);
		return gson.toJson(result);
	}

	/**
	 * 删除快照信息
	 * 
	 * @param didTypeIds
	 *            主键ID(多个)
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteSnapshotInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除快照信息", httpMethod = "DELETE", response = Gson.class)
	public Object deleteSnapshotInfo(String didTypeIds) {
		result = dataManageService.deleteSnapshotInfo(didTypeIds);
		return gson.toJson(result);
	}

	/**
	 * 从session查询具体数据信息
	 * 
	 * @param page
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findSmallFlowInfoSession")
	@ApiOperation(value = "从session查询具体数据信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findSmallFlowInfoSession(Page page, HttpSession session) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<DidVo> listSmallFlow = (List<DidVo>) session.getAttribute("listSmallFlow");
		if (listSmallFlow == null) {
			listSmallFlow = new ArrayList<DidVo>();
		}
		List<DidVo> smallFolwInfo = new ArrayList<DidVo>();
		// 进行分页处理
		if (listSmallFlow.size() > 0) {
			int start = 0;
			for (int i = 0; i < page.getLimit(); i++) {
				start = page.getStartIndex() + i;
				if (start < listSmallFlow.size()) {
					smallFolwInfo.add(listSmallFlow.get(start));
				} else {
					break;
				}
			}
		} else {
			smallFolwInfo = listSmallFlow;
		}

		LayuiJSON<DidVo> info = new LayuiJSON<DidVo>(listSmallFlow.size(), smallFolwInfo);
		return JSONSerializer.toJSON(info);
	}

	/**
	 * 新增小流程步骤信息到session中
	 * 
	 * @param snapshot
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/insertFlowDetailInfoToSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增小流程步骤信息到session中", httpMethod = "POST", response = Gson.class)
	public Object insertFlowDetailInfoToSession(DidVo smallFlow, HttpSession session) {
		boolean state = false;
		List<DidVo> listSmallFlow = (List<DidVo>) session.getAttribute("listSmallFlow");
		if (listSmallFlow == null) {
			listSmallFlow = new ArrayList<DidVo>();
			smallFlow.setDidId(1);
		} else {
			int num = listSmallFlow.size();
			int id = 1;
			if (num > 0) {
				id = listSmallFlow.get(num - 1).getDidId() + 1;
			}
			smallFlow.setDidId(id);

		}

		state = listSmallFlow.add(smallFlow);
		session.setAttribute("listSmallFlow", listSmallFlow);
		if (state) {
			return gson.toJson(200);
		} else {
			return gson.toJson(404);
		}
	}

	/**
	 * 清空session中小流程步骤信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/clearFlowDetailInfoSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "清空session中小流程步骤信息", httpMethod = "DELETE", response = Gson.class)
	public Object clearFlowDetailInfoSession(HttpServletRequest request) {
		request.getSession().removeAttribute("listSmallFlow");
		return gson.toJson(true);
	}

	/**
	 * 修改session中小流程步骤信息
	 * 
	 * @param smallFlow
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/updateFlowDetailInfoToSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改session中小流程步骤信息", httpMethod = "POST", response = Gson.class)
	public Object updateFlowDetailInfoToSession(DidVo smallFlow, HttpSession session) {
		boolean state = false;
		List<DidVo> listSmallFlow = (List<DidVo>) session.getAttribute("listSmallFlow");
		if (listSmallFlow != null) {
			// 查询是否有重复的快照名称
			for (int i = 0; i < listSmallFlow.size(); i++) {
				if (listSmallFlow.get(i).getDidId().equals(smallFlow.getDidId())) {
					listSmallFlow.set(i, smallFlow);
					state = true;
				}
			}
		}

		session.setAttribute("listSmallFlow", listSmallFlow);
		if (state) {
			return gson.toJson(200);
		} else {
			return gson.toJson(404);
		}
	}

	/**
	 * 查询与DID关联的模块信息
	 * 
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findModuleDataList", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询与DID关联的模块信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findModuleDataList(Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<Module> ecu = dataManageService.selectModuleDataList(page.getStartIndex(), page.getLimit());
		int totalRows = dataManageService.selectModuleDataListRows();
		LayuiJSON<Module> ecuInfo = new LayuiJSON<Module>(totalRows, ecu);
		return JSONSerializer.toJSON(ecuInfo);
	}

	/**
	 * 查询小流程信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param flowId
	 *            流程ID
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findFlowInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询小流程信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findFlowInfo(Integer vehicleId, Integer flowId, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<FlowVo> flow = dataManageService.selectFlowInfo(vehicleId, flowId, page.getStartIndex(), page.getLimit());
		int totalRows = dataManageService.selectFlowInfoRows(vehicleId, flowId);
		LayuiJSON<FlowVo> flowInfo = new LayuiJSON<FlowVo>(totalRows, flow);
		return JSONSerializer.toJSON(flowInfo);
	}

	/**
	 * 查询小流程信息(绑定下拉框)
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findFlowInfoToDownBox", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询小流程信息(绑定下拉框)", notes = "用于绑定下拉框", httpMethod = "POST", response = Gson.class)
	public Object findFlowInfoToDownBox() {
		result = dataManageService.selectFlowInfoToDownBox();
		return gson.toJson(result);
	}

	/**
	 * 查询供应商信息(绑定下拉框)
	 * 
	 * @param mouduleId
	 *            模块ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findSupplierById", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询供应商信息(绑定下拉框)", notes = "用于绑定下拉框", httpMethod = "POST", response = Gson.class)
	public Object findSupplierById(Integer moduleId, Integer vehicleId) {
		result = dataManageService.selectSupplierById(moduleId, vehicleId);
		return gson.toJson(result);
	}

	/**
	 * 查询响应数据信息(绑定下拉框)
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findAllResponseOrder", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询响应数据信息(绑定下拉框)", httpMethod = "POST", response = Gson.class)
	public Object findAllResponseOrder() {
		result = dataManageService.selectAllResponseOrder();
		return gson.toJson(result);
	}

	/**
	 * 查询小流程步骤信息
	 * 
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findSmallFlowStepInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询小流程步骤信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findSmallFlowStepInfo(Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<FlowStep> flow = dataManageService.selectSmallFlowStepInfo(page.getStartIndex(), page.getLimit());
		int totalRows = dataManageService.selectSmallFlowStepInfoRows();
		LayuiJSON<FlowStep> flowInfo = new LayuiJSON<FlowStep>(totalRows, flow);
		return JSONSerializer.toJSON(flowInfo);
	}

	/**
	 * 新增、修改小流程步骤信息
	 * 
	 * @param flowStep
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveSmallFlowStepInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增、修改小流程步骤信息", httpMethod = "POST", response = Gson.class)
	public Object saveSmallFlowStepInfo(FlowStep flowStep) {
		result = dataManageService.saveSmallFlowStepInfo(flowStep);
		return gson.toJson(result);
	}

	/**
	 * 删除小流程步骤信息
	 * 
	 * @param flowStepIds
	 *            流程步骤ID(多个)
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteSmallFlowStepInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除小流程步骤信息", httpMethod = "DELETE", response = Gson.class)
	public Object deleteSmallFlowStepInfo(String flowStepIds) {
		result = dataManageService.deleteSmallFlowStepInfo(flowStepIds);
		return gson.toJson(result);
	}

	/**
	 * 查询响应指令信息
	 * 
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findResponseOrderInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询响应指令信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findResponseOrderInfo(Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<ResponseOrder> responseOrder = dataManageService.selectResponseOrderInfo(page.getStartIndex(),
				page.getLimit());
		int totalRows = dataManageService.selectResponseOrderInfoRows();
		LayuiJSON<ResponseOrder> responseOrderInfo = new LayuiJSON<ResponseOrder>(totalRows, responseOrder);
		return JSONSerializer.toJSON(responseOrderInfo);
	}
	
	/**
	 * 新增、修改响应指令信息
	 * @param responseOrder
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveResponseOrderInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增、修改响应指令信息", httpMethod = "POST", response = Gson.class)
	public Object saveResponseOrderInfo(ResponseOrder responseOrder) {
		result = dataManageService.saveResponseOrderInfo(responseOrder);
		return gson.toJson(result);
	}
	
	/**
	 * 删除响应指令信息
	 * @param responseOrdernfoIds 响应指令ID(多个)
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteResponseOrdernfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除响应指令信息", httpMethod = "DELETE", response = Gson.class)
	public Object deleteResponseOrdernfo(String responseOrdernfoIds) {
		result = dataManageService.deleteResponseOrderInfo(responseOrdernfoIds);
		return gson.toJson(result);
	}
}
