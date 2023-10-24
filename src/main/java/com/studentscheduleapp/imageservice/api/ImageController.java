package com.studentscheduleapp.imageservice.api;

import com.studentscheduleapp.imageservice.models.api.*;
import com.studentscheduleapp.imageservice.services.AuthorizeUserService;
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
import java.util.Collections;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class ImageController {

    @Autowired
    private ImageService imageService;
    @Autowired
    private AuthorizeUserService authorizeUserService;

    @PostMapping("upload")
    public ResponseEntity<String> upload(@RequestParam("image") MultipartFile file, @RequestBody AuthUserCreds authUserCreds) {
        try {
            if(!authorizeUserService.authorize(new AuthorizeUserRequest(authUserCreds.getToken(), Collections.singletonList(new AuthorizeEntity(AuthorizeType.CREATE, Collections.singletonList(authUserCreds.getGroupId()), Entity.IMAGE, null)))))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (file == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        String url = "";
        try {
            url = imageService.create(file);
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(url);
    }

    @GetMapping("{name}")
    public ResponseEntity<Byte[]> download(@PathVariable("name") String name){
        File f = null;
        try {
            f = imageService.get(name);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if(f != null){
            try {
                byte[] bs = FileUtils.readFileToByteArray(f);
                Byte[] Bs = new Byte[bs.length];
                for (int i = 0; i < bs.length; i++){
                    Bs[i] = bs[i];
                }
                return ResponseEntity.ok(Bs);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("{name}")
    public ResponseEntity<Void> delete(@PathVariable("name") String name, @RequestBody AuthUserCreds authUserCreds) {
        try {
            if(!authorizeUserService.authorize(new AuthorizeUserRequest(authUserCreds.getToken(), Collections.singletonList(new AuthorizeEntity(AuthorizeType.CREATE, Collections.singletonList(authUserCreds.getGroupId()), Entity.IMAGE, null)))))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            imageService.delete(name);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }

}