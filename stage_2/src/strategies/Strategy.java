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

    /**
     * Sort the producers by given criteria from strategy
     * Choose the first producers who are still available until
     * the energy needed by the distributor is reached
     */
    public void chooseProducers() {
        int currentEnergy = 0;

        /* sort producers by given strategy */
        sortProducers();

        for (Producer producer : producers) {
            if (currentEnergy < distributor.getEnergyNeeded()) {
                if (producer.getNumberOfContracts() < producer.getMaxDistributors()) {
                    /* add producers until the current distributor reached his demand of energy */
                    currentEnergy += producer.getEnergyPerDistributor();

                    /* create new production contract */
                    double price = producer.getPriceKW();
                    int energy = producer.getEnergyPerDistributor();
                    ProductionContract newContract;
                    newContract = new ProductionContract(producer, distributor, price, energy);

                    /* add new contract to distributor and to producer */
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
