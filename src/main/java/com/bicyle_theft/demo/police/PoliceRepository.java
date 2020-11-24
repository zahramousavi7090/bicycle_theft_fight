package com.bicyle_theft.demo.police;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PoliceRepository extends JpaRepository<Police, UUID> {

    @Query("select p from Police p where p.isDeleted = false")
    List<Police> findAllNotDeleted();

    @Modifying
    @Query("update Police p set p.isDeleted=true where p.id =:id")
    void setDeleteById(@Param(value = "id") UUID id);


    @Modifying
    @Query("update Police p set p.status =:status where p.id =:id")
    void changeStatus(@Param(value = "id") UUID id, @Param(value = "status") String status);

    @Query("select p from Police p where p.status =:status")
    List<Police> findPoliceByStatus(@Param(value = "status") String status);

}
