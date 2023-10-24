package com.drug.service.goods;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.drug.entity.goods.GoodsStorage;
import com.drug.web.goods.request.GoodsStorageSearchParamRequest;

public interface GoodsStorageService extends IService<GoodsStorage> {
    // 更新库存
    public int updateNumByGoodsId(Integer goodsId, Integer num);

    // 参数查询
    public IPage<GoodsStorage> getByQueryParams(Page<GoodsStorage> page, GoodsStorageSearchParamRequest params);
}
