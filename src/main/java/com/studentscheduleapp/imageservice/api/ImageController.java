package com.studentscheduleapp.imageservice.api;

import com.studentscheduleapp.imageservice.services.ImageService;
import com.studentscheduleapp.imageservice.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.io.StringWriter;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private static final Logger log = LogManager.getLogger(ImageController.class);
    @Autowired
    private ImageService imageService;
    @Autowired
    private UrlService urlService;

    @PostMapping("${mapping.upload}")
    public ResponseEntity<String> upload(@RequestParam("image") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            log.warn("bad request: image is null or empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String url;
        try {
            url = imageService.create(file);
            if (url == null) {
                log.warn("upload failed: bad file");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (NullPointerException | StreamCorruptedException e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.warn("bad request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error("upload failed: " + errors);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        log.info("image " + url + " saved");
        return ResponseEntity.ok(url);
    }

    @DeleteMapping("${mapping.delete}")
    public ResponseEntity<Void> delete(@RequestParam("downloadUrl") String url) {
        try {
            imageService.delete(url);
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            if (e.getMessage().contains("404")) {
                log.warn("delete failed: image + " + url + " not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            log.warn("delete failed: " + errors);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        log.info("image " + url + " deleted");
        return ResponseEntity.ok().build();
    }

}