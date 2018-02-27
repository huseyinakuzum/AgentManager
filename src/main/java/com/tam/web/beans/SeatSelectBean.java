package com.tam.web.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.tam.model.Seat;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

import com.tam.model.Coupon;
import com.tam.model.Segment;
import com.tam.service.SeatService;

import java.io.IOException;


@ManagedBean
@RequestScoped
public class SeatSelectBean {

    @Autowired
    @ManagedProperty("#{seatService}")
    SeatService seatService;

    @Autowired
    @ManagedProperty("#{ticketBean}")
    TicketBean ticketBean;

    private Segment segment = new Segment();
    private Coupon coupon;
    private Seat seat;

    public void selectSeat() {
        ticketBean.setSeatSegment(segment);
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "ucakBilet.jsf");
    }

    public String redir(){
        ticketBean.setSeatSegment(segment);
        return "ucakBilet.jsf?faces-redirect=true";
    }

    public SeatService getSeatService() {
        return seatService;
    }

    public void setSeatService(SeatService seatService) {
        this.seatService = seatService;
    }

    public TicketBean getTicketBean() {
        return ticketBean;
    }

    public void setTicketBean(TicketBean ticketBean) {
        this.ticketBean = ticketBean;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
