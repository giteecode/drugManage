package com.drug.web.user.response;

import com.drug.api.request.BaseResponse;
import com.drug.entity.user.Role;
import com.drug.utils.Collections;

import java.util.List;


public class UserFromResponse extends BaseResponse {

	private static final long serialVersionUID = -5754435863291418626L;
	
	//用户对象
	protected UserResponse user;
	//角色集合
	protected List<Role> roles = Collections.emptyList();

	public UserFromResponse(){
		
	}
	
	public UserResponse getUser() {
		return user;
	}

	public void setUser(UserResponse user) {
		this.user = user;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
