package com.studentscheduleapp.imageservice.services;

import org.springframework.stereotype.Service;

@Service
public class UrlService {

    public String getNameFromImageUrl(String url) {
        try {
            return url.substring(43, url.length() - 16);
        } catch (Exception e) {
            return "";
        }
    }

}
