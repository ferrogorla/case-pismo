package com.pismo.repository;

import com.pismo.model.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByDocumentNumber(Long documentNumber);

    @Query("SELECT a FROM Account a WHERE a.user.id = :userId")
    List<Account> getAccounts(Long userId, Pageable pageable);

}
