package com.handsome.keo.simplespringswagger.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;



public interface FileUploadService {
    String uploadSingleFile(MultipartFile file ) throws IOException;
    List<String> uploadMultipleFiles(MultipartFile[] files) throws IOException;
}
