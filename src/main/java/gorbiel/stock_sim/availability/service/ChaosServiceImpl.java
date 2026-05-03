package gorbiel.stock_sim.availability.service;

import org.springframework.stereotype.Service;

@Service
public class ChaosServiceImpl implements ChaosService {

    @Override
    public void terminate() {
        new Thread(() -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    System.exit(1);
                })
                .start();
    }
}
