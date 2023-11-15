package com.studentscheduleapp.imageservice.repos;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Repository
public class DriveRepo {

    public File get(String name) throws IOException {
        return null;
    }
    public String create(File file) throws IOException {
        return "adfhdfghsfh";
    }
    public boolean delete(String name) throws IOException {
        return true;
    }

}
