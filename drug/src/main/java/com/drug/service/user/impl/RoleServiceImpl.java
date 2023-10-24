package com.drug.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drug.dao.user.PermissionMapper;
import com.drug.entity.user.Role;
import com.drug.service.user.RoleService;
import com.drug.dao.user.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private  RoleMapper roleDao;

    @Autowired
    private PermissionMapper permissionMapper;

    public List<Role> getRolesByUserId(Integer userId) {
        List<Role> roles = roleDao.getRolesByUserId(userId);
        roles.forEach(item->{
            item.setPermissionNames(permissionMapper.getPermissionsByRoleId(item.getId()));
        });
        return roleDao.getRolesByUserId(userId);
    }
}
