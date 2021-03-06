package com.tam.model;

// Generated Apr 26, 2015 5:10:28 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * PaymentType generated by hbm2java
 */
@Entity
@Table(name = "payment_type", catalog = "tam")
public class PaymentType implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String code;
	private String explanation;
	private Set<Ticket> tickets = new HashSet<Ticket>(0);

	public PaymentType() {
	}

	public PaymentType(String code) {
		this.code = code;
	}

	public PaymentType(String code, String explanation, Set<Ticket> tickets) {
		this.code = code;
		this.explanation = explanation;
		this.tickets = tickets;
	}

	@Id
	@Column(name = "code", unique = true, nullable = false, length = 2)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "explanation", length = 200)
	public String getExplanation() {
		return this.explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "paymentType")
	public Set<Ticket> getTickets() {
		return this.tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}

}
