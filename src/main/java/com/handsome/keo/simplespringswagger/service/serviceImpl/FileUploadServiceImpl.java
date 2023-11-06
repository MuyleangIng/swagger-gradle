package com.handsome.keo.simplespringswagger.service.serviceImpl;

import com.handsome.keo.simplespringswagger.service.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Value("${file.upload.directory}")
    private  String  UPLOAD_DIR;
    @Override
    public String uploadSingleFile(MultipartFile file) throws IOException{
        if(file.isEmpty()){
            throw new IllegalArgumentException("Please Select file in order to upload!");
        }

        System.out.println("File name is : "+file.getOriginalFilename());
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        System.out.println("UPLOAD DIR : "+UPLOAD_DIR);
        Path uploadPath = Path.of(UPLOAD_DIR);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);

        }

        try{
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        }catch (IOException ex ){
            throw new IOException("Failed to store file : "+fileName ,ex );
        }
        return fileName;
    }

    @Override
    public List<String> uploadMultipleFiles(MultipartFile[] files) throws IOException {
            List<String> fileNames = new ArrayList<>();
            for(MultipartFile file : files ){
                if(file.isEmpty()){
                    continue;
                }



                String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                Path uploadPath = Path.of(UPLOAD_DIR);

                try{
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(file.getInputStream(),filePath,  StandardCopyOption.REPLACE_EXISTING);
                    fileNames.add(fileName);
                }catch (IOException ex ){
                    throw new IOException("Failed to store file " + fileName, ex);

                }
            }

        return fileNames;
    }
}
