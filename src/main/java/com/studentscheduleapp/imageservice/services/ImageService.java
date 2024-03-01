package com.studentscheduleapp.imageservice.services;

import com.studentscheduleapp.imageservice.repos.DriveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    @Autowired
    private DriveRepo driveRepo;

    public String create(MultipartFile file) throws Exception {
        if (file.getContentType() != null && !file.getContentType().split("/")[0].equals("image") || file.getContentType() == null)
            return null;
        return driveRepo.create(file);
    }

    public void delete(String url) throws Exception {
        driveRepo.delete(url);
    }
}
