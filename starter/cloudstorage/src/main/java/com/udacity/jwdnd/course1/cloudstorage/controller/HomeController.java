package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.FileInfo;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UploadFileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping({"/home", "/"})
public class HomeController {


    private final UploadFileService uploadFileService;
    private final UserService userService;
    private final CredentialService credentialService;

    private final NoteService noteService;

    public HomeController(UploadFileService uploadFileService, UserService userService, CredentialService credentialService, NoteService noteService) {
        this.uploadFileService = uploadFileService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.noteService = noteService;
    }

    @GetMapping()
    public String getHomePage(Model model) {
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        List<FileInfo> fileInfos = uploadFileService.getFiles(user.getUserId());
        List<Note> notes = noteService.getNotes(user.getUserId());
        List<Credential> credentials = credentialService.getCredentials(user.getUserId());
        model.addAttribute("fileInfos", fileInfos);
        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);
        return "home";
    }



}
