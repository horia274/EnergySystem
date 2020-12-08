package fileio.output;

import entities.Consumer;

public class ConsumerOutputData {
    private int id;
    private boolean isBankrupt;
    private int budget;

    public ConsumerOutputData(Consumer consumer) {
        id = consumer.getId();
        isBankrupt = consumer.isBankrupt();
        budget = consumer.getBudget();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public void setIsBankrupt(boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
