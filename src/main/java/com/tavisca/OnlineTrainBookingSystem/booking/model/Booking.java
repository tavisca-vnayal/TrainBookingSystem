package com.tavisca.OnlineTrainBookingSystem.booking.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@IdClass(CompositeKey.class)
public class Booking {

    @Id
    private int routeId;

    @Id
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="UTC")
    private LocalDate date;

    private int noOfConfirmedTicket = 0;
    private int noOfRACTicket = 0;
    private int noOfWaitingListTicket = 0;

    public Booking() {
    }

    public Booking(int routeId, LocalDate date, int noOfConfirmedTicket, int noOfRACTicket, int noOfWaitingListTicket) {
        this.routeId = routeId;
        this.date = date;
        this.noOfConfirmedTicket = noOfConfirmedTicket;
        this.noOfRACTicket = noOfRACTicket;
        this.noOfWaitingListTicket = noOfWaitingListTicket;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNoOfConfrimedTicket() {
        return noOfConfirmedTicket;
    }

    public void setNoOfConfrimedTicket(int noOfConfrimedTicket) {
        this.noOfConfirmedTicket = noOfConfrimedTicket;
    }

    public int getNoOfRACTicket() {
        return noOfRACTicket;
    }

    public void setNoOfRACTicket(int noOfRACTicket) {
        this.noOfRACTicket = noOfRACTicket;
    }

    public int getNoOfWaitingListTicket() {
        return noOfWaitingListTicket;
    }

    public void setNoOfWaitingListTicket(int noOfWaitingListTicket) {
        this.noOfWaitingListTicket = noOfWaitingListTicket;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "routeId=" + routeId +
                ", date=" + date +
                ", noOfConfirmedTicket=" + noOfConfirmedTicket +
                ", noOfRACTicket=" + noOfRACTicket +
                ", noOfWaitingListTicket=" + noOfWaitingListTicket +
                '}';
    }
}