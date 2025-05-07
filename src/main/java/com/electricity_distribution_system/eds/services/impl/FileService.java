package com.electricity_distribution_system.eds.services.impl;

import com.electricity_distribution_system.eds.exceptions.ResourceNotFoundException;
import com.electricity_distribution_system.eds.models.File;
import com.electricity_distribution_system.eds.repositories.FileRepository;
import com.electricity_distribution_system.eds.services.IFileService;
import com.electricity_distribution_system.eds.services.standalone.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService implements IFileService {
 public final FileRepository fileRepository;
 public final FileStorageService fileStorageService;
    @Override
    public File getById(UUID id) {
        return this.fileRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("File", "id", id.toString()));
    }

    @Override
    public File create(File file) {
        return  fileRepository.save(file);
    }

    @Override
    public boolean delete(UUID id) {
        boolean exists = this.fileRepository.existsById(id);
        if (!exists)
            throw new ResourceNotFoundException("File", "id", id.toString());
         fileStorageService.removeFileOnDisk(this.getById(id).getPath());
         fileRepository.deleteById(id);
  return true;
    }

    @Override
    public String getFileExtension(File file) {
        return "";
    }

    @Override
    public String handleFileName(String fileName, UUID id) {
        return "";
    }

    @Override
    public boolean isValidExtension(String extension) {
        return false;
    }

    @Override
    public File getByName(String fileName) {
        return null;
    }
}
