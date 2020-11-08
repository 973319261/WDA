package com.gx.vo;

import java.util.List;

public class UploadFile {
	private String src;
	private Integer code;
	private String msg;
	private List<String> data;
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}
	public UploadFile(String src, Integer code, List<String> data) {
		super();
		this.src = src;
		this.code = code;
		this.msg = "";
		this.data = data;
	}
	public UploadFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static UploadFile getUploadFile(String src, Integer code, List<String> data){
		return new UploadFile(src, code, data);
	}
	
	
}
