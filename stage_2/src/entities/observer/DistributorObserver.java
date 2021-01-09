package entities.observer;

import entities.Producer;

import java.util.List;

public interface DistributorObserver {
    void update();
    boolean hasContractWith(Producer producer);
}
