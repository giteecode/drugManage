package com.drug.web.user.request;

import com.drug.api.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Collections;
import java.util.List;

@ApiModel(description="角色权限保存/修改请求参数")
public class RolePermissionRequest extends BaseRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9178308106083001451L;
	
	@ApiModelProperty(value="角色ID")
	protected Integer roleId;
	@ApiModelProperty(value="权限ID 查询时候用到")
	protected Integer permissionId;
	@ApiModelProperty(value="权限ID集合")
	protected List<Integer> permissionIds = Collections.emptyList();

	public RolePermissionRequest(){
		
	}
	
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}
	
	public List<Integer> getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(List<Integer> permissionIds) {
		this.permissionIds = permissionIds;
	}
}
