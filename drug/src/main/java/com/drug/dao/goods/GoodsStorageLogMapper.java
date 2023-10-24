package com.drug.dao.goods;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.drug.entity.goods.GoodsStorageLog;
import com.drug.web.goods.request.GoodsStorageLogSearchParamRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GoodsStorageLogMapper extends BaseMapper<GoodsStorageLog> {

    @Select("<script>"
            + " select sl.* "
            + " from goods_storage_log sl left join goods d on sl.goods_id=d.id"
            + " where 1=1"
            + " <if test=\"params.typeId != null\">and sl.operator_type = #{params.typeId} </if>"
            + " <if test=\"params.goodsName != null\">and d.name like CONCAT('%',#{params.goodsName},'%') </if>"
            + " order by sl.create_time desc"
            + "</script>")
    public IPage<GoodsStorageLog> getByQueryParams(IPage<GoodsStorageLog> page, @Param("params") GoodsStorageLogSearchParamRequest params);
}