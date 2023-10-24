package com.drug.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.drug.entity.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@TableName("ROLE")
@Data
public class Role extends IdEntity {

	private static final long serialVersionUID = -6998691082059319752L;

	@TableField("NAME")
	protected String name;
	@TableField("description")
	protected String description;

	@TableField(exist = false) // 权限集合
	protected List<String> permissionNames;

	public Role (Integer id) {
		this.id = id;
	}

}
