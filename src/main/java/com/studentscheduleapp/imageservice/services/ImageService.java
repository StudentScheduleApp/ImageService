package com.studentscheduleapp.imageservice.services;

import com.studentscheduleapp.imageservice.repos.DriveRepo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.misc.FieldUtil;

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

    public Byte[] get(String name) throws Exception {
        return driveRepo.get(name.substring(0, name.length() - 4));
    }

    public String create(MultipartFile file) throws Exception {
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
        byte[] bs = FileUtils.readFileToByteArray(f);
        Byte[] Bs = new Byte[bs.length];
        for (int i = 0; i < bs.length; i++){
            Bs[i] = bs[i];
        }
        return path + "/" + driveRepo.create(Bs) + ".jpg";
    }
    public void delete(String name) throws Exception {
        driveRepo.delete(name.substring(0, name.length() - 4));
    }
}
