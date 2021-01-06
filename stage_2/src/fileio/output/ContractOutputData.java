package fileio.output;

import entities.contracts.DistributionContract;

/**
 * contains contract information for output
 */
public final class ContractOutputData {
    private final int consumerId;
    private final int price;
    private final int remainedContractMonths;

    public ContractOutputData(final DistributionContract distributionContract) {
        this.consumerId = distributionContract.getConsumer().getId();
        this.price = distributionContract.getPrice();
        this.remainedContractMonths = distributionContract.getRemainedContractMonths();
    }

    public int getConsumerId() {
        return consumerId;
    }

    public int getPrice() {
        return price;
    }

    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }
}
