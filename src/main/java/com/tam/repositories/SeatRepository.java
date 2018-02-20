package com.tam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tam.model.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer>{

}
