package gorbiel.stock_sim.availability.controller;

import gorbiel.stock_sim.availability.service.ChaosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Availability", description = "Endpoint for simulating application instance failure")
@RestController
@RequiredArgsConstructor
public class ChaosController {

    private final ChaosService chaosService;

    @Operation(
            summary = "Terminate current application instance",
            description = "Terminates the application instance that handles the request. Used to simulate failure scenarios.")
    @ApiResponse(responseCode = "200", description = "Instance termination triggered")
    @PostMapping("/chaos")
    public void chaos() {
        chaosService.terminate();
    }
}