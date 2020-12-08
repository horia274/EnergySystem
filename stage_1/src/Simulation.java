import entities.Consumer;
import entities.Contract;
import entities.Distributor;
import entities.factory.ConsumerFactory;
import entities.factory.ConsumerFactory.ConsumerType;
import fileio.input.InputData;
import fileio.input.MonthlyUpdateInputData;
import fileio.input.ConsumerInputData;
import fileio.input.DistributorInputData;
import fileio.input.CostChangesInputData;

import java.util.ArrayList;
import java.util.List;

public final class Simulation {
    private final ConsumerFactory factory;
    private final List<Consumer> consumers;
    private final List<Distributor> distributors;
    private final List<MonthlyUpdateInputData> monthlyUpdates;

    public Simulation(final InputData inputData) {
        factory = ConsumerFactory.getInstance();
        List<ConsumerInputData> consumerInput = inputData.getInitialData().getConsumers();
        consumers = new ArrayList<>();
        for (ConsumerInputData consumer : consumerInput) {
            Consumer currentConsumer;
            currentConsumer = factory.createConsumer(ConsumerType.Concrete, consumer);
            consumers.add(currentConsumer);
        }

        List<DistributorInputData> distributorInput = inputData.getInitialData().getDistributors();
        distributors = new ArrayList<>();
        for (DistributorInputData distributor : distributorInput) {
            Distributor currentDistributor = new Distributor(distributor);
            distributors.add(currentDistributor);
        }

        monthlyUpdates = inputData.getMonthlyUpdates();
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    /**
     * compute first round of simulation
     */
    private void firstTurn() {
        for (Distributor distributor : distributors) {
            if (!distributor.isBankrupt()) {
                /* recalculate contracts price */
                distributor.computeContractPrice();
                /* remove invalid contracts */
                distributor.removeInvalidContracts();
            }
        }

        for (Consumer consumer : consumers) {
            if (!consumer.isBankrupt()) {
                /* receive salary */
                consumer.earnSalary();
                /* choose contract */
                consumer.chooseContract(distributors);

                /* add contract to distributor list of contracts */
                Contract contract = consumer.getContract();
                Distributor chosenDistributor = contract.getDistributor();
                chosenDistributor.addContract(contract);

                /* pay contract */
                consumer.payBill();
                /* decrease duration of contract */
                contract.decreaseContractMonths();
            }
        }

        for (Distributor distributor : distributors) {
            if (!distributor.isBankrupt()) {
                /* receive money from consumers */
                distributor.earnMoneyFromConsumers();
                /* pay bill */
                distributor.payBill();
                /* remove bankrupt consumers */
                distributor.removeBankruptConsumers();
            } else {
                /* removed all contracts if is bankrupt */
                distributor.removeContractsIfIsBankrupt();
            }
        }
    }

    /**
     * simulate intermediate round
     * @param monthlyUpdate new consumers list and new distributors attributes
     */
    private void simulateTurn(final MonthlyUpdateInputData monthlyUpdate) {
        List<ConsumerInputData> newConsumers = monthlyUpdate.getNewConsumers();
        List<CostChangesInputData> costsChanges = monthlyUpdate.getCostsChanges();

        /* update consumers list */
        for (ConsumerInputData newConsumer : newConsumers) {
            Consumer currentConsumer;
            currentConsumer = factory.createConsumer(ConsumerType.Concrete, newConsumer);
            consumers.add(currentConsumer);
        }

        /* update distributors attributes */
        for (CostChangesInputData costChanges : costsChanges) {
            int id = costChanges.getId();
            Distributor currentDistributor = Distributor.findDistributor(distributors, id);
            if (currentDistributor != null) {
                currentDistributor.setInfrastructureCost(costChanges.getInfrastructureCost());
                currentDistributor.setProductionCost(costChanges.getProductionCost());
            }
        }

        /* simulate round 0 */
        firstTurn();
    }

    /**
     * simulate the entire procedure
     */
    public void simulateAllTurns() {
        firstTurn();
        for (MonthlyUpdateInputData monthlyUpdate : monthlyUpdates) {
            simulateTurn(monthlyUpdate);
        }
    }
}
