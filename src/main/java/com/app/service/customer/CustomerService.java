package com.app.service.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.app.entity.customer.Customer;
import com.app.jpa.CustomerRepository;
import com.app.service.GenericService;

@Service
public class CustomerService implements GenericService<Customer, Long> {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public Long getId(Customer entity) {
		return entity.getId();
	}

	@Override
	public CrudRepository<Customer, Long> getRepository() {
		return this.customerRepository;
	}
	
	public List<Customer> findByName(String firstName) {
		return customerRepository.findByFirstName(firstName);
	}
}
