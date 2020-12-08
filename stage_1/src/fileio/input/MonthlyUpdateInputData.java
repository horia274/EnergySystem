package fileio.input;

import java.util.List;

public class MonthlyUpdateInputData {
    private List<ConsumerInputData> newConsumers;
    private List<CostChangesInputData> costsChanges;

    public List<ConsumerInputData> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(List<ConsumerInputData> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public List<CostChangesInputData> getCostsChanges() {
        return costsChanges;
    }

    public void setCostsChanges(List<CostChangesInputData> costsChanges) {
        this.costsChanges = costsChanges;
    }
}
