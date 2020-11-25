package com.bicyle_theft.demo.notify;

import com.bicyle_theft.demo.managingCaseAndPolice.ManageCasesBetweenPolices;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Notifier {
    private final ManageCasesBetweenPolices manageCasesBetweenPolices;

    public Notifier(ManageCasesBetweenPolices manageCasesBetweenPolices) {
        this.manageCasesBetweenPolices = manageCasesBetweenPolices;
    }

    public void notifyPolice(UUID id) {
        manageCasesBetweenPolices.addPolice(id);
    }

    public void notifyCase(UUID id) {
        manageCasesBetweenPolices.addCase(id);
    }
}
