package fileio.input;

import java.util.List;

public class InputData {
    private int numberOfTurns;
    private InitialInputData initialData;
    private List<MonthlyUpdateInputData> monthlyUpdates;

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public InitialInputData getInitialData() {
        return initialData;
    }

    public void setInitialData(InitialInputData initialData) {
        this.initialData = initialData;
    }

    public List<MonthlyUpdateInputData> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public void setMonthlyUpdates(List<MonthlyUpdateInputData> monthlyUpdates) {
        this.monthlyUpdates = monthlyUpdates;
    }
}
