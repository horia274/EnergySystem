package fileio.output;

import entities.Contract;

/**
 * contains contract information for output
 */
public final class ContractOutput {
    private final int consumerId;
    private final int price;
    private final int remainedContractMonths;

    public ContractOutput(final Contract contract) {
        this.consumerId = contract.getConsumer().getId();
        this.price = contract.getPrice();
        this.remainedContractMonths = contract.getRemainedContractMonths();
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
