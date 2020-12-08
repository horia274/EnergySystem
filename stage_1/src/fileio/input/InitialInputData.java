package fileio.input;

import java.util.List;

public class InitialInputData {
    private List<ConsumerInputData> consumers;
    private List<DistributorInputData> distributors;

    public List<ConsumerInputData> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<ConsumerInputData> consumers) {
        this.consumers = consumers;
    }

    public List<DistributorInputData> getDistributors() {
        return distributors;
    }

    public void setDistributors(List<DistributorInputData> distributors) {
        this.distributors = distributors;
    }
}
