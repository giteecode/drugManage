package com.drug.web.user.response;

import com.drug.entity.user.Permission;

import java.io.Serializable;

public class PermissionParentResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1016360465856477360L;
	
	//ID
	protected Integer id;
	//权限名称
	protected String name;
	
	public PermissionParentResponse(){
		
	}
	
	public PermissionParentResponse(Permission permission){
		
		if(permission != null){
			this.id = permission.getId();
			this.name = permission.getName();
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


	@Override
	public String toString() {
		return "PermissionResponse [id=" + id + ", name=" + name + "]";
	}
	
}
