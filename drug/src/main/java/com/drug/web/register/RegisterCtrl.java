package com.drug.web.register;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drug.api.APIResult;
import com.drug.api.response.CommonPageRequest;
import com.drug.entity.customer.CustomerType;
import com.drug.entity.register.Register;
import com.drug.service.customer.CustomerTypeService;
import com.drug.service.register.RegisterService;
import com.drug.service.user.UserService;
import com.drug.utils.UserUtil;
import com.drug.web.register.request.RegisterRequest;
import com.drug.web.register.response.RegisterResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/register")
public class RegisterCtrl {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private CustomerTypeService customerTypeService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list/", method = RequestMethod.GET)
    @ResponseBody
    public List<Register> findRegisterAll() {
        //获取所有的
        return registerService.list();
    }

    @RequestMapping(value = "/list/page/", method = RequestMethod.POST)
    @ResponseBody
    public Page<Register> list(@RequestBody CommonPageRequest pageRequest) {

        Integer currentPage = pageRequest.getCurrentPage();
        Integer size = pageRequest.getPageSize();
        Object keyword = pageRequest.getKeyword();
        //初始化page
        Page<Register> page = new Page<Register>(currentPage, size);

        //设置条件
        QueryWrapper<Register> wrapper =new QueryWrapper<Register>();
        //eq是等于，ge是大于等于，gt是大于，le是小于等于，lt是小于，like是模糊查询
        if(!StringUtils.isEmpty(keyword.toString())){
            wrapper.like("customer_name", keyword);
        }
        //执行查询
        Page<Register> register = registerService.page(page, wrapper);
        register.getRecords().forEach(item -> {
            if(item.getCustomerType() != null) {
                item.setCustomerTypeName(customerTypeService.getById(item.getCustomerType()).getName());
            }
            if(item.getUserId() != null) {
                item.setUserName(userService.getById(item.getUserId()).getUsername());
            }
        });
        return register;
    }

    @RequestMapping(value = "/save/", method = RequestMethod.POST)
    @ResponseBody
    public APIResult<?> addRegister(@RequestBody RegisterRequest registerRequest) {
        //新建对象
        Register register = null;
        //判断ID不为空
        if (registerRequest.getId() != null) {
            //根据ID查询对象
            register = registerService.getById(registerRequest.getId());
        }
        //获取修改后的对象
        register = RegisterRequest.parseRequest(registerRequest, register);

        if (registerRequest.getId() == null) {
            register.setUserId(UserUtil.getCurrentPrincipal().getId());
            register.setCreateTime(new Date());
        }

        //保存对象
        registerService.saveOrUpdate(register);
        return APIResult.newSuccessResult();
    }

    @ApiOperation(value = "根据ID加载详情")
    @ApiImplicitParam(dataType = "int", example = "1", required = true, name = "id", allowEmptyValue = false, value = "ID")
    @RequestMapping(value = "/loadDetail/{id:[0-9]+}", method = RequestMethod.GET)
    @ResponseBody
    public RegisterResponse loadDetail(@PathVariable(name = "id") Integer id, HttpServletRequest request, HttpServletResponse response) {
        //获取当前对象
        Register register = registerService.getById(id);
        RegisterResponse registerResponse = RegisterResponse.parseResponse(register);
        return registerResponse;
    }

    @RequestMapping(value = "/typeList/", method = RequestMethod.GET)
    @ResponseBody
    public List<CustomerType> typeList() {
        return customerTypeService.list();
    }
}