package com.drug.service.register.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drug.dao.register.RegisterMapper;
import com.drug.entity.register.Register;
import com.drug.service.register.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl extends ServiceImpl<RegisterMapper, Register> implements RegisterService {

    @Autowired
    private  RegisterMapper registerDao;
}
