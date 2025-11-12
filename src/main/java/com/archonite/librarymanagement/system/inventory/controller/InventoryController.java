package com.archonite.librarymanagement.system.inventory.controller;

import com.archonite.librarymanagement.system.account.exception.UserUnAvailableException;
import com.archonite.librarymanagement.system.account.service.AccountService;
import com.archonite.librarymanagement.system.account.service.AccountServiceImpl;
import com.archonite.librarymanagement.system.account.service.LoginServiceImpl;
import com.archonite.librarymanagement.system.catalog.dto.BookRequestDto;
import com.archonite.librarymanagement.system.inventory.dto.OrderResponse;
import com.archonite.librarymanagement.system.inventory.exception.BookUnAvailabilityException;
import com.archonite.librarymanagement.system.inventory.exception.OrderNotFoundException;
import com.archonite.librarymanagement.system.inventory.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/inventory")
public class InventoryController {

    private InventoryService inventoryService;

    private final AccountServiceImpl accountService;

    public InventoryController(InventoryService inventoryService, AccountServiceImpl accountService) {
        this.inventoryService = inventoryService;
        this.accountService = accountService;
    }

    @PostMapping("/lend")
    public ResponseEntity<OrderResponse> lend(@RequestHeader("Authorization") String token,
                                              @RequestBody List<BookRequestDto> bookRequestDto) throws BookUnAvailabilityException, UserUnAvailableException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("userName: " + userName);
        Optional<String> userId = Optional.ofNullable(accountService.findByUserName(userName));
        return ResponseEntity.ok(inventoryService.lend(userId.get(), bookRequestDto));
    }

    @DeleteMapping("/return")
    public ResponseEntity<String> returnBook(@RequestHeader("Authorization") String token,
                                         @RequestParam UUID accountId, @RequestParam UUID orderId) throws BookUnAvailabilityException, UserUnAvailableException, OrderNotFoundException {
        return ResponseEntity.ok(inventoryService.returnBook(accountId, orderId));

    }

}
