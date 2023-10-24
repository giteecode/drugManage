package com.drug.entity.customer;

import com.baomidou.mybatisplus.annotation.TableName;
import com.drug.entity.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@TableName("customer_type")
@Data
public class CustomerType extends IdEntity {
    protected String name;
    public CustomerType(Integer id) {
        this.id = id;
    }
}
