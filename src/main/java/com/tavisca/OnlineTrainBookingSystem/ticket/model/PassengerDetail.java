package com.tavisca.OnlineTrainBookingSystem.ticket.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PassengerDetail implements Serializable {

    private String passengerName;
    private String seatStatus;
    private int seatIndex;

    public PassengerDetail() {
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(String seatStatus) {
        this.seatStatus = seatStatus;
    }

    public int getSeatIndex() {
        return seatIndex;
    }

    public void setSeatIndex(int seatIndex) {
        this.seatIndex = seatIndex;
    }

    @Override
    public String toString() {
        return "PassengerDetail{" +
                "passengerName='" + passengerName + '\'' +
                ", seatStatus='" + seatStatus + '\'' +
                ", seatIndex='" + seatIndex + '\'' +
                '}';
    }
}
