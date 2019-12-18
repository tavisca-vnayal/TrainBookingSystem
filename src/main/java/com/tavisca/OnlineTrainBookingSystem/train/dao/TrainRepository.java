package com.tavisca.OnlineTrainBookingSystem.train.dao;

import com.tavisca.OnlineTrainBookingSystem.train.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRepository extends JpaRepository<Train, Integer> {

}