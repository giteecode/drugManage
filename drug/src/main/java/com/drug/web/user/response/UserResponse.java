package com.drug.web.user.response;

import com.drug.api.request.BaseResponse;
import com.drug.entity.user.Role;
import com.drug.entity.user.User;
import com.drug.utils.Collections;

public class UserResponse extends BaseResponse {

	private static final long serialVersionUID = -5754435863291418626L;
	
	//用户名
	protected String userName;
	//昵称
	protected String empName;
	//用户状态
	protected String isEnable;
	//部门ID
	protected Integer departmentId;
	//角色ID集合
	protected Integer roleId;
	
	public UserResponse(){
		
	}
	
	public UserResponse(User user){
		this.id = user.getId();
		this.userName = user.getUsername();
		this.empName = user.getEmpName();
		
		if(Collections.isNotEmpty(user.getRoles())){
			for(Role role : user.getRoles()){
				this.roleId = role.getId();
				break;
			}
		}
		
		this.isEnable = user.getIsEnable() == null ? "false" : user.getIsEnable().toString();
	}
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

}
