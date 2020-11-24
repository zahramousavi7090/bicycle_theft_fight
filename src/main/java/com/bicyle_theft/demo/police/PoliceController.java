package com.bicyle_theft.demo.police;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@RequestMapping("polices")
public class PoliceController {

    private final PoliceService policeService;

    @Autowired

    public PoliceController(PoliceService policeService) {
        this.policeService = policeService;
    }

    @Operation(summary = "Get all polices")
    @GetMapping
    public List<Police> getPolices() {
        return policeService.getPolices();
    }
}
