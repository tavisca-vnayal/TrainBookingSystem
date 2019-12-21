package com.tavisca.OnlineTrainBookingSystem.cancel.dao;

import com.tavisca.OnlineTrainBookingSystem.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CancelTicketRepository extends JpaRepository<Ticket, Integer> {

}