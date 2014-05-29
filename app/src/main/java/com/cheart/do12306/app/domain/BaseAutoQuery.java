package com.cheart.do12306.app.domain;

/**
 * Created by cheart on 5/29/2014.
 */
public class BaseAutoQuery {

    String date;
    String seatType;
    String passenger;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getPassenger() {
        return passenger;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }
}
