package com.example.maynard.tradition.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.*;
import com.example.maynard.tradition.Interceptor.*;

/**
 * @author zhangyu
 */
@Slf4j
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.example.maynard.tradition.controller")
@PropertySource(value = "classpath:application.yaml", ignoreResourceNotFound = true, encoding = "UTF-8")
public class MvcConfig implements WebMvcConfigurer {


    @Bean
    SecurityInterceptor securityInterceptor() {
        return new SecurityInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截器按照顺序执行
        registry.addInterceptor(securityInterceptor())
                .addPathPatterns("/api/message/**")
                .addPathPatterns("/api/server/**")
                .excludePathPatterns("/api/user/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

}
