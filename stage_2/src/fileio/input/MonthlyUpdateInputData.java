package fileio.input;

import java.util.List;

/**
 * contains monthly update information from input
 */
public final class MonthlyUpdateInputData {
    private List<ConsumerInputData> newConsumers;
    private List<DistributorChangesInputData> distributorChanges;
    private List<ProducerChangesInputData> producerChanges;

    public List<ConsumerInputData> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(final List<ConsumerInputData> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public List<DistributorChangesInputData> getDistributorChanges() {
        return distributorChanges;
    }

    public void setDistributorChanges(final List<DistributorChangesInputData> distributorChanges) {
        this.distributorChanges = distributorChanges;
    }

    public List<ProducerChangesInputData> getProducerChanges() {
        return producerChanges;
    }

    public void setProducerChanges(List<ProducerChangesInputData> producerChanges) {
        this.producerChanges = producerChanges;
    }
}
