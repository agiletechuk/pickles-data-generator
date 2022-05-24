package uk.agiletech.pickles.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.agiletech.pickles.PicklesException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * DataFormat - provides a json record with any number of the fields consisting of generated values
 *
 * @param <T> - The type. Most of the time this will be Object
 */
public class DataFormat<T> implements Format<T> {

    private final Map<String, Format<?>> generatedFields;
    private final T dataObject;

    public DataFormat(Map<String, Format<?>> generatedFields, String jsonString) throws JsonProcessingException {
        this(generatedFields, new ObjectMapper().readValue(jsonString, new TypeReference<T>() {
        }));
    }

    public DataFormat(Map<String, Format<?>> generatedFields, File jsonFile) throws IOException {
        this(generatedFields, new ObjectMapper().readValue(jsonFile, new TypeReference<T>() {
        }));
    }

    public DataFormat(Map<String, Format<?>> generatedFields, T dataObject) {
        this.generatedFields = generatedFields;
        this.dataObject = dataObject;
    }

    private void applyGeneraatedFields() {
        for (Map.Entry<String, Format<?>> entry : generatedFields.entrySet()) {
            applyGeneraatedField(dataObject, entry.getKey(), entry.getValue(), 0);
        }
    }

    private void applyGeneraatedField(Object dataObject, String key, Format<?> format, int pos) {
        String[] fields = key.split("\\.");
        if (fields.length == 0) {
            throw new RuntimeException("key has no fields");
        } else {
            String[] subFields = fields[0].split("\\[");
            if (subFields[0].length() > 0) {
                if (dataObject instanceof Map map) {
                    // subfield[0] is a field in a map
                    processMap(map, key, format, pos, fields, subFields);
                } else {
                    throw new PicklesException("Map expected but data object is not a Map");
                    // TODO add more detail to exception what where why
                }
            } else {
                // subfield[1] contains an index to get from a list'
                if (dataObject instanceof List list) {
                    processList(list, key, format, pos, fields, subFields);
                } else {
                    throw new PicklesException("List expected but data object is not a list");
                    // TODO add more detail to exception what where why
                }
            }
        }
    }

    private void processList(List<Object> dataObject, String key, Format<?> format, int pos, String[] fields, String[] subFields) {
        int endIndex = subFields[1].indexOf(']');
        if (endIndex == -1) {
            throw new RuntimeException("missing ']' at position " + pos + subFields[0].length());
        } else {
            String arrayIndexString = subFields[1].substring(0, endIndex);
            int arrayIndex = Integer.parseInt(arrayIndexString);

            if (fields.length > 1 || subFields.length > 2) {
                // apply in subobject
                applyGeneraatedField(
                        dataObject.get(arrayIndex),
                        key.substring(subFields[1].length() + 1),
                        format,
                        pos + subFields[1].length() + 1
                );
            } else {
                addFormatToList(dataObject, arrayIndex, format);
            }
        }
    }

    private void processMap(Map<String, Object> dataObject, String key, Format<?> format, int pos, String[] fields, String[] subFields) {
        if (fields.length > 1 || subFields.length > 1) {
            // apply in subobject
            applyGeneraatedField(
                    dataObject.get(subFields[0]),
                    key.substring(subFields[0].length() +
                            (subFields.length == 1 ? 1 : 0)),
                    format,
                    pos + subFields[0].length() +
                            (subFields.length == 1 ? 1 : 0)
            );
        } else {
            // replace the field value with a format
            addFormatToMap(dataObject, subFields[0], format);
        }
    }

    private void addFormatToMap(Map<String, Object> dataObject, String field, Format<?> format) {
        dataObject.put(field, format.getValue());
    }

    private void addFormatToList(List<Object> dataObject, int arrayIndex, Format<?> format) {
        dataObject.set(arrayIndex, format.getValue());
    }

    @Override
    public T getValue() {
        applyGeneraatedFields();
        return dataObject;
    }
}
