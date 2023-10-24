package com.drug.web.user.response;

import com.drug.utils.Collections;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(description="权限列表返回结果")
public class PermissionTotalResponse implements Serializable{

	private static final long serialVersionUID = -1016360465856477360L;
	
	@ApiModelProperty(value="权限")
	protected PermissionResponse permission = new PermissionResponse();
	@ApiModelProperty(value="权限集合")
	protected List<PermissionResponse> permissions = Collections.emptyList();
	
	public PermissionTotalResponse(){
		
	}
	
	public PermissionResponse getPermission() {
		return permission;
	}

	public void setPermission(PermissionResponse permission) {
		this.permission = permission;
	}

	public List<PermissionResponse> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionResponse> permissions) {
		this.permissions = permissions;
	}



	@Override
	public String toString() {
		return "PermissionTotalResponse [permission=" + permission + ", permissions=" + permissions + "]";
	}
	
}
