package com.archonite.librarymanagement.system.account.repository;

import com.archonite.librarymanagement.system.account.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, UUID> {

    Optional<AccountModel> findByUserName(String userName);
}
