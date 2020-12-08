package entities.factory;

import entities.ConcreteConsumer;
import entities.Consumer;
import fileio.input.ConsumerInputData;

public final class ConsumerFactory {
    private static ConsumerFactory consumerFactoryInstance;
    public enum ConsumerType {
        Concrete
    }

    private ConsumerFactory() { }

    /**
     * make singleton instance for consumer factory
     * @return instance
     */
    public static ConsumerFactory getInstance() {
        if (consumerFactoryInstance == null) {
            consumerFactoryInstance = new ConsumerFactory();
        }
        return consumerFactoryInstance;
    }

    /**
     *
     * @param consumerType type of consumer, now it is just one type
     * @param consumer ConsumerInputData object who forms the Consumer object
     * @return general Consumer type
     */
    public Consumer createConsumer(final ConsumerType consumerType,
                                   final ConsumerInputData consumer) {
        if (consumerType == ConsumerType.Concrete) {
            return new ConcreteConsumer(consumer);
        }
        throw new IllegalArgumentException("Consumer type " + consumerType + " is not recognized");
    }
}
