package com.drug.service.room.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drug.dao.room.RoomStorageLogMapper;
import com.drug.dao.room.RoomStorageMapper;
import com.drug.entity.room.RoomStorage;
import com.drug.entity.room.RoomStorageLog;
import com.drug.entity.storage.StorageOperatorTypeEnum;
import com.drug.service.room.RoomStorageLogService;
import com.drug.web.room.request.RoomStorageLogSearchParamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomStorageLogServiceImpl extends ServiceImpl<RoomStorageLogMapper, RoomStorageLog> implements RoomStorageLogService {

    @Autowired
    private RoomStorageMapper roomStorageMapper;

    @Autowired
    private RoomStorageLogMapper roomStorageLogMapper;

    @Override
    public synchronized void saveAndUpdateStorage(RoomStorageLog roomStorageLog) {
        RoomStorage roomStorage = roomStorageMapper.getByDrugId(roomStorageLog.getDrugId());
        if(roomStorage == null) {
            roomStorage = new RoomStorage();
            roomStorage.setStockNum(roomStorageLog.getNum());
            roomStorage.setDrugId(roomStorageLog.getDrugId());
            roomStorageMapper.insert(roomStorage);
        } else {
            // 根据类型设置 数量 + - 符号
            if(roomStorageLog.getOperatorType() == StorageOperatorTypeEnum.DAMAGE.getId()
                    || roomStorageLog.getOperatorType() == StorageOperatorTypeEnum.DEPARTMENT.getId()) {
                roomStorageLog.setNum(roomStorageLog.getNum() * -1);
            }
            roomStorageMapper.updateNumByDrugId(roomStorageLog.getDrugId(), roomStorageLog.getNum());
        }
        roomStorageLog.setOperatorEndNum(roomStorageMapper.getByDrugId(roomStorageLog.getDrugId()).getStockNum());
        roomStorageLogMapper.insert(roomStorageLog);
    }

    @Override
    public IPage<RoomStorageLog> getByQueryParams(Page<RoomStorageLog> page, RoomStorageLogSearchParamRequest params) {
        return roomStorageLogMapper.getByQueryParams(page, params);
    }
}
