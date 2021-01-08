package fileio.output;

import entities.Producer;

import java.util.ArrayList;
import java.util.List;

public final class ProducerOutputData {
    private final int id;
    private final int maxDistributors;
    private final double priceKW;
    private final String energyType;
    private final int energyPerDistributor;
    private final List<MonthlyStatOutputData> monthlyStats;

    public ProducerOutputData(Producer producer) {
        id = producer.getId();
        maxDistributors = producer.getMaxDistributors();
        priceKW = producer.getPriceKW();
        energyType = producer.getEnergyType().getLabel();
        energyPerDistributor = producer.getEnergyPerDistributor();
        monthlyStats = new ArrayList<>();
        for (int i = 1; i < producer.getAllDistributors().size(); i++) {
            List<Integer> distributorsIds = producer.getAllDistributors().get(i);
            MonthlyStatOutputData monthlyStat = new MonthlyStatOutputData(i, distributorsIds);
            monthlyStats.add(monthlyStat);
        }
    }

    public int getId() {
        return id;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public String getEnergyType() {
        return energyType;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public List<MonthlyStatOutputData> getMonthlyStats() {
        return monthlyStats;
    }
}
