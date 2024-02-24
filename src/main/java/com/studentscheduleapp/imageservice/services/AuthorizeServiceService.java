package com.studentscheduleapp.imageservice.services;

import com.studentscheduleapp.imageservice.properties.GlobalProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeServiceService {
    @Autowired
    private GlobalProperties globalProperties;

    public boolean authorize(String token){
        return globalProperties.getServiceToken().equals(token);
    }

}
