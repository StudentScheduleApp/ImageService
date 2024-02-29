package com.studentscheduleapp.imageservice.api;

import com.studentscheduleapp.imageservice.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
                log.error("upload failed: bad file");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (NullPointerException | StreamCorruptedException e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.warn("bad request: " + errors);
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

    @DeleteMapping("${mapping.delete}/{name}")
    public ResponseEntity<Void> delete(@PathVariable("name") String name) {
        try {
            imageService.delete(name);
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error("delete failed: " + errors);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        log.info("image " + name + " deleted");
        return ResponseEntity.ok().build();
    }

}