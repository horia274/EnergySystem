package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.List;

public class QuantityStrategy extends Strategy {
    public QuantityStrategy(Distributor distributor, List<Producer> producers) {
        super(distributor, producers);
    }

    @Override
    protected void sortProducers() {
        producers.sort((p1, p2) -> {
            if (p1.getEnergyPerDistributor() != p2.getEnergyPerDistributor()) {
                return Integer.compare(p1.getEnergyPerDistributor(), p2.getEnergyPerDistributor());
            }
            return Integer.compare(p1.getId(), p2.getId());
        });
    }
}
