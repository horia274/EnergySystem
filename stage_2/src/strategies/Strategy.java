package strategies;

import entities.Distributor;
import entities.Producer;
import entities.contracts.ProductionContract;

import java.util.ArrayList;
import java.util.List;

public abstract class Strategy {
    protected Distributor distributor;
    protected List<Producer> producers;

    public Strategy(Distributor distributor, List<Producer> producers) {
        this.distributor = distributor;
        this.producers = new ArrayList<>(producers);
    }

    protected abstract void sortProducers();

    public void chooseProducers() {
        int currentEnergy = 0;

        sortProducers();

        for (Producer producer : producers) {
            if (currentEnergy < distributor.getEnergyNeeded()) {
                if (producer.getNumberOfContracts() < producer.getMaxDistributors()) {
                    currentEnergy += producer.getEnergyPerDistributor();
                    ProductionContract newContract = new ProductionContract(producer, distributor, producer.getPriceKW(), producer.getEnergyPerDistributor());
                    distributor.addProductionContract(newContract);
                    producer.addContract(newContract);
                    producer.register(distributor);
                }
            } else {
                break;
            }
        }
    }
}
