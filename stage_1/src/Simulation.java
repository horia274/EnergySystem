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

    public void simulateAllTurns() {
    }
}
