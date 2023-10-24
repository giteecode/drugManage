package com.drug.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.drug.entity.IdEntity;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@TableName("user_login_log")
@Data
public class UserLoginLog extends IdEntity {

	protected String userName;
	protected Date loginTime;
	protected String des;

}
