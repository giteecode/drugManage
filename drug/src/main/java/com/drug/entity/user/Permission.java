package com.drug.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.drug.entity.IdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value="权限对象")
@NoArgsConstructor
@AllArgsConstructor
@TableName("PERMISSION")
@Data
public class Permission extends IdEntity {

	@ApiModelProperty(value="权限名称")
	protected String name;
	@ApiModelProperty(value="权限描述")
	protected String description;
	@ApiModelProperty(value="所属父")
	protected Integer parentId;
	@ApiModelProperty(value="权限的Api地址")
	protected String url;
	@TableField(exist = false)
	protected String parentName;
	
	public Permission(Integer id) {
		super();
		this.id = id;
	}
}
