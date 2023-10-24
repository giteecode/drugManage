package com.drug.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

/**
 * properties配置文件中数据的读取
 * LIKE:
 * @Autowired
 * private ConfigBeanValue configBeanValue;
 */
@Component
@PropertySources(value = {@PropertySource("classpath:application.properties")})
public class ConfigBeanValue {
	
}
