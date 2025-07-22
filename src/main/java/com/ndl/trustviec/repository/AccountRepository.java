package com.ndl.trustviec.repository;

import com.ndl.trustviec.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    @Query("SELECT e FROM Account e WHERE e.username = :username")
    AccountEntity findByUsername(@Param("username") String username);

    AccountEntity findByEmail(String email);
}
