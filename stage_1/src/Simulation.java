import entities.Consumer;
import entities.Contract;
import entities.Distributor;
import entities.factory.ConsumerFactory;
import fileio.input.*;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private List<Consumer> consumers;
    private List<Distributor> distributors;
    private int numberOfTurns;
    private List<MonthlyUpdateInputData> monthlyUpdates;

    public Simulation(InputData inputData) {
        List<ConsumerInputData> consumerInputData = inputData.getInitialData().getConsumers();
        consumers = new ArrayList<>();
        for (ConsumerInputData consumer : consumerInputData) {
            Consumer currentConsumer;
            currentConsumer = ConsumerFactory.getInstance().createConsumer(ConsumerFactory.ConsumerType.Concrete, consumer);
            consumers.add(currentConsumer);
        }

        List<DistributorInputData> distributorInputData = inputData.getInitialData().getDistributors();
        distributors = new ArrayList<>();
        for (DistributorInputData distributor : distributorInputData) {
            Distributor currentDistributor = new Distributor(distributor);
            distributors.add(currentDistributor);
        }

        numberOfTurns = inputData.getNumberOfTurns();
        monthlyUpdates = inputData.getMonthlyUpdates();
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    private void firstTurn() {
        for (Distributor distributor : distributors) {
            if (!distributor.isBankrupt()) {
                distributor.computeContractPrice();
                distributor.removeInvalidContracts();
            }
        }

        for (Consumer consumer : consumers) {
            if (!consumer.isBankrupt()) {
                consumer.earnSalary();
                consumer.chooseContract(distributors);

                Contract contract = consumer.getContract();
                Distributor chosenDistributor = contract.getDistributor();
                chosenDistributor.addContract(contract);

                consumer.payBill();
                contract.decreaseContractMonths();
            }
        }

        for(Distributor distributor : distributors) {
            if (!distributor.isBankrupt()) {
                distributor.removeContractsIfIsBankrupt();
                distributor.earnMoneyFromConsumers();
                distributor.payBill();
                distributor.removeBankruptConsumers();
            }
        }
    }

    private void simulateTurn(MonthlyUpdateInputData monthlyUpdate) {
        List<ConsumerInputData> newConsumers = monthlyUpdate.getNewConsumers();
        List<CostChangesInputData> costsChanges = monthlyUpdate.getCostsChanges();

        for (ConsumerInputData newConsumer : newConsumers) {
            Consumer currentConsumer;
            currentConsumer = ConsumerFactory.getInstance().createConsumer(ConsumerFactory.ConsumerType.Concrete, newConsumer);
            consumers.add(currentConsumer);
        }

        for (CostChangesInputData costChanges : costsChanges) {
            Distributor currentDistributor = Distributor.findDistributor(distributors, costChanges.getId());
            if (currentDistributor != null) {
                currentDistributor.setInfrastructureCost(costChanges.getInfrastructureCost());
                currentDistributor.setProductionCost(costChanges.getProductionCost());
            }
        }

        firstTurn();
    }

    public void simulateAllTurns() {
        firstTurn();
        for (MonthlyUpdateInputData monthlyUpdate : monthlyUpdates) {
            simulateTurn(monthlyUpdate);
        }
    }
}
