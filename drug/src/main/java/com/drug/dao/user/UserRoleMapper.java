package com.drug.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drug.entity.user.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    @Select("delete from user_role where user_id = #{userId}")
    public Integer deleteByUserId(@Param("userId") Integer userId);
}