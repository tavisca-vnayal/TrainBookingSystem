package com.tavisca.OnlineTrainBookingSystem.train.service;

import com.tavisca.OnlineTrainBookingSystem.route.model.Route;
import com.tavisca.OnlineTrainBookingSystem.train.dao.TrainRepository;
import com.tavisca.OnlineTrainBookingSystem.train.model.Train;
import com.tavisca.OnlineTrainBookingSystem.route.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrainService {
    @Autowired
    private TrainRepository trainRepo;
    @Autowired RouteService routeService;

    public boolean isEmpty(){
        return trainRepo.findAll().isEmpty();
    }

    public List<Train> getTrains() {
        List<Train> trains = trainRepo.findAll();
        return trains;
    }

    public Optional<Train> getTrainById(int trainNo) {
        Optional<Train> train = trainRepo.findById(trainNo);
        return train;
    }

    public String addTrain(Train train) {
        trainRepo.save(train);
        return "Added";
    }

    public String updateTrain(Train train) {
        trainRepo.save(train);
        return "Updated";
    }

    public String deleteTrain(int trainId) {
        trainRepo.deleteById(trainId);
        return "Deleted";
    }

    public List<Train> searchTrainsBetween(String source, String destination) {
        Optional<List<Route>> listOfTrainsViaSource = routeService.getTrainNoByStationName(source);
        Optional<List<Route>> listOfTrainsViaDestination = routeService.getTrainNoByStationName(destination);
        if (!listOfTrainsViaSource.isPresent() || !listOfTrainsViaDestination.isPresent()) {
            return null;
        }
        else{
            List<Train> listOfTrainsBetweenSourceAndDestStations = new ArrayList<>();
            for (Route sourceRoute: listOfTrainsViaSource.get()
            ) {
                for (Route destRoute : listOfTrainsViaDestination.get()
                ) {
                    if ((sourceRoute.getTrainNo() == destRoute.getTrainNo()) && sourceRoute.getArrivalTime().isBefore(destRoute.getArrivalTime())) {
                        listOfTrainsBetweenSourceAndDestStations.add(getTrainById(sourceRoute.getTrainNo()).get());
                    }
                }
            }
            return listOfTrainsBetweenSourceAndDestStations;
        }
    }
}