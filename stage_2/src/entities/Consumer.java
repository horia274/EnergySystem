package entities;

import constants.Const;
import entities.contracts.DistributionContract;
import fileio.input.ConsumerInputData;

import java.util.List;
import java.util.Objects;

public final class Consumer {
    private final int id;
    private final int monthlyIncome;
    private int budget;

    private DistributionContract distributionContract;
    private DistributionContract oldDistributionContract;

    private boolean hasOverduePayment;
    private boolean isBankrupt;

    public Consumer(final ConsumerInputData consumer) {
        id = consumer.getId();
        budget = consumer.getInitialBudget();
        monthlyIncome = consumer.getMonthlyIncome();
    }

    public int getId() {
        return id;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public int getBudget() {
        return budget;
    }

    public DistributionContract getContract() {
        return distributionContract;
    }

    public DistributionContract getOldContract() {
        return oldDistributionContract;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Consumer that = (Consumer) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void earnSalary() {
        budget += monthlyIncome;
    }

    public void chooseContract(final List<Distributor> distributors) {
        int minPrice = Integer.MAX_VALUE;
        Distributor chosenDistributor = null;

        /* check if current consumer is bankrupt or distributor list is empty */
        if (isBankrupt || distributors == null || distributors.size() == 0) {
            distributionContract = null;
            oldDistributionContract = null;
            return;
        }

        /* check if current consumer has already a valid contract */
        if (distributionContract != null && distributionContract.isValid() && !distributionContract.getDistributor().isBankrupt()) {
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
            distributionContract = new DistributionContract(this, chosenDistributor,
                    chosenDistributor.getContractLength(), chosenDistributor.getContractPrice());
            if (!hasOverduePayment && (oldDistributionContract == null || oldDistributionContract.wasPaid())) {
                oldDistributionContract = distributionContract;
            }
        }
    }

    /**
     * compute total payment, form current contract and from old one, if there is overdue payment
     * @return monthly payment
     */
    private int computePayment() {
        int oldPrice = oldDistributionContract.getPrice();
        int currentPrice = distributionContract.getPrice();

        if (oldDistributionContract.wasPaid()) {
            return distributionContract.getPrice();
        }
        return (int) Math.round(Math.floor(Const.OVERDUE * oldPrice) + currentPrice);
    }

    public void payBill() {
        /* keep the status payment of old contract */
        hasOverduePayment = !oldDistributionContract.wasPaid();

        /* compare budget with monthly payment to check the situation */
        if (budget >= computePayment()) {
            budget -= computePayment();
            distributionContract.setPaymentStatus(true);
        } else if (!hasOverduePayment) {
            distributionContract.setPaymentStatus(false);
        } else {
            if (budget >= computePayment()) {
                budget -= computePayment();
                distributionContract.setPaymentStatus(true);
            } else {
                isBankrupt = true;
                distributionContract = null;
                oldDistributionContract = null;
            }
        }
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public boolean hasOverduePayment() {
        return hasOverduePayment;
    }
}
