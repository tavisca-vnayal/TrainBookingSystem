package com.tavisca.OnlineTrainBookingSystem.booking.model;

import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;

public class CompositeKey implements Serializable {
    private int routeId;
    private Date date;

    public CompositeKey() {
    }

    public CompositeKey(int routeId, Date date) {
        this.routeId = routeId;
        this.date = date;
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
}
