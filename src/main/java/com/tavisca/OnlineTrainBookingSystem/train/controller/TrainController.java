package com.tavisca.OnlineTrainBookingSystem.train.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tavisca.OnlineTrainBookingSystem.train.model.Train;
import com.tavisca.OnlineTrainBookingSystem.train.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TrainController {
    @Autowired
    private TrainService trainService;

    @GetMapping(path = "/")
    public String hello() {
        return "Welcome to TrainBookingApp by Saqlain";
    }

    @GetMapping(path = "/trains")
    public ResponseEntity<List<Train>> getTrains() {
        if (!trainService.isEmpty())
            return new ResponseEntity<>(trainService.getTrains(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/train/{id}")
    public ResponseEntity<?> getTrainById(@PathVariable("id") int trainId) {
        if (trainService.getTrainById(trainId).isPresent())
            return new ResponseEntity<>(trainService.getTrainById(trainId), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/train")
    public ResponseEntity<?> addTrain(@RequestBody Train train) throws JsonProcessingException {
        trainService.addTrain(train);
        if ((trainService.getTrainById(train.getTrainNo()).isPresent()))
            return new ResponseEntity<>(train, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping(path = "/train/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int trainId, @RequestBody Train train) throws JsonProcessingException {
        if((trainService.getTrainById(trainId).isPresent())) {
            trainService.updateTrain(train);
            return new ResponseEntity<>(train, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/train/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int trainId) {
        if (trainService.getTrainById(trainId).isPresent()) {
            trainService.deleteTrain(trainId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}