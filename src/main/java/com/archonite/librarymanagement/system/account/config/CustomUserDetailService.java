package com.archonite.librarymanagement.system.account.config;

import com.archonite.librarymanagement.system.account.model.AccountModel;
import com.archonite.librarymanagement.system.account.repository.AccountRepository;
import com.archonite.librarymanagement.system.account.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public CustomUserDetailService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountModel user = accountRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Convert your entity to Spring Security UserDetails
        return User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(String.valueOf(user.getRole()))
                .build();
    }
}
