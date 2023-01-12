package com.springframework.universitycourses.controllers;

import com.springframework.universitycourses.api.v1.model.ErrorDTO;
import com.springframework.universitycourses.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler
{
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ErrorDTO handleNotFound(Exception e)
	{
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setErrorCode(HttpStatus.NOT_FOUND.value());
		errorDTO.setError("No value found for the id provided");
		errorDTO.setErrorMessage(e.getMessage());
		return errorDTO;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ErrorDTO handleNumberFormatException(Exception e)
	{
		ErrorDTO errorDTO = new ErrorDTO();
		MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) e;

		errorDTO.setErrorCode(HttpStatus.BAD_REQUEST.value());
		errorDTO.setError("Not valid value: ('" + exception.getValue() + "') for type: " + exception.getRequiredType());
		errorDTO.setErrorMessage(exception.getMessage());
		return errorDTO;
	}
}
