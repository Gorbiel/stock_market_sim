package gorbiel.stock_sim.availability.service;

/**
 * Provides functionality to simulate application failure.
 *
 * <p>Used by the chaos endpoint to terminate the current application instance.
 * Intended for testing availability and restart behavior.
 */
public interface ChaosService {

    /**
     * Terminates the current application instance.
     *
     * <p>This method is expected to stop the running process immediately.
     * No guarantees are made about completing the current request.
     */
    void terminate();
}
