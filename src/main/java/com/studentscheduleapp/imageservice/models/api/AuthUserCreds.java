package com.studentscheduleapp.imageservice.models.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserCreds {
    private String token;
    private Long groupId;
}
