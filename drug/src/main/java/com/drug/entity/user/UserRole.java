package com.drug.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.drug.entity.IdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(value="用户角色对象")
@NoArgsConstructor
@AllArgsConstructor
@TableName("USER_ROLE")
@Data
public class UserRole extends IdEntity {

	private static final long serialVersionUID = -6998691082059319752L;

	@ApiModelProperty(value="用户id")
	@TableField("USER_ID")
	protected Integer userId;

	@ApiModelProperty(value="角色id")
	@TableField("ROLE_ID")
	protected Integer roleId;
}
