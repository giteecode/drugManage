package com.drug.api.response;

import com.drug.api.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="分页公共组件")
public class CommonPageRequest extends BaseRequest {
	@ApiModelProperty(value="当前页", example="1", required=true)
	protected Integer currentPage;
	@ApiModelProperty(value="每页个数", example="10", required=true)
	protected Integer pageSize;
	@ApiModelProperty(value="关键字", example="", required=false, allowEmptyValue=true)
	protected String keyword;

	public Integer getCurrentPage() {
		return currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "CommonPageRequest [currentPage=" + currentPage + ", pageSize=" + pageSize + ", keyword=" + keyword
				+ "]";
	}
}
