package com.podzirei.onlineshop.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:application.properties"})
@ComponentScan(basePackages = {"com.podzirei.onlineshop"}, excludeFilters = {
        @ComponentScan.Filter(
                type = FilterType.REGEX,
                pattern = "com\\.podzirei\\.onlineshop\\.web..*")
})
public class RootConfig {
}
