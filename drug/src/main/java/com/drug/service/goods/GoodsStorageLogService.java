package com.drug.service.goods;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.drug.entity.goods.GoodsStorageLog;
import com.drug.web.goods.request.GoodsStorageLogSearchParamRequest;

public interface GoodsStorageLogService extends IService<GoodsStorageLog> {
    public void saveAndUpdateStorage(GoodsStorageLog goodsStorageLog);

    // 查询多参数
    public IPage<GoodsStorageLog> getByQueryParams(Page<GoodsStorageLog> page, GoodsStorageLogSearchParamRequest params);
}
