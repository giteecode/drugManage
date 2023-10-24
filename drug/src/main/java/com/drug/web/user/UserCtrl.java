package com.drug.web.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drug.api.APIResult;
import com.drug.api.response.CommonPageRequest;
import com.drug.entity.user.Role;
import com.drug.entity.user.User;
import com.drug.entity.user.UserLoginLog;
import com.drug.service.user.RoleService;
import com.drug.service.user.UserLoginLogService;
import com.drug.service.user.UserService;
import com.drug.utils.UserUtil;
import com.drug.web.common.BaseCtrl;
import com.drug.web.user.request.UserPasswordRequest;
import com.drug.web.user.request.UserRequest;
import com.drug.web.user.request.UserThemeRequest;
import com.drug.web.user.response.UserFromResponse;
import com.drug.web.user.response.UserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;

@Api(tags="用户相关控制类")
@RestController
@RequestMapping("/user")
public class UserCtrl extends BaseCtrl {
    @Autowired
    private UserService userService;

    @Autowired
    private UserLoginLogService  userLoginLogService;

    @Autowired
    private RoleService roleService;

    /**
     * 前台每次请求(如果VUEX没有)都需要获取最新的用户信息 包含权限
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "获取最新的用户信息 包含权限")
    @RequestMapping(value="/info/", method= RequestMethod.POST)
    @ResponseBody
    public User info(HttpServletRequest request, HttpServletResponse response) {
        //获取当前登录用户
        User user = UserUtil.getCurrentPrincipal();
        user.setRoles(new HashSet<>(roleService.getRolesByUserId(user.getId())));
        return user;
    }

    @ApiOperation(value = "根据ID获取用户")
    @ApiImplicitParam(dataType="int", example="1", required=true, name="id", allowEmptyValue=false, value="用户ID")
    @GetMapping(value="/get/")
    @ResponseBody
    public User get() {
            //根据用户ID获取用户对象
        User user = userService.getById(UserUtil.getCurrentPrincipal().getId());
        return user;
    }

    /**
     * @param userRequest
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "保存用户对象")
    @RequestMapping(value="/save/", method=RequestMethod.POST)
    @ResponseBody
    public APIResult<?> save(@RequestBody UserRequest userRequest, HttpServletRequest request, HttpServletResponse response) {
        //保存用户对象
        User user = userService.getById(userRequest.getId());
        user = UserRequest.parseUserRequest(userRequest, user);
        userService.saveUserAndRoles(user, userRequest.getRoleIds());
        return APIResult.newSuccessResult();
    }

    @ApiOperation(value = "根据ID停用账户")
    @ApiImplicitParam(dataType="int", example="1", required=true, name="id", allowEmptyValue=false, value="用户ID")
    @GetMapping(value="/close/{id:[0-9]+}")
    @ResponseBody
    public String close(@PathVariable(name="id") Integer id, HttpServletRequest request, HttpServletResponse response) {

        User user = userService.getById(id);
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setIsEnable(false);
        userService.updateById(newUser);
        return "";
    }

    @ApiOperation(value = "根据ID启用账户")
    @ApiImplicitParam(dataType="int", example="1", required=true, name="id", allowEmptyValue=false, value="用户ID")
    @GetMapping(value="/open/{id:[0-9]+}")
    @ResponseBody
    public String open(@PathVariable(name="id") Integer id, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.getById(id);
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setIsEnable(true);
        userService.updateById(newUser);
        return "";
    }


    @ApiOperation(value = "根据ID加载用户详情")
    @ApiImplicitParam(dataType="int", example="1", required=true, name="id", allowEmptyValue=false, value="用户ID")
    @GetMapping(value="/detail/{id:[0-9]+}")
    @ResponseBody
    public UserFromResponse loadDateil(@PathVariable(name="id") Integer id, HttpServletRequest request, HttpServletResponse response) {
        //获取用户信息
        User user = userService.getById(id);
        //将用户信息放入reponse中
        UserResponse userResponse = new UserResponse(user);
        List<Role> userRoles = roleService.getRolesByUserId(user.getId());
        userResponse.setRoleId(userRoles.size() == 0 ? null : userRoles.get(0).getId());
        UserFromResponse userFromResponse = new UserFromResponse();
        //将用户reponse放入userFromReponse中
        userFromResponse.setUser(userResponse);
        //获取全部角色集合放入userFromReponse中
        List<Role> roles = roleService.list();
//        //放入reponse对象
        userFromResponse.setRoles(roles);
        return userFromResponse;
    }


    // （注释换行使用两个空格和一个\n）
    @ApiOperation(value = "获取用户列表", notes = "currentPage 当前页  \n pageSize 一页多少个 \n keyword 关键字")
    //用户列表
    @RequestMapping(value="/list/", method=RequestMethod.POST)
    @ResponseBody
    public Page<User> list(@RequestBody CommonPageRequest pageRequest) {
        Integer currentPage = pageRequest.getCurrentPage();
        Integer size = pageRequest.getPageSize();
        Object keyword = pageRequest.getKeyword();

        //初始化page
        Page<User> page = new Page<User>(currentPage, size);

        //设置条件
        QueryWrapper<User> wrapper =new QueryWrapper<User>();
        //eq是等于，ge是大于等于，gt是大于，le是小于等于，lt是小于，like是模糊查询
        if(!StringUtils.isEmpty(keyword.toString())){
            wrapper.like("user_name", keyword);
        }

        //执行查询
        Page<User> users = userService.page(page,wrapper);

        for(User user : page.getRecords()){
            user.setRoles(new HashSet<>(roleService.getRolesByUserId(user.getId())));
        }
        return users;
    }

    @ApiOperation(value = "修改用户密码")
    @RequestMapping(value="/updatePassword/", method=RequestMethod.POST)
    @ResponseBody
    public APIResult<?> updatePassword(@RequestBody UserPasswordRequest userPasswordRequest) {
        //根据用户ID获取用户对象
        User user = userService.getById(userPasswordRequest.getId());
        //获取修改后的用户对象
        user = UserPasswordRequest.parseUserRequest(userPasswordRequest, user);
        //保存用户对象
        userService.updateById(user);
        return APIResult.newSuccessResult();
    }

    // （注释换行使用两个空格和一个\n）
    @ApiOperation(value = "获取用户列表", notes = "currentPage 当前页  \n pageSize 一页多少个 \n keyword 关键字")
    //用户列表
    @RequestMapping(value="/loginLog/page", method=RequestMethod.POST)
    @ResponseBody
    public Page<UserLoginLog> loginLogList(@RequestBody CommonPageRequest pageRequest) {
        Integer currentPage = pageRequest.getCurrentPage();
        Integer size = pageRequest.getPageSize();
        Object keyword = pageRequest.getKeyword();

        //初始化page
        Page<UserLoginLog> page = new Page<UserLoginLog>(currentPage, size);

        //执行查询
        Page<UserLoginLog> users = userLoginLogService.page(page);

        return users;
    }

    @ApiOperation(value = "修改用户主题")
    @RequestMapping(value="/updateTheme/", method=RequestMethod.POST)
    @ResponseBody
    public APIResult<?> updateTheme(@RequestBody UserThemeRequest userThemeRequest) {
        //根据用户ID获取用户对象
        //根据用户ID获取用户对象
        User user = userService.getById(userThemeRequest.getId());
        //设置主题
        user.setTheme(userThemeRequest.getTheme());
        //保存用户对象
        userService.updateById(user);
        return APIResult.newSuccessResult();
    }

}
