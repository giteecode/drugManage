package com.drug.web.storage.response;

import com.drug.api.request.BaseRequest;
import com.drug.entity.storage.StorageOperatorTypeEnum;
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
public class StorageOperatorTypeEnumResponse extends BaseRequest {

	protected String name;
	protected Boolean isAdd;

	public static StorageOperatorTypeEnumResponse parseResponse(StorageOperatorTypeEnum type){
		StorageOperatorTypeEnumResponse storageLogResponse = new StorageOperatorTypeEnumResponse();
		BeanUtils.copyProperties(type, storageLogResponse);
		return storageLogResponse;
	}
}
