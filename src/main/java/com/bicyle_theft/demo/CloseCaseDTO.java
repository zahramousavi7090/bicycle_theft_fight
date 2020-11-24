package com.bicyle_theft.demo;

import com.bicyle_theft.demo.bicycleCase.Case;
import com.bicyle_theft.demo.police.Police;

import java.util.UUID;

public class CloseCaseDTO {
    private UUID policeId;
    private UUID caseId;

    public UUID getPoliceId() {
        return policeId;
    }

    public void setPoliceId(UUID policeId) {
        this.policeId = policeId;
    }

    public UUID getCaseId() {
        return caseId;
    }

    public void setCaseId(UUID caseId) {
        this.caseId = caseId;
    }
}
