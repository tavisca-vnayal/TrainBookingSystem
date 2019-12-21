package com.tavisca.OnlineTrainBookingSystem.ticket.service;

import com.tavisca.OnlineTrainBookingSystem.booking.model.Booking;
import com.tavisca.OnlineTrainBookingSystem.booking.service.BookingService;
import com.tavisca.OnlineTrainBookingSystem.route.model.Route;
import com.tavisca.OnlineTrainBookingSystem.route.service.RouteService;
import com.tavisca.OnlineTrainBookingSystem.ticket.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingTicketService {
    @Autowired
    private RouteService routeService;

    @Autowired
    private BookingService bookingService;

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
    public List<Integer> bookTicket(Ticket ticket){
        List<Integer> allRouteNo = getRoutes(ticket.getTrainNo());
        List<Booking> bookings = getBookingsByRouteAndDate(allRouteNo, ticket.getDate());
        
        System.out.println(allRouteNo);
        System.out.println(bookings);

        return null;
    }
}
