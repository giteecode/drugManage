package com.drug.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description="公共请求对象")
public class BaseRequest implements Serializable {

	private static final long serialVersionUID = -2377420023435115428L;

	@ApiModelProperty(name="ID", value="实体ID")
	protected Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
