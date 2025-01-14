package com.tavisca.OnlineTrainBookingSystem.availability.service;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.tavisca.OnlineTrainBookingSystem.booking.model.Booking;
import com.tavisca.OnlineTrainBookingSystem.booking.service.BookingService;
import com.tavisca.OnlineTrainBookingSystem.route.model.Route;
import com.tavisca.OnlineTrainBookingSystem.route.service.RouteService;
import com.tavisca.OnlineTrainBookingSystem.ticket.service.TicketService;
import com.tavisca.OnlineTrainBookingSystem.train.model.SearchForm;
import com.tavisca.OnlineTrainBookingSystem.train.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class AvailabilityService {

    @Autowired
    private RouteService routeService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TrainService trainService;

    @Autowired
    private TicketService ticketService;

    public List<Integer> getAvailableSeats(int trainNo, LocalDate date){

        int capacity = getTotalSeatsInTrain(trainNo);

        List<Integer> availableSeats = IntStream.rangeClosed(1, capacity)
                .boxed().collect(Collectors.toList());

        if(!ticketService.getTicketByTrainNoAndDate(trainNo, date).isPresent())
            return availableSeats;

        ticketService.getTicketByTrainNoAndDate
                (trainNo, date).get().stream().forEach(ticket ->
                    ticket.getSeats().stream().filter(seat -> seat.getSeatIndex() != -1).forEach(seat ->
                        availableSeats.remove(new Integer(seat.getSeatIndex()))
                        )
        );

        System.out.println("availableSeats  " + availableSeats);

        return availableSeats;
    }

    public List<Integer> getRoutes(int trainNo){
        Optional<List<Route>> trainStoppages =
                routeService.getRouteByTrainNo(trainNo);

        //if(trainStoppages.isPresent())
        return trainStoppages.get().stream().map(Route::getRouteId).collect(Collectors.toList());
    }

    public List<Booking> getBookingsByRouteAndDate(List<Integer> routes, LocalDate date){

        List<Booking> bookings = new ArrayList<>();
        routes.forEach(route ->
                bookings.add(bookingService.getBookingByRouteIdAndDate(route, date).get()));
        return bookings;
    }

    public int getRouteFromStationName(int trainNo, String source) {
        return routeService.getRouteByTrainNoAndStationName(trainNo, source).get().getRouteId();
    }

    public int getTotalSeatsInTrain(int trainNo){
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

        return getStatus(capacity, requiredBookings);

    }

    private Optional<String> getStatus(int capacity, List<Booking> requiredBookings) {

//        int racCapacity = capacity * 25/100;
//
//        int cnfCapacity = capacity - racCapacity;

        int maxConfirmedBooking = requiredBookings.stream().max(Comparator.comparingInt
                (Booking::getNoOfConfirmedTicket)).get().getNoOfConfirmedTicket();

        if(maxConfirmedBooking <= capacity){
            return Optional.of("CNF " + (capacity - maxConfirmedBooking));
        }

//        if(maxConfirmedBooking <= cnfCapacity){
//            return Optional.of("CNF " + (cnfCapacity - maxConfirmedBooking));
//        }

//        int maxRacBooking = requiredBookings.stream().max(Comparator.comparingInt
//                (Booking::getNoOfConfirmedTicket)).get().getNoOfConfirmedTicket();
//
//        if(maxRacBooking <= racCapacity){
//            return Optional.of("RAC " + maxRacBooking);
//        }
//
//        int maxWaitListBooking = requiredBookings.stream().max(Comparator.comparingInt
//                (Booking::getNoOfConfirmedTicket)).get().getNoOfConfirmedTicket();
//
//        if(maxWaitListBooking <= capacity){
//            return Optional.of("WL " + maxConfirmedBooking);
//        }

        return Optional.of("REGRET");
    }
}
