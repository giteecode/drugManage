package com.drug.dao.room;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.drug.entity.room.RoomStorageLog;
import com.drug.web.room.request.RoomStorageLogSearchParamRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoomStorageLogMapper extends BaseMapper<RoomStorageLog> {

    @Select("<script>"
            + " select sl.* "
            + " from room_storage_log sl left join drug d on sl.drug_id=d.id"
            + " where 1=1"
            + " <if test=\"params.typeId != null\">and sl.operator_type = #{params.typeId} </if>"
            + " <if test=\"params.drugName != null\">and d.name like CONCAT('%',#{params.drugName},'%') </if>"
            + " order by sl.create_time desc"
            + "</script>")
    public IPage<RoomStorageLog> getByQueryParams(IPage<RoomStorageLog> page, @Param("params") RoomStorageLogSearchParamRequest params);
}