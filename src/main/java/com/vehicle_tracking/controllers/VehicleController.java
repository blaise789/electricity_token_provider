package com.vehicle_tracking.controllers;

import com.vehicle_tracking.models.Vehicle;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles/")
public class VehicleController {
@PostMapping("")
public String createVehicle(@RequestBody Vehicle vehicle) {
  return "Vehicle created";
}

@GetMapping(

)
  public List<Vehicle> getAllVehicles() {
    return null;
}

@GetMapping("/transfer/")
    public  String transferVehicle(){
    return null;
}





    
}
