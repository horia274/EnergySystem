package entities.contracts;

import entities.Consumer;
import entities.Distributor;

import java.util.Objects;

public final class DistributionContract {
    private final Consumer consumer;
    private final Distributor distributor;
    private final int contractLength;
    private final int price;

    private int remainedContractMonths;
    private boolean paymentStatus;

    public DistributionContract(final Consumer consumer,
                                final Distributor distributor,
                                final int contractLength,
                                final int price) {
        this.consumer = consumer;
        this.distributor = distributor;
        this.contractLength = contractLength;
        this.remainedContractMonths = contractLength;
        this.price = price;
        paymentStatus = true;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public int getContractLength() {
        return contractLength;
    }

    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }

    public int getPrice() {
        return price;
    }

    /**
     * check if contract was paid by consumer
     * @return Boolean object
     */
    public boolean wasPaid() {
        return paymentStatus;
    }

    /**
     * set contract payment status, if consumer pays, than true, else false
     * @param paymentStatus new status
     */
    public void setPaymentStatus(final boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DistributionContract distributionContract = (DistributionContract) o;
        return consumer.equals(distributionContract.consumer)
                && distributor.equals(distributionContract.distributor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(consumer, distributor);
    }

    /**
     * decrease contract duration
     */
    public void decreaseContractMonths() {
        remainedContractMonths -= 1;
    }

    /**
     * check if is still valid
     * @return Boolean object
     */
    public boolean isValid() {
        return remainedContractMonths > 0;
    }

    /**
     * check if is expired
     * @return Boolean object
     */
    public boolean isExpired() {
        return remainedContractMonths < 0;
    }
}
