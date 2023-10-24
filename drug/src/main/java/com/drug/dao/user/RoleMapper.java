package com.drug.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drug.entity.user.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select r.* from role r where id in  (select role_id from user_role where user_id = #{userId})")
    List<Role> getRolesByUserId(@Param("userId") Integer userId);
}