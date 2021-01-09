package fileio.output;

import entities.contracts.DistributionContract;
import entities.Distributor;

import java.util.ArrayList;
import java.util.List;

/**
 * contains distributor information for output
 */
public final class DistributorOutputData {
    private final int id;
    private final int energyNeededKW;
    private final int contractCost;
    private final int budget;
    private final String producerStrategy;
    private final boolean isBankrupt;
    private final List<ContractOutputData> contracts;

    public DistributorOutputData(final Distributor distributor) {
        id = distributor.getId();
        energyNeededKW = distributor.getEnergyNeeded();
        contractCost = distributor.getContractPrice();
        budget = distributor.getBudget();
        producerStrategy = distributor.getProducerStrategy().getLabel();
        isBankrupt = distributor.isBankrupt();
        contracts = new ArrayList<>();
        for (DistributionContract distributionContract : distributor.getContracts()) {
            contracts.add(new ContractOutputData(distributionContract));
        }
    }

    public int getId() {
        return id;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public int getContractCost() {
        return contractCost;
    }

    public int getBudget() {
        return budget;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public List<ContractOutputData> getContracts() {
        return contracts;
    }
}
