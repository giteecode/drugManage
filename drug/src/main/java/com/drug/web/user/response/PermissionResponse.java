package com.drug.web.user.response;

import com.drug.entity.user.Permission;
import com.drug.utils.Collections;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class PermissionResponse implements Serializable{
	
	private static final long serialVersionUID = -1016360465856477360L;
	
	@ApiModelProperty(value="ID")
	protected Integer id;
	@ApiModelProperty(value="权限名称")
	protected String name;
	@ApiModelProperty(value="权限父类")
	protected PermissionParentResponse parent;
	@ApiModelProperty(value="权限父类Id")
	protected Integer parentId;
	@ApiModelProperty(value="权限URL")
	protected String url;
	@ApiModelProperty(value="权限描述")
	protected String description;
	@ApiModelProperty(value="权限集合")
	protected List<PermissionResponse> children = Collections.emptyList();
	
	public PermissionResponse(){
		
	}

	public PermissionResponse(Permission permission, Permission permissionParent){
		
		if(permission != null){
			this.id = permission.getId();
			this.name = permission.getName();
			this.url = permission.getUrl();
			this.description = permission.getDescription();
			
			if(permission.getParentId() != null){
				PermissionParentResponse permissionParentResponse = new PermissionParentResponse(permissionParent);
				this.parent = permissionParentResponse;
				this.parentId = permission.getParentId();
			}else{
				this.parent = new PermissionParentResponse();
				this.parentId = null;
			}
		}
	}
	
	public PermissionResponse(Permission permission, Permission permissionParent, List<Permission> childs){
		
		if(permission != null){
			this.id = permission.getId();
			this.name = permission.getName();
			this.url = permission.getUrl();
			this.description = permission.getDescription();
			
			if(permissionParent != null){
				PermissionParentResponse permissionParentResponse = new PermissionParentResponse(permissionParent);
				this.parent = permissionParentResponse;
				this.parentId = permissionParent.getId();
			}else{
				this.parent = new PermissionParentResponse();
				this.parentId = null;
			}
			
			if(Collections.isNotEmpty(childs)){
				for(Permission permissionNew : childs){
					PermissionResponse permissionResponse = new PermissionResponse(permissionNew, null);
					this.children.add(permissionResponse);
				}
			}
		}
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PermissionResponse> getChildren() {
		return children;
	}

	public void setChildren(List<PermissionResponse> children) {
		this.children = children;
	}
	
	public PermissionParentResponse getParent() {
		return parent;
	}

	public void setParent(PermissionParentResponse parent) {
		this.parent = parent;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
