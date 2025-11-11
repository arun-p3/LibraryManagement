package com.archonite.librarymanagement.system.account.service;

import com.archonite.librarymanagement.system.account.dto.LoginRequest;
import com.archonite.librarymanagement.system.account.dto.SignUpRequestDto;
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
    public String registerAccount(SignUpRequestDto dto) throws Exception {
//        String password = AESDycrypt.decrypt(dto.password());
//        String encodedPassword = passwordEncoder.encode(dto.password());
        AccountModel accountModel = AccountModel.builder()
                .fullName(dto.fullName())
                .userName(dto.userName())
                .password(dto.password())
                .role(dto.role())
                .build();

        accountRepository.save(accountModel);
        return "User Saved Successfully";
    }

    @Override
    public String login(LoginRequest dto) throws Exception {
        Optional<String> tokenOptional = authService.authenticate(dto);
        if (!tokenOptional.isPresent()) {
            return "";
        }
        return tokenOptional.get();
    }
}
