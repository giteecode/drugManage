package com.drug.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="返回编码")
public class ResultCode {
	@ApiModelProperty(value="成功")
	public static final int SUCCESS_CODE = 1; 
	@ApiModelProperty(value="失败")
	public static final int ERROR_CODE = -1; 
}
