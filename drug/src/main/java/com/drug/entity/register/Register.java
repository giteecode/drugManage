package com.drug.entity.register;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.drug.entity.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("REGISTER")
@Data
public class Register extends IdEntity {

	protected String customerName;
	protected String customerNo;
	protected Integer customerType;
	protected Date createTime;
	protected Integer userId;

	@TableField(exist = false)
	protected String userName;
	@TableField(exist = false)
	protected String customerTypeName;

	public Register(Integer id) {
		this.id = id;
	}

}
