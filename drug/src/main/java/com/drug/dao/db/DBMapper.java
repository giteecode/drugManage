package com.drug.dao.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drug.entity.DBBackLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DBMapper extends BaseMapper<DBBackLog> {
}