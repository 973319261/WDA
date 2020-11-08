package com.gx.vo;

/**
 * 选项
 */
public class AppendOptionVo {
	private Integer id;//选项ID
	private String name;//选项名称
	private String fullName;//全称
	private String value;

	public AppendOptionVo() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	@Override
	public boolean equals(Object that) {
		AppendOptionVo other = (AppendOptionVo) that;
	     return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()));
	}
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public AppendOptionVo(Integer id, String name, String fullName, String value) {
		super();
		this.id = id;
		this.name = name;
		this.fullName = fullName;
		this.value = value;
	}
}
