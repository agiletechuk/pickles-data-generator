package uk.agiletech.pickles;

import org.junit.jupiter.api.Test;
import uk.agiletech.pickles.format.Format;
import uk.agiletech.pickles.format.JsonFormat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonDataGeneratorTest extends TestBase {
    private static final String JSON_FILE = "test.json";
    private static final Map<String, Format<?>> FIELDMAP = Collections.emptyMap();

    JsonFormat jsonDataGenerator;

    @Test
    public void testString() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(getFile(JSON_FILE).getPath())));
        jsonDataGenerator = new JsonFormat(FIELDMAP, json);
        test();
    }

    @Test
    public void testFile() throws IOException {
        jsonDataGenerator = new JsonFormat(FIELDMAP, getFile(JSON_FILE));
        test();
    }

    public void test() {
        Map<String, Object> data = jsonDataGenerator.getValue();
        assertEquals("stringVal", data.get("stringField"));
        assertEquals(42, data.get("intField"));
        assertEquals(123.456789, data.get("decimalField"));
        assertArrayEquals(new Object[]{5, 4, 3, 2, 1}, ((List) data.get("list")).toArray());
        Map<String, Object> subObject = (Map) data.get("objectField");
        assertEquals(32, subObject.get("f1"));
        assertEquals(false, subObject.get("f2"));
    }

}