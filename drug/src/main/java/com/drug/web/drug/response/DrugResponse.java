package com.drug.web.drug.response;

import com.drug.api.request.BaseRequest;
import com.drug.entity.drug.Drug;
import com.drug.entity.drug.DrugType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(description="保存/修改请求参数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrugResponse extends BaseRequest {

	protected List<DrugType> types;
	protected Drug drug;

	/**
	 * 根据REQUEST参数组装ROLE对象
	 */
	public static DrugResponse parseResponse(Drug drug){
		return parseResponse(drug, null);
	}

	/**
	 * 根据REQUEST参数组装ROLE对象
	 */
	public static DrugResponse parseResponse(Drug drug, List<DrugType> drugTypes){
		DrugResponse drugResponse = new DrugResponse();
		drugResponse.setDrug(drug);
		if(drugTypes != null ) {
			drugResponse.setTypes(drugTypes);
		}
		return drugResponse;
	}

}
