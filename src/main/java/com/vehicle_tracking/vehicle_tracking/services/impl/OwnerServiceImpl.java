package com.vehicle_tracking.vehicle_tracking.services.impl;

import com.vehicle_tracking.vehicle_tracking.dtos.requests.OwnerRequest;
import com.vehicle_tracking.vehicle_tracking.dtos.response.OwnerResponseDTO;
import com.vehicle_tracking.vehicle_tracking.exceptions.ResourceAlreadyExistsException;
import com.vehicle_tracking.vehicle_tracking.mappers.OwnerMapper;
import com.vehicle_tracking.vehicle_tracking.models.Owner;
import com.vehicle_tracking.vehicle_tracking.repositories.OwnerRepository;
import com.vehicle_tracking.vehicle_tracking.repositories.UserRepository;
import com.vehicle_tracking.vehicle_tracking.services.IOwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OwnerServiceImpl implements IOwnerService {
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;


    @Override
    public OwnerResponseDTO createOwner(OwnerRequest ownerRequest) {
//        owner exists as User

        Optional<Owner> owner=ownerRepository.findByEmailAndNationalId(ownerRequest.getEmail(), ownerRequest.getNationalId());
        if(owner.isPresent()){
            throw new ResourceAlreadyExistsException("Owner already exists");
        }

        Owner newOwner=OwnerMapper.toEntity(ownerRequest);
        log.info(newOwner.getEmail());
        Owner ownerSaved=ownerRepository.save(newOwner);
        return OwnerMapper.mapToOwnerResponseDTO(ownerSaved);

    }

    @Override
    public void deleteOwner(Long id) {

    }

    @Override
    public OwnerResponseDTO updateOwner(Long id, OwnerRequest ownerRequest) {
        return null;
    }


//    @Override
//    public Page<OwnerResponse> searchOwners(String nationalId, String email, String phone, Pageable pageable) {
// return null;
//    }

    @Override
    public Page<Owner> searchOwners(Pageable pageable,String nationalId,String email,String phoneNumber) {

        return null;
    }

    @Override
    public Owner getOwnerById(Long id) {
        return ownerRepository.findById(id).orElseThrow(()->new RuntimeException("owner not found"));
    }
//    mapping request to entity


}

