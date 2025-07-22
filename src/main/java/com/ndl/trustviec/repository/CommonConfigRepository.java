package com.ndl.trustviec.repository;

import com.ndl.trustviec.entity.CommonConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonConfigRepository extends JpaRepository<CommonConfigEntity, String> {
    List<CommonConfigEntity> findAll();
}
