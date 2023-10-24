package com.drug.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drug.dao.user.PermissionMapper;
import com.drug.dao.user.RolePermissionMapper;
import com.drug.entity.user.RolePermission;
import com.drug.service.user.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Integer deleteByRoleId(Integer roleId) {
        return rolePermissionMapper.deleteByRoleId(roleId);
    }

    @Override
    public void deleteOldAndSaveNewByRoleId(Integer roleId, List<Integer> permissionIds) {
        rolePermissionMapper.deleteByRoleId(roleId);

        for(Integer pid : permissionIds) {
            List<Integer> allParentsAndSelfIds =  permissionMapper.getAllParentsAndSelfById(pid);
            for (Integer parentAndSelfId : allParentsAndSelfIds) {
                RolePermission rolePermission = new RolePermission(roleId, parentAndSelfId);
                rolePermissionMapper.insert(rolePermission);
            }
        }
    }
}
