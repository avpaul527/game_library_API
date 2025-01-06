package com.gameapi.game_library.handler;

import com.gameapi.game_library.error.ErrorDetail;
import com.gameapi.game_library.error.ValidationError;
import com.gameapi.game_library.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException nre, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Message Not Readable");
        errorDetail.setDetail(nre.getMessage());
        errorDetail.setDeveloperMessage(nre.getClass().getName());

        ValidationError validationError = new ValidationError();

        validationError.setCode("Bad JSON Format");
        validationError.setMessage(nre.getLocalizedMessage());

        List<ValidationError> errors = new ArrayList<>();

        errors.add(validationError);

        errorDetail.getErrors().put("Message Not Readable", errors);


        return handleExceptionInternal(nre, errorDetail, headers, status, request);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manve, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setTitle("Validation Failed");
        errorDetail.setDetail(manve.getMessage());
        errorDetail.setDeveloperMessage(manve.getClass().getName());

        Map<String, List<ValidationError>> validationErrors = new HashMap<>();

        for (FieldError fieldError : manve.getBindingResult().getFieldErrors()) {
            String fieldName = fieldError.getCode();
            String errorMessage = messageSource.getMessage(fieldError, Locale.getDefault());

            ValidationError validationError = new ValidationError(fieldName, errorMessage);

            validationErrors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(validationError);
        }

        errorDetail.setErrors(validationErrors);

        return handleExceptionInternal(manve, errorDetail, headers, status, request);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe, HttpServletRequest request){

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Resource Not Found");
        errorDetail.setDetail(rnfe.getMessage());
        errorDetail.setDeveloperMessage(rnfe.getClass().getName());

        ValidationError validationError = new ValidationError();
        validationError.setCode("NOT FOUND");
        validationError.setMessage(rnfe.toString());
        List<ValidationError> errors = new ArrayList<>();

        errors.add(validationError);

        errorDetail.getErrors().put("Resource Not Found", errors);

        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }
}
