package uk.agiletech.pickles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JsonDataGenerator implements DataGenerator<Map<String, Object>> {

    private Context context;
    private Map<String, FieldFormat> generatedFields;
    private Map<String, Object> dataObject;

    public JsonDataGenerator(Context context, Map<String,FieldFormat> generatedFields, String jsonString) throws JsonProcessingException {
        this(context, generatedFields, new ObjectMapper().readValue(jsonString, new TypeReference<Map<String, Object>>() {
        }));
    }

    public JsonDataGenerator(Context context, Map<String,FieldFormat> generatedFields, File jsonFile) throws IOException {
        this(context, generatedFields, new ObjectMapper().readValue(jsonFile, new TypeReference<Map<String, Object>>() {
        }));
    }

    public JsonDataGenerator(Context context, Map<String,FieldFormat> generatedFields, Map<String, Object> dataObject) {
        this.context = context;
        this.generatedFields = generatedFields;
        this.dataObject = dataObject;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public void next() {
        // todo
    }

    @Override
    public Map<String, Object> getCurrentValue() {
        return dataObject;
    }
}
