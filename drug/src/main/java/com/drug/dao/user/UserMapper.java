package com.drug.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drug.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}