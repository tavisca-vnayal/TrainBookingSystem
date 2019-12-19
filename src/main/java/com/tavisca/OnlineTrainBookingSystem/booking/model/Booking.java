package com.tavisca.OnlineTrainBookingSystem.booking.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
public class Booking {

    private int routeId;
    private Date date;
    private int noOfConfirmedTicket;
    private int noOfRACTicket;
    private int noOfWaitingListTicket;

    public Booking() {
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
}