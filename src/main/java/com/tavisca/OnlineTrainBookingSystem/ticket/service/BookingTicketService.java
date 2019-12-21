package com.tavisca.OnlineTrainBookingSystem.ticket.service;
import com.tavisca.OnlineTrainBookingSystem.booking.model.Booking;
import com.tavisca.OnlineTrainBookingSystem.booking.service.BookingService;
import com.tavisca.OnlineTrainBookingSystem.route.model.Route;
import com.tavisca.OnlineTrainBookingSystem.route.service.RouteService;
import com.tavisca.OnlineTrainBookingSystem.ticket.model.Ticket;
import com.tavisca.OnlineTrainBookingSystem.train.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class BookingTicketService {
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

    public String bookTicket(Ticket ticket){
        List<Integer> allRouteNo = getRoutes(ticket.getTrainNo());
        List<Booking> bookings = getBookingsByRouteAndDate(allRouteNo, ticket.getDate());

        int srcRoute = getRouteFromStationName(ticket.getTrainNo(), ticket.getSource());
        int destRoute = getRouteFromStationName(ticket.getTrainNo(), ticket.getDestination());

        int capacity = getTotalSeatsInTrain(ticket.getTrainNo());
        int seatsRequired = ticket.getSeats().size();

        List<Booking> requiredBookings = bookings.stream().filter(booking ->
                        (booking.getRouteId() >= srcRoute && booking.getRouteId() < destRoute)
                ).collect(Collectors.toList());

        int maxBooking = requiredBookings.stream().max(Comparator.comparingInt
                        (Booking::getNoOfConfrimedTicket)).get().getNoOfConfrimedTicket();

        if(maxBooking + seatsRequired >= capacity){
            //Handle RAC and WL
            return "RAC or WL";
        }

        boolean flag = false;
        for(Booking booking: bookings){
            if(booking.getRouteId() == destRoute)
                break;

            if(booking.getRouteId() == srcRoute)
                flag = true;

            if(flag == true){
                int noOfConfrimedTicket = booking.getNoOfConfrimedTicket();
                int noOfRACTicket = booking.getNoOfRACTicket();
                int noOfWaitingListTicket = booking.getNoOfWaitingListTicket();

                Booking tempBooking = booking;
                tempBooking.setNoOfConfrimedTicket(
                        tempBooking.getNoOfConfrimedTicket() + seatsRequired);

                bookingService.updateBooking(tempBooking);
            }
        }

        AtomicInteger ordinal = new AtomicInteger(maxBooking);
        ticket.getSeats().stream().forEach(
                seat -> {
                    seat.setSeatStatus("CNF");
                    seat.setSeatIndex(ordinal.getAndIncrement());
                }
        );
//        System.out.println(allRouteNo);
//        System.out.println(bookings);

        return "CNF";
    }
}
