package com.drug.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.drug.entity.user.User;

import java.util.Set;

public interface UserService extends IService<User> {
   public void saveUserAndRoles (User user, Set<Integer> roleIds);
}
