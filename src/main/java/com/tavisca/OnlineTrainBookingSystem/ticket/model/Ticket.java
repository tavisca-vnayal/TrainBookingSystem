package com.tavisca.OnlineTrainBookingSystem.ticket.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pnr;

    private int userId;
    private int trainNo;
    private String source;
    private String destination;
    private LocalDate date;

    @ElementCollection
    private List<PassengerDetail> seats;

    public Ticket() {
    }

    public int getPnr() {
        return pnr;
    }

    public void setPnr(int pnr) {
        this.pnr = pnr;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(int trainNo) {
        this.trainNo = trainNo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<PassengerDetail> getSeats() {
        return seats;
    }

    public void setSeats(List<PassengerDetail> seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "pnr=" + pnr +
                ", userId=" + userId +
                ", trainNo=" + trainNo +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", date=" + date +
                ", seats=" + seats +
                '}';
    }
}
