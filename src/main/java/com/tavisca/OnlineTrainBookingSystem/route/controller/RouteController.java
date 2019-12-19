package com.tavisca.OnlineTrainBookingSystem.route.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tavisca.OnlineTrainBookingSystem.route.model.Route;
import com.tavisca.OnlineTrainBookingSystem.route.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RouteController {
    @Autowired
    private RouteService routeService;

    @GetMapping(path = "/routes")
    public ResponseEntity<List<Route>> getRoutes() {
        if (!routeService.isEmpty())
            return new ResponseEntity<>(routeService.getRoutes(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/route/{id}")
    public ResponseEntity<?> getRouteById(@PathVariable("id") int routeId) {
        if (routeService.getRouteById(routeId).isPresent())
            return new ResponseEntity<>(routeService.getRouteById(routeId), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/route")
    public ResponseEntity<?> addRoute(@RequestBody Route route) {
        routeService.addRoute(route);
        if ((routeService.getRouteById(route.getRouteId()).isPresent()))
            return new ResponseEntity<>(route, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping(path = "/route/{id}")
    public ResponseEntity<?> updateRoute(@PathVariable("id") int routeId, @RequestBody Route route){
        if((routeService.getRouteById(routeId).isPresent())) {
            routeService.updateRoute(route);
            return new ResponseEntity<>(route, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/route/{id}")
    public ResponseEntity<?> deleteRoute(@PathVariable("id") int routeId) {
        if (routeService.getRouteById(routeId).isPresent()) {
            routeService.deleteRoute(routeId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}