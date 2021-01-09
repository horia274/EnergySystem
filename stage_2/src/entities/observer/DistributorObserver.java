package entities.observer;

public interface DistributorObserver {
    /**
     * when a producer receives an update, all of their distributors
     * has to perform an update, to choose again the production contracts
     */
    void update();
}
