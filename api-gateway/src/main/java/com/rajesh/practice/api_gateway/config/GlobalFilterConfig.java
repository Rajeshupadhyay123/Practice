package com.rajesh.practice.api_gateway.config;

import com.rajesh.practice.api_gateway.filter.AuthenticationFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalFilterConfig {

    private final Logger logger = LogManager.getLogger(GlobalFilterConfig.class);

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean
    public GlobalFilter authenticationGlobalFilter(){
        logger.info("Global Filter is calling");
        return ((exchange, chain) -> {

            return authenticationFilter.apply(new AuthenticationFilter.Config())
                    .filter(exchange,chain);
        });
    }
}
