package com.tavisca.OnlineTrainBookingSystem.booking.dao;

import com.tavisca.OnlineTrainBookingSystem.booking.model.Booking;
import com.tavisca.OnlineTrainBookingSystem.booking.model.CompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, CompositeKey> {
    Optional<Booking> findByRouteIdAndDate(int routeId, LocalDate date);
}