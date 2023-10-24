package com.drug.service.room.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drug.dao.room.RoomSendMapper;
import com.drug.dao.room.RoomStorageMapper;
import com.drug.entity.room.RoomSend;
import com.drug.entity.room.RoomStorage;
import com.drug.service.room.RoomSendService;
import com.drug.utils.UserUtil;
import com.drug.web.room.request.RoomSendRequest;
import com.drug.web.room.request.RoomSendSearchParamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RoomSendServiceImpl extends ServiceImpl<RoomSendMapper, RoomSend> implements RoomSendService {

    @Autowired
    private RoomStorageMapper roomStorageMapper;

    @Autowired
    private RoomSendMapper roomSendMapper;

    @Override
    public synchronized void saveAndUpdateStorage(RoomSendRequest roomSendRequest) {
        for(RoomSendRequest.RoomSendRequestDrug roomSendRequestDrug : roomSendRequest.getRoomSendRequestDrugs()) {
            Integer drugId = roomSendRequestDrug.getDrugId();
            Integer num = roomSendRequestDrug.getNum() * -1; // 发药为减库存

            // 目前库存
            RoomStorage roomStorage = roomStorageMapper.getByDrugId(drugId);
            // 在save check 已验证 此处不再验证
            // 根据类型设置 数量 - 符号
            roomStorageMapper.updateNumByDrugId(drugId, num);

            // 生成 send 对象
            RoomSend roomSend = new RoomSend();
            roomSend.setOperatorEndNum(roomStorageMapper.getByDrugId(drugId).getStockNum());// 操作后库存
            roomSend.setCreateTime(new Date());
            roomSend.setUserId(UserUtil.getCurrentPrincipal().getId());
            roomSend.setDrugId(drugId);
            roomSend.setNum(num);
            roomSend.setRegisterId(roomSendRequest.getRegisterId());
            roomSendMapper.insert(roomSend);
        }
    }

    @Override
    public IPage<RoomSend> getByQueryParams(Page<RoomSend> page, RoomSendSearchParamRequest params) {
        return roomSendMapper.getByQueryParams(page, params);
    }
}
