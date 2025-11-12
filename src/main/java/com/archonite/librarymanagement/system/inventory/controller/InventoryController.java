package com.archonite.librarymanagement.system.inventory.controller;

import com.archonite.librarymanagement.system.account.exception.UserUnAvailableException;
import com.archonite.librarymanagement.system.catalog.dto.BookRequestDto;
import com.archonite.librarymanagement.system.inventory.dto.OrderResponse;
import com.archonite.librarymanagement.system.inventory.exception.BookUnAvailabilityException;
import com.archonite.librarymanagement.system.inventory.exception.OrderNotFoundException;
import com.archonite.librarymanagement.system.inventory.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/inventory")
public class InventoryController {

    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/lend")
    public ResponseEntity<OrderResponse> lend(@RequestParam("accountId") String accountId,
                                              @RequestBody List<BookRequestDto> bookRequestDto) throws BookUnAvailabilityException, UserUnAvailableException {
        return ResponseEntity.ok(inventoryService.lend(accountId, bookRequestDto));
    }

    @DeleteMapping("/return")
    public ResponseEntity<String> returnBook(@RequestHeader("Authorization") String token,
                                         @RequestParam UUID accountId, @RequestParam UUID orderId) throws BookUnAvailabilityException, UserUnAvailableException, OrderNotFoundException {
        return ResponseEntity.ok(inventoryService.returnBook(accountId, orderId));

    }

}
