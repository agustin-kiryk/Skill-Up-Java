package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.ApiErrorDTO;
import com.alkemy.wallet.exception.ParamNotFound;
import com.alkemy.wallet.exception.RepeatedUsername;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {ParamNotFound.class})
  protected ResponseEntity<Object> handleParamNotFound(RuntimeException ex, WebRequest request){
    ApiErrorDTO errorDTO = new ApiErrorDTO(
        HttpStatus.BAD_REQUEST,
        ex.getMessage(),
        Arrays.asList("Param Not Found")
    );
    return handleExceptionInternal(ex, errorDTO, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(value = {RepeatedUsername.class})
  protected ResponseEntity<Object> handleRepeatedUsername(RuntimeException ex, WebRequest request){
    ApiErrorDTO errorDTO = new ApiErrorDTO(
        HttpStatus.IM_USED,
        ex.getMessage(),
        Arrays.asList("Username repetido")
    );
    return handleExceptionInternal(ex, errorDTO, new HttpHeaders(), HttpStatus.IM_USED, request);
  }

  @ExceptionHandler(value = {BadCredentialsException.class})
  protected ResponseEntity<Object> handleErrorUser(RuntimeException ex, WebRequest request){
    ApiErrorDTO errorDTO = new ApiErrorDTO(
        HttpStatus.NOT_FOUND,
        ex.getMessage(),
        Arrays.asList("Username or pass incorrect")
    );
    return handleExceptionInternal(ex, errorDTO, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }


  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    List<String> errors = new ArrayList<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()){
      errors.add(error.getField() + ": " + error.getDefaultMessage());
    }
    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add((error.getObjectName()) + ": " + error.getDefaultMessage());
    }
    ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
    return handleExceptionInternal(
        ex, apiError, headers, apiError.getStatus(), request);

  }
  @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
  public ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "this should be application specific";
    return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
  }



}
