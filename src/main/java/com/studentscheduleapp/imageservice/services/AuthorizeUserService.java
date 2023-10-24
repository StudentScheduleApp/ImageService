package com.studentscheduleapp.imageservice.services;

import com.studentscheduleapp.imageservice.models.api.AuthorizeUserRequest;
import com.studentscheduleapp.imageservice.repos.AuthorizeUserRepository;
import com.studentscheduleapp.imageservice.repos.ServiceTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeUserService {
    @Autowired
    private AuthorizeUserRepository authorizeUserRepository;

    public boolean authorize(AuthorizeUserRequest authorizeUserRequest) throws Exception {
        return authorizeUserRepository.authorize(authorizeUserRequest);
    }

}
