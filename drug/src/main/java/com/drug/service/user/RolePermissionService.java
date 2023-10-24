package com.drug.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.drug.entity.user.RolePermission;

import java.util.List;

public interface RolePermissionService extends IService<RolePermission> {
    public Integer deleteByRoleId(Integer roleId);

    public void deleteOldAndSaveNewByRoleId(Integer roleId, List<Integer> permissionIds);
}
