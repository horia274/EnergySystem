package fileio.input;

import java.util.List;

/**
 * contains monthly update information from input
 */
public final class MonthlyUpdateInputData {
    private List<ConsumerInputData> newConsumers;
    private List<CostChangesInputData> costsChanges;

    public List<ConsumerInputData> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(final List<ConsumerInputData> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public List<CostChangesInputData> getCostsChanges() {
        return costsChanges;
    }

    public void setCostsChanges(final List<CostChangesInputData> costsChanges) {
        this.costsChanges = costsChanges;
    }
}
