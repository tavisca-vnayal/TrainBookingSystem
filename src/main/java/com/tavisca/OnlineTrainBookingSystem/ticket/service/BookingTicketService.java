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

        int maxConfirmedBooking = requiredBookings.stream().max(Comparator.comparingInt
                        (Booking::getNoOfConfrimedTicket)).get().getNoOfConfrimedTicket();

        int maxRacBooking = requiredBookings.stream().max(Comparator.comparingInt
                (Booking::getNoOfConfrimedTicket)).get().getNoOfConfrimedTicket();

        int maxWaitListBooking = requiredBookings.stream().max(Comparator.comparingInt
                (Booking::getNoOfConfrimedTicket)).get().getNoOfConfrimedTicket();

        if(maxConfirmedBooking + seatsRequired > capacity){

            int remainingConfirmedSeats = capacity - maxConfirmedBooking;
            int racBooking = seatsRequired - remainingConfirmedSeats;

            int racCapacity = capacity * 25/100;

            if(maxRacBooking + racBooking > racCapacity){
                int remainingRacSeats = racCapacity - maxRacBooking;
                int waitingListBooking = racBooking - remainingRacSeats;
            }
            //Handle RAC and WL
            return "RAC or WL";
        }

        for(Booking booking: requiredBookings){
            handleConfirmBooking(seatsRequired, booking);
        }

        System.out.println(maxConfirmedBooking);

        AtomicInteger ordinal = new AtomicInteger(maxConfirmedBooking);
        ticket.getSeats().stream().forEach(
                seat -> {
                    seat.setSeatStatus("CNF");
                    seat.setSeatIndex(ordinal.getAndIncrement());
                }
        );

        return "CNF";
    }

    private void handleConfirmBooking(int seatsRequired, Booking booking) {
        int noOfConfirmedTicket = booking.getNoOfConfrimedTicket();

        Booking tempBooking = booking;
        tempBooking.setNoOfConfrimedTicket(
                noOfConfirmedTicket + seatsRequired);

        bookingService.updateBooking(tempBooking);
    }

    private void handleRacBooking(int seatsRequired, Booking booking) {
        int noOfRACTicket = booking.getNoOfRACTicket();

        Booking tempBooking = booking;
        tempBooking.setNoOfConfrimedTicket(
                noOfRACTicket + seatsRequired);

        bookingService.updateBooking(tempBooking);
    }

    private void handleWaitingListBooking(int seatsRequired, Booking booking) {
        int noOfWaitingListTicket = booking.getNoOfWaitingListTicket();

        Booking tempBooking = booking;
        tempBooking.setNoOfConfrimedTicket(
                noOfWaitingListTicket + seatsRequired);

        bookingService.updateBooking(tempBooking);
    }
}
