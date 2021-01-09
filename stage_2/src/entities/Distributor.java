package entities;

import constants.Const;
import entities.contracts.DistributionContract;
import entities.contracts.ProductionContract;
import entities.observer.DistributorObserver;
import fileio.input.DistributorInputData;
import strategies.EnergyChoiceStrategyType;
import strategies.Strategy;
import strategies.StrategyFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Distributor implements DistributorObserver {
    private final int id;
    private final int contractLength;
    private int budget;
    private int infrastructureCost;
    private int productionCost;
    private int profit;

    private boolean isBankrupt;

    private final List<DistributionContract> distributionContracts;
    private int contractPrice;

    private final int energyNeeded;
    private EnergyChoiceStrategyType producerStrategy;
    private final List<ProductionContract> productionContracts;
    private boolean hasUpdatedProducer;

    public Distributor(final DistributorInputData distributorInputData) {
        id = distributorInputData.getId();
        contractLength = distributorInputData.getContractLength();
        budget = distributorInputData.getInitialBudget();
        infrastructureCost = distributorInputData.getInitialInfrastructureCost();
        distributionContracts = new ArrayList<>();
        productionContracts = new ArrayList<>();
        energyNeeded = distributorInputData.getEnergyNeededKW();
        producerStrategy = distributorInputData.getProducerStrategy();
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

    public int getContractPrice() {
        return contractPrice;
    }

    public List<DistributionContract> getContracts() {
        return distributionContracts;
    }

    public int getEnergyNeeded() {
        return energyNeeded;
    }

    public EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(EnergyChoiceStrategyType producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    public boolean hasUpdatedProducer() {
        return hasUpdatedProducer;
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
        if (distributionContracts.size() != 0) {
            contractPrice = (int) Math.round(Math.floor(infrastructureCost
                                                        / (double) distributionContracts.size())
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
     * @param distributionContract contract that will be added
     */
    public void addContract(final DistributionContract distributionContract) {
        if (distributionContracts.contains(distributionContract)) {
            return;
        }
        distributionContracts.add(distributionContract);
    }

    /**
     * remove contracts that are no longer valid
     * contract length passed and was paid every month
     */
    public void removeInvalidContracts() {
        distributionContracts.removeIf(contract -> !contract.isValid() && contract.wasPaid());
    }

    /**
     * remove contract if consumer is bankrupt
     */
    public void removeBankruptConsumers() {
        distributionContracts.removeIf(contract -> contract.getConsumer().isBankrupt());
    }

    /**
     * remove all contracts if current distributor becomes bankrupt
     */
    public void removeContractsIfIsBankrupt() {
        if (isBankrupt) {
            distributionContracts.clear();
        }
    }

    /**
     * add all payments from consumers
     */
    public void earnMoneyFromConsumers() {
        for (DistributionContract distributionContract : distributionContracts) {
            Consumer currentConsumer = distributionContract.getConsumer();
            DistributionContract currentDistributionContract = currentConsumer.getContract();
            DistributionContract oldDistributionContract = currentConsumer.getOldContract();

            /* check if contract can be paid and if it has an overdue payment */
            if (!currentConsumer.isBankrupt() && !currentDistributionContract.isExpired()) {
                if (currentConsumer.hasOverduePayment()) {
                    budget += (int) Math.round(Math.floor(Const.OVERDUE * oldDistributionContract.getPrice()));
                } else if (currentDistributionContract.wasPaid()) {
                    budget += currentDistributionContract.getPrice();
                }
            }
        }
    }

    /**
     * compute payment
     * @return total payment
     */
    private int computePayment() {
        return infrastructureCost + productionCost * distributionContracts.size();
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

    private void computeProductionCost() {
        double cost = 0;

        for (ProductionContract contract : productionContracts) {
            contract.computeCost();
            cost += contract.getPrice();
        }

        productionCost = (int) Math.round(Math.floor(cost / 10));
    }

    public void addProductionContract(ProductionContract productionContract) {
        productionContracts.add(productionContract);
    }

    private void removeAllProductionContracts() {
        productionContracts.clear();
    }

    @Override
    public boolean hasContractWith(Producer producer) {
        for (ProductionContract contract : productionContracts) {
            if (contract.getProducer().equals(producer)) {
                return true;
            }
        }
        return false;
    }

    private void computeStrategy(List<Producer> producers) {
        StrategyFactory strategyFactory = StrategyFactory.getInstance();
        Strategy strategy = strategyFactory.createStrategy(producerStrategy, this, producers);
        strategy.chooseProducers();
    }

    public void chooseProducers(List<Producer> producers) {
        if (!isBankrupt) {
            for (ProductionContract contract : productionContracts) {
                Producer producer = contract.getProducer();
                producer.unregister(this);
                producer.removeInvalidContract(contract);
            }
            removeAllProductionContracts();
            computeStrategy(producers);
            computeProductionCost();
            hasUpdatedProducer = false;
        }
    }

    @Override
    public void update() {
        hasUpdatedProducer = true;
    }
}
