package com.drug.entity.drug;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.drug.entity.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@TableName("DRUG")
@Data
public class Drug extends IdEntity {

	protected String name;
	protected Date buyDate;
	protected Integer typeId;
	protected Integer prescriptionType; //处方药类型
	protected String company;
	protected String specifications;
	protected Integer userId;
	protected Date createTime;
	protected Double price;
	@TableField(exist = false)
	protected String typeName;

	public Drug(Integer id) {
		this.id = id;
	}

}
