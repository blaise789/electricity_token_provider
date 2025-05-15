package com.electricity_distribution_system.eds.services;

import com.electricity_distribution_system.eds.models.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IFileService {

    File getById(UUID  id);
    File create(MultipartFile file,String directory);
    boolean delete(UUID id);
    String getFileExtension(String fileName);
    String handleFileName(String fileName,UUID id);
    boolean isValidExtension(String extension);
    File getByName(String fileName);
}
