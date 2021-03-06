package fileio.output;

import entities.Contract;
import entities.Distributor;

import java.util.ArrayList;
import java.util.List;

/**
 * contains distributor information for output
 */
public final class DistributorOutputData {
    private final int id;
    private final int budget;
    private final boolean isBankrupt;
    private final List<ContractOutput> contracts;

    public DistributorOutputData(final Distributor distributor) {
        id = distributor.getId();
        budget = distributor.getBudget();
        isBankrupt = distributor.isBankrupt();
        contracts = new ArrayList<>();
        for (Contract contract : distributor.getContracts()) {
            contracts.add(new ContractOutput(contract));
        }
    }

    public int getId() {
        return id;
    }

    public int getBudget() {
        return budget;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public List<ContractOutput> getContracts() {
        return contracts;
    }
}
