package com.bicyle_theft.demo.police;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoliceService {
    private final PoliceRepository policeRepository;

    @Autowired

    public PoliceService(PoliceRepository policeRepository) {
        this.policeRepository = policeRepository;
    }


    public Police createPolice(Police police) {
        return policeRepository.save(police);
    }


    public List<Police> getPolices() {
        return policeRepository.findAllNotDeleted();
    }
}
