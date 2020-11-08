package com.gx.vo;

public class Page {

	private Integer page = 1;
	private Integer limit = 10;
	private Integer startIndex;
	private String findType;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer page, Integer limit) {
		this.startIndex = (page - 1) * limit;
	}

	public String getFindType() {
		return findType;
	}

	public void setFindType(String findType) {
		this.findType = findType;
	}

}
