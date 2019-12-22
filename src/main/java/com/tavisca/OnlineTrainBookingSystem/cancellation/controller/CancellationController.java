package com.tavisca.OnlineTrainBookingSystem.cancellation.controller;

import com.tavisca.OnlineTrainBookingSystem.availability.service.AvailabilityService;
import com.tavisca.OnlineTrainBookingSystem.cancellation.service.CancellationService;
import com.tavisca.OnlineTrainBookingSystem.train.model.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CancellationController {
    @Autowired
    CancellationService cancellationService;

    @PutMapping("/cancellation/{pnr}")
    public ResponseEntity<String> cancelTicket
            (@PathVariable("pnr") int pnr){
        return new ResponseEntity<>
                (cancellationService.cancelTicket(pnr), HttpStatus.OK);
    }

}
