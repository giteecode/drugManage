package com.drug.web.goods.response;

import com.drug.api.request.BaseRequest;
import com.drug.entity.goods.GoodsOperatorTypeEnum;
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
public class GoodsStorageOperatorTypeEnumResponse extends BaseRequest {

	protected String name;
	protected Boolean isAdd;

	public static GoodsStorageOperatorTypeEnumResponse parseResponse(GoodsOperatorTypeEnum type){
		GoodsStorageOperatorTypeEnumResponse storageLogResponse = new GoodsStorageOperatorTypeEnumResponse();
		BeanUtils.copyProperties(type, storageLogResponse);
		return storageLogResponse;
	}
}
