package com.tam.model;

// Generated Apr 26, 2015 5:10:28 PM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * MuseumFare generated by hbm2java
 */
@Entity
@Table(name = "museum_fare", catalog = "tam")
public class MuseumFare implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Museum museum;
	private double adultFare;
	private double childFare;
	private double discountFare;
	private String currency;

	public MuseumFare() {
	}

	public MuseumFare(Museum museum, double adultFare, double childFare,
			double discountFare, String currency) {
		this.museum = museum;
		this.adultFare = adultFare;
		this.childFare = childFare;
		this.discountFare = discountFare;
		this.currency = currency;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "museum_id", nullable = false)
	public Museum getMuseum() {
		return this.museum;
	}

	public void setMuseum(Museum museum) {
		this.museum = museum;
	}

	@Column(name = "adult_fare", nullable = false, precision = 22, scale = 0)
	public double getAdultFare() {
		return this.adultFare;
	}

	public void setAdultFare(double adultFare) {
		this.adultFare = adultFare;
	}

	@Column(name = "child_fare", nullable = false, precision = 22, scale = 0)
	public double getChildFare() {
		return this.childFare;
	}

	public void setChildFare(double childFare) {
		this.childFare = childFare;
	}

	@Column(name = "discount_fare", nullable = false, precision = 22, scale = 0)
	public double getDiscountFare() {
		return this.discountFare;
	}

	public void setDiscountFare(double discountFare) {
		this.discountFare = discountFare;
	}

	@Column(name = "currency", nullable = false, length = 3)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
