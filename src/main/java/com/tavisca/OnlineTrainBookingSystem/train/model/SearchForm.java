package com.tavisca.OnlineTrainBookingSystem.train.model;

import java.time.LocalDate;

public class SearchForm {
    private String source;
    private String destination;
    private LocalDate date;

    public SearchForm() {
    }

    public SearchForm(String source, String destination, LocalDate date) {
        this.source = source;
        this.destination = destination;
        this.date = date;
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
}
