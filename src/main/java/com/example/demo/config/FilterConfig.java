package com.example.demo.config;

import com.example.demo.filter.RequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author meow
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RequestFilter> loggingFilterRegistration() {
        FilterRegistrationBean<RequestFilter> registration = new FilterRegistrationBean<>();
        RequestFilter reqFilter = new RequestFilter();
        reqFilter.setIncludePayload(true);
        reqFilter.setIncludeQueryString(true);
        registration.setFilter(reqFilter);
        registration.addUrlPatterns("/*");
        registration.setName("reqFilter");
        registration.setOrder(1);
        return registration;
    }
}
