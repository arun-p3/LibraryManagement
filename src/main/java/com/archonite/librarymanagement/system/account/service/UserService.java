package com.archonite.librarymanagement.system.account.service;

import com.archonite.librarymanagement.system.account.exception.InvalidCredentialsException;
import com.archonite.librarymanagement.system.account.model.AccountModel;
import com.archonite.librarymanagement.system.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final AccountRepository userRepository;

    public UserService(AccountRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<AccountModel> findByUserName(String userName) throws InvalidCredentialsException {
        Optional<AccountModel> acc = userRepository.findByUserName(userName);
        if(acc.isEmpty()) {
            throw new InvalidCredentialsException("Invalid Credentials");
        }
        return userRepository.findByUserName(userName);
    }
}
