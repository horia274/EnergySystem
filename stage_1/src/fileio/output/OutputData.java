package fileio.output;

import entities.Consumer;
import entities.Distributor;

import java.util.ArrayList;
import java.util.List;

public class OutputData {
    private List<ConsumerOutputData> consumers;
    private List<DistributorOutputData> distributors;

    public OutputData(List<Consumer> consumers, List<Distributor> distributors) {
        this.consumers = new ArrayList<>();
        for (Consumer consumer : consumers) {
            this.consumers.add(new ConsumerOutputData(consumer));
        }

        this.distributors = new ArrayList<>();
        for (Distributor distributor : distributors) {
            this.distributors.add(new DistributorOutputData(distributor));
        }
    }

    public List<ConsumerOutputData> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<ConsumerOutputData> consumers) {
        this.consumers = consumers;
    }

    public List<DistributorOutputData> getDistributors() {
        return distributors;
    }

    public void setDistributors(List<DistributorOutputData> distributors) {
        this.distributors = distributors;
    }
}
