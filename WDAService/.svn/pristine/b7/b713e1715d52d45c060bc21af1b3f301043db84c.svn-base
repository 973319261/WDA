package com.gx.util;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

@SuppressWarnings("all")
public class ExcelResponseUtil {

	/**
	 * 导出Excel
	 * 
	 * @param response
	 * @param list       导出数据集合
	 * @param filedNames excel标题&字段 此参数为map，实例为new LinkedHashMap<String, Object>();
	 * @param titleName  导出文件名
	 * @param objClass   实体类字节码.class
	 */
	public static void exportToExcel(HttpServletResponse response, Object list, Map<String, Object> filedNames,
			String titleName, Class<?> objClass) {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(titleName);
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
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow(0);
		// 第四步，创建单元格，并设置值表头设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setWrapText(true);
		style.setAlignment(HorizontalAlignment.CENTER);
		// 声明列对象
		HSSFCell cell = null;
		Set<String> fileds = filedNames.keySet();
		try {
			int i = 0;
			// 创建标题
			for (String filed : fileds) {
				cell = row.createCell(i);
				cell.setCellValue(filedNames.get(filed).toString());
				cell.setCellStyle(style);
				i++;
			}
			if (list instanceof List) {
				List lists = (List) list;
				for (int j = 0; j < lists.size(); j++) {
					Object obj = objClass.newInstance();// 获取对象实例
					row = sheet.createRow(j + 1);
					
					obj = lists.get(j);// 获取一条数据
					int k = 0;
					for (String filed : fileds) {
						String firstBig = filed.substring(0, 1).toUpperCase() + filed.substring(1);
						// ---获取getter方法，创建内容
						Method getMethod = objClass.getMethod("get" + firstBig);
						if(getMethod.invoke(obj)==null) {
							row.createCell(k).setCellValue("");
						}else {
							row.createCell(k).setCellValue(getMethod.invoke(obj).toString());
						}
						row.getCell(k).setCellStyle(style);
						k++;
					}
				}
			}
			String fileName = "";// 文件名
			// 输出的文件名+以毫秒为单位返回当前时间
			fileName = new String((titleName + System.currentTimeMillis() + ".xls").getBytes(), "ISO8859-1");// ISO8859-1不能改为UTF-8,否则文件名是乱码
			response.setContentType("application/octet-stream;charset=ISO8859-1");// application应用；octet-stream八进制；charset字符集(请求应用)
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);// Content-Disposition内容配置；attachment附件；(下载完成提示)
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
