package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final HashService hashService;
    public CredentialService(CredentialMapper credentialMapper, HashService hashService) {
        this.credentialMapper = credentialMapper;
        this.hashService = hashService;
    }

    public void createCredential(Credential credential) throws Exception {
        credential.setPassword(Base64.getEncoder().encodeToString(credential.getPassword().getBytes()));
        credentialMapper.insert(credential);
    }

    public List<Credential> getCredentials(Integer userId) {
        return credentialMapper.getCredentials(userId);
    }

    public Credential getCredential(Integer credentialId) {
        return credentialMapper.getCredential(credentialId);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }

    public void updateCredential(Credential credential) {
        credential.setPassword(Base64.getEncoder().encodeToString(credential.getPassword().getBytes()));
        credentialMapper.update(credential);
    }
}
