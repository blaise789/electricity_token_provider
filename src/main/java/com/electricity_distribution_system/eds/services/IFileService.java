package com.electricity_distribution_system.eds.services;

import java.io.File;
import java.util.UUID;

public interface IFileService {

    File getById(UUID  id);
    File create(File file);
    boolean delete(UUID id);
    String getFileExtension(File file);
    String handleFileName(String fileName,UUID id);
    boolean isValidExtension(String extension);
    File getByName(String fileName);
}
