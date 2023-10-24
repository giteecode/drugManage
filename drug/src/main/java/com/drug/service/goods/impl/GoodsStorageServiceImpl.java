package com.drug.service.goods.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drug.dao.goods.GoodsStorageMapper;
import com.drug.entity.goods.GoodsStorage;
import com.drug.service.goods.GoodsStorageService;
import com.drug.web.goods.request.GoodsStorageSearchParamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsStorageServiceImpl extends ServiceImpl<GoodsStorageMapper, GoodsStorage> implements GoodsStorageService {

    @Autowired
    private GoodsStorageMapper goodsStorageMapper;

    @Override
    public synchronized int updateNumByGoodsId(Integer goodsId, Integer num) {
        return goodsStorageMapper.updateNumByGoodsId(goodsId, num);
    }

    @Override
    public IPage<GoodsStorage> getByQueryParams(Page<GoodsStorage> page, GoodsStorageSearchParamRequest params) {
        return goodsStorageMapper.getByQueryParams(page, params);
    }
}
