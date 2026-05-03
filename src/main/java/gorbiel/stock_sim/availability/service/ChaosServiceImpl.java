package gorbiel.stock_sim.availability.service;

import org.springframework.stereotype.Service;

/**
 * Terminates the application process after a short delay.
 *
 * <p>Delay ensures HTTP response can be sent before shutdown.
 */
@Service
public class ChaosServiceImpl implements ChaosService {

    @Override
    public void terminate() {
        new Thread(() -> {
                    try {
                        // Small delay to allow response to complete
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    System.exit(1);
                })
                .start();
    }
}
