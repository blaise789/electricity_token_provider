package com.electricity_distribution_system.eds.controllers;

import com.electricity_distribution_system.eds.models.File;
import com.electricity_distribution_system.eds.services.IFileService;
import com.electricity_distribution_system.eds.services.impl.FileService;
import com.electricity_distribution_system.eds.services.standalone.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.mail.Multipart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final IFileService fileService;
    private final FileStorageService  fileStorageService;
    @Value("${uploads.directory.user_profiles}")
    private String directory;
    //    get file
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<UrlResource> loadProfileImage(@PathVariable String filename) {


        UrlResource file = this.fileStorageService.load(directory, filename);
        File _file = this.fileService.getByName(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, _file.getType())
                .body(file);

    }

    @PostMapping("/upload")
    @Operation(summary = "upload",description = "Upload a file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
        {
             return ResponseEntity.ok(fileStorageService.save(file,"uploads",file.getOriginalFilename())) ;

        }
    }

}
