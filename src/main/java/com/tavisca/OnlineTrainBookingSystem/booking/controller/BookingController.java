package com.tavisca.OnlineTrainBookingSystem.booking.controller;

import com.tavisca.OnlineTrainBookingSystem.booking.model.Booking;
import com.tavisca.OnlineTrainBookingSystem.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping(path = "/bookings")
    public ResponseEntity<List<Booking>> getBookings() {
        if (!bookingService.isEmpty())
            return new ResponseEntity<>(bookingService.getBookings(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/booking/{id}")
    public ResponseEntity<?> getBookingRouteIdAndDate(@PathVariable("id") int routeId,
                                                 @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        System.out.println(date.getClass());
        Optional<Booking> booking=bookingService.getBookingByRouteIdAndDate(routeId, date);

        System.out.println(booking);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @PostMapping(path = "/booking")
    public ResponseEntity<?> addBooking(@RequestBody Booking booking) {
        bookingService.addBooking(booking);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
//        if ((bookingService.getBookingByRouteId(booking.getRouteId()).isPresent()))
//            return new ResponseEntity<>(booking, HttpStatus.CREATED);
//        else
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

//    @PutMapping(path = "/booking/{id}")
//    public ResponseEntity<?> updateBooking(@PathVariable("id") int routeId, @RequestBody Booking booking) {
//        if((bookingService.getBookingByRouteId(routeId).isPresent())) {
//            bookingService.updateBooking(booking);
//            return new ResponseEntity<>(booking, HttpStatus.CREATED);
//        }
//        else
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }

//    @DeleteMapping(path = "/booking/{id}")
//    public ResponseEntity<?> deleteBooking(@PathVariable("id") int routeId) {
//        if (bookingService.getBookingByRouteId(routeId).isPresent()) {
//            bookingService.deleteBooking(routeId);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } else
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

}