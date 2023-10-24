package com.drug.config.secure;

import com.drug.common.Constant;
import com.drug.service.user.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 过滤所有的URL 如果是ADMIN 则通过权限验证，否则根据 USER.ROLES.PERMIMMIOS中拥有权限的URL和当前请求的URL做对接，相等则通过，否则403
 */
@Component("rbacService")
public class RbacServiceImpl implements RbacService {
	
	@Autowired
	protected PermissionService permissionService;
	
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    
    public static Logger log = LoggerFactory.getLogger(RbacServiceImpl.class);

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
    	log.debug("=========hasPermission Authentication");
        Object principe = authentication.getPrincipal();
        if(principe instanceof UserDetails) {
            //拿到用户名后可以拿到用户角色和用户所有的权限
            Collection<? extends GrantedAuthority> grantedAuthorities = ((UserDetails) principe).getAuthorities();
            //如果角色是admin 则直接通行
            for(GrantedAuthority grantedAuthority : grantedAuthorities){
            	if(grantedAuthority.getAuthority().equalsIgnoreCase("ADMIN")){
            		log.debug("hasPermission with isAdmin");
            		return true;
            	}
            }
            
            //读取用户所有的url
            Set<Object> urls = new HashSet<Object>();
            for(GrantedAuthority grantedAuthority : grantedAuthorities){
            	String roleName = grantedAuthority.getAuthority();
            	List<String> permissionUrls = permissionService.getPermissionUrlsByRoleName(roleName);
            	for(String url : permissionUrls){
            		//一个权限 可能包含多个URL
            		if(url.indexOf(Constant.UserPermission.SPLIT_TAG) != -1){
            			
            			String[] permissions = url.split(Constant.UserPermission.SPLIT_TAG);
            			List<Object> urlStrings = Arrays.asList(permissions);
            			urls.addAll(urlStrings);
            			
//            			urls.addAll(Arrays.asList(url.split(Constant.UserPermission.SPLIT_TAG)));
            		}else{
            			urls.add(url);
            		}
            	}
            	
            }
            urls.removeAll(java.util.Collections.singleton(null));
            for(Object url : urls) {
                if(antPathMatcher.match(url.toString(), request.getRequestURI())) {
                   log.debug("hasPermission with matchUrl" + request.getRequestURI());
                   return true;
                }
            }
        }
        log.debug("no Permission " + request.getRequestURI());
        return false;
    }
}
