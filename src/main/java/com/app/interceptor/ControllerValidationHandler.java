package com.app.interceptor;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import com.app.models.ErrorMessageDTO;

@ControllerAdvice
public class ControllerValidationHandler {

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorMessageDTO processValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		FieldError error = result.getFieldError();

		return processFieldError(error);
	}
	
	private ErrorMessageDTO processFieldError(FieldError error) {
		return new ErrorMessageDTO(error.getDefaultMessage());
	}
}
