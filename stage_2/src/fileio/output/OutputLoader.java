package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;

public final class OutputLoader {
    /**
     * json path
     */
    private final String outputPath;
    /**
     * object written in json file
     */
    private final OutputData outputData;

    public OutputLoader(final String outputPath, final OutputData outputData) {
        this.outputPath = outputPath;
        this.outputData = outputData;
    }

    /**
     * write OutputData object in json file
     */
    public void writeData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
            objectWriter.writeValue(new File(outputPath), outputData);
        } catch (IOException e) {
            System.out.println("Could not create output file");
            System.exit(-1);
        }
    }
}
