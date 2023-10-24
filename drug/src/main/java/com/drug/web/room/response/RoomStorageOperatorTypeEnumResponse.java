package com.drug.web.room.response;

import com.drug.api.request.BaseRequest;
import com.drug.entity.room.RoomOperatorTypeEnum;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@EqualsAndHashCode(callSuper = true)
@ApiModel(description="保存/修改请求参数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomStorageOperatorTypeEnumResponse extends BaseRequest {

	protected String name;
	protected Boolean isAdd;

	public static RoomStorageOperatorTypeEnumResponse parseResponse(RoomOperatorTypeEnum type){
		RoomStorageOperatorTypeEnumResponse storageLogResponse = new RoomStorageOperatorTypeEnumResponse();
		BeanUtils.copyProperties(type, storageLogResponse);
		return storageLogResponse;
	}
}
