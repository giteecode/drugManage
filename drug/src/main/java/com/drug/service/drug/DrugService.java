package com.drug.service.drug;

import com.baomidou.mybatisplus.extension.service.IService;
import com.drug.entity.drug.Drug;

import java.util.List;

public interface DrugService extends IService<Drug> {
    public List<Drug> getAllByTypeId(Integer typeId);
}
