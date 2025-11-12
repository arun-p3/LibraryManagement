package com.archonite.librarymanagement.system.account.controlleradvice;

import com.archonite.librarymanagement.system.account.exception.DuplicateResourceException;
import com.archonite.librarymanagement.system.account.exception.InvalidCredentialsException;
import com.archonite.librarymanagement.system.account.exception.UserUnAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AccountExceptionHandler {


    @ExceptionHandler(UserUnAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleUserUnAvailableException(UserUnAvailableException e) {
        Map<String, String> map = new HashMap<>();
        map.put("error message", e.getMessage());
        map.put("status", "400");
        return map;
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidCredentialsException(InvalidCredentialsException e) {
        Map<String, String> map = new HashMap<>();
        map.put("error message", e.getMessage());
        map.put("status", "400");
        return map;
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleDuplicateResource(DuplicateResourceException e) {
        Map<String, String> map = new HashMap<>();
        map.put("error message", e.getMessage());
        map.put("status", "400");

        return map;
    }
}
