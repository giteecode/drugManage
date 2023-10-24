package com.drug.web.register.request;

import com.drug.api.request.BaseRequest;
import com.drug.entity.register.Register;
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
public class RegisterRequest extends BaseRequest {

	protected String customerName;
	protected String customerNo;
	protected Integer customerType;

	/**
	 * 根据REQUEST参数组装ROLE对象
	 */
	public static Register parseRequest(RegisterRequest request, Register register){
		if(register == null){
			register = new Register();
		}
		BeanUtils.copyProperties(request, register);
		return register;
	}
}
