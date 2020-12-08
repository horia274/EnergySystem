package fileio.input;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class InputLoader {
    private final String inputPath;

    public InputLoader(String inputPath) {
        this.inputPath = inputPath;
    }

    public InputData readData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(inputPath), InputData.class);
        } catch (IOException e) {
            System.out.println("Could not find input file");
            System.exit(-1);
        }
        return null;
    }
}
