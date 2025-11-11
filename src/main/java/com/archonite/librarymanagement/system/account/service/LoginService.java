package com.archonite.librarymanagement.system.account.service;

import com.archonite.librarymanagement.system.account.dto.LoginRequest;
import com.archonite.librarymanagement.system.account.dto.SignUpRequestDto;

public interface LoginService {

    String registerAccount(SignUpRequestDto dto) throws Exception;

    String login(LoginRequest dto) throws Exception;
}
