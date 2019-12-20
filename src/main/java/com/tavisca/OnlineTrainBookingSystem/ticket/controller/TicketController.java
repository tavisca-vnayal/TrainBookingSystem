package com.tavisca.OnlineTrainBookingSystem.ticket.controller;

import com.tavisca.OnlineTrainBookingSystem.ticket.model.Ticket;
import com.tavisca.OnlineTrainBookingSystem.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping(path = "/tickets")
    public ResponseEntity<List<Ticket>> getTickets() {
        if (!ticketService.isEmpty())
            return new ResponseEntity<>(ticketService.getTickets(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/ticket/{pnr}")
    public ResponseEntity<?> getTicketByPnr(@PathVariable("pnr") int pnr) {
        Optional<Ticket> ticketByPnr = ticketService.getTicketByPnr(pnr);

        if (ticketByPnr.isPresent())
            return new ResponseEntity<>(ticketByPnr, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/user_tickets/{userId}")
    public ResponseEntity<?> getTicketByUserId(@PathVariable("userId") int userId) {
        Optional<List<Ticket>> ticketByUserId = ticketService.getTicketByUserId(userId);

        if (ticketByUserId.isPresent())
            return new ResponseEntity<>(ticketByUserId, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(path = "/tickets/{trainNo}")
    public ResponseEntity<?> getTicketByTrainNoAndDate
            (@PathVariable("trainNo") int trainNo,
                   @RequestParam("date")
                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                           LocalDate date) {

        Optional<List<Ticket>> ticketByTrainNoAndDate =
                ticketService.getTicketByTrainNoAndDate(trainNo, date);

        if (ticketByTrainNoAndDate.isPresent())
            return new ResponseEntity<>(ticketByTrainNoAndDate, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/ticket")
    public ResponseEntity<?> addTicket(@RequestBody Ticket ticket) {

        //System.out.println(ticket);

        ticketService.addTicket(ticket);
        if ((ticketService.getTicketByPnr(ticket.getPnr()).isPresent()))
            return new ResponseEntity<>(ticket, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping(path = "/ticket/{pnr}")
    public ResponseEntity<?> updateTicket(@PathVariable("pnr") int pnr, @RequestBody Ticket ticket){
        if((ticketService.getTicketByPnr(pnr).isPresent())) {
            ticketService.updateTicket(ticket);
            return new ResponseEntity<>(ticket, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/ticket/{pnr}")
    public ResponseEntity<?> deleteRoute(@PathVariable("pnr") int pnr) {
        if (ticketService.getTicketByPnr(pnr).isPresent()) {
            ticketService.deleteTicket(pnr);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}