package com.studentscheduleapp.imageservice.repos;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public interface StorageRepo {
    File get(String name) throws IOException;
    String create(MultipartFile file) throws IOException;
    boolean delete(String name) throws IOException;
}
