package com.app.controller.customer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.app.entity.customer.Customer;
import com.app.models.ResponseVO;
import com.app.service.customer.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/customer")
@Api(tags = { "Customer" }, description = "Rest Service to Create, Read, Update and Delete customer profile")
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	@ApiOperation(value = "Retrieve list of all customers with details in the system", response = Customer.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list of all customers") })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<Customer>> findAll(
			@ApiParam(name = "name", value = "First name of the customer", required = false) @RequestParam(required = false, name = "name") String firstName) {
		if (firstName != null) {
			return ResponseEntity.ok(customerService.findByName(firstName));
		}
		return ResponseEntity.ok(customerService.findAll());
	}

	@ApiOperation(value = "Retrieve a customer based on the passed ID", response = Customer.class, responseReference = "results")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the customer") })
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Customer>> get(
			@ApiParam(name = "id", value = "Internal Id of the customer", required = true) @PathVariable("id") Long customerId) {
		Customer customerDetails = customerService.get(customerId);
		return ResponseEntity.ok(new ResponseVO<>(customerDetails));
	}

	@ApiOperation(value = "Persist customer details in the system")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created the customer") })
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(HttpServletRequest request,
			@ApiParam(value = "Customer Object to be persisted", required = true) @Validated @RequestBody Customer customer) {
		customerService.save(customer);
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
		uriBuilder.path("/{id}");
		return ResponseEntity.created(uriBuilder.buildAndExpand(customer.getId()).toUri()).build();
	}

	@ApiOperation(value = "Update customer details in the system")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated the customer"),
			@ApiResponse(code = 404, message = "Cannot find the customer details") })
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(
			@ApiParam(value = "Customer Object to be persisted", required = true) @Validated @RequestBody Customer customer) {
		return customerService.update(customer);
	}

	@ApiOperation(value = "Delete customer details in the system")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Successfully deleted the customer"),
			@ApiResponse(code = 404, message = "Cannot find the customer details") })
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delete(
			@ApiParam(name = "id", value = "Internal Id of the customer to be deleted", required = true) @PathVariable("id") Long id) {
		logger.debug("Deleting all customer details {}", id);
		return customerService.delete(id);
	}
}
