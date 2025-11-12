package com.archonite.librarymanagement.system.account.service;

import com.archonite.librarymanagement.system.account.dto.LoginRequest;
import com.archonite.librarymanagement.system.account.dto.SignUpRequestDto;
import com.archonite.librarymanagement.system.account.exception.DuplicateResourceException;
import com.archonite.librarymanagement.system.account.exception.InvalidCredentialsException;
import com.archonite.librarymanagement.system.account.model.AccountModel;
import com.archonite.librarymanagement.system.account.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthService authService;


    public LoginServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder, AuthService authService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    @Override
    public String registerAccount(SignUpRequestDto dto) throws DuplicateResourceException {

        if (accountRepository.existsByUserName(dto.userName())) {
            throw new DuplicateResourceException("Username '" + dto.userName() + "' already exists");
        }
            AccountModel accountModel = AccountModel.builder()
                    .fullName(dto.fullName())
                    .userName(dto.userName())
                    .password(passwordEncoder.encode(dto.password()))
                    .role(dto.role())
                    .build();

            accountRepository.save(accountModel);

        return "User Saved Successfully";
    }

    @Override
    public String login(LoginRequest dto) throws InvalidCredentialsException {
        Optional<String> tokenOptional = authService.authenticate(dto);
        if (!tokenOptional.isPresent()) {
            return "";
        }
        return tokenOptional.get();
    }
}
