package com.tam.model;

// Generated Apr 26, 2015 5:10:28 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ContactInfo generated by hbm2java
 */
@Entity
@Table(name = "contact_info", catalog = "tam")
public class ContactInfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Integer id;
	
	
	private String email;
	private String mobile;
	private String homePhone;
	private String companyPhone;
	private String address;
	private Set<Guide> guides = new HashSet<Guide>(0);
	private Set<Pax> paxes = new HashSet<Pax>(0);

	public ContactInfo() {
	}

	public ContactInfo(String email, String mobile, String homePhone,
			String companyPhone, String address, Set<Guide> guides, Set<Pax> paxes) {
		this.email = email;
		this.mobile = mobile;
		this.homePhone = homePhone;
		this.companyPhone = companyPhone;
		this.address = address;
		this.guides = guides;
		this.paxes = paxes;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "email", length = 45)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "mobile", length = 18)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "home_phone", length = 18)
	public String getHomePhone() {
		return this.homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	@Column(name = "company_phone", length = 18)
	public String getCompanyPhone() {
		return this.companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	@Column(name = "address", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "contactInfo")
	public Set<Guide> getGuides() {
		return this.guides;
	}

	public void setGuides(Set<Guide> guides) {
		this.guides = guides;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "contactInfo")
	public Set<Pax> getPaxes() {
		return this.paxes;
	}

	public void setPaxes(Set<Pax> paxes) {
		this.paxes = paxes;
	}

}
