package fileio.output;

import entities.Consumer;

/**
 * contains consumer information for output
 */
public final class ConsumerOutputData {
    private final int id;
    private final boolean isBankrupt;
    private final int budget;

    public ConsumerOutputData(final Consumer consumer) {
        id = consumer.getId();
        isBankrupt = consumer.isBankrupt();
        budget = consumer.getBudget();
    }

    public int getId() {
        return id;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public int getBudget() {
        return budget;
    }
}
