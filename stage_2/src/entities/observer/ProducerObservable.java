package entities.observer;

public interface ProducerObservable {
    /**
     * register a distributor (current producer has contract with him)
     * @param distributor distributor who will be registered
     */
    void register(DistributorObserver distributor);

    /**
     * unregister a distributor (current producer has not contract with him anymore)
     * @param distributor distributor who will be unregistered
     */
    void unregister(DistributorObserver distributor);

    /**
     * when the producer is updated, he used this method to notify
     * all the observers (distributors with whom has production contract)
     */
    void notifyObservers();
}
