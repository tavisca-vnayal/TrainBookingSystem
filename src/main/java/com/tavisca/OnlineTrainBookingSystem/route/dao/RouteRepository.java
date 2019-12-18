package com.tavisca.OnlineTrainBookingSystem.route.dao;

import com.tavisca.OnlineTrainBookingSystem.route.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

}