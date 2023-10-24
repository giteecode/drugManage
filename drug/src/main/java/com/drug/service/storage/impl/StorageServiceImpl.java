package com.drug.service.storage.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drug.dao.storage.StorageMapper;
import com.drug.entity.storage.DrugStorage;
import com.drug.service.storage.StorageService;
import com.drug.web.storage.request.StorageSearchParamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, DrugStorage> implements StorageService  {

    @Autowired
    private  StorageMapper storageMapper;

    @Override
    public synchronized int updateNumByDrugId(Integer drugId, Integer num) {
        return storageMapper.updateNumByDrugId(drugId, num);
    }

    @Override
    public IPage<DrugStorage> getByQueryParams(Page<DrugStorage> page, StorageSearchParamRequest params) {
        return storageMapper.getByQueryParams(page, params);
    }
}
