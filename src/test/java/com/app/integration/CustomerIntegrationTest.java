package com.app.integration;

import java.text.SimpleDateFormat;
import java.util.HashSet;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.collection.IsEmptyCollection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.app.entity.customer.Address;
import com.app.entity.customer.Address.AddressType;
import com.app.models.ResponseVO;
import com.app.entity.customer.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class CustomerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testCreateCustomer() throws Exception {
		Address address = new Address().setAddress("Castle Black").setType(AddressType.WORK);
		Customer customer = new Customer().setFirstName("Tormund")
										.setLastName("Giantsbane")
										.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse("21/12/2000"))
										.setAddresses(new HashSet<Address>() {{add(address);}});
		
		ObjectMapper mapper = new ObjectMapper();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer")
				.with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
				.content(mapper.writeValueAsString(customer))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.header().string("location", Matchers.notNullValue()));
	}
	
	@Test
	public void testCreateCustomerWithMissingMandatoryParams() throws Exception {
		Customer customer = new Customer().setLastName("last");
		
		ObjectMapper mapper = new ObjectMapper();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer")
				.with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
				.content(mapper.writeValueAsString(customer))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message", Is.is("error.firstname.notnull")));
	}
	
	@Test
	public void testGetAllCustomerDetails() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customer")
				.with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
				.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Is.is(IsNot.not(IsEmptyCollection.empty()))));
	}
	
	@Test
	public void testGetCustomerDetailByFirstName() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customer")
				.with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
				.param("name", "Jon")
				.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("[?(@.firstName=='Jon')]", IsCollectionWithSize.hasSize(1)));
	}
	
	@Test
	public void testUpdateCustomerDetails() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customer/1")
				.with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
				.contentType(MediaType.APPLICATION_JSON);
		ResponseVO<Customer> response = new ObjectMapper().readValue(
				mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString(), new TypeReference<ResponseVO<Customer>>() {});
		
		//Update Last Name Type
		ObjectMapper mapper = new ObjectMapper();
		RequestBuilder updateRequestBuilder = MockMvcRequestBuilders.put("/customer")
				.with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
				.content(mapper.writeValueAsString(response.getResults().setLastName("Targaryen")))
				.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(updateRequestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testDeleteCustomerDetails() throws Exception {
		// Customer no longer exists (ref: data.sql)
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/customer/2")
				.with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
				.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	public void testUnauthorizedRequest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/customer/1")
				.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}
}
