package com.bicyle_theft.demo.bicycleCase;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("cases")
public class CaseController {
    private final CaseService caseService;

    @Autowired
    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    @Operation(summary = "create a case")
    @PostMapping
    public Case createCase(@RequestBody Case aCase) {
        return caseService.CreateCase(aCase);
    }

    @Operation(summary = "Get all cases")
    @GetMapping
    public List<Case> getCases() {
        return caseService.getCases();
    }

}
