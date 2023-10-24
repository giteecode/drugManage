package com.drug.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drug.dao.user.PermissionMapper;
import com.drug.entity.user.Permission;
import com.drug.service.user.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

	@Autowired
	protected PermissionMapper permissionDao;

//	@Override
//	public List<Permission> getPermissionsByRoleName(String roleName) {
//		// TODO Auto-generated method stub
//		return permissionDao.getPermissionsByRoleName(roleName);
//	}

	@Override
	public List<String> getPermissionUrlsByRoleName(String roleName) {
		// TODO Auto-generated method stub
		return permissionDao.getPermissionUrlsByRoleName(roleName);
	}

	@Override
	public List<Permission> loadPermission(Integer permissionId) {
		// TODO Auto-generated method stub
		return permissionDao.loadPermission(permissionId);
	}

	@Override
	public List<Permission> loadNoParentPermission() {
		// TODO Auto-generated method stub
		return permissionDao.loadNoParentPermission();
	}

	@Override
	public List<Integer> findPermissionByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		return permissionDao.findPermissionByRoleId(roleId);
	}

	@Override
	public Integer deletePermissionByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		return permissionDao.deletePermissionByRoleId(roleId);
	}

	@Override
	public Permission findById(Integer id) {
		// TODO Auto-generated method stub
		return permissionDao.selectById(id);
	}

	@Override
	public List<Permission> loadItemPermission(Integer roleId) {
		// TODO Auto-generated method stub
		return permissionDao.loadItemPermission(roleId);
	}

	@Override
	public List<Permission> findItemRoot() {
		// TODO Auto-generated method stub
		return permissionDao.findItemRoot();
	}
}
