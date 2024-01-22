package com.studentscheduleapp.imageservice.properties.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class DriveServiceProperties {

    @Value("${driveservice.uri}")
    private String uri;
    @Value("${driveservice.path.upload}")
    private String uploadPath;
    @Value("${driveservice.path.delete}")
    private String deletePath;
}
