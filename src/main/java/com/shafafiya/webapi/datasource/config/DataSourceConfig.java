package com.shafafiya.webapi.datasource.config;


import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class DataSourceConfig {
	
	@Bean(name = "datasourcecore")
	@Primary
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource primaryDataSource() {
	    return DataSourceBuilder.create().build();
	}

	@Bean(name = "datasourceinhouse")
	@ConfigurationProperties(prefix="spring.second.datasource")
	public DataSource secondaryDataSource() {
	    return DataSourceBuilder.create().build();
	}

}
