package com.drug.web.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drug.api.APIResult;
import com.drug.api.response.CommonPageRequest;
import com.drug.entity.user.Permission;
import com.drug.entity.user.Role;
import com.drug.service.user.PermissionService;
import com.drug.service.user.RolePermissionService;
import com.drug.service.user.RoleService;
import com.drug.utils.Collections;
import com.drug.web.user.request.RolePermissionRequest;
import com.drug.web.user.request.RoleRequest;
import com.drug.web.user.response.PermissionResponse;
import com.drug.web.user.response.RolePermissionResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleCtrl {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;


    @ApiOperation(value = "获取所有角色列表")
    @RequestMapping(value = "/list/", method = RequestMethod.GET)
    @ResponseBody
    public List<Role> findRoleAll() {
        //获取所有的角色
        return roleService.list();
    }

    @ApiOperation(value = "获取所有角色列表(分页)", notes = "currentPage 当前页  \n pageSize 一页多少个 \n keyword 关键字")
    @RequestMapping(value = "/list/page/", method = RequestMethod.POST)
    @ResponseBody
    public Page<Role> list(@RequestBody CommonPageRequest pageRequest) {

        Integer currentPage = pageRequest.getCurrentPage();
        Integer size = pageRequest.getPageSize();
        Object keyword = pageRequest.getKeyword();
        //初始化page
        Page<Role> page = new Page<Role>(currentPage, size);

        //设置条件
        QueryWrapper<Role> wrapper =new QueryWrapper<Role>();
        //eq是等于，ge是大于等于，gt是大于，le是小于等于，lt是小于，like是模糊查询
        if(!StringUtils.isEmpty(keyword.toString())){
            wrapper.like("name", keyword);
        }

        //执行查询
        Page<Role> roles = roleService.page(page, wrapper);
        return roles;
    }

    @ApiOperation(value = "保存角色对象")
    @RequestMapping(value = "/save/", method = RequestMethod.POST)
    @ResponseBody
    public APIResult<?> addRole(@RequestBody RoleRequest roleRequest) {
        //新建角色对象
        Role role = null;
        //判断角色ID不为空
        if (roleRequest.getId() != null) {
            //根据角色ID查询角色对象
            role = roleService.getById(roleRequest.getId());
        }
        //获取修改后的角色对象
        role = RoleRequest.parseRoleRequest(roleRequest, role);
        //保存角色对象
        roleService.saveOrUpdate(role);
        return APIResult.newSuccessResult();
    }

    @ApiOperation(value = "根据ID加载角色详情")
    @ApiImplicitParam(dataType = "int", example = "1", required = true, name = "id", allowEmptyValue = false, value = "角色ID")
    @RequestMapping(value = "/loadDetail/{id:[0-9]+}", method = RequestMethod.GET)
    @ResponseBody
    public Role loadDetail(@PathVariable(name = "id") Integer id, HttpServletRequest request, HttpServletResponse response) {
        //获取当前角色对象
        Role role = roleService.getById(id);
        return role;
    }

    @ApiOperation(value = "加载角色权限树")
    @RequestMapping(value = "/loadPermission", method = RequestMethod.POST)
    @ResponseBody
    public RolePermissionResponse loadPermission(@RequestBody RolePermissionRequest rolePermissionRequest) {
        //新建角色权限reponse
       RolePermissionResponse rolePermissionResponse = new RolePermissionResponse();
        //新建权限ID集合
        List<Integer> permissionIds = Collections.emptyList();
        //新建权限集合
        List<Permission> permissions = Collections.emptyList();
        //判断权限ID, 说明前台想获取此权限的所有子权限
        if (rolePermissionRequest.getPermissionId() != null) {
            permissions = permissionService.loadPermission(rolePermissionRequest.getPermissionId());
        } else {
            //如果没有权限ID, 说明前台想接收所有的一级权限
            permissions = permissionService.loadNoParentPermission();
        }
        rolePermissionResponse.setPermissions(permissions);

        //查询当前角色下的所有权限
        if (rolePermissionRequest.getRoleId() != null) {
            permissionIds = permissionService.findPermissionByRoleId(rolePermissionRequest.getRoleId());
        }
        rolePermissionResponse.setEntityIds(permissionIds);
        //返回前台
        return rolePermissionResponse;
    }

    @ApiOperation(value = "保存角色权限集合")
    @RequestMapping(value = "/saveRolePermission", method = RequestMethod.POST)
    @ResponseBody
    public APIResult<?> saveRolePermission(@RequestBody RolePermissionRequest rolePermissionRequest) {
        rolePermissionService.deleteOldAndSaveNewByRoleId(rolePermissionRequest.getRoleId(), rolePermissionRequest.getPermissionIds());
        return APIResult.newSuccessResult();
    }

    @ApiOperation(value = "根据角色ID加载搜索权限列表")
    @ApiImplicitParam(dataType = "int", example = "1", required = true, name = "id", allowEmptyValue = false, value = "角色ID")
    @RequestMapping(value = "/loadSerachItemPermission/{id:[0-9]+}", method = RequestMethod.GET)
    @ResponseBody
    public List<PermissionResponse> loadSerachItemPermission(@PathVariable(name = "id") Integer roleId, HttpServletRequest request, HttpServletResponse response) {
        //新建全新集合
        List<Permission> permissions = new ArrayList<Permission>();
        //角色ID不为空
        if (roleId != null) {
            //获取当前角色所有的模块类型的权限
            permissions = permissionService.loadItemPermission(roleId);
        }
        //新建权限reponse集合
        List<PermissionResponse> permissionResponses = new ArrayList<PermissionResponse>();
        //根据角色ID获取角色对象
        Role role = roleService.getById(roleId);
        //判断集合不为空
        if (Collections.isNotEmpty(permissions)) {
            //循环权限集合
            for (Permission permission : permissions) {
                //装载权限reponse
                Permission permissionParent = permissionService.getById(permission.getParentId());
                PermissionResponse permissionResponse = new PermissionResponse(permission, permissionParent);
                //将权限reponse放入权限reponse集合
                permissionResponses.add(permissionResponse);
            }
        }
        //排序
        Collections.sort(permissionResponses, "id", true);
        return permissionResponses;
    }
}