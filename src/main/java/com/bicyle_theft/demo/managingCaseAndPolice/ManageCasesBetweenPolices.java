package com.bicyle_theft.demo.managingCaseAndPolice;

import com.bicyle_theft.demo.bicycleCase.Case;
import com.bicyle_theft.demo.bicycleCase.CaseRepository;
import com.bicyle_theft.demo.police.Police;
import com.bicyle_theft.demo.police.PoliceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

@Component
public class ManageCasesBetweenPolices {
    private final Queue<UUID> policeQueue;
    private final Queue<UUID> caseQueue;
    private final PoliceRepository policeRepository;
    private final CaseRepository caseRepository;
    private Thread thread;

    @Autowired
    public ManageCasesBetweenPolices(PoliceRepository policeRepository, CaseRepository caseRepository) {

        this.policeRepository = policeRepository;
        this.caseRepository = caseRepository;
        policeQueue = new ConcurrentLinkedDeque<>();
        caseQueue = new ConcurrentLinkedDeque<>();
        start();

    }

    public void addPolice(UUID id) {
        policeQueue.add(id);
    }

    public void addCase(UUID id) {
        caseQueue.add(id);
    }


    private void start() {
        caseQueue.addAll(caseRepository.findCasesByStatus("free").stream().map(aCase -> aCase.getId()).collect(Collectors.toList()));
        policeQueue.addAll(policeRepository.findPoliceByStatus("free").stream().map(police -> police.getId()).collect(Collectors.toList()));
        thread = new Thread(new Asigner(policeRepository, caseRepository));
        thread.start();
    }


    public class Asigner implements Runnable {
        private final PoliceRepository policeRepository;
        private final CaseRepository caseRepository;

        public Asigner(PoliceRepository policeRepository, CaseRepository caseRepository) {
            this.policeRepository = policeRepository;
            this.caseRepository = caseRepository;
        }

        @Override
        public void run() {
            waitforCase();
        }

        @Transactional
        public void makeCaseInProgress(UUID caseId, UUID policeId) {
            Police police = policeRepository.findById(policeId).get();
            Case acase = caseRepository.findById(caseId).get();
            police.setStatus("engage");
            policeRepository.save(police);
            acase.setPolice(police);
            acase.setStatus("inProgress");
            caseRepository.save(acase);

        }

        private void waitforCase() {
            while (true) {
                UUID c = caseQueue.poll();
                if (c == null) {
                    waitForData(100);
                } else {
                    waitForPolice(c);
                }
            }
        }

        private void waitForPolice(UUID c) {
            while (true) {
                UUID p = policeQueue.poll();
                if (p == null) {
                    waitForData(100);
                } else {
                    makeCaseInProgress(c, p);
                    break;
                }
            }
        }

        private void waitForData(long sleepTime) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
