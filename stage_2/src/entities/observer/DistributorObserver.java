package entities.observer;

import entities.Producer;

import java.util.List;

public interface DistributorObserver {
    void update(List<Producer> producers);
}
