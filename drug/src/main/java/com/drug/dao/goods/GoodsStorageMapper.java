package com.drug.dao.goods;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.drug.entity.goods.GoodsStorage;
import com.drug.web.goods.request.GoodsStorageSearchParamRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface GoodsStorageMapper extends BaseMapper<GoodsStorage> {

    @Update("update goods_storage set stock_num = ifnull(stock_num,0) + #{num} where goods_id = #{goodsId}")
    public Integer updateNumByGoodsId(@Param("goodsId") Integer goodsId, @Param("num") Integer num);

    @Select("select * from goods_storage where goods_id = #{goodsId}")
    public GoodsStorage getByGoodsId(@Param("goodsId") Integer goodsId);

    @Select("<script>"
            + " select sl.* "
            + " from goods_storage sl left join goods d on sl.goods_id=d.id"
            + " where 1=1"
            + " <if test=\"params.goodsName != null\">and d.name like CONCAT('%',#{params.goodsName},'%') </if>"
            + " order by sl.id desc"
            + "</script>")
    public IPage<GoodsStorage> getByQueryParams(IPage<GoodsStorage> page, @Param("params") GoodsStorageSearchParamRequest params);
}