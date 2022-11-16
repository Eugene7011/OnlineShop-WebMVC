package com.podzirei.onlineshop.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = {"com.podzirei.onlineshop"},
        excludeFilters = {@ComponentScan.Filter(
                type = FilterType.REGEX,
                pattern = "com\\.podzirei\\.onlineshop\\.web\\.*")})
public class RootConfig {
}
