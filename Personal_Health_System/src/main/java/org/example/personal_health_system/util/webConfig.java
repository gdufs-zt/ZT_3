package org.example.personal_health_system.util;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//全局配置类，配置跨域请求

@Configuration  //说明是全局配置类
public class webConfig implements  WebMvcConfigurer {
    /**
     * 1.访问路径
     * 2、请求来源
     * 3、方法
     * 4、允许携带
     * 5、最大时间
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("Http://localhost:8080")
                .allowedMethods("GET","POST","PUT","OPTIONS","DELETE")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
