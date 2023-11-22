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
import java.util.logging.Logger;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("upload")
    public ResponseEntity<String> upload(@RequestParam("image") MultipartFile file) {
        if (file == null) {
            Logger.getGlobal().info("bad request: image is null or empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String url = "";
        try {
            url = imageService.create(file);
        } catch (NullPointerException e){
            Logger.getGlobal().info("bad request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            Logger.getGlobal().info("upload failed:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        Logger.getGlobal().info("image " + url + " saved successful");
        return ResponseEntity.ok(url);
    }

    @GetMapping("{name}")
    public ResponseEntity<Byte[]> download(@PathVariable("name") String name){
        Byte[] f = null;
        try {
            f = imageService.get(name);
        } catch (Exception e) {
            Logger.getGlobal().info("download failed:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if (f == null) {
            Logger.getGlobal().info("not found: image " + name + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Logger.getGlobal().info("image " + name + " send successful");
        return ResponseEntity.ok(f);
    }

    @DeleteMapping("{name}")
    public ResponseEntity<Void> delete(@PathVariable("name") String name){
        try {
            imageService.delete(name);
        } catch (Exception e) {
            Logger.getGlobal().info("delete failed:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        Logger.getGlobal().info("image " + name + " deleted successful");
        return ResponseEntity.ok().build();
    }

}