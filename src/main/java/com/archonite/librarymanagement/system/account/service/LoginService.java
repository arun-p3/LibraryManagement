package com.archonite.librarymanagement.system.account.service;

import com.archonite.librarymanagement.system.account.dto.LoginRequest;
import com.archonite.librarymanagement.system.account.dto.SignUpRequestDto;
import com.archonite.librarymanagement.system.account.exception.DuplicateResourceException;
import com.archonite.librarymanagement.system.account.exception.InvalidCredentialsException;

public interface LoginService {

    String registerAccount(SignUpRequestDto dto) throws DuplicateResourceException;

    String login(LoginRequest dto) throws InvalidCredentialsException;
}
