package com.tavisca.OnlineTrainBookingSystem.train.model;

public class Station {
    private String stationName;
    private String arrivalTime;

    public Station() {
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}