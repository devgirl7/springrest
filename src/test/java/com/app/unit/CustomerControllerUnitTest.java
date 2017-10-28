package com.app.unit;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.app.controller.customer.CustomerController;
import com.app.entity.customer.Address;
import com.app.entity.customer.Customer;
import com.app.entity.customer.Address.AddressType;
import com.app.service.customer.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerUnitTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CustomerService customerService;
	
	private static final List<Customer> customers = initialize();
	
	private static List<Customer> initialize() {
		Address address = new Address().setAddress("Winterfell").setType(AddressType.WORK);
		Customer customer = new Customer(1L).setFirstName("Jon")
										.setLastName("Snow")
									 	.setAddresses(new HashSet<>(Collections.singletonList(address)));
		return Collections.singletonList(customer);
	}
	
	@Test
	public void testGetAllCustomers() throws Exception {
		when(customerService.findAll()).thenReturn(customers);
		
		mockMvc.perform(get("/customer").with(SecurityMockMvcRequestPostProcessors.user("user").password("password")))
		 		.andExpect(status().isOk())
		 		.andExpect(jsonPath("$", hasSize(1)))
		 		.andExpect(jsonPath("$[0].id", Is.is(1)))
		 		.andExpect(jsonPath("$[0].firstName", Is.is("Jon")))
		 		.andExpect(jsonPath("$[0].lastName", Is.is("Snow")));
		 
		 verify(customerService, times(1)).findAll();
	     verifyNoMoreInteractions(customerService);
	}
	
	@Test
	public void testGetCustomer() throws Exception {
		when(customerService.get(1L)).thenReturn(customers.get(0));
		
		mockMvc.perform(get("/customer/{id}", 1).with(SecurityMockMvcRequestPostProcessors.user("user").password("password")))
 				.andExpect(status().isOk())
 				.andExpect(jsonPath("$.results.firstName", Is.is("Jon")))
		 		.andExpect(jsonPath("$.results.lastName", Is.is("Snow")));
		
		verify(customerService, times(1)).get(1L);
	    verifyNoMoreInteractions(customerService);
	}
	
	@Test
	public void testCreateCustomer() throws Exception {
		Address address = new Address().setAddress("Castle Black").setType(AddressType.HOME);
		Customer customer = new Customer(1L).setFirstName("Tormund")
										.setLastName("Giantsbane")
									 	.setAddresses(new HashSet<>(Collections.singletonList(address)));
		when(customerService.save(customer)).thenReturn(customer);
		
		mockMvc.perform(post("/customer").with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(new ObjectMapper().writeValueAsString(customer)))
	            		.andExpect(status().isCreated())
	            		.andExpect(header().string("location", containsString("http://localhost/customer/1")));
		
	    verify(customerService, times(1)).save(customer);
	    verifyNoMoreInteractions(customerService);
	}
	
	@Test
	public void testUpdateCustomer() throws Exception {
		when(customerService.update(customers.get(0))).thenReturn(ResponseEntity.ok().build());
		
		mockMvc.perform(put("/customer").with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customers.get(0))))
                		.andExpect(status().isOk());
		
		verify(customerService, times(1)).update(customers.get(0));
        verifyNoMoreInteractions(customerService);
	}
	
	@Test
	public void testDeleteCustomer() throws Exception {
		when(customerService.delete(customers.get(0).getId())).thenReturn(ResponseEntity.ok().build());
		
		mockMvc.perform(delete("/customer/{id}", customers.get(0).getId())
				.with(SecurityMockMvcRequestPostProcessors.user("user").password("password")))
                .andExpect(status().isOk());

        verify(customerService, times(1)).delete(customers.get(0).getId());
        verifyNoMoreInteractions(customerService);
	}
	
	@Test
	public void testDeleteUserNotFound() throws Exception {
		when(customerService.delete(0L)).thenReturn(ResponseEntity.notFound().build());
		
		mockMvc.perform(delete("/customer/{id}", 0L)
				.with(SecurityMockMvcRequestPostProcessors.user("user").password("password")))
                .andExpect(status().isNotFound());

        verify(customerService, times(1)).delete(0L);
        verifyNoMoreInteractions(customerService);
	}
}
