package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileInfo;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UploadFileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

    private final UploadFileService uploadFileService;
    private final UserService userService;

    public FileController(UploadFileService uploadFileService, UserService userService) {
        this.uploadFileService = uploadFileService;
        this.userService = userService;
    }

    @PostMapping("/upload/file")
    public String uploadFile(@RequestParam(value = "fileUpload", required = false) MultipartFile image) throws Exception {
        if (image == null || image.isEmpty()) {
            return "redirect:/result?error";
        } else {
            User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
            FileInfo fileInfo = new FileInfo(null, image.getOriginalFilename(), image.getContentType(), image.getSize(), user.getUserId(), image.getBytes());
            uploadFileService.createFile(fileInfo);
        }
        return "redirect:/result?success";
    }

    @GetMapping("/images/{fileid}")
    public ResponseEntity<byte[]> getImage(@PathVariable("fileid") Integer fileId) {
        FileInfo fileInfo = uploadFileService.getFile(fileId);
        byte[] imageData = fileInfo.getFileData();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }

    @PostMapping("/delete/file/{fileid}")
    public String deleteFile(@PathVariable("fileid") Integer fileId) throws Exception {
        uploadFileService.deleteFile(fileId);
        return "redirect:/result?success";
    }
}
