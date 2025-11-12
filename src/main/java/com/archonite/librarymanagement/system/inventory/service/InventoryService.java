package com.archonite.librarymanagement.system.inventory.service;

import com.archonite.librarymanagement.system.account.exception.UserUnAvailableException;
import com.archonite.librarymanagement.system.catalog.dto.BookRequestDto;
import com.archonite.librarymanagement.system.inventory.dto.OrderResponse;
import com.archonite.librarymanagement.system.inventory.exception.BookUnAvailabilityException;
import com.archonite.librarymanagement.system.inventory.exception.OrderNotFoundException;

import java.util.List;
import java.util.UUID;

public interface InventoryService {

    OrderResponse lend(String accountId, List<BookRequestDto> bookRequestDto) throws BookUnAvailabilityException, UserUnAvailableException;

    String returnBook(UUID accountId, UUID orderId) throws UserUnAvailableException, OrderNotFoundException;
}
