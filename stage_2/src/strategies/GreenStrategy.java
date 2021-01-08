package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.List;

public class GreenStrategy extends Strategy {
    public GreenStrategy(Distributor distributor, List<Producer> producers) {
        super(distributor, producers);
    }

    @Override
    protected void sortProducers() {
        producers.sort((p1, p2) -> {
            if (p1.getEnergyType().isRenewable() != p2.getEnergyType().isRenewable()) {
                return Boolean.compare(p2.getEnergyType().isRenewable(), p1.getEnergyType().isRenewable());
            }
            if (p1.getPriceKW() != p2.getPriceKW()) {
                return Double.compare(p1.getPriceKW(), p2.getPriceKW());
            }
            if (p1.getEnergyPerDistributor() != p2.getEnergyPerDistributor()) {
                return Integer.compare(p2.getEnergyPerDistributor(), p1.getEnergyPerDistributor());
            }
            return Integer.compare(p1.getId(), p2.getId());
        });
    }
}
