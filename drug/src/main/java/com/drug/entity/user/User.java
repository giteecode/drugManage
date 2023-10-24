package com.drug.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.drug.entity.IdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@ApiModel(value="用户对象")
@NoArgsConstructor
@AllArgsConstructor
@TableName("USER")
@Data
public class User extends IdEntity implements UserDetails {

	private static final long serialVersionUID = -6998691082059319752L;

	@ApiModelProperty(value="用户名")
	@TableField("USER_NAME")
	protected String userName;
	@ApiModelProperty(value="员工英文名")
	@TableField("EMP_NAME")
	protected String empName;
	@ApiModelProperty(value="密码")
	@TableField("PASSWORD")
	protected String password;
	@TableField("THEME")
	protected String theme;
	@ApiModelProperty(value="创建时间", example="2019-01-01 11:11:11")
	@TableField("CREATE_TIME")
	protected Date createDate;
	@ApiModelProperty(value="是否停用")
	@TableField("IS_ENABLE")
	protected Boolean isEnable;
	@ApiModelProperty(value="对应角色")
	@TableField(exist = false)
	protected Set<Role> roles = new HashSet<Role>();

	public User(Integer id) {
		super();
		this.id = id;
	}

	public User(String userName, String empName, String password, Date createDate, Date lastLoginDate) {
		super();
		this.userName = userName;
		this.empName = empName;
		this.password = password;
		this.createDate = createDate;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		Set<GrantedAuthority> authorities = new HashSet<>();
		this.getRoles().forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getName())));
		return authorities;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.isEnable == null ? false : this.isEnable;
	}
}
