package com.tam.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.tam.model.*;
import com.tam.service.PaymentTypeService;
import com.tam.service.SeatService;

import com.tam.service.PortService;
import com.tam.service.TicketService;

@ManagedBean
@ApplicationScoped
public class TicketBean implements Serializable {
    private static final long serialVersionUID = 1L;


    @ManagedProperty("#{ticketService}")
    TicketService ticketService;

    @ManagedProperty("#{portService}")
    PortService portService;

    @ManagedProperty("#{seatService}")
    SeatService seatService;

    @ManagedProperty("#{paymentTypeService}")
    PaymentTypeService paymentTypeService;

    private Port port = new Port();
    private List<Port> portList;
    private Segment segment = new Segment();
    private Segment seatSegment = new Segment();
    private List<Segment> segmentList = new ArrayList<>();
    private Ticket ticket = new Ticket();
    private List<Ticket> ticketList = new ArrayList<>();
    private Pax pax = new Pax();
    private List<Pax> paxList = new ArrayList<>();
    private Pnr pnr = new Pnr();
    private List<Pnr> pnrList = new ArrayList<>();
    private ContactInfo contactInfo = new ContactInfo();
    private List<ContactInfo> contactInfoList = new ArrayList<>();
    private PaymentType paymentType = new PaymentType();
    private List<PaymentType> paymentTypes = new ArrayList<>();
    private List<String> portNames = new ArrayList<>();
    private List<String> portCodes = new ArrayList<>();
    private List<Seat> seats = new ArrayList<>();
    private Seat seat = new Seat();
    private int selectedSeat;
    private String seatName = "SEAT_NULL";

    @PostConstruct
    public void postConstruct() {
        contactInfo = new ContactInfo();
        segment = new Segment();
        ticket = new Ticket();
        paymentTypes = paymentTypeService.findAll();
        paymentTypes();
        pax = new Pax();
        paxList = new ArrayList<>();
        ticketList = new ArrayList<>();
    }

    public void paymentTypes() {
        paymentTypes = paymentTypeService.findAll();
    }

    public void saveContactInfo() {
        ticketService.saveContact(contactInfo);
    }

    public void saveSegment() {
        segmentList.add(segment);
        ticketService.saveSegment(segment);
        for (Pax aPaxList : paxList) {
            Seat s = new Seat();
            Coupon c = new Coupon();
            Ticket t = new Ticket();
            Pax tempPax = aPaxList;
            t.setPax(tempPax);
            c.setTicket(t);
            s.setCoupon(c);
            s.setSegment(segment);
            seats.add(s);
        }
        segment = new Segment();
    }

    public void savePnr() {
        ticketService.savePnr(pnr);
    }

    public void savePax() {
        paxList.add(pax);
        ticketService.savePax(pax, contactInfo, pnr);
        for (Segment aSegmentList : segmentList) {
            Seat s = new Seat();
            Coupon c = new Coupon();
            Ticket t = new Ticket();
            Pax tempPax;
            tempPax = pax;
            t.setPax(tempPax);
            c.setTicket(t);
            s.setCoupon(c);
            s.setSegment(aSegmentList);
            seats.add(s);
        }
        pax = new Pax();
    }

    public void allocateSeat() {
        seats = new ArrayList<>(paxList.size() * segmentList.size());
    }

    public void seatSelect(Seat s) {
        selectedSeat = seats.indexOf(s);
    }

    public void saveTicket() {
        ticketList.add(ticket);
        ticketService.saveTicket(ticket, pnr, paxList.get(paxList.size() - 1), segmentList, seats);
        ticket = new Ticket();
    }


    public List<String> getPortNames() {
        portNames = portService.listPortNames();
        return portNames;
    }

    public List<String> getPortCodes() {
        portCodes = portService.listPortCodes();
        return portCodes;

    }

    public void setPortCodes(List<String> portCodes) {
        this.portCodes = portCodes;
    }

    public void setPortNames(List<String> portNames) {
        this.portNames = portNames;
    }

    public List<String> complete(String query) {
        List<String> tempPortList = getPortNames();
        List<String> codeList = getPortCodes();
        List<String> completeList = new ArrayList<>();

        for (int i = 0; i < tempPortList.size(); i++) {
            if (tempPortList.get(i).toLowerCase().contains(query.toLowerCase())) {
                completeList.add(tempPortList.get(i) + " (" + codeList.get(i) + ")");
            }
        }
        completeList.sort(String::compareToIgnoreCase);
        return completeList;
    }

    public void segmentSelect() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect(externalContext.getRequestContextPath() + "/ucakBilet.jsf");
    }

    public int indexOfSeats(Seat s) {
        for (int i = 0; i < seats.size(); i++) {
            if (seats.get(i).getCoupon().getTicket().getPax().getName().
                    equals(s.getCoupon().getTicket().getPax().getName()) &&
                    seats.get(i).getCoupon().getTicket().getPax().getSurname().
                            equals(s.getCoupon().getTicket().getPax().getSurname()) &&
                    seats.get(i).getSegment().getPortByDepPort().getCode().
                            equals(s.getSegment().getPortByDepPort().getCode()) &&
                    seats.get(i).getSegment().getPortByArrPort().getCode().
                            equals(s.getSegment().getPortByArrPort().getCode()) &&
                    seats.get(i).getSegment().getDepDate().
                            equals(s.getSegment().getDepDate())) {
                selectedSeat = i;
                return i;

            }
        }
        return 0;
    }

    public TicketService getTicketService() {
        return ticketService;
    }

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public PortService getPortService() {
        return portService;
    }

    public PaymentTypeService getPaymentTypeService() {
        return paymentTypeService;
    }

    public void setPaymentTypeService(PaymentTypeService paymentTypeService) {
        this.paymentTypeService = paymentTypeService;
    }

    public void setPortService(PortService portService) {
        this.portService = portService;

    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public void setPaymentTypes(List<PaymentType> paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    public List<PaymentType> getPaymentTypes() {
        return paymentTypes;
    }

    public TicketBean() {
        //
    }

    public List<Port> getPort() {
        portList = portService.findAll();
        return portList;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public List<Port> getPortList() {
        return portList;
    }

    public void setPortList(List<Port> portList) {
        this.portList = portList;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public List<Segment> getSegmentList() {
        return segmentList;
    }

    public void setSegmentList(List<Segment> segmentList) {
        this.segmentList = segmentList;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public Pax getPax() {
        return pax;
    }

    public void setPax(Pax pax) {
        this.pax = pax;
    }

    public List<Pax> getPaxList() {
        return paxList;
    }

    public void setPaxList(List<Pax> paxList) {
        this.paxList = paxList;
    }

    public Pnr getPnr() {
        return pnr;
    }

    public void setPnr(Pnr pnr) {
        this.pnr = pnr;
    }

    public List<Pnr> getPnrList() {
        return pnrList;
    }

    public void setPnrList(List<Pnr> pnrList) {
        this.pnrList = pnrList;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<ContactInfo> getContactInfoList() {
        return contactInfoList;
    }

    public void setContactInfoList(List<ContactInfo> contactInfoList) {
        this.contactInfoList = contactInfoList;
    }


    public SeatService getSeatService() {
        return seatService;
    }

    public void setSeatService(SeatService seatService) {
        this.seatService = seatService;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Segment getSeatSegment() {
        System.out.println("getterseatsegment" + seatSegment.toString());

        return seatSegment;
    }

    public void setSeatSegment(Segment seatSegment) {

        System.out.println("setterseatsegment");
        this.seatSegment = seatSegment;
    }

    public int getSelectedSeat() {
        return selectedSeat;
    }

    public void setSelectedSeat(int selectedSeat) {
        this.selectedSeat = selectedSeat;
    }
}