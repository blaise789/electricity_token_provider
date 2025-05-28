package com.vehicle_tracking.vehicle_tracking.services.impl;

import com.vehicle_tracking.vehicle_tracking.dtos.requests.UpdateUserDTO;
import com.vehicle_tracking.vehicle_tracking.enums.ERole;
import com.vehicle_tracking.vehicle_tracking.enums.EUserStatus;
import com.vehicle_tracking.vehicle_tracking.exceptions.BadRequestException;
import com.vehicle_tracking.vehicle_tracking.exceptions.ResourceNotFoundException;
import com.vehicle_tracking.vehicle_tracking.models.File;
import com.vehicle_tracking.vehicle_tracking.models.User;
import com.vehicle_tracking.vehicle_tracking.repositories.UserRepository;
import com.vehicle_tracking.vehicle_tracking.services.IFileService;
import com.vehicle_tracking.vehicle_tracking.services.IUserService;
import com.vehicle_tracking.vehicle_tracking.services.standalone.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
   private final IFileService fileService;
   private final FileStorageService fileStorageService;
  @Value("${uploads.directory.user_profiles}")
  private String userProfilesPath;
    @Override
    public Page<User> getAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    public User getById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

    }

    @Override
    public User create(User user) throws BadRequestException {
        System.out.println("here");
        try {
            Optional<User> userOptional = this.userRepository.findByEmail(user.getEmail());
            if (userOptional.isPresent())
                throw new BadRequestException(String.format("User with email '%s' already exists", user.getEmail()));
            return this.userRepository.save(user);

        } catch (DataIntegrityViolationException ex) {
            System.out.println(ex);
//            String errorMessage = Utility.getConstraintViolationMessage(ex, user);
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public User update(UUID id, UpdateUserDTO userDTO) throws BadRequestException {
        return null;
    }

    @Override
    public User save(User user) {
        try{
            return this.userRepository.save(user);
        }
        catch(DataIntegrityViolationException ex){
            throw new BadRequestException(ex.getMessage());
        }

    }

    @Override
    public Page<User> getAllByRole(Pageable pageable, ERole role) {
        return this.userRepository.findByRoleName(pageable,role);
    }


    @Override
    public Page<User> searchUser(Pageable pageable, String searchKey) {
        return  null;
//                this.userRepository.searchUser(pageable,searchKey);
    }

    @Override
    public User getLoggedInUser() {
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            email = ((UserDetails)principal).getUsername();
        }
        else{
            email = principal.toString();
        }
        return this.userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User","id",email));
    }

    @Override
    public User getByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User","email",email));
    }

    @Override
    public User changeStatus(UUID id, EUserStatus status) {
        User entity=this.userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id.toString()));
        entity.setStatus(status);
        return this.userRepository.save(entity);
    }

    @Override
        public User changeProfileImage(UUID id, MultipartFile document) {
            User entity=this.userRepository.findById(id).orElseThrow(
                    ()->new ResourceNotFoundException("User","id",id.toString())
            );
            File existingFile=entity.getProfileImage();
            if(existingFile!=null) {
                fileStorageService.removeFileOnDisk(existingFile.getPath());

            }
            File file=fileService.create(document,userProfilesPath);

      entity.setProfileImage(file);
      return this.userRepository.save(entity);
        }
    @Override
    public User removeProfileImage(UUID id) {
//    user exists
        User entity = this.userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id.toString())
        );
        File existingFile = entity.getProfileImage();
        if (existingFile != null) {

            this.fileService.delete(existingFile.getId());

        }
        entity.setProfileImage(null);
        return this.userRepository.save(entity);
    }



        @Override
        public Optional<User> findByActivationCode(String activationCode) {
            return null;


        }

    }


