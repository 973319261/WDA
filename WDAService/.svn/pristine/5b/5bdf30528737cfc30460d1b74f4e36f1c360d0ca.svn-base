package com.gx.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gx.po.ReVehicleSupplier;
import com.gx.service.IFaultCodeService;
import com.gx.util.ExcelResponseUtil;
import com.gx.util.Tools;
import com.gx.vo.FaultCodeVo;
import com.gx.vo.JsonReturn;
import com.gx.vo.LayuiJSON;
import com.gx.vo.Page;
import com.gx.vo.SupplierVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/faultCode")
@Api(value = "故障码接口", description = "故障码管理相关api")
public class FaultCodeController {

	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	private Object result;

	@Autowired
	IFaultCodeService faultCodeService;

	/**
	 * 查询车型信息(绑定下拉框)
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findCarTypeInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询车型信息", notes = "用于绑定下拉框",httpMethod = "POST", response = Gson.class)
	public Object findCarTypeInfo() {
		result = faultCodeService.selectCarTypeInfo();
		return gson.toJson(result);
	}

	/**
	 * 查询故障码管理信息
	 * 
	 * @param carTypeId
	 *            车型ID
	 * @param moudleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findFaultCodeInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询故障码管理信息", httpMethod = "POST", response = JSONSerializer.class)
	public Object findFaultCodeInfo(int carTypeId, int moudleId, int supplierId, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<SupplierVo> faultCode = faultCodeService.selectFaultCodeInfo(carTypeId, moudleId, supplierId,
				page.getStartIndex(), page.getLimit());
		int totalRows = faultCodeService.selectFaultCodeInfoRows(carTypeId, moudleId, supplierId);
		LayuiJSON<SupplierVo> faultCodeInfo = new LayuiJSON<SupplierVo>(totalRows, faultCode);
		return JSONSerializer.toJSON(faultCodeInfo);
	}

	/**
	 * 查询故障码相关车型模块供应商
	 * 
	 * @param rev
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findSupFault")
	@ApiOperation(value = "查询故障码相关车型模块供应商", httpMethod = "POST", response = JSONSerializer.class)
	public Object findSupFault(FaultCodeVo fcv, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		fcv.setStartIndex(page.getStartIndex());
		fcv.setPageSize(page.getLimit());
		List<FaultCodeVo> fault = faultCodeService.selectSupFault(fcv);
		int totalRows = faultCodeService.selectSupFaultCount(fcv);
		LayuiJSON<FaultCodeVo> faultInfo = new LayuiJSON<FaultCodeVo>(totalRows, fault);
		return JSONSerializer.toJSON(faultInfo);
	}

	/**
	 * 查询新增故障码保存在session中
	 * 
	 * @param rev
	 * @param page
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findFaultCodeSession")
	@ApiOperation(value = "查询新增的故障码信息", notes="从session中获取",httpMethod = "POST", response = JSONSerializer.class)
	public Object findFaultCodeSession(FaultCodeVo fcv, Page page, HttpSession session) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		//fcv.setStartIndex(page.getStartIndex());
		//fcv.setPageSize(page.getLimit());
		List<FaultCodeVo> listFault = (List<FaultCodeVo>) session.getAttribute("listFault");
		if (listFault == null) {
			listFault = new ArrayList<FaultCodeVo>();
		}
		List<FaultCodeVo> faultCodeInfo = new ArrayList<FaultCodeVo>();
		if (listFault.size() > 0) {
			int start = 0;
			for (int i = 0; i < page.getLimit(); i++) {
				start = page.getStartIndex() + i;
				if (start < listFault.size()) {
					faultCodeInfo.add(listFault.get(start));
				} else {
					break;
				}
			}
		} else {
			faultCodeInfo = listFault;
		}

		LayuiJSON<FaultCodeVo> faultInfo = new LayuiJSON<FaultCodeVo>(listFault.size(), faultCodeInfo);
		return JSONSerializer.toJSON(faultInfo);
	}

	/**
	 * 新增单条故障码到Session
	 * 
	 * @param fcv
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/insertFaultCode")
	@ApiOperation(value = "新增单条故障码到Session", httpMethod = "POST", response = Gson.class)
	public Object insertFaultCode(FaultCodeVo fcv, HttpSession session) {
		boolean state = false;
		List<FaultCodeVo> listFault = (List<FaultCodeVo>) session.getAttribute("listFault");
		if (listFault == null) {
			listFault = new ArrayList<FaultCodeVo>();
			fcv.setFaultCodeId(0);
		} else {
			int num=listFault.size();
			int id=0;
			if(num>0) {
				id=listFault.get(num-1).getFaultCodeId()+1;
			}
			fcv.setFaultCodeId(id);
		}
		for (int i = 0; i < listFault.size(); i++) {
			if (fcv.getDtc().equalsIgnoreCase(listFault.get(i).getDtc())) {
				return gson.toJson(500);
			}
		}

		state = listFault.add(fcv);
		session.setAttribute("listFault", listFault);
		if (state) {
			return gson.toJson(200);
		} else {
			return gson.toJson(404);
		}
	}

	/**
	 * 修改Session中的故障码信息
	 * 
	 * @param fcv
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/updateSessionFaultCode")
	@ApiOperation(value = "修改Session中的故障码信息", httpMethod = "PATCH", response = Gson.class)
	public Object updateSessionFaultCode(FaultCodeVo fcv, HttpSession session) {
		boolean state = false;
		List<FaultCodeVo> listFault = (List<FaultCodeVo>) session.getAttribute("listFault");
		FaultCodeVo faultVo=null;
		if (listFault != null) {
			for (int i = 0; i < listFault.size(); i++) {
				if (fcv.getDtc().equalsIgnoreCase(listFault.get(i).getDtc())
						&& fcv.getFaultCodeId() != listFault.get(i).getFaultCodeId()) {
					return gson.toJson(500);
				}
				if(listFault.get(i).getFaultCodeId().equals(fcv.getFaultCodeId())) {
					faultVo = listFault.remove(i);
				}
			}
			if (faultVo != null) {
				state = true;
			}

			listFault.add(fcv.getFaultCodeId(), fcv);
			session.setAttribute("listFault", listFault);
		}
		if (state) {
			return gson.toJson(200);
		} else {
			return gson.toJson(404);
		}
	}

	/**
	 * 下载模板
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/downloadTemplate", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "下载故障码信息模板", httpMethod = "GET")
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");

		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，创建table工作薄
		HSSFSheet sheet = wb.createSheet("table");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow(0);
		// 设置标题样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setWrapText(true);
		style.setAlignment(HorizontalAlignment.CENTER);

		// 设置字体样式
		HSSFFont font = wb.createFont();
		font.setFontName("仿宋_GB2312");
		font.setFontHeightInPoints((short) 12);
		// 给标题的每一个单元格赋值
		row.createCell(0).setCellValue("故障码\r\nDTC");
		row.createCell(1).setCellValue("故障码\r\n(hex)");
		row.createCell(2).setCellValue("故障码英文描述\r\nEnglish description");
		row.createCell(3).setCellValue("故障码中文描述\r\nChinese description");
		row.createCell(4).setCellValue("故障码运行条件\r\nDTC operating conditions");
		row.createCell(5).setCellValue("故障码设置条件\r\nDTC setting conditions");
		row.createCell(6).setCellValue("故障码设置时发生的操作\r\nStrategy after DTC appears");
		row.createCell(7).setCellValue("故障恢复条件\r\nDTC resume conditions");
		row.createCell(8).setCellValue("激近故障灯原则\r\nActivate MIL regulations");
		row.createCell(9).setCellValue("熄灭故障灯原则\r\nMil Off regulations");
		row.createCell(10).setCellValue("清除故障码条件\r\nClear fault information conditions");
		// row.setHeightInPoints(20);// 设置行高
		for (int i = 0; i < 11; i++) {
			row.getCell(i).setCellStyle(style);
			// style.setFont(font);
		}

		// 设置列宽
		sheet.setColumnWidth(0, 10 * 256);
		sheet.setColumnWidth(1, 10 * 256);
		sheet.setColumnWidth(2, 36 * 256);
		sheet.setColumnWidth(3, 36 * 256);
		sheet.setColumnWidth(4, 36 * 256);
		sheet.setColumnWidth(5, 36 * 256);
		sheet.setColumnWidth(6, 36 * 256);
		sheet.setColumnWidth(7, 36 * 256);
		sheet.setColumnWidth(8, 36 * 256);
		sheet.setColumnWidth(9, 36 * 256);
		sheet.setColumnWidth(10, 80 * 256);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSSS");
		Date date = new Date();
		String fileName = "故障码信息" + dateFormat.format(date);
		// 输出的文件名+以毫秒为单位返回当前时间
		fileName = new String((fileName + ".xls").getBytes(), "ISO8859-1");// ISO8859-1不能改为UTF-8,否则文件名是乱码
		response.setContentType("application/octet-stream;charset=ISO8859-1");// application应用；octet-stream八进制；charset字符集(请求应用)
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);// Content-Disposition内容配置；attachment附件；(下载完成提示)
		OutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
	}

	/**
	 * 清空故障码session
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/clearFaultCodeSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "清空故障码session", httpMethod = "DELETE", response = Gson.class)
	public Object clearFaultCodeSession(HttpServletRequest request) {
		request.getSession().removeAttribute("listFault");
		request.getSession().removeAttribute("listFaultTwo");
		return gson.toJson(true);
	}

	/**
	 * 删除session中的故障码信息
	 * 
	 * @param session
	 * @param faultCodeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteFaultCodeSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除session中的故障码信息", httpMethod = "DELETE", response = Gson.class)
	public Object deleteFaultCodeSession(HttpSession session, int faultCodeId) {
		boolean state = false;
		List<FaultCodeVo> listFault = (List<FaultCodeVo>) session.getAttribute("listFault");
		if (faultCodeId >= 0) {
			FaultCodeVo faultVo = null;
			for (int i = 0; i < listFault.size(); i++) {
				if (listFault.get(i).getFaultCodeId() == faultCodeId) {
					faultVo = listFault.remove(i);
					break;
				}
			}
			if (faultVo != null) {
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
	 * 新增故障码信息(从session中获取数据)
	 * 
	 * @param session
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/insertFaultCodeInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增故障码信息", notes="从session中获取数据",httpMethod = "POST", response = Gson.class)
	public Object insertFaultCodeInfo(HttpSession session, ReVehicleSupplier supplier, Integer typeId) {
		List<FaultCodeVo> listFault = new ArrayList<FaultCodeVo>();
		if (typeId == 1) {
			listFault = (List<FaultCodeVo>) session.getAttribute("listFault");
		} else {
			listFault = (List<FaultCodeVo>) session.getAttribute("listFaultTwo");
		}
		result = faultCodeService.insertFaultCodeInfo(listFault, supplier);
		return gson.toJson(result);
	}

	/**
	 * 导入Excel
	 * 
	 * @param request
	 * @param uploadFile
	 *            上传的文件
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "resource", "unused", "unchecked" })
	@ResponseBody
	@RequestMapping(value = "/importExcelFileTwo", method = RequestMethod.POST)
	@ApiOperation(value = "导入Excel", notes="导入故障码信息",httpMethod = "POST", response = Gson.class)
	public Object importExcelFileTwo(HttpServletRequest request, HttpSession session,
			@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile, Integer typeId)
			throws IOException {
		JsonReturn jsonReturn = new JsonReturn();

		String[] dtcInfo = { "B0", "B1", "B2", "B3", "C0", "C1", "C2", "C3", "P0", "P1", "P2", "P3", "U0", "U1", "U2",
				"U3" };

		// poi--exl解析
		InputStream is = uploadFile.getInputStream();
		// 获取文件后缀名
		String name = uploadFile.getOriginalFilename();
		String suffixName = name.substring(name.lastIndexOf("."));// 获取后缀名
		Workbook work = null;
		if (".xls".equals(suffixName)) {
			work = new HSSFWorkbook(is); // 2003-
		} else {
			work = new XSSFWorkbook(is); // 2007+
		}
		int successRows = 0;
		if (work != null) {
			Sheet sheet = null;
			Row row = null;

			List<FaultCodeVo> listFault = null;
			if (typeId == 1) {
				listFault = (List<FaultCodeVo>) session.getAttribute("listFault");
			} else {
				listFault = (List<FaultCodeVo>) session.getAttribute("listFaultTwo");
			}
			if (listFault == null) {
				listFault = new ArrayList<FaultCodeVo>();
			}

			// 判断工作簿中的工作表（Sheet）的个数
			if (work.getNumberOfSheets() > 0) {
				// 获取第一个工作表
				sheet = work.getSheetAt(0);
				if (sheet == null) {
					jsonReturn.setCode(500);
					jsonReturn.setText("这是一个空的工作簿");
					return jsonReturn;
				}

				// 遍历当前sheet中的所有行
				int num = sheet.getRow(0).getLastCellNum();
				if (num == 11 && sheet.getLastRowNum() > 0) {
					for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
						row = sheet.getRow(j);
						if (row == null || row.getFirstCellNum() == j) {
							continue;
						}

						// 遍历所有的列
						Cell cell0 = row.getCell(0);// 故障码DTC
						Cell cell1 = row.getCell(1);// 故障码Hex
						Cell cell2 = row.getCell(2);// 故障码英文描述
						Cell cell3 = row.getCell(3);// 故障码中文描述
						Cell cell4 = row.getCell(4);// 故障码运行条件
						Cell cell5 = row.getCell(5);// 故障码设置条件
						Cell cell6 = row.getCell(6);// 故障码设置时发生的操作
						Cell cell7 = row.getCell(7);// 故障恢复条件
						Cell cell8 = row.getCell(8);// 激近故障灯原则
						Cell cell9 = row.getCell(9);// 熄灭故障灯原则
						Cell cell10 = row.getCell(10);// 清除故障码条件

						FaultCodeVo fault = new FaultCodeVo();

						// 判断列不能为空
						if (cell2 == null || cell3 == null || cell4 == null || cell5 == null || cell6 == null
								|| cell7 == null || cell8 == null || cell9 == null || cell10 == null) {
							continue;
						}
						// 判断第一和第二列不能同时为空或者故障码输入不正确（不符合转换规则）
						if (cell0 == null && cell1 == null) {
							continue;
						}
						String strFault = "";
						if (cell0 == null) {
							strFault = checkDTC("", cell1.toString());
						} else if (cell1 == null) {
							strFault = checkDTC(cell0.toString(), "");
						} else {
							strFault = checkDTC(cell0.toString(), cell1.toString());
						}

						if (strFault.equals("")) {
							continue;
						}

						String[] splitdh = strFault.split("&");
						int count = 0;
						// 判断故障码是否重复
						if (listFault != null) {
							for (int k = 0; k < listFault.size(); k++) {
								if (splitdh[0].equalsIgnoreCase(listFault.get(k).getDtc())) {
									count = 1;
									break;
								}
							}
							if (count > 0) {
								continue;
							}
						}

						// 判断故障码是否符合规范
						int exits = 0;
						if (splitdh[0].length() >= 2) {
							for (int m = 0; m < dtcInfo.length; m++) {
								String str = splitdh[0].toString().substring(0, 2);
								if (str.equalsIgnoreCase(dtcInfo[m])) {
									exits = 1;
									break;
								}
							}
						}
						if (exits == 0) {
							continue;
						}

						successRows++;
						if (listFault.size() > 0) {
							fault.setFaultCodeId(listFault.size());
						} else {
							fault.setFaultCodeId(0);
						}

						// 赋值
						fault.setDtc(splitdh[0]);
						fault.setHexDtc(splitdh[1]);
						fault.setChineseDescription(cell3.toString());
						fault.setEnglishDescription(cell2.toString());
						fault.setOperatingConditions(cell4.toString());
						fault.setSettingConditions(cell5.toString());
						fault.setSettingAfterConditions(cell6.toString());
						fault.setRestoreConditions(cell7.toString());
						fault.setActivateMilRegulations(cell8.toString());
						fault.setMilOffRegulations(cell9.toString());
						fault.setClearConditions(cell10.toString());
						if (typeId == 2) {
							fault.setTypeId(2);
						}
						listFault.add(fault);
					}
					if (successRows == sheet.getLastRowNum()) {
						jsonReturn.setCode(200);
						jsonReturn.setText("导入成功");
					} else if (successRows >= 0 && successRows < sheet.getLastRowNum()) {
						jsonReturn.setCode(201);
						int failRows = sheet.getLastRowNum() - successRows;
						jsonReturn.setText(
								successRows + "条数据导入成功，" + failRows + "条数数据导入失败，失败原因：可能存在有相同的数据，故障码填写不符合规范，或者存在有空的列");
					}
					// 设置session
					if (typeId == 1) {
						session.setAttribute("listFault", listFault);
					} else {
						session.setAttribute("listFaultTwo", listFault);
					}
				} else {
					jsonReturn.setCode(500);
					jsonReturn.setText("模块格式不正确或者物理行数为0");
				}
			}
		} else {
			jsonReturn.setCode(500);
			jsonReturn.setText("创建Excel工作薄为空！");
		}
		is.close();
		return gson.toJson(jsonReturn);
	}

	/**
	 * 检查故障码
	 * 
	 * @param DTC
	 * @param Hex
	 * @return
	 */
	public String checkDTC(String DTC, String Hex) {
		String[] checkDtc = { "B0", "B1", "B2", "B3", "C0", "C1", "C2", "C3", "P0", "P1", "P2", "P3", "U0", "U1", "U2",
				"U3" };
		String[] checkHex = { "8", "9", "A", "B", "4", "5", "6", "7", "0", "1", "2", "3", "C", "D", "E", "F" };
		int indexofDtc = 0, indexofHex = 0;
		if (Tools.isNotNull(DTC)) {
			String strDtc = DTC.substring(0, 2).toString().toUpperCase();
			for (int i = 0; i < checkDtc.length; i++) {
				indexofHex = checkDtc[i].indexOf(strDtc);
				if (indexofHex != -1) {// 循环查找字符串数组中的每个字符串中是否包含所有查找的内容
					indexofHex = i;// 查找到了就不在继续查询
					break;
				}
			}
			if (indexofHex > -1) {
				if (DTC.length() >= 2) {
					String strDtcEnd = "";
					if (DTC.length() > 2) {
						strDtcEnd = DTC.substring(2).toString().toUpperCase();
					}
					Hex = checkHex[indexofHex] + strDtcEnd;
					return DTC + "&" + Hex;
				}
			}
		} else if (Tools.isNotNull(Hex)) {
			String strHex = Hex.substring(0, 1).toString().toUpperCase();
			for (int i = 0; i < checkHex.length; i++) {
				indexofDtc = checkHex[i].indexOf(strHex);
				if (indexofDtc != -1) {// 循环查找字符串数组中的每个字符串中是否包含所有查找的内容
					indexofDtc = i;// 查找到了就不在继续查询
					break;
				}
			}
			if (indexofDtc > -1) {
				if (Hex.length() >= 1) {
					String strHexEnd = Hex.substring(1).toString().toUpperCase();
					if (strHexEnd.indexOf(".0") > -1) {
						strHexEnd = strHexEnd.substring(0, strHexEnd.indexOf(".0"));
					}
					if (Hex.indexOf(".0") > -1) {
						Hex = Hex.substring(0, Hex.indexOf(".0"));
					}
					DTC = checkDtc[indexofDtc] + strHexEnd;
					return DTC + "&" + Hex;
				}
			}
		}
		return "";
	}

	/**
	 * 导出Excel
	 * 
	 * @param response
	 * @param session
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/exportToExcel")
	@ApiOperation(value = "导出Excel", notes="导出故障码信息",httpMethod = "POST")
	public void exportToExcel(Integer relevanceId, HttpServletResponse response, HttpSession session) {
		Map<String, Object> filedNames = new LinkedHashMap<String, Object>();
		String titleName = "";
		Class<?> objClass = null;
		Object obj = null;
		try {
			List<FaultCodeVo> listFault = null;
			if (relevanceId == 0) {
				listFault = (List<FaultCodeVo>) session.getAttribute("listFault");
			} else {
				listFault = faultCodeService.selectFaultCodeById(relevanceId);
			}
			if (listFault != null) {
				// 声明一个list集合接收Dao层查询所返回来的值
				obj = listFault;
				// excel标题和字段
				filedNames.put("dtc", "故障码\r\nDTC");
				filedNames.put("hexDtc", "故障码\r\n(hex)");
				filedNames.put("englishDescription", "故障码英文描述\r\nEnglish description");
				filedNames.put("chineseDescription", "故障码中文描述\r\nChinese description");
				filedNames.put("operatingConditions", "故障码运行条件\r\nDTC operating conditions");
				filedNames.put("settingConditions", "故障码设置条件\r\nDTC setting conditions");
				filedNames.put("settingAfterConditions", "故障码设置时发生的操作\r\nStrategy after DTC appears");
				filedNames.put("restoreConditions", "故障恢复条件\r\nDTC resume conditions");
				filedNames.put("activateMilRegulations", "激近故障灯原则\r\nActivate MIL regulations");
				filedNames.put("milOffRegulations", "熄灭故障灯原则\r\nMil Off regulations");
				filedNames.put("clearConditions", "清除故障码条件\r\nClear fault information conditions");
				titleName = "故障码信息表";
				objClass = FaultCodeVo.class;
				// 调用ExcelResponseUtil
				ExcelResponseUtil.exportToExcel(response, obj, filedNames, titleName, objClass);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查询故障码详情信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moduleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @param dtc
	 *            故障码
	 * @param strName
	 *            排序字段
	 * @param sortType
	 *            倒叙、升序
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findfaultCodeDetailInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询故障码详情信息(1)", notes="新增故障码部分",httpMethod = "POST", response = JSONSerializer.class)
	public Object findfaultCodeDetailInfo(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId, String dtc, String strName, String sortType, Page page, HttpSession session) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<FaultCodeVo> faultCode = faultCodeService.selectfaultCodeDetailInfo(vehicleId, configurationLevelId,
				moduleId, supplierId, dtc, strName, sortType, page.getStartIndex(), page.getLimit());
		int totalRows = faultCodeService.selectfaultCodeDetailInfoRows(vehicleId, configurationLevelId, moduleId,
				supplierId, dtc);
		LayuiJSON<FaultCodeVo> faultCodeInfo = new LayuiJSON<FaultCodeVo>(totalRows, faultCode);
		return JSONSerializer.toJSON(faultCodeInfo);
	}

	/**
	 * 查询故障码详情信息
	 *@param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moduleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @param dtc
	 *            故障码
	 * @param strName
	 *            排序字段
	 * @param sortType
	 *            倒叙、升序
	 * @param page
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findfaultCodeDetailInfoTwo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询故障码详情信息(2)", notes="修改故障码部分",httpMethod = "POST", response = JSONSerializer.class)
	public Object findfaultCodeDetailInfoTwo(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId, String dtc, String strName, String sortType, Page page, HttpSession session) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<FaultCodeVo> faultCode = faultCodeService.selectfaultCodeDetailInfo(vehicleId, configurationLevelId,
				moduleId, supplierId, dtc, strName, sortType, 0, 0);
		int totalRows = faultCodeService.selectfaultCodeDetailInfoRows(vehicleId, configurationLevelId, moduleId,
				supplierId, dtc);
		List<FaultCodeVo> fault = new ArrayList<FaultCodeVo>();
		List<FaultCodeVo> listFault = (List<FaultCodeVo>) session.getAttribute("listFaultTwo");
		if (listFault != null) {
			fault.addAll(listFault);
			totalRows = totalRows + listFault.size();
		}
		fault.addAll(faultCode);
		List<FaultCodeVo> faults = new ArrayList<FaultCodeVo>();
		if (fault != null) {
			int start = 0;
			for (int i = 0; i < page.getLimit(); i++) {
				start = page.getStartIndex() + i;
				if (start < fault.size()) {
					faults.add(fault.get(start));
				} else {
					break;
				}
			}
		}
		LayuiJSON<FaultCodeVo> faultCodeInfo = new LayuiJSON<FaultCodeVo>(totalRows, faults);
		return JSONSerializer.toJSON(faultCodeInfo);
	}

	/**
	 * 新增故障码信息
	 * 
	 * @param faultCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addFaultCodeInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增故障码信息", httpMethod = "POST", response = Gson.class)
	public Object addFaultCodeInfo(FaultCodeVo faultCode) {
		result = faultCodeService.addFaultCodeInfo(faultCode);
		return gson.toJson(result);
	}

	/**
	 * 修改故障码信息
	 * 
	 * @param faultCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateFaultCodeInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改故障码信息", httpMethod = "PATCH", response = Gson.class)
	public Object updateFaultCodeInfo(FaultCodeVo faultCode, HttpSession session) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("数据异常");
		if (faultCode.getTypeId() == 2) {
			List<FaultCodeVo> listFault = (List<FaultCodeVo>) session.getAttribute("listFaultTwo");
			if (listFault != null) {
				int index = faultCode.getFaultCodeId();
				FaultCodeVo faultVo = listFault.remove(index);
				if (faultVo != null) {
					jsonReturn.setText("success");
				}
				listFault.add(faultCode.getFaultCodeId(), faultCode);
				session.setAttribute("listFaultTwo", listFault);
				result = jsonReturn;
			}
		} else {
			result = faultCodeService.updateFaultCodeInfo(faultCode);
		}
		return gson.toJson(result);
	}

	/**
	 * 删除故障码信息
	 * 
	 * @param reId
	 *            关联ID
	 * @param faultCodeId
	 *            故障码ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteFaultCodeInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除故障码信息(1)", notes="修改部分，查看故障码详情页面",httpMethod = "DELETE", response = Gson.class)
	public Object deleteFaultCodeInfo(Integer reId, Integer faultCodeId, Integer typeId, HttpSession session) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("数据异常");
		if (typeId == 2) {
			List<FaultCodeVo> listFault = (List<FaultCodeVo>) session.getAttribute("listFaultTwo");
			if (listFault != null) {
				for (int i = 0; i < listFault.size(); i++) {
					if (listFault.get(i).getFaultCodeId() == faultCodeId) {
						FaultCodeVo faultVo = listFault.remove(i);
						if (faultVo != null) {
							jsonReturn.setText("success");
						}
						break;
					}
				}
				session.setAttribute("listFaultTwo", listFault);
				result = jsonReturn;
			}
		} else {
			result = faultCodeService.deleteFaultCodeInfo(reId, faultCodeId);
		}
		return gson.toJson(result);
	}

	/**
	 * 删除故障码信息
	 * 
	 * @param relevanceId
	 *            关联ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteFaultCodeInfoTwo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除故障码信息(2)", notes="故障码信息,通过模块供应商关联ID进行删除",httpMethod = "DELETE", response = Gson.class)
	public Object deleteFaultCodeInfoTwo(Integer relevanceId) {
		result = faultCodeService.deleteFaultCodeInfoTwo(relevanceId);
		return gson.toJson(result);
	}
}
