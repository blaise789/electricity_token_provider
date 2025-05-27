package com.vehicle_tracking.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {
    String action(); // e.g., "REGISTER_OWNER", "LOGIN_ATTEMPT", "TRANSFER_VEHICLE"
}