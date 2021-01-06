package entities.contracts;

import entities.Distributor;
import entities.Producer;

import java.util.Objects;

public class ProductionContract {
    private Producer producer;
    private Distributor distributor;

    private double priceKW;
    private int distributedEnergy;
    private double price;

    public ProductionContract(Producer producer, Distributor distributor, double priceKW, int distributedEnergy) {
        this.producer = producer;
        this.distributor = distributor;
        this.priceKW = priceKW;
        this.distributedEnergy = distributedEnergy;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(double priceKW) {
        this.priceKW = priceKW;
    }

    public int getDistributedEnergy() {
        return distributedEnergy;
    }

    public void setDistributedEnergy(int distributedEnergy) {
        this.distributedEnergy = distributedEnergy;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductionContract that = (ProductionContract) o;
        return producer.equals(that.producer) && distributor.equals(that.distributor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producer, distributor);
    }

    public void computeCost() {
        if (price == 0) {
            price = priceKW * distributedEnergy;
        }
    }
}
