package uk.agiletech.pickles.format;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonTest {
    @Test
    public void test() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        String json = "{\"color\":\"Black\",\"type\":\"FIAT\"}";
        JsonNode jsonNode = objectMapper.readTree(json);
        String color = jsonNode.get("color").asText();
        assertEquals("Black",color);

        Object data = objectMapper.readValue(json, new TypeReference<>() {
        });
        objectMapper.writer().writeValue(bos,data);
        assertEquals(json, bos.toString());
    }
}
