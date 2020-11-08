package com.gx.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gx.po.Admin;
import com.gx.po.Comment;
import com.gx.po.DidConversion;
import com.gx.po.Eol;
import com.gx.po.Inform;
import com.gx.po.Notice;
import com.gx.po.ReVehicleSupplier;
import com.gx.po.User;
import com.gx.service.IShareService;
import com.gx.util.Tools;
import com.gx.vo.CommentsVo;
import com.gx.vo.DidConversionVo;
import com.gx.vo.FilesVo;
import com.gx.vo.InformVo;
import com.gx.vo.JsonReturn;
import com.gx.vo.LayuiJSON;
import com.gx.vo.NoticeVo;
import com.gx.vo.Page;
import com.gx.vo.Table;
import com.gx.vo.UploadFile;

import cn.hutool.core.io.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/share")
@Api(value = "通知分享管理接口", description = "通知分享管理相关api")
public class ShareController {

	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	private Object result;

	@Autowired
	private IShareService shareService;

	/***************************** ECU产线EOL操作 start ******************************/
	/**
	 * 查询ECU产线EOL操作
	 * 
	 * @param inputValue
	 *            输入值
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findEolOperationInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询ECU产线EOL操作", notes = "查询",httpMethod = "POST", response = JSONSerializer.class)
	public Object findEolOperationInfo(String inputValue, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<Eol> eolOperation = shareService.selectEolOperationInfo(inputValue, page.getStartIndex(), page.getLimit());
		int totalRows = shareService.selectEolOperationInfoRows(inputValue);
		LayuiJSON<Eol> eolInfo = new LayuiJSON<Eol>(totalRows, eolOperation);
		return JSONSerializer.toJSON(eolInfo);
	}

	/**
	 * 新增、修改ECU产线EOL操作
	 * 
	 * @param eol
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveEcuProductionLineInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增、修改ECU产线EOL操作", notes = "新增/修改",httpMethod = "POST", response = Gson.class)
	public Object saveEcuProductionLineInfo(Eol eol) {
		result = shareService.saveEcuProductionLineInfo(eol);
		return gson.toJson(result);
	}

	/***************************** ECU产线EOL操作 over ******************************/

	/**
	 * 删除ECU名称中文全称操作
	 * 
	 * @param ecuId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteEcuInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除ECU名称中文全称操作",httpMethod = "POST", response = Gson.class)
	public Object deleteEcuInfo(int ecuId, int item) {
		result = shareService.deleteEcuInfo(ecuId, item);
		return gson.toJson(result);
	}
	
	/***************************** 通知管理 start ******************************/

	/**
	 * 查询通知管理信息
	 * 
	 * @param informTitle
	 *            通知标题
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findInform", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除ECU名称中文全称操作",httpMethod = "DELETE", response = Gson.class)
	public Object findInform(String informTitle, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<InformVo> informs = shareService.selectInform(informTitle, page.getStartIndex(), page.getLimit());
		int totalRows = shareService.selectInformRows(informTitle);
		LayuiJSON<InformVo> inform = new LayuiJSON<InformVo>(totalRows, informs);
		return JSONSerializer.toJSON(inform);
	}

	/**
	 * 新增通知管理信息
	 * 
	 * @param inform
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertInform", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增通知管理信息",httpMethod = "POST", response = Gson.class)
	public Object insertInform(HttpServletRequest request, Inform inform) {
		// 获取管理员的用户ID
		JsonReturn info = (JsonReturn) request.getSession().getAttribute("admin");
		if (info != null) {
			Admin adminInfo = (Admin) info.getData();
			inform.setAdminId(adminInfo.getAdminId());
		}
		result = shareService.insertInform(inform);
		return gson.toJson(result);
	}

	/**
	 * 删除通知管理信息
	 * 
	 * @param informId
	 *            通知管理ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteInform", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除通知管理信息",httpMethod = "DELETE", response = Gson.class)
	public Object deleteInform(int informId) {
		result = shareService.deleteInform(informId);
		return gson.toJson(result);
	}

	/***************************** 通知管理 over ******************************/

	/**
	 * 查询公告信息
	 * 
	 * @param noticeTheme
	 *            公告主题
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findNoticeInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询公告信息",httpMethod = "POST", response = JSONSerializer.class)
	public Object findNoticeInfo(String noticeTheme, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<NoticeVo> notice = shareService.selectNoticeInfo(noticeTheme, page.getStartIndex(), page.getLimit());
		int totalRows = shareService.selectNoticeInfoRows(noticeTheme);
		LayuiJSON<NoticeVo> noticeInfo = new LayuiJSON<NoticeVo>(totalRows, notice);
		return JSONSerializer.toJSON(noticeInfo);
	}

	/**
	 * 通过公告ID查询文件
	 * 
	 * @param noticeId
	 *            公告ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findFileByNoticeId", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过公告ID查询文件",httpMethod = "POST", response = JSONSerializer.class)
	public Object findFileByNoticeId(Integer noticeId) {
		List<FilesVo> file = shareService.selectFileByNoticeId(noticeId);
		int totalRows = file.size();
		for (int i = 0; i < file.size(); i++) {
			String name = file.get(i).getFileName();
			file.get(i).setFileName(name.substring(name.lastIndexOf("/") + 1));

			String names = name.substring(name.lastIndexOf("/") + 1);
			String nameOne = names.substring(0, names.lastIndexOf(".") - 24) + names.substring(names.lastIndexOf("."));
			file.get(i).setName(nameOne);
		}
		LayuiJSON<FilesVo> fileInfo = new LayuiJSON<FilesVo>(totalRows, file);
		return JSONSerializer.toJSON(fileInfo);
	}

	/**
	 * 公告文件下载
	 * 
	 * @param request
	 * @param response
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/downloadFile", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "公告文件下载",httpMethod = "POST", response = ResponseEntity.class)
	public ResponseEntity<byte[]> downloadFile(HttpServletRequest request, HttpServletResponse response,
			String fileName) throws Exception {
		// 文件下载路径
		String path = request.getSession().getServletContext().getRealPath("") + File.separator + "fileDir\\notice\\";
		// String paths=path.replaceAll("\\\\", "/");

		// 获取文件
		File file = new File(path, fileName);

		// 如果文件不存在跳转到异常页面
		if (!file.exists()) {
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
			return null;
		}
		// 设置header
		HttpHeaders headers = new HttpHeaders();
		// application/octet-stream二进制流数据（最常见的文件下载）
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		// 通知浏览器以attachment（下载方式） 下载文件，文件名称为指定名称
		String name = fileName.substring(0, fileName.lastIndexOf(".") - 24)
				+ fileName.substring(fileName.lastIndexOf("."));
		headers.setContentDispositionFormData("attachment", new String(name.getBytes("utf-8"), "ISO8859-1"));

		// 返回文件下载相关参数
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}

	/**
	 * 文件上传
	 * 
	 * @param request
	 * @param uploadFile
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadAttachment", method = RequestMethod.POST)
	@ApiOperation(value = "文件上传",httpMethod = "GET", response = UploadFile.class)
	public UploadFile uploadAttachment(HttpServletRequest request,
			@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile) {
		int code = -1;
		String src = "";
		// E:\MavenWorkSpace\TestFive\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\WDAService\fileDir\temp
		try {
			String path = request.getSession().getServletContext().getRealPath("") + File.separator + "fileDir\\temp";
			String fileName = uploadFile(request, uploadFile, path);
			code = 0;
			src = fileName;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			code = -1;
			e.printStackTrace();
		}

		List<String> data = new ArrayList<String>();
		data.add(src);
		UploadFile file = UploadFile.getUploadFile(src, code, data);
		return file;
	}

	/**
	 * 文件上传
	 * 
	 * @param file
	 *            文件
	 * @param path
	 *            文件上传路径
	 * @return
	 * @throws IOException
	 */
	public static String uploadFile(HttpServletRequest request, MultipartFile file, String path) throws IOException {
		String fileTypeName = "";
		Integer fileTypeId = 0;
		String name = file.getOriginalFilename();
		String suffixName = name.substring(name.lastIndexOf("."));// 获取后缀名
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSSS");
		String time = dateFormat.format(new Date());
		System.out.println("文件名称:" + name.substring(0, name.lastIndexOf(".")) + time + suffixName);

		String fileName = name.substring(0, name.lastIndexOf(".")) + time + suffixName;
		File tempFile = new File(path, fileName);
		if (!tempFile.getParentFile().exists()) {
			tempFile.getParentFile().mkdir();
		}
		tempFile.createNewFile();
		file.transferTo(tempFile);
		String fileType = suffixName.toLowerCase();
		// |(.rm)|(.3gp)|(.mkv)|(.dvd)|(.mpg)|(.mov)
		if ("(.mp4)|(.avi)|(.flv)|(.rmvb)".contains(fileType)) {
			fileTypeId = 1;
			fileTypeName = "视频";
		} else if ("(.doc)|(.docx)|".contains(fileType)) {
			fileTypeId = 3;
			fileTypeName = "Word";
		} else if ("(.xls)|(.xlsx)".contains(fileType)) {
			fileTypeId = 4;
			fileTypeName = "Excel";
		} else if ("(.ppt)|(.pptx)".contains(fileType)) {
			fileTypeId = 5;
			fileTypeName = "PPT";
		} else if ("(.pdf)".contains(fileType)) {
			fileTypeId = 2;
			fileTypeName = "PDF";
		} else {
			fileTypeName = "其他";
		}

		request.getSession().removeAttribute("fileInfo");
		List<FilesVo> list = new ArrayList<FilesVo>();
		FilesVo files = new FilesVo();
		files.setFileTypeId(fileTypeId);
		files.setFileName(fileName);
		files.setName(name);
		files.setFileTypeName(fileTypeName);
		list.add(files);
		request.getSession().setAttribute("fileInfo", list);
		return tempFile.getName();
	}

	/**
	 * 查询附件列表
	 * 
	 * @return
	 */
	@RequestMapping("/findFileInfo")
	@ApiOperation(value = "查询附件列表信息",notes="从session中获取",httpMethod = "GET", response = Table.class)
	public @ResponseBody Table findFileInfo(HttpServletRequest request) {
		List<FilesVo> list = new ArrayList<FilesVo>();
		if (request.getSession().getAttribute("fileInfo") != null) {
			list = (List<FilesVo>) request.getSession().getAttribute("fileInfo");
		}
		int count = list.size();
		Table table = Table.getTable(count, list);
		return table;
	}

	/**
	 * 新增公告信息
	 * 
	 * @param request
	 * @param notice
	 * @param files
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertNoticeInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增公告信息",httpMethod = "POST", response = Gson.class)
	public Object insertNoticeInfo(HttpServletRequest request, Notice notice, FilesVo files) {
		JsonReturn jsonReturn = new JsonReturn();
		// 获取管理员的用户ID
		JsonReturn info = (JsonReturn) request.getSession().getAttribute("admin");
		if (info != null) {
			Admin adminInfo = (Admin) info.getData();
			Integer adminId = adminInfo.getAdminId();
			String savePath = request.getSession().getServletContext().getRealPath("") + File.separator
					+ "fileDir\\notice";

			notice.setAdminId(adminId);
			notice.setReleaseTime(new Date());
			jsonReturn = shareService.insertNoticeInfo(notice, files);

			// 获取临时文件中的附件信息
			File temp = new File(request.getSession().getServletContext().getRealPath("") + File.separator
					+ "fileDir\\temp\\" + files.getFileName());
			File save = new File(savePath + File.separator + files.getFileName());
			FileUtil.move(temp, save, true);
			// 清除临时文件
			cleanTemp(request);
		} else {
			jsonReturn.setText("请先登录");
		}
		result = jsonReturn;
		return gson.toJson(result);
	}

	/**
	 * 删除附件信息
	 * 
	 * @return
	 */
	@RequestMapping("/deleteRedundant")
	@ApiOperation(value = "删除附件信息",httpMethod = "DELETE", response = Boolean.class)
	public @ResponseBody boolean deleteRedundant(HttpServletRequest request, String upFileFileName) {
		boolean flag = false;
		List<FilesVo> list = new ArrayList<FilesVo>();
		if (request.getSession().getAttribute("fileInfo") != null) {
			list = (List<FilesVo>) request.getSession().getAttribute("fileInfo");
		}
		String path = request.getSession().getServletContext().getRealPath("") + File.separator + "fileDir\\temp\\";
		for (FilesVo file : list) {
			if (file.getFileName().equals(upFileFileName)) {
				flag = list.remove(file);
				FileUtil.del(path + upFileFileName);
				break;
			}
		}
		request.getSession().setAttribute("fileInfo", list);
		return flag;
	}

	/**
	 * 通过公告ID查询公告信息
	 * 
	 * @param noticeId
	 *            公告ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findNoticeInfoById", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过公告ID查询公告信息",httpMethod = "POST", response = Gson.class)
	public Object findNoticeInfoById(HttpServletRequest request, Integer noticeId) {
		NoticeVo notice = shareService.selectNoticeInfoById(noticeId);
		if (notice != null) {
			request.getSession().setAttribute("noticeDetail", notice);
		}
		result = notice;
		return gson.toJson(result);
	}

	/**
	 * 通过公告ID查询公告附件信息
	 * 
	 * @param request
	 * @param noticeId
	 *            公告ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findFileInfoById", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过公告ID查询公告附件信息",httpMethod = "POST", response = Gson.class)
	public Object findFileInfoById(HttpServletRequest request, Integer noticeId) {
		NoticeVo notice = shareService.selectNoticeInfoById(noticeId);
		Boolean flag = false;
		if (notice != null) {
			flag = true;
			String names = "";
			// 截取附件名称
			String name = notice.getFileName();
			notice.setFileName(name.substring(name.lastIndexOf("/") + 1));
			List<FilesVo> list = new ArrayList<FilesVo>();
			FilesVo fileInfo = new FilesVo();
			fileInfo.setFileId(notice.getFileId());
			fileInfo.setFileTypeId(notice.getFileTypeId());
			String fileName = notice.getFileName();
			names = fileName.substring(0, fileName.lastIndexOf(".") - 24)
					+ fileName.substring(fileName.lastIndexOf("."));
			fileInfo.setName(names);
			fileInfo.setFileName(notice.getFileName());
			fileInfo.setFileTypeName(notice.getFileTypeName());
			list.add(fileInfo);
			request.getSession().setAttribute("fileInfo", list);
		}
		result = flag;
		return gson.toJson(result);
	}

	/**
	 * 修改公告信息
	 * 
	 * @param request
	 * @param notice
	 * @param files
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateNoticeInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改公告信息",httpMethod = "PATCH", response = Gson.class)
	public Object updateNoticeInfo(HttpServletRequest request, Notice notice, FilesVo files, Integer id) {
		if (notice != null && files != null) {
			List<FilesVo> file = shareService.selectFileByNoticeId(notice.getNoticeId());
			String name = file.get(0).getFileName();
			file.get(0).setFileName(name.substring(name.lastIndexOf("/") + 1));

			List<FilesVo> list = (List<FilesVo>) request.getSession().getAttribute("fileInfo");
			// 处理附件
			if (list != null) {
				if (id == 0) {
					String oldFilePath = request.getSession().getServletContext().getRealPath("") + File.separator
							+ "fileDir\\notice" + File.separator + file.get(0).getFileName();
					// 删除文件中的附件
					FileUtil.del(oldFilePath);

					// 处理新上传的附件
					File tempFile = new File(request.getSession().getServletContext().getRealPath("") + File.separator
							+ "fileDir\\temp" + File.separator + files.getFileName());
					File saveFile = new File(request.getSession().getServletContext().getRealPath("") + File.separator
							+ "fileDir\\notice" + File.separator + files.getFileName());
					FileUtil.move(tempFile, saveFile, false);
				}
			}
			result = shareService.updateNoticeInfo(notice, files, id);
			cleanTemp(request);
		}
		return gson.toJson(result);
	}

	/**
	 * 删除公告信息
	 * 
	 * @param request
	 * @param noticeId
	 *            公告ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteNoticeInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除公告信息",httpMethod = "DELETE", response = Gson.class)
	public Object deleteNoticeInfo(HttpServletRequest request, Integer noticeId) {
		String filePath = request.getSession().getServletContext().getRealPath("") + File.separator
				+ "fileDir\\notice\\";
		result = shareService.deleteNoticeInfo(noticeId, filePath);
		return gson.toJson(result);
	}

	/**
	 * 清空临时文件夹 和 session
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/cleanTemp")
	@ApiOperation(value = "清空临时文件夹和session",httpMethod = "DELETE", response = Boolean.class)
	public @ResponseBody static boolean cleanTemp(HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("") + File.separator + "fileDir\\temp";
		FileUtil.clean(path);
		request.getSession().removeAttribute("fileInfo");
		return true;
	}

	/**
	 * 跳转到评论页面
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pageToComment", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "跳转到评论页面",httpMethod = "POST", response = ModelAndView.class)
	public ModelAndView pageToComment(Integer noticeId) {
		ModelAndView mv = new ModelAndView("/BasicData/commentDetail");
		mv.addObject("noticeId", noticeId);
		return mv;
	}

	/**
	 * 通过公告ID查询评论信息
	 * 
	 * @param noticeId
	 *            公告ID
	 * @param userName
	 *            用户名
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findCommentInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过公告ID查询评论信息",httpMethod = "POST", response = JSONSerializer.class)
	public Object findCommentInfo(Integer noticeId, String userName, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<CommentsVo> comment = shareService.selectCommentInfo(noticeId, userName, page.getStartIndex(),
				page.getLimit());
		int totalRows = shareService.selectCommentInfoRows(noticeId, userName);
		LayuiJSON<CommentsVo> commentInfo = new LayuiJSON<CommentsVo>(totalRows, comment);
		return JSONSerializer.toJSON(commentInfo);
	}

	/**
	 * 通过评论ID查询回复信息
	 * 
	 * @param replyId
	 *            评论ID
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findCommentReplyInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过评论ID查询回复信息",httpMethod = "POST", response = JSONSerializer.class)
	public Object findCommentReplyInfo(Integer noticeId, Integer replyId, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<CommentsVo> reply = shareService.selectCommentReplyInfo(noticeId, replyId, page.getStartIndex(),
				page.getLimit());
		int totalRows = shareService.selectCommentReplyInfoRows(noticeId, replyId);
		LayuiJSON<CommentsVo> replyInfo = new LayuiJSON<CommentsVo>(totalRows, reply);
		return JSONSerializer.toJSON(replyInfo);
	}

	/**
	 * 添加回复
	 * 
	 * @param comment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertReplyInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "添加回复",notes="新增评论回复信息",httpMethod = "POST", response = Gson.class)
	public Object insertReplyInfo(HttpServletRequest request, Comment comment) {
		JsonReturn info = (JsonReturn) request.getSession().getAttribute("admin");
		if (info != null) {
			Admin adminInfo = (Admin) info.getData();
			comment.setAdminId(adminInfo.getAdminId());
		}
		result = shareService.insertReplyInfo(comment);
		return gson.toJson(result);
	}

	/**
	 * 删除评论信息
	 * 
	 * @param commentId
	 *            评论ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCommentInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除评论信息",httpMethod = "DELETE", response = Gson.class)
	public Object deleteCommentInfo(Integer commentId) {
		result = shareService.deleteCommentInfo(commentId);
		return gson.toJson(result);
	}

	/********************************************DID转换工具********************************************/
	/**
	 * 查询DID仓库表格
	 * 
	 * @param didName DID名称
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findDIDContent", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询DID仓库表格",httpMethod = "POST", response = JSONSerializer.class)
	public Object findDIDContent(String didName, Integer typeId, Page page, HttpSession session) {
		if (Tools.isNotNull(didName)) {
			didName = didName.substring(0, 1).toUpperCase().concat(didName.substring(1));
		}
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<DidConversion> list = null;
		if (typeId == 1) {
			list = shareService.findDIDContent(didName, page.getStartIndex(), page.getLimit());
		} else {
			list = shareService.findDIDContent(didName, page.getStartIndex(), 0);
		}
		int totalRows = shareService.findDIDContentCount(didName);// 获取数据总条数
		List<DidConversion> listDid = new ArrayList<DidConversion>();
		int num = 0;
		if (typeId == 2) {
			List<DidConversion> listSession = (List<DidConversion>) session.getAttribute("saveDidContent");
			if (listSession != null) {
				for (DidConversion didInfo : listSession) {
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).getDidName().equals(didInfo.getDidName())) {
							DidConversion didConversion = list.remove(i);
							if (didConversion != null) {
								num++;
								break;
							}
						}
					}
				}
			}
		} else if (typeId == 3) {
			int relevanceId = (int) session.getAttribute("relevanceId");
			List<DidConversionVo> didLists = shareService.selectDidListInfo(relevanceId, didName, page.getStartIndex(),
					0);
			for (DidConversionVo didInfo : didLists) {
				for (int j = 0; j < list.size(); j++) {
					if (didInfo.getDidConversionId() == list.get(j).getDidConversionId()) {
						DidConversion didConversion = list.remove(j);
						if (didConversion != null) {
							num++;
							break;
						}
					}
				}
			}
		}
		// 分页
		if (typeId != 1 && list != null) {
			int start = 0;
			for (int i = 0; i < page.getLimit(); i++) {
				start = page.getStartIndex() + i;
				if (start < list.size()) {
					listDid.add(list.get(start));
				} else {
					break;
				}
			}
		} else {
			listDid.addAll(list);
		}

		totalRows = totalRows - num;
		LayuiJSON<DidConversion> didInfo = new LayuiJSON<DidConversion>(totalRows, listDid);
		return JSONSerializer.toJSON(didInfo);
	}

	/**
	 * 新增did到did仓库
	 * 
	 * @param did
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertDIDContent", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增DID到DID仓库",httpMethod = "POST", response = Gson.class)
	public Object insertDIDContent(DidConversion did) {
		if (Tools.isNotNull(did.getDidName())) {
			String didName = did.getDidName().substring(0, 1).toUpperCase().concat(did.getDidName().substring(1));
			did.setDidName(didName);
		}

		result = shareService.insertDIDContent(did);
		return gson.toJson(result);
	}

	/**
	 * 查询did仓库是否存在该条数据
	 * 
	 * @param did
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findDidByNameType", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询DID仓库是否存在该条数据",httpMethod = "POST", response = Gson.class)
	public Object findDidByNameType(DidConversion did) {
		if (Tools.isNotNull(did.getDidName())) {
			String didName = did.getDidName().substring(0, 1).toUpperCase().concat(did.getDidName().substring(1));
			did.setDidName(didName);
		}
		result = shareService.findDidByNameType(did);
		return gson.toJson(result);
	}

	/**
	 * 修改did
	 * 
	 * @param did
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateDIDContent", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改DID",httpMethod = "PATCH", response = Gson.class)
	public Object updateDIDContent(DidConversion did) {
		if (Tools.isNotNull(did.getDidName())) {
			String didName = did.getDidName().substring(0, 1).toUpperCase().concat(did.getDidName().substring(1));
			did.setDidName(didName);
		}
		result = shareService.updateDIDContent(did);
		return gson.toJson(result);
	}

	/**
	 * 批量删除did
	 * 
	 * @param did
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delDIDContent", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "批量删除DID",httpMethod = "DELETE", response = Gson.class)
	public Object delDIDContent(String ids) {
		String[] list = ids.split(",");
		List<Integer> listId = new ArrayList<>();
		for (String id : list) {
			listId.add(Integer.parseInt(id));
		}
		result = shareService.delDIDContent(listId);
		return gson.toJson(result);
	}

	/**
	 * 清除session
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cleanSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "清除session",notes="清除session中的DID转换信息",httpMethod = "DELETE", response = Gson.class)
	public Object cleanSession(HttpSession session) {
		session.removeAttribute("saveDidContent");
		return gson.toJson(true);
	}

	/**
	 * 查询DidSession
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectDidSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询DidSession",notes="查询session中的DID转换信息",httpMethod = "POST", response = JSONSerializer.class)
	public Object selectDidSession(HttpSession session, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<DidConversion> list = (List<DidConversion>) session.getAttribute("saveDidContent");
		int totalRows = list == null ? 0 : list.size();
		List<DidConversion> listInfo=new ArrayList<DidConversion>();
		int start = 0;
		//分页
		if(list!=null) {
			for (int i = 0; i < page.getLimit(); i++) {
				start = page.getStartIndex() + i;
				if (start < list.size()) {
					listInfo.add(list.get(start));
				} else {
					break;
				}
			}
		}else {
			listInfo=list;
		}
		LayuiJSON<DidConversion> didInfo = new LayuiJSON<DidConversion>(totalRows, listInfo);
		return JSONSerializer.toJSON(didInfo);
	}

	/**
	 * 导出did到session
	 * 
	 * @param dids
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveDidSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "导出did到session",httpMethod = "POST", response = Gson.class)
	public Object saveDidSession(String dids, HttpSession session) {
		List<DidConversion> listDid = Tools.jsonToList(dids, DidConversion.class);
		List<DidConversion> listSession = (List<DidConversion>) session.getAttribute("saveDidContent");
		if (listSession != null) {
			for (DidConversion did : listDid) {
				if (listSession.indexOf(did) < 0) {
					listSession.add(did);
				}
			}
			session.setAttribute("saveDidContent", listSession);
		} else {
			session.setAttribute("saveDidContent", listDid);
		}
		return gson.toJson(true);
	}

	/**
	 * 从session里删除did
	 * 
	 * @param session
	 * @param did
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delDidSession", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "从session中删除did",httpMethod = "POST", response = Gson.class)
	public Object delDidSession(HttpSession session, Integer didConversionId) {
		List<DidConversion> listSession = (List<DidConversion>) session.getAttribute("saveDidContent");
		boolean state = false;
		if(didConversionId>0) {
			for (int i = 0; i < listSession.size(); i++) {
				if(listSession.get(i).getDidConversionId()==didConversionId) {
					listSession.remove(i);
					state=true;
					break;
				}
			}
		}
		return gson.toJson(state);
	}

	/**
	 * 查询did转换信息
	 * 
	 * @param vehicleId
	 *            车型ID
	 * @param configurationLevelId
	 *            配置等级ID
	 * @param moduleId
	 *            模块ID
	 * @param supplierId
	 *            供应商ID
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findDidConversionInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询did转换信息",httpMethod = "POST", response = JSONSerializer.class)
	public Object findDidConversionInfo(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId, Page page) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		List<DidConversionVo> didConversion = shareService.selectDidConversionInfo(vehicleId, configurationLevelId,
				moduleId, supplierId, page.getStartIndex(), page.getLimit());
		int totalRows = shareService.selectDidConversionInfoRows(vehicleId, configurationLevelId, moduleId, supplierId);
		LayuiJSON<DidConversionVo> didConversionInfo = new LayuiJSON<DidConversionVo>(totalRows, didConversion);
		return JSONSerializer.toJSON(didConversionInfo);
	}

	/**
	 * 查询did列表信息
	 * 
	 * @param relevanceId
	 *            关联ID
	 * @param didName
	 *            DID名称
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findDidListInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询did列表信息",httpMethod = "POST", response = JSONSerializer.class)
	public Object findDidListInfo(Integer relevanceId, String didName, Page page, HttpSession session) {
		page.setStartIndex(page.getPage(), page.getLimit());// 计算分页开始索引
		session.setAttribute("relevanceId", relevanceId);
		List<DidConversionVo> didList = shareService.selectDidListInfo(relevanceId, didName, page.getStartIndex(),
				page.getLimit());
		int totalRows = shareService.selectDidListInfoRows(relevanceId, didName);
		LayuiJSON<DidConversionVo> didListInfo = new LayuiJSON<DidConversionVo>(totalRows, didList);
		return JSONSerializer.toJSON(didListInfo);
	}

	/**
	 * 新增DID转换信息
	 * 
	 * @param dids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertDidConversionInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "新增DID转换信息",httpMethod = "POST", response = JSONSerializer.class)
	public Object insertDidConversionInfo(String dids, Integer relevanceId) {
		List<DidConversion> listDid = Tools.jsonToList(dids, DidConversion.class);
		result = shareService.insertDidConversionInfo(listDid, relevanceId);
		return gson.toJson(result);
	}

	/**
	 * 删除DID转换信息
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteDidConversionInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "删除DID转换信息",httpMethod = "DELETE", response = Gson.class)
	public Object deleteDidConversionInfo(String ids) {
		result = shareService.deleteDidConversionInfo(ids);
		return gson.toJson(result);
	}

	/**
	 * 保存DID转换信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveDidConversionInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "保存DID转换信息",httpMethod = "POST", response = Gson.class)
	public Object saveDidConversionInfo(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId, HttpSession session) {
		List<DidConversion> listSession = (List<DidConversion>) session.getAttribute("saveDidContent");
		result=shareService.saveDidConversionInfo(vehicleId, configurationLevelId, moduleId, supplierId, listSession);
		return gson.toJson(result);
	}
	
	/**
	 * 通过主键ID进行批量删除DID转换信息
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteDidInfoById", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "通过主键ID进行批量删除DID转换信息",httpMethod = "DELETE", response = Gson.class)
	public Object deleteDidInfoById(String ids) {
		result=shareService.deleteDidInfoById(ids);
		return gson.toJson(result);
	}
	
	/**
	 * 修改DID转换的模块供应商信息
	 * @param vehicleSupplier
	 * @param reDidRelevanceId 关联ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateDidSupplierInfo", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "修改DID转换的模块供应商信息",httpMethod = "PATCH", response = Gson.class)
	public Object updateDidSupplierInfo(ReVehicleSupplier vehicleSupplier,Integer relevanceId) {
		result=shareService.updateDidSupplierInfo(vehicleSupplier, relevanceId);
		return gson.toJson(result);
	}
}
