package uk.agiletech.pickles;

import java.io.File;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestHelper {

    public static File getFile(String fileName) {
        requireNonNull(fileName);
        var classLoader = TestHelper.class.getClassLoader();
        var file = new File(requireNonNull(classLoader.getResource(fileName)).getFile());
        assertTrue(file.exists());
        return file;
    }
}
