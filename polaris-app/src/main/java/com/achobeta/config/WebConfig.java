package com.achobeta.config;

import com.achobeta.intercepter.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author BanTanger 半糖
 * @date 2024/11/4
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 所有的请求都加上 Log 拦截器
        registry.addInterceptor(new LogInterceptor());
    }

}
