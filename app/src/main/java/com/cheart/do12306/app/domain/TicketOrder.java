package com.cheart.do12306.app.domain;

/**
 * Created by cheart on 5/6/2014.
 */
public class TicketOrder {

    private String name;
    private Passenger passenger;
    private BaseData baseData;
    private BaseQueryLeft baseQueryLeft;
    private String ticketType;
    private String seatType;
    private String date;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public BaseData getBaseData() {
        return baseData;
    }

    public void setBaseData(BaseData baseData) {
        this.baseData = baseData;
    }

    public BaseQueryLeft getBaseQueryLeft() {
        return baseQueryLeft;
    }

    public void setBaseQueryLeft(BaseQueryLeft baseQueryLeft) {
        this.baseQueryLeft = baseQueryLeft;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }
}
