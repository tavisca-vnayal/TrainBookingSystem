package com.tavisca.OnlineTrainBookingSystem.booking.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.sql.Date;

@Entity
@IdClass(CompositeKey.class)
public class Booking {

    @Id
    private int routeId;
    @Id
    private Date date;

    private int noOfConfirmedTicket;
    private int noOfRACTicket;
    private int noOfWaitingListTicket;

    public Booking() {
    }

    public Booking(int routeId, Date date, int noOfConfirmedTicket, int noOfRACTicket, int noOfWaitingListTicket) {
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