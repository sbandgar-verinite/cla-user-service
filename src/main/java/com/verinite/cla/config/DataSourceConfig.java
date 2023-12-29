package com.verinite.cla.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.verinite.commons.TenantRowMapper;
import com.verinite.commons.model.Tenant;

@Configuration
public class DataSourceConfig {

	@Value("${spring.datasource.host}")
	private String url;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;

	@Value("${spring.datasource.url}")
	private String defaultUrl;

//	@Qualifier("firstDataSource") DataSource firstDataSource,
//    @Qualifier("secondDataSource") DataSource secondDataSource

	@Bean
	public DataSource dynamicDataSource() {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		Map<Object, Object> dataSourceMap = new HashMap<>();
		dataSourceBuilder.driverClassName(driverClassName);
		dataSourceBuilder.username(username);
		dataSourceBuilder.password(password);

		MysqlDataSource mysqlDataSource = new MysqlDataSource();
		mysqlDataSource.setUrl(defaultUrl);
		mysqlDataSource.setPassword(password);
		mysqlDataSource.setUser(username);
		
//        dataSourceMap.put(dataSourceBuilder, dataSourceMap);
//        AbstractRoutingDataSource dataSource = new DynamicDataSource();
//        dataSource.setDefaultTargetDataSource(resolvedDataSources.get("tenant_1"));
//        dataSource.setTargetDataSources(resolvedDataSources);

		List<Tenant> tenantList =  new JdbcTemplate(mysqlDataSource).query("select * from tenant", new TenantRowMapper());
		
		if (!CollectionUtils.isEmpty(tenantList)) {
			for(Tenant tenant : tenantList) {
				dataSourceBuilder.url(url + tenant.getTenantCode());
				dataSourceMap.put(tenant.getTenantCode(), dataSourceBuilder.build());
			}
		}
		
		dataSourceBuilder.url(defaultUrl);
		dataSourceMap.put("default", dataSourceBuilder.build());
		dynamicDataSource.setDefaultTargetDataSource(dataSourceMap.get("default"));
		dynamicDataSource.setTargetDataSources(dataSourceMap);
		dynamicDataSource.afterPropertiesSet();

		return dynamicDataSource;
	}
}