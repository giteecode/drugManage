package com.drug.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.drug.entity.user.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    public List<Role> getRolesByUserId(Integer userId);
}
