package entities.observer;

import entities.Producer;

import java.util.List;

public interface ProducerObservable {
    void register(DistributorObserver distributor);
    void unregister(DistributorObserver distributor);
    void notifyObservers();
}
