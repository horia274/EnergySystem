package entities;

import java.util.List;

public interface DistributorObserver {
    void update(List<Producer> producers);
}
