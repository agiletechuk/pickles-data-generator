package uk.agiletech.pickles.format;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class JsonFormat implements Format<String> {

    final private static ObjectMapper objectMapper = new ObjectMapper();
    private final Format<?> source;

    public JsonFormat(Format<?> source) {
        this.source = source;
    }

    @Override
    public String getValue() {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            objectMapper.writer().writeValue(bos, source.getValue());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bos.toString();
    }
}
