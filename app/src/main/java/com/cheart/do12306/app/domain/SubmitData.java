package com.cheart.do12306.app.domain;

/**
 * Created by cheart on 4/27/2014.
 */

public class SubmitData {

    String seatTypeCode;
    String constStrig = "0";
    String ticketTypeCode;
    String name;
    String passengerIdTypeCode;
    String passengerIdNo;
    String mobileNo;
    String save;
    String passengerType;
    public String getSeatTypeCode() {
        return seatTypeCode;
    }
    public void setSeatTypeCode(String setTypeCode) {
        this.seatTypeCode = setTypeCode;
    }
    public String getConstStrig() {
        return constStrig;
    }
    public void setConstStrig(String constStrig) {
        this.constStrig = constStrig;
    }
    public String getTicketTypeCode() {
        return ticketTypeCode;
    }
    public void setTicketTypeCode(String ticketTypeCode) {
        this.ticketTypeCode = ticketTypeCode;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassengerIdTypeCode() {
        return passengerIdTypeCode;
    }
    public void setPassengerIdTypeCode(String passengerIdTypeCode) {
        this.passengerIdTypeCode = passengerIdTypeCode;
    }
    public String getPassengerIdNo() {
        return passengerIdNo;
    }
    public void setPassengerIdNo(String passengerIdNo) {
        this.passengerIdNo = passengerIdNo;
    }
    public String getMobileNo() {
        return mobileNo;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    public String getSave() {
        return save;
    }
    public void setSave(String save) {
        this.save = save;
    }
    public String getPassengerType() {
        return passengerType;
    }
    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }



}
