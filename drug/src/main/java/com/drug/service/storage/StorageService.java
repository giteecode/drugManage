package com.drug.service.storage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.drug.entity.storage.DrugStorage;
import com.drug.web.storage.request.StorageSearchParamRequest;

public interface StorageService extends IService<DrugStorage> {
    // 更新库存
    public int updateNumByDrugId(Integer drugId, Integer num);

    // 查询多参数
    public IPage<DrugStorage> getByQueryParams(Page<DrugStorage> page, StorageSearchParamRequest params);
}
