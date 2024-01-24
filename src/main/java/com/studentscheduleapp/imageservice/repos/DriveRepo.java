package com.studentscheduleapp.imageservice.repos;

import com.studentscheduleapp.imageservice.properties.services.DriveServiceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class DriveRepo {

    @Autowired
    private DriveServiceProperties driveServiceProperties;

    @Autowired
    private RestTemplate restTemplate;

    public String create(MultipartFile file) throws Exception {
        ResponseEntity<String> r = restTemplate.postForEntity(driveServiceProperties.getUri() + driveServiceProperties.getUploadPath(), file, String.class);
        if(r.getStatusCode().is2xxSuccessful())
            return r.getBody();
        throw new Exception("request to " + driveServiceProperties.getUri() + " return code " + r.getStatusCode());
    }
    public boolean delete(String name) throws Exception {
        ResponseEntity<Void> r = restTemplate.exchange(driveServiceProperties.getUri() + driveServiceProperties.getDeletePath() + "/" + name, HttpMethod.DELETE, null, Void.class);
        if(r.getStatusCode().is2xxSuccessful())
            return true;
        throw new Exception("request to " + driveServiceProperties.getUri() + " return code " + r.getStatusCode());
    }

}
