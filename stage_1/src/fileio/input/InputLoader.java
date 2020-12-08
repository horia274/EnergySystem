package fileio.input;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public final class InputLoader {
    /**
     * json path
     */
    private final String inputPath;

    public InputLoader(final String inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * read InputData Object from json file
     * @return InputData object
     */
    public InputData readData() {
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
