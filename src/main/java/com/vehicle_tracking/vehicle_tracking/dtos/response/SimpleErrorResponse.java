package com.vehicle_tracking.vehicle_tracking.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all arguments
public class SimpleErrorResponse {

    private boolean success;
    private String message;

    // Optional: You can add more fields if needed for specific error scenarios,
    // for example, a custom error code or more detailed information.
    // private String errorCode;
    // private Object details;

    /**
     * Convenience static factory method to create an error response.
     * @param message The error message.
     * @return A new SimpleErrorResponse instance.
     */
    public static SimpleErrorResponse error(String message) {
        return new SimpleErrorResponse(false, message);
    }

    /**
     * Convenience static factory method to create a success response (though less common for this DTO).
     * @param message The success message.
     * @return A new SimpleErrorResponse instance.
     */
    public static SimpleErrorResponse success(String message) {
        return new SimpleErrorResponse(true, message);
    }
}