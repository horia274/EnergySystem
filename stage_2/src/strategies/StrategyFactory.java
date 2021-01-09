package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.List;

public final class StrategyFactory {
    private static StrategyFactory instance;

    private StrategyFactory() { }

    /**
     * Get the instance of the class using Singleton design pattern
     * @return factory instance
     */
    public static StrategyFactory getInstance() {
        if (instance == null) {
            instance = new StrategyFactory();
        }
        return instance;
    }

    /**
     * Create a new Strategy object, using Factory design pattern
     *
     * @param strategyType the type of the strategy
     * @param distributor current distributor who will perform that strategy
     * @param producers list of all producers
     * @return Strategy object
     */
    public Strategy createStrategy(EnergyChoiceStrategyType strategyType,
                                   Distributor distributor, List<Producer> producers) {
        return switch (strategyType) {
            case GREEN -> new GreenStrategy(distributor, producers);
            case PRICE -> new PriceStrategy(distributor, producers);
            case QUANTITY -> new QuantityStrategy(distributor, producers);
        };
    }
}
