package com.vehicle_tracking.vehicle_tracking.mappers;

import com.vehicle_tracking.vehicle_tracking.dtos.requests.OwnerRequest;
import com.vehicle_tracking.vehicle_tracking.dtos.response.OwnerResponseDTO;
import com.vehicle_tracking.vehicle_tracking.models.Owner;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper {

    public static Owner toEntity(OwnerRequest ownerRequest) {
        Owner owner = new Owner();
        owner.setAddress(ownerRequest.getAddress());
        owner.setNames(ownerRequest.getNames());
        owner.setEmail(ownerRequest.getEmail());
        owner.setNationalId(ownerRequest.getNationalId());
        owner.setPhone(ownerRequest.getPhoneNumber());
        owner.setAddress(ownerRequest.getAddress());

        return owner;
    }

    public static OwnerResponseDTO mapToOwnerResponseDTO( Owner owner){

        return OwnerResponseDTO.builder()
                .id(owner.getId())
                .names(owner.getNames())
                .phoneNumber(owner.getPhone())
                .nationalId(owner.getNationalId())
                .address(owner.getAddress())
                .build();
    }


}