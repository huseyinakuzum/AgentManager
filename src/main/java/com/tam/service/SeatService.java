package com.tam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tam.model.Seat;
import com.tam.repositories.SeatRepository;

@Service("seatService")
public class SeatService {

	@Autowired
	private SeatRepository seatRepository;

	
	public void save(Seat seat) {
		seatRepository.save(seat);
	}

	public SeatRepository getSeatRepository() {
		return seatRepository;
	}

	public void setSeatRepository(SeatRepository seatRepository) {
		this.seatRepository = seatRepository;
	}

}
