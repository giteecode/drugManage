package com.drug.entity.drug;

import com.baomidou.mybatisplus.annotation.TableName;
import com.drug.entity.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("DRUG_TYPE")
@Data
public class DrugType extends IdEntity {

	protected String name;

	public DrugType(Integer id) {
		this.id = id;
	}

}
