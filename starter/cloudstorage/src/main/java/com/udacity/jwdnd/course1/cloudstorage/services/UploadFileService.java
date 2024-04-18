package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileInfo;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadFileService {
    private final FileMapper fileMapper;

    public UploadFileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public void createFile(FileInfo fileInfo) throws Exception {
        fileMapper.insert(fileInfo);
    }

    public List<FileInfo> getFiles(Integer userId) {
        return fileMapper.getFiles(userId);
    }

    public FileInfo getFile(Integer fileId) {
        return fileMapper.getFile(fileId);
    }

    public void deleteFile(Integer fileId) {
        fileMapper.deleteFile(fileId);
    }

}
