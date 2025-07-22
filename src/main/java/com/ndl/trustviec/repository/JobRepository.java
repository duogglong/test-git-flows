package com.ndl.trustviec.repository;

import com.ndl.trustviec.entity.JobEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {
    @Query("SELECT e FROM Job e WHERE (:keyword IS NULL OR e.jobTitle LIKE %:keyword%)")
    Page<JobEntity> filter(@Param("keyword") String keyword, Pageable pageable);
}
