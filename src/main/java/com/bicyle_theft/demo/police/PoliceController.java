package com.bicyle_theft.demo.police;

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

@RequestMapping("polices")
public class PoliceController {

    private final PoliceService policeService;

    @Autowired

    public PoliceController(PoliceService policeService) {
        this.policeService = policeService;
    }

    @Operation(summary = "create a police")
    @PostMapping
    public Police createPolice(@RequestBody Police police) {
        return policeService.createPolice(police);
    }

    @Operation(summary = "Get all polices")
    @GetMapping
    public List<Police> getPolices() {
        return policeService.getPolices();
    }

    @Operation(summary = "Get a police by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the police",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Police.class)) }),
            @ApiResponse(responseCode = "404", description = "this police Not Found!",
                    content = @Content) })

    @GetMapping(value = "/{policeId}")
    public Optional<Police> getPoliceByID(@PathVariable("policeId") UUID id) {
        return policeService.getPoliceByID(id);
    }


    @Operation(summary = "edit a police by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "edit the police",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Police.class)) }),
            @ApiResponse(responseCode = "404", description = "this police Not Found!",
                    content = @Content) })
    @PutMapping(value = "/{policeId}")
    public Police updatePolice(@PathVariable(value = "policeId") UUID id, @RequestBody Police police) {
        return policeService.updatePolice(id, police);
    }

    @Operation(summary = "delete a police by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delete the police",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Police.class)) }),
            @ApiResponse(responseCode = "404", description = "this police Not Found!",
                    content = @Content) })

    @DeleteMapping(value = "/{policeId}")
    public void deleteByID(@PathVariable(value = "policeId") UUID id) {
        policeService.deleteByID(id);
    }

}
