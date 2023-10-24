package com.drug.web.drug.request;

import com.drug.api.request.BaseRequest;
import com.drug.entity.drug.Drug;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@ApiModel(description="保存/修改请求参数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrugRequest extends BaseRequest {

	protected String name;
	protected Date buyDate;
	protected Integer typeId;
	protected Integer prescriptionType;
	protected String company;
	protected String specifications;
	protected Integer userId;
	protected Date createTime;
	protected Double price;

	/**
	 * 根据REQUEST参数组装ROLE对象
	 */
	public static Drug parseRequest(DrugRequest request, Drug drug){
		if(drug == null){
			drug = new Drug();
		}
		BeanUtils.copyProperties(request, drug);
		return drug;
	}
}
