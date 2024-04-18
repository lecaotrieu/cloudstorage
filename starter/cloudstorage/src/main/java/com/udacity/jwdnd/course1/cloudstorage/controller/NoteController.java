package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileInfo;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
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
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/upload/note")
    public String uploadNote(@ModelAttribute Note note) throws Exception {
        if (note.getNoteId() == null) {
            User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
            note.setUserId(user.getUserId());
            noteService.createNote(note);

        } else {
            noteService.updateNote(note);
        }

        return "redirect:/result?success";
    }


    @PostMapping("/delete/note/{noteid}")
    public String deleteFile(@PathVariable("noteid") Integer noteId) throws Exception {
        noteService.deleteNote(noteId);
        return "redirect:/result?success";
    }
}
