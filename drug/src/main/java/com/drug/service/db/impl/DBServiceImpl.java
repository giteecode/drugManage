package com.drug.service.db.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drug.dao.db.DBMapper;
import com.drug.entity.DBBackLog;
import com.drug.service.db.DBService;
import org.springframework.stereotype.Service;

@Service
public class DBServiceImpl extends ServiceImpl<DBMapper, DBBackLog> implements DBService {
}
