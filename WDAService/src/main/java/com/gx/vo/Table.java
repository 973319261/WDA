package com.gx.vo;


public class Table {
	private int code;
	private String msg;
	private int count;
	private Object data;
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public Table(int count, Object data) {
		super();
		this.code = 0;
		this.msg = "";
		this.count = count;
		this.data = data;
	}
	public static Table getTable(int count, Object data){
		return new Table(count, data);
	}
	
	
}	
