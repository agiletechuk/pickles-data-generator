package uk.agiletech.pickles.format;

import org.junit.jupiter.api.Test;
import uk.agiletech.pickles.data.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.agiletech.pickles.TestHelper.getFile;

public class DataFormatTest {
    private static final String JSON_FILE = "test.json";

    DataFormat<Map<String,?>> dataFormatObject;
    DataFormat<List<?>> dataFormatList;

    @Test
    public void testString() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(getFile(JSON_FILE).getPath())));
        dataFormatObject = new DataFormat<>(emptyMap(), json);
        test();
    }

    @Test
    public void testFile() throws IOException {
        dataFormatObject = new DataFormat<>(emptyMap(), getFile(JSON_FILE));
        test();
    }

    @Test
    public void testJsonList() throws IOException {
        String jsonList = "[{\"name\":\"bob\"},{\"name\":\"jim\"},{\"name\":\"john\"}]";
        dataFormatList = new DataFormat<>(emptyMap(), jsonList);
        testList();
    }

    @Test
    public void split() {
        String[] result = "field[123]".split("\\[");
        assertThat(result, arrayContaining("field", "123]"));
        result = "[123]".split("\\[");
        assertThat(result, arrayContaining("", "123]"));
        result = "field".split("\\[");
        assertThat(result, arrayContaining("field"));
    }

    @Test
    public void withGenerator() throws IOException {
        IntegerData intdata = new IntegerData(1, 4, 1);
        UUIDData uuidData = new UUIDData();
        Map<String, Format<?>> fieldMap = new HashMap<>();
        fieldMap.put("intField", intdata);
        fieldMap.put("objectField.f1", intdata);
        fieldMap.put("newField",uuidData);
        dataFormatObject = new DataFormat<>(fieldMap, getFile(JSON_FILE));
        testGenerator(intdata);
    }

    @Test
    void testPerformance() throws IOException {
        IntegerData intdata = new IntegerData(1, 2, 1, LimitBehavior.NULL);
        UUIDData uuidData = new UUIDData();
        GeneratorGroup generatorGroup = new GeneratorGroup(intdata, uuidData);
        Map<String, Format<?>> fieldMap = new HashMap<>();
        fieldMap.put("intField", intdata);
        fieldMap.put("objectField.f1", intdata);
        fieldMap.put("list[0]", intdata);
        fieldMap.put("list[4]", intdata);
        fieldMap.put("newField",uuidData);
        dataFormatObject = new DataFormat<>(fieldMap, getFile(JSON_FILE));
        JsonFormat jsonFormat = new JsonFormat(dataFormatObject);

        long count = 0;
        long start = System.currentTimeMillis();
        while (count <5 || System.currentTimeMillis() - start < 1000) {
            String vals = jsonFormat.getValue();
            if (count < 5) System.out.println("Item: " + vals);
            generatorGroup.next();
            count++;
        }
        System.out.printf("DataFormat performance %,d per second%n",count);
        assertThat(count,is(greaterThan(500000L)));
    }


    private void testList() {
        List<Map<String,Object>> data = (List<Map<String,Object>>) dataFormatList.getValue();
        assertThat(data.get(0).get("name"), equalTo("bob"));
        assertThat(data.get(1).get("name"), equalTo("jim"));
        assertThat(data.get(2).get("name"), equalTo("john"));
    }

    public void test(Integer val1, Integer val2) {
        Map<String, Object> data = (Map<String, Object>) dataFormatObject.getValue();
        assertEquals("stringVal", data.get("stringField"));
        assertEquals(val1, data.get("intField"));
        assertEquals(123.456789, data.get("decimalField"));
        assertArrayEquals(new Object[]{5, 4, 3, 2, 1}, ((List<?>) data.get("list")).toArray());
        Map<String, Object> subObject = (Map<String, Object>) data.get("objectField");
        assertEquals(val2, subObject.get("f1"));
        assertEquals(false, subObject.get("f2"));
    }

    void test() {
        test(42, 32);
    }

    private void testGenerator(Generator generator) {
        test(1, 1);
        assertThat(dataFormatObject.getValue().get("newField"),notNullValue());
        generator.next();
        test(2, 2);
        generator.next();
        test(3, 3);
        generator.next();
        test(4, 4);
        assertThat(generator.endSequence(), is(false));
        generator.next();
        test(null, null);
        assertThat(generator.endSequence(), is(true));
    }

}