package com.drug.service.storage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.drug.entity.storage.DrugStorageLog;
import com.drug.web.storage.request.StorageLogSearchParamRequest;

public interface StorageLogService extends IService<DrugStorageLog> {
    public void saveAndUpdateStorage(DrugStorageLog drugStorageLog);

    // 查询多参数
    public IPage<DrugStorageLog> getByQueryParams(Page<DrugStorageLog> page, StorageLogSearchParamRequest params);
}
