package entities;

import constants.Const;
import fileio.input.DistributorInputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Distributor {
    private final int id;
    private final int contractLength;
    private int budget;
    private int infrastructureCost;
    private int productionCost;
    private int profit;

    private boolean isBankrupt;

    private final List<Contract> contracts;
    private int contractPrice;

    public Distributor(final DistributorInputData distributorInputData) {
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

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(final int productionCost) {
        this.productionCost = productionCost;
    }

    public int getContractPrice() {
        return contractPrice;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    /**
     * check if is bankrupt
     * @return Boolean object
     */
    public boolean isBankrupt() {
        return isBankrupt;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Distributor that = (Distributor) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * compute profit
     */
    private void computeProfit() {
        profit = (int) Math.round(Math.floor(Const.PROFIT * productionCost));
    }

    /**
     * compute contract price
     */
    public void computeContractPrice() {
        computeProfit();
        if (contracts.size() != 0) {
            contractPrice = (int) Math.round(Math.floor(infrastructureCost
                                                        / (double) contracts.size())
                                                        + productionCost + profit);
        } else {
            contractPrice = infrastructureCost + productionCost + profit;
        }
    }

    /**
     * search for a distributor by id in a list of distributors
     * @param distributors list of distributors
     * @param id searched id
     * @return Distributor object on null if not found
     */
    public static Distributor findDistributor(final List<Distributor> distributors, final int id) {
        for (Distributor distributor : distributors) {
            if (distributor.id == id) {
                return distributor;
            }
        }
        return null;
    }

    /**
     * add contract to contracts list
     * @param contract contract that will be added
     */
    public void addContract(final Contract contract) {
        if (contracts.contains(contract)) {
            return;
        }
        contracts.add(contract);
    }

    /**
     * remove contracts that are no longer valid
     * contract length passed and was paid every month
     */
    public void removeInvalidContracts() {
        contracts.removeIf(contract -> !contract.isValid() && contract.wasPaid());
    }

    /**
     * remove contract if consumer is bankrupt
     */
    public void removeBankruptConsumers() {
        contracts.removeIf(contract -> contract.getConsumer().isBankrupt());
    }

    /**
     * remove all contracts if current distributor becomes bankrupt
     */
    public void removeContractsIfIsBankrupt() {
        if (isBankrupt) {
            contracts.clear();
        }
    }

    /**
     * add all payments from consumers
     */
    public void earnMoneyFromConsumers() {
        for (Contract contract : contracts) {
            Consumer currentConsumer = contract.getConsumer();
            Contract currentContract = currentConsumer.getContract();
            Contract oldContract = currentConsumer.getOldContract();

            /* check if contract can be paid and if it has an overdue payment */
            if (!currentConsumer.isBankrupt() && !currentContract.isExpired()) {
                if (currentConsumer.hasOverduePayment()) {
                    budget += (int) Math.round(Math.floor(Const.OVERDUE * oldContract.getPrice()));
                } else if (currentContract.wasPaid()) {
                    budget += currentContract.getPrice();
                }
            }
        }
    }

    /**
     * compute payment
     * @return total payment
     */
    private int computePayment() {
        return infrastructureCost + productionCost * contracts.size();
    }

    /**
     * pay bill
     */
    public void payBill() {
        budget -= computePayment();
        if (budget < 0) {
            isBankrupt = true;
        }
    }
}
