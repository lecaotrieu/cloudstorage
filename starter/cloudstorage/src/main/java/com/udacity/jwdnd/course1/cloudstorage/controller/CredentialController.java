package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("/upload/credential")
    public String uploadCredential(@ModelAttribute Credential credential) throws Exception {
        if (credential.getCredentialId() == null) {
            User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
            credential.setUserId(user.getUserId());
            credentialService.createCredential(credential);
        } else {
            credentialService.updateCredential(credential);
        }

        return "redirect:/result?success";
    }


    @PostMapping("/delete/credential/{credentialid}")
    public String deleteFile(@PathVariable("credentialid") Integer credentialId) throws Exception {
        credentialService.deleteCredential(credentialId);
        return "redirect:/result?success";
    }
}
