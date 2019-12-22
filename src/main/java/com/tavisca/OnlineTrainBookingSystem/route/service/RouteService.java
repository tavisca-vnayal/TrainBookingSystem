package com.tavisca.OnlineTrainBookingSystem.route.service;

import com.tavisca.OnlineTrainBookingSystem.route.dao.RouteRepository;
import com.tavisca.OnlineTrainBookingSystem.route.model.Route;
import com.tavisca.OnlineTrainBookingSystem.train.model.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RouteService {
    @Autowired
    private RouteRepository routeRepo;

    public boolean isEmpty(){
        return routeRepo.findAll().isEmpty();
    }

    public List<Route> getRoutes() {
        List<Route> routes = routeRepo.findAll();
        return routes;
    }

    public Optional<Route> getRouteById(int routeId) {
        Optional<Route> train = routeRepo.findById(routeId);
        return train;
    }

    public String addRoute(Route route) {
        System.out.println(route);
        routeRepo.save(route);
        System.out.println(route);
        return "Added";
    }

    public String updateRoute(Route route) {
        routeRepo.save(route);
        return "Updated";
    }

    public String deleteRoute(int routeId) {
        routeRepo.deleteById(routeId);
        return "Deleted";
    }

    public Optional<List<Route>> getRouteByTrainNo(int trainNo) {
        return routeRepo.findByTrainNo(trainNo);
    }

    public Optional<List<Route> > getTrainNoByStationName(String stationName) {
        return routeRepo.findByStationName(stationName);
    }

    public Optional<Route> getRouteByTrainNoAndStationName(int trainNo, String stationName) {
        return routeRepo.findByTrainNoAndStationName(trainNo, stationName);
    }

    public List<String> getStations() {
        List<Route> routes = getRoutes();
        Set<String> uniqueRoutes = routes.stream().map(
                route -> route.getStationName()).collect(Collectors.toSet());

        return uniqueRoutes.stream().collect(Collectors.toList());
    }
}