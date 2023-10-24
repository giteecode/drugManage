package com.drug.config.secure;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.drug.entity.user.User;
import com.drug.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public User loadUserByUsername(String username) {
		
		System.out.println("==============loadUserByUsername=========" + username);
		
		if (StringUtils.isBlank(username)) {
			throw new UsernameNotFoundException("用户名为空");
		}
		QueryWrapper<User> wrapper = Wrappers.query();
		wrapper.eq("user_name", username);
		List<User> users = userService.list(wrapper);
		if (users == null || users.size() == 0) {
			throw new UsernameNotFoundException("用户不存在");
		}
		return users.get(0);
	}
}