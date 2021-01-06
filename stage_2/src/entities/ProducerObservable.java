package entities;

import java.util.List;

public interface ProducerObservable {
    void register(DistributorObserver distributor);
    void unregisterAll();
    void notifyObservers(List<Producer> producers);
}
