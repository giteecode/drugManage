package com.drug.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drug.dao.user.UserMapper;
import com.drug.entity.user.User;
import com.drug.dao.user.UserRoleMapper;
import com.drug.entity.user.UserRole;
import com.drug.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper  userRoleMapper;

    @Override
    public void saveUserAndRoles(User user, Set<Integer> roleIds) {
        if(user.getId() == null) {
            userMapper.insert(user);
        } else {
            // 删除之前的权限
            userRoleMapper.deleteByUserId(user.getId());
            // 更新用户
            userMapper.updateById(user);
        }

        // 新增新的权限
        for(Integer roleId : roleIds) {
           UserRole userRole = new UserRole();
           userRole.setUserId(user.getId());
           userRole.setRoleId(roleId);
           userRoleMapper.insert(userRole);
        }
    }
}
