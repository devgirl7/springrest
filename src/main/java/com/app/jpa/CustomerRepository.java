package com.app.jpa;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.customer.Customer;

@Transactional
@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
	List<Customer> findByFirstName(String firstName);
}
