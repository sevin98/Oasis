package com.ssafy.oasis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.ssafy.oasis.model.dao")
public class DBConfig {

}
