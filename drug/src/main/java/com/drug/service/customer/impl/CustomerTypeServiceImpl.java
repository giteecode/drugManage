package com.drug.service.customer.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drug.dao.customer.CustomerTypeMapper;
import com.drug.entity.customer.CustomerType;
import com.drug.service.customer.CustomerTypeService;
import org.springframework.stereotype.Service;

@Service
public class CustomerTypeServiceImpl extends ServiceImpl<CustomerTypeMapper, CustomerType> implements CustomerTypeService {

}
