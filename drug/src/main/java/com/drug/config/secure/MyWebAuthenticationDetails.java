package com.drug.config.secure;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * 接受验证的参数 目前只有账号密码 如果有验证码需要重新加参数
 *
 */
public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

	private static final long serialVersionUID = 6975601077710753878L;
    
    private String username;
    
    private String password;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        username = request.getParameter("username"); 
        password = request.getParameter("password");
    }
}
