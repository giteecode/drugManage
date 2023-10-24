package com.drug.dao.drug;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drug.entity.drug.Drug;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DrugMapper extends BaseMapper<Drug> {

    @Select("select r.* from drug r where type_id  = #{typeId})")
    List<Drug> getAllByTypeId(@Param("typeId") Integer typeId);
}