package com.bicyle_theft.demo.bicycleCase;

import com.bicyle_theft.demo.CloseCaseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Operation(summary = "Get a case by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the case",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Case.class)) }),
            @ApiResponse(responseCode = "404", description = "this case Not Found!",
                    content = @Content) })

    @GetMapping(value = "/{caseId}")
    public Optional<Case> getCaseByID(@PathVariable(value = "caseId") UUID id) {
        return caseService.getCaseByID(id);
    }

    @Operation(summary = "edit a case by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "edit the case",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Case.class)) }),
            @ApiResponse(responseCode = "404", description = "this case Not Found!",
                    content = @Content) })
    @PutMapping(value = "/{caseId}")
    public Case updateCase(@PathVariable(value = "caseId") UUID id, @RequestBody Case aCase) {
        caseService.updateCase(id, aCase);
        return aCase;
    }

    @Operation(summary = "delete a case by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delete the case",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Case.class)) }),
            @ApiResponse(responseCode = "404", description = "this case Not Found!",
                    content = @Content) })
    @DeleteMapping(value = {"/{caseId}"})
    public void delete(@PathVariable(value = "caseId") UUID id) {
        caseService.deleteById(id);
    }


    @Operation(summary = "patch a case by its id for change its status")
    @PatchMapping(value="/close-case")
    public void closeCase(@RequestBody CloseCaseDTO closeCaseDTO){
        caseService.closeCase(closeCaseDTO);


    }

}
