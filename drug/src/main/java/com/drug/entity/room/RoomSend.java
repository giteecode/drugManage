package com.drug.entity.room;

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
@TableName("room_send")
@Data
public class RoomSend extends IdEntity {

	protected Integer drugId;
	protected Integer registerId; // 挂号ID
	protected Date createTime;
	protected Integer userId; // userId
	protected Integer num; // 数量
	protected Integer operatorEndNum;//本次操作剩余

	@TableField(exist = false)
	protected String userName;
	@TableField(exist = false)
	protected String customerName;
	@TableField(exist = false)
	protected String customerNo;
	@TableField(exist = false)
	protected String drugName;

	public RoomSend(Integer id) {
		this.id = id;
	}

}
