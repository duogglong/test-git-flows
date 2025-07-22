package com.ndl.trustviec.repository;

import com.ndl.trustviec.entity.OtpTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OtpTransactionRepository extends JpaRepository<OtpTransactionEntity, UUID> {
//    OtpTransactionEntity findByIdAndCif(UUID uuid, String cif);

}
