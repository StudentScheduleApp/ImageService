package com.studentscheduleapp.imageservice.services;

import com.studentscheduleapp.imageservice.repos.GoogleDriveRepo;
import com.studentscheduleapp.imageservice.repos.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class ImageService {


    @Value("${file.path}")
    private String path;

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private GoogleDriveRepo googleDriveRepo;

    public File get(String name) throws IOException {
        return googleDriveRepo.get(name);
    }

    public String create(MultipartFile file) throws IOException {
        return path + "/" + googleDriveRepo.create(file);
    }
    public void delete(String name) throws IOException {
        googleDriveRepo.delete(name);
    }
}
