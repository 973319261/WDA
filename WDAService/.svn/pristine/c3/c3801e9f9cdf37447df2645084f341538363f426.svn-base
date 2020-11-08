package com.gx.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gx.mapper.CommentDAO;
import com.gx.mapper.DidConversionDAO;
import com.gx.mapper.EolDAO;
import com.gx.mapper.FileDAO;
import com.gx.mapper.InformDAO;
import com.gx.mapper.InformDetailDAO;
import com.gx.mapper.NoticeDAO;
import com.gx.mapper.NoticeInformDAO;
import com.gx.mapper.ReDidRelevanceDAO;
import com.gx.mapper.ReVehicleSupplierDAO;
import com.gx.mapper.UserDAO;
import com.gx.po.Comment;
import com.gx.po.DidConversion;
import com.gx.po.Eol;
import com.gx.po.File;
import com.gx.po.Inform;
import com.gx.po.InformDetail;
import com.gx.po.Notice;
import com.gx.po.NoticeInform;
import com.gx.po.ReDidRelevance;
import com.gx.po.ReVehicleSupplier;
import com.gx.po.User;
import com.gx.service.IShareService;
import com.gx.util.WebSocketMapUtil;
import com.gx.vo.CommentsVo;
import com.gx.vo.DidConversionVo;
import com.gx.vo.FilesVo;
import com.gx.vo.InformVo;
import com.gx.vo.JsonReturn;
import com.gx.vo.NoticeVo;
import com.gx.web.MyWebSocketServer;

import cn.hutool.core.io.FileUtil;

@Transactional
@Service("iShareService")
public class ShareServiceImpl implements IShareService {

	// 注入DAO层
	@Autowired
	private NoticeDAO noticeDAO;
	@Autowired
	private FileDAO fileDAO;
	@Autowired
	private CommentDAO commentDAO;
	@Autowired
	private EolDAO eolDAO;
	@Autowired
	private InformDAO informDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private InformDetailDAO informDetailDAO;
	@Autowired
	private NoticeInformDAO noticeInformDAO;
	@Autowired
	private DidConversionDAO didConversionDAO;
	@Autowired
	private ReDidRelevanceDAO reDidRelevanceDAO;
	@Autowired
	private ReVehicleSupplierDAO reVehicleSupplierDAO;

	@Override
	public List<NoticeVo> selectNoticeInfo(String noticeTheme, int startIndex, int pageSize) {
		// TODO Auto-generated method stub
		return noticeDAO.selectNoticeInfo(noticeTheme, startIndex, pageSize);
	}

	@Override
	public int selectNoticeInfoRows(String noticeTheme) {
		// TODO Auto-generated method stub
		return noticeDAO.selectNoticeInfoRows(noticeTheme);
	}

	@Override
	public List<FilesVo> selectFileByNoticeId(int noticeId) {
		// TODO Auto-generated method stub
		return fileDAO.selectFileByNoticeId(noticeId);
	}

	@Override
	public JsonReturn insertNoticeInfo(Notice notice, FilesVo files) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("发布公告失败");
		if (notice != null && notice.getAdminId() > 0) {
			int intR = noticeDAO.insertSelective(notice);
			if (intR > 0) {
				File fileInfo = new File();
				fileInfo.setFileName("/fileDir/notice/" + files.getFileName());
				fileInfo.setFileTypeId(files.getFileTypeId());
				fileInfo.setNoticeId(notice.getNoticeId());
				int num = fileDAO.insertSelective(fileInfo);
				if (num > 0) {
					List<User> user = userDAO.selectCommonUserInfo();
					List<NoticeInform> informs = new ArrayList<NoticeInform>();
					NoticeInform noticeInform = null;
					for (int i = 0; i < user.size(); i++) {
						noticeInform = new NoticeInform();
						noticeInform.setNoticeId(notice.getNoticeId());
						;
						noticeInform.setIsRead(false);
						noticeInform.setUserId(user.get(i).getUserId());
						informs.add(noticeInform);
						// 消息推送到客户端
						try {
							// MyWebSocketServer myWebSocket =
							// WebSocketMapUtil.get(String.valueOf(user.get(i).getUserId()));
							MyWebSocketServer myWebSocket = WebSocketMapUtil.get(user.get(i).getUserId());
							InformVo informVo = new InformVo();
							informVo.setInformId(notice.getNoticeId());
							informVo.setInformTitle(notice.getNoticeName());
							informVo.setInformContent(notice.getNoticeDescribe());
							informVo.setTypeId(1);// 公告类型
							if (myWebSocket != null) {
								myWebSocket.sendMessage(1, "消息推送成功", informVo);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					int total = noticeInformDAO.insertNoticeInformByBatch(informs);
					if (total > 0) {
						jsonReturn.setText("success");
					}
				}
			}
		}
		return jsonReturn;
	}

	@Override
	public NoticeVo selectNoticeInfoById(Integer noticeId) {
		// TODO Auto-generated method stub
		return noticeDAO.selectNoticeById(noticeId);
	}

	@Override
	public JsonReturn updateNoticeInfo(Notice notice, FilesVo files, Integer id) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("修改失败");
		int intR = noticeDAO.updateByPrimaryKeySelective(notice);
		if (intR == 1) {
			if (id == 0) {
				File fileInfo = new File();
				fileInfo.setFileId(files.getFileId());
				fileInfo.setFileName("/fileDir/notice/" + files.getFileName());
				fileInfo.setFileTypeId(files.getFileTypeId());
				int num = fileDAO.updateByPrimaryKeySelective(fileInfo);
				if (num == 1) {
					jsonReturn.setText("success");
				}
			} else {
				jsonReturn.setText("success");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteNoticeInfo(Integer noticeId, String filePath) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		if (noticeId > 0) {
			// 查询公告的附件信息
			List<FilesVo> files = fileDAO.selectFileByNoticeId(noticeId);
			// 删除公告、附件和评论信息
			int intR = noticeDAO.deleteNoticeInfoById(noticeId);
			if (intR > 0) {
				String str = files.get(0).getFileName();
				String name = str.substring(str.lastIndexOf("/") + 1);
				FileUtil.del(filePath + name);
				jsonReturn.setText("success");
			}
		}
		return jsonReturn;
	}

	@Override
	public List<CommentsVo> selectCommentInfo(Integer noticeId, String userName, int startIndex, int pageSize) {
		// TODO Auto-generated method stub
		return commentDAO.selectCommentInfo(noticeId, userName, startIndex, pageSize);
	}

	@Override
	public int selectCommentInfoRows(Integer noticeId, String userName) {
		// TODO Auto-generated method stub
		return commentDAO.selectCommentInfoRows(noticeId, userName);
	}

	@Override
	public List<CommentsVo> selectCommentReplyInfo(Integer noticeId, Integer replyId, Integer startIndex,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return commentDAO.selectCommentReplyInfo(noticeId, replyId, startIndex, pageSize);
	}

	@Override
	public int selectCommentReplyInfoRows(Integer noticeId, Integer replyId) {
		// TODO Auto-generated method stub
		return commentDAO.selectCommentReplyInfoRows(noticeId, replyId);
	}

	@Override
	public JsonReturn insertReplyInfo(Comment comment) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("回复失败");
		if (comment != null) {
			if (comment.getAdminId() != null) {
				comment.setCreationTime(new Date());
				int intR = commentDAO.insertSelective(comment);
				if (intR == 1) {
					jsonReturn.setText("success");
				}
			} else {
				jsonReturn.setText("请先登录");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteCommentInfo(Integer commentId) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		if (commentId > 0) {
			int intR = commentDAO.deleteByPrimaryKey(commentId);
			if (intR == 1) {
				jsonReturn.setText("success");
				int count = commentDAO.selectLevelInfoById(commentId);
				if (count > 0) {
					int num = commentDAO.delCommentByReplyID(commentId);
					if (num > 0) {
						jsonReturn.setText("success");
					} else {
						jsonReturn.setText("删除失败");
					}
				}

			}
		}
		return jsonReturn;
	}

	@Override
	public List<Eol> selectEolOperationInfo(String inputValue, int startIndex, int pageSize) {
		return eolDAO.selectEolOperationInfo(inputValue, startIndex, pageSize);
	}

	@Override
	public int selectEolOperationInfoRows(String inputValue) {
		return eolDAO.selectEolOperationInfoRows(inputValue);
	}

	@Override
	public JsonReturn saveEcuProductionLineInfo(Eol eol) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("保存失败");
		int item = 1;
		if (eol.getEouId() != null) {
			item = 2;
		}

		if (item == 1) {
			int num = eolDAO.selectEolNameUseRows(eol.getInputValue(), 0);
			if (num == 0) {
				int intR = eolDAO.insertSelective(eol);
				if (intR == 1) {
					jsonReturn.setText("success");
				}
			} else {
				jsonReturn.setText("该输入值已存在");
			}
		} else if (item == 2) {
			int num = eolDAO.selectEolNameUseRows(eol.getInputValue(), eol.getEouId());
			if (num == 0) {
				int intR = eolDAO.updateByPrimaryKeySelective(eol);
				if (intR == 1) {
					jsonReturn.setText("success");
				}
			} else {
				jsonReturn.setText("该输入值已存在");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteEcuInfo(int ecuId, int item) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		int intR = 0;
		if (item == 1) {
			// 删除ECU名称中文全称信息
			// intR = smSimEcuDAO.deleteByPrimaryKey(ecuId);
		} else if (item == 2) {
			// 删除供应商代码信息
			// intR = smSimSuppliercodeDAO.deleteByPrimaryKey(ecuId);
		} else if (item == 3) {
			// 删除ECU诊断CANID信息
			// intR = smSimCanidDAO.deleteByPrimaryKey(ecuId);
		} else if (item == 4) {
			// 删除ECU产线EOL信息
			intR = eolDAO.deleteByPrimaryKey(ecuId);
		}

		if (intR == 1) {
			jsonReturn.setText("success");
		}
		return jsonReturn;
	}

	@Override
	public List<InformVo> selectInform(String informTitle, int startIndex, int pageSize) {
		// TODO Auto-generated method stub
		return informDAO.selectInform(informTitle, startIndex, pageSize);
	}

	@Override
	public int selectInformRows(String informTitle) {
		// TODO Auto-generated method stub
		return informDAO.selectInformRows(informTitle);
	}

	@Override
	public JsonReturn insertInform(Inform inform) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("发送失败");
		if (inform != null) {
			if (inform.getAdminId() != null) {
				inform.setCreationTime(new Date());
				int intR = informDAO.insertSelective(inform);
				if (intR == 1) {
					List<User> user = userDAO.selectCommonUserInfo();
					List<InformDetail> informs = new ArrayList<InformDetail>();
					InformDetail informDetail = null;
					for (int i = 0; i < user.size(); i++) {
						informDetail = new InformDetail();
						informDetail.setInformId(inform.getInformId());
						informDetail.setIsRead(false);
						informDetail.setUserId(user.get(i).getUserId());
						informs.add(informDetail);
						// 消息推送到客户端
						try {
							// MyWebSocketServer myWebSocket =
							// WebSocketMapUtil.get(String.valueOf(user.get(i).getUserId()));
							MyWebSocketServer myWebSocket = WebSocketMapUtil.get(user.get(i).getUserId());
							InformVo informVo = new InformVo();
							informVo.setInformId(inform.getInformId());
							informVo.setInformTitle(inform.getInformTitle());
							informVo.setInformContent(inform.getInformContent());
							informVo.setTypeId(0);// 通知类型
							if (myWebSocket != null) {
								myWebSocket.sendMessage(1, "消息推送成功", informVo);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					int num = informDetailDAO.insertRelevanceInformByBatch(informs);
					if (num > 0) {
						jsonReturn.setText("success");
					}
				}
			} else {
				jsonReturn.setText("请先登录");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteInform(int informId) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		int intR = informDAO.deleteInformById(informId);
		if (intR > 0) {
			jsonReturn.setText("success");
		}
		return jsonReturn;
	}

	@Override
	public List<DidConversion> findDIDContent(String didName, int startIndex, int pageSize) {
		return didConversionDAO.findDIDContent(didName, startIndex, pageSize);
	}

	@Override
	public Integer findDIDContentCount(String didName) {
		return didConversionDAO.findDIDContentCount(didName);
	}

	@Override
	public JsonReturn insertDIDContent(DidConversion did) {
		JsonReturn jsonReturn = new JsonReturn();
		int intR = didConversionDAO.insertDIDContent(did);
		if (intR > 0) {
			jsonReturn.setCode(200);
		} else {
			jsonReturn.setCode(500);
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn updateDIDContent(DidConversion did) {
		JsonReturn jsonReturn = new JsonReturn();
		int intR = didConversionDAO.updateDIDContent(did);
		if (intR > 0) {
			jsonReturn.setCode(200);
		} else {
			jsonReturn.setCode(500);
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn delDIDContent(List<Integer> ids) {
		JsonReturn jsonReturn = new JsonReturn();
		int intR = didConversionDAO.delDIDContent(ids);
		if (intR > 0) {
			jsonReturn.setCode(200);
		} else {
			jsonReturn.setCode(500);
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn findDidByNameType(DidConversion did) {
		JsonReturn jsonReturn = new JsonReturn();
		DidConversion dbContent = didConversionDAO.findDidByNameType(did);
		if (dbContent == null) {
			jsonReturn.setCode(200);
		} else if (dbContent.getDidConversionId() == did.getDidConversionId()
				&& dbContent.getDidName().equals(did.getDidName())) {
			jsonReturn.setCode(200);
		} else {
			jsonReturn.setCode(500);
		}
		return jsonReturn;
	}

	@Override
	public List<DidConversionVo> selectDidConversionInfo(Integer vehicleId, Integer configurationLevelId,
			Integer moduleId, Integer supplierId, Integer startIndex, Integer pageSize) {
		// TODO Auto-generated method stub
		return reDidRelevanceDAO.selectDidConversionInfo(vehicleId, configurationLevelId, moduleId, supplierId,
				startIndex, pageSize);
	}

	@Override
	public int selectDidConversionInfoRows(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId) {
		// TODO Auto-generated method stub
		return reDidRelevanceDAO.selectDidConversionInfoRows(vehicleId, configurationLevelId, moduleId, supplierId);
	}

	@Override
	public List<DidConversionVo> selectDidListInfo(Integer relevanceId, String didName, Integer startIndex,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return reDidRelevanceDAO.selectDidListInfo(relevanceId, didName, startIndex, pageSize);
	}

	@Override
	public int selectDidListInfoRows(Integer relevanceId, String didName) {
		// TODO Auto-generated method stub
		return reDidRelevanceDAO.selectDidListInfoRows(relevanceId, didName);
	}

	@Override
	public JsonReturn insertDidConversionInfo(List<DidConversion> listDid, Integer relevanceId) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("导入失败");
		if (listDid != null && relevanceId > 0) {
			ReDidRelevance didInfo = null;
			int num = 0;
			for (int i = 0; i < listDid.size(); i++) {
				didInfo = new ReDidRelevance();
				didInfo.setDidConversionId(listDid.get(i).getDidConversionId());
				didInfo.setRelevanceId(relevanceId);
				int intR = reDidRelevanceDAO.insertSelective(didInfo);
				if (intR > 0) {
					num++;
				}
			}
			if (num == listDid.size()) {
				jsonReturn.setText("success");
			} else {
				jsonReturn.setText(num + "条导入成功，" + (listDid.size() - num) + "条导入失败");
			}
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteDidConversionInfo(String ids) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		String[] list = ids.split(",");
		List<Integer> listId = new ArrayList<>();
		for (String id : list) {
			listId.add(Integer.parseInt(id));
		}
		int intR = reDidRelevanceDAO.deleteDidConversionInfo(listId);
		if (intR > 0) {
			jsonReturn.setText("success");
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn saveDidConversionInfo(Integer vehicleId, Integer configurationLevelId, Integer moduleId,
			Integer supplierId, List<DidConversion> listDid) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("保存失败");
		int num = reDidRelevanceDAO.selectDidConversionInfoRows(vehicleId, configurationLevelId, moduleId, supplierId);
		if (num == 0) {
			ReVehicleSupplier info = reVehicleSupplierDAO.selectPrimaryKeyById(vehicleId, configurationLevelId,
					moduleId, supplierId);
			int intR = reDidRelevanceDAO.saveDidConversionInfo(listDid, info.getRelevanceId());
			if (intR > 0) {
				jsonReturn.setText("success");
			}
		} else {
			jsonReturn.setText("存在有相同的数据，请重新选择模块供应商信息");
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn deleteDidInfoById(String ids) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("删除失败");
		String[] list = ids.split(",");
		List<Integer> listId = new ArrayList<>();
		for (String id : list) {
			listId.add(Integer.parseInt(id));
		}
		int num = reDidRelevanceDAO.deleteDidInfoById(listId);
		if (num > 0) {
			jsonReturn.setText("success");
		}
		return jsonReturn;
	}

	@Override
	public JsonReturn updateDidSupplierInfo(ReVehicleSupplier vehicleSupplier, Integer relevanceId) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setText("修改失败");
		if(vehicleSupplier!=null && relevanceId>0) {
			// 查询模块供应商的关联ID
			ReVehicleSupplier relevance = reVehicleSupplierDAO.selectPrimaryKeyById(vehicleSupplier.getVehicleId(),
					vehicleSupplier.getConfigurationLevelId(), vehicleSupplier.getModuleId(),
					vehicleSupplier.getSupplierId());
			if(relevanceId.equals(relevance.getRelevanceId())) {
				jsonReturn.setText("success");
			}else {
				//查询组合是否已经存在
				int num=reDidRelevanceDAO.selectConnectRelevanceRows(relevance.getRelevanceId());
				if(num==0) {
					//通过关联查询DID转换信息
					List<ReDidRelevance> didRelevance=reDidRelevanceDAO.selectDidRelevanceByRelevanceId(relevanceId);
					for (int i = 0; i < didRelevance.size(); i++) {
						didRelevance.get(i).setRelevanceId(relevance.getRelevanceId());
					}
					//批量修改DID转换关联信息
					int count=reDidRelevanceDAO.updatetDidRelevanceInfoByBatch(didRelevance);
					if(count>0) {
						jsonReturn.setText("success");
					}
				}else {
					jsonReturn.setText("该组合已经存在，请重新选择");
				}
			}
		}
		return jsonReturn;
	}

}
