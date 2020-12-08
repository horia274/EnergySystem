package entities.factory;

import entities.ConcreteConsumer;
import entities.Consumer;
import fileio.input.ConsumerInputData;

public class ConsumerFactory {
    public static ConsumerFactory consumerFactoryInstance;
    public enum ConsumerType {
        Concrete
    }

    private ConsumerFactory() {}

    public static ConsumerFactory getInstance() {
        if (consumerFactoryInstance == null) {
            consumerFactoryInstance = new ConsumerFactory();
        }
        return consumerFactoryInstance;
    }

    public Consumer createConsumer(ConsumerType consumerType, ConsumerInputData consumer) {
        switch (consumerType) {
            case Concrete: return new ConcreteConsumer(consumer);
        }
        throw new IllegalArgumentException("The consumer type " + consumerType + " is not recognized");
    }
}
