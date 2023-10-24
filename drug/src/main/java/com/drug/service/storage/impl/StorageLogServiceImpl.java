package com.drug.service.storage.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drug.dao.storage.StorageLogMapper;
import com.drug.dao.storage.StorageMapper;
import com.drug.entity.storage.DrugStorage;
import com.drug.entity.storage.DrugStorageLog;
import com.drug.entity.storage.StorageOperatorTypeEnum;
import com.drug.service.storage.StorageLogService;
import com.drug.web.storage.request.StorageLogSearchParamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageLogServiceImpl extends ServiceImpl<StorageLogMapper, DrugStorageLog> implements StorageLogService {

    @Autowired
    private StorageMapper storageMapper;

    @Autowired
    private StorageLogMapper storageLogMapper;

    @Override
    public synchronized void saveAndUpdateStorage(DrugStorageLog drugStorageLog) {
        DrugStorage drugStorage = storageMapper.getByDrugId(drugStorageLog.getDrugId());
        if(drugStorage == null) {
            drugStorage = new DrugStorage();
            drugStorage.setStockNum(drugStorageLog.getNum());
            drugStorage.setDrugId(drugStorageLog.getDrugId());
            storageMapper.insert(drugStorage);
        } else {
            // 根据类型设置 数量 + - 符号
            if(drugStorageLog.getOperatorType() == StorageOperatorTypeEnum.DAMAGE.getId()
                    || drugStorageLog.getOperatorType() == StorageOperatorTypeEnum.DEPARTMENT.getId()) {
                drugStorageLog.setNum(drugStorageLog.getNum() * -1);
            }
            storageMapper.updateNumByDrugId(drugStorageLog.getDrugId(), drugStorageLog.getNum());
        }
        drugStorageLog.setOperatorEndNum(storageMapper.getByDrugId(drugStorageLog.getDrugId()).getStockNum());
        storageLogMapper.insert(drugStorageLog);
    }

    @Override
    public IPage<DrugStorageLog> getByQueryParams(Page<DrugStorageLog> page, StorageLogSearchParamRequest params) {
        return storageLogMapper.getByQueryParams(page, params);
    }
}
