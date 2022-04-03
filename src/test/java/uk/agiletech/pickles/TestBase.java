package uk.agiletech.pickles;

import java.io.File;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBase {

    public File getFile(String fileName) {
        requireNonNull(fileName);
        var classLoader = this.getClass().getClassLoader();
        var file = new File(requireNonNull(classLoader.getResource("test.json")).getFile());
        assertTrue(file.exists());
        return file;
    }
}
