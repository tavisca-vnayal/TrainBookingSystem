package com.tavisca.OnlineTrainBookingSystem.route.dao;

import com.tavisca.OnlineTrainBookingSystem.route.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

    Optional<List<Route> > findByTrainNo(int trainNo);

    Optional<List<Route>> findByStationName(String stationName);

}