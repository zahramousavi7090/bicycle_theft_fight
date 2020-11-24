package com.bicyle_theft.demo.bicycleCase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CaseRepository extends JpaRepository<Case, UUID> {

    @Query("select c from Case c where c.isDeleted=false")
    List<Case> findAllNotDeleted();

    @Modifying
    @Query("update Case c set c.isDeleted = true where c.id =:id")
    void setDeleteById(@Param(value = "id") UUID id);

    @Modifying
    @Query("update Case c set c.status =:status where c.id =:id")
    void changeStatus(@Param(value = "id") UUID id, @Param(value = "status") String status);

    @Query("select c from Case c where c.status =:status")
    List<Case> findCasesByStatus(@Param(value = "status")String status);
}
