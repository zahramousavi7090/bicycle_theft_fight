package com.bicyle_theft.demo.police;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PoliceRepository extends JpaRepository<Police, UUID> {

    @Query("select p from Police p where p.isDeleted = false")
    List<Police> findAllNotDeleted();

}
