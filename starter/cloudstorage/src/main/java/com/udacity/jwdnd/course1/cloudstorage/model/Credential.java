package com.udacity.jwdnd.course1.cloudstorage.model;

import java.util.Base64;

public class Credential {
    private Integer credentialId;
    private String url;
    private String key;
    private String username;
    private String password;
    private Integer userId;

    public Credential(Integer credentialId, String url, String key, String username, String password, Integer userId) {
        this.credentialId = credentialId;
        this.url = url;
        this.key = key;
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPasswordDecode() {
        byte[] decodedBytes = Base64.getDecoder().decode(password);
        return new String(decodedBytes);
    }
}
