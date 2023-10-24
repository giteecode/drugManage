package com.drug.web.user.request;

import com.drug.api.request.BaseRequest;
import com.drug.entity.user.Permission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="权限保存/修改请求参数")
public class PermissionRequest extends BaseRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5901852674241024876L;
	
	@ApiModelProperty(value="权限名称")
	protected String name;
	@ApiModelProperty(value="权限描述")
	protected String description;
	@ApiModelProperty(value="所属父权限ID")
	protected Integer parentId;
	@ApiModelProperty(value="权限的Api地址")
	protected String url;
	
	public PermissionRequest(){
		
	}
	
	/**
	 * 根据REQUEST参数组装PERMISSION对象
	 */
	public static Permission parsePermissionRequest(PermissionRequest permissionRequest, Permission permission){
		if(permission == null){
			permission = new Permission(permissionRequest.getId());
		}
		
		permission.setName(permissionRequest.getName());
		permission.setDescription(permissionRequest.getDescription());
		permission.setUrl(permissionRequest.getUrl());
		
		permission.setParentId(permissionRequest.getParentId());

		return permission;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}