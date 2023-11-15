package com.studentscheduleapp.imageservice.services;

import com.studentscheduleapp.imageservice.repos.DriveRepo;
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


    @Value("${file.path}")
    private String path;
    @Autowired
    private DriveRepo driveRepo;

    public File get(String name) throws IOException {
        return driveRepo.get(name.substring(0, name.length() - 4));
    }

    public String create(MultipartFile file) throws IOException {
        File f = new File("mpf_to_f");
        FileOutputStream outputStream = new FileOutputStream(f);
        outputStream.write(file.getBytes());
        outputStream.close();
        final FileInputStream fileInputStream = new FileInputStream(f);
        final BufferedImage image = ImageIO.read(fileInputStream);
        fileInputStream.close();

        final BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        convertedImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);

        File out = new File("f_to_jpg");
        final FileOutputStream fileOutputStream = new FileOutputStream(out);
        ImageIO.write(convertedImage, "jpg", fileOutputStream);
        fileOutputStream.close();
        if (f.exists())
            f.delete();
        if (out.exists())
            out.delete();
        return path + "/" + driveRepo.create(out) + ".jpg";
    }
    public void delete(String name) throws IOException {
        driveRepo.delete(name);
    }
}
