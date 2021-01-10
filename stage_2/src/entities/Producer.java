package entities;

import entities.contracts.ProductionContract;
import entities.energytypes.EnergyType;
import entities.observer.DistributorObserver;
import entities.observer.ProducerObservable;
import fileio.input.ProducerInputData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class Producer implements ProducerObservable {
    private int id;
    private final EnergyType energyType;
    private int maxDistributors;
    private double priceKW;
    private int energyPerDistributor;

    private final List<ProductionContract> productionContracts;
    private final List<DistributorObserver> distributors;
    private boolean isUpdated;

    private final List<List<Integer>> allDistributors;

    public Producer(ProducerInputData producer) {
        id = producer.getId();
        energyType = producer.getEnergyType();
        maxDistributors = producer.getMaxDistributors();
        priceKW = producer.getPriceKW();
        energyPerDistributor = producer.getEnergyPerDistributor();
        productionContracts = new ArrayList<>();
        distributors = new ArrayList<>();
        isUpdated = true;
        allDistributors = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(double priceKW) {
        this.priceKW = priceKW;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    /**
     * if energy per distributor is updated, than isUpdated flag is set
     * @param energyPerDistributor how much energy gives current producer
     */
    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
        isUpdated = true;
    }

    public List<List<Integer>> getAllDistributors() {
        return allDistributors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Producer producer = (Producer) o;
        return id == producer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * search for a producer by id in a list of producers
     * @param producers list of producers
     * @param id searched id
     * @return Producers object or null if not found
     */
    public static Producer findProducer(List<Producer> producers, int id) {
        if (id < producers.size()) {
            return producers.get(id);
        }
        return null;
    }

    /**
     * compute the number of production contracts
     * @return int value
     */
    public int getNumberOfContracts() {
        return productionContracts.size();
    }

    /**for (Producer producer : producers) {
            if (producer.id == id) {
                return producer;
            }
        }
     * add a production contract in list of contracts
     * @param productionContract given contract which will be added
     */
    public void addContract(ProductionContract productionContract) {
        if (!productionContracts.contains(productionContract)) {
            productionContracts.add(productionContract);
        }
    }

    /**
     * remove a contract
     * @param contract current contract
     */
    public void removeInvalidContract(ProductionContract contract) {
        productionContracts.remove(contract);
    }

    @Override
    public void register(DistributorObserver distributor) {
        if (!distributors.contains(distributor)) {
            distributors.add(distributor);
        }
    }

    @Override
    public void unregister(DistributorObserver distributor) {
        distributors.remove(distributor);
    }

    @Override
    public void notifyObservers() {
        if (isUpdated) {
            for (DistributorObserver distributor : distributors) {
                /* update each observer by setting or not the *hasUpdatedProducer* flag */
                distributor.update();
            }
            isUpdated = false;
        }
    }

    /**
     * save the list of distributors id any month
     */
    public void addCurrentDistributors() {
        List<Integer> currentDistributors = new ArrayList<>();
        for (ProductionContract contract : productionContracts) {
            Distributor currentDistributor = contract.getDistributor();
            currentDistributors.add(currentDistributor.getId());
        }
        currentDistributors.sort(Comparator.naturalOrder());
        allDistributors.add(currentDistributors);
    }
}
