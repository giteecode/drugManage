package com.drug.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.drug.entity.IdEntity;
import io.swagger.annotations.ApiModel;
import lombok.*;

@ApiModel(value="角色权限对象")
@NoArgsConstructor
@AllArgsConstructor
@TableName("ROLE_PERMISSION")
@Data
public class RolePermission extends IdEntity {

	@TableField("ROLE_ID")
	protected Integer roleId;

	@TableField("PERMISSION_ID")
	protected Integer permissionId;
}
