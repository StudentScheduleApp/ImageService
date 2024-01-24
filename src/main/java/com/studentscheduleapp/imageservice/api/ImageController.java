package com.studentscheduleapp.imageservice.api;

import com.studentscheduleapp.imageservice.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("${mapping.upload}")
    public ResponseEntity<String> upload(@RequestParam("image") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            Logger.getGlobal().info("bad request: image is null or empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String url = "";
        try {
            url = imageService.create(file);
        } catch (NullPointerException | StreamCorruptedException e){
            Logger.getGlobal().info("bad request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            Logger.getGlobal().info("upload failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        Logger.getGlobal().info("image " + url + " saved successful");
        return ResponseEntity.ok(url);
    }

    @DeleteMapping("${mapping.delete}/{name}")
    public ResponseEntity<Void> delete(@PathVariable("name") String name){
        try {
            imageService.delete(name);
        } catch (Exception e) {
            Logger.getGlobal().info("delete failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        Logger.getGlobal().info("image " + name + " deleted successful");
        return ResponseEntity.ok().build();
    }

}