package com.drug.web.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drug.api.APIResult;
import com.drug.api.response.CommonPageRequest;
import com.drug.entity.user.Permission;
import com.drug.service.user.PermissionService;
import com.drug.web.user.request.PermissionRequest;
import com.drug.web.user.response.PermissionResponse;
import com.drug.web.user.response.PermissionTotalResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Api(tags = "权限业务请求地址")
@RestController
@RequestMapping("/permission")
public class PermissionCtrl {
	
	@Autowired
	private PermissionService permissionService;
	
	@ApiOperation(value = "获取权限列表", notes = "currentPage 当前页  \n pageSize 一页多少个 \n keyword 关键字")
	@RequestMapping(value="/list/", method=RequestMethod.POST)
	@ResponseBody
	public Page<Permission> list(@RequestBody CommonPageRequest pageRequest) {
		Integer currentPage = pageRequest.getCurrentPage();
		Integer size = pageRequest.getPageSize();
		Object keyword = pageRequest.getKeyword();
		//初始化page
		Page<Permission> page = new Page<Permission>(currentPage, size);

		//设置条件
		QueryWrapper<Permission> wrapper =new QueryWrapper<Permission>();
		//eq是等于，ge是大于等于，gt是大于，le是小于等于，lt是小于，like是模糊查询
		if(!StringUtils.isEmpty(keyword.toString())){
			wrapper.like("name", keyword);
		}

		//执行查询
		Page<Permission> permissions = permissionService.page(page, wrapper);
		for (Permission permission : permissions.getRecords()) {
			if(permission.getParentId() != null){
				String parentName = permissionService.findById(permission.getParentId()).getName();
				permission.setParentName(parentName);
			}
		}
		return permissions;
	}
	
	@ApiOperation(value = "根据ID获取权限详情")
	@ApiImplicitParam(dataType="int", example="1", required=true, name="id", allowEmptyValue=false, value="权限ID")
	@RequestMapping(value="/detail/{id:[0-9]+}", method=RequestMethod.GET)
	@ResponseBody
	public PermissionTotalResponse detail(@PathVariable(name="id") Integer id) {
		//新建总权限reponse
		PermissionTotalResponse permissionTotalResponse = new PermissionTotalResponse();
		//新建权限集合
		List<Permission> permissions = Collections.emptyList();
		//新建权限reponse集合
		List<PermissionResponse> permissionResponses = new ArrayList<PermissionResponse>();
		//新建权限reponse
		PermissionResponse permissionResponse = null;
		//判断权限ID不为空
		if(id != null){
			//获取当前权限
			Permission permission = permissionService.findById(id);
			//装载权限reponse
			permissionResponse = new PermissionResponse(permission, permissionService.getById(permission.getParentId()));
		//权限ID为空
		}else{
			//新建权限reponse
			permissionResponse = new PermissionResponse();
		}
		//将权限reponse放入总权限reponse
		permissionTotalResponse.setPermission(permissionResponse);

		// 一级权限
		permissions = permissionService.findItemRoot();
		//循环权限集合
		for(Permission permission : permissions){
			//新建权限reponse
			PermissionResponse permissResponse = new PermissionResponse(permission, permissionService.getById(permission.getParentId()));
			//将权限reponse放入权限reponse集合
			permissionResponses.add(permissResponse);
		}
		//将权限reponse集合放入总权限reponse
		permissionTotalResponse.setPermissions(permissionResponses);
		return permissionTotalResponse;
	}
	
	@ApiOperation(value = "新增权限加载详情数据")
	@RequestMapping(value="/detail/", method=RequestMethod.GET)
	@ResponseBody
	public List<PermissionResponse> addDetail() {
		//新建权限集合
		List<Permission> permissions = Collections.emptyList();
		//新建权限reponse集合
		List<PermissionResponse> permissionResponses = new ArrayList<PermissionResponse>();
		// 一级权限
		permissions = permissionService.findItemRoot();
		//循环权限集合
		for(Permission permission : permissions){
			//装载权限reponse
			PermissionResponse permissResponse = new PermissionResponse(permission, permissionService.getById(permission.getParentId()));
			//将reponse放入集合
			permissionResponses.add(permissResponse);
		}
		return permissionResponses;
	}
	
	@ApiOperation(value = "保存权限对象")
	@RequestMapping(value="/save/", method=RequestMethod.POST)
	@ResponseBody
	public APIResult<?> savePermission(@RequestBody PermissionRequest permissionRequest) {
		//新建权限对象
		Permission perm = null;
		//判断权限ID不为空
		if(permissionRequest.getId() != null){
			//权限ID不为空, 获取权限对象
			perm = permissionService.findById(permissionRequest.getId());
		}
		//装载权限对象
		Permission permission = PermissionRequest.parsePermissionRequest(permissionRequest, perm);
		//保存权限对象
		permissionService.saveOrUpdate(permission);
		
		return APIResult.newSuccessResult();
	}
}