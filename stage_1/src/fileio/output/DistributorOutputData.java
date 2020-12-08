package fileio.output;

import entities.Contract;
import entities.Distributor;

import java.util.ArrayList;
import java.util.List;

public class DistributorOutputData {
    private int id;
    private int budget;
    private boolean isBankrupt;
    private List<ContractOutput> contracts;

    public DistributorOutputData(Distributor distributor) {
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

    public void setId(int id) {
        this.id = id;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public void setIsBankrupt(boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public List<ContractOutput> getContracts() {
        return contracts;
    }

    public void setContracts(List<ContractOutput> contracts) {
        this.contracts = contracts;
    }
}
