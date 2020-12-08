package fileio.input;

import java.util.List;

/**
 * contains input information
 */
public final class InputData {
    private int numberOfTurns;
    private InitialInputData initialData;
    private List<MonthlyUpdateInputData> monthlyUpdates;

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(final int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public InitialInputData getInitialData() {
        return initialData;
    }

    public void setInitialData(final InitialInputData initialData) {
        this.initialData = initialData;
    }

    public List<MonthlyUpdateInputData> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public void setMonthlyUpdates(final List<MonthlyUpdateInputData> monthlyUpdates) {
        this.monthlyUpdates = monthlyUpdates;
    }
}
