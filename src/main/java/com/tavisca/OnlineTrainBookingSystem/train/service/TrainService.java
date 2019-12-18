package com.tavisca.OnlineTrainBookingSystem.train.service;

import com.tavisca.OnlineTrainBookingSystem.train.dao.TrainRepository;
import com.tavisca.OnlineTrainBookingSystem.train.model.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainService {
    @Autowired
    private TrainRepository trainRepo;

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
}