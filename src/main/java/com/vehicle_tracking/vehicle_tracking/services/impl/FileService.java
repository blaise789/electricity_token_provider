package com.vehicle_tracking.vehicle_tracking.services.impl;

import com.vehicle_tracking.vehicle_tracking.enums.EFileSizeType;
import com.vehicle_tracking.vehicle_tracking.enums.EFileStatus;
import com.vehicle_tracking.vehicle_tracking.exceptions.AppFailureException;
import com.vehicle_tracking.vehicle_tracking.exceptions.BadRequestException;
import com.vehicle_tracking.vehicle_tracking.exceptions.ResourceNotFoundException;
import com.vehicle_tracking.vehicle_tracking.models.File;
import com.vehicle_tracking.vehicle_tracking.repositories.FileRepository;
import com.vehicle_tracking.vehicle_tracking.services.IFileService;
import com.vehicle_tracking.vehicle_tracking.services.standalone.FileStorageService;
import com.vehicle_tracking.vehicle_tracking.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService implements IFileService {
 public final FileRepository fileRepository;
 public final FileStorageService fileStorageService;
    @Value("${uploads.extensions}")
    private String extensions;
    @Override
    public File getById(UUID id) {
        return this.fileRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("File", "id", id.toString()));
    }

    @Override
    public File create(MultipartFile document,String directory) {
        File file = new File();
        file.setStatus(EFileStatus.PENDING);
        String fileName = FileUtil.generateUUID(Objects.requireNonNull(document.getOriginalFilename()));
        String updatedFileName = this.handleFileName(fileName, UUID.randomUUID());
        EFileSizeType sizeType = FileUtil.getFileSizeTypeFromFileSize(file.getSize());
        int size = FileUtil.getFormattedFileSizeFromFileSize(document.getSize(), sizeType);
        file.setName(updatedFileName);
        file.setPath(fileStorageService.save(document, directory, updatedFileName));
        file.setStatus(EFileStatus.SAVED);
        file.setType(document.getContentType());
        file.setSize(size);
        file.setSizeType(sizeType);
 return this.fileRepository.save(file);


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
    public String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex < 0) {
            return null;
        }
        return fileName.substring(dotIndex + 1);
    }

    @Override
    public String handleFileName(String fileName, UUID id) {
        String cleanFileName = fileName.replaceAll("[^A-Za-z0-9.()]", "");
        String extension = getFileExtension(cleanFileName);

        if (!isValidExtension(cleanFileName)) {
            throw new BadRequestException("Invalid File Extension");
        }

        String base = "image-" + id;

        cleanFileName = base + "." + extension;

        return cleanFileName;
    }

    @Override
    public boolean isValidExtension(String fileName) {
        String fileExtension = getFileExtension(fileName);

        if (fileExtension == null) {
            throw new AppFailureException("No File Extension");
        }

        fileExtension = fileExtension.toLowerCase();

        for (String validExtension : extensions.split(",")) {
            if (fileExtension.equals(validExtension)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public File getByName(String fileName) {
        return this.fileRepository.getFileByName(fileName).orElseThrow(()-> new ResourceNotFoundException("File", "name", fileName));
    }
}
