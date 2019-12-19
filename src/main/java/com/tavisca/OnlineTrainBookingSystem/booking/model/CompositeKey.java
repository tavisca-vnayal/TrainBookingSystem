package com.tavisca.OnlineTrainBookingSystem.booking.model;

import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class CompositeKey implements Serializable {
    private int routeId;
    private LocalDate date;

    public CompositeKey() {
    }

    public CompositeKey(int routeId, LocalDate date) {
        this.routeId = routeId;
        this.date = date;
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
}
