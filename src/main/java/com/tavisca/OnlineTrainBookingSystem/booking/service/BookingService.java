package com.tavisca.OnlineTrainBookingSystem.booking.service;

import com.tavisca.OnlineTrainBookingSystem.booking.dao.BookingRepository;
import com.tavisca.OnlineTrainBookingSystem.booking.model.Booking;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
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

//    public Optional<Booking> getBookingByRouteId(int routeId) {
//        Optional<Booking> booking = routeRepo.findById(routeId);
//        return booking;
//    }

    public Optional<Booking> getBookingByRouteIdAndDate(int routeId, LocalDate date) {
        Optional<Booking> booking = routeRepo.findByRouteIdAndDate(routeId, date);

        if(booking.isPresent())
            return booking;
        else
            addBooking(new Booking(routeId, date, 0, 0, 0));

        return booking;
    }

    public String addBooking(Booking booking) {

        System.out.println("addBooking " + booking);

        routeRepo.save(booking);
        return "Added";
    }

    public String updateBooking(Booking booking) {
        routeRepo.save(booking);
        return "Updated";
    }

//    public String deleteBooking(int routeId) {
//        routeRepo.deleteById(routeId);
//        return "Deleted";
//    }

//    public Date getDate(String dateFromUI) {
////        JSONObject jsonObject = new JSONObject(dateFromUI);
//        Date date = Date.valueOf(dateFromUI);
//        return date;
//    }


}