package com.bicyle_theft.demo.bicycleCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CaseService {

    private final CaseRepository caseRepository;

    @Autowired
    public CaseService(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    public Case CreateCase(Case aCase) {
        return caseRepository.save(aCase);
    }

    public List<Case> getCases() {
        return caseRepository.findAllNotDeleted();
    }

    public Optional<Case> getCaseByID(UUID id) {
        return caseRepository.findById(id).filter(aCase -> !(aCase.isDeleted()));
    }

}
