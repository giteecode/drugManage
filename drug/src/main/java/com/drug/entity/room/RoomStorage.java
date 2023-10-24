package com.drug.entity.room;

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
@TableName("room_storage")
@Data
public class RoomStorage extends IdEntity {

	protected Integer drugId;
	protected Integer stockNum;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" , timezone = "GMT+8")
	protected Date validityEndTime;

	@TableField(exist = false)
	protected String drugName;

	public RoomStorage(Integer id) {
		this.id = id;
	}

}
