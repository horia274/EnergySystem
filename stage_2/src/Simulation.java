import entities.Consumer;
import entities.Producer;
import entities.contracts.DistributionContract;
import entities.Distributor;
import fileio.input.*;

import java.util.ArrayList;
import java.util.List;

public final class Simulation {
    private final List<Consumer> consumers;
    private final List<Distributor> distributors;
    private final List<Producer> producers;
    private final List<MonthlyUpdateInputData> monthlyUpdates;

    public Simulation(final InputData inputData) {
        List<ConsumerInputData> consumerInput = inputData.getInitialData().getConsumers();
        consumers = new ArrayList<>();
        for (ConsumerInputData consumer : consumerInput) {
            Consumer currentConsumer = new Consumer(consumer);
            consumers.add(currentConsumer);
        }

        List<DistributorInputData> distributorInput = inputData.getInitialData().getDistributors();
        distributors = new ArrayList<>();
        for (DistributorInputData distributor : distributorInput) {
            Distributor currentDistributor = new Distributor(distributor);
            distributors.add(currentDistributor);
        }

        List<ProducerInputData> producerInputData = inputData.getInitialData().getProducers();
        producers = new ArrayList<>();
        for (ProducerInputData producer : producerInputData) {
            Producer currentProducer = new Producer(producer, distributors);
            producers.add(currentProducer);
        }

        monthlyUpdates = inputData.getMonthlyUpdates();
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    public List<Producer> getProducers() { return producers; }

    /**
     * compute first round of simulation
     */
    private void firstTurn() {
        for (Producer producer : producers) {
            producer.notifyObservers(producers);
            producer.addCurrentDistributors();
        }

        for (Distributor distributor : distributors) {
            if (!distributor.isBankrupt()) {
                /* recalculate production cost */
                distributor.computeProductionCost();
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
                DistributionContract distributionContract = consumer.getContract();
                Distributor chosenDistributor = distributionContract.getDistributor();
                chosenDistributor.addContract(distributionContract);

                /* pay contract */
                consumer.payBill();
                /* decrease duration of contract */
                distributionContract.decreaseContractMonths();
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

    private void update(final MonthlyUpdateInputData monthlyUpdate) {
        List<ConsumerInputData> newConsumers = monthlyUpdate.getNewConsumers();
        List<DistributorChangesInputData> distributorsChanges = monthlyUpdate.getDistributorChanges();
        List<ProducerChangesInputData> producersChanges = monthlyUpdate.getProducerChanges();

        /* update consumers list */
        for (ConsumerInputData newConsumer : newConsumers) {
            Consumer currentConsumer = new Consumer(newConsumer);
            consumers.add(currentConsumer);
        }

        /* update distributors attributes */
        for (DistributorChangesInputData distributorChanges : distributorsChanges) {
            int id = distributorChanges.getId();
            Distributor currentDistributor = Distributor.findDistributor(distributors, id);
            if (currentDistributor != null) {
                currentDistributor.setInfrastructureCost(distributorChanges.getInfrastructureCost());
            }
        }

        /* update producers attributes */
        for (ProducerChangesInputData producerChanges : producersChanges) {
            int id = producerChanges.getId();
            Producer currentProducer = Producer.findProducer(producers, id);
            if (currentProducer != null) {
                currentProducer.setEnergyPerDistributor(producerChanges.getEnergyPerDistributor());
            }
        }
    }

    /**
     * simulate intermediate round
     * @param monthlyUpdate new consumers list and new distributors attributes
     */
    private void simulateTurn(final MonthlyUpdateInputData monthlyUpdate) {
        update(monthlyUpdate);
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
