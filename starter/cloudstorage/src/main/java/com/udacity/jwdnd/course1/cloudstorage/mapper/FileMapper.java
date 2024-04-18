package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.FileInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<FileInfo> getFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    FileInfo getFile(Integer fileId);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    void deleteFile(Integer fileId);
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(FileInfo fileInfo);
}
