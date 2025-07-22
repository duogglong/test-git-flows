package com.ndl.trustviec.repository;

import com.ndl.trustviec.entity.ProfessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProfessionRepository extends JpaRepository<ProfessionEntity, UUID> {

}
