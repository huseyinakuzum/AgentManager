package com.tam.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.tam.model.ContactInfo;
import com.tam.model.Coupon;
import com.tam.model.Pax;
import com.tam.model.Pnr;
import com.tam.model.Port;
import com.tam.model.Segment;
import com.tam.model.Ticket;
import com.tam.service.PortService;
import com.tam.service.SearchService;
import com.tam.service.TicketService;

@ManagedBean
@ViewScoped
public class SearchBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{ticketService}")
    TicketService ticketService;

    @ManagedProperty(value = "#{portService}")
    PortService portService;

    @ManagedProperty(value = "#{searchService}")
    SearchService searchService;

    @ManagedProperty(value = "#{ticketBean}")
    TicketBean ticketBean;

    private Pnr pnr = new Pnr();
    private Segment segment = new Segment();
    private Date date = new Date();
    private Date date1 = new Date();
    private Date date2 = new Date();
    private Date date3 = new Date();
    private Pax pax = new Pax();
    private List<Pax> paxes = new ArrayList<>();
    private List<Segment> segments = new ArrayList<>();
    private Port port1 = new Port();
    private Port port2 = new Port();
    private List<String> portNames = new ArrayList<>();
    private List<String> portCodes = new ArrayList<>();
    private boolean searchPaxByPnrB;
    private boolean searchPaxesBetweenDatesB;
    private boolean searchPaxesByDepPortAndArrPortAndDepDateB;
    private Logger logger = Logger.getLogger(getClass().getName());

    private int index;

    @PostConstruct
    public void postConstruct() {
        port1 = new Port();
        port2 = new Port();
        date = new Date();
        port1.setName("");
        port2.setName("");
        segment = new Segment();
        pax = new Pax();
        segment.setPortByArrPort(port1);
        segment.setPortByDepPort(port2);
        segment.setDepDate(date);
        paxes = new ArrayList<>();
        segments = new ArrayList<>();
        searchPaxByPnrB = false;
        searchPaxesBetweenDatesB = false;
        searchPaxesByDepPortAndArrPortAndDepDateB = false;
    }


    public void searchFlight() {
        System.out.println(segment.getAaCode());
        segment = searchService.searchFlight(segment.getAaCode(), segment.getFltNo());
        System.out.println(segment.getPortByArrPort().getName());
    }

    public List<Pax> searchPaxByPnr() {
        paxes = searchService.searchPaxByPnr(pnr.getPnrNo());
        searchPaxByPnrB = paxes.size() != 0;
        return paxes;
    }

    public List<Segment> searchSegmentByDate() {
        segments = searchService.searchSegmentByDate(segment.getDepDate());
        return segments;
    }

    public List<Segment> searchSegmentBetweenDates() {
        segments = searchService.searchSegmentBetweenDates(date1, date2);
        return segments;
    }

    public List<Pax> searchPaxesBetweenDates() {
        paxes = searchService.searchPaxesBetweenDates(date1, date2);
        searchPaxesBetweenDatesB = paxes.size() != 0;
        return paxes;
    }

    public List<Pax> searchPaxesByDepPortAndArrPortAndDepDate() {
        paxes = searchService.searchPaxesByDepPortAndArrPortAndDepDate(port1, port2, date3);
        searchPaxesByDepPortAndArrPortAndDepDateB = paxes.size() != 0;
        return paxes;
    }

    public int getSelectedRowIndex(int index) {
        this.index = index;
        return this.index;
    }

    public void onRowSelect(SelectEvent event) throws IOException {

        ticketBean.setPaxList(new ArrayList<Pax>());
        ticketBean.setContactInfo(new ContactInfo());
        ticketBean.setPnr(new Pnr());
        ticketBean.setTicketList(new ArrayList<Ticket>());
        ticketBean.setSegmentList(new ArrayList<Segment>());
        ticketBean.getPaxList().add(paxes.get(index));
        ticketBean.setContactInfo(searchService.findContactInfo(paxes.get(index)));
        ticketBean.setPnr(searchService.findPnr(paxes.get(index)));
        ticketBean.setTicketList(searchService.findTicketByPnr(paxes.get(index).getPnr()));
        for (Ticket t : searchService.findTicketByPnr(paxes.get(index).getPnr())) {
            for (Coupon c : searchService.findCouponByTicket(t)) {
                Segment s = searchService.findSegmentByCoupon(c);
                s.setPortByArrPort(searchService.findArrPortByCoupon(c));
                s.setPortByDepPort(searchService.findDepPortByCoupon(c));
                ticketBean.getSegmentList().add(s);
            }
        }

        FacesContext.getCurrentInstance().getExternalContext().redirect("ucakBilet.jsf?");

    }

    public List<String> getPortCodes() {
        portCodes = portService.listPortCodes();
        return portCodes;

    }

    public void onRowUnselect(UnselectEvent event) {

        FacesMessage msg = new FacesMessage("Pax Unselected");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void setPortCodes(List<String> portCodes) {
        this.portCodes = portCodes;
    }

    public void setPortNames(List<String> portNames) {
        this.portNames = portNames;
    }

    public List<String> getPortNames() {
        portNames = portService.listPortNames();
        return portNames;
    }

    public List<String> complete(String query) {
        List<String> portList = getPortNames();
        List<String> codeList = getPortCodes();
        List<String> completeList = new ArrayList<>();

        for (String aPortList : portList) {
            if (aPortList.toLowerCase().contains(query.toLowerCase())) {
                completeList.add(aPortList);
            }
        }
        completeList.sort(String::compareToIgnoreCase);
        return completeList;
    }

    public Pnr getPnr() {
        return pnr;
    }

    public void setPnr(Pnr pnr) {
        this.pnr = pnr;
    }

    public Pax getPax() {
        return pax;
    }

    public void setPax(Pax pax) {
        this.pax = pax;
    }

    public List<Pax> getPaxes() {
        return paxes;
    }

    public void setPaxes(List<Pax> paxes) {
        this.paxes = paxes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public Port getPort1() {
        return port1;
    }

    public void setPort1(Port port) {
        this.port1 = port;
    }

    public Port getPort2() {
        return port2;
    }

    public void setPort2(Port port) {
        this.port2 = port;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public SearchService getSearchService() {
        return searchService;
    }

    public List<Segment> getSegmetns() {
        return segments;
    }

    public void setSegmetns(List<Segment> segments) {
        this.segments = segments;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
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

    public void setPortService(PortService portService) {
        this.portService = portService;
    }

    public Date getDate3() {
        return date3;
    }

    public void setDate3(Date date3) {
        this.date3 = date3;
    }

    public boolean isSearchPaxByPnrB() {
        return searchPaxByPnrB;
    }

    public void setSearchPaxByPnrB(boolean searchPaxByPnrB) {
        this.searchPaxByPnrB = searchPaxByPnrB;
    }

    public boolean isSearchPaxesBetweenDatesB() {
        return searchPaxesBetweenDatesB;
    }

    public void setSearchPaxesBetweenDatesB(boolean searchPaxesBetweenDatesB) {
        this.searchPaxesBetweenDatesB = searchPaxesBetweenDatesB;
    }

    public boolean isSearchPaxesByDepPortAndArrPortAndDepDateB() {
        return searchPaxesByDepPortAndArrPortAndDepDateB;
    }

    public void setSearchPaxesByDepPortAndArrPortAndDepDateB(boolean searchPaxesByDepPortAndArrPortAndDepDateB) {
        this.searchPaxesByDepPortAndArrPortAndDepDateB = searchPaxesByDepPortAndArrPortAndDepDateB;
    }

    public TicketBean getTicketBean() {
        return ticketBean;
    }

    public void setTicketBean(TicketBean ticketBean) {
        this.ticketBean = ticketBean;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
