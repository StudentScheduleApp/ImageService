package com.studentscheduleapp.imageservice.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Repository
public class DriveRepo {

    @Value("${ip.driveservice}")
    private String driveService;

    @Autowired
    private RestTemplate restTemplate;

    public Byte[] get(String name) throws Exception {
        ResponseEntity<Byte[]> r = restTemplate.getForEntity(driveService + "/api/download/" + name, Byte[].class);
        if(r.getStatusCode().is2xxSuccessful())
            return r.getBody();
        if(r.getStatusCode().equals(HttpStatus.NOT_FOUND))
            return null;
        throw new Exception("request to " + driveService + " return code " + r.getStatusCode());
    }
    public String create(Byte[] file) throws Exception {
        ResponseEntity<String> r = restTemplate.postForEntity(driveService + "/api/upload", file, String.class);
        if(r.getStatusCode().is2xxSuccessful())
            return r.getBody();
        throw new Exception("request to " + driveService + " return code " + r.getStatusCode());
    }
    public boolean delete(String name) throws Exception {
        ResponseEntity<Void> r = restTemplate.exchange(driveService + "/api/" + name, HttpMethod.DELETE, null, Void.class);
        if(r.getStatusCode().is2xxSuccessful())
            return true;
        throw new Exception("request to " + driveService + " return code " + r.getStatusCode());
    }

}
