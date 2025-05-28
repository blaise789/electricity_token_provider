package com.vehicle_tracking.vehicle_tracking.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@Getter
@Setter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    private String resourceName;
    private String fieldName;
    private Object fieldValue;


    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s with %s ['%s'] not found", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}