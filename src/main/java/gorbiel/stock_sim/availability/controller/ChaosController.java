package gorbiel.stock_sim.availability.controller;

import gorbiel.stock_sim.availability.service.ChaosService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChaosController {

    private final ChaosService chaosService;

    @PostMapping("/chaos")
    public void chaos() {
        chaosService.terminate();
    }
}
