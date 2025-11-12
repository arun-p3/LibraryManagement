package com.archonite.librarymanagement.system.account.controller;

import com.archonite.librarymanagement.system.account.dto.AccountManagerResponse;
import com.archonite.librarymanagement.system.account.dto.AccountRequestDto;
import com.archonite.librarymanagement.system.account.exception.UserUnAvailableException;
import com.archonite.librarymanagement.system.account.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/account-management")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/admin/find")
    public ResponseEntity<Page<AccountManagerResponse>> findAll(@RequestParam("page") int page,
                                                                @RequestParam("pageSize") int pageSize,
                                                                @RequestHeader("Authorization") String token) {
        Page<AccountManagerResponse> allUsers = accountService.findAllUsers(page, pageSize);
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/findById")
    public ResponseEntity<AccountManagerResponse> findByUserId(@RequestHeader("Authorization") String token) throws UserUnAvailableException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<String> id = Optional.ofNullable(accountService.findByUserName(userName));
        return ResponseEntity.ok(accountService.findUserById(id.get()));
    }

    @PutMapping("/admin/update")
    public ResponseEntity<AccountManagerResponse> updateUser(@RequestBody AccountRequestDto accountRequestDto,
                                                             @RequestHeader("Authorization") String token) throws UserUnAvailableException {
        return ResponseEntity.ok(accountService.updateUser(accountRequestDto));
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable UUID id, @RequestHeader("Authorization") String token) throws UserUnAvailableException {
        return ResponseEntity.ok(accountService.deleteUser(id));
    }
}
