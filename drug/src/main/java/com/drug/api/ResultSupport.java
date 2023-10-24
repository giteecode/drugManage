package com.drug.api;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultSupport implements Serializable {
	
	    private static final long serialVersionUID = -2235152751651905167L;

	    @ApiModelProperty(value="返回描述")
	    private String message;
	    @ApiModelProperty(value="返回编码")
	    private int code;
	    @ApiModelProperty(value="是否成功")
	    public boolean isSuccess() {
	        return code == ResultCode.SUCCESS_CODE;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }

	    public int getCode() {
	        return code;
	    }

	    public void setCode(int code) {
	        this.code = code;
	}
}
