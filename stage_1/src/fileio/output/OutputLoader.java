package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import fileio.input.InputData;

import java.io.File;
import java.io.IOException;

public class OutputLoader {
    private final String outputPath;
    private OutputData outputData;

    public OutputLoader(String outputPath, OutputData outputData) {
        this.outputPath = outputPath;
        this.outputData = outputData;
    }

    public void writeData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputPath), outputData);
        } catch (IOException e) {
            System.out.println("Could not create output file");
            System.exit(-1);
        }
    }
}
