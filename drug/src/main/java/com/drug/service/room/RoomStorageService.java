package com.drug.service.room;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.drug.entity.room.RoomStorage;
import com.drug.web.room.request.RoomStorageSearchParamRequest;

public interface RoomStorageService extends IService<RoomStorage> {
    // 更新库存
    public int updateNumByDrugId(Integer drugId, Integer num);

    // 查询多参数
    public IPage<RoomStorage> getByQueryParams(Page<RoomStorage> page, RoomStorageSearchParamRequest params);
}
