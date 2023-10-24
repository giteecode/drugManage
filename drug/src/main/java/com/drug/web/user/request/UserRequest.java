package com.drug.web.user.request;

import com.drug.entity.user.Role;
import com.drug.entity.user.User;
import com.drug.api.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@ApiModel(description="用户保存/修改请求")
public class UserRequest extends BaseRequest {

	private static final long serialVersionUID = -5754435863291418626L;
	
	@ApiModelProperty(value="用户名")
	protected String userName;
	@ApiModelProperty(value="员工英文名")
	protected String empName;
	@ApiModelProperty(value="密码")
	protected String password;
	@ApiModelProperty(value="确认密码")
	protected String confirmPassword;
	@ApiModelProperty(value="是否停用")
	protected String isEnable;
	@ApiModelProperty(value="对应角色IDS")
	protected Set<Integer> roleIds = new HashSet<Integer>();
	@ApiModelProperty(value="对应角色ID")
	protected Integer roleId;
	
	public UserRequest(){
		
	}
	
	/**
	 * 根据REQUEST参数组装USER对象
	 */
	public static User parseUserRequest(UserRequest userRequest, User user){
		if(user == null){
			user = new User(userRequest.getId());
			user.setCreateDate(new Date());
		}
		user.setEmpName(userRequest.getEmpName());
		if(userRequest.getIsEnable() != null){
			if(userRequest.getIsEnable().equals("true")){
				user.setIsEnable(true);
			}else{
				user.setIsEnable(false);
			}
		}
		
		user.setUserName(userRequest.getUserName());
		Set<Role> roles = new HashSet<Role>();
		userRequest.getRoleIds().forEach(id -> roles.add(new Role(id)));
		user.setRoles(roles);
		if(StringUtils.isNotBlank(userRequest.getPassword())){
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        String password = passwordEncoder.encode(userRequest.getPassword());
	        user.setPassword(password);
		}
		
		return user;
	}

	public String getUserName() {
		return userName;
	}

	public String getEmpName() {
		return empName;
	}

	public String getPassword() {
		return password;
	}

	public Set<Integer> getRoleIds() {
		return roleIds;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRoleIds(Set<Integer> roleIds) {
		this.roleIds = roleIds;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
}
