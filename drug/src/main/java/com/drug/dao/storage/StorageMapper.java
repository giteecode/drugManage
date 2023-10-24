package com.drug.dao.storage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.drug.entity.storage.DrugStorage;
import com.drug.web.storage.request.StorageSearchParamRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StorageMapper extends BaseMapper<DrugStorage> {

    @Update("update drug_storage set stock_num = ifnull(stock_num,0) + #{num} where drug_id = #{drugId}")
    public Integer updateNumByDrugId(@Param("drugId") Integer drugId, @Param("num") Integer num);

    @Select("select * from drug_storage where drug_id = #{drugId}")
    public DrugStorage getByDrugId(@Param("drugId") Integer drugId);

    @Select("<script>"
            + " select sl.* "
            + " from drug_storage sl left join drug d on sl.drug_id=d.id"
            + " where 1=1"
            + " <if test=\"params.drugName != null\">and d.name like CONCAT('%',#{params.drugName},'%') </if>"
            + " order by sl.id desc"
            + "</script>")
    public IPage<DrugStorage> getByQueryParams(IPage<DrugStorage> page, @Param("params") StorageSearchParamRequest params);
}