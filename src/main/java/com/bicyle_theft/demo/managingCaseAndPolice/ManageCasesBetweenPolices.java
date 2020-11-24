package com.bicyle_theft.demo.managingCaseAndPolice;

import com.bicyle_theft.demo.bicycleCase.CaseService;
import com.bicyle_theft.demo.police.PoliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

@Component
public class ManageCasesBetweenPolices {
    private final Queue<UUID> policeQueue;
    private final Queue<UUID> caseQueue;
    private final CaseService caseService;
    private final PoliceService policeService;
    private Thread thread;

    @Autowired
    public ManageCasesBetweenPolices(CaseService caseService, PoliceService policeService) {
        this.caseService = caseService;
        this.policeService = policeService;
        policeQueue = new ConcurrentLinkedDeque<>();
        caseQueue = new ConcurrentLinkedDeque<>();
        start();

    }


    private void start() {
        caseQueue.addAll(caseService.findCasesByStatus("free").stream().map(aCase -> aCase.getId()).collect(Collectors.toList()));
        policeQueue.addAll(policeService.findPoliceByStatus("free").stream().map(police -> police.getId()).collect(Collectors.toList()));
        thread = new Thread(new Asigner());
        thread.start();
    }


    public class Asigner implements Runnable {

        @Override
        public void run() {
            waitforCase();
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
                    caseService.makeCaseInProgress(c, p);
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
