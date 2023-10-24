package com.drug.web.goods.response;

import com.drug.api.request.BaseRequest;
import com.drug.entity.goods.Goods;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@ApiModel(description="保存/修改请求参数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsResponse extends BaseRequest {

	protected Goods goods;

	/**
	 * 根据REQUEST参数组装对象
	 */
	public static GoodsResponse parseResponse(Goods goods){
		GoodsResponse goodsResponse = new GoodsResponse();
		goodsResponse.setGoods(goods);
		return goodsResponse;
	}
}
