package com.drug.service.goods.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drug.dao.goods.GoodsStorageLogMapper;
import com.drug.dao.goods.GoodsStorageMapper;
import com.drug.entity.goods.GoodsStorage;
import com.drug.entity.goods.GoodsStorageLog;
import com.drug.entity.storage.StorageOperatorTypeEnum;
import com.drug.service.goods.GoodsStorageLogService;
import com.drug.web.goods.request.GoodsStorageLogSearchParamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsStorageLogServiceImpl extends ServiceImpl<GoodsStorageLogMapper, GoodsStorageLog> implements GoodsStorageLogService {

    @Autowired
    private GoodsStorageMapper storageMapper;

    @Autowired
    private GoodsStorageLogMapper storageLogMapper;

    @Override
    public synchronized void saveAndUpdateStorage(GoodsStorageLog goodsStorageLog) {
        GoodsStorage goodsStorage = storageMapper.getByGoodsId(goodsStorageLog.getGoodsId());
        if(goodsStorage == null) {
            goodsStorage = new GoodsStorage();
            goodsStorage.setStockNum(goodsStorageLog.getNum());
            goodsStorage.setGoodsId(goodsStorageLog.getGoodsId());
            storageMapper.insert(goodsStorage);
        } else {
            // 根据类型设置 数量 + - 符号
            if(goodsStorageLog.getOperatorType() == StorageOperatorTypeEnum.DAMAGE.getId()
                    || goodsStorageLog.getOperatorType() == StorageOperatorTypeEnum.DEPARTMENT.getId()) {
                goodsStorageLog.setNum(goodsStorageLog.getNum() * -1);
            }
            storageMapper.updateNumByGoodsId(goodsStorageLog.getGoodsId(), goodsStorageLog.getNum());
        }
        goodsStorageLog.setOperatorEndNum(storageMapper.getByGoodsId(goodsStorageLog.getGoodsId()).getStockNum());
        storageLogMapper.insert(goodsStorageLog);
    }

    @Override
    public IPage<GoodsStorageLog> getByQueryParams(Page<GoodsStorageLog> page, GoodsStorageLogSearchParamRequest params) {
        return storageLogMapper.getByQueryParams(page, params);
    }
}
