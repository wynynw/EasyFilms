package com.example.and319;

import android.widget.Button;

public class Ticket {
    private String TicketName;
    private String TicketDateTime;
    private String TicketSeat;
    private String TicketCode;
    private String TicketStatus;

    public Ticket(String TicketName, String TicketDateTime, String TicketSeat, String TicketCode, String TicketStatus) {
        this.TicketName = TicketName;
        this.TicketDateTime = TicketDateTime;
        this.TicketSeat = TicketSeat;
        this.TicketCode = TicketCode;
        this.TicketStatus = TicketStatus;

    }


    public String getTicketName() {
        return TicketName;
    }

    public String getTicketTime() {
        return TicketDateTime;
    }

    public String getTicketSeat() {
        return TicketSeat;
    }

    public String getTicketCode() {
        return TicketCode;
    }

    public String getTicketStatus() {
        return TicketStatus;
    }

}
