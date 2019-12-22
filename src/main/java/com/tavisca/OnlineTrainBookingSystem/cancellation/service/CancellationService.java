package com.tavisca.OnlineTrainBookingSystem.cancellation.service;

import com.tavisca.OnlineTrainBookingSystem.availability.service.AvailabilityService;
import com.tavisca.OnlineTrainBookingSystem.booking.model.Booking;
import com.tavisca.OnlineTrainBookingSystem.booking.service.BookingService;
import com.tavisca.OnlineTrainBookingSystem.route.model.Route;
import com.tavisca.OnlineTrainBookingSystem.route.service.RouteService;
import com.tavisca.OnlineTrainBookingSystem.ticket.model.Ticket;
import com.tavisca.OnlineTrainBookingSystem.ticket.service.TicketService;
import com.tavisca.OnlineTrainBookingSystem.train.model.SearchForm;
import com.tavisca.OnlineTrainBookingSystem.train.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CancellationService {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private AvailabilityService availabilityService;

    public String cancelTicket(int pnr) {
        Ticket ticket = ticketService.getTicketByPnr(pnr).get();

        int trainNo = ticket.getTrainNo();
        String source = ticket.getSource();

        Route routeSrc = routeService.getRouteByTrainNoAndStationName(trainNo, source).get();
        LocalDateTime currentTime = LocalDateTime.now();

        System.out.println(routeSrc.getArrivalTime());

        System.out.println(currentTime);
        if(currentTime.isBefore(routeSrc.getArrivalTime())){
            updateBookingAvailability(ticket);
            updateTicketStatus(ticket);
            return "Ticket Cancelled Successfully!";
        }

        return "Ticket can't be cancelled as the train has left!";
    }

    private void updateBookingAvailability(Ticket ticket) {
        int trainNo = ticket.getTrainNo();
        LocalDate date = ticket.getDate();

        int seatsRequired = ticket.getSeats().size();

        int srcRoute = availabilityService.getRouteFromStationName
                (ticket.getTrainNo(), ticket.getSource());

        int destRoute = availabilityService.getRouteFromStationName
                (ticket.getTrainNo(), ticket.getDestination());

        List<Integer> allRouteNo = availabilityService.getRoutes(trainNo);
        List<Booking> bookings = availabilityService.getBookingsByRouteAndDate(allRouteNo, date);

        List<Booking> requiredBookings = bookings.stream().filter(booking ->
                (booking.getRouteId() >= srcRoute && booking.getRouteId() < destRoute)
        ).collect(Collectors.toList());

        for(Booking booking: requiredBookings){
            handleCancellationBooking(seatsRequired, booking);
        }
    }

    private void updateTicketStatus(Ticket ticket) {
        ticket.getSeats().stream().forEach(
                seat -> {
                    seat.setSeatStatus("CANCELLED");
                    seat.setSeatIndex(-1);
                }
        );
        ticketService.updateTicket(ticket);
    }

    private void handleCancellationBooking(int seatsRequired, Booking booking) {
        int noOfConfirmedTicket = booking.getNoOfConfirmedTicket();

        Booking tempBooking = booking;
        tempBooking.setNoOfConfirmedTicket(
                noOfConfirmedTicket - seatsRequired);

        bookingService.updateBooking(tempBooking);
    }
}
