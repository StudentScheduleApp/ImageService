package com.studentscheduleapp.imageservice.config;

import com.studentscheduleapp.imageservice.properties.GlobalProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class FileConfig {

    @Autowired
    private GlobalProperties globalProperties;

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofBytes(globalProperties.getMaxImageSize()));
        factory.setMaxRequestSize(DataSize.ofBytes(globalProperties.getMaxImageSize()));
        return factory.createMultipartConfig();
    }

}
