package com.drug.web.user.response;

import com.drug.entity.user.Permission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@ApiModel(description="角色权限返回结果")
public class RolePermissionResponse implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 6939805833686667088L;
	
	@ApiModelProperty(value="类ID集合")
	protected List<Integer> entityIds = Collections.emptyList();
	@ApiModelProperty(value="权限集合")
	protected List<Permission> permissions = Collections.emptyList();
	
	public RolePermissionResponse(){
		
	}

	public List<Integer> getEntityIds() {
		return entityIds;
	}

	public void setEntityIds(List<Integer> entityIds) {
		this.entityIds = entityIds;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
}
