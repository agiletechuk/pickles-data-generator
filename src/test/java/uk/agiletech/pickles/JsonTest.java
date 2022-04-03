package uk.agiletech.pickles;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonTest {
    @Test
    public void test() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        String json = "{ \"color\" : \"Black\", \"type\" : \"FIAT\" }";
        JsonNode jsonNode = objectMapper.readTree(json);
        objectMapper.readValue(json, new TypeReference<Map<String,Object>>(){});
        String color = jsonNode.get("color").asText();
        assertTrue(color.equals("Black"));
    }
}
