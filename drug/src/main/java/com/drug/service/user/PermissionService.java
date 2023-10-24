package com.drug.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.drug.entity.user.Permission;

import java.util.List;


public interface PermissionService extends IService<Permission> {

//	List<Permission> getPermissionsByRoleName(String roleName);

	List<String> getPermissionUrlsByRoleName(String roleName);
	
	List<Permission> loadPermission(Integer permissionId);
	
	List<Permission> loadItemPermission(Integer roleId);
	
	List<Permission> loadNoParentPermission();
	
	List<Integer> findPermissionByRoleId(Integer roleId);
	
	List<Permission> findItemRoot();

	Integer deletePermissionByRoleId(Integer roleId);

	Permission findById(Integer id);

}
