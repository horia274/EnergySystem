package entities;

import constants.Const;
import fileio.input.ConsumerInputData;

import java.util.List;
import java.util.Objects;

public final class ConcreteConsumer implements Consumer {
    private final int id;
    private final int monthlyIncome;
    private int budget;

    private Contract contract;
    private Contract oldContract;

    private boolean hasOverduePayment;
    private boolean isBankrupt;

    public ConcreteConsumer(final ConsumerInputData consumer) {
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

    @Override
    public int getBudget() {
        return budget;
    }

    @Override
    public Contract getContract() {
        return contract;
    }

    @Override
    public Contract getOldContract() {
        return oldContract;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConcreteConsumer that = (ConcreteConsumer) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public void earnSalary() {
        budget += monthlyIncome;
    }

    @Override
    public void chooseContract(final List<Distributor> distributors) {
        int minPrice = Integer.MAX_VALUE;
        Distributor chosenDistributor = null;

        /* check if current consumer is bankrupt or distributor list is empty */
        if (isBankrupt || distributors == null || distributors.size() == 0) {
            contract = null;
            oldContract = null;
            return;
        }

        /* check if current consumer has already a valid contract */
        if (contract != null && contract.isValid() && !contract.getDistributor().isBankrupt()) {
            return;
        }

        /* if not, choose the cheapest contract */
        for (Distributor distributor : distributors) {
            if (!distributor.isBankrupt() && minPrice > distributor.getContractPrice()) {
                minPrice = distributor.getContractPrice();
                chosenDistributor = distributor;
            }
        }

        /* if any distributor is found, update contract */
        /* also update old contract, if there is overdue payment */
        if (chosenDistributor != null) {
            contract = new Contract(this, chosenDistributor,
                    chosenDistributor.getContractLength(), chosenDistributor.getContractPrice());
            if (!hasOverduePayment && (oldContract == null || oldContract.wasPaid())) {
                oldContract = contract;
            }
        }
    }

    /**
     * compute total payment, form current contract and from old one, if there is overdue payment
     * @return monthly payment
     */
    private int computePayment() {
        int oldPrice = oldContract.getPrice();
        int currentPrice = contract.getPrice();

        if (oldContract.wasPaid()) {
            return contract.getPrice();
        }
        return (int) Math.round(Math.floor(Const.OVERDUE * oldPrice) + currentPrice);
    }

    @Override
    public void payBill() {
        /* keep the status payment of old contract */
        hasOverduePayment = !oldContract.wasPaid();

        /* compare budget with monthly payment to check the situation */
        if (budget >= computePayment()) {
            budget -= computePayment();
            contract.setPaymentStatus(true);
        } else if (!hasOverduePayment) {
            contract.setPaymentStatus(false);
        } else {
            if (budget >= computePayment()) {
                budget -= computePayment();
                contract.setPaymentStatus(true);
            } else {
                isBankrupt = true;
                contract = null;
                oldContract = null;
            }
        }
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
