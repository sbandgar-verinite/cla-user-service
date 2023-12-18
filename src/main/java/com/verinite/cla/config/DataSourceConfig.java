//package com.verinite.cla.config;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
//
//@Configuration
//public class DataSourceConfig {
//	
//	@Value("${spring.datasource.host}")
//	private String url;
//	
//	@Value("${spring.datasource.username}")
//	private String username;
//	
//	@Value("${spring.datasource.password}")
//	private String password;
//
//	@Value("${spring.datasource.driverClassName}")
//	private String driverClassName;
//
//	@Value("${spring.datasource.url}")
//	private String defaultUrl;
//
////	@Qualifier("firstDataSource") DataSource firstDataSource,
////    @Qualifier("secondDataSource") DataSource secondDataSource
//	
//    @Bean
//    public DataSource dynamicDataSource() {
////    	DynamicDataSource dynamicDataSource = new DynamicDataSource();
//    	DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
//        Map<Object, Object> dataSourceMap = new HashMap<>();
////        dataSourceMap.put("tenant-1", dynamicDataSource.determineCurrentLookupKey());
//        dataSourceBuilder.driverClassName(driverClassName);
//        dataSourceBuilder.username(username);
//        dataSourceBuilder.password(password);
//        
////        dataSourceMap.put(dataSourceBuilder, dataSourceMap);
//        AbstractRoutingDataSource dataSource = new DynamicDataSource();
//        dataSource.setDefaultTargetDataSource(resolvedDataSources.get("tenant_1"));
//        dataSource.setTargetDataSources(resolvedDataSources);
//
//        
//        
//        if (!Objects.isNull(dataSource.determineCurrentLookupKey())) {
//        	url = url + dataSource.determineCurrentLookupKey();
//        	dataSourceBuilder.url(url);
//        	dataSourceMap.put(dynamicDataSource.determineCurrentLookupKey(), dataSourceBuilder.build());
//        	dynamicDataSource.setTargetDataSources(dataSourceMap);
//        }
//        else {
//        	dataSourceBuilder.url(defaultUrl);
//        	dataSourceMap.put("ums", dataSourceBuilder.build());
//        	dynamicDataSource.setDefaultTargetDataSource(dataSourceMap);
//        	dynamicDataSource.setTargetDataSources(dataSourceMap);
//        }
//        dataSource.afterPropertiesSet();
//        
//        return dynamicDataSource;
//    }
//}
