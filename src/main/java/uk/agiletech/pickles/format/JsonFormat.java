package uk.agiletech.pickles.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.agiletech.pickles.format.Format;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JsonFormat implements Format<Map<String, Object>> {

    private Map<String, Format> generatedFields;
    private Map<String, Object> dataObject;

    public JsonFormat(Map<String, Format> generatedFields, String jsonString) throws JsonProcessingException {
        this(generatedFields, new ObjectMapper().readValue(jsonString, new TypeReference<Map<String, Object>>() {
        }));
    }

    public JsonFormat(Map<String, Format> generatedFields, File jsonFile) throws IOException {
        this(generatedFields, new ObjectMapper().readValue(jsonFile, new TypeReference<Map<String, Object>>() {
        }));
    }

    public JsonFormat(Map<String, Format> generatedFields, Map<String, Object> dataObject) {
        this.generatedFields = generatedFields;
        this.dataObject = dataObject;
    }

    @Override
    public Map<String, Object> getValue() {
        return dataObject;
    }
}
