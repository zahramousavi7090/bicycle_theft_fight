package com.bicyle_theft.demo.bicycleCase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CaseRepository extends JpaRepository<Case, UUID> {
}
