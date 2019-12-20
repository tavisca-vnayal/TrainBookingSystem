package com.tavisca.OnlineTrainBookingSystem.ticket.dao;

import com.tavisca.OnlineTrainBookingSystem.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    //Optional<Ticket> findByPnr(int pnr);

    Optional<List<Ticket>> findByUserId(int userId);

    Optional<List<Ticket>> findByTrainNoAndDate(int trainNo, LocalDate date);

}