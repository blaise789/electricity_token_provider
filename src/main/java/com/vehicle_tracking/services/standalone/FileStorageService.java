package com.vehicle_tracking.services.standalone;

import com.vehicle_tracking.exceptions.AppFailureException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@Service
@Configuration
public class FileStorageService {
    @Value("${uploads.directory}")
    private String root;

    @Value("${uploads.directory.user_profiles}")
    private String userProfilesFolder;

    @Value("${uploads.directory.docs}")
    private String docsFolder;


    @PostConstruct
    public void init() {
//        try {
//            create folder
//            Files.createDirectories(Paths.get(root, userProfilesFolder, docsFolder));
//        } catch (IOException e) {
//            throw new AppFailureException(e.getMessage());
//        }
    }

    public String save(MultipartFile file, String directory, String filename) {
        try {
            Path dirPath = Paths.get(directory);
            if (Files.notExists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            Path filePath = dirPath.resolve(Objects.requireNonNull(filename));

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return filePath.toString();
        } catch (IOException e) {
            throw new AppFailureException("Failed to save file: " + e.getMessage(), e);
        }
    }



    public UrlResource load(String uploadDirectory, String fileName) {
        Path path = Paths.get(uploadDirectory);

        try {
            Path file = path.resolve(fileName);
            UrlResource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void removeFileOnDisk(String filePath) {
        try {
            FileSystemUtils.deleteRecursively(Paths.get(filePath));
        } catch (IOException e) {
            throw new AppFailureException(e.getMessage());
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(root).toFile());
    }


    public Stream<Path> loadAll() {
        try {
            return Files.walk(Paths.get(this.root), 1).filter(path -> !path.equals(this.root)).map(Paths.get(this.root)::relativize);
        } catch (IOException e) {
            throw new AppFailureException(e.getMessage());
        }
    }

}
