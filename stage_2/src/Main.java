import entities.Consumer;
import entities.Distributor;
import entities.Producer;
import fileio.input.InputData;
import fileio.input.InputLoader;
import fileio.output.OutputData;
import fileio.output.OutputLoader;

import java.util.List;

public final class Main {

    private Main() { }

    /**
     *
     * @param args contains input and output paths
     */
    public static void main(final String[] args) {

        if (args.length != 2) {
            System.out.println("Proper Usage is: input path, output path");
            System.exit(-1);
        }

        String inputPath = args[0];
        String outputPath = args[1];

        InputLoader inputLoader = new InputLoader(inputPath);
        InputData inputData = inputLoader.readData();

        Simulation simulation = new Simulation(inputData);
        simulation.simulateAllTurns();

        List<Consumer> consumers = simulation.getConsumers();
        List<Distributor> distributors = simulation.getDistributors();
        List<Producer> producers = simulation.getProducers();

        OutputData outputData = new OutputData(consumers, distributors, producers);
        OutputLoader outputLoader = new OutputLoader(outputPath, outputData);
        outputLoader.writeData();
    }
}
