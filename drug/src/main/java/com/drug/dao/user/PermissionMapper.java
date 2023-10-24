package com.drug.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drug.entity.user.Permission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    @Select("select p.name from permission p left join role_permission rp on p.id = rp.permission_id " +
			"where rp.role_id = #{roleId}")
    public List<String> getPermissionsByRoleId(@Param("roleId") Integer roleId);
    
    @Select("SELECT p.url FROM Permission p LEFT JOIN role_permission rp ON p.id=rp.PERMISSION_ID " +
			"LEFT JOIN role r ON rp.role_id=r.id WHERE r.NAME= #{roleName} AND p.url IS NOT NULL")
    public List<String> getPermissionUrlsByRoleName(@Param("roleName") String roleName);
    
    @Select("select p.* from Permission p where p.parent_id = #{parentId}")
	public List<Permission> loadPermission(@Param("parentId")Integer permissionId);
	
	@Select("select p.* from Permission p where p.parent_id is null")
	public List<Permission> loadNoParentPermission();
	
	@Select(value = "select p.PERMISSION_ID from ROLE_PERMISSION p where p.ROLE_ID = #{roleId}")
	public List<Integer> findPermissionByRoleId(@Param("roleId")Integer roleId);
	
	@Delete(value = "delete from ROLE_PERMISSION r where r.ROLE_ID =:roleId")
	public Integer deletePermissionByRoleId(@Param("roleId")Integer roleId);
	
	@Select("select p.* from Permission p left join p.roles r where AND p.parent_id is null AND r.id = #{roleId}")
	public List<Permission> loadItemPermission(@Param("roleId")Integer roleId);
	
	@Select("select p.* from Permission p order by id desc")
	public List<Permission> findAll();
	
	@Select("select * FROM Permission WHERE parent_id is null")
	public List<Permission> findItemRoot();

	@Select("SELECT T2.* FROM ( SELECT @r AS _id, ( SELECT @r := parent_id FROM permission WHERE id = _id ) AS parent_id, " +
			"@l := @l + 1 AS lvl FROM (SELECT @r := #{id}, @l := 0) vars, permission m WHERE @r <> 0 ) T1 " +
			"JOIN permission T2 ON T1._id = T2.id ORDER BY T1.lvl DESC;")
	public List<Integer> getAllParentsAndSelfById(@Param("id") Integer id);
	
}
