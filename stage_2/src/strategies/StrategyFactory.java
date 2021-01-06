package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.List;

public class StrategyFactory {
    private static StrategyFactory instance;

    private StrategyFactory() {}

    public static StrategyFactory getInstance() {
        if (instance == null) {
            instance = new StrategyFactory();
        }
        return instance;
    }

    public Strategy createStrategy(EnergyChoiceStrategyType strategyType, Distributor distributor, List<Producer> producers) {
        return switch (strategyType) {
            case GREEN -> new GreenStrategy(distributor, producers);
            case PRICE -> new PriceStrategy(distributor, producers);
            case QUANTITY -> new QuantityStrategy(distributor, producers);
        };
    }
}
