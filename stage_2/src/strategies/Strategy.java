package strategies;

import entities.Distributor;
import entities.Producer;
import entities.contracts.ProductionContract;

import java.util.List;

public abstract class Strategy {
    protected Distributor distributor;
    protected List<Producer> producers;

    public Strategy(Distributor distributor, List<Producer> producers) {
        this.distributor = distributor;
        this.producers = producers;
    }

    protected abstract void sortProducers();

    public void chooseProducers() {
        int currentEnergy = 0;

        sortProducers();

        for (Producer producer : producers) {
            if (producer.getNumberOfContracts() < producer.getMaxDistributors() && currentEnergy < distributor.getEnergyNeeded()) {
                currentEnergy += producer.getEnergyPerDistributor();
                ProductionContract newContract = new ProductionContract(producer, distributor, producer.getPriceKW(), producer.getEnergyPerDistributor());
                distributor.addProductionContract(newContract);
                producer.addContract(newContract);
                producer.register(distributor);
            } else {
                break;
            }
        }
    }
}
