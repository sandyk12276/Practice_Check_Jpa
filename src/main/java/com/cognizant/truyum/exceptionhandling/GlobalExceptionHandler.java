package com.cognizant.truyum.exceptionhandling;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		LOGGER.info("In handleMethodArgumentNotValid method in GlobalExceptionHandler class");
        Map<String, Object> body = new LinkedHashMap<String, Object>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        
        body.put("errors", errors);
        LOGGER.info("Exiting handleMethodArgumentNotValid method in GlobalExceptionHandler class");
        return new ResponseEntity<>(body, headers, status);
	}
	
	@ExceptionHandler(ListEmptyException.class)
	public void handleListEmptyException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), "List is Empty");
	}
	
	@ExceptionHandler(UpdateFailedException.class)
	public void handleUpdateFailedException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(), "Could not find the element. hence update failed");
	}
	
	@ExceptionHandler(ItemNotFoundException.class)
	public void handleItemNotFoundException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), "Item Not Found");
	}
	
	@ExceptionHandler(UserNotFound.class)
	public void handleUserNotFoundException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), "User not Found");
	}
	
	@ExceptionHandler(UserExists.class)
	public void handleUserExists(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(), "User Already Exists");
	}
}
