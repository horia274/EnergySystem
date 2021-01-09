package entities;

import entities.contracts.ProductionContract;
import entities.energytypes.EnergyType;
import entities.observer.DistributorObserver;
import entities.observer.ProducerObservable;
import fileio.input.ProducerInputData;

import java.util.ArrayList;
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

    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
        isUpdated = true;
    }

    public List<List<Integer>> getAllDistributors() {
        return allDistributors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producer producer = (Producer) o;
        return id == producer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static Producer findProducer(List<Producer> producers, int id) {
        for (Producer producer : producers) {
            if (producer.id == id) {
                return producer;
            }
        }
        return null;
    }

    public int getNumberOfContracts() {
        int count = 0;
        for (ProductionContract contract : productionContracts) {
            Distributor distributor = contract.getDistributor();
            if (distributor.hasContractWith(this)) {
                count++;
            }
        }
        return count;
//        return productionContracts.size();
    }

    public void addContract(ProductionContract productionContract) {
        if (!productionContracts.contains(productionContract)) {
            productionContracts.add(productionContract);
        }
    }

    public void removeCurrentContracts() {
        productionContracts.clear();
    }

//    public void removeOldContracts() {
//        for (ProductionContract contract : productionContracts) {
//            Distributor currentDistributor = contract.getDistributor();
//            if (!currentDistributor.hasContractWith(this)) {
//                productionContracts.remove(contract);
//            }
//        }
//    }

    @Override
    public void register(DistributorObserver distributor) {
        if (!distributors.contains(distributor)) {
            distributors.add(distributor);
        }
    }

    @Override
    public void unregisterAll() {
        distributors.clear();
    }

    @Override
    public void notifyObservers() {
        if (isUpdated) {
            List<DistributorObserver> currentDistributors = new ArrayList<>(distributors);
            unregisterAll();
            removeCurrentContracts();
            for (DistributorObserver currentDistributor : currentDistributors) {
                currentDistributor.update();
            }
            isUpdated = false;
        }
    }

    public void addCurrentDistributors() {
        List<Integer> currentDistributors = new ArrayList<>();
        for (ProductionContract contract : productionContracts) {
            Distributor currentDistributor = contract.getDistributor();
            if (currentDistributor.hasContractWith(this)) {
                currentDistributors.add(currentDistributor.getId());
            }
        }
        allDistributors.add(currentDistributors);
    }
}
