package entities;

import java.util.List;

public interface Consumer {
    /**
     * get consumer id
     * @return consumerId
     */
    int getId();

    /**
     * get consumer budget
     * @return consumerBudget
     */
    int getBudget();

    /**
     * check if is bankrupt
     * @return Boolean object
     */
    boolean isBankrupt();

    /**
     * check if consumer has overdue payment
     * @return Boolean object
     */
    boolean hasOverduePayment();

    /**
     * add monthly income to budget
     */
    void earnSalary();

    /**
     * choose contract with a distributor from a list of distributors
     * @param distributorList list of distributors
     */
    void chooseContract(List<Distributor> distributorList);

    /**
     * get actual contract
     * @return Contract object, actual contract
     */
    Contract getContract();

    /**
     * get old contract
     * @return Contract object, old contract
     */
    Contract getOldContract();

    /**
     * pay price of contract
     */
    void payBill();

    /**
     * compare current consumer with another one, by id
     * @param o another object to compare
     * @return Boolean object
     */
    boolean equals(Object o);

    /**
     * override method with id
     * @return new hashcode
     */
    int hashCode();
}
