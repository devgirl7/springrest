package com.app.entity.customer;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.app.entity.PersistentObject;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "CUSTOMER")
public class Customer extends PersistentObject {

	/**
	 * TODO Auto-generated
	 */
	private static final long serialVersionUID = 7434477496654894976L;

	public Customer() {}
	
	public Customer(Long customerId) {
		this.customerId = customerId;
	}
	
	@Id
    @GeneratedValue(strategy = IDENTITY)
	@ApiModelProperty(notes = "The database generated customer ID", readOnly= true)
    @Column(name = "CUSTOMER_ID", nullable = false)
	private Long customerId;
	
	@ApiModelProperty(notes = "First name of the customer", required = true)
	@NotNull(message = "error.firstname.notnull")
	@Size(max = 100, message = "error.firstname.size.exceeded")
	@Column(name = "FIRST_NAME", nullable = false, length = 100)
	private String firstName;
	
	@ApiModelProperty(notes = "Last name of the customer")
	@Column(name = "LAST_NAME", nullable = true, length = 100)
	private String lastName;
	
	@ApiModelProperty(notes = "Date of Birth of the customer")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DOB", nullable = true)
	private Date dateOfBirth;
	
	@ApiModelProperty(notes = "List of all customer addresses")
	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, mappedBy="customer", cascade = CascadeType.ALL)
    private Collection<Address> addresses;

	@Override
	public Long getId() {
		return this.customerId;
	}
	
	/**
	 * Set the customer id
	 * 
	 * @param customerId id of the Customer
	 */
	public void setId(Long customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public Customer setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public Customer setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public Customer setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}

	public Collection<Address> getAddresses() {
		return addresses;
	}

	public Customer setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
		return this;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
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
		Customer other = (Customer) obj;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}
	
}
