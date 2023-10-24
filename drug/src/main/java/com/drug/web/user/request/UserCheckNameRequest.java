package com.drug.web.user.request;

import com.drug.api.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="用户保存/修改请求")
public class UserCheckNameRequest extends BaseRequest {

	private static final long serialVersionUID = -5754435863291418626L;
	
	@ApiModelProperty(value="用户名")
	protected String name;
	
	public UserCheckNameRequest(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
