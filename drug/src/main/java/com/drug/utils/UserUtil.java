package com.drug.utils;

import com.drug.entity.user.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {

	/**
     * 获取当前用户对象
     * @return
     */
    public static UsernamePasswordAuthenticationToken getCurrentUserAuthentication(){
        return (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }
    
 
    /**
     * 获取当前用户登陆权限信息 
     * @return
     */
    public static User getCurrentPrincipal(){

    	User user = (User) SecurityContextHolder.getContext()
    		    .getAuthentication()
    		    .getPrincipal();
        return user;
    }
}	
