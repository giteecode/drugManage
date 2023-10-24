package com.drug.entity.goods;

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
@TableName("GOODS")
@Data
public class Goods extends IdEntity {

	protected String name;
	protected Integer userId;
	protected Date createTime;

	public Goods(Integer id) {
		this.id = id;
	}

}
