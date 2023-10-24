package com.drug.service.drug.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drug.dao.drug.DrugMapper;
import com.drug.entity.drug.Drug;
import com.drug.service.drug.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugServiceImpl extends ServiceImpl<DrugMapper, Drug> implements DrugService {

    @Autowired
    private  DrugMapper DrugDao;

    public List<Drug> getAllByTypeId(Integer typeId) {
        return DrugDao.getAllByTypeId(typeId);
    }
}
