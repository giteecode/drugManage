package com.drug.service.room;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.drug.entity.room.RoomSend;
import com.drug.web.room.request.RoomSendRequest;
import com.drug.web.room.request.RoomSendSearchParamRequest;

public interface RoomSendService extends IService<RoomSend> {

    public void saveAndUpdateStorage(RoomSendRequest roomSendRequest);

    // 查询多参数
    public IPage<RoomSend> getByQueryParams(Page<RoomSend> page, RoomSendSearchParamRequest params);
}
