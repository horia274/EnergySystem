package entities;

import java.util.List;

public interface Consumer {
    void earnSalary();

    Contract getContract();

    Contract getOldContract();

    boolean isBankrupt();

    boolean hasOverduePayment();

    int getBudget();

    String toString();

    boolean equals(Object o);

    int getId();
}
