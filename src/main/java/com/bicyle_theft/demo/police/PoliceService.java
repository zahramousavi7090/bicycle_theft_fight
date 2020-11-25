package com.bicyle_theft.demo.police;

import com.bicyle_theft.demo.exception.NotFoundException;
import com.bicyle_theft.demo.notify.Notifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PoliceService {
    private final PoliceRepository policeRepository;
    private final Notifier notifier;

    @Autowired

    public PoliceService(PoliceRepository policeRepository, Notifier notifier) {
        this.policeRepository = policeRepository;
        this.notifier = notifier;
    }


    public Police createPolice(Police police) {
        Police p = policeRepository.save(police);
        notifier.notifyPolice(p.getId());
        return p;
    }


    public List<Police> getPolices() {
        return policeRepository.findAllNotDeleted();
    }

    public Optional<Police> getPoliceByID(UUID id) {
        return policeRepository.findById(id);
    }


    public Police updatePolice(UUID id, Police police) {
        Optional<Police> currentPoliceOpt = getPoliceByID(id);

        if (!currentPoliceOpt.isPresent()) {
            throw new NotFoundException("this police Not Found!");
        }

        Police currentPolice = currentPoliceOpt.get();
        if (currentPolice.isDeleted()) {
            throw new NotFoundException("this police Not Found!");
        }
        currentPolice.setName(police.getName());
        currentPolice.setStatus(police.getStatus());
        return policeRepository.save(currentPolice);
    }

    @Transactional
    public void deleteByID(UUID id) {
        if (!policeRepository.existsById(id))
            throw new NotFoundException("this police Not Found");
        policeRepository.setDeleteById(id);
    }

    public List<Police> findPoliceByStatus(String status) {
        return policeRepository.findPoliceByStatus(status);
    }

}
