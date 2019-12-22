package com.tavisca.OnlineTrainBookingSystem.train.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tavisca.OnlineTrainBookingSystem.route.model.Route;
import com.tavisca.OnlineTrainBookingSystem.train.model.SearchForm;
import com.tavisca.OnlineTrainBookingSystem.train.model.Train;
import com.tavisca.OnlineTrainBookingSystem.train.service.TrainService;
import com.tavisca.OnlineTrainBookingSystem.route.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TrainController {
    @Autowired
    private TrainService trainService;
    @Autowired
    private RouteService routeService;

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
    public ResponseEntity<?> addTrain(@RequestBody Train train)  {
        trainService.addTrain(train);
        if ((trainService.getTrainById(train.getTrainNo()).isPresent()))
            return new ResponseEntity<>(train, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping(path = "/train/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int trainId, @RequestBody Train train) {
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

    @PostMapping(path = "/search_trains_between")
    public ResponseEntity<?> searchTrainsBetweenStations(@RequestBody SearchForm searchForm) {
        List<Train> listOfTrainsBetweenSourceAndDestStations = trainService.searchTrainsBetween(searchForm.getSource(), searchForm.getDestination());
        if (listOfTrainsBetweenSourceAndDestStations.isEmpty()) {
            return new ResponseEntity<>("No Direct Trains Found", HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(listOfTrainsBetweenSourceAndDestStations, HttpStatus.OK);
        }
    }
}