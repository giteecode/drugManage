package com.drug.web.user.request;


import com.drug.api.request.BaseRequest;
import io.swagger.annotations.ApiModel;

@ApiModel(description="保存用户主题请求参数")
public class UserThemeRequest extends BaseRequest {

	private static final long serialVersionUID = -5754435863291418626L;
	
	protected String theme;
	
	public UserThemeRequest(){
		
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
}