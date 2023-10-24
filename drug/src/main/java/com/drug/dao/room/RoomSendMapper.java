package com.drug.dao.room;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.drug.entity.room.RoomSend;
import com.drug.web.room.request.RoomSendSearchParamRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoomSendMapper extends BaseMapper<RoomSend> {

    @Select("<script>"
            + " select rs.* "
            + " from room_send rs left join drug d on rs.drug_id=d.id"
            + " left join register r on rs.register_id=r.id"
            + " where 1=1"
            + " <if test=\"params.drugName != null and params.drugName.trim() != ''\">and d.name like CONCAT('%',#{params.drugName},'%') </if>"
            + " <if test=\"params.customerNo != null and params.customerNo.trim() != ''\">and r.customer_no like CONCAT('%',#{params.customerNo},'%') </if>"
            + " <if test=\"params.customerName != null and params.customerName.trim() != ''\">and r.customer_name like CONCAT('%',#{params.customerName},'%') </if>"
            + " order by rs.create_time desc"
            + "</script>")
    public IPage<RoomSend> getByQueryParams(IPage<RoomSend> page, @Param("params") RoomSendSearchParamRequest params);
}