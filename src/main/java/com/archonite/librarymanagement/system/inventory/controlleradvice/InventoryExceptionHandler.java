package com.archonite.librarymanagement.system.inventory.controlleradvice;

import com.archonite.librarymanagement.system.inventory.exception.BookUnAvailabilityException;
import com.archonite.librarymanagement.system.inventory.exception.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class InventoryExceptionHandler {

    @ExceptionHandler(BookUnAvailabilityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBookUnAvailabilityException(BookUnAvailabilityException ex) {
        Map<String, String> map = new HashMap<>();
        map.put("message", ex.getMessage());
        map.put("status", "400");
        return map;
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleOrdenNotFoundException(OrderNotFoundException ex) {
        Map<String, String> map = new HashMap<>();
        map.put("message", ex.getMessage());
        map.put("status", "404");
        return map;
    }
}
