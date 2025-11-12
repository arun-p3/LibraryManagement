package com.archonite.librarymanagement.system.account.service;

import com.archonite.librarymanagement.system.account.dto.AccountManagerResponse;
import com.archonite.librarymanagement.system.account.dto.AccountRequestDto;
import com.archonite.librarymanagement.system.account.exception.UserUnAvailableException;
import com.archonite.librarymanagement.system.account.model.AccountModel;
import com.archonite.librarymanagement.system.account.repository.AccountRepository;
import com.archonite.librarymanagement.system.commonutils.MapperUtils;
import com.archonite.librarymanagement.system.inventory.model.OrderModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public final class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Page<AccountManagerResponse> findAllUsers(int page, int pageSize) {
        Page<AccountModel> accounts = accountRepository.findAll(PageRequest.of(page, pageSize));

        return accounts.map(a -> MapperUtils.map(a, AccountManagerResponse.class));
    }

    @Override
    public AccountManagerResponse findUserById(String id) throws UserUnAvailableException {
        Optional<AccountModel> accountModel = accountRepository.findById(UUID.fromString(id));
        accountModel.get().getOrders()
                .forEach(ac -> ac.setDue(ac.getReturnDate().equals(LocalDate.now())));
        if (accountModel.isEmpty()) {
            throw new UserUnAvailableException("Requested User not available!");
        }
        return MapperUtils.map(accountModel.get(), AccountManagerResponse.class);
    }

    @Override
    public AccountManagerResponse updateUser(AccountRequestDto accountRequestDto) throws UserUnAvailableException {
        Optional<AccountModel> account = accountRepository.findById(accountRequestDto.id());
        if (account.isEmpty()) {
            throw new UserUnAvailableException("Requested User not available!");
        }
        List<OrderModel> orders = account.get().getOrders();

        AccountModel accountModel = account.get();
        accountModel.setId(accountRequestDto.id());
        accountModel.setFullName(accountRequestDto.fullName());
        accountModel.setRole(accountRequestDto.role());
        accountModel.setOrders(orders);
        accountRepository.save(accountModel);
        return MapperUtils.map(accountModel, AccountManagerResponse.class);
    }

    @Override
    public String deleteUser(UUID id) throws UserUnAvailableException {
        Optional<AccountModel> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new UserUnAvailableException("Requested User not available!");
        }
        try {
            accountRepository.deleteById(account.get().getId());
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        log.info("Account Has been deleted");

        return "Account Has been deleted UserName:" + account.get().getUserName();

    }

    public String findByUserName(String userName) throws UserUnAvailableException {
        Optional<AccountModel> account = accountRepository.findByUserName(userName);
        if (account.isEmpty()) {
            throw new UserUnAvailableException("Requested User not available!");
        }
        return account.get().getId().toString();
    }
}
