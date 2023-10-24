package com.studentscheduleapp.imageservice.repos;

import com.studentscheduleapp.imageservice.models.api.AuthorizeEntity;
import com.studentscheduleapp.imageservice.models.api.AuthorizeServiceRequest;
import com.studentscheduleapp.imageservice.models.api.AuthorizeType;
import com.studentscheduleapp.imageservice.models.api.AuthorizeUserRequest;
import jdk.internal.agent.resources.agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class AuthorizeUserRepository {

    @Value("${ip.identityservice}")
    private String identityService;

    @Autowired
    private RestTemplate restTemplate;

    public boolean authorize(AuthorizeUserRequest authorizeServiceRequest) throws Exception{
        ResponseEntity<Void> r = restTemplate.postForEntity(identityService + "/api/user/authorize", authorizeServiceRequest, Void.class);
        if(r.getStatusCode().is2xxSuccessful())
            return true;
        if(r.getStatusCode().equals(HttpStatus.NOT_FOUND))
            return false;
        throw new Exception("request to " + identityService + " return code " + r.getStatusCode());
    }
}
