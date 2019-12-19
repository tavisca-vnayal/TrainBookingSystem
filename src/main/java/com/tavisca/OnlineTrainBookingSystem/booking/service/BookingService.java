package com.tavisca.OnlineTrainBookingSystem.booking.service;

import com.tavisca.OnlineTrainBookingSystem.booking.dao.BookingRepository;
import com.tavisca.OnlineTrainBookingSystem.booking.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository routeRepo;

    public boolean isEmpty(){
        return routeRepo.findAll().isEmpty();
    }

    public List<Booking> getBookings() {
        List<Booking> bookings = routeRepo.findAll();
        return bookings;
    }

    public Optional<Booking> getBookingByRouteId(int routeId) {
        Optional<Booking> booking = routeRepo.findById(routeId);
        return booking;
    }

    public String addBooking(Booking booking) {
        routeRepo.save(booking);
        return "Added";
    }

    public String updateBooking(Booking booking) {
        routeRepo.save(booking);
        return "Updated";
    }

    public String deleteBooking(int routeId) {
        routeRepo.deleteById(routeId);
        return "Deleted";
    }
}