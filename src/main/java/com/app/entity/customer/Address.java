package com.app.entity.customer;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.app.entity.PersistentObject;
import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "ADDRESS")
public class Address extends PersistentObject {
	
	/**
	 * TODO Auto-generated
	 */
	private static final long serialVersionUID = 1989992806395476431L;

	@Id
    @GeneratedValue(strategy = IDENTITY)
	@ApiModelProperty(notes = "The database generated address ID", readOnly= true)
    @Column(name = "ADDRESS_ID", nullable = false)
	private Long addressId;
	
	@ApiModelProperty(notes = "Address Details of the customer", required = false)
	@Column(name = "ADDRESS_DETAILS", nullable = true, length = 250)
	private String address;
	
	@ApiModelProperty(notes = "Type of address", required = false)
	@Enumerated(EnumType.STRING)
	private AddressType type;
	
	@JsonBackReference
	@ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	public Address() {
		
	}
	
	public enum AddressType {
	    HOME,
	    WORK,
	    OFFICE,
	    EMAIL
	}

	@Override
	public Long getId() {
		return this.addressId;
	}
	
	public void setId(Long addressId) {
		this.addressId = addressId;
	}

	public String getAddress() {
		return address;
	}

	public Address setAddress(String address) {
		this.address = address;
		return this;
	}
	
	public AddressType getType() {
		return type;
	}

	public Address setType(AddressType type) {
		this.type = type;
		return this;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public Address setCustomer(Customer customer) {
		this.customer = customer;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
