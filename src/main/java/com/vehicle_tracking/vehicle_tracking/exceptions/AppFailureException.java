package com.vehicle_tracking.vehicle_tracking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AppFailureException extends RuntimeException {
    public AppFailureException(String message) {
        super(message);
    }
    public AppFailureException(String message, Throwable cause) {
      super(message, cause);
    }
}

