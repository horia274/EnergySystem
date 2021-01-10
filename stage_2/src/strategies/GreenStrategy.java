package strategies;

import entities.Distributor;
import entities.Producer;
import entities.energytypes.EnergyType;

import java.util.List;

public final class GreenStrategy extends Strategy {
    public GreenStrategy(Distributor distributor, List<Producer> producers) {
        super(distributor, producers);
    }

    @Override
    protected void sortProducers() {
        producers.sort((p1, p2) -> {
            if (p1.getEnergyType().isRenewable() != p2.getEnergyType().isRenewable()) {
                EnergyType e1 = p1.getEnergyType();
                EnergyType e2 = p2.getEnergyType();
                return Boolean.compare(e2.isRenewable(), e1.isRenewable());
            }
            if (p1.getPriceKW() != p2.getPriceKW()) {
                double price1 = p1.getPriceKW();
                double price2 = p2.getPriceKW();
                return Double.compare(price1, price2);
            }
            if (p1.getEnergyPerDistributor() != p2.getEnergyPerDistributor()) {
                return Integer.compare(p2.getEnergyPerDistributor(), p1.getEnergyPerDistributor());
            }
            return Integer.compare(p1.getId(), p2.getId());
        });
    }
}
