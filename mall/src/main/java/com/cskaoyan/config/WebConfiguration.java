package com.cskaoyan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description
 * @Author Xrw
 * @Date 2022/7/14 20:03
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * 文件上传组件注册，该组件id不允许被修改
     *
     * @return org.springframework.web.multipart.MultipartResolver
     * @author fanxing056
     * @date 2022/07/17 16:36
     */
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        return commonsMultipartResolver;
    }
}
