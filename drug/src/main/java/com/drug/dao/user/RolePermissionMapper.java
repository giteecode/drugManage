package com.drug.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drug.entity.user.RolePermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    @Delete("delete from role_permission where role_id = #{roleId}")
    public Integer deleteByRoleId(@Param("roleId") Integer roleId);

}