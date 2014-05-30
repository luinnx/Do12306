package com.cheart.do12306.app.domain;

import android.test.ServiceTestCase;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by cheart on 5/29/2014.
 */
public class BaseAutoQuery implements Serializable {


    Set<String> date;
    Set<String> seatType;
    Set<String> trainCode;
    Set<String> passenger;


    public Set<String> getSeatType() {
        return seatType;
    }

    public void setSeatType(Set<String> seatType) {
        this.seatType = seatType;
    }

    public Set<String> getDate() {
        return date;
    }

    public void setDate(Set<String> date) {
        this.date = date;
    }

    public Set<String> getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(Set<String> trainCode) {
        this.trainCode = trainCode;
    }

    public Set<String> getPassenger() {
        return passenger;
    }

    public void setPassenger(Set<String> passenger) {
        this.passenger = passenger;
    }
}
