package com.bicyle_theft.demo.bicycleCase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CaseRepository extends JpaRepository<Case, UUID> {

    @Query("select c from Case c where c.isDeleted=false")
    List<Case> findAllNotDeleted();
}
