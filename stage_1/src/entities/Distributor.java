package entities;

import fileio.input.DistributorInputData;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class Distributor {
    private final int id;
    private final int contractLength;
    private int budget;
    private int infrastructureCost;
    private int productionCost;
    private int profit;
    private boolean isBankrupt;
    private List<Contract> contracts;
    private int contractPrice;

    public Distributor(DistributorInputData distributorInputData) {
        id = distributorInputData.getId();
        contractLength = distributorInputData.getContractLength();
        budget = distributorInputData.getInitialBudget();
        infrastructureCost = distributorInputData.getInitialInfrastructureCost();
        productionCost = distributorInputData.getInitialProductionCost();
        contracts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public int getContractLength() {
        return contractLength;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(int productionCost) {
        this.productionCost = productionCost;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public int getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(int contractPrice) {
        this.contractPrice = contractPrice;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    @Override
    public String toString() {
        return "Distributor{" +
                "id=" + id +
                ", contractLength=" + contractLength +
                ", budget=" + budget +
                ", infrastructureCost=" + infrastructureCost +
                ", productionCost=" + productionCost +
                ", profit=" + profit +
                ", isBankrupt=" + isBankrupt +
                ", contracts=" + contracts +
                ", contractPrice=" + contractPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Distributor that = (Distributor) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private void computeProfit() {
        profit = (int) Math.round(Math.floor(0.2 * productionCost));

    }

    public void computeContractPrice() {
        computeProfit();
        if (contracts.size() == 0) {
            contractPrice = infrastructureCost + productionCost + profit;
        } else {
            contractPrice = (int) Math.round(Math.floor(infrastructureCost / contracts.size()) + productionCost + profit);
        }
    }

    public static Distributor findDistributor(List<Distributor> distributors, int id) {
        for (Distributor distributor : distributors) {
            if (distributor.id == id) {
                return distributor;
            }
        }
        return null;
    }

    public void addContract(Contract contract) {
        if (contracts.contains(contract)) {
            return;
        }
        contracts.add(contract);
    }

    public void removeInvalidContracts() {
        contracts.removeIf(contract -> !contract.isValid() && contract.wasPaid());
    }

    public void removeBankruptConsumers() {
        contracts.removeIf(contract -> contract.getConsumer().isBankrupt());
    }

    public void removeContractsIfIsBankrupt() {
        if (isBankrupt) {
            contracts.clear();
        }
    }

    public void earnMoneyFromConsumers() {
        for (Contract contract : contracts) {
            Consumer currentConsumer = contract.getConsumer();
            Contract currentContract = currentConsumer.getContract();
            Contract oldContract = currentConsumer.getOldContract();

            if (currentConsumer.isBankrupt() || currentContract.isExpired()) {
                continue;
            }
            else if (currentConsumer.hasOverduePayment()) {
                budget += (int) Math.round(Math.floor(1.2 * oldContract.getPrice()));
            }
            else if (currentContract.wasPaid()){
                budget += currentContract.getPrice();
            }
        }
    }

    private int computePayment() {
        return infrastructureCost + productionCost * contracts.size();
    }

    public void payBill() {
        budget -= computePayment();
        if (budget < 0) {
            isBankrupt = true;
        }
    }
}
