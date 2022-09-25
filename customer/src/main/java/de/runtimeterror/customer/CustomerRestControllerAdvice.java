package de.runtimeterror.customer;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomerRestControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleRuntimeException(RuntimeException e) {
        return new ErrorMessage()
                .setStatusCode(500)
                .setMessage(e.getMessage())
                .setLocalDateTime(LocalDateTime.now())
                .setDescription("Runtime Terror");
    }

    @ExceptionHandler(value = {de.runtimeterror.customer.CustomerNotFoundException.class})
    @ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
    public ErrorMessage handleCustomerNotFoundException(de.runtimeterror.customer.CustomerNotFoundException e) {
        return new ErrorMessage()
                .setStatusCode(404)
                .setMessage(e.getMessage())
                .setLocalDateTime(LocalDateTime.now())
                .setDescription("Customer not found");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ErrorMessage()
                .setStatusCode(400)
                .setMessage(e.getMessage())
                .setLocalDateTime(LocalDateTime.now())
                .setDescription("Bad Request")
                .setFieldErrors(e.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList()));
    }
}


class ErrorMessage {
    private int statusCode;
    private LocalDateTime localDateTime;
    private String message;
    private String description;
    private List<String> fieldErrors;

    public ErrorMessage() {

    }

    public ErrorMessage(int statusCode, LocalDateTime localDateTime, String message, String description) {
        this.statusCode = statusCode;
        this.localDateTime = localDateTime;
        this.message = message;
        this.description = description;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public ErrorMessage setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public ErrorMessage setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        return this;
    }

    public ErrorMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    public ErrorMessage setDescription(String description) {
        this.description = description;
        return this;
    }

    public ErrorMessage setFieldErrors(List<String> fieldErrors) {
        this.fieldErrors = fieldErrors;
        return this;
    }

    public List<String> getFieldErrors() {
        return fieldErrors;
    }
}