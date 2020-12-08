import fileio.input.InputData;
import fileio.input.InputLoader;
import fileio.output.OutputData;
import fileio.output.OutputLoader;

public class Main {

    public static void main(String[] args) throws Exception {
        String inputPath = args[0];
        String outputPath = args[1];

        InputLoader inputLoader = new InputLoader(inputPath);
        InputData inputData = inputLoader.readData();

        Simulation simulation = new Simulation(inputData);
        simulation.simulateAllTurns();

        OutputData outputData = new OutputData(simulation.getConsumers(), simulation.getDistributors());
        OutputLoader outputLoader = new OutputLoader(outputPath, outputData);
        outputLoader.writeData();
    }
}
