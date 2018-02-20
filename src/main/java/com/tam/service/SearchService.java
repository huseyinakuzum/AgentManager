package com.tam.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tam.model.ContactInfo;
import com.tam.model.Coupon;
import com.tam.model.Pax;
import com.tam.model.Pnr;
import com.tam.model.Port;
import com.tam.model.Segment;
import com.tam.model.Ticket;
import com.tam.repositories.ContactInfoRepository;
import com.tam.repositories.CouponRepository;
import com.tam.repositories.PaxRepository;
import com.tam.repositories.PaymentTypeRepository;
import com.tam.repositories.PnrRepository;
import com.tam.repositories.PortRepository;
import com.tam.repositories.SegmentRepository;
import com.tam.repositories.TicketRepository;
import com.tam.repositories.UserRepository;

import java.util.logging.Logger;

@Service("searchService")
public class SearchService {

    private final TicketRepository ticketRepository;
    private final PaxRepository paxRepository;
    private final PnrRepository pnrRepository;
    private final SegmentRepository segmentRepository;
    private final CouponRepository couponRepository;
    private final ContactInfoRepository contactInfoRepository;
    private final UserRepository userRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final PortRepository portRepository;

    private Logger logger = Logger.getLogger(getClass().getName());
    public List<Segment> segments = new ArrayList<>();

    @Autowired
    public SearchService(TicketRepository ticketRepository, PaxRepository paxRepository, PnrRepository pnrRepository, SegmentRepository segmentRepository, CouponRepository couponRepository, ContactInfoRepository contactInfoRepository, UserRepository userRepository, PaymentTypeRepository paymentTypeRepository, PortRepository portRepository) {
        this.ticketRepository = ticketRepository;
        this.paxRepository = paxRepository;
        this.pnrRepository = pnrRepository;
        this.segmentRepository = segmentRepository;
        this.couponRepository = couponRepository;
        this.contactInfoRepository = contactInfoRepository;
        this.userRepository = userRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.portRepository = portRepository;
    }

    @Transactional
    public Segment searchFlight(String aaCode, String fltNo) {
        return segmentRepository.findSegmentByAaCodeAndFltNo(aaCode, fltNo);
    }

    @Transactional
    public List<Pax> searchPaxByPnr(String pnrNo) {
        return paxRepository.findByPnr(pnrRepository.findByPnrNo(pnrNo));
    }

    @Transactional
    public List<Segment> searchSegmentByDate(Date depDate) {
        return segmentRepository.findByDepDate(depDate);
    }

    @Transactional
    public List<Segment> searchSegmentBetweenDates(Date depDate1, Date depDate2) {
        return segmentRepository.findByDepDateBetween(depDate1, depDate2);
    }

    @Transactional
    public List<Pax> searchPaxesBetweenDates(Date depDate1, Date depDate2) {
        List<Coupon> coupons = new ArrayList<>();
        List<Pax> paxes = new ArrayList<>();
        for (Segment s : segmentRepository.findByDepDateBetween(depDate1, depDate2)) {
            coupons.addAll(couponRepository.findBySegment(s));
        }
        for (Coupon c : coupons) {
            paxes.add(c.getTicket().getPax());
            logger.info(c.getTicket().getPax().getName() + "     " + c.getTicket().getPax().getSurname());
        }
        return paxes;
    }


    @Transactional
    public List<Pax> searchPaxesByDepPortAndArrPortAndDepDate(Port port1, Port port2, Date date) {
        List<Coupon> coupons = couponRepository.findBySegment(segmentRepository
                .findByPortByDepPortAndPortByArrPortAndDepDate(portRepository.findPortByName(port1.getName()),
                        portRepository.findPortByName(port2.getName()), date));
        List<Pax> paxes = new ArrayList<>();
        for (Coupon c : coupons) {
            paxes.add(c.getTicket().getPax());
        }
        return paxes;
    }

    @Transactional
    public ContactInfo findContactInfo(Pax pax) {
        return contactInfoRepository.findById(pax.getContactInfo().getId());
    }

    @Transactional
    public List<Ticket> findTicketByPnr(Pnr pnr) {
        return ticketRepository.findByPnr(pnr);
    }

    @Transactional
    public List<Coupon> findCouponByTicket(Ticket ticket) {
        return couponRepository.findByTicket(ticket);
    }


    @Transactional
    public Pnr findPnr(Pax pax) {
        return pnrRepository.findById(pax.getPnr().getId());
    }

    @Transactional
    public Segment findSegmentByCoupon(Coupon c) {
        return segmentRepository.findByCouponId(c.getId());
    }

    @Transactional
    public Port findArrPortByCoupon(Coupon c) {
        return portRepository.findByArrPortByCouponId(c.getId());
    }

    @Transactional
    public Port findDepPortByCoupon(Coupon c) {
        return portRepository.findByDepPortByCouponId(c.getId());
    }


}
