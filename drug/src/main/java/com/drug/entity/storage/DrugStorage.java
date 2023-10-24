package com.drug.entity.storage;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.drug.entity.IdEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("drug_storage")
@Data
public class DrugStorage extends IdEntity {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" , timezone = "GMT+8")
	protected Date validityEndTime;
	protected Integer drugId;
	protected Integer stockNum;

	@TableField(exist = false)
	protected String drugName;

	public DrugStorage(Integer id) {
		this.id = id;
	}

}
