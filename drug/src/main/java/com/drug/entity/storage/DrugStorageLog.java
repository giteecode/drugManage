package com.drug.entity.storage;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.drug.entity.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@TableName("drug_storage_log")
@Data
public class DrugStorageLog extends IdEntity {

	protected Date createTime;
//	protected Date validityEndTime;
	protected Integer drugId; // 药品ID
	protected Integer userId; // userId
	protected Integer num; // 数量
	protected Integer operatorType;//操作类型
	protected Integer operatorEndNum;//本次操作剩余

	@TableField(exist = false)
	protected String userName;
	@TableField(exist = false)
	protected String typeName;
	@TableField(exist = false)
	protected String drugName;

	public DrugStorageLog(Integer id) {
		this.id = id;
	}

}
