package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.List;

public class PriceStrategy extends Strategy {
    public PriceStrategy(Distributor distributor, List<Producer> producers) {
        super(distributor, producers);
    }

    @Override
    protected void sortProducers() {
        producers.sort((p1, p2) -> {
            if (p1.getPriceKW() != p2.getPriceKW()) {
                return Double.compare(p1.getPriceKW(), p2.getPriceKW());
            }
            if (p1.getEnergyPerDistributor() != p2.getEnergyPerDistributor()) {
                return Integer.compare(p1.getEnergyPerDistributor(), p2.getEnergyPerDistributor());
            }
            return Integer.compare(p1.getId(), p2.getId());
        });
    }
}
