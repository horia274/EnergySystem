package fileio.input;

import java.util.List;

/**
 * contains initial data from input
 */
public final class InitialInputData {
    private List<ConsumerInputData> consumers;
    private List<DistributorInputData> distributors;
    private List<ProducerInputData> producers;

    public List<ConsumerInputData> getConsumers() {
        return consumers;
    }

    public void setConsumers(final List<ConsumerInputData> consumers) {
        this.consumers = consumers;
    }

    public List<DistributorInputData> getDistributors() {
        return distributors;
    }

    public void setDistributors(final List<DistributorInputData> distributors) {
        this.distributors = distributors;
    }

    public List<ProducerInputData> getProducers() {
        return producers;
    }

    public void setProducers(List<ProducerInputData> producers) {
        this.producers = producers;
    }
}
