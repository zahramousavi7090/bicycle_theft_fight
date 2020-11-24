package com.bicyle_theft.demo.bicycleCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
