package fileio.output;

import entities.Consumer;
import entities.Distributor;

import java.util.ArrayList;
import java.util.List;

/**
 * contains output information
 */
public final class OutputData {
    private final List<ConsumerOutputData> consumers;
    private final List<DistributorOutputData> distributors;

    public OutputData(final List<Consumer> consumers, final List<Distributor> distributors) {
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

    public List<DistributorOutputData> getDistributors() {
        return distributors;
    }
}
