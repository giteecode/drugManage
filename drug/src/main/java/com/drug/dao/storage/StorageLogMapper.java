package com.drug.dao.storage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.drug.entity.storage.DrugStorageLog;
import com.drug.web.storage.request.StorageLogSearchParamRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StorageLogMapper extends BaseMapper<DrugStorageLog> {

    @Select("<script>"
            + " select sl.* "
            + " from drug_storage_log sl left join drug d on sl.drug_id=d.id"
            + " where 1=1"
            + " <if test=\"params.typeId != null\">and sl.operator_type = #{params.typeId} </if>"
            + " <if test=\"params.drugName != null\">and d.name like CONCAT('%',#{params.drugName},'%') </if>"
            + " order by sl.create_time desc"
            + "</script>")
    public IPage<DrugStorageLog> getByQueryParams(IPage<DrugStorageLog> page, @Param("params") StorageLogSearchParamRequest params);
}