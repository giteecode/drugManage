package com.drug.api.request;

import java.io.Serializable;

public class BaseResponse implements Serializable{

	private static final long serialVersionUID = -5157110200926411907L;

	protected Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
