package com.tam.service;

import com.tam.model.Coupon;
import com.tam.model.Segment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tam.model.Seat;
import com.tam.repositories.SeatRepository;
import org.springframework.transaction.annotation.Transactional;

@Service("seatService")
public class SeatService {

	@Autowired
	private SeatRepository seatRepository;

	@Transactional
	public void save(Seat seat, Segment segment, Coupon coupon) {
		seat.setSegment(segment);
		seat.setCoupon(coupon);
		segment.addSeat(seat);
		seatRepository.save(seat);
	}

	public SeatRepository getSeatRepository() {
		return seatRepository;
	}

	public void setSeatRepository(SeatRepository seatRepository) {
		this.seatRepository = seatRepository;
	}

}
