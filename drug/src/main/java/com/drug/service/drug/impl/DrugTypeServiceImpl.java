package com.drug.service.drug.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drug.dao.drug.DrugTypeMapper;
import com.drug.entity.drug.DrugType;
import com.drug.service.drug.DrugTypeService;
import org.springframework.stereotype.Service;

@Service
public class DrugTypeServiceImpl extends ServiceImpl<DrugTypeMapper, DrugType> implements DrugTypeService {

}
