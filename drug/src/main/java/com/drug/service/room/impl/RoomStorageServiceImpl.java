package com.drug.service.room.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drug.dao.room.RoomStorageMapper;
import com.drug.entity.room.RoomStorage;
import com.drug.service.room.RoomStorageService;
import com.drug.web.room.request.RoomStorageSearchParamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomStorageServiceImpl extends ServiceImpl<RoomStorageMapper, RoomStorage> implements RoomStorageService {

    @Autowired
    private  RoomStorageMapper roomStorageMapper;

    @Override
    public synchronized int updateNumByDrugId(Integer drugId, Integer num) {
        return roomStorageMapper.updateNumByDrugId(drugId, num);
    }

    @Override
    public IPage<RoomStorage> getByQueryParams(Page<RoomStorage> page, RoomStorageSearchParamRequest params) {
        return roomStorageMapper.getByQueryParams(page, params);
    }
}
