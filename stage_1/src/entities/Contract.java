package entities;

import java.util.Objects;

public class Contract {
    private Consumer consumer;
    private Distributor distributor;
    private int contractLength;
    private int remainedContractMonths;
    private int price;
    private boolean paymentStatus;

    public Contract(Consumer consumer, Distributor distributor, int contractLength, int price) {
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

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(int contractLength) {
        this.contractLength = contractLength;
    }

    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }

    public void setRemainedContractMonths(int remainedContractMonths) {
        this.remainedContractMonths = remainedContractMonths;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean wasPaid() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "consumerId=" + consumer.getId() +
                ", distributorId=" + distributor.getId() +
                ", contractLength=" + contractLength +
                ", remainedContractMonths=" + remainedContractMonths +
                ", price=" + price +
                ", paymentStatus=" + paymentStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return consumer.equals(contract.consumer) && distributor.equals(contract.distributor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(consumer, distributor);
    }

    public void decreaseContractMonths() {
        remainedContractMonths -= 1;
    }

    public boolean isValid() {
        return remainedContractMonths > 0;
    }

    public boolean isExpired(){
        return remainedContractMonths < 0;
    }
}
