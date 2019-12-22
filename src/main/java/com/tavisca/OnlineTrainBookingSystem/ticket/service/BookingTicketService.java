package com.tavisca.OnlineTrainBookingSystem.ticket.service;
import com.tavisca.OnlineTrainBookingSystem.availability.service.AvailabilityService;
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

    @Autowired
    private AvailabilityService availabilityService;


    public String bookTicket(Ticket ticket){
        List<Integer> allRouteNo = availabilityService.getRoutes(ticket.getTrainNo());
        List<Booking> bookings = availabilityService.getBookingsByRouteAndDate(allRouteNo, ticket.getDate());

        int srcRoute = availabilityService.getRouteFromStationName(ticket.getTrainNo(), ticket.getSource());
        int destRoute = availabilityService.getRouteFromStationName(ticket.getTrainNo(), ticket.getDestination());

        int capacity = availabilityService.getTotalSeatsInTrain(ticket.getTrainNo());

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
