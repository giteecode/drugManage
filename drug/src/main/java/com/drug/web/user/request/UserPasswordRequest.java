package com.drug.web.user.request;

import com.drug.entity.user.User;
import com.drug.api.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ApiModel(description="用户保存/修改请求")
public class UserPasswordRequest extends BaseRequest {

	private static final long serialVersionUID = -5754435863291418626L;
	
	@ApiModelProperty(value="密码")
	protected String npsd;
	@ApiModelProperty(value="确认密码")
	protected String nconfirmPsd;
	
	public UserPasswordRequest(){
		
	}
	
	/**
	 * 根据REQUEST参数组装USER对象
	 */
	public static User parseUserRequest(UserPasswordRequest userPasswordRequest, User user){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(userPasswordRequest.getNpsd());
        user.setPassword(password);
		return user;
	}

	public String getNpsd() {
		return npsd;
	}

	public void setNpsd(String npsd) {
		this.npsd = npsd;
	}

	public String getNconfirmPsd() {
		return nconfirmPsd;
	}

	public void setNconfirmPsd(String nconfirmPsd) {
		this.nconfirmPsd = nconfirmPsd;
	}

	@Override
	public String toString() {
		return "UserRequest [npsd=" + npsd + ", nconfirmPsd=" + nconfirmPsd + "]";
	}
}
