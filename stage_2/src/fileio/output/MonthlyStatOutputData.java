package fileio.output;

import java.util.List;

public final class MonthlyStatOutputData {
    private final int month;
    private final List<Integer> distributorsIds;

    public MonthlyStatOutputData(int month, List<Integer> distributorsIds) {
        this.month = month;
        this.distributorsIds = distributorsIds;
    }
}
