package com.tavisca.OnlineTrainBookingSystem.ticket.service;

import com.tavisca.OnlineTrainBookingSystem.ticket.dao.TicketRepository;
import com.tavisca.OnlineTrainBookingSystem.ticket.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepo;

    public boolean isEmpty(){
        return ticketRepo.findAll().isEmpty();
    }

    public List<Ticket> getTickets() {
        List<Ticket> tickets = ticketRepo.findAll();
        return tickets;
    }

    public Optional<Ticket> getTicketByPnr(int pnr) {
        Optional<Ticket> train = ticketRepo.findById(pnr);
        return train;
    }

    public String addTicket(Ticket ticket) {
        ticketRepo.save(ticket);
        return "Added";
    }

    public String updateTicket(Ticket ticket) {
        ticketRepo.save(ticket);
        return "Updated";
    }

    public String deleteTicket(int pnr) {
        ticketRepo.deleteById(pnr);
        return "Deleted";
    }

    public Optional<List<Ticket>> getTicketByTrainNoAndDate(int trainNo, LocalDate date) {
        return ticketRepo.findByTrainNoAndDate(trainNo, date);
    }

    public Optional<List<Ticket>> getTicketByUserId(int userId) {
        return ticketRepo.findByUserId(userId);
    }
}