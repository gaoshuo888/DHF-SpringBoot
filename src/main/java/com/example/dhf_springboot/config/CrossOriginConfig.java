package com.example.dhf_springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * FileName: CrossOriginConfig.java
 * 类的详细说明
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/9
 */
@Configuration
public class CrossOriginConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//配置可以被跨域的路径，可以任意配置，可以具体到直接请求路径
                //（/** ，表示任意路径，表示对所有路径开放跨域请求）
                .allowedOrigins("http://localhost:8080") // 允许的前端地址
                //如果你部署到线上，记得换成前端的域名，如 "https://www.xxx.com"
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的请求方法
                .allowedHeaders("*") // 允许的请求头
                .allowCredentials(true); // 是否允许携带凭证
    }

}
