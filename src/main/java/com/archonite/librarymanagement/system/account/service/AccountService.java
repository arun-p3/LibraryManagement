package com.archonite.librarymanagement.system.account.service;

import com.archonite.librarymanagement.system.account.dto.AccountManagerResponse;
import com.archonite.librarymanagement.system.account.dto.AccountRequestDto;
import com.archonite.librarymanagement.system.account.exception.UserUnAvailableException;
import org.springframework.data.domain.Page;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

public sealed interface AccountService permits AccountServiceImpl {

    Page<AccountManagerResponse> findAllUsers(int page, int pageSize);

    AccountManagerResponse findUserById(UUID id) throws UserUnAvailableException;

    AccountManagerResponse updateUser(AccountRequestDto accountRequestDto) throws UserUnAvailableException;

    String deleteUser(UUID id) throws UserUnAvailableException;
}
