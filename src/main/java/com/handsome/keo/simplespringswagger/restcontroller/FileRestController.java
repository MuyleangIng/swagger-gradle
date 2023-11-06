package com.handsome.keo.simplespringswagger.restcontroller;

import com.handsome.keo.simplespringswagger.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/api/v1")

public class FileRestController {


    private final FileUploadService fileUploadService;
    private HttpServletRequest request;

   @Autowired
    public FileRestController(FileUploadService fileUploadService, HttpServletRequest request) {
        this.fileUploadService = fileUploadService;
        this.request = request;
    }

    @PostMapping(value = "/uploadSingleFile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadSingleFile(
            @RequestPart("file") MultipartFile file) throws IOException {

       String fileName = fileUploadService.uploadSingleFile(file);
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String scheme = request.getScheme();
        boolean addPort = !serverName.matches("[a-zA-Z]+") || serverName.equals("localhost");

        String imageUrl ;
        if(addPort){
            imageUrl = scheme +"://"+serverName+ ":"+serverPort + "/images/" + fileName;
        }else {
            imageUrl = scheme +"://"+serverName+ "/images/" + fileName;
        }

        return  imageUrl;
    }

    @PostMapping(value = "/uploadMultipleFiles",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public List<String> uploadMultipleFiles(

            @RequestPart("files") MultipartFile[] files) throws IOException {

       List<String> fileNames = fileUploadService.uploadMultipleFiles(files);
       List<String> fileResponse= new ArrayList<>();
       for(String fileName : fileNames){
           String serverName = request.getServerName();
           int serverPort = request.getServerPort();
           String scheme = request.getScheme();
           boolean addPort = !serverName.matches("[a-zA-Z]+") || serverName.equals("localhost");

           String imageUrl ;
           if(addPort){
               imageUrl = scheme +"://"+serverName+ ":"+serverPort + "/images/" + fileName;
           }else {
               imageUrl = scheme +"://"+serverName+ "/images/" + fileName;
           }
           fileResponse.add(imageUrl);

       }
        return fileResponse;
    }
}
