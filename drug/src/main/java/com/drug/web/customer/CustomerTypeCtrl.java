package com.drug.web.customer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drug.api.APIResult;
import com.drug.api.response.CommonPageRequest;
import com.drug.entity.customer.CustomerType;
import com.drug.service.customer.CustomerTypeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/customerType")
public class CustomerTypeCtrl {

    @Autowired
    private CustomerTypeService customerTypeService;

    @RequestMapping(value = "/list/", method = RequestMethod.GET)
    @ResponseBody
    public List<CustomerType> findCustomerAll() {
        //获取所有的
        return customerTypeService.list();
    }

    @RequestMapping(value = "/list/page/", method = RequestMethod.POST)
    @ResponseBody
    public Page<CustomerType> list(@RequestBody CommonPageRequest pageRequest) {

        Integer currentPage = pageRequest.getCurrentPage();
        Integer size = pageRequest.getPageSize();
        Object keyword = pageRequest.getKeyword();
        //初始化page
        Page<CustomerType> page = new Page<CustomerType>(currentPage, size);

        //设置条件
        QueryWrapper<CustomerType> wrapper =new QueryWrapper<CustomerType>();
        //eq是等于，ge是大于等于，gt是大于，le是小于等于，lt是小于，like是模糊查询
        if(!StringUtils.isEmpty(keyword.toString())){
            wrapper.like("name", keyword);
        }

        //执行查询
        Page<CustomerType> customers = customerTypeService.page(page, wrapper);
        return customers;
    }

    @RequestMapping(value = "/save/", method = RequestMethod.POST)
    @ResponseBody
    public APIResult<?> addCustomer(@RequestBody CustomerType customerType) {
        customerTypeService.saveOrUpdate(customerType);
        return APIResult.newSuccessResult();
    }

    @ApiOperation(value = "根据ID加载详情")
    @ApiImplicitParam(dataType = "int", example = "1", required = true, name = "id", allowEmptyValue = false, value = "ID")
    @RequestMapping(value = "/loadDetail/{id:[0-9]+}", method = RequestMethod.GET)
    @ResponseBody
    public CustomerType loadDetail(@PathVariable(name = "id") Integer id, HttpServletRequest request, HttpServletResponse response) {
        //获取当前对象
        CustomerType customerType = customerTypeService.getById(id);
        return customerType;
    }
}