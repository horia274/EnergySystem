import entities.Consumer;
import entities.Producer;
import entities.contracts.DistributionContract;
import entities.Distributor;

import fileio.input.InputData;
import fileio.input.MonthlyUpdateInputData;
import fileio.input.ConsumerInputData;
import fileio.input.DistributorInputData;
import fileio.input.ProducerInputData;
import fileio.input.DistributorChangesInputData;
import fileio.input.ProducerChangesInputData;

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
            Producer currentProducer = new Producer(producer);
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

    public List<Producer> getProducers() {
        return producers;
    }

    /**
     * all distributors choose the energy producers, for the first round
     */
    private void chooseProductionContracts() {
        for (Distributor distributor : distributors) {
            /* choose energy producers */
            distributor.chooseProducers(producers);
        }
    }

    /**
     * perform the interaction process between customers and distributors
     */
    private void interactDistributorsWithCustomers() {
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

    /**
     * perform the interaction process between producers and distributors
     */
    private void interactDistributorsWithProducers() {
        for (Producer producer : producers) {
            /* notify each observer if producer is updated */
            producer.notifyObservers();
        }

        for (Distributor distributor : distributors) {
            /* perform the strategy again if it is necessary */
            if (distributor.hasUpdatedProducer()) {
                distributor.chooseProducers(producers);
            }
        }

        for (Producer producer : producers) {
            /* save distributors id for the current turn */
            producer.addCurrentDistributors();
        }
    }

    /**
     * perform update
     * @param monthlyUpdate given update
     */
    private void update(final MonthlyUpdateInputData monthlyUpdate) {
        List<ConsumerInputData> newConsumers = monthlyUpdate.getNewConsumers();
        List<DistributorChangesInputData> disChanges = monthlyUpdate.getDistributorChanges();
        List<ProducerChangesInputData> producersChanges = monthlyUpdate.getProducerChanges();

        /* update consumers list */
        for (ConsumerInputData newConsumer : newConsumers) {
            Consumer currentConsumer = new Consumer(newConsumer);
            consumers.add(currentConsumer);
        }

        /* update distributors attributes */
        for (DistributorChangesInputData distributorChanges : disChanges) {
            int id = distributorChanges.getId();
            Distributor currentDistributor = Distributor.findDistributor(distributors, id);
            if (currentDistributor != null) {
                int infrastructure = distributorChanges.getInfrastructureCost();
                currentDistributor.setInfrastructureCost(infrastructure);
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
     * compute first round of simulation
     */
    private void firstTurn() {
        chooseProductionContracts();
        interactDistributorsWithCustomers();
    }

    /**
     * simulate intermediate round
     * @param monthlyUpdate new consumers list and new distributors attributes
     */
    private void simulateTurn(final MonthlyUpdateInputData monthlyUpdate) {
        update(monthlyUpdate);
        interactDistributorsWithCustomers();
        interactDistributorsWithProducers();
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
