package com.studentscheduleapp.imageservice.repos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.*;

@Repository
public class ImageRepository {



    @Value("${file.root}")
    private String root;

    public File get(String name) throws IOException {
        File f = new File(root + "/" + name);
        if (!f.exists())
            return null;
        return f;
    }

    public String create(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty() && ImageIO.read(file.getInputStream()) != null) {
            try {
                if(!new File(root).exists())
                    new File(root).mkdir();
                File[] listOfFiles = new File(root).listFiles();

                long id = -1;
                do{
                    id = Math.round(Math.random()*1000000000);
                    for (int i = 0; i < listOfFiles.length; i++) {
                        File f = listOfFiles[i];
                        if (f.isFile()) {
                            if(f.getName().equals(String.valueOf(id)))
                                id = -1;
                        }
                    }
                } while (id == -1);
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(root + "/" + id)));
                stream.write(bytes);
                stream.close();
                return String.valueOf(id);
            } catch (Exception e) {
                throw new IOException(e);
            }
        } else {
            throw new NullPointerException();
        }
    }
    public void delete(String name) throws IOException {
        File f = new File(root + "/" + name);
        if (f.exists())
            f.delete();
    }


}
