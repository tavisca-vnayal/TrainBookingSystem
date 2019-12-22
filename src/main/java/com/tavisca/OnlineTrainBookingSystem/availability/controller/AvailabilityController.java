package com.tavisca.OnlineTrainBookingSystem.availability.controller;

import com.tavisca.OnlineTrainBookingSystem.availability.service.AvailabilityService;
import com.tavisca.OnlineTrainBookingSystem.train.model.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AvailabilityController {
    @Autowired
    AvailabilityService availabilityService;

    @PostMapping("/availability/{trainNo}")
    public ResponseEntity<String> getAvailability
            (@PathVariable("trainNo") int trainNo, @RequestBody SearchForm searchForm){
        return new ResponseEntity<>
                (availabilityService.getAvailability(trainNo, searchForm).get(), HttpStatus.OK);
    }

}
