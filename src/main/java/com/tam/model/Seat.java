package com.tam.model;

import org.hibernate.annotations.Cascade;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "seat", catalog = "tam")
public class Seat implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "seat_name", length = 5)
    private String seatName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cpn_id")
    private Coupon coupon;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "record_date", length = 19)
    private Date recordDate;

    @ManyToOne
    @JoinColumn(name = "seg_id")
    private Segment segment;

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }


    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

}
