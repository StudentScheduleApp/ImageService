package com.studentscheduleapp.imageservice.services;

import com.studentscheduleapp.imageservice.repos.DriveRepo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class ImageService {

    @Autowired
    private DriveRepo driveRepo;

    public String create(MultipartFile file) throws Exception {
        if (!file.getContentType().split("/")[0].equals("image"))
            return null;
        return driveRepo.create(file);
    }
    public void delete(String name) throws Exception {
        driveRepo.delete(name);
    }
}
