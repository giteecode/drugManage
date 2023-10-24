package com.drug.web.register.response;

import com.drug.api.request.BaseRequest;
import com.drug.entity.register.Register;
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
public class RegisterResponse extends BaseRequest {

	protected String customerName;
	protected String customerNo;
	protected Integer customerType;
	protected Date createTime;

	/**
	 * 根据REQUEST参数组装对象
	 */
	public static RegisterResponse parseResponse(Register register){
		RegisterResponse response = new RegisterResponse();
		BeanUtils.copyProperties(register, response);
		return response;
	}
}
