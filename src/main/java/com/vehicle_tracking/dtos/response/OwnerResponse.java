package com.vehicle_tracking.dtos.response;
import java.util.List;

public class OwnerResponse {
    private Long id;
    private String names;
    private String nationalId;
    private String phoneNumber;
    private String address;
    private List<PlateNumberResponse> plateNumbers;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<PlateNumberResponse> getPlateNumbers() {
        return plateNumbers;
    }

    public void setPlateNumbers(List<PlateNumberResponse> plateNumbers) {
        this.plateNumbers = plateNumbers;
    }
}