package com.archonite.librarymanagement.system.inventory.service;

import com.archonite.librarymanagement.system.account.exception.UserUnAvailableException;
import com.archonite.librarymanagement.system.account.model.AccountModel;
import com.archonite.librarymanagement.system.account.repository.AccountRepository;
import com.archonite.librarymanagement.system.catalog.dto.BookRequestDto;
import com.archonite.librarymanagement.system.catalog.model.BookModel;
import com.archonite.librarymanagement.system.catalog.repository.CatalogRepository;
import com.archonite.librarymanagement.system.commonutils.MapperUtils;
import com.archonite.librarymanagement.system.inventory.dto.OrderResponse;
import com.archonite.librarymanagement.system.inventory.exception.BookUnAvailabilityException;
import com.archonite.librarymanagement.system.inventory.exception.OrderNotFoundException;
import com.archonite.librarymanagement.system.inventory.model.OrderModel;
import com.archonite.librarymanagement.system.inventory.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class InventoryServiceImpl implements InventoryService {

    private InventoryRepository inventoryRepository;

    private CatalogRepository catalogRepository;

    private AccountRepository accountRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, CatalogRepository catalogRepository, AccountRepository accountRepository) {
        this.inventoryRepository = inventoryRepository;
        this.catalogRepository = catalogRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public OrderResponse lend(String accountId, List<BookRequestDto> bookRequestDto) throws BookUnAvailabilityException, UserUnAvailableException {
        Boolean availability = isAvailable(bookRequestDto);
        if (availability) {
            Optional<AccountModel> user = accountRepository.findById(UUID.fromString(accountId));
            if (user.isPresent()) {
                List<BookModel> bookModelList = bookRequestDto.stream()
                        .map(request -> {
                            try {
                                return catalogRepository.findById(request.id())
                                        .orElseThrow(() -> new BookUnAvailabilityException("Book not found: " + request.id()));
                            } catch (BookUnAvailabilityException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toList();

                OrderModel orderModel = OrderModel.builder()
                        .orderDate(LocalDate.now().atStartOfDay())
                        .book(bookModelList)
                        .due(false)
                        .returnDate(LocalDate.now().plusDays(30))
                        .account(user.get())
                        .build();

                OrderModel order = inventoryRepository.save(orderModel);

                updateBookStockBeforeLend(bookRequestDto);

                return MapperUtils.map(order, OrderResponse.class);
            } else {
                throw new UserUnAvailableException("User not found with ID: "+ accountId);
            }
        } else {
            throw new BookUnAvailabilityException("Stock Unavailable!");
        }
    }


    private Boolean isAvailable(List<BookRequestDto> bookRequestDto) {
        for (BookRequestDto dto : bookRequestDto) {
            Optional<BookModel> book = catalogRepository.findById(dto.id());
            if (book.isPresent()) {
                boolean stock = book.get().getDetailedInformation().getStockCount() > dto.qty();
                if (!book.get().getDetailedInformation().isAvailable() || !stock)
                    return false;
            }
        }
        return true;

    }

    @Override
    public String returnBook(UUID accountId, UUID orderId) throws UserUnAvailableException, OrderNotFoundException {
        Optional<AccountModel> accountModel = accountRepository.findById(accountId);
        if (accountModel.isPresent()) {
            try {
                List<OrderModel> orderModelList = accountModel.get().getOrders();

                Optional<OrderModel> orderToDeleteOpt = orderModelList.stream()
                        .filter(order -> order.getId().equals(orderId))
                        .findFirst();
                if (orderToDeleteOpt.isPresent()) {
                    OrderModel orderToDelete = orderToDeleteOpt.get();

                    orderModelList.remove(orderToDelete);

                    inventoryRepository.delete(orderToDelete);

                    updateBookStock(orderModelList);
                } else {
                    throw new OrderNotFoundException("Order not found!");
                }
            } catch (RuntimeException | OrderNotFoundException e) {
                throw e;
            }

        } else {
            throw new UserUnAvailableException("User not available with acc ID: "+ accountId.toString());
        }

        return "Books Returned Successfully!";
    }

    /**
     * Update Book Stock after return
     * @param orderModelList
     */
    private void updateBookStock(List<OrderModel> orderModelList) {
        List<BookModel> bookList = new ArrayList<>();
        for (OrderModel orderModelModel : orderModelList) {
            bookList.addAll(orderModelModel.getBook());
        }

        for (BookModel bookModel : bookList) {
            BookModel bookModel1 = catalogRepository.findById(bookModel.getId()).orElse(null);
            if (Objects.nonNull(bookModel1)) {
                int stockCount = bookModel1.getDetailedInformation().getStockCount() + bookModel.getDetailedInformation().getStockCount();
                bookModel1.getDetailedInformation().setStockCount(stockCount);
                bookModel1.getDetailedInformation().setAvailable(stockCount > 0);
                catalogRepository.save(bookModel1);
            }
        }

        log.info("Updated Stock Successfully!");
    }

    /**
     * updateBookStockBeforeLend
     * @param bookRequestDto
     */
    private void updateBookStockBeforeLend(List<BookRequestDto> bookRequestDto) throws BookUnAvailabilityException {

        for (BookRequestDto request : bookRequestDto) {

            BookModel book = catalogRepository.findById(request.id())
                    .orElseThrow(() -> new BookUnAvailabilityException("Book not found: " + request.id()));

            int newStock = book.getDetailedInformation().getStockCount() - request.qty();
            if (newStock < 0) {
                throw new RuntimeException("Insufficient stock for book: " + book.getTitle());
            }

            book.getDetailedInformation().setStockCount(newStock);
            book.getDetailedInformation().setAvailable(newStock > 0);
            catalogRepository.save(book);
        }

    }
}
