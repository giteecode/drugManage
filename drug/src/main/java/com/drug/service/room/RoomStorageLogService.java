package com.drug.service.room;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.drug.entity.room.RoomStorageLog;
import com.drug.web.room.request.RoomStorageLogSearchParamRequest;

public interface RoomStorageLogService extends IService<RoomStorageLog> {
    public void saveAndUpdateStorage(RoomStorageLog roomStorageLog);

    // 查询多参数
    public IPage<RoomStorageLog> getByQueryParams(Page<RoomStorageLog> page, RoomStorageLogSearchParamRequest params);
}
