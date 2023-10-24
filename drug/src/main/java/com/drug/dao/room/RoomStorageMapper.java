package com.drug.dao.room;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.drug.entity.room.RoomStorage;
import com.drug.web.room.request.RoomStorageSearchParamRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface RoomStorageMapper extends BaseMapper<RoomStorage> {

    @Update("update room_storage set stock_num = ifnull(stock_num,0) + #{num} where drug_id = #{drugId}")
    public Integer updateNumByDrugId(@Param("drugId") Integer drugId, @Param("num") Integer num);

    @Select("select * from room_storage where drug_id = #{drugId}")
    public RoomStorage getByDrugId(@Param("drugId") Integer drugId);

    @Select("<script>"
            + " select sl.* "
            + " from room_storage sl left join drug d on sl.drug_id=d.id"
            + " where 1=1"
            + " <if test=\"params.drugName != null\">and d.name like CONCAT('%',#{params.drugName},'%') </if>"
            + " order by sl.id desc"
            + "</script>")
    public IPage<RoomStorage> getByQueryParams(IPage<RoomStorage> page, @Param("params") RoomStorageSearchParamRequest params);
}