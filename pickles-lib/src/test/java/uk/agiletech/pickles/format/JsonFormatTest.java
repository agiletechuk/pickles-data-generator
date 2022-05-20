package uk.agiletech.pickles.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import uk.agiletech.pickles.data.IntegerData;
import uk.agiletech.pickles.data.UUIDData;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


class JsonFormatTest {

    @Test
    public void integerSource() {
        IntegerData data = new IntegerData(0, 3, 1);
        JsonFormat jsonFormat = new JsonFormat(data);
        assertThat(jsonFormat.getValue(), equalTo("0"));
        data.next();
        assertThat(jsonFormat.getValue(), equalTo("1"));
        data.next();
        assertThat(jsonFormat.getValue(), equalTo("2"));
    }

    @Test
    public void stringSource() {
        IntegerData data = new IntegerData(0, 3, 1);
        StringFormat stringFormat = new StringFormat("%d", List.of(data));
        JsonFormat jsonFormat = new JsonFormat(stringFormat);
        assertThat(jsonFormat.getValue(), equalTo("\"0\""));
        data.next();
        assertThat(jsonFormat.getValue(), equalTo("\"1\""));
        data.next();
        assertThat(jsonFormat.getValue(), equalTo("\"2\""));
    }

    @Test
    public void objectSource() throws JsonProcessingException {
        String source = "{\"a1\":23,\"a2\":\"stringval\",\"a3\":\"some val\"}";
        IntegerData intdata = new IntegerData(1, 4, 1);
        UUIDData uuidData = new UUIDData();
        HashMap<String, Format<?>> fieldMap = new HashMap<>();
        fieldMap.put("a1", intdata);
        fieldMap.put("a2", uuidData);
        DataFormat<Object> dataFormatObject = new DataFormat<>(fieldMap, source);
        JsonFormat json = new JsonFormat(dataFormatObject);
        assertThat(json.getValue(), equalTo("{\"a1\":1,\"a2\":\"" + uuidData.getValue() + "\",\"a3\":\"some val\"}"));
        intdata.next();
        uuidData.next();
        assertThat(json.getValue(), equalTo("{\"a1\":2,\"a2\":\"" + uuidData.getValue() + "\",\"a3\":\"some val\"}"));
        intdata.next();
        uuidData.next();
        assertThat(json.getValue(), equalTo("{\"a1\":3,\"a2\":\"" + uuidData.getValue() + "\",\"a3\":\"some val\"}"));
    }
}