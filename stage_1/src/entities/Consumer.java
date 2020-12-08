package entities;

import java.util.List;

public interface Consumer {
    void earnSalary();

    void chooseContract(List<Distributor> distributorList);

    Contract getContract();

    Contract getOldContract();

    void payBill();

    boolean isBankrupt();

    boolean hasOverduePayment();

    int getBudget();

    String toString();

    boolean equals(Object o);

    int getId();
}
