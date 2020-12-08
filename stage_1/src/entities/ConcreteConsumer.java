package entities;

import fileio.input.ConsumerInputData;

import java.util.List;
import java.util.Objects;

public class ConcreteConsumer implements Consumer {
    private int id;
    private int monthlyIncome;
    private int budget;

    private Contract contract;
    private Contract oldContract;

    private boolean hasOverduePayment;
    private boolean isBankrupt;

    public ConcreteConsumer(ConsumerInputData consumer) {
        id = consumer.getId();
        budget = consumer.getInitialBudget();
        monthlyIncome = consumer.getMonthlyIncome();
    }

    @Override
    public int getId() {
        return id;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    @Override
    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    @Override
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public void setBankrupt(boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    @Override
    public Contract getOldContract() {
        return oldContract;
    }

    public void setOldContract(Contract oldContract) {
        this.oldContract = oldContract;
    }

    public boolean HasOverduePayment() {
        return hasOverduePayment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConcreteConsumer that = (ConcreteConsumer) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ConcreteConsumer{" +
                "id=" + id +
                ", monthlyIncome=" + monthlyIncome +
                ", budget=" + budget +
                ", contract=" + contract +
                ", oldContract=" + oldContract +
                ", hasOverduePayment=" + hasOverduePayment +
                ", isBankrupt=" + isBankrupt +
                '}';
    }

    @Override
    public void earnSalary() {
        budget += monthlyIncome;
    }

    @Override
    public boolean isBankrupt() {
        return isBankrupt;
    }

    @Override
    public boolean hasOverduePayment() {
        return hasOverduePayment;
    }
}
