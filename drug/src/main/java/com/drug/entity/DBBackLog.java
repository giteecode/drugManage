package com.drug.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@TableName("db_back_log")
@Data
public class DBBackLog extends IdEntity {

    protected String userName;
    protected Date backTime;
}