package com.tavisca.OnlineTrainBookingSystem.booking.dao;

import com.tavisca.OnlineTrainBookingSystem.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

}