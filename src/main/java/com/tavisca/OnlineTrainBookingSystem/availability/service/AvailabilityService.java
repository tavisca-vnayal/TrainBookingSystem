package com.tavisca.OnlineTrainBookingSystem.availability.service;

import com.tavisca.OnlineTrainBookingSystem.booking.model.Booking;
import com.tavisca.OnlineTrainBookingSystem.booking.service.BookingService;
import com.tavisca.OnlineTrainBookingSystem.route.model.Route;
import com.tavisca.OnlineTrainBookingSystem.route.service.RouteService;
import com.tavisca.OnlineTrainBookingSystem.train.model.SearchForm;
import com.tavisca.OnlineTrainBookingSystem.train.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AvailabilityService {

    @Autowired
    private RouteService routeService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TrainService trainService;

    private List<Integer> getRoutes(int trainNo){
        Optional<List<Route>> trainStoppages =
                routeService.getRouteByTrainNo(trainNo);

        //if(trainStoppages.isPresent())
        return trainStoppages.get().stream().map(Route::getRouteId).collect(Collectors.toList());
    }

    private List<Booking> getBookingsByRouteAndDate(List<Integer> routes, LocalDate date){

        List<Booking> bookings = new ArrayList<>();
        routes.forEach(route ->
                bookings.add(bookingService.getBookingByRouteIdAndDate(route, date).get()));
        return bookings;
    }

    private int getRouteFromStationName(int trainNo, String source) {
        return routeService.getRouteByTrainNoAndStationName(trainNo, source).get().getRouteId();
    }

    private int getTotalSeatsInTrain(int trainNo){
        return trainService.getTrainById(trainNo).get().getCapacity();
    }

    public Optional<String> getAvailability(int trainNo, SearchForm searchForm) {
        LocalDate date = searchForm.getDate();
        String srcStation = searchForm.getSource();
        String destStation = searchForm.getDestination();

        List<Integer> allRouteNo = getRoutes(trainNo);
        List<Booking> bookings = getBookingsByRouteAndDate(allRouteNo, date);

        int srcRoute = getRouteFromStationName(trainNo, srcStation);
        int destRoute = getRouteFromStationName(trainNo, destStation);

        int capacity = getTotalSeatsInTrain(trainNo);

        List<Booking> requiredBookings = bookings.stream().filter(booking ->
                (booking.getRouteId() >= srcRoute && booking.getRouteId() < destRoute)
        ).collect(Collectors.toList());

        int maxConfirmedBooking = requiredBookings.stream().max(Comparator.comparingInt
                (Booking::getNoOfConfrimedTicket)).get().getNoOfConfrimedTicket();

        int racCapacity = capacity * 25/100;

        int cnfCapacity = capacity - racCapacity;

        if(maxConfirmedBooking <= cnfCapacity){
            return Optional.of("CNF " + (cnfCapacity - maxConfirmedBooking));
        }

        int maxRacBooking = requiredBookings.stream().max(Comparator.comparingInt
                (Booking::getNoOfConfrimedTicket)).get().getNoOfConfrimedTicket();

        if(maxRacBooking <= racCapacity){
            return Optional.of("RAC " + maxRacBooking);
        }

        int maxWaitListBooking = requiredBookings.stream().max(Comparator.comparingInt
                (Booking::getNoOfConfrimedTicket)).get().getNoOfConfrimedTicket();

        if(maxWaitListBooking <= capacity){
            return Optional.of("WL " + maxConfirmedBooking);
        }

        return Optional.of("REGRET");

    }
}
