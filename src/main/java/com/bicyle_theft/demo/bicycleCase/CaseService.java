package com.bicyle_theft.demo.bicycleCase;

import com.bicyle_theft.demo.CloseCaseDTO;
import com.bicyle_theft.demo.exception.BadRequestException;
import com.bicyle_theft.demo.exception.NotFoundException;
import com.bicyle_theft.demo.police.Police;
import com.bicyle_theft.demo.police.PoliceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CaseService {

    private final CaseRepository caseRepository;
    private final PoliceRepository policeRepository;

    @Autowired
    public CaseService(CaseRepository caseRepository, PoliceRepository policeRepository) {
        this.caseRepository = caseRepository;
        this.policeRepository = policeRepository;
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

    public Case updateCase(UUID id, Case acase) {
        Optional<Case> currentCaseOpt = getCaseByID(id);

        if (!currentCaseOpt.isPresent()) {
            throw new NotFoundException("this case Not Found!");
        }

        Case currentCase = currentCaseOpt.get();
        if (currentCase.isDeleted()) {
            throw new NotFoundException("this case Not found!");
        }
        currentCase.setName(acase.getName());
        return caseRepository.save(currentCase);
    }

    @Transactional
    public void deleteById(UUID id) {
        if (!caseRepository.existsById(id))
            throw new NotFoundException("this case Not Found!");
        caseRepository.setDeleteById(id);
    }


    @Transactional
    public void closeCase(CloseCaseDTO closeCaseDTO) {
        if (closeCaseDTO.getCaseId() == null || closeCaseDTO.getPoliceId() == null)
            throw new BadRequestException();


        if (!caseRepository.existsById(closeCaseDTO.getCaseId()) || !policeRepository.existsById(closeCaseDTO.getPoliceId()))
            throw new BadRequestException();

        caseRepository.changeStatus(closeCaseDTO.getCaseId(), "close");
        policeRepository.changeStatus(closeCaseDTO.getPoliceId(), "free");
    }

    public List<Case> findCasesByStatus(String status) {
        return caseRepository.findCasesByStatus(status);
    }

    @Transactional
    public void makeCaseInProgress(UUID caseId, UUID policeId) {
        Police police = policeRepository.getOne(policeId);
        Case acase = caseRepository.getOne(caseId);
        police.setStatus("engage");
        acase.setPolice(police);
        acase.setStatus("inProgress");

        policeRepository.save(police);
        caseRepository.save(acase);
    }
}
