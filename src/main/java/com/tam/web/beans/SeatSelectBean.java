package com.tam.web.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import com.tam.model.Seat;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

import com.tam.model.Coupon;
import com.tam.model.Segment;
import com.tam.service.SeatService;

import java.io.IOException;

@ManagedBean
public class SeatSelectBean {

    @Autowired
    @ManagedProperty("#{seatService}")
    SeatService seatService;

    @Autowired
    @ManagedProperty("#{ticketBean}")
    TicketBean ticketBean;

    private Segment segment;
    private Coupon coupon;
    private Seat seat;

    public String selectSeat() {
        return "ucakBilet";
    }


}	
