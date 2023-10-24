package com.drug.web.goods.request;

import com.drug.api.request.BaseRequest;
import com.drug.entity.goods.Goods;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@ApiModel(description="保存/修改请求参数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsRequest extends BaseRequest {

	protected String name;
	protected Integer userId;
	protected Date createTime;

	/**
	 * 根据REQUEST参数组装ROLE对象
	 */
	public static Goods parseRequest(GoodsRequest request, Goods goods){
		if(goods == null){
			goods = new Goods();
		}
		BeanUtils.copyProperties(request, goods);
		return goods;
	}
}
