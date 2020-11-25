package com.bicyle_theft.demo.bicycleCase;

import com.bicyle_theft.demo.CloseCaseDTO;
import com.bicyle_theft.demo.exception.BadRequestException;
import com.bicyle_theft.demo.exception.NotFoundException;
import com.bicyle_theft.demo.notify.Notifier;
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
    private final Notifier notifier;

    @Autowired
    public CaseService(CaseRepository caseRepository, PoliceRepository policeRepository, Notifier notifier) {
        this.caseRepository = caseRepository;
        this.policeRepository = policeRepository;
        this.notifier = notifier;
    }

    public Case CreateCase(Case aCase) {

        Case c = caseRepository.save(aCase);
        notifier.notifyCase(c.getId());
        return c;
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
        notifier.notifyPolice(closeCaseDTO.getPoliceId());
    }

    public List<Case> findCasesByStatus(String status) {
        return caseRepository.findCasesByStatus(status);
    }


}
