package com.drug.web.user.request;

import com.drug.api.request.BaseRequest;
import com.drug.entity.user.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="角色保存/修改请求参数")
public class RoleRequest extends BaseRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6784751020328613014L;
	
	@ApiModelProperty(value="角色名称")
	protected String name;
	@ApiModelProperty(value="角色描述")
	protected String description;
	
	public RoleRequest(){
		
	}
	
	/**
	 * 根据REQUEST参数组装ROLE对象
	 */
	public static Role parseRoleRequest(RoleRequest roleRequest, Role role){
		if(role == null){
			role = new Role(roleRequest.getId());
		}
		
		role.setName(roleRequest.getName());
		role.setDescription(roleRequest.getDescription());
		return role;
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

	@Override
	public String toString() {
		return "RoleRequest [name=" + name + ", description=" + description + "]";
	}
}
