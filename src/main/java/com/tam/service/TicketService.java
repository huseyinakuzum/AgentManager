package com.tam.service;

import java.util.*;
import java.util.logging.Logger;

import com.tam.model.*;
import com.tam.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ticketService")
public class TicketService {

    private final TicketRepository ticketRepository;
    private final PaxRepository paxRepository;
    private final PnrRepository pnrRepository;
    private final SegmentRepository segmentRepository;
    private final CouponRepository couponRepository;
    private final ContactInfoRepository contactInfoRepository;
    private final UserRepository userRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final PortRepository portRepository;
    private final SeatRepository seatRepository;
    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public TicketService(TicketRepository ticketRepository, PaxRepository paxRepository, PnrRepository pnrRepository, SegmentRepository segmentRepository, CouponRepository couponRepository, ContactInfoRepository contactInfoRepository, UserRepository userRepository, PaymentTypeRepository paymentTypeRepository, PortRepository portRepository, SeatRepository seatRepository) {
        this.ticketRepository = ticketRepository;
        this.paxRepository = paxRepository;
        this.pnrRepository = pnrRepository;
        this.segmentRepository = segmentRepository;
        this.couponRepository = couponRepository;
        this.contactInfoRepository = contactInfoRepository;
        this.userRepository = userRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.portRepository = portRepository;
        this.seatRepository = seatRepository;
    }


    @Transactional
    public void saveContact(ContactInfo contactInfo) {
        contactInfoRepository.save(contactInfo);
    }

    @Transactional
    public void saveSegment(Segment segment) {
        System.out.println(segment.getPortByDepPort().getName() + " + " + segment.getPortByArrPort().getName());

        segment.setPortByDepPort(portRepository.findPortByName(
                segment.getPortByDepPort().getName().substring(0, segment.getPortByDepPort().getName().length() - 6)));
        segment.setPortByArrPort(portRepository.findPortByName(
                segment.getPortByArrPort().getName().substring(0, segment.getPortByArrPort().getName().length() - 6)));
        segment.setAaCode(segment.getAaCode().toUpperCase());
        if (segmentRepository.findSegmentByAaCodeAndFltNo(segment.getAaCode(), segment.getFltNo()) != null)
            return;
        Date dt = new Date();
        segment.setRecordDate(dt);
        segmentRepository.save(segment);
        segment = new Segment();
    }

    @Transactional
    public void savePnr(Pnr pnr) {
        pnr.setUser(userRepository.findOne(1));
        Date dt = new Date();

        pnr.setRecordDate(dt);
        pnrRepository.save(pnr);
        pnr = new Pnr();
    }

    @Transactional
    public void savePax(Pax pax, ContactInfo contactInfo, Pnr pnr) {
        pax.setPnr(pnr);
        pax.setContactInfo(contactInfo);
        pax.setCompany(pax.getCompany().toUpperCase());
        pax.setName(pax.getName().toUpperCase());
        pax.setPassportNo(pax.getPassportNo().toUpperCase());
        pax.setSurname(pax.getSurname().toUpperCase());
        Date dt = new Date();
        pax.setRecordDate(dt);
        paxRepository.save(pax);
    }

    @Transactional
    public void saveTicket(Ticket ticket, Pnr pnr, Pax pax, List<Segment> segments, Seat seat) {
        Date dt = new Date();
        Ticket tc = ticket;
        // Create a coupons set
        Set<Coupon> coupons = new HashSet<Coupon>();
        Coupon tempCoupon = new Coupon();
        // Create coupons for each segment in list
        for (Segment segment : segments) {
            if (segment != null) {
                Coupon coupon = new Coupon();
                coupon.setSegment(segment);
                coupon.setRecordDate(dt);
                coupon.setTicket(tc);
                couponRepository.save(coupon);
                coupons.add(coupon);
                tempCoupon = coupon;
                coupon = new Coupon();
            }
        }
        seat.setCoupon(tempCoupon);
        ticket.setSellingCurrency(ticket.getBuyingCurrency());
        ticket.setPnr(pnr);
        ticket.setUser(userRepository.findOne(1));
        ticket.setPax(pax);
        ticket.setCoupons(coupons);
        seatRepository.save(seat);
        ticketRepository.save(ticket);
    }

}
